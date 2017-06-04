package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.SchoolDAO;
import com.ubertutor.dao.UserDAO;
import com.ubertutor.entity.SchoolEntity;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class ProfileService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolDAO schoolDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserEntity getUser(String username) {
		String hql = "FROM UserEntity WHERE username = ?";
		List<UserEntity> result = this.userDAO.find(hql, username);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new UserEntity();
	}

	public SchoolEntity getSchool(long schoolId){
		String hql = "FROM SchoolEntity WHERE id = ?";
		List<SchoolEntity> result = this.schoolDAO.find(hql, schoolId);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new SchoolEntity();
	}

	public SchoolEntity getSchoolFromName(String name){
		String hql = "FROM SchoolEntity WHERE name = ?";
		List<SchoolEntity> result = this.schoolDAO.find(hql, name);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new SchoolEntity();
	}
	
	public List<Map<String,Object>> getSchoolList(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, NAME FROM SCHOOLS");
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	public String getUserId(String username) {
		return this.getUser(username).getUsername();
	}

	public void updateProfile(String id, String fullname, String email, String mobile, String bio, String schoolId){
		StringBuffer sql = null;
		List<Object> params = null;
		//TODO Find a way to update the school as well
		sql = new StringBuffer();
		sql.append(" UPDATE USERS SET");
		sql.append(" FULLNAME = ?");
		sql.append(",EMAIL = ?");
		sql.append(",MOBILE = ?");
		sql.append(",BIO = ?");
		sql.append(",SCHOOL_ID = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(fullname);
		params.add(email);
		params.add(mobile);
		params.add(bio);
		params.add(schoolId);
		params.add(id);
		params.add(id);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
}
