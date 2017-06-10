package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

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
	
	/**
	 * Removes a user's subject
	 * @throws Exception
	 */
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
		String msg = "";
		try{
			String userId = SessionData.getLoginUserId();
			String subjectId = Struts2Utils.getParameter("subject");
			String category = Struts2Utils.getParameter("category");
			if(category == null || category == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(subjectId == null || subjectId.toString() == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(tutorSubjectRegisterService.subjectExists(Long.parseLong(userId), Long.parseLong(subjectId))){
				msg = "You already registered this subject!";
				throw new Exception(msg);
			}
			tutorSubjectRegisterService.addTutorSubject(entity, Long.parseLong(userId), Long.parseLong(subjectId));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
	
	public void writeSuccessResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (null != data) {
			resultMap.put("data", data);
		}

		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
