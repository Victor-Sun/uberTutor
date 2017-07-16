package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MakeRequestService;

@Namespace("/main")
@AllowedMethods("save")
public class MakeRequestAction extends PDMSCrudActionSupport<UserRequestEntity>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private MakeRequestService makeRequestService;
	private UserRequestEntity requestEntity = new UserRequestEntity();
	private Long id, subjectId;
	private String description, title;
	
	/**
	 * Returns id
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Returns description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**Zelin
	 * Function that submit a request
	 */
	@Override
	public String save() throws Exception{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg;
			if(title.isEmpty() || title.startsWith(" ")){
				msg = "Title cannot be empty! Fill it in and try again!";
				throw new Exception(msg);
			}
			if(description.isEmpty() || description.startsWith(" ")){
				msg = "Description cannot be empty! Fill it in and try again!";
				throw new Exception(msg);
			}
			requestEntity.setUserId(Long.parseLong(SessionData.getLoginUserId()));
			requestEntity.setSubjectId(subjectId);
			requestEntity.setDescription(description);
			requestEntity.setTitle(title);
			requestEntity.setCreateDate(new Date());
			requestEntity.setStatus("OPEN");
			makeRequestService.save(requestEntity);
			resultMap.put("requestId", requestEntity.getId());
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	public UserRequestEntity getModel() {
		return requestEntity;
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
	public String delete() throws Exception {
		try {
			makeRequestService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		requestEntity = (id != null) ? makeRequestService.get(id) : new UserRequestEntity();
	}
}
