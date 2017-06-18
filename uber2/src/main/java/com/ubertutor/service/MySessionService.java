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
	
	public List<Map<String,Object>> getUserSessions(Long studentId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE STUDENT_ID = ?");
		params.add(studentId);
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public List<Map<String,Object>> getTutorSessions(Long tutorId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE TUTOR_ID = ?");
		params.add(tutorId);
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public Map<String, Object> getSessionInfo(Long requestId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM SESSION_INFO WHERE REQUEST_ID = ?");
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}
	
	public int updateRequest(String statusCode, Long requestId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" UPDATE USER_REQUEST SET STATUS = ? WHERE REQUEST_ID = ?");
		params.add(statusCode);
		params.add(requestId);
		return this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
}
