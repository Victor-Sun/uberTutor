package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="IMS_LAST_ACCESS_LOG")
public class ImsLastAccessLogEntity extends StringIdEntity {
	
	public final static String OBJECT_TYPE_ISSUE = "OBJECT_TYPE_ISSUE";
	
	public final static String OBJECT_TYPE_OPEN_ISSUE = "OBJECT_TYPE_OPEN_ISSUE";

	//访问对象代码
	@Column(name="OBJECT_TYPE_CODE")
	private String objectTypeCode;

	//用户ID
	@Column(name="USERID")
	private String userid;

	//访问对象ID
	@Column(name="OBJECT_ID")
	private String objectId;

	//访问次数
	@Column(name="ACCESS_TIMES")
	private Long accessTimes;

	//最后访问时间
	@Column(name="LAST_ACCESS_DATE")
	private Date lastAccessDate;
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
	//
	@Column(name="DELETE_BY") 
	private String deleteBy;
	//
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	public String getObjectTypeCode() {
		return objectTypeCode;
	}
	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Long getAccessTimes() {
		return accessTimes;
	}
	public void setAccessTimes(Long accessTimes) {
		this.accessTimes = accessTimes;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
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
