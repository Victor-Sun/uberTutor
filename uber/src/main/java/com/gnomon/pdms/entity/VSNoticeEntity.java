package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="V_SYS_NOTICE")
public class VSNoticeEntity extends StringIdEntity {

 	//通知标题
	@Column(name="TITLE") 
	private String title;

	//通知内容
	@Column(name="CONTENT") 
	private String content;

	//来源CODE
	@Column(name="NOTICE_SOURCE_CODE") 
	private String noticeSourceCode;

	//来源ID
	@Column(name="SOURCE_ID") 
	private String sourceId;

	//是否是新通知
	@Column(name="IS_NEW") 
	private String isNew;

	//是否再次提醒
	@Column(name="IS_REMIND_NEXT_TIME") 
	private String isRemindNextTime;

	//下次提醒日期
	@Column(name="NEXT_REMIND_DATE") 
	private Date nextRemindDate;

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

	//删除日期
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	@Column(name="NOTICE_SOURCE_NAME") 
	private Date noticeSourceName;

	@Column(name="TASK_PROGRESS_STATUS_CODE") 
	private Date taskProgressStatusCode;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoticeSourceCode() {
		return noticeSourceCode;
	}

	public void setNoticeSourceCode(String noticeSourceCode) {
		this.noticeSourceCode = noticeSourceCode;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIsRemindNextTime() {
		return isRemindNextTime;
	}

	public void setIsRemindNextTime(String isRemindNextTime) {
		this.isRemindNextTime = isRemindNextTime;
	}

	public Date getNextRemindDate() {
		return nextRemindDate;
	}

	public void setNextRemindDate(Date nextRemindDate) {
		this.nextRemindDate = nextRemindDate;
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

	public Date getNoticeSourceName() {
		return noticeSourceName;
	}

	public void setNoticeSourceName(Date noticeSourceName) {
		this.noticeSourceName = noticeSourceName;
	}

	public Date getTaskProgressStatusCode() {
		return taskProgressStatusCode;
	}

	public void setTaskProgressStatusCode(Date taskProgressStatusCode) {
		this.taskProgressStatusCode = taskProgressStatusCode;
	}

}
