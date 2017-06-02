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
@Table(name="FEEDBACK")
public class FeedbackEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="FEEDBACK_SEQ")     
	@SequenceGenerator(name="FEEDBACK_SEQ", sequenceName="FEEDBACK_SEQ") 
	private Long id; 
	
	@Column(name="USER_ID") 
	private Long userid;
	
	@Column(name="TUTOR_ID") 
	private Long tutorid;
	
	@Column(name="REQUEST_ID") 
	private Long requestid;
	
	@Column(name="CREATE_DATE") 
	private String createdate; 
	
	@Column(name="RATING") 
	private int rating;
	
	@Column(name="COMMENTS") 
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getTutorid() {
		return tutorid;
	}

	public void setTutorid(Long tutorid) {
		this.tutorid = tutorid;
	}

	public Long getRequestid() {
		return requestid;
	}

	public void setRequestid(Long requestid) {
		this.requestid = requestid;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
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
