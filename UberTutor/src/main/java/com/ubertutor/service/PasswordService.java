package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class PasswordService {
	@Autowired
	private UserDAO userDAO;

	/**
	 * Updates a user's password
	 * @param id User's ID
	 * @param password User's Password
	 */
	public void updatePassword(UserEntity userEntity){
		this.userDAO.save(userEntity);
	}
	
	/**
	 * Get's UserEntity from ID
	 * @param userId
	 * @return UserEntity
	 */
	public UserEntity get(Long userId){
		return this.userDAO.get(userId);
	}
}
