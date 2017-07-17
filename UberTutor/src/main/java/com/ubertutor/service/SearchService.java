package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.nec.flowlites.core.FLJdbcTemplate;
import jp.co.nec.flowlites.core.FLPage;
import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.UserRequestEntity;

@Service
@Transactional
public class SearchService {
	@Autowired
	private FLJdbcTemplate jdbcTemplate;
	@Autowired 
	private UserRequestDAO userRequestDAO;
	
	/**
	 * Returns a list of requests
	 * @param tutorId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public FLPage<Map<String, Object>> getRequests(Long tutorId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_AVAILABLE_REQUESTS WHERE TUTOR_ID = ? AND USER_ID <> ?ORDER BY REQUEST_ID, STATUS";
		params.add(tutorId);
		params.add(tutorId);
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	/**
	 * Returns a map of RequestInfo
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
	 * Returns UserRequestEntity
	 * @param requestId
	 * @return
	 */
	public UserRequestEntity get(Long requestId){
		return userRequestDAO.get(requestId);
	}
	
	/**
	 * Saves UserRequestEntity
	 * @param requestEntity
	 */
	public void save(UserRequestEntity requestEntity){
		userRequestDAO.save(requestEntity);
	}
}
