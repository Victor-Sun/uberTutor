package com.gnomon.pdms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="PM_PROGRAM_BASEINFO")
public class ProgramBaseinfoEntity extends StringIdEntity {
	
	//项目标识(UUID)
	@Column(name="PROGRAM_ID") 
	private String programId;
	
	//项目类型
	@Column(name="PROGRAM_TYPE_ID") 
	private String programTypeId;
	
	//车型类型
	@Column(name="VEHICLE_TYPE_ID") 
	private String vehicleTypeId;
	
	//车型平台
	@Column(name="VEHICLE_PLATFORM_ID") 
	private String vehiclePlatformId;
	
	//开发范围
	@Column(name="DEV_SCOPE")
	private String devScope;
	
	//开发状态代码
	@Column(name="DEV_STATUS_CODE")
	private String devStatusCode;
	
	//开发类型ID
	@Column(name="DEV_TYPE_ID")
	private String devTypeId;
	
	// 生产单位ID
	@Column(name="MANUFACTURE_COMP_ID")
	private String manufactureCompId;
	
	// 级别
	@Column(name="PROGRAM_LEVEL")
	private String programLevel;
	
	// 产品定位
	@Column(name="PRODUCT_POSITIONING")
	private String productPositioning;
	
	//总监
	@Column(name="DIRECTOR")
	private String director;
	
	//项目经理(从项目组织中查询)?
	@Column(name="PM") 
	private String pm;
	
	//项目优先级代码
	@Column(name="PROGRAM_PRIORITY_CODE") 
	private String programPriorityCode;
	
	//是否签订对外合同(Y/N)
	@Column(name="IS_OUT_CONTRACT") 
	private String isOutContract;
	
	//备注
	@Column(name="MEMO") 
	private String memo;
	
	//修改人
	@Column(name="UPDATE_BY") 
	private String updateBy;
	
	//修改时间
	@Column(name="UPDATE_DATE") 
	private Date updateDate;
	
	//创建人
	@Column(name="CREATE_BY") 
	private String createBy;
	
	//创建时间
	@Column(name="CREATE_DATE") 
	private Date createDate;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehiclePlatformId() {
		return vehiclePlatformId;
	}

	public void setVehiclePlatformId(String vehiclePlatformId) {
		this.vehiclePlatformId = vehiclePlatformId;
	}

	public String getDevScope() {
		return devScope;
	}

	public void setDevScope(String devScope) {
		this.devScope = devScope;
	}

	public String getDevStatusCode() {
		return devStatusCode;
	}

	public void setDevStatusCode(String devStatusCode) {
		this.devStatusCode = devStatusCode;
	}

	public String getDevTypeId() {
		return devTypeId;
	}

	public void setDevTypeId(String devTypeId) {
		this.devTypeId = devTypeId;
	}

	public String getManufactureCompId() {
		return manufactureCompId;
	}

	public void setManufactureCompId(String manufactureCompId) {
		this.manufactureCompId = manufactureCompId;
	}

	public String getProgramLevel() {
		return programLevel;
	}

	public void setProgramLevel(String programLevel) {
		this.programLevel = programLevel;
	}

	public String getProductPositioning() {
		return productPositioning;
	}

	public void setProductPositioning(String productPositioning) {
		this.productPositioning = productPositioning;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getProgramPriorityCode() {
		return programPriorityCode;
	}

	public void setProgramPriorityCode(String programPriorityCode) {
		this.programPriorityCode = programPriorityCode;
	}

	public String getIsOutContract() {
		return isOutContract;
	}

	public void setIsOutContract(String isOutContract) {
		this.isOutContract = isOutContract;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	

}
