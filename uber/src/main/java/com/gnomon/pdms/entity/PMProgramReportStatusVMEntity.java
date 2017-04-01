package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Table(name="V_PM_PROGRAM_REPORT_STATUS")
public class PMProgramReportStatusVMEntity extends StringIdEntity {

	@Column(name="PROGRAM_ID")
	private String programId;

	@Column(name="STATUS_CODE_PS")
	private String statusCodePs;

	@Column(name="STATUS_CODE_QI")
	private String statusCodeQi;

	@Column(name="STATUS_CODE_PI")
	private String statusCodePi;

	@Column(name="STATUS_CODE_VC")
	private String statusCodeVc;

	@Column(name="STATUS_CODE_IB")
	private String statusCodeIb;
	
	@Column(name="PROGRAM_VEHICLE_ID")
	private String programVehicleId;
	
	@Column(name="MEMO")
	private String memo;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getStatusCodePs() {
		return statusCodePs;
	}

	public void setStatusCodePs(String statusCodePs) {
		this.statusCodePs = statusCodePs;
	}

	public String getStatusCodeQi() {
		return statusCodeQi;
	}

	public void setStatusCodeQi(String statusCodeQi) {
		this.statusCodeQi = statusCodeQi;
	}

	public String getStatusCodePi() {
		return statusCodePi;
	}

	public void setStatusCodePi(String statusCodePi) {
		this.statusCodePi = statusCodePi;
	}

	public String getStatusCodeVc() {
		return statusCodeVc;
	}

	public void setStatusCodeVc(String statusCodeVc) {
		this.statusCodeVc = statusCodeVc;
	}

	public String getStatusCodeIb() {
		return statusCodeIb;
	}

	public void setStatusCodeIb(String statusCodeIb) {
		this.statusCodeIb = statusCodeIb;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
