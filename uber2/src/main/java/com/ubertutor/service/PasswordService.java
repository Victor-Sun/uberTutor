package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.EncryptUtil;
import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class PasswordService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Updates a user's password
	 * @param id User's ID
	 * @param password User's Password
	 */
	public void updatePassword(String id, String password){
		String hql = " UPDATE USERS SET PASSWORD = ?, UPDATE_BY = ?, UPDATE_DATE = SYSDATE WHERE ID = ?";
		this.userDAO.executeSQL(hql, EncryptUtil.encrypt(password), id, id);
	}
}
