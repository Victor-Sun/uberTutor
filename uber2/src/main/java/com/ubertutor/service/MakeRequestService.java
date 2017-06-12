package com.ubertutor.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.dao.UserSubjectDAO;
import com.ubertutor.entity.SubjectCategoryEntity;
import com.ubertutor.entity.SubjectEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class MakeRequestService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserRequestDAO userRequestDao;
	
	//Zelin
	@Autowired
	private UserSubjectDAO userSubjectDao;
	
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
	
	//Zelin: list all subjects based on category user select
	public List<SubjectEntity> showSubjectList(Long categoryId){
		
		String hql="FROM SUBJECT WHERE id = ?";
		List<SubjectEntity> result=this.userSubjectDao.find(hql,categoryId);
		
		return result;
	}
	
}
