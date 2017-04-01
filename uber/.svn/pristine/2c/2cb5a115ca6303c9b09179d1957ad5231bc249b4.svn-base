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
@Table(name="IMS_ISSUE_SOURCE")
public class IssueSourceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="IMS_ISSUE_SOURCE_SEQ")     
	@SequenceGenerator(name="IMS_ISSUE_SOURCE_SEQ", sequenceName="IMS_ISSUE_SOURCE_SEQ")  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="ID") 
	private Long id;
	
	//阶段编码
	@Column(name="CODE") 
	private String code;

	//阶段名称
	@Column(name="NAME") 
	private String name;

	//牵头科室ID
	@Column(name="LEAD_DEPT_ID") 
	private String leadDeptId;

	//
	@Column(name="CREATE_BY") 
	private String createBy;

	//
	@Column(name="CREATE_DATE") 
	private Date createDate;

	//
	@Column(name="UPDATE_BY") 
	private String updateBy;

	//
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

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

	public String getLeadDeptId() {
		return leadDeptId;
	}

	public void setLeadDeptId(String leadDeptId) {
		this.leadDeptId = leadDeptId;
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
