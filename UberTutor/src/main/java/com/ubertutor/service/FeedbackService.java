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
	public Map<String, Object> getFeedbackInfo(Long requestId){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USER_FEEDBACK WHERE REQUEST_ID = ?";
		params.add(requestId);
		return this.jdbcTemplate.queryForMap(sql, params.toArray());
	}

	/**
	 * Returns true if a request has a feedback
	 * @param requestId
	 * @return True if feedback has been made for request
	 */
	public boolean hasFeedback(Long requestId){
		try{
			String sql = "SELECT FEEDBACK FROM USER_REQUEST WHERE ID = ?";
			List<Object> params = new ArrayList<Object>();
			params.add(requestId);
			return this.jdbcTemplate.queryForObject(sql, new Object[] {requestId}, Integer.class) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Updates UserRequest with feedbackId
	 * @param requestId
	 * @param feedbackId
	 */
	public void updateRequest(Long requestId, Long feedbackId){
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE USER_REQUEST SET FEEDBACK = ? WHERE ID = ?";
		params.add(feedbackId);
		params.add(requestId);
		this.jdbcTemplate.update(sql, params.toArray());
	}
}
