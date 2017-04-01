package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_RELEASE_RECORD")
public class PMReleaseRecordEntity extends StringIdEntity {
	// 发布对象类型
	@Column(name="OBJECT_TYPE_CODE") 
	private String objectTypeCode;
	
	// 发布对象ID
	@Column(name="OBJECT_ID") 
	private String objectId;
	
	// 发布人
	@Column(name="RELEASE_BY") 
	private String releaseBy;
	
	// 发布时间
	@Column(name="RELEASE_DATE") 
	private Date releaseDate;
	
	// 发布原因
	@Column(name="RELEASE_NOTE") 
	private String releaseNote;
	
	// 发布证据（文件ID）
	@Column(name="RELEASE_EVIDENCE_ID")
	private String releaseEvidenceId;

	public String getObjectTypeCode() {
		return objectTypeCode;
	}

	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getReleaseBy() {
		return releaseBy;
	}

	public void setReleaseBy(String releaseBy) {
		this.releaseBy = releaseBy;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseNote() {
		return releaseNote;
	}

	public void setReleaseNote(String releaseNote) {
		this.releaseNote = releaseNote;
	}

	public String getReleaseEvidenceId() {
		return releaseEvidenceId;
	}

	public void setReleaseEvidenceId(String releaseEvidenceId) {
		this.releaseEvidenceId = releaseEvidenceId;
	}
	
}
