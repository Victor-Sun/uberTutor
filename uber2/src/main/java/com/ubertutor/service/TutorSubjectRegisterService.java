package com.ubertutor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserSubjectDAO;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class TutorSubjectRegisterService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserSubjectDAO userSubjectDao;
	
	/**
	 * Get all categories
	 * @return List of all categories
	 */
	public List<Map<String,Object>> getCategoryList(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, TITLE FROM SUBJECT_CATEGORY WHERE ID <> 1");
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * Get all subjects according to category
	 * @param categoryId
	 * @return List of subjects according to category
	 */
	public List<Map<String,Object>> getSubjectList(String categoryId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, TITLE FROM SUBJECT WHERE CATEGORY_ID = ?");
		params.add(categoryId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	/**
	 * Get all of a user's subjects/categories
	 * @param userId
	 * @return List of subjects according to user
	 */
	public List<Map<String,Object>> getUserSubjects(Long userId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUBJECT_ID, SUBJECT_TITLE, CATEGORY_ID, CATEGORY_TITLE FROM USERS_SUBJECT_CATEGORY WHERE USER_ID = ?");
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	/**
	 * Add subject to the table user_subject
	 * @param entity
	 * @param userId
	 * @param subjectId
	 */
	public void addTutorSubject(UserSubjectEntity entity, Long userId, Long subjectId){
		Date date = new Date();
		entity.setUserid(userId);
		entity.setSubjectid(subjectId);
		entity.setAdddate(date);
		userSubjectDao.save(entity);
	}
	
	/**
	 * Removes subject from user_subject
	 * @param userId
	 * @param subjectId
	 */
	public void removeSubject(Long userId, Long subjectId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM USER_SUBJECT WHERE USER_ID = ? AND SUBJECT_ID = ?");
		params.add(userId);
		params.add(subjectId);
		this.jdbcTemplate.update(sql.toString(),params.toArray());
	}
	
	public boolean subjectExists(Long userId, Long subjectId){
		String hql = "FROM UserSubjectEntity WHERE userid = ? AND subjectid = ?";
		List<UserSubjectEntity> result = this.userSubjectDao.find(hql, userId, subjectId);
        return result.size() > 0; 
	}
	
	public UserSubjectEntity get(Long id){
		return userSubjectDao.get(id);
	}
	
	public void delete(UserSubjectEntity entity){
		userSubjectDao.delete(entity);
	}
	
	public void delete(Long id){
		userSubjectDao.delete(id);
	}
}
