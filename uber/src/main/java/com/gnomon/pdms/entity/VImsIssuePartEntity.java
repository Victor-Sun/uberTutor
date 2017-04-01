package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_IMS_ISSUE_PART")
public class VImsIssuePartEntity extends StringIdEntity {

	//
	@Column(name="ISSUE_ID") 
	private String issueId;
	//顺序
	@Column(name="SEQ") 
	private Long seq;
	//
	@Column(name="PART_NUMBER") 
	private String partNumber;
	//
	@Column(name="TROUBLE_PART_NUMBER") 
	private String troublePartNumber;
	//
	@Column(name="PART_NUMBER_CODE") 
	private String partNumberCode;
	//
	@Column(name="PART_NAME") 
	private String partName;
	//
	@Column(name="PART_STATUS") 
	private String partStatus;
	//
	@Column(name="PART_STATUS_NAME") 
	private String partStatusName;
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

	public String getTroublePartNumber() {
		return troublePartNumber;
	}

	public void setTroublePartNumber(String troublePartNumber) {
		this.troublePartNumber = troublePartNumber;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartNumberCode() {
		return partNumberCode;
	}

	public void setPartNumberCode(String partNumberCode) {
		this.partNumberCode = partNumberCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	public String getPartStatusName() {
		return partStatusName;
	}

	public void setPartStatusName(String partStatusName) {
		this.partStatusName = partStatusName;
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
