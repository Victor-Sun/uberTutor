package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.UserRequestEntity;

@Service
@Transactional
public class MySessionService {
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	@Autowired 
	private UserRequestDAO userRequestDAO;
	
	public List<Map<String,Object>> getSessions(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM USER_SESSIONS");
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	public GTPage<Map<String, Object>> getUserSessions(Long studentId, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE STUDENT_ID = ?");
		params.add(studentId);
		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize, params.toArray());
	}
	
	public GTPage<Map<String,Object>> getTutorSessions(Long tutorId, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM USER_SESSIONS WHERE TUTOR_ID = ?");
		params.add(tutorId);
		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize, params.toArray());
	}
	
	public Map<String, Object> getSessionInfo(Long requestId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM SESSION_INFO WHERE REQUEST_ID = ?");
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}
	
	public void updateRequest(UserRequestEntity entity){
		this.userRequestDAO.save(entity);
	}
	
	public UserRequestEntity get(Long id){
		return this.userRequestDAO.get(id);
	}
}
