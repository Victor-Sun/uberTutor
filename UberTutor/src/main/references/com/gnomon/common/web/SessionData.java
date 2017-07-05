package com.gnomon.common.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.ubertutor.entity.UserEntity;

public class SessionData {
	
	public static final String KEY_LOGIN_USER = "loginUser";
	public static final String KEY_LOGIN_DEPT = "loginDept";

	private static Map<String,Object> getSession() {
		Map<String, Object> session = new HashMap<String,Object>();
		if(ActionContext.getContext() != null){
			session = ActionContext.getContext().getSession();
			if(session == null || session.isEmpty()){
				HttpSession httpSession = ServletActionContext.getRequest().getSession();
				session =  new HashMap<String,Object>();
				Enumeration attributeNames = httpSession.getAttributeNames();
				while(attributeNames.hasMoreElements()){
					String attributeName = (String) attributeNames.nextElement();
					session.put(attributeName, httpSession.getAttribute(attributeName));
				}
				ActionContext.getContext().setSession(session);
			}
		}
		return session;
	}

	public static UserEntity getLoginUser() {
		return (UserEntity)getSession().get(KEY_LOGIN_USER);
	}
	
	public static String getLoginUserId() {
		return getLoginUser().getId().toString();
	}
	
	public static String getUserId() {
		return getLoginUser().getUsername();
	}
	
	public static Map<String, Object> getUserDeptInfo() {
		return (Map<String, Object>)getSession().get(KEY_LOGIN_DEPT);
	}
	
	public static String getUserTopDeptId() {
		Map<String, Object> userDeptInfo = getUserDeptInfo();
		return userDeptInfo.get("TOP_DEPARTMENT_ID") != null ? userDeptInfo.get("TOP_DEPARTMENT_ID").toString() : null;
	}
}
