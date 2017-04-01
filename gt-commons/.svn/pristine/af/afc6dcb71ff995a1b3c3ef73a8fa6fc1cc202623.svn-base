/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

import java.util.List;


/**
 * @author frank
 * 
 */
public class SSOUserInfo {
	private String id;
	private String userId;
	private String userName;
	private String departmentId;
	private String departmentName;
	private String email;
	private boolean admin;
	private List<SSORole> userRoles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 用于显示的用户名属性
	 * @return
	 */
	public String getUserDisplayName(){
		return userId+"<"+userName+">";
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<SSORole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<SSORole> userRoles) {
		this.userRoles = userRoles;
	}

	
}
