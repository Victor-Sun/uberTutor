package com.gnomon.pdms.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.base.CrudActionSupport;
//import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.intergration.sso.SSOUserInfo;
import com.gnomon.intergration.sso.UserContext;

public abstract class PDMSCrudActionSupport<T> extends CrudActionSupport<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495901603955879773L;
	
	private String programId;//项目ID
	
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
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			// Struts2Utils.getResponse().getWriter().write(jsonObject.toString());
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e);
		}
	}

	protected void writeErrorResult(Object data) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (null != data) {
			resultMap.put("data", data);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			// Struts2Utils.getResponse().getWriter().write(jsonObject.toString());
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e);
		}
	}

	protected void writeErrorResult(final Exception exception) {

		logger.error(exception.getMessage(),exception);

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
//			HttpServletResponse response = Struts2Utils.getResponse();
//            response.setContentType("application/json;charset=UTF-8");
//		    response.setCharacterEncoding("UTF-8");
//		    PrintWriter pw = response.getWriter();
//		    pw.write(jsonObject.toString());
//		    pw.flush();
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
	 * JSON转Map
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
	 * JSON转List<Map<String, String>>
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