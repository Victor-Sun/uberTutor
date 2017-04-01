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
@Table(name="PM_WORK_GROUP_MEMBER")
public class PMWorkGroupMemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_WORK_GROUP_MEMBER_SEQ")     
	@SequenceGenerator(name="PM_WORK_GROUP_MEMBER_SEQ", sequenceName="PM_WORK_GROUP_MEMBER_SEQ")
	private Long id;
	
	@Column(name="WORK_GROUP_ID")
	private Long workGroupId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
