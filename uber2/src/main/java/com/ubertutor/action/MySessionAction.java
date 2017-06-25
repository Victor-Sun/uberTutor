package com.ubertutor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.alibaba.fastjson.JSON;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MySessionService;

public class MySessionAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MySessionService sessionService;
	private UserEntity entity = SessionData.getLoginUser();
	private UserRequestEntity requestEntity;

	public void displayAllSessions(){
		this.writeSuccessResult(sessionService.getSessions());
	}

	public void displayUserSessions(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String,Object>> pageResult = this.sessionService.getUserSessions(entity.getId(), this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("REQUEST_ID", map.get("REQUEST_ID"));
				dataMap.put("CREATE_DATE", map.get("CREATE_DATE"));
				dataMap.put("STUDENT_ID", map.get("STUDENT_ID"));
				dataMap.put("STUDENT_NAME", map.get("STUDENT_NAME"));
				dataMap.put("TUTOR_ID", map.get("TUTOR_ID"));
				dataMap.put("TUTOR_NAME", map.get("TUTOR_NAME"));
				dataMap.put("CATEGORY", map.get("CATEGORY"));
				dataMap.put("SUBJECT", map.get("SUBJECT"));
				dataMap.put("STATUS", map.get("STATUS"));
				dataMap.put("SUBJECT_DESCRIPTION", map.get("SUBJECT_DESCRIPTION"));
				dataMap.put("REQUEST_TITLE", map.get("REQUEST_TITLE"));
				dataMap.put("ROW_NUM", map.get("ROW_NUM"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void displayTutorSessions(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String,Object>> pageResult = this.sessionService.getUserSessions(entity.getId(), this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("REQUEST_ID", map.get("REQUEST_ID"));
				dataMap.put("CREATE_DATE", map.get("CREATE_DATE"));
				dataMap.put("STUDENT_ID", map.get("STUDENT_ID"));
				dataMap.put("STUDENT_NAME", map.get("STUDENT_NAME"));
				dataMap.put("TUTOR_ID", map.get("TUTOR_ID"));
				dataMap.put("TUTOR_NAME", map.get("TUTOR_NAME"));
				dataMap.put("CATEGORY", map.get("CATEGORY"));
				dataMap.put("SUBJECT", map.get("SUBJECT"));
				dataMap.put("STATUS", map.get("STATUS"));
				dataMap.put("SUBJECT_DESCRIPTION", map.get("SUBJECT_DESCRIPTION"));
				dataMap.put("REQUEST_TITLE", map.get("REQUEST_TITLE"));
				dataMap.put("ROW_NUM", map.get("ROW_NUM"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void displaySessionInfo(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter("requestId"));
		this.writeSuccessResult(sessionService.getSessionInfo((id)));
	}

	public void updateSessionToPending(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("PENDING");
		requestEntity.setPendingDate(new Date());
		sessionService.updateRequest(requestEntity);
	}

	public void updateSessionToInProcess(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("IN PROCESS");
		requestEntity.setProcessDate(new Date());
		sessionService.updateRequest(requestEntity);
	}

	public void updateSessionToClosed(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("CLOSED");
		requestEntity.setCloseDate(new Date());
		sessionService.updateRequest(requestEntity);
	}	

	public void updateSessionToCanceled(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("CANCELED");
		requestEntity.setCancelDate(new Date());
		sessionService.updateRequest(requestEntity);
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

	@Override
	public UserRequestEntity getModel() {
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
