package com.ubertutor.service;

import java.util.ArrayList;
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
	
	public List<Map<String,Object>> getCategoryList(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, TITLE FROM SUBJECT_CATEGORY");
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	public List<Map<String,Object>> getSubjectList(String categoryId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, TITLE FROM SUBJECT WHERE CATEGORY_ID = ?");
		params.add(categoryId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	public List<Map<String,Object>> getUserSubjects(String userId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUBJECT_ID, SUBJECT_TITLE, CATEGORY_ID, CATEGORY_TITLE FROM USERS_SUBJECT_CATEGORY WHERE USER_ID = ?");
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	public void addTutorSubject(UserSubjectEntity entity, Long userId, Long subjectId){
		entity.setUserid(userId);
		entity.setSubjectid(subjectId);
		userSubjectDao.save(entity);
	}
	
	public void removeSubject(Long userId, Long subjectId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM USER_SUBJECT WHERE USER_ID = ? AND SUBJECT_ID = ?");
		params.add(userId);
		params.add(subjectId);
		this.jdbcTemplate.update(sql.toString(),params.toArray());
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
