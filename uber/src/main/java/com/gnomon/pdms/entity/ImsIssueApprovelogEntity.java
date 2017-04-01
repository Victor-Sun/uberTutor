package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="IMS_ISSUE_APPROVELOG")
public class ImsIssueApprovelogEntity extends StringIdEntity {

	//
	@Column(name="ISSUE_ID") 
	private String issueId;

	//活动名称
	@Column(name="STEP_NAME") 
	private String stepName;

	//动作
	@Column(name="DECISION") 
	private String decision;

	//备注
	@Column(name="NOTE") 
	private String note;

	//操作人
	@Column(name="APPROVE_BY") 
	private String approveBy;

	//操作时间
	@Column(name="APPROVE_DATE") 
	private Date approveDate;

	//是否已提交
	@Column(name="IS_SUBMITTED") 
	private String isSubmitted;

	//任务实例ID
	@Column(name="TASK_ID") 
	private String taskId;

	//ACT_ID_
	@Column(name="STEP_ID") 
	private String stepId;

	//
	@Column(name="PROCESS_INSTANCE_ID") 
	private String processInstanceId;

	//
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//
	@Column(name="CREATE_DATE") 
	private Date  createDate;
	
	//
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getIsSubmitted() {
		return isSubmitted;
	}

	public void setIsSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

}
