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
    private String fullname = user.getFullname(), 
    		email = user.getEmail(),
    		mobile = user.getMobile(),
    		bio = user.getBio(),
    		school = profileService.getSchool((long)2).getName();
    
    public void display() throws Exception{
        try{
            JsonResult result = new JsonResult();
            Map<String, String> profileResult = new HashMap<String,String>();
            profileResult.put("fullname",fullname);
            profileResult.put("email",email);
            profileResult.put("mobile",mobile);
            profileResult.put("bio",bio);
            profileResult.put("school",school);
            result.buildSuccessResult(profileResult);
            Struts2Utils.renderJson(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update() throws Exception{
    	try{
    		//TODO Checks + Verification
    		profileService.updateProfile(fullname, email, mobile, bio);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
