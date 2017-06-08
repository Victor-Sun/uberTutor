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

	/**
	 * Sends Json to front end to display a user's profile
	 * @throws Exception
	 */
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
			if(user.getIsTutor().equals("Y")){
				profileResult.put("isTutor","true");
			}else{
				profileResult.put("isTutor","false");
			}
			result.buildSuccessResult(profileResult);
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}

	/**
	 * Updates a user's profile
	 * @throws Exception
	 */
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

	/**
	 * Separate function to display school
	 */
	public void displaySchool(){
		try{
			JsonResult result = new JsonResult();
			result.buildSuccessResult(profileService.getSchoolList());
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function to register a user as a tutor
	 */
	public void registerAsTutor(){
		profileService.registerAsTutor(user.getId());
	}

	/**
	 * Get user's isTutor status
	 */
	public void getTutorStatus(){
		try{
			JsonResult result = new JsonResult();
			if(user.getIsTutor().equals("Y")){
				result.buildSuccessResult("true");
			} else {
				result.buildSuccessResult("false");
			}
			Struts2Utils.renderJson(result);	
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
