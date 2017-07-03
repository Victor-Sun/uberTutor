package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class PasswordService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Updates a user's password
	 * @param id User's ID
	 * @param password User's Password
	 */
	public void updatePassword(UserEntity entity){
		this.userDAO.save(entity);
	}
	
	/**
	 * Get's UserEntity from ID
	 * @param id
	 * @return UserEntity
	 */
	public UserEntity get(Long id){
		return this.userDAO.get(id);
	}
}
