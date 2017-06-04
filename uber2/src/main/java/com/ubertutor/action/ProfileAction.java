package com.ubertutor.action;

import java.util.HashMap;
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
    
    private UserEntity user = SessionData.getLoginUser();
    
    public void display() throws Exception{
        try{
            JsonResult result = new JsonResult();
            Map<String, String> profileResult = new HashMap<String,String>();
            profileResult.put("fullname",user.getFullname());
            profileResult.put("email",user.getEmail());
            profileResult.put("mobile",user.getMobile());
            profileResult.put("bio",user.getBio());
            profileResult.put("school",profileService.getSchool((long)2).getName());
            result.buildSuccessResult(profileResult);
            Struts2Utils.renderJson(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update() throws Exception{
    	try{
    		//TODO Checks + Verification
    		String fullname, email, mobile, bio, school;
    		fullname = Struts2Utils.getRequest().getParameter("fullname");
    		email = Struts2Utils.getRequest().getParameter("email");
    		mobile = Struts2Utils.getRequest().getParameter("mobile");
    		bio = Struts2Utils.getRequest().getParameter("bio");
    		profileService.updateProfile(user.getId().toString(), fullname, email, mobile, bio);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
