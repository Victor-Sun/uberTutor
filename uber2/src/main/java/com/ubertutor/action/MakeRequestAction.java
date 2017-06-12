package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomontech.pdms.redis.OnlineUtils;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MakeRequestService;

@Namespace("/main")
public class MakeRequestAction extends PDMSCrudActionSupport<UserRequestEntity>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MakeRequestService makeRequestService;
	private UserRequestEntity entity;
	private Long id;
	
	//Zelin: Save request  
	
	@Override
	public String save() throws Exception {
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
