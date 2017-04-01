package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleDashBoardService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 车型看板-开放问题数据取得
	 */
	public Map<String, Object> getOpenIssueReport(String programVehicleId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_ISSUE_TOTAL_RPT");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		
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
	 * 车型看板-交付物看板数据取得
	 */
	public Map<String, Object> getDeliverableStatusReport(String programVehicleId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELV_TOTAL_RPT");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		
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
	 * 车型看板-交付物看板数据取得
	 */
	public List<Map<String, Object>> getDeliverableStatusData(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELV_RPT");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND GATE_CODE IN (SELECT PKG_PM.GET_NEXT_GATE_CODE(PROGRAM_VEHICLE_ID) FROM DUAL)");
		params.add(programVehicleId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 车型看板-节点进度趋势图数据取得
	 */
	public List<Map<String, Object>> getTaskProgressData(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_NODE_TREND_REPORT where PROGRAM_VEHICLE_ID=?");
		params.add(programVehicleId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 车型看板-节点分布图数据取得
	 */
	public List<Map<String, Object>> getNodeTrendData(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_NODE_TREND_REPORT where PROGRAM_VEHICLE_ID=?");
		params.add(programVehicleId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 车型看板-质量问题趋势图数据取得
	 */
	public List<Map<String, Object>> getOpenIssueTrendData(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_IMS_TREND_REPORT");
		sql.append(" WHERE");
		sql.append(" SUB_PROJECT_ID = ?");
		sql.append(" ORDER BY");
		sql.append(" DATE_TO");
		params.add(programVehicleId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
}
