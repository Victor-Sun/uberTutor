package com.ubertutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.SchoolDAO;
import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class ProfileService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolDAO schoolDAO;
	
	public UserEntity getUser(String loginusername) {
		String hql = "FROM UserEntity WHERE username = ?";
		List<UserEntity> result = this.userDAO.find(hql, loginusername);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new UserEntity();
    }
	
	/**
	 * 
	 * @param loginUsername
	 * @return User's Username
	 */
	public String getUserId(String loginUsername) {
		return this.getUser(loginUsername).getUsername();
    }
}
