package com.gnomon.pdms.action.pm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/pm")
public class ProjectPrivilegeAction extends ActionSupport{
	private static final long serialVersionUID = 2993831250981686861L;

	@Autowired
	private PrivilegeService privilegeService;

	/**
	 * 获得所有的项目权限
	 */
	public void getProgramPrivileges(){
		JsonResult result = new JsonResult();
		try{
			if(StringUtils.isNotEmpty(programVehicleId)){
				getProgramVehiclePrivileges();
				return;
			}
			Map<String, List<String>> projectPrivilege =
				privilegeService.getProgramPrivileges(
						SessionData.getLoginUserId(), programId);
			result.buildSuccessResult(projectPrivilege);
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
			result.buildErrorResult(e);
		}
	}
	
	/**
	 * 获得所有的车型权限
	 */
	public void getProgramVehiclePrivileges(){
		JsonResult result = new JsonResult();
		try{
			Map<String, List<String>> projectPrivilege =
				privilegeService.getProgramVehiclePrivileges(
						SessionData.getLoginUserId(), programId,programVehicleId);
			result.buildSuccessResult(projectPrivilege);
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			result.buildErrorResult(e);
		}
	}
	
	/**
	 * 获取OBS权限
	 */
	public void hasObsPriv() {
		JsonResult result = new JsonResult();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap.put("obsPrivilege",
					this.privilegeService.hasObsPriv(obsId, privilegeCode));
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			result.buildErrorResult(e);
		}
	}

	//Properties and getter,setter.
	private String privilegeCode;
	private String programId;
	private String programVehicleId;
	private String obsId;
	

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
}
