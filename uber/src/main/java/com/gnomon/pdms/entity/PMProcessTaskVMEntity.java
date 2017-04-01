package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_PROCESS_TASK")
public class PMProcessTaskVMEntity extends StringIdEntity {
	
	// 流程ID
	@Column(name="PROCESS_ID") 
	private Long processId;
	
	// 流程StepID
	@Column(name="STEP_ID") 
	private Long stepId;
	
	// 审批人
	@Column(name="TASK_OWNER") 
	private String taskOwner;
	
	// 角色ID
	@Column(name="ROLE_ID") 
	private String roleId;
	
	// 审批通过Comment
	@Column(name="OWNER_COMMENT") 
	private String ownerComment;
	
	// 审批未通过Comment
	@Column(name="RETURN_COMMENT") 
	private String returnComment;
	
	// 完成标志
	@Column(name="COMPLETE_FLAG") 
	private String completeFlag;
	
	// 完成时间
	@Column(name="COMPLETE_DATE") 
	private Date completeDate;
	
	// 创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	// 修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 任务信息
	@Column(name="TASK_MESSAGE") 
	private String taskMessage;
	
	// 审批人姓名
	@Column(name="TASK_OWNER_NAME") 
	private String taskOwnerName;
	
	// STEPNAME
	@Column(name="STEP_NAME") 
	private String stepName;

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOwnerComment() {
		return ownerComment;
	}

	public void setOwnerComment(String ownerComment) {
		this.ownerComment = ownerComment;
	}

	public String getReturnComment() {
		return returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	public String getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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

	public String getTaskMessage() {
		return taskMessage;
	}

	public void setTaskMessage(String taskMessage) {
		this.taskMessage = taskMessage;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
}
