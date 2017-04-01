package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_ISSUE_LAST_ACCESS")
public class VIssueLastAccessEntity extends StringIdEntity {

	@Column(name="USERID")
	private String userid;
	
	@Column(name="ACCESS_TIMES")
	private String accessTimes;
	
	@Column(name="LAST_ACCESS_DATE")
	private Date lastAccessDate;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="ISSUE_STATUS_CODE")
	private String issueStatusCode;
	
	@Column(name="ISSUE_STATUS")
	private String issueStatus;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="SUBMIT_USER")
	private String submitUser;
	
	@Column(name="SUBMIT_USER_NAME")
	private String submitUserName;
	
	@Column(name="OPEN_DATE")
	private Date openDate;
	
	@Column(name="ISSUE_SOURCE")
	private String issueSource;
	
	@Column(name="PROJECT_ID")
	private String projectId;
	
	@Column(name="PROJECT_NAME")
	private String projectName;
	
	@Column(name="SUB_PROJECT_CODE")
	private String subProjectCode;
	
	@Column(name="SUB_PROJECT_NAME")
	private String subProjectName;
	
	@Column(name="STAGE")
	private String stage;
	
	@Column(name="OCCURRENCE_DATE")
	private Date occurrenceDate;
	
	@Column(name="OCCURRENCE_SITE")
	private String occurrenceSite;
	
	@Column(name="TEST_TYPE")
	private String testType;
	
	@Column(name="SAMPLE_NUMBER")
	private String sampleNumber;
	
	@Column(name="TROUBLE_PART_MILEAGE")
	private String troublePartMileage;
	
	@Column(name="TEST_PROGRESS")
	private String testProgress;
	
	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="FIRST_CAUSE_ANALYSIS")
	private String firstCauseAnalysis;

	@Column(name="MEMO")
	private String memo;
	
	@Column(name="DEPT_ID")
	private String deptId;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="ISSUE_LEVEL_CODE")
	private String issueLevelCode;
	
	@Column(name="ISSUE_LEVEL")
	private String issueLevel;
	
	@Column(name="OWNER")
	private String owner;
	
	@Column(name="OWNER_NAME")
	private String ownerName;

	@Column(name="IS_ISSUE")
	private String isIssue;
	
	@Column(name="IS_ISSUE_REASON")
	private String isIssueReason;
	
	@Column(name="TEMP_ACTION")
	private String tempAction;
	
	@Column(name="ISSUE_TYPE")
	private String issueType;
	
	@Column(name="ISSUE_TYPE_ID")
	private String issueTypeId;
	
	@Column(name="EXPECTED_STAGE")
	private String expectedStage;
	
	@Column(name="EXPECTED_STAGE_ID")
	private String expectedStageId;
	
	@Column(name="EXPECTED_DATE")
	private Date expectedDate;
	
	@Column(name="ROOT_CAUSE")
	private String rootCause;
	
	@Column(name="IS_ACTION")
	private String isAction;
	
	@Column(name="PERM_ACTION")
	private String permAction;
	
	@Column(name="ISSUE_LIFECYCLE_CODE") 
	private String issueLifecycleCode;
	
	@Column(name="IS_MARK") 
	private String isMark;
	
	@Column(name="FORM_KEY") 
	private String formKey;
	
	@Column(name="RISK_LEVEL_CODE") 
	private String riskLevelCode;
	
	@Column(name="CREATE_BY") 
	private String createBy;

	@Column(name="CREATE_DATE") 
	private Date createDate;

	public String getIsMark() {
		return isMark;
	}

	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(String accessTimes) {
		this.accessTimes = accessTimes;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getIssueLevelCode() {
		return issueLevelCode;
	}

	public void setIssueLevelCode(String issueLevelCode) {
		this.issueLevelCode = issueLevelCode;
	}

	public String getIssueStatusCode() {
		return issueStatusCode;
	}

	public void setIssueStatusCode(String issueStatusCode) {
		this.issueStatusCode = issueStatusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getTempAction() {
		return tempAction;
	}

	public void setTempAction(String tempAction) {
		this.tempAction = tempAction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getIssueLifecycleCode() {
		return issueLifecycleCode;
	}

	public void setIssueLifecycleCode(String issueLifecycleCode) {
		this.issueLifecycleCode = issueLifecycleCode;
	}

	public Date getOccurrenceDate() {
		return occurrenceDate;
	}

	public void setOccurrenceDate(Date occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}

	public String getOccurrenceSite() {
		return occurrenceSite;
	}

	public void setOccurrenceSite(String occurrenceSite) {
		this.occurrenceSite = occurrenceSite;
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

	public String getTestProgress() {
		return testProgress;
	}

	public void setTestProgress(String testProgress) {
		this.testProgress = testProgress;
	}

	public String getFirstCauseAnalysis() {
		return firstCauseAnalysis;
	}

	public void setFirstCauseAnalysis(String firstCauseAnalysis) {
		this.firstCauseAnalysis = firstCauseAnalysis;
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

	public String getExpectedStageId() {
		return expectedStageId;
	}

	public void setExpectedStageId(String expectedStageId) {
		this.expectedStageId = expectedStageId;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getIsAction() {
		return isAction;
	}

	public void setIsAction(String isAction) {
		this.isAction = isAction;
	}

	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getIssueSource() {
		return issueSource;
	}

	public void setIssueSource(String issueSource) {
		this.issueSource = issueSource;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubProjectCode() {
		return subProjectCode;
	}

	public void setSubProjectCode(String subProjectCode) {
		this.subProjectCode = subProjectCode;
	}

	public String getSubProjectName() {
		return subProjectName;
	}

	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIssueLevel() {
		return issueLevel;
	}

	public void setIssueLevel(String issueLevel) {
		this.issueLevel = issueLevel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getExpectedStage() {
		return expectedStage;
	}

	public void setExpectedStage(String expectedStage) {
		this.expectedStage = expectedStage;
	}

	public String getPermAction() {
		return permAction;
	}

	public void setPermAction(String permAction) {
		this.permAction = permAction;
	}

	public String getRiskLevelCode() {
		return riskLevelCode;
	}

	public void setRiskLevelCode(String riskLevelCode) {
		this.riskLevelCode = riskLevelCode;
	}

}
