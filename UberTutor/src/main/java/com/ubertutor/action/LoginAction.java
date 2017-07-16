package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.utils.EncryptUtil;
import com.gnomon.common.utils.OnlineUtils;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.LoginService;

@Namespace("/main")
@AllowedMethods("login")
public class LoginAction extends PDMSCrudActionSupport<UserEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private LoginService loginService;
	private String username, password;

	/**
	 * Returns username
	 * @return Username as String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns password
	 * @return password as String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Login function
	 * @throws Exception
	 */
	public void login() throws Exception {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg; 
			if(username.isEmpty() || password.isEmpty()){
				msg = "Username and password cannot be empty!";
				throw new Exception(msg);
			}
			if (!loginService.verifyUsername(username)) {
				msg = "User does not exist";
				throw new Exception(msg);
			}
			UserEntity userEntity = this.loginService.getUser(username);
			username = userEntity.getUsername();
			if (!loginService.verifyUserPassword(username, EncryptUtil.encrypt(password))) {
				msg = "Username and password combination is incorrect!";
				throw new Exception(msg);
			}
			if(userEntity.getIsDisabled().equals("Y")){
				msg = "User has been disabled!";
				throw new Exception(msg);
			}
			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_USER, userEntity);
			if(OnlineUtils.isUseRedis()){
				if(OnlineUtils.isOnline(username)){
					OnlineUtils.logout(username);
				}
				OnlineUtils.login(Struts2Utils.getSession().getId(), userEntity);
			}
			resultMap.put("userName", username);
			writeSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * Logout function
	 */
	public void logout() {
		try {
			Struts2Utils.getRequest().getSession().invalidate();
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	@Override
	public UserEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}
}