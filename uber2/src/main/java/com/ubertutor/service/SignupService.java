package com.ubertutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gnomon.common.system.entity.UserEntity;
import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class SignupService {

	@Autowired
	private UserDAO userDAO;
	private LoginService loginService; 
	
	/*
	 * Checks if the username has already been used
	 */
	public boolean userExists(String loginUsername) {
		return loginService.verifyUserId(loginUsername);
    }
	
	/*
	 * Checks if the email is already registered in the database
	 */
	public boolean emailExists(String email){
		List<UserEntity> result = this.userDAO.findBy("email", email);
        return result.size() > 0;
	}
	
	/*
	 * Registers account into the DB
	 */
	public void registerAccount(UserEntity entity){
		//TODO Registration
		userDAO.save(entity);;
	}
	
	public UserEntity get(String id){
		return userDAO.get(id);
	}
	
	public void delete(String id){
		userDAO.delete(id);
	}
}
