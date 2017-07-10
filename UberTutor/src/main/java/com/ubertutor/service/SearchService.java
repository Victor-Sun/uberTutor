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
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public FLPage<Map<String, Object>> getRequests(Long userId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM USER_SESSIONS WHERE STUDENT_ID <> ? AND STATUS = 'OPEN' OR STATUS = 'PENDING' ORDER BY REQUEST_ID, STATUS";
		params.add(userId);
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize, params.toArray());
	}
	
	/**
	 * Returns a map of RequestInfo
	 * @param requestId
	 * @return
	 */
	public Map<String, Object> getRequestInfo(Long requestId){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM SESSION_INFO WHERE REQUEST_ID = ?";
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql, params.toArray());
	}
	
	/**
	 * Returns UserRequestEntity
	 * @param id
	 * @return
	 */
	public UserRequestEntity get(Long id){
		return userRequestDAO.get(id);
	}
	
	/**
	 * Saves UserRequestEntity
	 * @param entity
	 */
	public void save(UserRequestEntity entity){
		userRequestDAO.save(entity);
	}
}
