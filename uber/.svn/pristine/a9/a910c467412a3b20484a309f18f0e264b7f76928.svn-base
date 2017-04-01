package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
//对策决定-故障零件



import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name = "IMS_ISSUE_PART")
public class ImsIssuePartEntity extends StringIdEntity {

	//问题ID
	@Column(name = "ISSUE_ID")
	private String issueId;
	
	//顺序
	@Column(name="SEQ") 
	private Long seq;
		
	// 故障零件代号ID
	@Column(name = "PART_NUMBER")
	private String partNumber;
	
	// 故障零件代号
	@Column(name = "TROUBLE_PART_NUMBER")
	private String troublePartNumber;

	// 故障零件名称
	@Column(name = "PART_NAME")
	private String partName;

	// 故障零件状态
	@Column(name = "PART_STATUS")
	private String partStatus;

	public String getTroublePartNumber() {
		return troublePartNumber;
	}

	public void setTroublePartNumber(String troublePartNumber) {
		this.troublePartNumber = troublePartNumber;
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

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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

}
