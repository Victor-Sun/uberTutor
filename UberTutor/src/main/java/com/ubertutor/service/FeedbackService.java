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
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Saves entity to db
	 * @param FeedbackEntity entity
	 */
	public void save(FeedbackEntity entity){
		this.feedbackDAO.save(entity);
	}

	/**
	 * Saves entity to db
	 * @param FeedbackEntity entity
	 */
	public void save(UserRequestEntity entity){
		this.userRequestDAO.save(entity);
	}

	/**
	 * Returns FeedbackEntity
	 * @param id
	 * @return FeedbackEntity
	 */
	public FeedbackEntity getFeedback(Long id){
		return this.feedbackDAO.get(id);
	}

	/**
	 * Returns UserRequestEntity
	 * @param requestId
	 * @return UserRequestEntity
	 */
	public UserRequestEntity getRequest(Long requestId){
		return this.userRequestDAO.get(requestId);
	}

	/**
	 * Returns feedback info
	 * @param requestId
	 * @return FeedbackInfo as Map
	 */
	public Map<String, Object> getFeedbackInfo(Long requestId, boolean isUser){
		List<Object> params = new ArrayList<Object>();
		String sql = (!isUser) ? "SELECT * FROM V_USER_FEEDBACK WHERE REQUEST_ID = ?" : "SELECT * FROM V_TUTOR_FEEDBACK WHERE REQUEST_ID = ?";
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql, params.toArray());
	}

	/**
	 * Returns true if a request has a feedback
	 * @param requestId
	 * @return True if feedback has been made for request
	 */
	public boolean hasFeedback(Long requestId, boolean isUser){
		try{
			String sql = (!isUser) ? "SELECT TUTOR_FEEDBACK FROM USER_REQUEST WHERE ID = ?" : "SELECT USER_FEEDBACK FROM USER_REQUEST WHERE ID = ?"; 
			List<Object> params = new ArrayList<Object>();
			params.add(requestId);
			return this.jdbcTemplate.queryForObject(sql, new Object[] {requestId}, Integer.class) > 0;
		} catch (Exception e) {
			return false;
		}
	}
}
