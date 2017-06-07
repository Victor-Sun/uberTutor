package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
public class TutorSubjectRegisterAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	
	public void displayCategories(){
		JsonResult result = new JsonResult();
		try{
			result.buildSuccessResult(tutorSubjectRegisterService.getCategoryList());
			Struts2Utils.renderJson(result);	
		} catch (Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}

	public void displayCategorySubject(){
		JsonResult result = new JsonResult();
		try {
			String id = Struts2Utils.getParameter("categoryId");
			result.buildSuccessResult(tutorSubjectRegisterService.getSubjectList(id));
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}

	public void displayUserSubjects(){
		JsonResult result = new JsonResult();
		try{
			String userId = SessionData.getLoginUserId();
			result.buildSuccessResult(tutorSubjectRegisterService.getUserSubjects(userId));
			Struts2Utils.renderJson(result);
		} catch (Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}
	
	public void displayUserCategory(){
		JsonResult result = new JsonResult();
		try{
			String subjectId = Struts2Utils.getParameter("");
			result.buildSuccessResult(tutorSubjectRegisterService.getSubjectCategory(subjectId));
			Struts2Utils.renderJson(result);
		} catch (Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}
}
