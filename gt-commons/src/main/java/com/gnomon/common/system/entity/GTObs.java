package com.gnomon.common.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Access(AccessType.FIELD)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="GT_OBS")
public class GTObs extends StringIdEntity{
	
	@Column(name="OBS_NAME")
	private String obsName;
	@Column(name="OBS_DESC")
	private String obsDesc;
	@Column(name="PARENT_ID")
	private String parentId;
	@Column(name="LFT")
	private Long left;
	@Column(name="RGT")
	private Long right;
	public String getObsName() {
		return obsName;
	}
	public void setObsName(String obsName) {
		this.obsName = obsName;
	}
	public String getObsDesc() {
		return obsDesc;
	}
	public void setObsDesc(String obsDesc) {
		this.obsDesc = obsDesc;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Long getLeft() {
		return left;
	}
	public void setLeft(Long left) {
		this.left = left;
	}
	public Long getRight() {
		return right;
	}
	public void setRight(Long right) {
		this.right = right;
	}
	
	
	
	
}
