package com.ubertutor.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

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
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			this.writeSuccessResult(tutorSubjectRegisterService.getUserSubjects(userId));
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
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
			Long subjectId = Long.parseLong(Struts2Utils.getRequest().getParameter("userSubjectId"));
			String description = Struts2Utils.getRequest().getParameter("description");
			entity = tutorSubjectRegisterService.get(subjectId);
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
			Long subjectId = Long.parseLong(Struts2Utils.getRequest().getParameter("subject"));
			String category = Struts2Utils.getRequest().getParameter("category");
			if(category == null || category == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(subjectId == null || subjectId.toString() == ""){
				msg = "Invalid subject, select a valid subject and try again!";
				throw new Exception(msg);
			}
			if(tutorSubjectRegisterService.subjectExists(userId, subjectId)){
				msg = "You already registered this subject!";
				throw new Exception(msg);
			}
			entity.setUserId(userId);
			entity.setSubjectId(subjectId);
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
