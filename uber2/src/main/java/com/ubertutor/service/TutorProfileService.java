package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;

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

	public String getCompletedRequestCount(){

		return null;
	}

	public String getTutorName(Long id){
		UserEntity user = userDAO.get(id);
		return user.getFullname();
	}

	public Integer getRatingTotal(String id){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT RATING FROM FEEDBACK WHERE TUTOR_ID = ?");
		int total = this.jdbcTemplate.queryForInt(sql.toString());
		return total;
	}

	public Integer getRatingCount(String id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*) FROM FEEDBACK WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
	}
	
}
