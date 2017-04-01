package com.gnomon.common.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_SYS_USER")
public class SysUserVMEntity extends StringIdEntity {
	
	//用户标识
	@Column(name="USERID") 
	private String userid;
	
	//姓名
	@Column(name="USERNAME") 
	private String username;
	
	//密码(加密存储)
	@Column(name="PASSWORD") 
	private String password;
	
	// 邮箱
	@Column(name="EMAIL") 
	private String email;
	
	// 手机号
	@Column(name="MOBILE") 
	private String mobile;
	
	// 员工编号
	@Column(name="EMPLOYEE_NO") 
	private String employeeNo;
	
	// 语言
	@Column(name="LANGUAGE") 
	private String language;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;

	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;	

	//修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	// 删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	
	// 角色ID
	@Column(name="PROFILE_ID") 
	private String profileId;
	
	// 部门ID
	@Column(name="DEPARTMENT_ID") 
	private String departmentId;
	
	// 所属部门名称
	@Column(name="DEPARTMENT_NAME") 
	private String departmentName;
	
	// 角色名称
	@Column(name="PROFILE_NAME") 
	private String profileName;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

}
