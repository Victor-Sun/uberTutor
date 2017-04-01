package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_IMS_ISSUE_NOTIFICATION")
public class VImsIssueNotificationEntity extends StringIdEntity {

	//问题ID
	@Column(name="ISSUE_ID") 
	private String issueId;

	//知会的人员
	@Column(name="MEMBER_ROLE_CODE") 
	private String memberRoleCode;

	//
	@Column(name="MEMBER_USERID") 
	private String memberUserid;
	
	//
	@Column(name="IS_DELETE") 
	private String isDelete;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;

	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;

	//修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;

	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	//删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;

	//删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	//修改人
	@Column(name="TO_USER_NAME") 
	private String toUserName;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
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

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMemberRoleCode() {
		return memberRoleCode;
	}

	public void setMemberRoleCode(String memberRoleCode) {
		this.memberRoleCode = memberRoleCode;
	}

	public String getMemberUserid() {
		return memberUserid;
	}

	public void setMemberUserid(String memberUserid) {
		this.memberUserid = memberUserid;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
