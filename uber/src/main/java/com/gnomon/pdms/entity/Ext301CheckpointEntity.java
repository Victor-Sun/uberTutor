package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PM_EXT301_CHECKPOINT")
public class Ext301CheckpointEntity {
	
	@Id
	private Long id;
	
	@Column(name="EXT_PROJECT_ID") 
	private String extProjectId;	
	
	@Column(name="CODE") 
	private String code;	
	
	@Column(name="TITLE") 
	private String title;	
	
	@Column(name="ROLE_CODE") 
	private String roleCode;	
	
	@Column(name="DAYS_OFFSET") 
	private Long daysOffset;	
	
	@Column(name="SEQ_NO") 
	private Long seqNo;
	
	@Column(name="RESP_FM_GROUP") 
	private String respFmGroup;	
	
	@Column(name="OWNER") 
	private String owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Long getDaysOffset() {
		return daysOffset;
	}

	public void setDaysOffset(Long daysOffset) {
		this.daysOffset = daysOffset;
	}

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public String getRespFmGroup() {
		return respFmGroup;
	}

	public void setRespFmGroup(String respFmGroup) {
		this.respFmGroup = respFmGroup;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}	
	
	
}
