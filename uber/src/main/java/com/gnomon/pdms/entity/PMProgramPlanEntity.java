package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_PROGRAM_PLAN")
public class PMProgramPlanEntity extends StringIdEntity {
	
	// 项目ID
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	// 计划类型
	@Column(name="PLAN_LEVEL") 
	private Long planLevel;
	
	// 专业领域的OBS_ID
	@Column(name="OBS_ID") 
	private String obsId;
	
	// 计划状态（未发布、预发布、已发布）
	@Column(name="STATUS_CODE") 
	private String statusCode;
	
	//车型ID
	@Column(name="PROGRAM_VEHICLE_ID") 
	private String programVehicleId;
	
	// 创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	// 创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	// 修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;

	// 修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;

	// 删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public Long getPlanLevel() {
		return planLevel;
	}

	public void setPlanLevel(Long planLevel) {
		this.planLevel = planLevel;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
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

}
