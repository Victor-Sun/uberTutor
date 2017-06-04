package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.SchoolEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;

@Namespace("/main")
public class ProfileAction extends ActionSupport{
    private static final long serialVersionUID = 1L;

    @Autowired
    private ProfileService profileService;
    private SchoolEntity schoolEntity;
    private UserEntity user = SessionData.getLoginUser();
    
    public void display() throws Exception{
        try{
            JsonResult result = new JsonResult();
            Map<String, String> profileResult = new HashMap<String,String>();
            SchoolEntity school = profileService.getSchool(Long.parseLong(user.getSchoolId()));
            profileResult.put("fullname",user.getFullname());
            profileResult.put("email",user.getEmail());
            profileResult.put("mobile",user.getMobile());
            profileResult.put("bio",user.getBio());
            profileResult.put("school",school.getName());
            result.buildSuccessResult(profileResult);
            Struts2Utils.renderJson(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update() throws Exception{
    	try{
    		//TODO Checks + Verification
    		String fullname, email, mobile, bio, school, schoolid;
    		fullname = Struts2Utils.getRequest().getParameter("fullname");
    		email = Struts2Utils.getRequest().getParameter("email");
    		mobile = Struts2Utils.getRequest().getParameter("mobile");
    		bio = Struts2Utils.getRequest().getParameter("bio");
    		school = Struts2Utils.getRequest().getParameter("school");
    		schoolEntity = profileService.getSchoolFromName(school);
    		schoolid = schoolEntity.getId().toString();
    		profileService.updateProfile(user.getId().toString(), fullname, email, mobile, bio, schoolid);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void displaySchool(){
    	try{
    		JsonResult result = new JsonResult();
    		result.buildSuccessResult(profileService.getSchoolList());
    		Struts2Utils.renderJson(result);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
