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
@Table(name="PM_EXT203_WBS")
public class Ext203ItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_EXT203_WBS_SEQ")     
	@SequenceGenerator(name="PM_EXT203_WBS_SEQ", sequenceName="PM_EXT203_WBS_SEQ")
	private Long id;
	
	//
	@Column(name="EXT_PROJECT_ID") 
	private Long extProjectId;	
	
	@Column(name="PARENT_ID") 
	private Long parentId;	
	
	@Column(name="TASK_NAME") 
	private String taskName;	
	
	@Column(name="TASK_OWNER") 
	private String taskOwner;	
	
	@Column(name="PLANNED_START_DATE") 
	private Date plannedStartDate;	
	
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;	
	
	@Column(name="PLANNED_DURATION") 
	private Long plannedDuration;	
	
	@Column(name="PLANNED_MANHOUR") 
	private Long plannedManhour;	
	
	@Column(name="ACTUAL_START_DATE") 
	private Date actualStartDate;	
	
	@Column(name="ACTUAL_FINISH_DATE") 
	private Date actualFinishDate;	
	
	@Column(name="ACTUAL_MANHOUR") 
	private Long actualManhour;	
	
	@Column(name="PERCENT_COMPLETE") 
	private Double percentComplete;
	
	@Column(name="IS_LEAF") 
	private String isLeaf;

	public Long getExtProjectId() {
		return extProjectId;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setExtProjectId(Long extProjectId) {
		this.extProjectId = extProjectId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(Date plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public Long getPlannedDuration() {
		return plannedDuration;
	}

	public void setPlannedDuration(Long plannedDuration) {
		this.plannedDuration = plannedDuration;
	}

	public Long getPlannedManhour() {
		return plannedManhour;
	}

	public void setPlannedManhour(Long plannedManhour) {
		this.plannedManhour = plannedManhour;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	public Long getActualManhour() {
		return actualManhour;
	}

	public void setActualManhour(Long actualManhour) {
		this.actualManhour = actualManhour;
	}

	public Double getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(Double percentComplete) {
		this.percentComplete = percentComplete;
	}	
	

	

	
	
}
