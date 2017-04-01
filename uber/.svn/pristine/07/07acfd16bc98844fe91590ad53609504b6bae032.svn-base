package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SYS_ROLE")
public class GTRole{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="SYS_ROLE_SEQ")     
	@SequenceGenerator(name="SYS_ROLE_SEQ", sequenceName="SYS_ROLE_SEQ")
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="ROLE_ID") 
	private String roleId;
	
	@Column(name="INTERNAL_USER") 
	private String internalUser;
	
	@Column(name="TITLE") 
	private String title;
	
	@Column(name="TITLE_EN") 
	private String titleEn;
	
	@Column(name="IS_SYS_ROLE") 
	private String isSysRole;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getIsSysRole() {
		return isSysRole;
	}

	public void setIsSysRole(String isSysRole) {
		this.isSysRole = isSysRole;
	}
	

	
}
