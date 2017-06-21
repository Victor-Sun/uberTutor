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
@Table(name = "SUBJECT_CATEGORY")
public class SubjectCategoryEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="SUBJECT_CATEGORY_SEQ")     
	@SequenceGenerator(name="SUBJECT_CATEGORY_SEQ", sequenceName="SUBJECT_CATEGORY_SEQ") 
	private Long id; 
	
	@Column(name = "TITLE")
	private String title;

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
}
