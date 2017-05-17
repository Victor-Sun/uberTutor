package com.ubertutor.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="SUBJECT_CATEGORY")
public class CategoryEntity extends StringIdEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;

	@Column(name="TITLE")
	private String title;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	
	
}
