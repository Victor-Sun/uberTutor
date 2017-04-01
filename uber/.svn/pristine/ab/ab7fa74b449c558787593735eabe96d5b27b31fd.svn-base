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
@Table(name="PM_ISSUE_TYPE")
public class PMIssueTyeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_ISSUE_TYPE_SEQ")     
	@SequenceGenerator(name="PM_ISSUE_TYPE_SEQ", sequenceName="PM_ISSUE_TYPE_SEQ")
	private Long id;
	
	//标题
	@Column(name="TITLE") 
	private String title;

	//描述
	@Column(name="DESCRIPTION") 
	private String description;
	
	
	@Column(name="IS_ACTIVE") 
	private String isActive;

	@Column(name="CREATE_DATE") 
	private Date  createDate;
	
	@Column(name="TYPE") 
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
