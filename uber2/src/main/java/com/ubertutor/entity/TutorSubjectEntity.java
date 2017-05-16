package com.ubertutor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="TUTOR_SUBJECT")
public class TutorSubjectEntity extends StringIdEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;

	// ID
	@Column(name="ID") 
	private String id;
	
	// User's ID
	@Column(name="USER_ID") 
	private String userid;
	
	// Subject's ID
	@Column(name="SUBJECT_ID") 
	private String subjectid;

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
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the subjectid
	 */
	public String getSubjectid() {
		return subjectid;
	}

	/**
	 * @param subjectid the subjectid to set
	 */
	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}
}
