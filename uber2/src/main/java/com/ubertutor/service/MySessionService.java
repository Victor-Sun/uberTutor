package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MySessionService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> getSessions(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM USER_SESSIONS");
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	public List<Map<String,Object>> getUserSessions(Long id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE STUDENT_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	public List<Map<String,Object>> getTutorSessions(Long id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForList(sql.toString());
	}
}
