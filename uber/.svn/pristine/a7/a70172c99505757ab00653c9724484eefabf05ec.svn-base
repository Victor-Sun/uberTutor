package com.gnomon.pdms.action.ims;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/ims")
public class ImsPrivilegeAction extends ActionSupport{
	private static final long serialVersionUID = 2993831250981686861L;

	@Autowired
	private PrivilegeService privilegeService;
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	/**
	 * 获取挂牌相关权限
	 */
	public void getImsListingPrivileges() {
		JsonResult result = new JsonResult();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String loginUser = SessionData.getLoginUserId();
		try{
			dataMap.put("listingPrivilege",
					this.privilegeService.canListIssue(issueId, loginUser));
			dataMap.put("delListingPrivilege",
					this.privilegeService.canDelListIssue(issueId, loginUser));
			dataMap.put("undoListingPrivilege",
					this.privilegeService.canUndoListIssue(issueId, loginUser));
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
			result.buildErrorResult(e.getMessage());
			Struts2Utils.renderJson(result);
		}
	}

}
