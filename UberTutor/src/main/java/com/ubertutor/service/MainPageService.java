package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.nec.flowlites.core.FLJdbcTemplate;
import jp.co.nec.flowlites.core.FLPage;

@Service
@Transactional
public class MainPageService {
	@Autowired
	private FLJdbcTemplate jdbcTemplate;
	
	/**
	 * Returns user's previous tutors
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return User's previous tutors
	 */
	public FLPage<Map<String, Object>> getPreviousTutors(Long userId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_PREVIOUS_TUTOR WHERE USER_ID = ?";
		params.add(userId);
		return this.jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	public FLPage<Map<String, Object>> getCurrentUserRequests(Long userId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USER_SESSIONS WHERE USER_ID = ?";
		params.add(userId);
		return this.jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	public FLPage<Map<String,Object>> getCurrentTutorRequests(Long tutorId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USER_SESSIONS WHERE TUTOR_ID = ? ORDER BY STATUS DESC, REQUEST_ID";
		params.add(tutorId);
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	
}
