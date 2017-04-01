package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_ISSUE_LEVEL")
public class VIssueLevelEntity extends StringIdEntity{

	//
	@Column(name="NAME") 
	private String name;

	//
	@Column(name="SEQ") 
	private Long seq;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}
