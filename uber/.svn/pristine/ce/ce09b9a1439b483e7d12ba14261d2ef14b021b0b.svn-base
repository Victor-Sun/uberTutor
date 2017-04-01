package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="V_PM_PRE_TASK")
public class PreTaskVMEntity extends StringIdEntity {
	
	//任务ID
	@Column(name="TASK_ID") 
	private String taskId;
	
	//前置任务ID
	@Column(name="PRE_TASK_ID") 
	private String preTaskId;
	
	//前置任务类型
	@Column(name="PRE_TASK_TYPE_CODE") 
	private String preTaskTypeCode;
	
	//滞后天数
	@Column(name="LAG") 
	private Long lag;
	
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

	//节点名称
	@Column(name="PRE_TASK_NAME") 
	private String preTaskName;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPreTaskId() {
		return preTaskId;
	}

	public void setPreTaskId(String preTaskId) {
		this.preTaskId = preTaskId;
	}

	public String getPreTaskTypeCode() {
		return preTaskTypeCode;
	}

	public void setPreTaskTypeCode(String preTaskTypeCode) {
		this.preTaskTypeCode = preTaskTypeCode;
	}

	public Long getLag() {
		return lag;
	}

	public void setLag(Long lag) {
		this.lag = lag;
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

	public String getPreTaskName() {
		return preTaskName;
	}

	public void setPreTaskName(String preTaskName) {
		this.preTaskName = preTaskName;
	}

}
