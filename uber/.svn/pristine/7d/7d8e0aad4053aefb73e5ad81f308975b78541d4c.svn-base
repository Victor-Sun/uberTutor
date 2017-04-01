package com.gnomon.pdms.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.SysProgramProfilePriviVMEntity;
import com.gnomon.pdms.entity.VIssueSourceEntity;
import com.gnomon.pdms.service.ProjectmanagerService;

@Namespace("/sys")
public class ProjectmanagerAction extends PDMSCrudActionSupport<VIssueSourceEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private VIssueSourceEntity vissueSourceEntity;

	@Autowired
	private ProjectmanagerService projectmanagerService;
	
	@Override
	public VIssueSourceEntity getModel() {
		return vissueSourceEntity;
	}
	
	private String editModel;
	
	public void setEditModel(String editModel) {
		this.editModel = editModel;
	}
	
	private String profileId;
	
	private String node;

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	private String sysId;

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	


	public String getNode() {
		return node;
	}



	public void setNode(String node) {
		this.node = node;
	}



	public String getProfileId() {
		return profileId;
	}

	public String getSysId() {
		return sysId;
	}

	private String allowFlag;

	private String id;

	public void setAllowFlag(String allowFlag) {
		this.allowFlag = allowFlag;
	}
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	/**
	 * 【项目管理-角色管理】一数据取得
	 */
	public void getProjectrolemanager() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.projectmanagerService.getProjectRoleList();
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 系统角色
				dataMap.put("systemFlag", map.get("SYSTEM_FLAG"));
				data.add(dataMap);
				if (map.get("SYSTEM_FLAG").equals("Y")){
					// 角色名称
					dataMap.put("profileName", "<"+map.get("PROFILE_NAME")+">");
				} else {
					// 角色名称
					dataMap.put("profileName", map.get("PROFILE_NAME"));
				}
				// 默认角色
				dataMap.put("defaultFlag", map.get("DEFAULT_FLAG"));
				// 默认角色
				dataMap.put("planLevel", map.get("PLAN_LEVEL"));	
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
	 * 【项目管理-角色管理】一数据更新
	 */
	public void updateProjectrolemanager(){
		try{
			Map<String, String> model = this.convertJson(this.editModel);
			this.projectmanagerService.updateProjectrolemanager(id,model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 【项目管理-角色管理】一数据插入
	 */
	public void insertProjectrolemanager(){
		try{
			Map<String, String> model = this.convertJson(this.editModel);
			this.projectmanagerService.insertProjectrolemanager(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 角色管理数据删除
	 */
	public void deleteProjectrolemanager(){
		try{
			this.projectmanagerService.deleteProjectrolemanager(sysId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 角色管理默认项目角色编辑
	 */
	public void editeProProjectrolemanager(){
		try{
			this.projectmanagerService.editeProProjectrolemanager(sysId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 【项目管理-权限管理】一数据取得
	 */
	public void getProjectrolemanagerPower() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<SysProgramProfilePriviVMEntity> list =
					this.projectmanagerService.getProjectrolemanagerPower(profileId);
			for(SysProgramProfilePriviVMEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", entity.getId());
				// 
				dataMap.put("privilegeCode", entity.getPrivilegeCode());
				// 
				if (entity.getAllowFlag().equals("Y")){
					dataMap.put("allowFlag", true);
				} else {
					dataMap.put("allowFlag", false);
				}
				//
				dataMap.put("privilegeName", entity.getPrivilegeName());
				//权限描述
				dataMap.put("privilegeDesc", entity.getPrivilegeDesc());
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
	 * 权限编辑
	 */
	public void editeProjectrolemanagerPower(){
		try{
			this.projectmanagerService.editeProjectrolemanagerPower(sysId,allowFlag);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void savePermission(){
		try{
			List<Map<String, String>> list = this.convertJson2List(editModel);
			this.projectmanagerService.savePermission(profileId,list);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getPermissionMenuList(){
//		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if("root".equals(node)){
			node = null;
		}
		List<Map<String, Object>> list = projectmanagerService.getPermissionMenuList(node, profileId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID").toString());
			dataMap.put("profilePrivilegeId", map.get("PROFILE_PRIVILEGE_ID"));
			dataMap.put("profileId", map.get("PROFILE_ID"));
			dataMap.put("text", map.get("NAME"));
			dataMap.put("parentId", map.get("PARENT_ID"));
			dataMap.put("leaf", ObjectConverter.convert2Boolean(map.get("IS_LEAF")));
			if(!ObjectConverter.convert2Boolean(map.get("IS_LEAF"))){
				dataMap.put("checked", this.getChecked(map.get("ID").toString()));
			}else{
				dataMap.put("checked", ObjectConverter.convert2Boolean(map.get("ALLOW_FLAG")));
			}
			
			dataMap.put("expanded", true);
			//dataMap.put("iconCls", map.get("ICON_CLS"));
			data.add(dataMap);
		}
//		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(data);
	}
	
	private boolean getChecked(String id){
		List<Map<String, Object>> list = projectmanagerService.getPermissionMenuList(id, profileId);
		for(Map<String, Object> map:list){
			if(ObjectConverter.convert2Boolean(map.get("ALLOW_FLAG"))){
				return true;
			}
		}
		return false;
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
