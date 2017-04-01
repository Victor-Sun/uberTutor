package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_EXTENDED_PROJECT_COLUMN_DEF")
public class ExtendedProjectColumnDefEntity extends StringIdEntity {
	
	// 专项ID
	@Column(name="EXT_PROJECT_ID") 
	private String extProjectId;
	
	// 序列号
	@Column(name="COLUMN_IDX") 
	private Long columnIdx;
	
	// 列标题
	@Column(name="COLUMN_TITLE") 
	private String columnTitle;
	
	// 列代码
	@Column(name="COLUMN_CODE") 
	private String columnCode;
	
	// 列类型代码
	@Column(name="COLUMN_TYPE_CODE") 
	private String columnTypeCode;
	
	// 列宽
	@Column(name="COLUMN_WIDTH") 
	private Long columnWidth;
	
	// 是否显示在列表
	@Column(name="IS_DISPLAY_ON_LIST") 
	private String isDisplayOnList;
	
	// 创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	// 创建日期
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	// 修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	// 修改日期
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	// 删除日期
	@Column(name="DELETE_DATE") 
	private Date deleteDate;

	public String getExtProjectId() {
		return extProjectId;
	}

	public void setExtProjectId(String extProjectId) {
		this.extProjectId = extProjectId;
	}

	public Long getColumnIdx() {
		return columnIdx;
	}

	public void setColumnIdx(Long columnIdx) {
		this.columnIdx = columnIdx;
	}

	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnTypeCode() {
		return columnTypeCode;
	}

	public void setColumnTypeCode(String columnTypeCode) {
		this.columnTypeCode = columnTypeCode;
	}

	public Long getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(Long columnWidth) {
		this.columnWidth = columnWidth;
	}

	public String getIsDisplayOnList() {
		return isDisplayOnList;
	}

	public void setIsDisplayOnList(String isDisplayOnList) {
		this.isDisplayOnList = isDisplayOnList;
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
