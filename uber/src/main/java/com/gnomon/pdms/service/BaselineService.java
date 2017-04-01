package com.gnomon.pdms.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.base.StringIdEntity;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class BaselineService extends StringIdEntity {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;

	/**
	 * 创建基线（车型计划）
	 */
	public void createBaseline(String programVehicleId, String obsId,
			String title, String note) {
		// 创建基线
		if (PDMSCommon.isNotNull(obsId)) {
			this.pkgPmDBProcedureServcie.createBaselinePvFnPlan(
					programVehicleId, title, note, obsId);
		} else {
			this.pkgPmDBProcedureServcie.createBaselinePvMainPlan(
					programVehicleId, title, note);
		}
	}
	
	/**
	 * 创建基线（项目基本信息）
	 */
	public void createBiBaseline(String programId, String title, String note) {
		// 创建基线
		this.pkgPmDBProcedureServcie.createPmBiBaseline(
				programId, title, note);
	}
	
	/**
	 * 设定默认基线(项目计划基线)
	 */
	public void setDefaultBaseline(String programVehicleId, String obsId, int baselineId) {
		if (PDMSCommon.isNotNull(obsId)) {
			// 设二级计划默认基线
			this.pkgPmDBProcedureServcie.setBaselinePvFnPlan(
					programVehicleId, obsId, baselineId);
		} else {
			// 设主计划默认基线
			this.pkgPmDBProcedureServcie.setBaselinePvMainPlan(
					programVehicleId, baselineId);
		}
	}
	
	/**
	 * 设定默认基线(项目基本信息基线)
	 */
	public void setDefaultBiBaseline(String programId, int baselineId) {
		// 设定默认基线
		this.pkgPmDBProcedureServcie.setDefaultPmBiBaseline(
				programId, baselineId);
	}

	/**
	 * 基线列表信息取得(项目计划基线)
	 */
	public List<Map<String, Object>> getBaselineList(String programVehicleId, String obsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_BASELINE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND BASELINE_TYPE = ?");
			params.add(PDMSConstants.BASELINE_TYPE_FN);
			sql.append(" AND OBS_ID = ?");
			params.add(obsId);
		} else {
			sql.append(" AND BASELINE_TYPE = ?");
			params.add(PDMSConstants.BASELINE_TYPE_MAIN);
		}
		return jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 基线列表信息取得(基本信息基线)
	 */
	public List<Map<String, Object>> getBiBaseline(String programId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_BI_BASELINE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		return jdbcTemplate.queryForList(sql.toString(), programId);
	}
	
	/**
	 * 项目基本信息基线详细取得
	 */
	public Map<String, Object> getBaseInfoBaseline(String baselineId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_BASELINE");
		sql.append(" WHERE");
		sql.append(" BASELINE_ID = ?");
		return this.jdbcTemplate.queryForMap(sql.toString(), baselineId);
	}
	
	/**
	 * 取得默认基线（主计划）
	 */
	public Map<String, Object> geDefaultBaseline(String programVehicleId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_BASELINE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ? AND BASELINE_TYPE = 'MAIN PLAN'");
		sql.append(" AND IS_DEFAULT = 'Y'");
		
		try {
			result = this.jdbcTemplate.queryForMap(sql.toString(), programVehicleId);
		} catch(EmptyResultDataAccessException e) {
			result = new HashMap<String, Object>();
		}
		return result;
	}
	
	/**
	 * 项目计划基线取得（发布的有效基线）
	 */
	public Map<String, Object> getBaselineTask(String programVehicleId, String obsId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_BASELINE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND IS_DEFAULT = ?");
		params.add(programVehicleId);
		params.add(PDMSConstants.STATUS_Y);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND BASELINE_TYPE = ?");
			sql.append(" AND OBS_ID = ?");
			params.add(PDMSConstants.BASELINE_TYPE_FN);
			params.add(obsId);
		} else {
			sql.append(" AND BASELINE_TYPE = ?");
			params.add(PDMSConstants.BASELINE_TYPE_MAIN);
		}
		try {
			result = this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
		} catch(EmptyResultDataAccessException e) {
			result = new HashMap<String, Object>();
		}
		return result;
	}
}
