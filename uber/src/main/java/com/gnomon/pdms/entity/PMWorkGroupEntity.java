package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PM_WORK_GROUP")
public class PMWorkGroupEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_WORK_GROUP_SEQ")     
	@SequenceGenerator(name="PM_WORK_GROUP_SEQ", sequenceName="PM_WORK_GROUP_SEQ")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="UUID")
	private String uuid;
	
	@Column(name="OWNER") 
	private String owner;
	
	@Column(name="MODULE_CODE") 
	private String moduleCode;
	
	@Column(name="INSTANCE_ID") 
	private String instanceId;
	
	@Column(name="IS_ACTIVE") 
	private String isActive;
	
	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
