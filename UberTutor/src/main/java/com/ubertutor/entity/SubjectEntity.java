package com.ubertutor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SUBJECT")
public class SubjectEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="SUBJECT_SEQ")     
	@SequenceGenerator(name="SUBJECT_SEQ", sequenceName="SUBJECT_SEQ") 
	private Long id; 
	
	@Column(name = "TITLE") 
	private String title;
	
	@Column(name = "DESCRIPTION") 
	private String description;
	
	@Column(name = "CATEGORY_ID") 
	private Long categoryId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long subjectID) {
		this.id = subjectID;
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

	public Long getCategoryid() {
		return categoryId;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryId = categoryid;
	}
}
