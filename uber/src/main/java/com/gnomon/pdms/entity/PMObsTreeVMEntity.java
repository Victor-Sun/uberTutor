package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_OBS_TREE")
public class PMObsTreeVMEntity extends StringIdEntity {
	
	// 项目组织编码
	@Column(name="OBS_CODE") 
	private String obsCode;
	
	// 项目组织名称
	@Column(name="OBS_NAME") 
	private String obsName;
	
	// 项目组织描述
	@Column(name="OBS_DESC") 
	private String obsDesc;
	
	// 上级ID
	@Column(name="PARENT_ID") 
	private String parentId;
	
	// 排序字段
	@Column(name="LFT") 
	private Integer lft;
	
	// 排序字段
	@Column(name="RGT") 
	private Integer rgt;
	
	// 项目计划层级
	@Column(name="PLAN_LEVEL") 
	private Integer planLevel;
	
	// 项目组织类型
	@Column(name="OBS_TYPE_CODE") 
	private String obsTypeCode;
	
	// 修改时间
	@Column(name="CREATE_DATE") 
	private Date createDate;	

	//修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	// 删除人
	@Column(name="DELETE_BY") 
	private String deleteBy;
	
	// 删除时间
	@Column(name="DELETE_DATE") 
	private Date deleteDate;
	
	// 项目标识
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	// S_LEVEL
	@Column(name="S_LEVEL") 
	private Integer sLevel;
	
	// 是否为叶子节点
	@Column(name="IS_LEAF") 
	private String isLeaf;

	// 车型ID
	@Column(name="PROGRAM_VEHICLE_ID") 
	private String programVehicleId;

	public String getObsCode() {
		return obsCode;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public void setObsCode(String obsCode) {
		this.obsCode = obsCode;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public String getObsDesc() {
		return obsDesc;
	}

	public void setObsDesc(String obsDesc) {
		this.obsDesc = obsDesc;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLft() {
		return lft;
	}

	public void setLft(Integer lft) {
		this.lft = lft;
	}

	public Integer getRgt() {
		return rgt;
	}

	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}

	public Integer getPlanLevel() {
		return planLevel;
	}

	public void setPlanLevel(Integer planLevel) {
		this.planLevel = planLevel;
	}

	public String getObsTypeCode() {
		return obsTypeCode;
	}

	public void setObsTypeCode(String obsTypeCode) {
		this.obsTypeCode = obsTypeCode;
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

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public Integer getsLevel() {
		return sLevel;
	}

	public void setsLevel(Integer sLevel) {
		this.sLevel = sLevel;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

}
