package com.gnomon.common.system.entity;

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
@Table(name="USERS")
public class UserEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="USERS_SEQ")     
	@SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ")  

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// User's UUID
	@Column(name="UUID") 
	private String uuid;
	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	// User's Profile Name
	@Column(name="USERNAME") 
	private String username;

	// User's Full Name
	@Column(name="FULLNAME") 
	private String fullname;
	
	// Password(Encrypted)
	@Column(name="PASSWORD") 
	private String password;
	
	// Email
	@Column(name="EMAIL") 
	private String email;
	
	// Mobile Phone Number
	@Column(name="MOBILE") 
	private String mobile;
	
	@Column(name="CREATE_BY") 
	private String createBy;

	// Date and time the account was created
	@Column(name="CREATE_DATE") 
	private Date createDate;	

	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	// When the account was updated
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	// When the account was deleted
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	
	@Column(name="IS_DISABLED") 
	private String isDisabled;

	@Column(name="IS_TUTOR") 
	private String isTutor;
	
	@Column(name="IS_VERIFIED") 
	private String isVerified;


	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getIsTutor() {
		return isTutor;
	}

	public void setIsTutor(String isTutor) {
		this.isTutor = isTutor;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

}