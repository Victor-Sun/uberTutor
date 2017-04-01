package com.gnomon.pdms.action.com;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;

@Namespace("/com")
public class SysPrivilegeAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	private String userId;
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	private String privilegeCode;
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	/**
	 * 是否具有指定的系统权限
	 */
	public void hasSystemPrivilege() {
		JsonResult result = new JsonResult();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (PDMSCommon.isNull(userId)) {
				userId = SessionData.getUserId();
			}
			boolean privilege = this.privilegeService.hasSystemPrivilege(
					userId, privilegeCode);
			dataMap.put("privilege", privilege);
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch(Exception e) {
			result.buildErrorResult(e);
		}
	}
	
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

}
