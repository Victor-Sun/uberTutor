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
@Table(name="USER_SUBJECT")
public class UserSubjectEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="USER_SUBJECT_SEQ")     
	@SequenceGenerator(name="USER_SUBJECT_SEQ", sequenceName="USER_SUBJECT_SEQ") 
	private Long id; 
	
	// User's ID
	@Column(name="USER_ID") 
	private String userid;
	
	// Subject's ID
	@Column(name="SUBJECT_ID") 
	private String subjectid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
