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
@Table(name = "USERS")
public class UserEntity implements Serializable{
	private static final long serialVersionUID = 5693315978187219180L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="USERS_SEQ")     
	@SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ")
	private Long id; 

	@Column(name = "UUID") 
	private String uuid;

	@Column(name = "USERNAME") 
	private String username;
	

	@Column(name = "FULLNAME") 
	private String fullname;
	
	
	@Column(name = "PASSWORD") 
	private String password;
	
	@Column(name = "SCHOOL_ID")
	private String schoolId;
	
	
	@Column(name = "EMAIL") 
	private String email;
	
	
	@Column(name = "MOBILE") 
	private String mobile;
	
	
	@Column(name = "CREATE_BY") 
	private String createBy;
	

	@Column(name = "CREATE_DATE") 
	private Date createDate;	

	@Column(name = "UPDATE_BY") 
	private String updateBy;
	
	@Column(name = "UPDATE_DATE") 
	private Date updateDate;
	
	@Column(name = "BIO")
	private String bio;
	
	@Column(name = "IS_DISABLED") 
	private String isDisabled;

	@Column(name = "IS_TUTOR") 
	private String isTutor;
	
	@Column(name = "IS_ADMIN")
	private String isAdmin;
	
	@Column(name = "IS_VERIFIED") 
	private String isVerified;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
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

	public void setCreateDate(Date l) {
		this.createDate = l;
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