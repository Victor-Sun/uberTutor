package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.TutorProfileService;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
public class TutorProfileAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	@Autowired
	private TutorProfileService tutorProfileService;
	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	
	/**
	 * Displays the tutor's profile
	 * @throws Exception
	 */
	public void display() throws Exception{
		JsonResult result = new JsonResult();
		try{
			Map<String, String> profileResult = new HashMap<String,String>();
			UserEntity tutor = tutorProfileService.getUser(id);
			int avg = tutorProfileService.getRatingTotal(id)/tutorProfileService.getRatingTotal(id);
			profileResult.put("fullname",tutor.getFullname());
			profileResult.put("rating",avg);
			profileResult.put("totalRequests",tutorProfileService.getTotalCompletedRequests(id));
			profileResult.put("bio",tutor.getBio());
			result.buildSuccessResult(profileResult);
			Struts2Utils.renderJson(result);		
		}catch(Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}
	
	/**
	 * Displays the reviews about the tutor
	 * @throws Exception
	 */
	public void displayReviews() throws Exception{
		JsonResult result = new JsonResult();
		try{
			result.buildSuccessResult(tutorProfileService.getFeedback(id));
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}
}
