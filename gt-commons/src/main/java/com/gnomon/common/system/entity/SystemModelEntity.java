package com.gnomon.common.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SYS_SYSTEM_MODULE")
public class SystemModelEntity extends StringIdEntity {
	
	//
	@Column(name="MODULE_CODE") 
	private String moduleCode;
	
	//
	@Column(name="MODULE_NAME") 
	private String moduleName;
	
	//
	@Column(name="MODULE_ACTION_URL") 
	private String moduleActionUrl;
	
	//
	@Column(name="MODULE_ICON_URL") 
	private String moduleIconUrl;
	
	//
	@Column(name="MODULE_BIG_ICON_URL") 
	private String moduleBigIconUrl;
	
	//
	@Column(name="MODULE_SEQ") 
	private Long moduleSeq;
	
	//
	@Column(name="IS_DISPLAY_ON_HOME") 
	private Long isDisplayOnHome;

	public Long getIsDisplayOnHome() {
		return isDisplayOnHome;
	}

	public void setIsDisplayOnHome(Long isDisplayOnHome) {
		this.isDisplayOnHome = isDisplayOnHome;
	}

	public void setModuleSeq(Long moduleSeq) {
		this.moduleSeq = moduleSeq;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleActionUrl() {
		return moduleActionUrl;
	}

	public void setModuleActionUrl(String moduleActionUrl) {
		this.moduleActionUrl = moduleActionUrl;
	}

	public String getModuleIconUrl() {
		return moduleIconUrl;
	}

	public void setModuleIconUrl(String moduleIconUrl) {
		this.moduleIconUrl = moduleIconUrl;
	}

	public String getModuleBigIconUrl() {
		return moduleBigIconUrl;
	}

	public void setModuleBigIconUrl(String moduleBigIconUrl) {
		this.moduleBigIconUrl = moduleBigIconUrl;
	}

	public long getModuleSeq() {
		return moduleSeq;
	}

	public void setModuleSeq(long moduleSeq) {
		this.moduleSeq = moduleSeq;
	}

}
