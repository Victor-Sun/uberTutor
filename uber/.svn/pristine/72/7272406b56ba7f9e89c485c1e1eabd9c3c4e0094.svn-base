package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_EXT201_ITEM")
public class Ext201ItemEntity extends StringIdEntity {
	
	//
	@Column(name="EXT_PROJECT_ID") 
	private String extProjectId;	
	
	//
	@Column(name="ITEM_NO") 
	private String itemNo;
	
	//
	@Column(name="ITEM_NAME") 
	private String itemName;
	
	@Transient
	private String obsName;
	
	@Transient
	private String owner;
	
	//
	@Column(name="SOURCING_TYPE") 
	private String sourcingType;
	
	//
	@Column(name="SEVERITY_LEVEL") 
	private String severityLevel;
	
	//
	@Column(name="DEV_TYPE") 
	private String devType;
	
	//
	@Column(name="IS_SE") 
	private String isSe;
	
	//
	@Column(name="PRODUCTION_TOOLING_PERIOD") 
	private Integer productionToolingPeriod;

	public String getExtProjectId() {
		return extProjectId;
	}

	public void setExtProjectId(String extProjectId) {
		this.extProjectId = extProjectId;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSourcingType() {
		return sourcingType;
	}

	public void setSourcingType(String sourcingType) {
		this.sourcingType = sourcingType;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getIsSe() {
		return isSe;
	}

	public void setIsSe(String isSe) {
		this.isSe = isSe;
	}

	public Integer getProductionToolingPeriod() {
		return productionToolingPeriod;
	}

	public void setProductionToolingPeriod(Integer productionToolingPeriod) {
		this.productionToolingPeriod = productionToolingPeriod;
	}
	
	
	
}
