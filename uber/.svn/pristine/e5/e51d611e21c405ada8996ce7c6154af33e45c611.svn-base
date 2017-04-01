package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_PROGRAM_VEHICLE_SOP")
public class PMProgramVehicleSopVMEntity extends StringIdEntity {
	
	// 项目标识(UUID)
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	// 车型名称
	@Column(name="VEHICLE_NAME") 
	private String vehicleName;
	
	// SOP节点ID
	@Column(name="TASK_ID") 
	private String taskId;
	
	// SOP节点名称
	@Column(name="TASK_NAME") 
	private String taskName;
	
	// SOP时间
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(Date plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

}
