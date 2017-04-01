package com.gnomon.pdms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="V_PM_TASK_TODOLIST")
public class PMTaskTodoListVMEntity extends StringIdEntity {

	//上级任务ID
	@Column(name="TASK_ID") 
	private String taskId;
	
	//任务名称
	@Column(name="TASK_NAME") 
	private String taskName;

	//所属项目ID
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	//任务描述
	@Column(name="TASK_DESCRIPTION") 
	private String taskDescription;
	
	//任务负责人
	@Column(name="TASK_OWNER") 
	private String taskOwner;
	
	//计划开始日期
	@Column(name="PLANNED_START_DATE") 
	private Date plannedStartDate;
	
	//计划完成日期
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;
	
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

	// 进展状态代码
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
	private Long planLevel;
	
	// 关联任务ID
	@Column(name="RELATION_TASK_ID") 
	private String relationTaskId;
	
	// 后置阀门ID
	@Column(name="GATE_ID") 
	private String gateId;
	
	//排序字段
	@Column(name="LFT") 
	private Long lft;
	
	//排序字段
	@Column(name="RGT") 
	private Long rgt;

	@Column(name="TASK_PRIORITY_CODE") 
	private String taskPriorityCode;

	@Column(name="DELAY_DAYS") 
	private Long delayDays;

	@Column(name="GATE_STATUS_CODE") 
	private String gateStatusCode;

	@Column(name="MEMO") 
	private String memo;
	
	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 最新更新时间
	@Column(name="LAST_REPORT_DATE")
	private Date lastReportDate;
	
	@Column(name="PROGRAM_CODE") 
	private String programCode;
	
	@Column(name="TASK_OWNER_NAME") 
	private String taskOwnerName;

	@Column(name="PUBLISH_BY_NAME") 
	private String publishByName;
	
	@Column(name="ATT_CNT") 
	private Long attCnt;
	
	@Column(name="IS_REPORTED") 
	private String isReported;
	
	@Column(name="TASK_TYPE_NAME") 
	private String taskTypeName;
	
	@Column(name="TASK_PRIORITY_NAME") 
	private String taskPriorityName;

	@Column(name="TASK_STATUS_NAME") 
	private String taskStatusName;

	@Column(name="OBS_NAME") 
	private String obsName;

	@Column(name="PROGRAM_VEHICLE_NAME") 
	private String programVehicleName;

	@Column(name="PROFESSIONAL_FIELD_NAME") 
	private String professionalFieldName;
	
	@Column(name="CURRENT_STEP_ID") 
	private Long currentStepId;
	
	@Column(name="PROCESS_STEP_ID") 
	private Long processStepId;
	
	@Column(name="PROCESS_TASK_OWNER") 
	private String processTaskOwner;
	
	@Column(name="PROCESS_ID") 
	private Long processId;
	
	@Column(name="PROCESS_CODE") 
	private String processCode;
	
	@Column(name="PROCESS_STATUS") 
	private String processStatus;
		
	@Column(name="DELIVERABLE_STATUS") 
	private String deliverableStatus;
	
	@Column(name="DELIVERABLE_ID") 
	private String deliverableId;
	
	@Column(name="PROCESS_TASK_PROGRESS_STATUS") 
	private String processTaskProgressStatus;
	
	@Column(name="COMPLETE_FLAG") 
	private String completeFlag;
	
	@Column(name="TASK_PROGRESS_CREATE_DATE") 
	private Date taskProgressCreateDate;

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

	public Long getPlanLevel() {
		return planLevel;
	}

	public void setPlanLevel(Long planLevel) {
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

	public String getTaskPriorityCode() {
		return taskPriorityCode;
	}

	public void setTaskPriorityCode(String taskPriorityCode) {
		this.taskPriorityCode = taskPriorityCode;
	}

	public Long getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(Long delayDays) {
		this.delayDays = delayDays;
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

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getPublishByName() {
		return publishByName;
	}

	public void setPublishByName(String publishByName) {
		this.publishByName = publishByName;
	}

	public Long getAttCnt() {
		return attCnt;
	}

	public void setAttCnt(Long attCnt) {
		this.attCnt = attCnt;
	}

	public String getIsReported() {
		return isReported;
	}

	public void setIsReported(String isReported) {
		this.isReported = isReported;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public String getTaskPriorityName() {
		return taskPriorityName;
	}

	public void setTaskPriorityName(String taskPriorityName) {
		this.taskPriorityName = taskPriorityName;
	}

	public String getTaskStatusName() {
		return taskStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.taskStatusName = taskStatusName;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getProgramVehicleName() {
		return programVehicleName;
	}

	public void setProgramVehicleName(String programVehicleName) {
		this.programVehicleName = programVehicleName;
	}

	public String getProfessionalFieldName() {
		return professionalFieldName;
	}

	public void setProfessionalFieldName(String professionalFieldName) {
		this.professionalFieldName = professionalFieldName;
	}

	public Long getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(Long currentStepId) {
		this.currentStepId = currentStepId;
	}

	public Long getProcessStepId() {
		return processStepId;
	}

	public void setProcessStepId(Long processStepId) {
		this.processStepId = processStepId;
	}

	public String getProcessTaskOwner() {
		return processTaskOwner;
	}

	public void setProcessTaskOwner(String processTaskOwner) {
		this.processTaskOwner = processTaskOwner;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public BigDecimal getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(BigDecimal percentComplete) {
		this.percentComplete = percentComplete;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getLastReportDate() {
		return lastReportDate;
	}

	public void setLastReportDate(Date lastReportDate) {
		this.lastReportDate = lastReportDate;
	}

	public String getDeliverableStatus() {
		return deliverableStatus;
	}

	public void setDeliverableStatus(String deliverableStatus) {
		this.deliverableStatus = deliverableStatus;
	}

	public String getDeliverableId() {
		return deliverableId;
	}

	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}

	public String getProcessTaskProgressStatus() {
		return processTaskProgressStatus;
	}

	public void setProcessTaskProgressStatus(String processTaskProgressStatus) {
		this.processTaskProgressStatus = processTaskProgressStatus;
	}

	public String getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}

	public Date getTaskProgressCreateDate() {
		return taskProgressCreateDate;
	}

	public void setTaskProgressCreateDate(Date taskProgressCreateDate) {
		this.taskProgressCreateDate = taskProgressCreateDate;
	}
}
