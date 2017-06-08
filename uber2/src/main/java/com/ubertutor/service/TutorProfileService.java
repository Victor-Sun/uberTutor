package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class TutorProfileService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserDAO userDAO;

	public UserEntity getUser(Long id){
		return userDAO.get(id);
	}
	
	public String getUserFullname(Long id){
		UserEntity user = userDAO.get(id);
		return user.getFullname();
	}

	public Integer getRatingTotal(String id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT RATING FROM FEEDBACK WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
	}

	public Integer getRatingCount(String id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*) FROM FEEDBACK WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForInt(sql.toString(),params.toArray()); 
	}

	public Integer getTotalCompletedRequests(String id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*) FROM USER_REQUEST_STATUS WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
	}
	
	public List<Map<String,Object>> getFeedback(String id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM FEEDBACK WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
}
