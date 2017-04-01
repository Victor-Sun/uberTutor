package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_VEHICLE_TYPE")
public class VehicleTypeEntity extends StringIdEntity {
	
	//名称
	@Column(name="NAME")
	private String name;

	//描述
	@Column(name="DESCRIPTION")
	private String description;
	
	//创建人
	@Column(name="CREATE_BY")
	private String createBy;
	
	//创建时间
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	//修改人
	@Column(name="UPDATE_BY")
	private Date updateBy;
	
	//修改时间
	@Column(name="UPDATE_DATE")
	private Date updateDate;
	
	//顺序
	@Column(name="SEQ")
	private Long seq;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Date updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
}
