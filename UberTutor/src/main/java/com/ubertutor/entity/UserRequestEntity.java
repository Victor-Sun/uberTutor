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
@Table(name = "USER_REQUEST")
public class UserRequestEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="USER_REQUEST_SEQ")     
	@SequenceGenerator(name="USER_REQUEST_SEQ", sequenceName="USER_REQUEST_SEQ")
	private Long id; 
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "TUTOR_ID")
	private Long tutorId;
	
	@Column(name = "SUBJECT_ID")
	private Long subjectId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String Description;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "PENDING_DATE")
	private Date pendingDate;
	
	@Column(name = "PROCESS_DATE")
	private Date processDate;
	
	@Column(name = "CLOSE_DATE")
	private Date closeDate;
	
	@Column(name = "CANCEL_DATE")
	private Date cancelDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPendingDate() {
		return pendingDate;
	}

	public void setPendingDate(Date pendingDate) {
		this.pendingDate = pendingDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
}
