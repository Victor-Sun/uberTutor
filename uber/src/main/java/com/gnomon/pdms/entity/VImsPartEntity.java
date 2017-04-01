package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_IMS_PART")
public class VImsPartEntity extends StringIdEntity {

	//零件代号
	@Column(name="PART_CODE") 
	private String partCode;
	
	//零件名称
	@Column(name="PART_NAME") 
	private String partName;

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

}
