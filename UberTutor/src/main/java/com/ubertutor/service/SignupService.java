package com.ubertutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class SignupService {
	@Autowired
	private UserDAO userDAO;

	/**
	 * Returns true if the email exists
	 * @param email
	 * @return true if the email exists in the database
	 */
	public boolean usedEmail(String email){
		List<UserEntity> result = this.userDAO.findBy("email", email);
		return result.size() > 0;
	}

	/**
	 * Saves UserEntity
	 * @param userEntity
	 */
	public void registerAccount(UserEntity userEntity){
		userDAO.save(userEntity);
	}

	/**
	 * Deletes UserEntity
	 * @param userEntity
	 */
	public void delete(UserEntity userEntity){
		userDAO.delete(userEntity);
	}

	/**
	 * Deletes UserEntity
	 * @param userId
	 */
	public void delete(Long userId){
		userDAO.delete(userId);
	}

	/**
	 * Returns UserEntity
	 * @param userId
	 * @return
	 */
	public UserEntity get(Long userId){
		return userDAO.get(userId);
	}
}