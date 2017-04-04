package com.gnomon.common.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.gnomon.common.system.entity.UserEntity;
import com.opensymphony.xwork2.ActionContext;

public class SessionData {
	
	public static final String KEY_LOGIN_USER = "loginUser";
	public static final String KEY_LOGIN_DEPT = "loginDept";

	// 取得Session
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

	// 用户信息取得
	public static UserEntity getLoginUser() {
		return (UserEntity)getSession().get(KEY_LOGIN_USER);
	}
	
	// 用户ID取得(UUID)
	public static String getLoginUserId() {
		return getLoginUser().getId();
	}
	
	// User ID取得, 是User Id, 不是UUID
	public static String getUserId() {
		return getLoginUser().getUserid();
	}
	
	// Login用户所属科室ID取得(默认科室)
	public static String getUserDeptId() {
		return getLoginUser().getDepartmentId();
	}
	
	// Login用户部门信息取得（所有）
	public static Map<String, Object> getUserDeptInfo() {
		return (Map<String, Object>)getSession().get(KEY_LOGIN_DEPT);
	}
	
	// Login用户所属部门ID取得(默认部门)
	public static String getUserTopDeptId() {
		Map<String, Object> userDeptInfo = getUserDeptInfo();
		return userDeptInfo.get("TOP_DEPARTMENT_ID") != null ? userDeptInfo.get("TOP_DEPARTMENT_ID").toString() : null;
	}
}