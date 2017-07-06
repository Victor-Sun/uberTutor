package com.ubertutor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.UserSubjectEntity;
import com.ubertutor.service.TutorSubjectRegisterService;

import jp.co.nec.flowlites.core.FLPage;

@Namespace("/main")
@AllowedMethods({"displayCategories",
	"displayCategorySubject",
	"displayUserSubjects",
	"removeSubject",
	"editSubject",
	"save"})
public class TutorSubjectRegisterAction extends PDMSCrudActionSupport<UserSubjectEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	private UserSubjectEntity subjectEntity;
	private Long id;
	private String categoryId, userSubjectId, description, subject, category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserSubjectId() {
		return userSubjectId;
	}

	public void setUserSubjectId(String userSubjectId) {
		this.userSubjectId = userSubjectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Displays all categories
	 * @throws Exception
	 */
	public void displayCategories() throws Exception{
		try{
			this.writeSuccessResult(tutorSubjectRegisterService.getCategoryList());
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Displays subjects based on category
	 * @throws Exception
	 */
	public void displayCategorySubject() throws Exception{
		try {
			this.writeSuccessResult(tutorSubjectRegisterService.getSubjectList(Long.parseLong(categoryId)));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Displays a user's subjects
	 * @throws Exception
	 */
	public void displayUserSubjects() throws Exception{
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.tutorSubjectRegisterService.getUserSubjects(Long.parseLong(SessionData.getLoginUserId()), this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("ID", map.get("ID"));
				dataMap.put("SUBJECT_ID", map.get("SUBJECT_ID"));
				dataMap.put("SUBJECT_TITLE", map.get("SUBJECT_TITLE"));
				dataMap.put("CATEGORY_ID", map.get("CATEGORY_ID"));
				dataMap.put("CATEGORY_TITLE", map.get("CATEGORY_TITLE"));
				dataMap.put("USER_ID", map.get("USER_ID"));
				dataMap.put("DESCRIPTION", map.get("DESCRIPTION"));
				dataMap.put("CREATE_DATE", map.get("CREATE_DATE"));
				dataMap.put("UPDATE_DATE", map.get("UPDATE_DATE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Removes a user's subject
	 * @throws Exception
	 */
	public void removeSubject() throws Exception{
		try{
			subjectEntity = tutorSubjectRegisterService.get(Long.parseLong(userSubjectId));
			tutorSubjectRegisterService.delete(subjectEntity);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}
	
	public void editSubject() throws Exception{
		try{
			if(description.isEmpty()){
				String msg = "Description cannot be empty! Fill in a description!";
				throw new Exception(msg);
			}
			subjectEntity = tutorSubjectRegisterService.get(Long.parseLong(userSubjectId));
			subjectEntity.setDescription(description);
			tutorSubjectRegisterService.saveTutorSubject(subjectEntity);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	@Override
	public String save() throws Exception{
		try{
			String msg = "";
			if(category == null || category == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(subject == null || subject == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(tutorSubjectRegisterService.subjectExists(Long.parseLong(SessionData.getLoginUserId()), Long.parseLong(subject))){
				msg = "You already registered this subject!";
				throw new Exception(msg);
			}
			subjectEntity.setUserId(Long.parseLong(SessionData.getLoginUserId()));
			subjectEntity.setSubjectId(Long.parseLong(subject));
			subjectEntity.setCreateDate(new Date());
			tutorSubjectRegisterService.saveTutorSubject(subjectEntity);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	@Override
	public String delete() throws Exception{
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
	protected void prepareModel() throws Exception{
		subjectEntity = (id != null) ? tutorSubjectRegisterService.get(id) : new UserSubjectEntity(); 
	}

	public UserSubjectEntity getModel(){
		return subjectEntity;
	}

	@Override
	public String list() throws Exception{
		return null;
	}

	@Override
	public String input() throws Exception{
		return null;
	}
}