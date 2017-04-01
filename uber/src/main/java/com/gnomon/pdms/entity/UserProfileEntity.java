package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SYS_USER_PROFILE")

public class UserProfileEntity extends StringIdEntity {
	
	//用户标识
	@Column(name="USER_ID") 
	private String userId;

	@Column(name="PROFILE_ID") 
	private String profileId;
	
	@Column(name="DEPARTMENT_ID") 
	private String departmentId;
	
	@Column(name="DEFAULT_FLAG") 
	private String defaultFlag;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

}
