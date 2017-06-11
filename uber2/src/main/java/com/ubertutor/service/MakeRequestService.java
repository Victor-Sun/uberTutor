package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.UserRequestEntity;

@Service
@Transactional
public class MakeRequestService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserRequestDAO userRequestDao;
	
	public void makeRequest(UserRequestEntity entity){
		userRequestDao.save(entity);
	}
	
	public UserRequestEntity get(Long id){
		return userRequestDao.get(id);
	}
	
	public void delete(UserRequestEntity entity){
		userRequestDao.delete(entity);
	}
	
	public void delete(Long id){
		userRequestDao.delete(id);
	}
}
