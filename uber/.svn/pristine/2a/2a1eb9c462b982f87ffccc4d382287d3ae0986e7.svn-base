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
@Table(name="PM_ISSUE_SOURCE")
public class PMIssueSourceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_ISSUE_SOURCE_SEQ")     
	@SequenceGenerator(name="PM_ISSUE_SOURCE_SEQ", sequenceName="PM_ISSUE_SOURCE_SEQ")  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="ID") 
	private Long id;
	
	//标题
	@Column(name="TITLE") 
	private String title;

	//描述
	@Column(name="DESCRIPTION") 
	private String description;

	//是否有效
	@Column(name="IS_ACTIVE") 
	private String isActive;

	//创建日期
	@Column(name="CREATE_DATE") 
	private Date createDate;
	//顺序号
	@Column(name="SEQ") 
	private Long seq;

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

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

}
