package com.gnomon.pdms.action.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.base.CrudActionSupport;
import com.gnomon.common.system.entity.SysDepartmentEntity;
import com.gnomon.common.system.entity.SysUserVMEntity;
import com.gnomon.common.system.service.SysUserDepartmentService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.service.BimDataSyncService;
import com.gnomon.pdms.service.PersonalSettingsService;
import com.gnomon.pdms.service.SysDataSyncLogServcie;

@Namespace("/com")
public class SysUserDepartmentAction extends CrudActionSupport<SysUserVMEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;
	
	@Autowired
	private BimDataSyncService bimDataSyncService;
	
	@Autowired
	private SysDataSyncLogServcie sysDataSyncLogServcie;
	
	@Autowired
	private PersonalSettingsService personalSettingsService;
	
	private String node;
	private String userId;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String departmentId;
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private boolean includeSubDept;
	public void setIncludeSubDept(boolean includeSubDept) {
		this.includeSubDept = includeSubDept;
	}
	
	private String query;
	public void setQuery(String query) {
		this.query = query;
	}
	
	private String deptType;
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	
	private String rootDeptId;
	public void setRootDeptId(String rootDeptId) {
		this.rootDeptId = rootDeptId;
	}

	/**
	 * 系统用户信息取得
	 */
	public void getSysUserList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> list =
					this.sysUserDepartmentService.getSysUserList(
							departmentId, includeSubDept, query, deptType);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("code", map.get("EMPLOYEE_NO"));
				dataMap.put("name", map.get("USERNAME"));
				dataMap.put("email", map.get("EMAIL"));
				dataMap.put("mobile", map.get("MOBILE"));
				dataMap.put("departmentId", map.get("DEPARTMENT_ID"));
				dataMap.put("departmentName", map.get("DEPARTMENT_NAME"));
				dataMap.put("profileId", map.get("PROFILE_ID"));
				dataMap.put("profileName", map.get("PROFILE_NAME"));
				dataMap.put("hrPosition", map.get("HR_POSITION"));
				dataMap.put("userid", map.get("USERID"));
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
	 * 系统用户信息取得（取得全部）
	 */
	public void getAllSysUserList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> list =
					this.sysUserDepartmentService.getSysUserList(query);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("code", map.get("EMPLOYEE_NO"));
				dataMap.put("name", map.get("USERNAME"));
				dataMap.put("email", map.get("EMAIL"));
				dataMap.put("mobile", map.get("MOBILE"));
				dataMap.put("departmentId", map.get("DEPARTMENT_ID"));
				dataMap.put("departmentName", map.get("DEPARTMENT_NAME"));
				dataMap.put("profileId", map.get("PROFILE_ID"));
				dataMap.put("profileName", map.get("PROFILE_NAME"));
				dataMap.put("userid", map.get("USERID"));
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
	 * 系统用户信息取得(专业经理)
	 */
	public void getFMUserList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.sysUserDepartmentService.getUserList();
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
//				dataMap.put("code", map.get("EMPLOYEE_NO"));
				dataMap.put("name", map.get("USERNAME"));
//				dataMap.put("email", map.get("EMAIL"));
//				dataMap.put("mobile", map.get("MOBILE"));
//				dataMap.put("departmentId", map.get("DEPARTMENT_ID"));
//				dataMap.put("departmentName", map.get("DEPARTMENT_NAME"));
//				dataMap.put("profileId", map.get("PROFILE_ID"));
//				dataMap.put("profileName", map.get("PROFILE_NAME"));
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
	 * 系统组织信息取得
	 */
	public void getSysDepartmentTree() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<SysDepartmentEntity> list = this.sysUserDepartmentService
					.getSysDepartmentList();
			
			if ("root".equals(node)) {
				for (SysDepartmentEntity entity : list) {
					if (StringUtils.isEmpty(entity.getParentId())) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", entity.getId());
						dataMap.put("parenId", entity.getParentId());
						dataMap.put("applyTo", entity.getApplyTo());
						dataMap.put("name", entity.getName());
						dataMap.put("code", entity.getCode());
						dataMap.put("text", entity.getName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
						data.add(dataMap);
					}
				}
			} else {
				for (SysDepartmentEntity entity : list) {
					if (node.equals(entity.getParentId())) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", entity.getId());
						dataMap.put("parenId", entity.getParentId());
						dataMap.put("applyTo", entity.getApplyTo());
						dataMap.put("name", entity.getName());
						dataMap.put("code", entity.getCode());
						dataMap.put("text", entity.getName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
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
	 * 地址簿-左侧组织列表取得(包括我的工作组)
	 */
	public void getSysDepartmentGroupTree() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			// 行政组织取得
			List<SysDepartmentEntity> list = this.sysUserDepartmentService
					.getSysDepartmentList();
			// 我的工作组取得
			List<Map<String, Object>> groupList =
					this.personalSettingsService.getMyWorkGroupList();
			
			if ("root".equals(node)) {
				for (SysDepartmentEntity entity : list) {
					if (StringUtils.isEmpty(entity.getParentId())) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", entity.getId());
						dataMap.put("text", entity.getName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", true);
						dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
						data.add(dataMap);
					}
				}
				// 我的工作组
				for (Map<String, Object> map : groupList) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("id", map.get("ID"));
					dataMap.put("text", map.get("NAME"));
					dataMap.put("iconCls", "x-fa fa-odnoklassniki");
					dataMap.put("leaf", true);
					dataMap.put("deptType", "group");
					data.add(dataMap);
				}
			} else {
				for (SysDepartmentEntity entity : list) {
					if (node.equals(entity.getParentId())) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", entity.getId());
						dataMap.put("text", entity.getName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
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
	 * 取得系统部门
	 */
	public void getSysDepartmentList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list = this.sysUserDepartmentService.getDeptList();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("name", map.get("NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得指定部门下的所有组织
	 */
	public void getDeptListByRootDeptId() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list =
					this.sysUserDepartmentService.getDeptListByRootDeptId(rootDeptId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("name", map.get("NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUserVMEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetPassword(){
		JsonResult result = new JsonResult();
		sysUserDepartmentService.resetPassword(userId);
		result.buildSuccessResult(null);
		Struts2Utils.renderJson(result);
	}
	
	public void syncOrgAndUserFromBim(){
		JsonResult result = new JsonResult();
		String syncLogId = null;
		try {
			syncLogId = sysDataSyncLogServcie.beginSync(PDMSConstants.SYSTEM_NAME_BIM,PDMSConstants.DIRECTION_INPUT,"BimDataSyncService.syncOrgAndUserFromBim");
			
			bimDataSyncService.syncOrgAndUserFromBim();

			sysDataSyncLogServcie.throwExceptionIfSyncFailed(syncLogId);
			
			sysDataSyncLogServcie.finishSyncWithSuccess(syncLogId, "组织机构和用户同步成功");
			result.buildSuccessResult(null);
		} catch (Exception e) {
			result.buildErrorResult(e.getMessage());
			e.printStackTrace();
		}
		Struts2Utils.renderJson(result);
	}
}
