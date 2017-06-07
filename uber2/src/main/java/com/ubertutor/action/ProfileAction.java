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
    	JsonResult result = new JsonResult();
        try{
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
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
        }
    }
    
    public void update() throws Exception{
    	JsonResult result = new JsonResult();
    	try{
    		String fullname, email, mobile, bio, school, schoolid, msg, mobileNo = "";
    		fullname = Struts2Utils.getRequest().getParameter("fullname");
    		email = Struts2Utils.getRequest().getParameter("email");
    		mobile = Struts2Utils.getRequest().getParameter("mobile");
    		bio = Struts2Utils.getRequest().getParameter("bio");
    		school = Struts2Utils.getRequest().getParameter("school");
    		schoolEntity = profileService.getSchoolByName(school);
    		schoolid = schoolEntity.getId().toString();
    		String[] temp = mobile.split("[-.()]");
    		for(int i = 0; i < temp.length; i++){
    			mobileNo += temp[i];
    		}
    		if(!mobileNo.matches("\\d{10}")){
    			msg = "Invalid phone number";
    			throw new Exception(msg);
    		}
    		profileService.updateProfile(user.getId().toString(), fullname, email, mobileNo, bio, schoolid);
    	}catch(Exception e){
    		e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
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
    
    public void registerAsTutor(){
    	profileService.registerAsTutor(user.getId());
    }
    
}
