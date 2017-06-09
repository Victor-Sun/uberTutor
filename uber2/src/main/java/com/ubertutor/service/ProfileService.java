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
	public void registerAsTutor(Long id, String s){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" UPDATE USERS SET IS_TUTOR = ? WHERE ID = ?");
		params.add(s);
		params.add(id);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}

	/**
	 * Function that get's all of a user's info
	 * @param id
	 * @return Map of user's info
	 */
	public Map<String,Object> getUserInfo(Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT USERS.*, SCHOOLS.NAME"
				+ " FROM USERS, SCHOOLS"
				+ " WHERE USERS.SCHOOL_ID = SCHOOLS.ID AND USERS.ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForMap(sql.toString(),params.toArray());
	}

	/**
	 * Function that uses regex to validate an email address
	 * @param email
	 * @return true if the email is valid
	 */
	public boolean isValidEmailAddress(String email) {
		String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}
