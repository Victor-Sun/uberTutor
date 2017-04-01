package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_CODE_TABLE")
public class CodeTableEntity {

	//代码类型
	@Column(name="TYPE") 
	private String type;
	
	//代码
	@Id
	@Column(name="CODE") 
	private String code;
	
	//名称
	@Column(name="NAME") 
	private String name;
	
	//描述
	@Column(name="DESCRIPTION") 
	private String description;
	
	//顺序
	@Column(name="SEQ") 
	private Number seq;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Number getSeq() {
		return seq;
	}

	public void setSeq(Number seq) {
		this.seq = seq;
	}
}
