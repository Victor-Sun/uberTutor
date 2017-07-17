package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.UserRequestEntity;

import jp.co.nec.flowlites.core.FLJdbcTemplate;
import jp.co.nec.flowlites.core.FLPage;

@Service
@Transactional
public class MySessionService {
	@Autowired
	private FLJdbcTemplate jdbcTemplate;
	@Autowired 
	private UserRequestDAO userRequestDAO;
	
	/**
	 * Returns list of all sessions
	 * @return
	 */
	public List<Map<String,Object>> getRequests(){
		String sql = "SELECT * FROM V_USER_SESSIONS";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * Returns list of a user's requests
	 * @param studentId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public FLPage<Map<String, Object>> getUserRequests(Long studentId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USER_SESSIONS WHERE STUDENT_ID = ? ORDER BY STATUS DESC, REQUEST_ID";
		params.add(studentId);
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	/**
	 * Returns list of tutor's requests
	 * @param tutorId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public FLPage<Map<String,Object>> getTutorRequests(Long tutorId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USER_SESSIONS WHERE TUTOR_ID = ? ORDER BY STATUS DESC, REQUEST_ID";
		params.add(tutorId);
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	/**
	 * Returns map of RequestInfo
	 * @param requestId
	 * @return
	 */
	public Map<String, Object> getRequestInfo(Long requestId){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_SESSION_INFO WHERE REQUEST_ID = ?";
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql, params.toArray());
	}
	
	/**
	 * Saves UserRequestEntity
	 * @param entity
	 */
	public void save(UserRequestEntity entity){
		this.userRequestDAO.save(entity);
	}
	
	/**
	 * Returns UserRequestEntity
	 * @param requestId
	 * @return
	 */
	public UserRequestEntity getRequest(Long requestId){
		return this.userRequestDAO.get(requestId);
	}
}
