package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMProgramVMEntity;
import com.gnomon.pdms.service.ImsDashboardService;

@Namespace("/ims")
public class ImsDashboardAction extends PDMSCrudActionSupport<PMProgramVMEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private ImsDashboardService imsDashboardService;
	
	
	private String modelData;
	
	private String id;
	
	private String issueId;
	
	
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getModelData() {
		return modelData;
	}

	public void setModelData(String modelData) {
		this.modelData = modelData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 首页权限取得
	 */
	public void getDashboardIMS() {
		try{
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String,Object>> list = this.imsDashboardService.getDashboardIMS(userId);
			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("menuId", map.get("MENU_ID"));
				dataMap.put("displaySeq", map.get("DISPLAY_SEQ"));
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("text", map.get("TEXT"));
				dataMap.put("iconCls", map.get("ICON_CLS"));
				dataMap.put("xType", map.get("X_TYPE"));
				dataMap.put("widgetWidth", map.get("WIDGET_WIDTH"));
				dataMap.put("widgetType", map.get("WIDGET_TYPE"));
				dataMap.put("permission", map.get("PERMISSION"));

				data.add(dataMap);
			}
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);			
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	@Override
	public PMProgramVMEntity getModel() {
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
	
	public void getIssueMemberList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  list = imsDashboardService.getIssueMemberList(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addMember() {
		try{
			List<Map<String, String>> memberIdlist = this.convertJson2List(this.modelData);
			imsDashboardService.addMembers(issueId, memberIdlist);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
}
