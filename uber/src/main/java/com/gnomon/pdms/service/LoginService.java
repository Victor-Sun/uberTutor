package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.pdms.dao.UserDAO;

@Service
@Transactional
public class LoginService {

	@Autowired
	private UserDAO userDAO;
	
	/*
	 * 验证用户ID是否存在
	 */
	public boolean verifyUserId(String loginUserId) {
		List<UserEntity> result = this.userDAO.findBy("userid", loginUserId);
        return result.size() > 0;
    }
	
	/*
	 * 验证用户密码是否正确
	 */
	public boolean verifyUserPassword(
			String loginUserId, String loginPassword) {
		String hql = "FROM UserEntity WHERE userid = ? AND password = ?";
		List<UserEntity> result =
				this.userDAO.find(hql, loginUserId, loginPassword);
        return result.size() > 0;
    }
	
	/*
	 * 用户信息取得
	 */
	public UserEntity getUser(String loginUserId) {
		String hql = "FROM UserEntity WHERE userid = ?";
		List<UserEntity> result =
				this.userDAO.find(hql, loginUserId);
		if (result.size() > 0) {
			return result.get(0);
		}
		return new UserEntity();
    }
	
	/**
	 * 保存用户登录日志
	 * @param userId
	 */
	public void saveLoginLog(String userId){
		String sql = "INSERT INTO SYS_LOGIN_LOG(ID,USER_ID,LOGIN_DATE) VALUES(SYS_LOGIN_LOG_SEQ.NEXTVAL,?,SYSDATE)";
		userDAO.executeSQL(sql, userId);
	}
	
	/*
	 * 用户Id取得
	 */
	public String getUserId(String loginUserId) {
		return this.getUser(loginUserId).getUserid();
    }
}

