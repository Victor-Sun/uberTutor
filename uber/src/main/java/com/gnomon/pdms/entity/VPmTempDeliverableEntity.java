package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_TEMP_DELIVERABLE")
public class VPmTempDeliverableEntity extends StringIdEntity {
	//
	@Column(name="CODE") 
	private String code;

	//
	@Column(name="NAME") 
	private String name;

	//
	@Column(name="DESCRIPTION") 
	private String description;

	//
	@Column(name="OBS_ID") 
	private String obsId;

	//
	@Column(name="TASK_ID") 
	private String taskId;

	//
	@Column(name="IS_KEY") 
	private String isKey;

	//
	@Column(name="HELP_URL") 
	private String helpUrl;
	
	//
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	//
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	//
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	//
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	
	//
	@Column(name="OBS_NAME") 
	private String obsName;
	
	//
	@Column(name="TASK_NAME") 
	private String taskName;
	
	//
	@Column(name="CHECKITEM_NAME") 
	private String checkitemName;
	
	//
	@Column(name="CHECK_REQUIREMENT") 
	private String checkRequirement;
	
	//
	@Column(name="CREATE_BY_NAME") 
	private String createByName;
	
	//
	@Column(name="UPDATE_BY_NAME") 
	private String updateByName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public String getHelpUrl() {
		return helpUrl;
	}

	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCheckitemName() {
		return checkitemName;
	}

	public void setCheckitemName(String checkitemName) {
		this.checkitemName = checkitemName;
	}

	public String getCheckRequirement() {
		return checkRequirement;
	}

	public void setCheckRequirement(String checkRequirement) {
		this.checkRequirement = checkRequirement;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

}
