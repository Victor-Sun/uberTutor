package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="IMS_ISSUE_NOTIFICATION")
public class ImsIssueNotificationEntity extends StringIdEntity {

	//问题ID
	@Column(name="ISSUE_ID") 
	private String issueId;

	//知会的人员USERID
	@Column(name="FROM_USERID") 
	private String fromUserid;

	//被知会的人员USERID
	@Column(name="TO_USERID") 
	private String toUserid;

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

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getFromUserid() {
		return fromUserid;
	}

	public void setFromUserid(String fromUserid) {
		this.fromUserid = fromUserid;
	}

	public String getToUserid() {
		return toUserid;
	}

	public void setToUserid(String toUserid) {
		this.toUserid = toUserid;
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
