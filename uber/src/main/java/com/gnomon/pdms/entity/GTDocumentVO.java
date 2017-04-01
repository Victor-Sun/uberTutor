/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.pdms.entity;

/**
 * @author 莫鹏
 *
 */
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GTDocumentVO{

	private String id;
	
	private String sourceType;//来源类型
	
	private String sourceID;//ID
	
	private Long gtDocumentId;//具体文件ID

	private String shortName;// 文件名称
	
	private String documentPath;// 保存路径
	
	private String uploadBy;// 上传人
	
	private Date uploadDate;// 上传时间
	
	
	private String stage; //所属阶段
	
	private String state;
	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getStateCode() {
		return stateCode;
	}


	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	private String stateCode;
	
	


	private String documentSource;//文件来源
	private String version;//文件版本号

	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getSourceID() {
		return sourceID;
	}


	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}


	public Long getGtDocumentId() {
		return gtDocumentId;
	}


	public void setGtDocumentId(Long gtDocumentId) {
		this.gtDocumentId = gtDocumentId;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getDocumentPath() {
		return documentPath;
	}


	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}


	public String getUploadBy() {
		return uploadBy;
	}


	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDocumentSource() {
		return documentSource;
	}

	public void setDocumentSource(String documentSource) {
		this.documentSource = documentSource;
	}


	public String getStage() {
		return stage;
	}


	public void setStage(String stage) {
		this.stage = stage;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	

}
