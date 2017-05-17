package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.service.LoginService;
import com.ubertutor.service.SignupService;

@Namespace("/main")
public class SignupAction extends PDMSCrudActionSupport<UserEntity> {

	private static final long serialVersionUID = 1L;

	private String fullName, username, email, password;

	@Autowired
	private SignupService signupService;
	
	@Autowired
	private LoginService loginService; 
	
	private UserEntity entity;

	private String id;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String save() throws Exception{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg;

			// Different way to write data from fields to database 
			// String username = Struts2Utils.getRequest().getPara	meter("username");
			// entity.setUsername(username);
			
			// Checks if the user already exists in the database
			if(loginService.verifyUserId(username)){
				msg = "Username already exists!";
				throw new Exception(msg);
			}
			// Checks if the email already exists in the database
			if(signupService.emailExists(email)){
				msg = "Email already exists!";
				throw new Exception(msg);
			}
			entity.setPassword(EncryptUtil.encrypt(entity.getPassword()));
			signupService.registerAccount(entity);
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new UserEntity();

		}else{
			entity = signupService.get(id);
		}
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
