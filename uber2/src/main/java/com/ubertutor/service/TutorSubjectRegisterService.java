package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TutorSubjectRegisterService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
		sql.append(" SELECT SUBJECT_CATEGORY.TITLE AS CATEGORY, SUBJECT.TITLE AS SUBJECT "
				+ "FROM SUBJECT_CATEGORY, SUBJECT, USER_SUBJECT "
				+ "WHERE SUBJECT_CATEGORY.ID = SUBJECT.CATEGORY_ID "
				+ "AND SUBJECT.ID = USER_SUBJECT.SUBJECT_ID AND USER_SUBJECT.USER_ID = ?");
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	public List<Map<String,Object>> getUserCategories(){
		return null;
	}
}
