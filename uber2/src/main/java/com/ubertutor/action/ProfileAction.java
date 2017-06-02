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
	private ProfileService profileService;
	
	public void display() throws Exception{
		try{
			UserEntity user = SessionData.getLoginUser();
			System.out.println(user.getFullname());
			System.out.println(user.getEmail());
			System.out.println(user.getMobile());
			System.out.println(user.getBio());
//			System.out.println(profileService.getSchool((long)2).getName());
			System.out.println(user.getSchools());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
