package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.system.entity.UserEntity;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.service.SignupService;

@Namespace("/main")
public class SignupAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String fullName, username, email, password;
	
	@Autowired
	private LoginAction loginAction;
	
	@Autowired
	private SignupService signupService;
	
	@Autowired
	private UserEntity newUser = new UserEntity();
	
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

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void signup() throws Exception{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg;

			// Checks if the user already exists in the database
			if(!signupService.userExists(username)){
				msg = "Username already exists!";
				throw new Exception(msg);
			}
			
			// Checks if the email already exists in the database
			if(!signupService.emailExists(email)){
				msg = "Email already exists!";
				throw new Exception(msg);
			}
			signupService.registerAccount(newUser);
			loginAction.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			loginAction.writeErrorResult(e.getMessage());
		}
	}
}
