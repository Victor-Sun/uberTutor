package com.gnomon.pdms.action.ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.PersonalSettingsService;
import com.gnomon.pdms.service.ProjectListService;

@Namespace("/ts")
public class PersonalSettingsAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PersonalSettingsService personalSettingsService;
	
	@Autowired
	private ProjectListService projectListService;

	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private Long workGroupId;
	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	/**
	 * 首页-个人设置-常用项目list取得
	 */
	public void getCommonProjectList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.projectListService.getProjectList(null);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("code", map.get("CODE"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
		        dataMap.put("pmName", map.get("PM_NAME"));
		        boolean isChecked = this.personalSettingsService.getCommonProject(
		        		PDMSCommon.nvl(map.get("ID")));
		        dataMap.put("commonProject", isChecked);
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
	 * 首页-个人设置-常用项目check更新
	 */
	public void PersonalSettingsEditChange() {
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.personalSettingsService.updatePersonalSettingsEditChange(modelList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 密码修正
	 */
	public void changePersonalSettingsPsw() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			// 密码一致Check
			if (! model.get("newPws").equals(model.get("againPws"))) {
				this.writeErrorResult("两次输入密码不一致，请重新输入！");
				return;
			}
			
			// 旧密码正确验证
			String loginUser = SessionData.getLoginUserId();
			if (! this.personalSettingsService.checkPassword(
					loginUser, model.get("oldPws"))) {
				this.writeErrorResult("原密码不正确，请重新输入！");
				return;
			}
			this.personalSettingsService.changePersonalSettingsPsw(loginUser, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 我的工作组-取得工作组列表
	 */
	public void getMyWorkGroupList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.personalSettingsService.getMyWorkGroupList();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("name", map.get("NAME"));
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
	 * 我的工作组-取得工作组成员列表
	 */
	public void getMyWorkGroupMemberList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.personalSettingsService.getMyWorkGroupMemberList(workGroupId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("userName", map.get("USERNAME"));
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
	 * 我的工作组-新建工作组
	 */
	public void createMyWorkGroup() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.personalSettingsService.createMyWorkGroup(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 我的工作组-重命名工作组
	 */
	public void updateMyWorkGroup() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.personalSettingsService.updateMyWorkGroup(workGroupId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 我的工作组-删除工作组
	 */
	public void deleteMyWorkGroup() {
		try{
			this.personalSettingsService.deleteMyWorkGroup(workGroupId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 我的工作组-添加工作组成员
	 */
	public void addMyWorkGroupMember() {
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			String result = this.personalSettingsService.addMyWorkgroupMember(workGroupId, modelList);
			if (PDMSCommon.isNotNull(result)) {
				this.writeSuccessResult("下列成员重复、没有保存成功<BR>" + result);
			} else {
				this.writeSuccessResult(null);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 我的工作组-删除工作组成员
	 */
	public void deleteMyWorkGroupMember() {
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.personalSettingsService.deleteMyWorkgroupMember(workGroupId, modelList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
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
