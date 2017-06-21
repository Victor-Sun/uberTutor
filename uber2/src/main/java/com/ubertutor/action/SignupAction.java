package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomontech.pdms.redis.OnlineUtils;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.LoginService;
import com.ubertutor.service.ProfileService;
import com.ubertutor.service.SignupService;

@Namespace("/main")
public class SignupAction extends PDMSCrudActionSupport<UserEntity> {
	private static final long serialVersionUID = 1L;
	private String fullName, username, email, password;
	private Long id;
	@Autowired
	private SignupService signupService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ProfileService profileService;
	private UserEntity entity = new UserEntity();

	/**
	 * 
	 * @return fullname as a String
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Set fullname
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 
	 * @return username as a String
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
	 * 
	 * @return email as a String
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
	 * 
	 * @return password as a String
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
	 * Get a user's ID
	 * @return id as a Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set user id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Function that registers a user
	 */
	@Override
	public String save() throws Exception{
		try {
			Date date = new Date();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg, p1 = "", p2 = "", un;
			p1 = Struts2Utils.getRequest().getParameter("password");
			p2 = Struts2Utils.getRequest().getParameter("password2");
			un = Struts2Utils.getRequest().getParameter("username");
			if(un.contains(" ")){
				msg = "Invalid username, check your username and try again!";
				throw new Exception(msg);
			}
			if(loginService.verifyUserId(username)){
				msg = "Username already exists!";
				throw new Exception(msg);
			}
			if(!p1.equals(p2)){
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
			entity.setIsDisabled("N");
			entity.setIsTutor("N");
			entity.setIsAdmin("N");
			entity.setIsVerified("N");
			entity.setCreateBy("System");
			entity.setCreateDate(date);
			entity.setPassword(EncryptUtil.encrypt(p1));
			signupService.registerAccount(entity);
			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_USER, entity);
			if(OnlineUtils.isUseRedis()){
				if(OnlineUtils.isOnline(username)){
					OnlineUtils.logout(username);
				}
				OnlineUtils.login(Struts2Utils.getSession().getId(), entity);
			}
			resultMap.put("username", entity.getUsername());
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity = (id != null) ? signupService.get(id) : new UserEntity();
	}

	@Override
	public UserEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
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