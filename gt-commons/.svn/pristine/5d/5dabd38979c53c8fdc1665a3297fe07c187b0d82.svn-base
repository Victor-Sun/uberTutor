package com.gnomon.common.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.common.system.entity.GTObs;

@Repository
public class GTObsDao extends HibernateDao<GTObs, String> {

	public List<GTObs> getChildrenList(String parentId){
		String sql="from GTObs obs  where  obs.parentId = "+parentId+" order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();
	}

	
	
	public List<GTObs> getObsList(){
		String sql="from GTObs obs order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();
	}
	
	public List<GTObs> getAllChildrenList(String parentId){
		GTObs obs = this.get(parentId);
		String sql="from GTObs obs where obs.left >= "+obs.getLeft()+ " and obs.right<="+obs.getRight()+" order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();
	}

	public List<GTObs> getAllChildrenList(String parentId,String obsName){
		GTObs obs = this.get(parentId);
		String sql="from GTObs obs where obs.left >= "+obs.getLeft()+ " and obs.right<="+obs.getRight() +" and obsName = '"+obsName.trim()+"'  order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();
	}
	
	public List<GTObs> getRootObsList(){
		String sql="from GTObs obs  where  obs.parentId is null order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();
	}


	public List<GTObs> getAllAncestryList(String obsId) {
		GTObs obs = this.get(obsId);
		String sql="from GTObs obs where obs.left < "+obs.getLeft()+ " and obs.right>"+obs.getRight() +" order by obs.left";
		Query query = this.createQuery(sql);
		return query.list();	
	}
	
	public GTObs getParentObs(String obsId){
		String hql="select parentObs from GTObs obs,GTObs parentObs where obs.parentId = parentObs.id and obs.id=?";
		return this.findUnique(hql, obsId);
	}
	

}
