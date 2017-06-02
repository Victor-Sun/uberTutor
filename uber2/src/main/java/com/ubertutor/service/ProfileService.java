package com.ubertutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	/**
	 * 
	 * @param username
	 * @return User's Username
	 */
	public String getUserId(String username) {
		return this.getUser(username).getUsername();
    }
}
