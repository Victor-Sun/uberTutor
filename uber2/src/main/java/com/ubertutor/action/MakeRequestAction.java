package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MakeRequestService;

@Namespace("/main")
public class MakeRequestAction extends PDMSCrudActionSupport<UserRequestEntity>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MakeRequestService makeRequestService;
	private UserRequestEntity entity;
	private Long id;
	

	/**Zelin
	 * Function that submit a request
	 */
	@Override
	public String save() throws Exception{
		try {
			
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			Long subjectId = Long.parseLong(Struts2Utils.getParameter("SUBJECT_ID"));
			String description = Struts2Utils.getRequest().getParameter("description");
			String title = Struts2Utils.getRequest().getParameter("title");
	        entity.setDescription(description);
	        entity.setTitle(title);
	        makeRequestService.makeRequest(entity, userId, description, subjectId, title);
	        
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}
	
	@Override
	public UserRequestEntity getModel() {
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
		entity = (id != null) ? makeRequestService.get(id) : new UserRequestEntity();
	}
}
