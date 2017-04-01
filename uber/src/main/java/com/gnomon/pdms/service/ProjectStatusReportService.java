package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMProgramReportStatusDAO;
import com.gnomon.pdms.dao.PMProgramReportStatusVMDAO;
import com.gnomon.pdms.entity.PMProgramReportStatusEntity;
import com.gnomon.pdms.entity.PMProgramReportStatusVMEntity;

@Service
@Transactional
public class ProjectStatusReportService {

	@Autowired
	private PMProgramReportStatusVMDAO pmProgramReportStatusVMDAO;
	
	@Autowired
	private PMProgramReportStatusDAO pmProgramReportStatusDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 项目状态信息取得
	 */
	public Map<String, Object> getReportStatus(String programVehicleId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_REPORT_STATUS");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND IS_ACTIVE = ?");
		params.add(programVehicleId);
		params.add(PDMSConstants.STATUS_Y);
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (list.size() > 0) {
			result = list.get(0);
		} else {
			result = new HashMap<String, Object>();
		}
		return result;
	}
	
	/**
	 * 项目状态变更记录取得
	 */
	public List<Map<String, Object>> getReportStatusList(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_REPORT_STATUS");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" ORDER BY CREATE_DATE DESC");
		params.add(programVehicleId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 项目总体状态变更
	 */
	public void changeReportStatus(String programId, String programVehicleId,
			Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新已有状态
		List<PMProgramReportStatusEntity> list =
				this.pmProgramReportStatusDAO.find(
						"from PMProgramReportStatusEntity"
						+ " where programVehicleId = ?"
						+ " and isActive = 'Y'", programVehicleId);
		for (PMProgramReportStatusEntity entity4upd : list) {
			entity4upd.setIsActive(PDMSConstants.STATUS_N);
			entity4upd.setUpdateBy(loginUser);
			entity4upd.setUpdateDate(new Date());
			this.pmProgramReportStatusDAO.save(entity4upd);
		}
		
		// 插入新状态
		PMProgramReportStatusEntity entity4ins = new PMProgramReportStatusEntity();
		entity4ins.setId(PDMSCommon.generateUUID());
		entity4ins.setProgramId(programId);
		entity4ins.setProgramVehicleId(programVehicleId);
		entity4ins.setStatusCodeTotal(model.get("statusCodeTotal"));
		entity4ins.setMemo(model.get("memo"));
		entity4ins.setIsActive(PDMSConstants.STATUS_Y);
		entity4ins.setCreateBy(loginUser);
		entity4ins.setCreateDate(new Date());
		this.pmProgramReportStatusDAO.save(entity4ins);
	}
	
	

	/*
	 * 项目列表-项目管理状态报告list取得
	 */
	public List<PMProgramReportStatusVMEntity> getProjectDBStatusReport(
			String programId, String vehicleId) {
		String hql = "FROM PMProgramReportStatusVMEntity WHERE"
				+ " programId = ? AND programVehicleId = ?";
		List<PMProgramReportStatusVMEntity> result = 
				this.pmProgramReportStatusVMDAO.find(hql, programId, vehicleId);

		return result;
	}

	/*
	 * 项目列表-项目管理状态报告list取得
	 */
	public void updateProjectStatusReport(String programId, String vehicleId,
			String statusReportId, Map<String, String> status) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		if (PDMSCommon.isNull(statusReportId) ||
				statusReportId.indexOf("projectmanagement") != -1) {
			// 插入项目信息
			sql = new StringBuffer();
			sql.append(" INSERT INTO PM_PROGRAM_REPORT_STATUS");
			sql.append("(ID");
			sql.append(",PROGRAM_ID");
			sql.append(",PROGRAM_VEHICLE_ID");
			sql.append(",STATUS_CODE_PS");
			sql.append(",STATUS_CODE_QI");
			sql.append(",STATUS_CODE_PI");
			sql.append(",STATUS_CODE_VC");
			sql.append(",STATUS_CODE_IB");
			sql.append(",CREATE_BY");
			sql.append(",CREATE_DATE)");
			sql.append(" VALUES(");
			sql.append(" ?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",SYSDATE)");
			params = new ArrayList<Object>();
			params.add(PDMSCommon.generateUUID());
			params.add(programId);
			params.add(vehicleId);
			params.add(status.get("statusCodePs"));
			params.add(status.get("statusCodeQi"));
			params.add(status.get("statusCodePi"));
			params.add(status.get("statusCodeVc"));
			params.add(status.get("statusCodeIb"));
			params.add(loginUser);
			this.jdbcTemplate.update(sql.toString(), params.toArray());
		} else {
			// 更新项目信息
			sql = new StringBuffer();
			sql.append(" UPDATE PM_PROGRAM_REPORT_STATUS SET");
			sql.append(" STATUS_CODE_PS = ?");
			sql.append(",STATUS_CODE_QI = ?");
			sql.append(",STATUS_CODE_PI = ?");
			sql.append(",STATUS_CODE_VC = ?");
			sql.append(",STATUS_CODE_IB = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			params = new ArrayList<Object>();
			params.add(status.get("statusCodePs"));
			params.add(status.get("statusCodeQi"));
			params.add(status.get("statusCodePi"));
			params.add(status.get("statusCodeVc"));
			params.add(status.get("statusCodeIb"));
			params.add(loginUser);
			params.add(statusReportId);
			this.jdbcTemplate.update(sql.toString(), params.toArray());
		}
	}
}
