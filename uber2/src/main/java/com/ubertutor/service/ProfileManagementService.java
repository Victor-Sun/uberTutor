package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.UserEntity;
import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class ProfileManagementService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserEntity user;
	@Autowired
	private UserSubjectEntity userSubject;

	public void updateProfile(){
		try{
			// TODO update a user's profile, what checks are needed
			
			userDAO.save(user);	
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	public UserEntity retrieveUser(String username){
		user = loginService.getUser(username);
		return user;
	}
}
