package com.gnomon.pdms.action.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTRole;
import com.gnomon.pdms.service.GTRoleManager;
import com.gnomon.pdms.service.GTUserRoleManager;

@Namespace("/role")
public class RoleAction extends PDMSCrudActionSupport<GTRole> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GTRole entity;
	
	@Autowired
	private GTRoleManager roleManager;
	
	@Autowired
	private GTUserRoleManager gtUserRoleManager;
	
	private Long id;
	
	private String roleId;
	
	private String userId;
	
	private Long parentId;
	
	private String node;
	
	private String idStr;
	
	private String type;
	
	private String departmentId;
	
	public Long getId() {
		return id;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public GTRole getModel() {
		return entity;
	}

	@Override
	public String list() throws Exception {
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		try {
			if (entity.getId() == null) {
				roleManager.createRole(entity.getRoleId(), entity.getInternalUser(),
						entity.getTitle(), entity.getTitleEn(), entity.getIsSysRole());
			} else {
				roleManager.updateRole(entity.getRoleId(), entity.getInternalUser(),
						entity.getTitle(), entity.getTitleEn(), entity.getIsSysRole());
			}
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String delete() throws Exception {
		roleManager.deleteRole(roleId);
		this.writeSuccessResult(null);
		return null;
	}
	
	public String deletePre() throws Exception {
		String result = roleManager.deleteRolePre(roleId);
		this.writeSuccessResult(result);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity = new GTRole();
	}
	
	/**
	 * 在preview()前执行二次绑定.
	 */
	public void preparePreview() throws Exception {
		prepareModel();
	}
	
	
	public void getRoleList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<GTRole> list = roleManager.getRoleList();
		for(GTRole gtRole:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("id", gtRole.getId());
			dataMap.put("roleId", gtRole.getRoleId());
			dataMap.put("title", gtRole.getTitle());
			dataMap.put("titleEn", gtRole.getTitleEn());
			dataMap.put("internalUser", gtRole.getInternalUser());
			dataMap.put("isSysRole", gtRole.getIsSysRole());
			data.add(dataMap);
		}
		
		
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getRoleUserList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = roleManager.getRoleUserList(roleId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("userId", map.get("USERID"));
			dataMap.put("roleId", map.get("ROLE_ID"));
			dataMap.put("userName", map.get("USERNAME"));
			dataMap.put("name", map.get("Name"));
			dataMap.put("departmentId", map.get("DepartmentId"));
			dataMap.put("roleTitle", map.get("TITLE"));
			data.add(dataMap);
		}
		
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getUserList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = roleManager.getUserList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("name", map.get("USERNAME"));
			dataMap.put("id", map.get("USERID"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	//部门取得
	public void getDepartmentList() {
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = roleManager.getDepartmentList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("name", map.get("NAME"));
			dataMap.put("id", map.get("ID"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getRoleMenuList(){
//		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if("root".equals(node)){
			node = "0_M";
		}
		List<Map<String, Object>> list = roleManager.getRoleMenuList(node,roleId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ITEM_ID").toString());
			dataMap.put("text", map.get("ITEM_NAME"));
			dataMap.put("parentId", map.get("ITEM_PARENT_ID"));
			dataMap.put("leaf", toBoolean(map.get("IS_LEAF")));
			dataMap.put("checked", toBoolean(map.get("IS_CHECK")));
			dataMap.put("expanded", true);
			//dataMap.put("iconCls", map.get("ICON_CLS"));
			data.add(dataMap);
		}
//		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(data);
	}

	private boolean toBoolean(Object flag){
		if("Y".equals(flag)){
			return true;
		}
		if("true".equals(flag)){
			return true;
		}
		return false;
	}
	
	public void saveRoleMenu() throws Exception {
		try {
			roleManager.saveRoleMenu(roleId, idStr);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	public void saveUserRole() throws Exception {
		try {
			gtUserRoleManager.createRoleUser(roleId,userId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	public void deleteUserRole() throws Exception {
		try {
			gtUserRoleManager.deleteRoleUser(roleId,userId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	public void getMenuWidgetList(){
//		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if("root".equals(node)){
			node = "0";
		}
		List<Map<String, Object>> list = roleManager.getUserWidgetList(SessionData.getUserId(), node);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID").toString());
			dataMap.put("text", map.get("TITLE"));
			dataMap.put("parentId", map.get("MENU_ID"));
			//dataMap.put("iconCls", map.get("ICON_CLS"));
			data.add(dataMap);
		}
//		result.buildSuccessResultForList(data, 1);
		this.writeSuccessResult(data);
	}
}
