package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.PropertyFilter.MatchType;
import org.springside.modules.utils.reflection.ReflectionUtils;

/**
 * ��װSpringSide��չ���ܵ�Hibernat DAO���ͻ���.
 * 
 * ��չ���ܰ�����ҳ��ѯ,�����Թ��������б��ѯ.
 * ����Service��ֱ��ʹ��,Ҳ������չ����DAO����ʹ��,���������캯����ע��.
 * 
 * @param <T> DAO�����Ķ�������
 * @param <PK> ��������
 * 
 * @author calvin
 */
public class HibernateDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {
	/**
	 * ����Dao������Ĺ��캯��.
	 * ͨ������ķ��Ͷ���ȡ�ö�������Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, String>{
	 * }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * ����ʡ��Dao��, Service��ֱ��ʹ��ͨ��HibernateDao�Ĺ��캯��.
	 * �ڹ��캯���ж����������Class.
	 * eg.
	 * HibernateDao<User, String> userDao = new HibernateDao<User, String>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- ��ҳ��ѯ���� --//

	/**
	 * ��ҳ��ȡȫ������.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����. ע�ⲻ֧�����е�orderBy����.
	 * @param hql hql���.
	 * @param values �����ɱ�Ĳ�ѯ����,��˳���.
	 * 
	 * @return ��ҳ��ѯ���, ��������б����в�ѯ�������.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����. ע�ⲻ֧�����е�orderBy����.
	 * @param hql hql���.
	 * @param values ��������,�����ư�.
	 * 
	 * @return ��ҳ��ѯ���, ��������б����в�ѯ�������.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��Criteria��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.
	 * @param criterions �����ɱ��Criterion.
	 * 
	 * @return ��ҳ��ѯ���.��������б����в�ѯ�������.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page����Ϊ��");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ���÷�ҳ������Query����,��������.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate��firstResult����Ŵ�0��ʼ
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * ���÷�ҳ������Criteria����,��������.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate��firstResult����Ŵ�0��ʼ
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "��ҳ�������������,�����ֶ���������ĸ��������");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * ִ��count��ѯ��ñ���Criteria��ѯ���ܻ�õĶ�������.
	 */
	@SuppressWarnings("unchecked")
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// �Ȱ�Projection��ResultTransformer��OrderByȡ����,������ߺ���ִ��Count����
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		// ִ��Count��ѯ
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// ��֮ǰ��Projection,ResultTransformer��OrderBy�����������ȥ
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		return totalCount;
	}

	//-- ���Թ�������(PropertyFilter)��ѯ���� --//

	/**
	 * �����Բ��Ҷ����б�,֧�ֶ���ƥ�䷽ʽ.
	 * 
	 * @param matchType ƥ�䷽ʽ,Ŀǰ֧�ֵ�ȡֵ��PropertyFilter��MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * �����Թ��������б���Ҷ����б�.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}

	/**
	 * �����Թ��������б��ҳ���Ҷ���.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * ������������������Criterion,��������.
	 */
	protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");
		Criterion criterion = null;
		//����MatchType����criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			break;

		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
		}
		return criterion;
	}

	/**
	 * �����������б���Criterion����,��������.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //ֻ��һ��������Ҫ�Ƚϵ����.
				Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter
						.getMatchType());
				criterionList.add(criterion);
			} else {//�������������Ҫ�Ƚϵ����,����or����.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
}