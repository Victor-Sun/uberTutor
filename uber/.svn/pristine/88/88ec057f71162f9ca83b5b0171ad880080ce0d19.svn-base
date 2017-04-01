package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Access(AccessType.FIELD)
@Table(name="PM_DOCUMENT_INDEX")
public class PMDocumentIndexEntity extends StringIdEntity {
	
	public static final String META_PROGRAM_ID = "PROGRAM_ID";
	public static final String META_SOURCE_TYPE = "SOURCE_TYPE";
	public static final String META_SOURCE_ID = "SOURCE_ID";

	@Column(name="PROGRAM_ID") 
	private String programId;
	
	@Column(name="SOURCE_TYPE") 
	private String sourceType;
	
	@Column(name="SOURCE_ID") 
	private String sourceId;
	
	@Column(name="DOCUMENT_ID") 
	private String documentId;

	@Column(name="DOCUMENT_REVISION_ID")
	private String documentVersionId;
	
	@Column(name="IS_LAST")
	private String isLast; 
	
	@Column(name="DOCUMENT_NAME") 
	private String documentName;
	
	@Column(name="CREATE_BY") 
	private String createBy;
	
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	
	@Column(name="DEPARTMENT_ID") 
	private String departmentId;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentVersionId() {
		return documentVersionId;
	}

	public void setDocumentVersionId(String documentVersionId) {
		this.documentVersionId = documentVersionId;
	}

	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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

	public static String getMetaProgramId() {
		return META_PROGRAM_ID;
	}

	public static String getMetaSourceType() {
		return META_SOURCE_TYPE;
	}

	public static String getMetaSourceId() {
		return META_SOURCE_ID;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
