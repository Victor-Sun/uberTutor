/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

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
	
	
//	
////	private UserContext(String userid , String username, String sessionId) {
////		super();
////		this.username = username;
////		this.userid = userid;
////		this.sessionId = sessionId;
////	}
//
//	private Map<String,Object> attributes = new HashMap<String,Object>();
//
////	private String username;
////	private String userid;
////	private String sessionId;
//
//	public Map<String, Object> getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(Map<String, Object> attributes) {
//		this.attributes = attributes;
//	}
//
//	public void setAttribute(String name,Object value){
//		if(attributes.containsKey(name)){
//			attributes.remove(name);
//		}
//		attributes.put(name, value);
//	}
//	
//	public Object getAttribute(String name){
//		return attributes.get(name);
//	}
//	
//	private Date loginDate = new Date();
////	public String getUsername() {
////		return username;
////	}
////	public void setUsername(String username) {
////		this.username = username;
////	}
////	public String getUserid() {
////		return userid;
////	}
////	public void setUserid(String userid) {
////		this.userid = userid;
////	}
////	public String getSessionId() {
////		return sessionId;
////	}
////	public void setSessionId(String sessionId) {
////		this.sessionId = sessionId;
////	}
//
//
//	public Date getLoginDate() {
//		return loginDate;
//	}
//
//
//	public void setLoginDate(Date loginDate) {
//		this.loginDate = loginDate;
//	}
//
//
//	public static void clear(){
//		context.set(null);
//	}
	
}
