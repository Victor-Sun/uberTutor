package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="V_PM_PROGRAM_PLAN")
public class PMProgramPlanVMEntity extends StringIdEntity {

	// 项目ID
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	// 计划类型（M-主计划、D-专业领域计划）
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

	// 专业领域
	@Column(name="OBS_NAME") 
	private String obsName;

	// 计划状态名称
	@Column(name="PLAN_STATUS_NAME") 
	private String planStatusName;

	// 专业经理
	@Column(name="OBS_MANAGER") 
	private String obsManager;
	
	// 专业经理
	@Column(name="OBS_MANAGER_ID") 
	private String obsManagerId;

	// 计划等级名称
	@Column(name="PLAN_LEVEL_NAME") 
	private String planLevelName;
	
	// 排序
	@Column(name="LFT") 
	private Long lft;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getObsManagerId() {
		return obsManagerId;
	}

	public void setObsManagerId(String obsManagerId) {
		this.obsManagerId = obsManagerId;
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

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}

	public String getObsManager() {
		return obsManager;
	}

	public void setObsManager(String obsManager) {
		this.obsManager = obsManager;
	}

	public String getPlanLevelName() {
		return planLevelName;
	}

	public void setPlanLevelName(String planLevelName) {
		this.planLevelName = planLevelName;
	}

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}
	
	
}
