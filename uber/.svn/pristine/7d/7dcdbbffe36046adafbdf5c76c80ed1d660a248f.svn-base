package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="IMS_ISSUE_MERGE")
public class MergeInfoEntity extends StringIdEntity {

	//主问题ID
	@Column(name="PRIMARY_ISSUE_ID") 
	private String primaryIssueId;
	//被合并的问题ID
	@Column(name="ISSUE_ID") 
	private String issueId;
	//被合并的问题编号
	@Column(name="CODE") 
	private String code;
	//被合并的问题标题
	@Column(name="TITLE") 
	private String title;
	//被合并的问题提出人
	@Column(name="SUBMIT_USER_NAME") 
	private String submitUserName;
	//被合并的问题提出时间
	@Column(name="OPEN_DATE") 
	private Date openDate;
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
	public String getPrimaryIssueId() {
		return primaryIssueId;
	}
	public void setPrimaryIssueId(String primaryIssueId) {
		this.primaryIssueId = primaryIssueId;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
