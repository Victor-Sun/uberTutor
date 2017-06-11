package com.ubertutor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_SUBJECT")
public class UserSubjectEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="USER_SUBJECT_SEQ")     
	@SequenceGenerator(name="USER_SUBJECT_SEQ", sequenceName="USER_SUBJECT_SEQ") 
	private Long id; 
	
	@Column(name = "USER_ID") 
	private Long userId;
	
	@Column(name = "SUBJECT_ID") 
	private Long subjectId;

	@Column(name = "ADD_DATE")
	private Date addDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserid() {
		return userId;
	}

	public void setUserid(Long userid) {
		this.userId = userid;
	}

	public Long getSubjectid() {
		return subjectId;
	}

	public void setSubjectid(Long subjectid) {
		this.subjectId = subjectid;
	}

	public Date getAdddate() {
		return addDate;
	}

	public void setAdddate(Date adddate) {
		this.addDate = adddate;
	}
}
