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
@Table(name="PM_EXT301_TASK")
public class Ext301TaskEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_EXT301_ANY_SEQ")     
	@SequenceGenerator(name="PM_EXT301_ANY_SEQ", sequenceName="PM_EXT301_ANY_SEQ")
	private Long id;
	
	//
	@Column(name="EXT_PROJECT_ID") 
	private Long extProjectId;	
	
	@Column(name="ITEM_ID") 
	private Long itemId;	
	
	@Column(name="CHECKPOINT_ID") 
	private Long checkpointId;	
	
	@Column(name="OBS_ID") 
	private String obsId;	
	
	@Column(name="OWNER") 
	private String owner;	
	
	@Column(name="IS_COMPLETE") 
	private String isComplete;	
	
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;
	
	@Column(name="ACTUAL_FINISH_DATE") 
	private Date actualFinishDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExtProjectId() {
		return extProjectId;
	}

	public void setExtProjectId(Long extProjectId) {
		this.extProjectId = extProjectId;
	}


	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCheckpointId() {
		return checkpointId;
	}

	public void setCheckpointId(Long checkpointId) {
		this.checkpointId = checkpointId;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(Date plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public Date getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}


	
}
