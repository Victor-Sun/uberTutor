package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="V_IMS_ISSUE_HISTORY")
public class VImsIssueHistoryEntity extends StringIdEntity{

	//
	@Column(name="ISSUE_ID") 
	private String issueId;
	//
	@Column(name="CODE") 
	private String code;
	//
	@Column(name="ISSUE_STATUS_CODE") 
	private String issueStatusCode;
	//
	@Column(name="TITLE") 
	private String title;
	//
	@Column(name="SUBMIT_USER") 
	private String submitUser;
	//
	@Column(name="SUBMIT_USER_NAME") 
	private String submitUserName;
	//
	@Column(name="OPEN_DATE") 
	private Date openDate;
	//
	@Column(name="ISSUE_SOURCE") 
	private String issueSource;
	//
	@Column(name="PROJECT_ID") 
	private String projectId;
	//
	@Column(name="SUB_PROJECT_ID") 
	private String subProjectId;
	//
	@Column(name="STAGE") 
	private String stage;
	//
	@Column(name="OCCURRENCE_DATE") 
	private Date occurrenceDate;
	//
	@Column(name="OCCURRENCE_SITE") 
	private String occurrenceSite;
	//
	@Column(name="TEST_TYPE") 
	private String textType;
	//
	@Column(name="SAMPLE_NUMBER") 
	private String sampleNumber;
	//
	@Column(name="TROUBLE_PART_MILEAGE") 
	private String troublePartMileage;
	//
	@Column(name="TEST_PROGRESS") 
	private String textProgress;
	//
	@Column(name="DESCRIPTION") 
	private String description;
	//
	@Column(name="DISPOSAL_MEASURES") 
	private String disposalMeasures;
	//
	@Column(name="FIRST_CAUSE_ANALYSIS") 
	private String firstCauseAnalysis;
	//
	@Column(name="MEMO") 
	private String memo;
	//
	@Column(name="DEPT_ID") 
	private String deptId;
	//
	@Column(name="ISSUE_LEVEL") 
	private String issueLevel;
	//
	@Column(name="OWNER") 
	private String owner;
	//
	@Column(name="IS_ISSUE") 
	private String isIssue;
	//
	@Column(name="IS_ISSUE_REASON") 
	private String isIssueReason;
	//
	@Column(name="TEMP_ACTION") 
	private String tempAction;
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIssueStatusCode() {
		return issueStatusCode;
	}
	public void setIssueStatusCode(String issueStatusCode) {
		this.issueStatusCode = issueStatusCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
	public String getSubmitUserName() {
		return submitUserName;
	}
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getOccurrenceDate() {
		return occurrenceDate;
	}
	public void setOccurrenceDate(Date occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}
	public String getIssueSource() {
		return issueSource;
	}
	public void setIssueSource(String issueSource) {
		this.issueSource = issueSource;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSubProjectId() {
		return subProjectId;
	}
	public void setSubProjectId(String subProjectId) {
		this.subProjectId = subProjectId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getOccurrenceSite() {
		return occurrenceSite;
	}
	public void setOccurrenceSite(String occurrenceSite) {
		this.occurrenceSite = occurrenceSite;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public String getSampleNumber() {
		return sampleNumber;
	}
	public void setSampleNumber(String sampleNumber) {
		this.sampleNumber = sampleNumber;
	}
	public String getTroublePartMileage() {
		return troublePartMileage;
	}
	public void setTroublePartMileage(String troublePartMileage) {
		this.troublePartMileage = troublePartMileage;
	}
	public String getTextProgress() {
		return textProgress;
	}
	public void setTextProgress(String textProgress) {
		this.textProgress = textProgress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDisposalMeasures() {
		return disposalMeasures;
	}
	public void setDisposalMeasures(String disposalMeasures) {
		this.disposalMeasures = disposalMeasures;
	}
	public String getFirstCauseAnalysis() {
		return firstCauseAnalysis;
	}
	public void setFirstCauseAnalysis(String firstCauseAnalysis) {
		this.firstCauseAnalysis = firstCauseAnalysis;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getIssueLevel() {
		return issueLevel;
	}
	public void setIssueLevel(String issueLevel) {
		this.issueLevel = issueLevel;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getIsIssue() {
		return isIssue;
	}
	public void setIsIssue(String isIssue) {
		this.isIssue = isIssue;
	}
	public String getIsIssueReason() {
		return isIssueReason;
	}
	public void setIsIssueReason(String isIssueReason) {
		this.isIssueReason = isIssueReason;
	}
	public String getTempAction() {
		return tempAction;
	}
	public void setTempAction(String tempAction) {
		this.tempAction = tempAction;
	}
}
