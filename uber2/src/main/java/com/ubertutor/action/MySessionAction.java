package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.alibaba.fastjson.JSON;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MySessionService;

public class MySessionAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MySessionService sessionService;
	private UserEntity entity = SessionData.getLoginUser();
	private UserRequestEntity requestEntity;
	
	public void displayAllSessions(){
		this.writeSuccessResult(sessionService.getSessions());
	}
	
	public void displayUserSessions(){
		this.writeSuccessResult(sessionService.getUserSessions(entity.getId()));
	}
	
	public void displayTutorSessions(){
		this.writeSuccessResult(sessionService.getTutorSessions(entity.getId()));
	}
	
	public void displaySessionInfo(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter("requestId"));
		this.writeSuccessResult(sessionService.getSessionInfo((id)));
	}
	
	public void updateRequestStatus(){
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		requestEntity = sessionService.get(id);
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
}
