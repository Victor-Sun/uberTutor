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
	 * Checks if the password is valid
	 * @param password
	 * @return true if the password is valid 
	 */
//	public boolean validPassword(String password){
		//TODO Password validation: Check if the password is specific number of characters, 
//		if the password has any illegal characters
//		if(){
//			return false;
//		}
//		return true;
//	}
		
	/**
	 * Saves UserEntity
	 * @param entity
	 */
	public void registerAccount(UserEntity entity){
		userDAO.save(entity);
	}
	
	/**
	 * Deletes UserEntity
	 * @param entity
	 */
	public void delete(UserEntity entity){
		userDAO.delete(entity);
	}
	
	/**
	 * Deletes UserEntity
	 * @param id
	 */
	public void delete(Long id){
		userDAO.delete(id);
	}
	
	/**
	 * Returns UserEntity
	 * @param id
	 * @return
	 */
	public UserEntity get(Long id){
		return userDAO.get(id);
	}
}