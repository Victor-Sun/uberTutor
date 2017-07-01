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
	
	public FLPage<Map<String, Object>> getRequests(int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM USER_SESSIONS WHERE STATUS = 'OPEN' OR STATUS = 'PENDING'");
		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize);
	}
	
	public Map<String, Object> getRequestInfo(Long requestId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * FROM SESSION_INFO WHERE REQUEST_ID = ?");
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}
	
	public UserRequestEntity get(Long id){
		return userRequestDAO.get(id);
	}
	
	public void save(UserRequestEntity entity){
		userRequestDAO.save(entity);
	}
}
