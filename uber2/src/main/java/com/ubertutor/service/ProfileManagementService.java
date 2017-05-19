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
	private UserEntity user;
	
	@Autowired
	private UserSubjectEntity userSubject;
	
	public UserEntity get(String id){
		return userDAO.get(id);
	}
	
	public void update(){
		userDAO.save(user);
	}
	
	public void updateSubjects(){
		userDAO.save(userSubject);
	}
	
	public String getSubject(){
		return userSubject.getSubjectid();
	}
}
