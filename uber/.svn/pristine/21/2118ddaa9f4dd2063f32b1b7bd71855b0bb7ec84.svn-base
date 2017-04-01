package com.gnomon.pdms.entity;

import java.io.Serializable;
import java.util.Date;
import com.gnomon.common.utils.DateUtils;


public class MyTaskInfo implements Serializable{
	
	private static final long serialVersionUID = 9140091242824447597L;

	//Issue Id
	private String issueId;
	
	//任务名称
	private String taskName;
	
	//任务类型
	private String taskCategory;
	
	//问题标题
	private String issueTitle;
	
	//问题状态
	private String issueStatus;
	
	//问题等级
	private String issueLevel;
	
	//所属项目
	private String project;
	
	//等待时间
	private String waitDays;
	
	//截止日期
	private String dueDate;
	
	//流程实例Id
	private String processInstanceId;
	
	//任务Id
	private String taskId;
	
	//表单标识
	private String formKey;
	
	//问题热点等级
	private String risk;
	
	//是否关注
	private String mark;
	
	//
	private String steps;
	
	// 问题编号
	private String code;
	
	// 当前任务负责人
	private String taskOwner;
	
	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	//开始日期
	private Date createDate;
	
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getIssueLevel() {
		return issueLevel;
	}

	public void setIssueLevel(String issueLevel) {
		this.issueLevel = issueLevel;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getWaitDays() {
		waitDays = String.valueOf(DateUtils.daysBetween(this.createDate, new Date()));
		return waitDays;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
