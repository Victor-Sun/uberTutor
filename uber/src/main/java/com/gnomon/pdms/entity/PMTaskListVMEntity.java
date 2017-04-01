package com.gnomon.pdms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_TASK_LIST")
public class PMTaskListVMEntity extends StringIdEntity {
	
	//上级任务ID
	@Column(name="PARENT_ID")
	private String parentId;
	
	//任务名称
	@Column(name="TASK_NAME") 
	private String taskName;

	//所属项目ID
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	//任务描述
	@Column(name="TASK_DESCRIPTION") 
	private String taskDescription;
	
	//任务负责人Id
	@Column(name="TASK_OWNER") 
	private String taskOwner;
	
	//计划开始日期
	@Column(name="PLANNED_START_DATE") 
	private Date plannedStartDate;
	
	//计划完成日期
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;
	
	//计划工时
	@Column(name="PLANNED_MANHOUR") 
	private BigDecimal plannedManhour;
	
	//实际开始日期
	@Column(name="ACTUAL_START_DATE") 
	private Date actualStartDate;
	
	//实际完成日期
	@Column(name="ACTUAL_FINISH_DATE") 
	private Date actualFinishDate;
	
	//实际工时
	@Column(name="ACTUAL_MANHOUR") 
	private BigDecimal actualManhour;
	
	//完成率
	@Column(name="PERCENT_COMPLETE") 
	private BigDecimal percentComplete;
	
	//任务状态
	@Column(name="TASK_STATUS_CODE") 
	private String taskStatusCode;
	
	//任务进展状态代码
	@Column(name="TASK_PROGRESS_STATUS_CODE") 
	private String taskProgressStatusCode;
	
	//任务来源
	@Column(name="TASK_ORIGIN") 
	private String taskOrigin;
	
	//任务类型
	@Column(name="TASK_TYPE_CODE") 
	private String taskTypeCode;
	
	//计划层级
	@Column(name="PLAN_LEVEL") 
	private Integer planLevel;
	
	//关联任务ID
	@Column(name="RELATION_TASK_ID") 
	private String relationTaskId;
	
	//后置阀门ID
	@Column(name="GATE_ID") 
	private String gateId;
	
	//排序字段
	@Column(name="LFT") 
	private Long lft;
	
	//排序字段
	@Column(name="RGT") 
	private Long rgt;
	
	// 是否显示在时程表上
	@Column(name="IS_SHOW") 
	private String isShow;
	
	//项目组织ID
	@Column(name="OBS_ID") 
	private String obsId;
	
	// 时程表代码（主计划、专业领域计划、其他）
	@Column(name="TIMESHEET_TYPE_CODE") 
	private String timesheetTypeCode;
	
	// 任务优先级代码
	@Column(name="TASK_PRIORITY_CODE") 
	private String taskPriorityCode;	
	
	// 延期天数
	@Column(name="DELAY_DAYS") 
	private Long DelayDays;
	
	// 阀门状态
	@Column(name="GATE_STATUS_CODE") 
	private String gateStatusCode;
	
	// 备注
	@Column(name="MEMO") 
	private String memo;
	
	// 阀门是否通过
	@Column(name="IS_GATE_PASS") 
	private String isGatePass;	
	
	// 发布人
	@Column(name="PUBLISH_BY") 
	private String publishBy;	
	
	//车型ID
	@Column(name="PROGRAM_VEHICLE_ID") 
	private String programVehicleId;
	
	//最新汇报时间
	@Column(name="LAST_REPORT_DATE") 
	private Date lastReportDate;
	
	//锁定日期
	@Column(name="LOCK_DATE") 
	private Date lockDate;
	
	//锁定人
	@Column(name="LOCK_BY") 
	private String lockBy;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	//修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	//删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	//删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;	
	
	//任务负责人姓名
	@Column(name="TASK_OWNER_NAME") 
	private String taskOwnerName;
	
	//关联交付物名称
	@Column(name="TASK_DELIVERABLE_NAME") 
	private String taskDeliverableName;
	
	//车型名称
	@Column(name="VEHICLE_NAME") 
	private String vehicleName;
	
	// 专业领域名称
	@Column(name="FUNCTION_OBS_NAME") 
	private String functionObsName;
	
	// 专业领域ID
	@Column(name="FUNCTION_OBS_ID") 
	private String functionObsId;
	
	// OBS名称
	@Column(name="OBS_NAME") 
	private String obsName;
	
	// 后置阀门编码
	@Column(name="GATE_CODE") 
	private String gateCode;
	
	// 后置阀门名称
	@Column(name="GATE_NAME") 
	private String gateName;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskOwner() {
		return taskOwner;
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

	public BigDecimal getPlannedManhour() {
		return plannedManhour;
	}

	public void setPlannedManhour(BigDecimal plannedManhour) {
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

	public BigDecimal getActualManhour() {
		return actualManhour;
	}

	public void setActualManhour(BigDecimal actualManhour) {
		this.actualManhour = actualManhour;
	}

	public BigDecimal getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(BigDecimal percentComplete) {
		this.percentComplete = percentComplete;
	}

	public String getTaskStatusCode() {
		return taskStatusCode;
	}

	public void setTaskStatusCode(String taskStatusCode) {
		this.taskStatusCode = taskStatusCode;
	}

	public String getTaskProgressStatusCode() {
		return taskProgressStatusCode;
	}

	public void setTaskProgressStatusCode(String taskProgressStatusCode) {
		this.taskProgressStatusCode = taskProgressStatusCode;
	}

	public String getTaskOrigin() {
		return taskOrigin;
	}

	public void setTaskOrigin(String taskOrigin) {
		this.taskOrigin = taskOrigin;
	}

	public String getTaskTypeCode() {
		return taskTypeCode;
	}

	public void setTaskTypeCode(String taskTypeCode) {
		this.taskTypeCode = taskTypeCode;
	}

	public Integer getPlanLevel() {
		return planLevel;
	}

	public void setPlanLevel(Integer planLevel) {
		this.planLevel = planLevel;
	}

	public String getRelationTaskId() {
		return relationTaskId;
	}

	public void setRelationTaskId(String relationTaskId) {
		this.relationTaskId = relationTaskId;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getTimesheetTypeCode() {
		return timesheetTypeCode;
	}

	public void setTimesheetTypeCode(String timesheetTypeCode) {
		this.timesheetTypeCode = timesheetTypeCode;
	}

	public String getTaskPriorityCode() {
		return taskPriorityCode;
	}

	public void setTaskPriorityCode(String taskPriorityCode) {
		this.taskPriorityCode = taskPriorityCode;
	}

	public Long getDelayDays() {
		return DelayDays;
	}

	public void setDelayDays(Long delayDays) {
		DelayDays = delayDays;
	}

	public String getGateStatusCode() {
		return gateStatusCode;
	}

	public void setGateStatusCode(String gateStatusCode) {
		this.gateStatusCode = gateStatusCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsGatePass() {
		return isGatePass;
	}

	public void setIsGatePass(String isGatePass) {
		this.isGatePass = isGatePass;
	}

	public String getPublishBy() {
		return publishBy;
	}

	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public Date getLastReportDate() {
		return lastReportDate;
	}

	public void setLastReportDate(Date lastReportDate) {
		this.lastReportDate = lastReportDate;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public String getLockBy() {
		return lockBy;
	}

	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
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

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getTaskDeliverableName() {
		return taskDeliverableName;
	}

	public void setTaskDeliverableName(String taskDeliverableName) {
		this.taskDeliverableName = taskDeliverableName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getFunctionObsName() {
		return functionObsName;
	}

	public void setFunctionObsName(String functionObsName) {
		this.functionObsName = functionObsName;
	}

	public String getFunctionObsId() {
		return functionObsId;
	}

	public void setFunctionObsId(String functionObsId) {
		this.functionObsId = functionObsId;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getGateCode() {
		return gateCode;
	}

	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}

	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
}
