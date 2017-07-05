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
	
	public UserRequestEntity get(Long id){
		return userRequestDAO.get(id);
	}
	
	public void delete(UserRequestEntity entity){
		userRequestDAO.delete(entity);
	}
	
	public void delete(Long id){
		userRequestDAO.delete(id);
	}
	
	//Zelin: list subjects based on category user select
	public List<Map<String,Object>> getSubjects(String categoryId){
		String hql = " FROM SubjectEntity WHERE categoryId = ?";
		return this.subjectDAO.find(hql, categoryId);
	}
	
	/**Zelin: save all request to database
	 * @param entity
	 * @param userId
	 * @param subjectId
	 */
	
	public void makeRequest(UserRequestEntity requestEntity){
	    userRequestDAO.save(requestEntity);
	}
	
}
