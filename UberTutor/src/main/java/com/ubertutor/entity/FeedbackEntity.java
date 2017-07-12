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
	
	@Column(name = "CREATE_DATE") 
	private Date createDate; 
	
	@Column(name = "RATING") 
	private Long rating;
	
	@Column(name = "FEEDBACK") 
	private String feedback;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
