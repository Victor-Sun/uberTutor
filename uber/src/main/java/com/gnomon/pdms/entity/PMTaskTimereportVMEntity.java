package com.gnomon.pdms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_TASK_TIMEREPORT")
public class PMTaskTimereportVMEntity extends StringIdEntity {
	
	//任务ID
	@Column(name="TASK_ID") 
	private String taskId;
	
	//任务进展状态代码
	@Column(name="TASK_PROGRESS_STATUS_CODE") 
	private String taskProgressStatusCode;
	
	//完成率
	@Column(name="PERCENT_COMPLETE") 
	private BigDecimal percentComplete;
	
	//工时
	@Column(name="MAN_HOUR") 
	private BigDecimal manHour;
	
	//备注
	@Column(name="REMARK") 
	private String remark;
	
	//进度状态（正常、延期）
	@Column(name="STATUS") 
	private String status;
	
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
	
	//删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	//删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskProgressStatusCode() {
		return taskProgressStatusCode;
	}

	public void setTaskProgressStatusCode(String taskProgressStatusCode) {
		this.taskProgressStatusCode = taskProgressStatusCode;
	}

	public BigDecimal getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(BigDecimal percentComplete) {
		this.percentComplete = percentComplete;
	}

	public BigDecimal getManHour() {
		return manHour;
	}

	public void setManHour(BigDecimal manHour) {
		this.manHour = manHour;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
