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

	/**
	 * Get UserEntity by username
	 * @param username
	 * @return UserEntity object
	 */
	public UserEntity getUser(String username) {
		String hql = "FROM UserEntity WHERE username = ?";
		List<UserEntity> result = this.userDAO.find(hql, username);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new UserEntity();
	}

	/**
	 * Get school entity by school's id
	 * @param schoolId
	 * @return School Entity Object
	 */
	public SchoolEntity getSchool(long schoolId){
		String hql = "FROM SchoolEntity WHERE id = ?";
		List<SchoolEntity> result = this.schoolDAO.find(hql, schoolId);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new SchoolEntity();
	}

	/**
	 * Get school entity by school's name
	 * @param name
	 * @return School Entity Object
	 */
	public SchoolEntity getSchoolByName(String name){
		String hql = "FROM SchoolEntity WHERE name = ?";
		List<SchoolEntity> result = this.schoolDAO.find(hql, name);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new SchoolEntity();
	}
	
	/**
	 * Get a list of schools by running a query
	 * @return List of schools
	 */
	public List<Map<String,Object>> getSchoolList(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, NAME FROM SCHOOLS");
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	
	/**
	 * Get a user's ID by username
	 * @param username
	 * @return User id as a string
	 */
	public String getUserId(String username) {
		return this.getUser(username).getUsername();
	}
	
	/**
	 * Function to run a query to update a user's profile
	 * @param id
	 * @param fullname
	 * @param email
	 * @param mobile
	 * @param bio
	 * @param schoolId
	 */
	public void updateProfile(String id, String fullname, String email, String mobile, String bio, String schoolId){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
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
		params.add(fullname);
		params.add(email);
		params.add(mobile);
		params.add(bio);
		params.add(schoolId);
		params.add(id);
		params.add(id);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * Function that changes the isTutor flag in db
	 * @param id
	 */
	public void registerAsTutor(Long id){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		UserEntity user = userDAO.get(id);
		sql.append(" UPDATE USERS SET IS_TUTOR = ? WHERE ID = ?");
		String status = (!user.getIsTutor().equals("Y"))?"Y":"N";
		params.add(status);
		params.add(id);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
}
