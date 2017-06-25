package com.ubertutor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
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
			Long categoryId = Long.parseLong(Struts2Utils.getRequest().getParameter("categoryId"));
			this.writeSuccessResult(tutorSubjectRegisterService.getSubjectList(categoryId));
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
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String,Object>> pageResult = this.tutorSubjectRegisterService.getUserSubjects(userId, this.getPage(), this.getLimit());
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
			Long subjectId = Long.parseLong(Struts2Utils.getRequest().getParameter("userSubjectId"));
			entity = tutorSubjectRegisterService.get(subjectId);
			tutorSubjectRegisterService.delete(entity);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	public void editSubject() throws Exception{
		try{
			Long userSubjectId = Long.parseLong(Struts2Utils.getRequest().getParameter("userSubjectId"));
			String description = Struts2Utils.getRequest().getParameter("description");
			if(description.isEmpty()){
				String msg = "Description cannot be empty! Fill in a description!";
				throw new Exception(msg);
			}
			entity = tutorSubjectRegisterService.get(userSubjectId);
			entity.setDescription(description);
			tutorSubjectRegisterService.saveTutorSubject(entity);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	@Override
	public String save() throws Exception{
		try{
			String msg = "";
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			String subjectId = Struts2Utils.getRequest().getParameter("subject");
			String category = Struts2Utils.getRequest().getParameter("category");
			if(category == null || category == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(subjectId == null || subjectId == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(tutorSubjectRegisterService.subjectExists(userId, Long.parseLong(subjectId))){
				msg = "You already registered this subject!";
				throw new Exception(msg);
			}
			entity.setUserId(userId);
			entity.setSubjectId(Long.parseLong(subjectId));
			entity.setCreateDate(new Date());
			tutorSubjectRegisterService.saveTutorSubject(entity);
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
		entity = (id != null) ? tutorSubjectRegisterService.get(id) : new UserSubjectEntity(); 
	}

	@Override
	public UserSubjectEntity getModel(){
		return entity;
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
