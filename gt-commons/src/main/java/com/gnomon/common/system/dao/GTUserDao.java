package com.gnomon.common.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.common.system.entity.SysUserEntity;

@Repository
public class GTUserDao extends HibernateDao<SysUserEntity, String>{
	
	public List<SysUserEntity> getUserListOfName(String userName) {
		String sql="from SysUserEntity where username like '%"+userName+"%' or userid like '%"+userName+"%'";
		Query query = this.createQuery(sql);
		return query.list();
	}
	
	public SysUserEntity getUserByUseridOrUsername(String useridOrUsername) {
		SysUserEntity user = null;
		String sql="from SysUserEntity where userid = ? or  username = ?";
		Query query = this.createQuery(sql,useridOrUsername,useridOrUsername);
		List<SysUserEntity> list = query.list();
		if(list.size() > 0){
			user =  list.get(0);
		}
		return user;
	}
}
