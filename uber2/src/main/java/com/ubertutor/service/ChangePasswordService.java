package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.EncryptUtil;
import com.ubertutor.dao.UserDAO;

@Service
@Transactional
public class ChangePasswordService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void updatePassword(String id, String password){
		StringBuffer sql = null;
		List<Object> params = null;

		sql = new StringBuffer();
		sql.append(" UPDATE USERS SET");
		sql.append(" PASSWORD = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(EncryptUtil.encrypt(password));
		params.add(id);
		params.add(id);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}

}
