package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.service.ProfileManagementService;
import com.ubertutor.service.SignupService;

@Namespace("/main")
public class ProfileManagementAction extends PDMSCrudActionSupport<UserEntity> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProfileManagementService profileManagement;
	@Autowired
	private SignupService signupService;
	private UserEntity entity;
	private String id, msg;
	
	public String save() throws Exception{
		try {

		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	public String savePassword() throws Exception{
		try {
			String p1 = "", p2 = "";
			p1 = Struts2Utils.getRequest().getParameter("password");
			p2 = Struts2Utils.getRequest().getParameter("password2");
			if(signupService.passwordConfirmation(p1, p2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}
	
	@Override
	public UserEntity getModel() {
		// TODO Auto-generated method stub
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
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new UserEntity();
		}else{
			entity = profileManagement.get(id);
		}
	}
}
