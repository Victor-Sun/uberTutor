package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_IMS_ISSUE_NOTIFICATION")
public class ImsIssueMemberEntity extends StringIdEntity {

	@Column(name="ISSUE_ID") 
	private String issueId;
	
	@Column(name="MEMBER_ROLE_CODE") 
	private String memberRoleCode;

	@Column(name="MEMBER_USERID") 
	private Date memberUserid;
	
	@Column(name="CREATE_BY") 
	private String createBy;

	@Column(name="CREATE_DATE") 
	private Date createDate;

	@Column(name="UPDATE_BY") 
	private String updateBy;

	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	@Column(name="DELETE_BY") 
	private String deleteBy;

	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getMemberRoleCode() {
		return memberRoleCode;
	}

	public void setMemberRoleCode(String memberRoleCode) {
		this.memberRoleCode = memberRoleCode;
	}

	public Date getMemberUserid() {
		return memberUserid;
	}

	public void setMemberUserid(Date memberUserid) {
		this.memberUserid = memberUserid;
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

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
}
