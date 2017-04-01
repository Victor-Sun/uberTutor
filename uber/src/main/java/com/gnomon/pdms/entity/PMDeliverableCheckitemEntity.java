package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_DELIVERABLE_CHECKITEM")
public class PMDeliverableCheckitemEntity extends StringIdEntity {
	
	// 序号
	@Column(name="SEQ") 
	private Long seq;
	
	// 检查项名称
	@Column(name="NAME") 
	private String name;
	
	// 单项通过要求及验收办法
	@Column(name="CHECK_REQUIREMENT") 
	private String checkRequirement;
	
	// 关联交付物ID
	@Column(name="DELIVERABLE_ID") 
	private String deliverableId;

	// 创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	// 创建时间
	@Column(name="CREATE_DATE") 
	private Date  createDate;
	
	// 修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	// 修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	// 删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCheckRequirement() {
		return checkRequirement;
	}

	public void setCheckRequirement(String checkRequirement) {
		this.checkRequirement = checkRequirement;
	}

	public String getDeliverableId() {
		return deliverableId;
	}

	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
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
