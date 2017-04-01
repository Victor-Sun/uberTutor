package com.gnomon.pdms.action.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMObsEntity;
import com.gnomon.pdms.service.ObsUserService;

@Namespace("/com")
public class ObsUserAction extends PDMSCrudActionSupport<PMObsEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ObsUserService obsUserService;
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String node;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private String rootObsId;
	public void setRootObsId(String rootObsId) {
		this.rootObsId = rootObsId;
	}
	
	private boolean includeSubDept;
	public void setIncludeSubDept(boolean includeSubDept) {
		this.includeSubDept = includeSubDept;
	}
	
	private String query;
	public void setQuery(String query) {
		this.query = query;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	
	private String userObsId;
	public void setUserObsId(String userObsId) {
		this.userObsId = userObsId;
	}

	/**
	 * 责任人信息取得
	 */
	public void getObsUserList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

			List<Map<String, Object>> list = this.obsUserService.getObsUserList(
					programVehicleId, obsId, includeSubDept, query);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("employeeNo", map.get("EMPLOYEE_NO"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("obsId", map.get("OBS_ID"));
				dataMap.put("profileName", map.get("PROFILE_NAME"));
				dataMap.put("obsName", map.get("OBS_NAME"));
				dataMap.put("mobile", map.get("MOBILE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 责任人组织信息取得
	 */
	public void getObsDepartmentTree() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list = this.obsUserService
					.getObsDepartmentList(programId, vehicleId);
			if ("root".equals(node)) {
				for (Map<String, Object> map : list) {
					if (PDMSCommon.isNotNull(rootObsId)) {
						if (rootObsId.equals(map.get("ID"))) {
							Map<String, Object> dataMap = new HashMap<String, Object>();
							dataMap.put("id", map.get("ID"));
							dataMap.put("text", map.get("OBS_NAME"));
							dataMap.put("iconCls", "x-fa fa-group");
							dataMap.put("expanded", false);
							dataMap.put("leaf", "Y".equals(map.get("IS_LEAF")));
							data.add(dataMap);
							break;
						}
					} else {
						if (PDMSCommon.isNull(PDMSCommon.nvl(map.get("PARENT_ID")))) {
							Map<String, Object> dataMap = new HashMap<String, Object>();
							dataMap.put("id", map.get("ID"));
							if (PDMSCommon.isNotNull(vehicleId)) {
								dataMap.put("text", map.get("OBS_NAME"));
							} else {
								dataMap.put("text", PDMSCommon.nvl(map.get("OBS_NAME"))
										+ "（" + PDMSCommon.nvl(map.get("VEHICLE_NAME")) + "）");
							}
							dataMap.put("iconCls", "x-fa fa-group");
							dataMap.put("expanded", false);
							dataMap.put("leaf", "Y".equals(map.get("IS_LEAF")));
							data.add(dataMap);
						}
					}
				}
			} else {
				for (Map<String, Object> map : list) {
					if (node.equals(map.get("PARENT_ID"))) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", map.get("ID"));
						dataMap.put("text", map.get("OBS_NAME"));
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(map.get("IS_LEAF")));
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
	 * 所属专业领域下成员取得
	 */
	public void getFunctionObsUser() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

			List<Map<String, Object>> list =
					this.obsUserService.getFunctionObsUser(obsId, userObsId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("employeeNo", map.get("EMPLOYEE_NO"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("obsId", map.get("OBS_ID"));
				dataMap.put("profileName", map.get("PROFILE_NAME"));
				dataMap.put("obsName", map.get("OBS_NAME"));
				dataMap.put("mobile", map.get("MOBILE"));
				data.add(dataMap);
			}
			// 结果返回
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
