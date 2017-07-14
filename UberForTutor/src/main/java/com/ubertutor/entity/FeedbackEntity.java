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
@Table(name = "FEEDBACK")
public class FeedbackEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="FEEDBACK_SEQ")     
	@SequenceGenerator(name="FEEDBACK_SEQ", sequenceName="FEEDBACK_SEQ") 
	private Long id; 
	
	@Column(name = "USER_ID") 
	private Long userId;
	
	@Column(name = "TUTOR_ID") 
	private Long tutorId;
	
	@Column(name = "REQUEST_ID") 
	private Long requestId;
	
	@Column(name = "CREATE_DATE") 
	private Date createDate; 
	
	@Column(name = "RATING") 
	private int rating;
	
	@Column(name = "COMMENTS") 
	private String comments;

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

	public Long getTutorid() {
		return tutorId;
	}

	public void setTutorid(Long tutorid) {
		this.tutorId = tutorid;
	}

	public Long getRequestid() {
		return requestId;
	}

	public void setRequestid(Long requestid) {
		this.requestId = requestid;
	}

	public Date getCreatedate() {
		return createDate;
	}

	public void setCreatedate(Date createdate) {
		this.createDate = createdate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
