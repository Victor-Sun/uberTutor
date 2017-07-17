package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.UserProfileService;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
@AllowedMethods({"display","getTutorSubjects","getTutorFeedbacks"})
public class UserProfileAction extends ActionSupport{
    private static final long serialVersionUID = 1L;
    @Autowired
    private UserProfileService sessionProfileService;
    @Autowired
    private TutorSubjectRegisterService tutorSubjectRegisterService;
    private UserEntity userEntity;
    
	/**
	 * Sends Json to display the tutor's info
	 * @throws Exception
	 */
	public void display() throws Exception{
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("tutorName", sessionProfileService.getUser(userEntity.getId()).getId());
//			int ratingAvg = sessionProfileService.getRatingTotal(userEntity.getId()) / sessionProfileService.getRatingCount(userEntity.getId());
//			result.put("tutorRating", ratingAvg);
			result.put("tutorCompletedRequests", sessionProfileService.getTotalCompletedRequests(userEntity.getId())); 
			result.put("tutorBio", userEntity.getBio());
			this.writeSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Sends Json to display a tutor's subjects
	 * @throws Exception
	 */
	public void getTutorSubjects() throws Exception{
		try{
			UserEntity userEntity = sessionProfileService.getUser(Long.parseLong(Struts2Utils.getRequest().getParameter("tutorId")));
//			this.writeSuccessResult(tutorSubjectRegisterService.getUserSubjects(userEntity.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Sends Json to display a tutor's feedbacks
	 * @throws Exception
	 */
	public void getTutorFeedbacks() throws Exception{
		try {
			UserEntity userEntity = sessionProfileService.getUser(Long.parseLong(Struts2Utils.getRequest().getParameter("tutorId")));
			this.writeSuccessResult(sessionProfileService.getFeedback(userEntity.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}
	
	protected void writeSuccessResult(Object data) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
		try {
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (null != data) {
			resultMap.put("data", data);
		}
		try {
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
