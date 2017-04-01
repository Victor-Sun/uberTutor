package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PM_WORK_ORDER")
public class WorkOrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_WORK_ORDER_SEQ")     
	@SequenceGenerator(name="PM_WORK_ORDER_SEQ", sequenceName="PM_WORK_ORDER_SEQ")
	private Long id;
	
	@Column(name="SOURCE_DEPT") 
	private String sourceDept;	
	
	@Column(name="TARGET_DEPT") 
	private String targetDept;	
	
	@Column(name="PROGRAM_NAME") 
	private String programName;	
	
	@Column(name="WORK_TYPE") 
	private Long workType;	
	
	@Column(name="TITLE") 
	private String title;	
	
	@Column(name="CREATE_BY") 
	private String createBy;	
	
	@Column(name="CREATE_DATE") 
	private Date createDate;	
	
	@Column(name="WORK_DESCRIPTION") 
	private String workDescription;	
	
	@Column(name="DUE_DATE") 
	private Date dueDate;	
	
	@Column(name="CONTACT_NAME") 
	private String contactName;	
	
	@Column(name="CONTACT_PHONE") 
	private String contactPhone;	
	
	@Column(name="REVIEW_BY_STRING") 
	private String reviewByString;

	@Column(name="REQ_REVIEW_DATE") 
	private Date reqReviewDate;
	
	@Column(name="APPROVE_BY_STRING") 
	private String approveByString;
	
	@Column(name="ONLINE_MODE") 
	private String onlineMode;
	
	@Column(name="WO_AGENT") 
	private String woAgent;
	
	@Column(name="PM") 
	private String pm;
	
	@Column(name="ENG") 
	private String eng;
	
	
	@Column(name="PG_DIRECTOR") 
	private String pgDirector;
	
	
	@Column(name="LINE_MGR") 
	private String lineMgr;
	
	@Column(name="DEPT_MGR") 
	private String deptMgr;
	
	@Column(name="DEPT_DIRECTOR") 
	private String deptDirector;
	
	
	@Column(name="REQ_APPROVE_DATE") 
	private Date reqApproveDate;
	
	@Column(name="RESPONSE_DESCRIPTION") 
	private String responseDescription;
	
	@Column(name="RESPONSE_CREATE_DATE") 
	private Date responseCreateDate;
	
	@Column(name="RESPONSE_CONTACT_NAME") 
	private String responseContactName;
	
	@Column(name="RESPONSE_CONTACT_PHONE") 
	private String responseContactPhone;
	
	@Column(name="RESPONSE_REVIEW_DATE") 
	private Date responseReviewDate;
	
	@Column(name="RESPONSE_REVIEW_BY") 
	private String responseReviewBy;
	
	@Column(name="RESPONSE_APPROVE_DATE") 
	private Date responseApproveDate;
	
	@Column(name="RESPONSE_APPROVE_BY") 
	private String responseApproveBy;
	
	
	@Column(name="VALIDATE_DESCRIPTION") 
	private String validateDescription;
	
	
	@Column(name="VALIDATE_BY") 
	private String validateBy;
	
	@Column(name="VALIDATE_DATE") 
	private Date validateDate;
	
	@Column(name="PROCESS_INSTANCE_ID") 
	private Long processInstanceId;
	
	@Column(name="STATUS_CODE") 
	private String statusCode;
	
	@Column(name="IS_PROJECT_TYPE") 
	private String isProjectType;
	
	@Transient
	private String action;
	
	@Transient
	private String ownerCommont;
	
	@Transient
	private String returnComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOwnerCommont() {
		return ownerCommont;
	}

	public void setOwnerCommont(String ownerCommont) {
		this.ownerCommont = ownerCommont;
	}

	public String getPgDirector() {
		return pgDirector;
	}

	public void setPgDirector(String pgDirector) {
		this.pgDirector = pgDirector;
	}

	public String getOnlineMode() {
		return onlineMode;
	}

	public void setOnlineMode(String onlineMode) {
		this.onlineMode = onlineMode;
	}

	public String getWoAgent() {
		return woAgent;
	}

	public void setWoAgent(String woAgent) {
		this.woAgent = woAgent;
	}

	public String getEng() {
		return eng;
	}

	public void setEng(String eng) {
		this.eng = eng;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getLineMgr() {
		return lineMgr;
	}

	public void setLineMgr(String lineMgr) {
		this.lineMgr = lineMgr;
	}

	public String getDeptMgr() {
		return deptMgr;
	}

	public void setDeptMgr(String deptMgr) {
		this.deptMgr = deptMgr;
	}

	public String getDeptDirector() {
		return deptDirector;
	}

	public void setDeptDirector(String deptDirector) {
		this.deptDirector = deptDirector;
	}

	public String getReturnComment() {
		return returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	public String getSourceDept() {
		return sourceDept;
	}

	public String getIsProjectType() {
		return isProjectType;
	}

	public void setIsProjectType(String isProjectType) {
		this.isProjectType = isProjectType;
	}

	public void setSourceDept(String sourceDept) {
		this.sourceDept = sourceDept;
	}

	public String getTargetDept() {
		return targetDept;
	}

	public void setTargetDept(String targetDept) {
		this.targetDept = targetDept;
	}

	

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public Date getReqReviewDate() {
		return reqReviewDate;
	}

	public void setReqReviewDate(Date reqReviewDate) {
		this.reqReviewDate = reqReviewDate;
	}


	public String getReviewByString() {
		return reviewByString;
	}

	public void setReviewByString(String reviewByString) {
		this.reviewByString = reviewByString;
	}

	public String getApproveByString() {
		return approveByString;
	}

	public void setApproveByString(String approveByString) {
		this.approveByString = approveByString;
	}

	public Date getReqApproveDate() {
		return reqApproveDate;
	}

	public void setReqApproveDate(Date reqApproveDate) {
		this.reqApproveDate = reqApproveDate;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Date getResponseCreateDate() {
		return responseCreateDate;
	}

	public void setResponseCreateDate(Date responseCreateDate) {
		this.responseCreateDate = responseCreateDate;
	}


	public String getResponseContactName() {
		return responseContactName;
	}

	public void setResponseContactName(String responseContactName) {
		this.responseContactName = responseContactName;
	}

	public String getResponseContactPhone() {
		return responseContactPhone;
	}

	public void setResponseContactPhone(String responseContactPhone) {
		this.responseContactPhone = responseContactPhone;
	}

	public Date getResponseReviewDate() {
		return responseReviewDate;
	}

	public void setResponseReviewDate(Date responseReviewDate) {
		this.responseReviewDate = responseReviewDate;
	}

	public String getResponseReviewBy() {
		return responseReviewBy;
	}

	public void setResponseReviewBy(String responseReviewBy) {
		this.responseReviewBy = responseReviewBy;
	}

	public Date getResponseApproveDate() {
		return responseApproveDate;
	}

	public void setResponseApproveDate(Date responseApproveDate) {
		this.responseApproveDate = responseApproveDate;
	}

	public String getResponseApproveBy() {
		return responseApproveBy;
	}

	public void setResponseApproveBy(String responseApproveBy) {
		this.responseApproveBy = responseApproveBy;
	}

	public String getValidateDescription() {
		return validateDescription;
	}

	public void setValidateDescription(String validateDescription) {
		this.validateDescription = validateDescription;
	}

	public String getValidateBy() {
		return validateBy;
	}

	public void setValidateBy(String validateBy) {
		this.validateBy = validateBy;
	}

	public Date getValidateDate() {
		return validateDate;
	}

	public void setValidateDate(Date validateDate) {
		this.validateDate = validateDate;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
