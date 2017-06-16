package com.ubertutor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.MySessionService;

public class MySessionAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MySessionService sessionService;
	private UserEntity entity = SessionData.getLoginUser();
	
	public void displayAllSessions(){
		JsonResult result = new JsonResult();
		result.buildSuccessResult(sessionService.getSessions());
		Struts2Utils.renderJson(result);
	}
	
	public void displayUserSessions(){
		JsonResult result = new JsonResult();
		result.buildSuccessResult(sessionService.getUserSessions(entity.getId()));
		Struts2Utils.renderJson(result);
	}
	
	public void displayTutorSessions(){
		JsonResult result = new JsonResult();
		result.buildSuccessResult(sessionService.getTutorSessions(entity.getId()));
		Struts2Utils.renderJson(result);
	}
	
	public void displaySessionInfo(){
		JsonResult result = new JsonResult();
		Long id = Long.parseLong(Struts2Utils.getRequest().getParameter("requestId"));
		result.buildSuccessResult(sessionService.getSessionInfo((id)));
		Struts2Utils.renderJson(result);
	}
	
	public void updateRequestStatus(){
		String statusCode = Struts2Utils.getRequest().getParameter("");
		Long requestId = Long.parseLong(Struts2Utils.getRequest().getParameter(""));
		sessionService.updateRequest(statusCode, requestId);
	}
}
