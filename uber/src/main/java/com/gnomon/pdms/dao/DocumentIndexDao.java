package com.gnomon.pdms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMDocumentIndexEntity;

@Repository
public class DocumentIndexDao extends HibernateDao<PMDocumentIndexEntity, String> {
	
	public List<Long> findDocIdsPage(Page<PMDocumentIndexEntity> page,String hql,Map<String, ?> values){
		
		List<Long> docIds = new ArrayList<Long>();
		
		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);
		
		docIds = q.list();
		
		
		return docIds;
	}
	
	public String findDocStage(String docId){
		String shortName = "";
		String sql="select shortName from GTStage where id = (select stageid from GTProcess where id = (select gtProcessId from GTDocumentIndex where gtDocumentId = '"+docId+"'))";
		Query query = createQuery(sql);
		List list = query.list();
		if(list.size() > 0){
			shortName = query.list().get(0).toString();
		}
		return shortName;
	}
}

