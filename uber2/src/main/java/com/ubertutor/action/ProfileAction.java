package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;

@Namespace("/main")
public class ProfileAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProfileService profile;
	
	private UserEntity userEntity;
	private String user = SessionData.getUserId();
	
	public void display(){
				
		System.out.println(userEntity.getUsername() + " " + user);
	}
	
	
}
