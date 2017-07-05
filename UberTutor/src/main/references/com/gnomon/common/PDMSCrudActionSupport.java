package com.gnomon.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.alibaba.fastjson.JSON;
import com.gnomon.common.base.CrudActionSupport;
import com.gnomon.integration.sso.SSOUserInfo;
import com.gnomon.integration.sso.UserContext;

public abstract class PDMSCrudActionSupport<T> extends CrudActionSupport<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495901603955879773L;
	
	private String programId;//��ĿID
	
	private int page;
	private int limit;
	private String filter;
	private String sort;

	
	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	public String getFilter() {
		return filter;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	protected void writeSuccessResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
		try {
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (null != data) {
			resultMap.put("data", data);
		}
		try {
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void writeErrorResult(final Exception exception) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.put("errors", new HashMap<String, String>() {
			{
				put("reason", exception.getMessage()+"</br></br>");
			}
		});

		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected String getUserDisplayName(String userid){
//		return userService.getUserDisplayName(userid);
		return "";
	}


//	public boolean hasTaskPrivilege(String taskId){
//		try{
//			String userId = UserContext.getUserInfo().getUserId();
//			boolean resultList = privilegeService.hasTaskPrivilege(userId, programId, taskId, PrivilegeService.P0083);
//			return resultList;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
//	}

	private SSOUserInfo getCurrentUserInfo(){
		return UserContext.getUserInfo();
	}
	

	protected String getCurrentUserid(){
		return getCurrentUserInfo().getUserId();
	}
	protected String getCurrentUsername(){
		return getCurrentUserInfo().getUserName();
	}
	protected String getCurrentUserDisplayName(){
		return getCurrentUserInfo().getUserDisplayName();
	}
	
	/*
	 * JSONתMap
	 */
	public Map<String, String> convertJson(String strJson) {
		if (strJson == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> result = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(strJson);  
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {  
			String key = PDMSCommon.nvl(it.next());
			String value = "null".equals(PDMSCommon.nvl(jsonObject.get(key)))
								? "" : PDMSCommon.nvl(jsonObject.get(key));
			result.put(key, value);
		}
		return result;
	}
	
	/*
	 * JSONתList<Map<String, String>>
	 */
	public List<Map<String, String>> convertJson2List(String strJson) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		JSONArray jsonArray = JSONArray.fromObject(strJson);
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String, String> resultMap = new HashMap<String, String>();
			JSONObject jsonObject = (JSONObject)jsonArray.get(i);
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = PDMSCommon.nvl(it.next());
				String value = "null".equals(PDMSCommon.nvl(jsonObject.get(key)))
									? "" : PDMSCommon.nvl(jsonObject.get(key));
				resultMap.put(key, value);
			}
			result.add(resultMap);
		}
		return result;
	}

}
