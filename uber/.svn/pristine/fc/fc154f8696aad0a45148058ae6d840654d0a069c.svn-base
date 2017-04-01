package com.gnomon.pdms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_EXT_PROJECT")
public class ExtendedProjectEntity extends StringIdEntity {
	
	// 关联的项目ID
	@Column(name="PDMS_PROGRAM_UUID") 
	private String pdmsProgramUuid;
	
	@Column(name="EXT_PROCESS_CODE") 
	private String extProcessCode;
	
	@Column(name="APPLICATION_NAME") 
	private String applicationName;

	public String getPdmsProgramUuid() {
		return pdmsProgramUuid;
	}

	public void setPdmsProgramUuid(String pdmsProgramUuid) {
		this.pdmsProgramUuid = pdmsProgramUuid;
	}

	public String getExtProcessCode() {
		return extProcessCode;
	}

	public void setExtProcessCode(String extProcessCode) {
		this.extProcessCode = extProcessCode;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	
}
