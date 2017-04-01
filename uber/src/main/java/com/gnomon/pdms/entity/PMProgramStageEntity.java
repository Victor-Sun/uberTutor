package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;
@Entity
@Table(name="PM_PROGRAM_STAGE")
public class PMProgramStageEntity extends StringIdEntity {
	
	//名称
	@Column(name="NAME") 
	private String name;
	//项目标识
	@Column(name="PROGRAM_ID") 
	private String programId;
	//开始日期
	@Column(name="BEGIN_DATE") 
	private Date beginDate;
	//结束日期
	@Column(name="END_DATE") 
	private Date endDate;
	//顺序
	@Column(name="SEQ") 
	private Long seq;
	//开始节点ID
	@Column(name="START_NODE_ID") 
	private String startNodeId;
	//结束节点ID
	@Column(name="END_NODE_ID") 
	private String endNodeId;
	//备注
	@Column(name="MEMO") 
	private String memo;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getStartNodeId() {
		return startNodeId;
	}
	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}
	public String getEndNodeId() {
		return endNodeId;
	}
	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
