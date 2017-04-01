package com.gnomon.common.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Access(AccessType.FIELD)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="SYS_PROFILE_SYSTEM_MODULE")
public class GTGlobalProfileSystemModule extends StringIdEntity{
	
	@Column(name="SYSTEM_MODULE_CODE")
	private String systemModuleCode;
	@Column(name="PROFILE_ID")
	private Long profileId;
	@Column(name="DEFAULT_FLAG")
	private String defaultFlag;
	
	
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getSystemModuleCode() {
		return systemModuleCode;
	}
	public void setSystemModuleCode(String systemModuleCode) {
		this.systemModuleCode = systemModuleCode;
	}
	
	

}
