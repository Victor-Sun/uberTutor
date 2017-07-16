package com.ubertutor.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.SubjectDAO;
import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.entity.UserRequestEntity;

@Service
@Transactional
public class MakeRequestService {
	@Autowired
	private UserRequestDAO userRequestDAO;
	@Autowired 
	private SubjectDAO subjectDAO;
	
	/**
	 * Returns UserRequestEntity from id
	 * @param requestId
	 * @return UserRequestEntity
	 */
	public UserRequestEntity get(Long requestId){
		return userRequestDAO.get(requestId);
	}
	
	/**
	 * Deletes UserRequestEntity
	 * @param entity
	 */
	public void delete(UserRequestEntity entity){
		userRequestDAO.delete(entity);
	}
	
	/**
	 * Deletes UserRequestEntity
	 * @param requestId
	 */
	public void delete(Long requestId){
		userRequestDAO.delete(requestId);
	}
	
	/**
	 * Returns list of all subjects
	 * @param categoryId
	 * @return List of all subjects
	 */
	//Zelin: list subjects based on category user select
	public List<Map<String,Object>> getSubjects(Long categoryId){
		String hql = " FROM SubjectEntity WHERE categoryId = ?";
		return this.subjectDAO.find(hql, categoryId);
	}
	
	/**Zelin: save all request to database
	 * @param entity
	 * @param userId
	 * @param subjectId
	 */
	public void save(UserRequestEntity requestEntity){
	    userRequestDAO.save(requestEntity);
	}
	
}
