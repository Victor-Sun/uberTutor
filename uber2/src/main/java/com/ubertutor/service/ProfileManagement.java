package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class ProfileManagement {
	
	@Autowired
	private UserDAO userDAO;
	
	public void updateProfile(String username){
		//TODO update a user's profile
	}
}