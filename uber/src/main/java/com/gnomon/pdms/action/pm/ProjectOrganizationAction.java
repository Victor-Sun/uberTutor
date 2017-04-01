package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMObsEntity;
import com.gnomon.pdms.service.ProjectOrganizationService;
import com.gnomon.pdms.service.ProjectPlanService;

public class ProjectOrganizationAction extends PDMSCrudActionSupport<PMObsEntity>{

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjectOrganizationService projectOrganizationService;
	
	@Autowired
	private ProjectPlanService projectPlanService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	
	private String node;
	public void setNode(String node) {
		this.node = node;
	}

	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String userObsId;
	public void setUserObsId(String userObsId) {
		this.userObsId = userObsId;
	}
	
	private String userId;
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}

	/**
	 * 一级组织架构取得
	 */
	public void getProjectOrganizationLevelOne() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 一级组织架构取得
			List<Map<String, Object>> list = this.projectOrganizationService
					.getObsTreeList(programId, programVehicleId, baselineId);
			if (PDMSCommon.isNotNull(node) && node.equals("root")) {
				for (Map<String, Object> map : list) {
					if (PDMSCommon.isNull(PDMSCommon.nvl(map.get("PARENT_ID")))) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", map.get("ID"));
						dataMap.put("parentObs", "");
						dataMap.put("obsTypeCode", map.get("OBS_TYPE_CODE"));
						dataMap.put("text", map.get("OBS_NAME"));
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", true);
						// 一级组织架构显示到专业领域
						if (PDMSConstants.OBS_TYPE_RESP_DEPT.equals(map.get("OBS_TYPE_CODE"))) {
							dataMap.put("leaf", true);
						} else {
							dataMap.put("leaf", "Y".equals(map.get("IS_LEAF")));
						}
						dataMap.put("obsCanEdit", "1".equals(PDMSCommon.nvl(
								map.get("OBS_CAN_EDIT"))));
						
						data.add(dataMap);
					}
				}
			} else {
				for (Map<String, Object> map : list) {
					if (node.equals(PDMSCommon.nvl(map.get("PARENT_ID")))) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", map.get("ID"));
						dataMap.put("parentObs", map.get("PARENT_ID"));
						dataMap.put("obsTypeCode", map.get("OBS_TYPE_CODE"));
						dataMap.put("text", map.get("OBS_NAME"));
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", true);
						// 一级组织架构显示到专业领域
						if (PDMSConstants.OBS_TYPE_RESP_DEPT.equals(map.get("OBS_TYPE_CODE"))) {
							dataMap.put("leaf", true);
						} else {
							dataMap.put("leaf", "Y".equals(map.get("IS_LEAF")));
						}
						dataMap.put("obsCanEdit", "1".equals(PDMSCommon.nvl(
								map.get("OBS_CAN_EDIT"))));
						data.add(dataMap);
					}
				}
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 二级组织架构取得
	 */
	public void getProjectOrganizationLevelTwo() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list = this.projectOrganizationService
					.getObsTreeFromDept(programId, programVehicleId, baselineId);
			if (PDMSCommon.isNotNull(node) && node.equals("root")) {
				for (Map<String, Object> orgInfo : list) {
					String obsId = PDMSCommon.nvl(orgInfo.get("ID"));
					if (PDMSCommon.isNotNull(baselineId)) {
						obsId = PDMSCommon.nvl(orgInfo.get("OBS_ID"));
					}
					if (PDMSConstants.OBS_TYPE_RESP_DEPT.equals(orgInfo.get("OBS_TYPE_CODE"))) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", obsId);
						dataMap.put("functionId", obsId);
						dataMap.put("parentObs", orgInfo.get("PARENT_ID"));
						dataMap.put("obsTypeCode", orgInfo.get("OBS_TYPE_CODE"));
						dataMap.put("text", orgInfo.get("OBS_NAME"));
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(orgInfo.get("IS_LEAF")));
						dataMap.put("obsCanEdit", "1".equals(PDMSCommon.nvl(
								orgInfo.get("OBS_CAN_EDIT"))));
						data.add(dataMap);
					}
				}
			} else {
				for (Map<String, Object> orgInfo : list) {
					String obsId = PDMSCommon.nvl(orgInfo.get("ID"));
					if (PDMSCommon.isNotNull(baselineId)) {
						obsId = PDMSCommon.nvl(orgInfo.get("OBS_ID"));
					}
					if (node.equals(orgInfo.get("PARENT_ID"))) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", obsId);
						dataMap.put("functionId", orgInfo.get("FUNCTION_ID"));
						dataMap.put("parentObs", orgInfo.get("PARENT_ID"));
						dataMap.put("obsTypeCode", orgInfo.get("OBS_TYPE_CODE"));
						dataMap.put("text", orgInfo.get("OBS_NAME"));
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(orgInfo.get("IS_LEAF")));
						dataMap.put("obsCanEdit", "1".equals(PDMSCommon.nvl(
								orgInfo.get("OBS_CAN_EDIT"))));
						data.add(dataMap);
					}
				}
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 组织成员取得
	 */
	public void getProjectOrganizationUser() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.projectOrganizationService
					.getProjectOrganizationUser(obsId, baselineId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("employeeNo", map.get("EMPLOYEE_NO"));
				dataMap.put("email", map.get("EMAIL"));
				dataMap.put("profileId", map.get("PROFILE_ID"));
				dataMap.put("sysDepartmentName", map.get("SYS_DEPARTMENT_NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProjectOrganizationUserLevelOne() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.projectOrganizationService
					.getProjectOrganizationUserLevelOne(obsId, baselineId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("employeeNo", map.get("EMPLOYEE_NO"));
				dataMap.put("email", map.get("EMAIL"));
				dataMap.put("profileId", map.get("PROFILE_ID"));
				dataMap.put("sysDepartmentName", map.get("SYS_DEPARTMENT_NAME"));
				dataMap.put("mobile", map.get("MOBILE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProjectOrganizationUserLevelTwo() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.projectOrganizationService
					.getProjectOrganizationUserLevelTwo(obsId, baselineId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("employeeNo", map.get("EMPLOYEE_NO"));
				dataMap.put("email", map.get("EMAIL"));
				dataMap.put("profileId", map.get("PROFILE_ID"));
				dataMap.put("sysDepartmentName", map.get("SYS_DEPARTMENT_NAME"));
				dataMap.put("mobile", map.get("MOBILE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 项目组织信息取得
	 */
	public void getObsInfo() {
		try{
			// 组织ID查询
			JsonResult result = new JsonResult();
			Map<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> obsMap =
					this.projectOrganizationService.getObsInfo(obsId, baselineId);
			data.put("obsCode", obsMap.get("OBS_CODE"));
			data.put("obsName", obsMap.get("OBS_NAME"));
			data.put("obsDesc", obsMap.get("OBS_DESC"));
			data.put("isRespDept", PDMSConstants.OBS_TYPE_RESP_DEPT.equals(obsMap.get("OBS_TYPE_CODE")));
			data.put("fnGroupMgrId", obsMap.get("FN_GROUP_MGR_ID"));
			data.put("fnGroupMgrName", obsMap.get("FN_GROUP_MGR_NAME"));
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 更新组织基本信息
	 */
	public void updateObsInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectOrganizationService.updateObsInfo(obsId, programVehicleId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更新项目组织成员信息
	 */
	public void updateObsUserInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectOrganizationService.updateObsUserInfo(programId,obsId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 新增项目组织成员信息
	 */
	public void createObsUserInfo() {
		try {
			// JSON解析
			List<Map<String, String>> model = this.convertJson2List(this.model);
			String result = this.projectOrganizationService.createObsUserInfo(
					programId, obsId, model);
			if (PDMSCommon.isNotNull(result)) {
				this.writeSuccessResult("下列人员以及角色重复、没有保存成功<BR>" + result);
			} else {
				this.writeSuccessResult(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 组织成员Check
	 */
	public void checkObsUser() {
		try {
			// 目标组织是否已被计划使用Check
			List<Map<String, Object>> obsTaskList =
					this.projectOrganizationService.getObsTaskList(obsId, userId);
			if (obsTaskList.size() > 0) {
				this.writeSuccessResult(false);
			} else {
				this.writeSuccessResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除项目组织成员信息
	 */
	public void deleteObsUserInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectOrganizationService.deleteObsUserInfo(userObsId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 新增项目组织
	 */
	public void createObsInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			String newObsId = this.projectOrganizationService.createObsInfo(
					programId, obsId, programVehicleId, model);
			this.writeSuccessResult(newObsId);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除项目组织信息
	 */
	public void deleteObsInfo() {
		try {
			this.projectOrganizationService.deleteObsInfo(obsId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 专业领域一览取得
	 */
	public void getRespDeptList() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.projectOrganizationService.getObsPlanList(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("obsName", map.get("OBS_NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public PMObsEntity getModel() {
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
