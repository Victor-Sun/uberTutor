package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.SubjectCategoryDAO;
import com.ubertutor.dao.SubjectDAO;
import com.ubertutor.dao.UserSubjectDAO;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class TutorSubjectRegisterService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserSubjectDAO userSubjectDAO;
	@Autowired
	private SubjectCategoryDAO categoryDAO;
	@Autowired
	private SubjectDAO subjectDAO;
	
	/**
	 * Get all categories
	 * @return List of all categories
	 */
	public List<Map<String,Object>> getCategoryList(){
		String hql = "FROM SubjectCategoryEntity";
		return this.categoryDAO.find(hql);
	}

	/**
	 * Get all subjects according to category
	 * @param categoryId
	 * @return List of subjects according to category
	 */
	public List<Map<String,Object>> getSubjectList(Long categoryId){
		String hql = " FROM SubjectEntity where categoryId = ?";
		return this.subjectDAO.find(hql, categoryId);
	}
	
	/**
	 * Get all of a user's subjects/categories
	 * @param userId
	 * @return List of subjects according to user
	 */
	public List<Map<String,Object>> getUserSubjects(Long userId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM USERS_SUBJECT_CATEGORY WHERE USER_ID = ?");
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	/**
	 * Add subject to the table user_subject
	 * @param entity
	 * @param userId
	 * @param subjectId
	 */
	public void saveTutorSubject(UserSubjectEntity entity){
		userSubjectDAO.save(entity);
	}

	public void editSubject(Long userSubjectId, String description){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE USER_SUBJECT SET DESCRIPTION = ? WHERE ID = ?");
		params.add(description);
		params.add(userSubjectId);
		this.jdbcTemplate.update(sql.toString(),params.toArray());
	}
	
	public boolean subjectExists(Long userId, Long subjectId){
		String hql = "FROM UserSubjectEntity WHERE userId = ? AND subjectId = ?";
		List<UserSubjectEntity> result = this.userSubjectDAO.find(hql, userId, subjectId);
        return result.size() > 0; 
	}
	
	public UserSubjectEntity get(Long id){
		return userSubjectDAO.get(id);
	}
	
	public void delete(UserSubjectEntity entity){
		userSubjectDAO.delete(entity);
	}
	
	public void delete(Long id){
		userSubjectDAO.delete(id);
	}
}
