package com.ubertutor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gnomon.common.system.entity.UserEntity;
import org.apache.commons.validator.routines.EmailValidator;

import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class SignupService {
	@Autowired
	private UserDAO userDAO;
	
	private EmailValidator emailValidator;
	/**
	 * Check for if the email already exists
	 * @param email
	 * @return true if the email exists in the database
	 */
	public boolean usedEmail(String email){
		List<UserEntity> result = this.userDAO.findBy("email", email);
        return result.size() > 0;
	}
	
	/**
	 * Check for if an email is valid
	 * @param email
	 * @return false if email is invalid
	 */
	public boolean validEmail(String email){
//		String r = "";
//		Pattern p = Pattern.compile(r);
//		Matcher m = p.matcher(email);
//		if(!m.find()){
//			return false;
//		}
//		return true;
		return emailValidator.isValid(email);
	}
	
	/**
	 * Checks if both passwords are the same
	 * @param password
	 * @param password2
	 * @return true if both passwords are the same
	 */
	public boolean passwordConfirmation(String password, String password2){
		if(!password.equals(password2)){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the password is valid
	 * @param password
	 * @return true if the password is valid 
	 */
	public boolean validPassword(String password){
		//TODO Password validation: Check if the password is specific number of characters, 
//		if the password has any illegal characters
//		if(){
//			return false;
//		}
		return true;
	}

	/**
	 * Saves the data to the entity
	 * @param entity
	 */
	public void registerAccount(UserEntity entity){
		userDAO.save(entity);
	}
	
	public UserEntity get(Long id){
		return userDAO.get(id);
	}
	
	public void delete(Long id){
		userDAO.delete(id);
	}
}