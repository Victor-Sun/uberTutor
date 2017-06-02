package com.ubertutor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
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
			JsonResult result = new JsonResult();
			Map<String, String> profileResult = new HashMap<String,String>();
			profileResult.put("fullname",user.getFullname());
			profileResult.put("email",user.getEmail());
			profileResult.put("mobile",user.getMobile());
			profileResult.put("bio",user.getBio());
			profileResult.put("school",profileService.getSchool((long)2).getName());
			System.out.println(profileResult.toString());
			Struts2Utils.renderJson(profileResult);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
