package com.gnomon.pdms.action.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.service.GTMenuManager;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/main")
public class MenuAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private GTMenuManager gtMenuManager;
	
	private String node;



	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	/*
	 * 取得菜单tree
	 */
	public void getMenu() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if ("root".equals(node) || node == null) {
			node = "0";
		}

		List<Map<String, Object>> list = gtMenuManager.getMenuList(SessionData.getUserId(), node, "PM");
		for(Map<String, Object> map:list){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("text", map.get("DEFAULT_LABEL"));
			dataMap.put("iconCls", map.get("ICON_CLS"));
			dataMap.put("viewType", map.get("VIEW_TYPE"));
			dataMap.put("selected", true);
			dataMap.put("leaf", toBoolean(map.get("IS_MENU_LEAF")));
			data.add(dataMap);
		}
		writeSuccessResult(data);
	}
	
	/*
	 * 取得菜单tree(质量问题管理)
	 */
	public void getIMSMenu() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if ("root".equals(node) || node == null) {
			node = "600";
		}

		List<Map<String, Object>> list = gtMenuManager.getMenuList(SessionData.getUserId(), node, "IM");
		for(Map<String, Object> map:list){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("text", map.get("DEFAULT_LABEL"));
			dataMap.put("iconCls", map.get("ICON_CLS"));
			dataMap.put("viewType", map.get("VIEW_TYPE"));
			dataMap.put("selected", true);
			dataMap.put("leaf", toBoolean(map.get("IS_MENU_LEAF")));
			// 首页展开
			if ("601".equals(PDMSCommon.nvl(map.get("ID")))) {
				dataMap.put("expanded", true);
			}
			data.add(dataMap);
		}
		writeSuccessResult(data);
	}
	
	private boolean toBoolean(Object flag){
		if ("Y".equals(flag)) {
			return true;
		}
		if("y".equals(flag)){
			return true;
		}
		if("true".equals(flag)){
			return true;
		}
		if("TRUE".equals(flag)){
			return true;
		}
		return false;
	}

	private void writeSuccessResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}