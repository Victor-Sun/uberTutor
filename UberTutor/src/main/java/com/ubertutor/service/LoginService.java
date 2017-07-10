package com.ubertutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class LoginService {
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * Verifies a user's username exists in the DB
	 * @param loginusername
	 * @return True if username exists
	 */
	public boolean verifyUsername(String loginusername) {
		List<UserEntity> result = this.userDAO.findBy("username", loginusername);
        return result.size() > 0;    
    }
	
	/**
	 * Verifies that a user's username and password match
	 * @param loginusername
	 * @param loginPassword
	 * @return True if the combation matches 
	 */
	public boolean verifyUserPassword(String loginusername, String loginPassword) {
		String hql = "FROM UserEntity WHERE username = ? AND password = ?";
		List<UserEntity> result = this.userDAO.find(hql, loginusername, loginPassword);
        return result.size() > 0;
    }

	/**
	 * Get the user based on username
	 * @param loginusername
	 * @return UserEntity object
	 */
	public UserEntity getUser(String loginusername) {
		String hql = "FROM UserEntity WHERE username = ?";
		List<UserEntity> result = this.userDAO.find(hql, loginusername);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new UserEntity();
    }
	
	/**
	 * Get user id by username
	 * @param loginUsername
	 * @return User's ID as a String
	 */
	public String getUserId(String loginUsername) {
		return this.getUser(loginUsername).getUsername();
    }
}

