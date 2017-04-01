package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PM_NOTE")
public class PMNoteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_NOTE_SEQ")     
	@SequenceGenerator(name="PM_NOTE_SEQ", sequenceName="PM_NOTE_SEQ")
	private Long id;
	
	@Column(name="NOTE_TITLE")
	private String noteTitle;
	
	@Lob	@Basic(fetch=FetchType.LAZY)	
	@Column(name="NOTE_CONTENT", columnDefinition="CLOB")	
	private String noteContent;
	
	@Column(name="FOLDER_ID") 
	private Long folderId;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;
	
	//更新人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//更新时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
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
