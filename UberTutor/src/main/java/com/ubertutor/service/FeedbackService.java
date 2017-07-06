package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.FeedbackDAO;
import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.FeedbackEntity;
import com.ubertutor.entity.UserRequestEntity;

@Service
@Transactional
public class FeedbackService {
	@Autowired
	private FeedbackDAO feedbackDAO;
	@Autowired
	private UserRequestDAO userRequestDAO;
	private JdbcTemplate jdbcTemplate;
	
	public void save(FeedbackEntity entity){
		this.feedbackDAO.save(entity);
	}
	
	public FeedbackEntity getFeedback(Long id){
		return this.feedbackDAO.get(id);
	}
	
	public UserRequestEntity getRequest(Long id){
		return this.userRequestDAO.get(id);
	}
	
	public Map<String, Object> getFeedbackInfo(Long requestId){
		String sql = "SELECT * FROM USER_FEEDBACK WHERE FEEDBACK_ID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(requestId);
		return jdbcTemplate.queryForMap(sql, params.toArray());
	}
}
