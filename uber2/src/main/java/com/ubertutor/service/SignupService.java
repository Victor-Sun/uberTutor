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
	public boolean verifyUserExists(String loginUsername) {
		return loginService.verifyUserId(loginUsername);
    }
	
	/*
	 * Checks if the email is already registered in the db
	 */
	public boolean verifyEmailExists(String email){
		List<UserEntity> result = this.userDAO.findBy("email", email);
        return result.size() > 0;
	}
	
	/*
	 * Registers account into the DB
	 */
	public void registerAccount(String fullname, String username, String email, String password){
		//TODO Registration
		//Use insert statement to write information into db? Is there a better solution?
	}
}
