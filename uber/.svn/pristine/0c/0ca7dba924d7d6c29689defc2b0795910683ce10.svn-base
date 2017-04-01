package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SYS_FOLDER_AUTH")
public class SysFolderAuthEntity extends StringIdEntity {
	
	// 项目角色ID
	@Column(name="ROLEID") 
	private String roleid;
	
	// 文件夹ID
	@Column(name="FOLDER_ID") 
	private Long folderId;
	
	// 创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	// 创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
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
}
