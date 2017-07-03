/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.integration.sso;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;


/**
 * @author frank
 *
 */
public class UserContext {
	
	public static final String KEY_USER_INFO = "userInfo";

	public static final String KEY_SESSION_ID = "sessionId";
	/**
	 * 
	 */
	public static final String KEY_ROLE_IDS = "roleIds";

	public static final String KEY_ROLES = "roles";

	/**
	 * 
	 */
	public static final String KEY_DEPARTMENT_ID = "departmentId";

	/**
	 * 
	 */
	public static final String KEY_IS_ADMIN = "isAdmin";
	
	private static Map<String,Object> getSession(){
		Map<String, Object> session = new HashMap<String,Object>();
		if(ActionContext.getContext() != null){
			session = ActionContext.getContext().getSession();
			if(session == null ||session.isEmpty()){
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
//	private static ThreadLocal<SSOUserInfo> context = new ThreadLocal<SSOUserInfo>();
	public static SSOUserInfo getUserInfo(){
		return (SSOUserInfo) getSession().get(KEY_USER_INFO);
	}
	
	public static Boolean isAdmin(){
		return (Boolean)getSession().get(KEY_IS_ADMIN);
	}
	
	public static List<String> getRoleIds(){
		return (List<String>)getSession().get(KEY_ROLE_IDS);
	}
	
	public static List<SSORole> getRoles(){
		return (List<SSORole>)getSession().get(KEY_ROLES);
	}
	
	public static String getDepartmentId(){
		return (String)getSession().get(KEY_DEPARTMENT_ID);
	}
	
	public static String getSessionId(){
		return (String)getSession().get(KEY_SESSION_ID);
	}
}
