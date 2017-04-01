package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMS_CODE_TABLE")
public class ImsCodeTableEntity {

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
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

	//代码类型
	@Column(name="CODE_TYPE") 
	private String codeType;

	//代码
	@Id
	@Column(name="CODE") 
	private String code;
	
	//名称
	@Column(name="NAME") 
	private String name;
	
	//描述
	@Column(name="DESCRIPTION") 
	private String description;
	
	//顺序
	@Column(name="SEQ") 
	private Long seq;

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

}
