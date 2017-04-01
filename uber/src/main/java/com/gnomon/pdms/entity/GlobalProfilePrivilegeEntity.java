package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SYS_GLOBAL_PROFILE_PRIVILEGE")
public class GlobalProfilePrivilegeEntity extends StringIdEntity {
	
	//
	@Column(name="PRIVILEGE_CODE") 
	private String privilegeCode;
	
	//
	@Column(name="PRIVILEGE_DESC") 
	private String privilegeDesc;
	
	//
	@Column(name="ALLOW_FLAG") 
	private String allowFlag;
	
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
	
	//
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	//
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}

	public String getAllowFlag() {
		return allowFlag;
	}

	public void setAllowFlag(String allowFlag) {
		this.allowFlag = allowFlag;
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
