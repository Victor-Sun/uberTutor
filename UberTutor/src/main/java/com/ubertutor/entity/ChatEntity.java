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
@Table(name = "CHAT")
public class ChatEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="CHAT_SEQ")     
	@SequenceGenerator(name="CHAT_SEQ", sequenceName="CHAT_SEQ") 
	private Long id;
	
	@Column(name ="TO_USER_ID")
	private Long toUserID;
	
	@Column(name ="FROM_USER_ID")
	private Long fromUserID;
	
	@Column(name ="CHAT_TIME")
	private Date chatTime;
	
	@Column(name ="MESSAGE")
	private String message;
	
	@Column(name ="READFLAG")
	private String readFlag;
	
	@Column(name ="RECALLEDFLAG")
	private String recalledFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getToUserID() {
		return toUserID;
	}

	public void setToUserID(Long toUserID) {
		this.toUserID = toUserID;
	}

	public Long getFromUserID() {
		return fromUserID;
	}

	public void setFromUserID(Long fromUserID) {
		this.fromUserID = fromUserID;
	}

	public Date getChatTime() {
		return chatTime;
	}

	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getRecalledFlag() {
		return recalledFlag;
	}

	public void setRecalledFlag(String recalledFlag) {
		this.recalledFlag = recalledFlag;
	}
}
