package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.UserDAO;
import com.ubertutor.entity.UserEntity;

@Service
@Transactional
public class SignUpService {
	
	@Autowired
	private UserDAO userDAO;
	
	/*
	 * Verify if user exists
	 */
	public boolean verifyUserIdExists(String loginUserId) {
		List<UserEntity> result = this.userDAO.findBy("userid", loginUserId);
		return result.size() > 0;
    }
}
