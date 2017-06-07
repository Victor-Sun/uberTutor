package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.UserSubjectEntity;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
public class TutorSubjectRegisterAction extends PDMSCrudActionSupport<UserSubjectEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	private UserSubjectEntity entity;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void displayCategories() throws Exception{
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

	public void displayCategorySubject() throws Exception{
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

	public void displayUserSubjects() throws Exception{
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

	public void displayUserCategory() throws Exception{
		JsonResult result = new JsonResult();
		try{
			String subjectId = Struts2Utils.getParameter("");
			result.buildSuccessResult(tutorSubjectRegisterService.getUserSubjectCategory(subjectId));
			Struts2Utils.renderJson(result);
		} catch (Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}

	@Override
	public UserSubjectEntity getModel() {
		return entity;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		JsonResult result = new JsonResult();
		try{
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			Long subjectId = Long.parseLong(Struts2Utils.getParameter("subject"));
			System.out.println("User ID: " + userId + "Subject ID: " + subjectId);
			tutorSubjectRegisterService.addTutorSubject(entity, userId, subjectId);
		} catch (Exception e) {
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
		return null;
	}

	@Override
	public String delete() throws Exception {
		try {
			tutorSubjectRegisterService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new UserSubjectEntity();
		}else{
			entity = tutorSubjectRegisterService.get(id);
		}
	}
}
