package com.gnomon.pdms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_PROGRAM")
public class ProgramEntity extends StringIdEntity {
	
	//项目代号
	@Column(name="CODE") 
	private String code;
	
	//项目名称
	@Column(name="PROGRAM_NAME") 
	private String programName;
	
	//计划开始日期?
	@Column(name="PLANNED_START_DATE") 
	private Date plannedStartDate;
	
	//计划结束日期?
	@Column(name="PLANNED_FINISH_DATE") 
	private Date plannedFinishDate;
	
	//实际开始日期?
	@Column(name="ACTUAL_START_DATE") 
	private Date actualStartDate;
	
	//实际结束日期?
	@Column(name="ACTUAL_FINISH_DATE") 
	private Date actualFinishDate;
	
	//完成率？
	@Column(name="PERCENT_COMPLETE") 
	private BigDecimal percentComplete;
	
	//锁定日期?
	@Column(name="LOCK_DATE") 
	private Date lockDate;
	
	//锁定人?
	@Column(name="LOCK_BY") 
	private String lockBy;
	
	//EPS_ID
	@Column(name="EPS_ID") 
	private String epsId;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(Date plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	public BigDecimal getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(BigDecimal percentComplete) {
		this.percentComplete = percentComplete;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public String getLockBy() {
		return lockBy;
	}

	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
	}

	public String getEpsId() {
		return epsId;
	}

	public void setEpsId(String epsId) {
		this.epsId = epsId;
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

}
