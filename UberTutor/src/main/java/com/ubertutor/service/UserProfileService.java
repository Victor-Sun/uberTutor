package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class UserProfileService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * Get a user by ID
	 * @param userId
	 * @return UserEntity object
	 */
	public UserEntity getUser(Long userId){
		return userDAO.get(userId);
	}
	
//	/**
//	 * Get a Tutor's total rating
//	 * @param tutorId
//	 * @return Tutor's total rating
//	 */
//	public Integer getRatingTotal(Long tutorId){
//		String sql = "SELECT RATING FROM FEEDBACK WHERE TUTOR_ID = ?";
//		Integer count = this.jdbcTemplate.queryForObject(sql, new Object[] {tutorId}, Integer.class);
//		return ((count==null) ? 0 : count);
//	}
//
//	/**
//	 * Get the total rating count a tutor has
//	 * @param id
//	 * @return Amount of ratings a tutor has received
//	 */
//	public Integer getRatingCount(Long id){
//		String sql = "SELECT COUNT(*) FROM FEEDBACK WHERE TUTOR_ID = ?";
//		Integer count = this.jdbcTemplate.queryForObject(sql, new Object[] {id}, Integer.class);
//		return ((count==null) ? 0 : count);
//	}

	/**
	 * Get the total completed requests a tutor has
	 * @param id
	 * @return Total count of completed requests
	 */
	public Integer getTotalCompletedRequests(Long id){
		String sql = "SELECT COUNT(*) FROM USER_REQUEST WHERE TUTOR_ID = ?"; 
		Integer count = this.jdbcTemplate.queryForObject(sql, new Object[] {id}, Integer.class);
		return ((count==null) ? 0 : count);
	}
	
	/**
	 * Get all feedback regarding a user
	 * @param id
	 * @return Map of feedbacks
	 */
	public Map<String,Object> getFeedback(Long id){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM FEEDBACK WHERE TUTOR_ID = ?";
		params.add(id);
		return this.jdbcTemplate.queryForMap(sql,params.toArray());
	}
}
