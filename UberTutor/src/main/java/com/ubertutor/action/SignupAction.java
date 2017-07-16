package com.ubertutor.action;

import java.util.Date;
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
import com.ubertutor.service.ProfileService;
import com.ubertutor.service.SignupService;

@Namespace("/main")
@AllowedMethods("save")
public class SignupAction extends PDMSCrudActionSupport<UserEntity> {
	private static final long serialVersionUID = 1L;
	private String username, email, password, password2;
	private Long id;
	@Autowired
	private SignupService signupService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ProfileService profileService;
	private UserEntity userEntity = new UserEntity();

	/**
	 * Returns id
	 * @return id as a Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns username
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password
	 * @return
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
	 * Returns password2
	 * @return
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * Set password2
	 * @param password2
	 */
	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * Function that registers a user
	 */
	@Override
	public String save() throws Exception{
		try {
			Date date = new Date();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg;
			if(username.isEmpty() || username.contains(" ")){
				msg = "Invalid username, check your username and try again!";
				throw new Exception(msg);
			}
			if(password.isEmpty() || password.contains(" ") || password.length() < 6 || password.length() > 16){
				msg = "Password must be 6-16 characters with no whitespaces!";
				throw new Exception(msg);
			}
			if(loginService.verifyUsername(username)){
				msg = "Username already exists!";
				throw new Exception(msg);
			}
			if(!password.equals(password2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
			if(!profileService.isValidEmailAddress(email)){
				msg = "Invalid email, please check that your email is written correctly, and try again.";
				throw new Exception(msg);
			}
			if(signupService.usedEmail(email)){
				msg = "Email has already been used!";
				throw new Exception(msg);
			}
			userEntity.setIsDisabled("N");
			userEntity.setIsTutor("N");
			userEntity.setIsAdmin("N");
			userEntity.setIsVerified("N");
			userEntity.setCreateBy("System");
			userEntity.setCreateDate(date);
			userEntity.setPassword(EncryptUtil.encrypt(password));
			signupService.registerAccount(userEntity);
			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_USER, userEntity);
			if(OnlineUtils.isUseRedis()){
				if(OnlineUtils.isOnline(username)){
					OnlineUtils.logout(username);
				}
				OnlineUtils.login(Struts2Utils.getSession().getId(), userEntity);
			}
			resultMap.put("username", userEntity.getUsername());
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		userEntity = (id != null) ? signupService.get(id) : new UserEntity();
	}

	public UserEntity getModel() {
		return userEntity;
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
	public String delete() throws Exception {
		try {
			signupService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}