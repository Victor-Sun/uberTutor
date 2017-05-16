package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.UserEntity;
import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class ProfileManagement {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserEntity user;
	
	public void update(UserEntity entity){
		//TODO update a user's profile
	}
	
	public UserEntity retrieveUser(String username){
		user = loginService.getUser(username);
		return user;
	}
}
