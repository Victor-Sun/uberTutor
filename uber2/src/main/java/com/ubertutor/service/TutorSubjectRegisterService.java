package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.web.struts2.Struts2Utils;

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
		sql.append(" SELECT SUBJECT.ID, SUBJECT.TITLE "
				+ "FROM USER_SUBJECT, SUBJECT "
				+ "WHERE USER_SUBJECT.SUBJECT_ID = SUBJECT.ID AND USER_SUBJECT.USER_ID = ?");
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	public List<Map<String,Object>> getSubjectCategory(String subjectId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUBJECT_CATEGORY.ID, SUBJECT_CATEGORY.TITLE "
				+ "FROM SUBJECT_CATEGORY, SUBJECT "
				+ "WHERE SUBJECT.CATEGORY_ID = SUBJECT_CATEGORY.ID AND SUBJECT.ID = ?");
		params.add(subjectId);
		return this.jdbcTemplate.queryForList(sql.toString());
	}
}
