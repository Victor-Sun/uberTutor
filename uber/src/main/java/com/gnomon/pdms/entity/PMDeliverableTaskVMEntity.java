package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_DELIVERABLE_TASK")
public class PMDeliverableTaskVMEntity extends StringIdEntity {
	
	//交付物编码
	@Column(name="CODE") 
	private String code;
	
	//交付物类型
	@Column(name="NAME") 
	private String name;
	
	//交付物名称
	@Column(name="TYPE") 
	private String type;
	
	//责任项目组织ID
	@Column(name="OBS_ID") 
	private String obsId;
	
	//所属任务ID
	@Column(name="TASK_ID") 
	private String taskId;
	
	//责任人ID
	@Column(name="USERID") 
	private String userid;
	
	//提交时间
	@Column(name="SUBMIT_DATE") 
	private Date submitDate;
	
	//交付物状态代码
	@Column(name="DELV_QUALITY_STATUS_CODE") 
	private String delvQualityStatusCode;
	
	//原因
	@Column(name="DELV_QUALITY_NOTE") 
	private String delvQualityNote;
	
	// 是否是关键交付物
	@Column(name="IS_KEY") 
	private String isKey;
	
	// 本项目是否适用
	@Column(name="IS_FIT") 
	private String isFit;
	
	// 不适用理由
	@Column(name="NOT_FIT_REASON") 
	private String notFitReason;

	//备注
	@Column(name="MEMO") 
	private String memo;
	
	//文档存放路径
	@Column(name="DOCUMENT_FILE_PATH") 
	private String documentFilePath;
	
	//变更次数
	@Column(name="CHANGE_TIMES") 
	private Long changeTimes;
	
	//变更记录(可放入子表）
	@Column(name="CHANGE_RECORD") 
	private String changeRecord;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//创建时间
	@Column(name="CREATE_DATE") 
	private Date  createDate;
	
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
	
	// 项目ID
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	// 名称
	@Column(name="TASK_NAME") 
	private String taskName;
	
	// 任务类型
	@Column(name="TASK_TYPE_CODE") 
	private String taskTypeCode;
	
	// 负责主体名称
	@Column(name="OBS_NAME") 
	private String obsName;
	
	// 负责人名称
	@Column(name="USERNAME") 
	private String username;

	// 文件
	@Column(name="ATT_CNT") 
	private Long attCnt;
	
	// 进展状态
	@Column(name="TASK_PROGRESS_STATUS_CODE") 
	private String taskProgressStatusCode;
	
	// 所属车型
	@Column(name="PROGRAM_VEHICLE_ID") 
	private String programVehicleId;
	
	// 质量阀时间
	@Column(name="GATE_DATE") 
	private Date gateDate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getDelvQualityStatusCode() {
		return delvQualityStatusCode;
	}

	public void setDelvQualityStatusCode(String delvQualityStatusCode) {
		this.delvQualityStatusCode = delvQualityStatusCode;
	}

	public String getDelvQualityNote() {
		return delvQualityNote;
	}

	public void setDelvQualityNote(String delvQualityNote) {
		this.delvQualityNote = delvQualityNote;
	}

	public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public String getIsFit() {
		return isFit;
	}

	public void setIsFit(String isFit) {
		this.isFit = isFit;
	}

	public String getNotFitReason() {
		return notFitReason;
	}

	public void setNotFitReason(String notFitReason) {
		this.notFitReason = notFitReason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDocumentFilePath() {
		return documentFilePath;
	}

	public void setDocumentFilePath(String documentFilePath) {
		this.documentFilePath = documentFilePath;
	}

	public Long getChangeTimes() {
		return changeTimes;
	}

	public void setChangeTimes(Long changeTimes) {
		this.changeTimes = changeTimes;
	}

	public String getChangeRecord() {
		return changeRecord;
	}

	public void setChangeRecord(String changeRecord) {
		this.changeRecord = changeRecord;
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

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskTypeCode() {
		return taskTypeCode;
	}

	public void setTaskTypeCode(String taskTypeCode) {
		this.taskTypeCode = taskTypeCode;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getAttCnt() {
		return attCnt;
	}

	public void setAttCnt(Long attCnt) {
		this.attCnt = attCnt;
	}

	public String getTaskProgressStatusCode() {
		return taskProgressStatusCode;
	}

	public void setTaskProgressStatusCode(String taskProgressStatusCode) {
		this.taskProgressStatusCode = taskProgressStatusCode;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public Date getGateDate() {
		return gateDate;
	}

	public void setGateDate(Date gateDate) {
		this.gateDate = gateDate;
	}

}
