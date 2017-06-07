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

	/**
	 * Get user ID
	 * @return id as Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set user ID
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Displays all categories
	 * @throws Exception
	 */
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

	/**
	 * Displays subjects based on category
	 * @throws Exception
	 */
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

	/**
	 * Displays a user's subjects
	 * @throws Exception
	 */
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
	
	public void removeSubject() throws Exception{
		JsonResult result = new JsonResult();
		try{
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			Long subjectId = Long.parseLong(Struts2Utils.getParameter("SUBJECT_ID"));
			tutorSubjectRegisterService.removeSubject(userId, subjectId);
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
		return null;
	}

	@Override
	public String input() throws Exception {
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
