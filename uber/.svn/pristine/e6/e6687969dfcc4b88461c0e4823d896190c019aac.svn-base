package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="PM_ISSUE_PROGRESS")
public class IssueProgressEntity extends StringIdEntity {

	//问题ID
	@Column(name="ISSUE_ID") 
	private String issueId;

	//完成状态
	@Column(name="DESCRIPTION") 
	private String description;

	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;

	//创建人
	@Column(name="USER_ID") 
	private String userId;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
