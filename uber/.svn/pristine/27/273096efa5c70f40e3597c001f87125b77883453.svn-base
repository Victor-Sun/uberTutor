package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name = "IMS_ISSUE_OPERATION_LOG")
public class ImsIssueOperationLogEntity extends StringIdEntity {
	//
	@Column(name = "ISSUE_ID")
	private String issueId;
	//处理人
	@Column(name = "OPERATION_USERID")
	private String operationUserid;
	//处理时间
	@Column(name = "OPERATION_DATE")
	private Date operationDate;
	//动作
	@Column(name = "ACTION")
	private String action;
	//
	@Column(name = "CREATE_BY")
	private String createBy;
	//
	@Column(name = "CREATE_DATE")
	private Date createDate;
	//
	@Column(name = "UPDATE_BY")
	private String updateBy;
	//
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getOperationUserid() {
		return operationUserid;
	}
	public void setOperationUserid(String operationUserid) {
		this.operationUserid = operationUserid;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
