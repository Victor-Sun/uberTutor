package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
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
	 * Get a list of schools from DAO
	 * @return List of schools
	 */
	public List<Map<String, Object>> getSchoolList(){
		String sql = "SELECT * FROM SCHOOLS";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * Updates a user's profile
	 * @param entity
	 */
	public void updateProfile(UserEntity entity){
		this.userDAO.save(entity);
	}

	/**
	 * Function that get's all of a user's info minus school
	 * @param userId
	 * @return Map of user's info
	 */
	public List<Map<String, Object>> getUserInfo(Long userId){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM USERS WHERE ID = ?";
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql,params.toArray());
	}

	/**
	 * Function that get's all of a user's profile information
	 * @param id
	 * @return Map of user's info
	 */
	public Map<String, Object> getAllUserInfo(Long id){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT USERS.*, SCHOOLS.NAME FROM USERS, SCHOOLS WHERE USERS.SCHOOL_ID = SCHOOLS.ID AND USERS.ID = ?";
		params.add(id);
		return this.jdbcTemplate.queryForMap(sql.toString(),params.toArray());
	}
	
	/**
	 * Function that checks if the user has a school
	 * @param userId
	 * @return size of query result
	 */
	public List<Map<String, Object>> hasSchool(Long userId){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT SCHOOLS.NAME FROM USERS, SCHOOLS WHERE USERS.SCHOOL_ID = SCHOOLS.ID AND USERS.ID = ?";
		params.add(userId);
		return this.jdbcTemplate.queryForList(sql,params.toArray());
	}

	
	/**
	 * Function that uses regex to validate an email address
	 * @param email
	 * @return true if the email is valid
	 */
	public boolean isValidEmailAddress(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}
}
