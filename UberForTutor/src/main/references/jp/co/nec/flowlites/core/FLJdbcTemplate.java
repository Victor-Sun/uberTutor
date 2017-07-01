package jp.co.nec.flowlites.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.gnomon.common.constant.GTConstants;

/**
 * ��ͨJDBC�ƥ�ץ�`�Ȼ��ץ��饹��<br />
 * ���Υ��饹�Ǥϡ����Ʃ`�ȥ��Ȥ����ɡ� �g�ФΤ褦�ʥ���JDBC�Υ�`���ե�`��g�Ф���
 * ���ץꥱ�`����󥳩`�ɤ���SQL���ܤ��ɤ��ȽY���γ������x���롣
 * <p>
 * <a href="FLJdbcTemplate.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class FLJdbcTemplate extends JdbcTemplate
{
  /* ����JDBC�ƥ�ץ�`�� */
  private NamedParameterJdbcTemplate namedJdbcTempleate;

  /* �����Y���������� */
  private int searchLimit = 0;

  /* �ǩ`���٩`����� */
  private String dbProductName = GTConstants.DATABASE_ORACLE;
  
  /**
   * OrderBy��Ф����񤫤Υե饰
   * add by dhc 2011/10/18 DB2����
   */
  private boolean useOrderByFlg = false;
  /**
   * �ǥե���ȥ��󥹥ȥ饯����
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�Τ⤷�ޤ���
   * <dt>���÷�����
   * <dd>�I�ե��`�ӥ��ǥѥ��`��ָ�����ʤ��Έ��ϡ����Υ᥽�åɤ���������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ����ɤ��ޤ���
   * </dl>
   */
  public FLJdbcTemplate() {

  }

  /**
   * ָ�����줿�ǩ`���٩`����ꡢFLJdbcTemplate�򘋺B���ޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>ָ�����줿�ǩ`���٩`�������ä��ơ����`�ѩ`���饹�Υ��󥹥ȥ饯������ӳ�����
   * <dt>���÷�����
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#createFLJdbcTemplate(DataSource)}�ڲ���ָ�����줿�ǩ`���٩`�������ä��ơ����Υ᥽�åɤ���������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param dataSource �ǩ`�����`��
   */
  public FLJdbcTemplate(DataSource dataSource) {
    super(dataSource);
  }

  /**
   * �ǩ`���٩`���ηN����Ό��궨����ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥����O�������ǩ`���٩`���ηN����Ό��궨��({@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_ORACLE}�ޤ���{@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_MSSQL})�򷵤���
   * <dt>���÷�����
   * <dd>�Y���������ޗ����ȥک`���󥰗����r�ˤ��Υ�å��ɤ�������줿ȡ�ä������ꂎ���SQL�Ĥ����ɤ��ޤ���
   * </dl>
   * 
   * @return ���Υ��󥹥����O�������ǩ`���٩`���ηN����Ό��궨��({@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_ORACLE}�ޤ���{@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_MSSQL})
   */
  public String getDbProductName() {
    return dbProductName;
  }

  /**
   * �ǩ`���٩`���ηN����Ό��궨�����O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υǩ`���٩`���ηN����Ό��궨�����O�����ޤ���
   * <dt>���÷�����
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#setDatabaseProductName(String)}�ڲ���ָ�����줿�ǩ`���٩`���ηN����Ό��궨�������ä��ơ����Υ᥽�åɤ����������ǩ`���٩`���ηN����Ό��궨�����O�����ޤ���
   * </dl>
   * 
   * @param dbProductName ���ץꥱ�`����󥳥�ƥ����Ȥ�XML�ե�����ˤ��O�������ǩ`���٩`���ηN����Ό��궨��({@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_ORACLE}�ޤ���{@link jp.co.com.gnomon.common.constant.common.GTConstants#DATABASE_MSSQL})
   */
  public void setDbProductName(String dbProductName) {
    this.dbProductName = dbProductName;
  }

  /**
   * �����Y�����޼�����ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ڲ��Η����Y�����޼����򷵤���
   * <dt>���÷�����
   * <dd>�ǩ`�������������ϡ������Y���������ޱ�Ҫ�r�ˡ����Υ�å��ɤ�������줿ȡ�ä��������Y�����޼�����ꡢ�I����Ф���
   * </dl>
   * 
   * @return �O�����������Y�����޼���
   */
  public int getSearchLimit() {
    return searchLimit;
  }

  /**
   * �����Y�����޼������O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Η����Y�����޼������O�����ޤ���
   * <dt>���÷�����
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#setSearchLimit(int)}�ڲ���ָ�����줿�����Y�����޼��������ä��ơ����Υ᥽�åɤ��������������Y�����޼������O�����ޤ���
   * </dl>
   * 
   * @param searchLimit �����Y�����޼���
   */
  public void setSearchLimit(int searchLimit) {
    this.searchLimit = searchLimit;
  }

  /**
   * �������ޥ����ɥ饤�Х��饹���O�����ޤ�.
   *
   * @param useOrderByFlg OrderBy��Ф����񤫤Υե饰
   * add by dhc 2011/10/18 DB2����
   */
  public void setUseOrderByFlg(boolean useOrderByFlg) {
    this.useOrderByFlg = useOrderByFlg;
  }

  /**
   * �����ýY���������ޤǤ�SQL�Ĥ����ɤ��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼�������0���Έ��ϡ�SQL�Ĥ򷵤���
   * <li>�����Y�����޼�������0���Ǥʤ��Έ��ϡ�{@link SqlBuilder#generateLimitSql(String, int, String)}��������졢�I�����SQL�Ĥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param sql �I��ǰ��SQL��
   * @return �I�����SQL��
   */
  private String getLimitSql(String sql) {
	  return sql;
  }

  /**
   * �ک`���󥰤�SQL�Ĥ����ɤ��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link jp.co.nec.flowlites.core.jdbc.util.SqlBuilder#getPaginationSql(String, int, int, String)}����������ک`�����ä�SQL�Ĥ����ɤ��ޤ���
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param sql �I��ǰ��SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`�����Ŀ��
   * @return �I�����SQL��
   */
  private String getPageSearchSql(String sql, int pageNo, int pageSize) {
    return SqlBuilder.getPaginationSql(sql, pageNo, pageSize,
      getDbProductName());
  }

  /**
   * �����Y���ξt�ک`������ȡ�ä��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ک`���󥰗����ä�SQL�Ĥ�ȡ�ä��ơ���������ä��ơ������Y���Υ쥳�`������ȡ�ä��ޤ���
   * <li>ȡ�ä����쥳�`������ꡢ�����Y���ξt�ک`������ȡ�ä��ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param sql SQL��
   * @param pageSize ���ک`�����Ŀ��
   * @return �t�ک`����
   */
  private int getTotalCount(String sql) {
      // upd by dhc 2011/10/21 -- start
      //return queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()));
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // �ե饰��true���{����
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // �ե饰��false���{����
          orderByFlg = false;
      } else {
          // useOrderByFlg�΂���ե饰�˸�{����
          orderByFlg = this.useOrderByFlg;
      }
      // ����ȡ��SQL����
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // ����ȡ��
      return queryForInt(sqlForQuery);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * �����Y���ξt�ک`������ȡ�ä��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ک`���󥰗����ä�SQL�Ĥ�ȡ�ä��ơ���������ä��ơ������Y���Υ쥳�`������ȡ�ä��ޤ���
   * <li>ȡ�ä����쥳�`������ꡢ�����Y���ξt�ک`������ȡ�ä��ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param sql SQL��
   * @param args �ѥ��`��
   * @param pageSize ���ک`�����Ŀ��
   * @return �t�ک`����
   */
  private int getTotalCount(String sql, Object[] args) {
      // upd by dhc 2011/10/21 -- start
      //return this.queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()),
      //  args);
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // �ե饰��true���{����
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // �ե饰��false���{����
          orderByFlg = false;
      } else {
          // useOrderByFlg�΂���ե饰�˸�{����
          orderByFlg = this.useOrderByFlg;
      }
      // ����ȡ��SQL����
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // ����ȡ��
      return this.queryForInt(sqlForQuery, args);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * �����Y���ξt�ک`������ȡ�ä��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ک`���󥰗����ä�SQL�Ĥ�ȡ�ä��ơ���������ä��ơ������Y���Υ쥳�`������ȡ�ä��ޤ���
   * <li>ȡ�ä����쥳�`������ꡢ�����Y���ξt�ک`������ȡ�ä��ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param sql SQL��
   * @param paraMap �ѥ��`���ޥå�
   * @param pageSize ���ک`�����Ŀ��
   * @return �t�ک`����
   */
  private int getTotalCount(String sql, Map paraMap) {
      // upd by dhc 2011/10/21 -- start
      //return this.queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()),
      //  paraMap);
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // �ե饰��true���{����
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // �ե饰��false���{����
          orderByFlg = false;
      } else {
          // useOrderByFlg�΂���ե饰�˸�{����
          orderByFlg = this.useOrderByFlg;
      }
      // ����ȡ��SQL����
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // ����ȡ��
      return this.queryForInt(sqlForQuery, paraMap);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * �����Y���ξt�ک`������ȡ�ä��롣
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>ȡ�ä����쥳�`������ꡢ�����Y���ξt�ک`������ȡ�ä��ޤ���
   * <dt>���÷�����
   * <dd>�����Y���������ޤ��Ȥ�������ϡ����Υ᥽�åɤ��������������������SQL�Ĥ����ɤ��롣
   * </dl>
   * 
   * @param count �����Y���ξt����
   * @param pageSize ���ک`�����Ŀ��
   * @return �t�ک`����
   */
  private int getPageTotalCount(int count, int pageSize) {
	if (pageSize == 0) return 0;

    if (count % pageSize == 0) {
      return count / pageSize;
    } else {
      return (count / pageSize) + 1;
    }
  }

  /**
   * �ѥ��`���Х��֥������Ȥ�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link jp.FLDBUtil.nec.flowlites.core.jdbc.util.FLDBUtil#initParameterizedRowMapper(Class)}����������Y���򷵤���
   * <dt>���÷�����
   * <dd>���Υ᥽�åɤ��ڲ��ΤߤǤ����á�
   * </dl>
   * 
   * @param requiredType �ƥ���
   * @return �ѥ��`���Х��֥�������
   */
  private <T> ParameterizedRowMapper<T> getParameterizedRowMapper(
    Class<T> requiredType) {
    return FLDBUtil.initParameterizedRowMapper(requiredType);
  }

  /**
   * �ǩ`����һ�����¤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����ä΂������ڤ�������å����ޤ���
   * <li>���ڤ�����ϡ�<code>org.springframework.jdbc.core.BatchPreparedStatementSetter</code>���֥������Ȥ����ɤ���
   * <code>org.springframework.jdbc.core.JdbcTemplate</code>��<code>batchUpdate(java.lang.String, org.springframework.jdbc.core.BatchPreparedStatementSetter)</code>���������
   * <li>���ڤ��ʤ����ϡ�<code>org.springframework.jdbc.core.JdbcTemplate</code>��<code>batchUpdate(java.lang.String[])</code>���������
   * <li> ���½Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`����һ�����¤���r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql SQL��
   * @param types �ƥ���
   * @param values ��
   * @return �I��Y��({0,1,0}:{ʧ��,�ɹ�,ʧ��})
   */
  public int[] batchUpdate(String sql, int[] types, Object... values) {
    final int[] updateTypes = types;
    final Object[] updateValues = values;
    if (values != null && values.length > 0) {
      BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int index)
          throws SQLException {
          int startIndex = index * updateTypes.length;
          for (int i = 0; i < updateTypes.length; i++) {
            // ps.setObject(i+1,updateValues[startIndex+i],updateTypes[i]);
            StatementCreatorUtils.setParameterValue(ps, i + 1, updateTypes[i],
              updateValues[startIndex + i]);
          }
        }

        public int getBatchSize() {
          return updateValues.length / updateTypes.length;
        }
      };
      return super.batchUpdate(sql, setter);
    } else {
      return super.batchUpdate(new String[] { sql });
    }
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�Ǘ��������ꥹ���Ф˥ޥåԥ󥰤��ͽY����֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼����������ä�SQL�Ĥ����ɤ��ޤ���
   * <li><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>��<code>query(java.lang.String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, java.lang.Object[])</code>��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @param args �o���ޥѥ��`���`
   * @return �ޥåԥ󥰤��ͽY����֤ĥꥹ��
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> query(String sql, ParameterizedRowMapper<T> rm,
    Object... args) {
    String limitSql = getLimitSql(sql);
    return super.query(limitSql, args, rm);
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�Ǘ��������ꥹ���Ф�ָ��Object�ͽY����֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼����������ä�SQL�Ĥ����ɤ��ޤ���
   * <li>{@link #query(String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, Object[])}��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param requiredType �Y��Object�Υ��饹
   * @param args �o���ޥѥ��`���`
   * @return �ޥåԥ󥰤��ͽY����֤ĥꥹ��
   */
//  public <T> List<T> query(String sql, Class<T> requiredType, Object... args) {
//    return query(sql, getParameterizedRowMapper(requiredType), args);
//  }

  /**
   * �ѥ��`���`�Ǘ��������ꥹ���Ф˥ޥå׽Y����֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�o���ޥѥ��`���`��������ϡ����`�ѩ`���饹��<code>queryForInt(String, Object)</code>��������������Y���򷵤���
   * <li>�o���ޥѥ��`���`���ʤ����ϡ����`�ѩ`���饹��<code>queryForInt(String)</code>��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param arg �ѥ��`���`
   * @return �ޥå׽Y����֤ĥꥹ��
   */
  public int queryForInt(String sql, Object arg) {
    if (arg == null) {
      return super.queryForInt(sql);
    } else {
      return super.queryForInt(sql, new Object[] { arg });
    }
  }

  /**
   * �ޥå��ͽY����g���������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>��<code>queryForInt(java.lang.String, java.util.Map)</code>��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǥޥå׽Y�����������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paraMap �ѥ��`���`�ޥå�
   * @return �ޥå׽Y��
   */
  public int queryForInt(String sql, Map paraMap) {
    return getNamedJdbcTempleate().queryForInt(sql, paraMap);
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤�o���ޥѥ��`���`�ǅg����������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>��<code>queryForObject(java.lang.String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, java.lang.Object[])</code>��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǽY��Object�ؤΥޥåԥ󥰤��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @param args �o���ޥѥ��`���`
   * @return �ޥåԥ󥰤�Object
   */
  @SuppressWarnings("unchecked")
  public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm,
    Object... args) {
    return (T)super.queryForObject(sql, args, rm);
  }

  /**
   * ָ���Y�����饹�ȟo���ޥѥ��`���`�ǅg���ǩ`����������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link #queryForObject(String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, Object[])}��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO��ָ���Y�����饹���������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param requiredType ָ��Object���饹
   * @param args �o���ޥѥ��`���`
   * @return ָ���Y�����饹���֥�������
   */
//  public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
//    return queryForObject(sql, getParameterizedRowMapper(requiredType), args);
//  }

  /**
   * �ѥ��`���`�Ǘ��������ꥹ���Ф˥ޥå׽Y����֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼����������ä�SQL�Ĥ����ɤ��ޤ���
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>��<code>queryForList(java.lang.String, java.util.Map)</code>��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paramMap �ѥ��`���`�ޥå�
   * @return �ޥå׽Y����֤ĥꥹ��
   */
  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> queryForList(String sql,
    Map<String, Object> paramMap) {
    return getNamedJdbcTempleate().queryForList(getLimitSql(sql), paramMap);

  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȥѥ��`���`�Ǘ��������ꥹ���Ф�ָ�����饹�Υ��֥������Ȥ�֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼����������ä�SQL�Ĥ����ɤ��ޤ���
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>��<code>query(java.lang.String, java.util.Map, org.springframework.jdbc.core.RowMapper)</code>��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paramMap �ѥ��`���`�ޥå�
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @return ָ���Y�����饹��֤ĥꥹ��
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> queryForList(String sql, Map<String, Object> paramMap,
    ParameterizedRowMapper<T> rm) {
    return getNamedJdbcTempleate().query(getLimitSql(sql), paramMap, rm);
  }

  /**
   * �Y��Object�Υ��饹�ȥѥ��`���`�Ǘ��������ꥹ���Ф�ָ�����饹�Υ��֥������Ȥ�֤��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link #queryForList(String, java.util.Map, org.springframework.jdbc.core.simple.ParameterizedRowMapper)}��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`���ꥹ�Ȥ��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paramMap �ѥ��`���`�ޥå�
   * @param requiredType �Y��Object�Υ��饹
   * @return ָ���Y�����饹��֤ĥꥹ��
   */
//  public <T> List<T> queryForList(String sql, Map<String, Object> paramMap,
//    Class<T> requiredType) {
//    return queryForList(sql, paramMap, getParameterizedRowMapper(requiredType));
//  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤�ѥ��`���`�ǅg����������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�����Y�����޼����������ä�SQL�Ĥ����ɤ��ޤ���
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>��<code>queryForObject(java.lang.String, java.util.Map, org.springframework.jdbc.core.RowMapper)</code>��������������Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǽY��Object�ؤΥޥåԥ󥰤��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paramMap �ѥ��`���`�ޥå�
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @return ָ�����饹��Object
   */
  @SuppressWarnings("unchecked")
  public <T> T queryForObject(String sql, Map<String, Object> paramMap,
    ParameterizedRowMapper<T> rm) {
    return (T)getNamedJdbcTempleate().queryForObject(getLimitSql(sql),
      paramMap, rm);
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤�ѥ��`���`�ǅg����������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link #queryForObject(String, Map, org.springframework.jdbc.core.simple.ParameterizedRowMapper)}��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǽY��Object�ؤΥޥåԥ󥰤��������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paramMap �ѥ��`���`�ޥå�
   * @param requiredType �Y��Object�Υ��饹
   * @return ָ�����饹��Object
   */
  public <T> T queryForObject(String sql, Map<String, Object> paramMap,
    Class<T> requiredType) {
    return queryForObject(sql, paramMap,
      getParameterizedRowMapper(requiredType));
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�ǥک`���󥰗������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}��������������Y���ξt�ک`������ȡ�ä��ޤ���
   * <li>{@link #getPageSearchSql(String, int, int)}����������ک`���󥰗����ä�SQL�Ĥ����ɤ��ޤ���
   * <li>{@link #query(String, ParameterizedRowMapper, Object...)}��������������Y����ȡ�ä��ޤ���
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.FLPage}���֥������Ȥ����ɤ����Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`���󥰗�������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`���Υ쥳�`����
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @param args �o���ޥѥ��`���`
   * @return �ک`���󥰥��֥�������
   */
  @SuppressWarnings("unchecked")
  public <T> FLPage<T> queryPagination(String sql, int pageNo, int pageSize,
    ParameterizedRowMapper<T> rm, Object... args) {
    int totalCount = getTotalCount(sql, args);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    FLResultSetExtractor rse =  new FLResultSetExtractor(pageNo,pageSize,rm);
    super.query(sql, args,  rse);
    List<T> items = rse.getItems();
    FLPage<T> ps = new FLPage<T>(items, totalCount, 
    		limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�ǥک`���󥰗������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link #queryPagination(String, int, int, ParameterizedRowMapper, Object[])}��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`���󥰗�������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`���Υ쥳�`����
   * @param requiredType �Y��Object���饹
   * @param args �o���ޥѥ��`���`
   * @return �ک`���󥰥��֥�������
   */
  public <T> FLPage<T> queryPagination(String sql, int pageNo, int pageSize,
    Class<T> requiredType, Object... args) {
    return queryPagination(sql, pageNo, pageSize,
      getParameterizedRowMapper(requiredType), args);
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�ǥک`���󥰗������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}��������������Y���ξt�ک`������ȡ�ä��ޤ���
   * <li>{@link #getPageSearchSql(String, int, int)}����������ک`���󥰗����ä�SQL�Ĥ����ɤ��ޤ���
   * <li>{@link #queryForList(String, Map, ParameterizedRowMapper)}��������������Y����ȡ�ä��ޤ���
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.FLPage}���֥������Ȥ����ɤ����Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`���󥰗�������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`���Υ쥳�`����
   * @param rm �Y��Object�ؤΥޥåԥ�
   * @param paramMap �ѥ��`���`
   * @return �ک`���󥰥��֥�������
   */
  @SuppressWarnings("unchecked")
  public <T> FLPage<T> queryPagination(String sql, int pageNo, int pageSize,
    ParameterizedRowMapper<T> rm, Map<String, Object> paramMap) {
    int totalCount = getTotalCount(sql, paramMap);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    FLResultSetExtractor rse =  new FLResultSetExtractor(pageNo,pageSize,rm);
    List<T> items = (List<T>)getNamedJdbcTempleate().query(sql, paramMap, rse);
    FLPage<T> ps = new FLPage<T>(items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * �Y��Object�ؤΥޥåԥ󥰤ȟo���ޥѥ��`���`�ǥک`���󥰗������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>{@link #queryPagination(String, int, int, ParameterizedRowMapper, Map)}��������������Y���򷵤���
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`���󥰗�������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`���Υ쥳�`����
   * @param requiredType �Y��Object���饹
   * @param paramMap �ѥ��`���`
   * @return �ک`���󥰥��֥�������
   */
  public <T> FLPage<T> queryPagination(String sql, int pageNo, int pageSize,
    Class<T> requiredType, Map<String, Object> paramMap) {
    return queryPagination(sql, pageNo, pageSize,
      getParameterizedRowMapper(requiredType), paramMap);
  }

  /**
   * �ک`���󥰗������ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}��������������Y���ξt�ک`������ȡ�ä��ޤ���
   * <li>{@link #getPageSearchSql(String, int, int)}����������ک`���󥰗����ä�SQL�Ĥ����ɤ��ޤ���
   * <li><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>��<code>queryForList(java.lang.String, java.lang.Object[])</code>��������������Y����ȡ�ä��ޤ���
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.FLPage}���֥������Ȥ����ɤ����Y���򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`���󥰗�������r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param pageNo �ک`������
   * @param pageSize ���ک`���Υ쥳�`����
   * @return �ک`���󥰥��֥�������
   */
  @SuppressWarnings("unchecked")
  public <T> FLPage<Map<String, Object>> queryPagination(String sql,
    int pageNo, int pageSize) {
    int totalCount = getTotalCount(sql);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    FLResultSetExtractor rse =  new FLResultSetExtractor(pageNo,pageSize);
    super.query(sql, rse);
    List items = rse.getItems();
    FLPage<Map<String, Object>> ps = new FLPage<Map<String, Object>>
      (items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }
  
  @SuppressWarnings("unchecked")
  public <T> FLPage<Map<String, Object>> queryPagination(String sql,
    int pageNo, int pageSize,Object... args) {
    int totalCount = getTotalCount(sql,args);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    List<Map<String, Object>> list = super.queryForList(getPageSearchSql(sql, pageNo,pageSize),args);
    FLPage<Map<String, Object>> ps = new FLPage<Map<String, Object>>(list, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * �ǩ`������I���ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ѥ��`���`���Пo�������å����ޤ���
   * <li>�o���Έ��ϡ����`�ѩ`���饹��<code>update(String)</code>����������ǩ`������I���ޤ���
   * <li>�Ф�Έ��ϡ�<code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>��<code>update(java.lang.String, java.lang.Object)</code>����������ǩ`������I���ޤ���
   * <li>���½Y�������򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`������¤���r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param arg �ѥ��`���`
   * @return ���¼���
   */
  public int update(String sql, Object arg) {
    if (arg == null) {
      return super.update(sql);
    } else {
      return super.update(sql, new Object[] { arg });
    }
  }

  /**
   * �ǩ`������I���ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>��<code>update(java.lang.String, java.util.Map)</code>����������ǩ`������I���ޤ���
   * <li>���½Y�������򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥǩ`������¤���r�ˤ��Υ᥽�åɤ��������
   * </dl>
   * 
   * @param sql �����ä�SQL��
   * @param paraMap �ѥ��`���`�ޥå�
   * @return ���¼���
   */
  public int update(String sql, Map paraMap) {
    return getNamedJdbcTempleate().update(sql, paraMap);
  }

  /**
   * ���Υ��󥹥����ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ򷵤���
   * <ul>
   * <li>�ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ�Null���ϡ������Y���������ޤĤ��Υ��饷�å�JDBC�ƥ�ץ�`�ȥ��֥������Ȥ����ɤ��ޤ���
   * <li>���ɤ��줿���饷�å�JDBC�ƥ�ץ�`�Ȥ����ä������ɤ��줿�����ƥ�ץ�`�ȥ��֥������Ȥ򷵤���
   * <li>�ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ�Null�Ǥʤ����ϡ��ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�ǩ`���٩`���Υǩ`��������I����r�� ���Υ�å��ɤ��������줿ȡ�ä�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ����ä��ơ��ǩ`����������ޤ���
   * </dl>
   * 
   * @return �Є�������JDBC�ƥ�ץ�`�ȥ��֥�������
   */
  protected NamedParameterJdbcTemplate getNamedJdbcTempleate() {
    if (namedJdbcTempleate == null) {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
      jdbcTemplate.setMaxRows(getSearchLimit());
      return new NamedParameterJdbcTemplate(jdbcTemplate);
    } else
      return namedJdbcTempleate;
  }

  /**
   * ���Υ��󥹥����ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ��O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ڲ�������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ��O�����ޤ���
   * <dt>���÷�����
   * <dd>�I�ե��`�ӥ��Ǽȴ������JDBC�ƥ�ץ�`�ȥ��֥������Ȥ����ä�����ϡ����Υ᥽�åɤ��������
   * </dl>
   * 
   * @param namedJdbcTempleate �Є�������JDBC�ƥ�ץ�`�ȥ��֥�������
   */
  protected void setNamedJdbcTempleate(
    NamedParameterJdbcTemplate namedJdbcTempleate) {
    this.namedJdbcTempleate = namedJdbcTempleate;
  }

  // add - start - by qnes '09.07.06
  public <T> FLPage<T> queryPaginationEx(
		  String countSql
		  , List<Object> countArgs
		  , String sql
		  , ParameterizedRowMapper<T> rm
		  , List<Object> args
		  , int pageNo
		  , int pageSize
  ) {
	  //-----------------------------------------------------------------------
	  // ����ȡ��SQL�ΰk��
	  //-----------------------------------------------------------------------
	  int totalCount = getTotalCountByCountSql( countSql, countArgs );
	  //-----------------------------------------------------------------------
	  // ���������O��
	  // ����������searchLimit����󤭤ʂ��Έ��ϡ�searchLimit��������
	  // ���Ȥ��롣
	  //-----------------------------------------------------------------------
	  int limitCount = totalCount;
	  if (this.searchLimit > 0 && this.searchLimit < totalCount) {
		  limitCount = this.searchLimit;
	  }
	  //-----------------------------------------------------------------------
	  // �ک`����ȡ��
	  //-----------------------------------------------------------------------
	  int pageTotalCount = getPageTotalCount(limitCount, pageSize);
	  //-----------------------------------------------------------------------
	  // �ک`����ȡ��
	  //-----------------------------------------------------------------------
	  FLResultSetExtractor rse =  new FLResultSetExtractor(pageNo,pageSize,rm);
	  super.query(sql, args.toArray(),  rse);
	  //-----------------------------------------------------------------------
	  // �Y��ȡ�����
	  //-----------------------------------------------------------------------
	  List<T> items = rse.getItems();
	  FLPage<T> ps = new FLPage<T>(items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);

	  return ps;
  }

  private int getTotalCountByCountSql(String countSql, List<Object> countArgs) {
	    return queryForInt( countSql, countArgs.toArray() );
  }
  // add - end - by qnes '09.07.06


}
