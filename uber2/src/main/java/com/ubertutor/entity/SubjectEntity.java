package com.ubertutor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SUBJECT")
public class SubjectEntity extends StringIdEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;

	// Subject's Title
	@Column(name="TITLE") 
	private String title;
	
	// Description about the subject
	@Column(name="DESCRIPTION") 
	private String description;
	
	// Category ID that the subject belongs to
	@Column(name="CATEGORY_ID") 
	private String categoryid;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the categoryid
	 */
	public String getCategoryid() {
		return categoryid;
	}

	/**
	 * @param categoryid the categoryid to set
	 */
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
}
