package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SYS_FOLDER_AUTH_PERMISSION")
public class SysFolderAuthPermissionEntity extends StringIdEntity {
	
	// 文件夹权限ID
	@Column(name="FOLDER_AUTH_ID") 
	private String folderAuthId;
	
	// 文件夹权限Code
	@Column(name="FOLDER_PERMISSION_CODE") 
	private String folderPermissionCode;
	
	// 创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	// 创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;

	public String getFolderAuthId() {
		return folderAuthId;
	}

	public void setFolderAuthId(String folderAuthId) {
		this.folderAuthId = folderAuthId;
	}

	public String getFolderPermissionCode() {
		return folderPermissionCode;
	}

	public void setFolderPermissionCode(String folderPermissionCode) {
		this.folderPermissionCode = folderPermissionCode;
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
