package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="PM_DEPT_ISSUE_KB")
public class PMDeptIssueKBEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_DEPT_ISSUE_KB_SEQ")     
	@SequenceGenerator(name="PM_DEPT_ISSUE_KB_SEQ", sequenceName="PM_DEPT_ISSUE_KB_SEQ")
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="ORIG_ISSUE_ID") 
	private Long origIssueId;

	@Column(name="TITLE") 
	private String title;

	@Column(name="ISSUE_DESCRIPTION") 
	private String issueDescription;

	@Column(name="ISSUE_CAUSE") 
	private String issueCause;

	@Column(name="ISSUE_RESOLUTION") 
	private String issueResolution;

	@Column(name="OPERATOR_ID") 
	private String operatorId;
	
	@Column(name="CREATE_DATE") 
	private Date createDate;

	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	public Long getOrigIssueId() {
		return origIssueId;
	}

	public void setOrigIssueId(Long origIssueId) {
		this.origIssueId = origIssueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getIssueCause() {
		return issueCause;
	}

	public void setIssueCause(String issueCause) {
		this.issueCause = issueCause;
	}

	public String getIssueResolution() {
		return issueResolution;
	}

	public void setIssueResolution(String issueResolution) {
		this.issueResolution = issueResolution;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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
}
