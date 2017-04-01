package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.pdms.dao.UserDAO;

@Service
@Transactional
public class GTUserManager {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public UserEntity getUser(String userid){
		return userDAO.get(userid);
	}
	
	public List<UserEntity> getUserList(String userName){
		return userDAO.find("from UserEntity where username = ?  ", userName);
	}
	
	public List<Map<String, Object>> getUserList(String userName,String obsId){
		return jdbcTemplate.queryForList(" select * from SYS_USER T1 where PKG_PERMISSION.IS_OBS_MEMBER(T1.ID,'"+obsId+"') = 1 and USERNAME = ?",userName);
	}
}

