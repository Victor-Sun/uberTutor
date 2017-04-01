package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMTaskVehicleVMDAO;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class ProjectTimeLineService {

	@Autowired
	private PMTaskVehicleVMDAO pmTaskVehicleVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;

	/**
	 * 时程表Project数据取得
	 */
	public Map<String, Object> getTimelineProjectData(
			String vehicleId, String baselineId) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer sql = null;
		List<Object> params = null;
		
		// 基线类型判定
		baselineId = this.resetBaselineId(baselineId);
		
		// 开始时间取得
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		//sql.append(" NVL(MIN(PLANNED_START_DATE)");
		//sql.append(",MIN(PLANNED_FINISH_DATE)) AS PLANNED_START_DATE");
		sql.append(" (CASE WHEN MIN(PLANNED_START_DATE) > MIN(PLANNED_FINISH_DATE)");
		sql.append(" THEN MIN(PLANNED_FINISH_DATE)");
		sql.append(" ELSE MIN(PLANNED_START_DATE) END) AS PLANNED_START_DATE");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" PM_TASK_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" PM_TASK");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND DELETE_BY IS NULL");
		params.add(vehicleId);
		Map<String, Object> stDate = null;
		try {
			stDate = this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
		} catch(EmptyResultDataAccessException ex) {
			stDate = new HashMap<String, Object>();
		}
		
		// SOP时间取得
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" PLANNED_FINISH_DATE");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" PM_TASK_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" PM_TASK");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE = ?");
		sql.append(" AND DELETE_BY IS NULL");
		params.add(vehicleId);
		params.add(PDMSConstants.TASK_TYPE_SOP_NODE);
		Map<String, Object> sopDate = null;
		try {
			sopDate = this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
		} catch(EmptyResultDataAccessException ex) {
			sopDate = new HashMap<String, Object>();
		}
		
		Date start = (Date)stDate.get("PLANNED_START_DATE");
		if (start != null) {
			result.put("start", DateUtils.addMonths(start, -1));
		}
		Date end = (Date)sopDate.get("PLANNED_FINISH_DATE");
		if (end != null) {
			if (end.getDate() < 15) {
				result.put("end", DateUtils.addMonths(end, 7));
			} else {
				result.put("end", DateUtils.addMonths(end, 8));
			}
		}
        return result;
    }

	/**
	 * 主计划泳道名称一览取得
	 */
	public List<Map<String, Object>> getTimelineResources(String programVehicleId,
			String planId, String obsId, String baselineId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_FUNCTION_PLAN_BL");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_FUNCTION_PLAN");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		sql.append(" AND (PLAN_ID = ?");
		params.add(planId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" OR PLAN_LEVEL = 1");
			sql.append(" AND RESP_OBS_ID = ?");
			params.add(obsId);
		}
		sql.append(" )");
		sql.append(" AND IS_VISIBLE = ?");
		sql.append(" ORDER BY");
		sql.append(" PLAN_LEVEL, SEQ_NO");
		params.add(PDMSConstants.STATUS_Y);
		
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

	/**
	 * 主节点数据取得
	 */
	public List<Map<String, Object>> getTimelineEventsMajor(
			String programId, String vehicleId, String baselineId) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		// 基线类型判定
		baselineId = this.resetBaselineId(baselineId);
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_VEHICLE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			paramList.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_VEHICLE");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_ID = ?");
		sql.append(" AND VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE IN(?, ?)");
		sql.append(" ORDER BY");
		sql.append(" PLANNED_FINISH_DATE");
		paramList.add(programId);
		paramList.add(vehicleId);
		paramList.add(PDMSConstants.TASK_TYPE_MAIN_NODE);
		paramList.add(PDMSConstants.TASK_TYPE_SOP_NODE);
        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}

	/**
	 * 质量阀数据取得
	 */
	public List<Map<String, Object>> getTimelineEventsGate(
			String programId, String vehicleId, String baselineId) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		// 基线类型判定
		baselineId = this.resetBaselineId(baselineId);
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_VEHICLE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			paramList.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_VEHICLE");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_ID = ?");
		sql.append(" AND VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE = ?");
		sql.append(" ORDER BY");
		sql.append(" PLANNED_FINISH_DATE");
		paramList.add(programId);
		paramList.add(vehicleId);
		paramList.add(PDMSConstants.TASK_TYPE_GATE);
        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 泳道数据取得
	 */
	public List<Map<String, Object>> getTimelineEventsObs(
			String functionId, String baselineId) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_FUNCTION_TASK_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			paramList.add(baselineId);
		} else {
			sql.append(" V_PM_FUNCTION_TASK");
			sql.append(" WHERE");
		}
		sql.append(" FUNCTION_ID = ?");
		sql.append(" AND IS_VISIBLE = ?");
		sql.append(" ORDER BY");
		sql.append(" PLANNED_FINISH_DATE");
		paramList.add(functionId);
		paramList.add(PDMSConstants.STATUS_Y);
        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}
	
//	/**
//	 * 二级计划泳道一览取得
//	 */
//	public List<Map<String, Object>> getTimelineResourcesDept(
//			String obsId, String baselineId) {
//		List<Object> paramList = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT *");
//		sql.append(" FROM");
//		if (PDMSCommon.isNotNull(baselineId)) {
//			sql.append(" V_PM_FUNCTION_BASELINE");
//			sql.append(" WHERE");
//			sql.append(" BASELINE_ID = ? AND");
//			paramList.add(baselineId);
//		} else {
//			sql.append(" V_PM_FUNCTION");
//			sql.append(" WHERE");
//		}
//		sql.append(" PARENT_OBS_ID = ?");
//		paramList.add(obsId);
//		sql.append(" ORDER BY");
//		sql.append(" SEQ_NO");
//		
//        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
//    }
	
	/**
	 * 时程表画面项目拖动保存
	 */
	public void saveDragDropEvent(Map<String, String> model,
			List<Map<String, String>> mileStonesModel) {
		List<Object> paramList = null;
		StringBuffer sql = null;
		// 登录用户信息取得
		String loginUser = SessionData.getLoginUserId();
		
		// 拖动节点时
		if (PDMSCommon.isNotNull(model.get("Date"))) {
			// 时间验证
			this.pkgPmDBProcedureServcie.pmTaskValidator(model.get("TaskId"),
					DateUtils.strToDate(model.get("Date")),
					DateUtils.strToDate(model.get("Date")));
		}
		
		// 更新节点(或活动)信息
		sql = new StringBuffer();
		paramList = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" PLANNED_START_DATE = ?");
		sql.append(",PLANNED_FINISH_DATE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		if (PDMSCommon.isNotNull(model.get("Date"))) {
			paramList.add(DateUtils.strToDate(model.get("Date")));
			paramList.add(DateUtils.strToDate(model.get("Date")));
		} else {
			paramList.add(DateUtils.strToDate(model.get("StartDate")));
			paramList.add(DateUtils.strToDate(model.get("EndDate")));
		}
		paramList.add(loginUser);
		paramList.add(model.get("TaskId"));
		jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		// 更新活动关联的节点信息
		for (Map<String, String> msModel : mileStonesModel) {
//			// 时间验证
//			this.pkgPmDBProcedureServcie.pmTaskValidator(msModel.get("TaskId"),
//					DateUtils.strToDate(msModel.get("Date")),
//					DateUtils.strToDate(msModel.get("Date")));
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" UPDATE PM_TASK SET");
			sql.append(" PLANNED_START_DATE = ?");
			sql.append(",PLANNED_FINISH_DATE = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			paramList.add(DateUtils.strToDate(msModel.get("Date")));
			paramList.add(DateUtils.strToDate(msModel.get("Date")));
			paramList.add(loginUser);
			paramList.add(msModel.get("TaskId"));
			jdbcTemplate.update(sql.toString(), paramList.toArray());
		}
	}
	
	/**
	 * 时程表画面拖动活动的开始/结束时间保存
	 */
	public void saveDragDropActivityEvent(Map<String, String> model,
			List<Map<String, String>> mileStonesModel,
			List<Map<String, String>>invalidMileStonesModel) {
		List<Object> paramList = null;
		StringBuffer sql = null;
		// 登录用户信息取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新活动信息
		sql = new StringBuffer();
		paramList = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" PLANNED_START_DATE = ?");
		sql.append(",PLANNED_FINISH_DATE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		paramList.add(DateUtils.strToDate(model.get("StartDate")));
		paramList.add(DateUtils.strToDate(model.get("EndDate")));
		paramList.add(loginUser);
		paramList.add(model.get("TaskId"));
		jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		// 更新活动关联的节点信息
		for (Map<String, String> msModel : mileStonesModel) {
//			// 时间验证
//			this.pkgPmDBProcedureServcie.pmTaskValidator(msModel.get("TaskId"),
//					DateUtils.strToDate(msModel.get("Date")),
//					DateUtils.strToDate(msModel.get("Date")));
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" UPDATE PM_TASK SET");
			sql.append(" PLANNED_START_DATE = ?");
			sql.append(",PLANNED_FINISH_DATE = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			paramList.add(DateUtils.strToDate(msModel.get("Date")));
			paramList.add(DateUtils.strToDate(msModel.get("Date")));
			paramList.add(loginUser);
			paramList.add(msModel.get("TaskId"));
			jdbcTemplate.update(sql.toString(), paramList.toArray());
		}
		
		// 删除活动关联外的节点信息
		for (Map<String, String> msModel : invalidMileStonesModel) {
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" UPDATE PM_TASK SET");
			sql.append(" DELETE_BY = ?");
			sql.append(",DELETE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			paramList.add(loginUser);
			paramList.add(msModel.get("TaskId"));
			jdbcTemplate.update(sql.toString(), paramList.toArray());
		}
	}
	
	/**
	 * 时程表画面Event编辑保存
	 */
	public void saveEditEvent(Map<String, String> model) {
		List<Object> paramList = null;
		StringBuffer sql = null;
		// 登录用户信息取得
		String loginUser = SessionData.getLoginUserId();
		
		// 时间验证
		this.pkgPmDBProcedureServcie.pmTaskValidator(model.get("taskId"),
				DateUtils.strToDate(model.get("date")),
				DateUtils.strToDate(model.get("date")));
		
		// 更新节点(或活动)信息
		sql = new StringBuffer();
		paramList = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" TASK_NAME = ?");
		paramList.add(model.get("taskName"));
		sql.append(",PLANNED_START_DATE = ?");
		sql.append(",PLANNED_FINISH_DATE = ?");
		paramList.add(DateUtils.strToDate(model.get("date")));
		paramList.add(DateUtils.strToDate(model.get("date")));
		if (PDMSConstants.TASK_TYPE_MAIN_NODE.equals(model.get("taskType"))) {
			sql.append(",TITLE_DISP_LOCATION_CODE = ?");
			paramList.add(model.get("titleDispLocation"));
		}
		
//		if (! PDMSConstants.TASK_TYPE_ACTIVITY.equals(model.get("taskType"))) {
//			paramList.add(DateUtils.strToDate(model.get("date")));
//			paramList.add(DateUtils.strToDate(model.get("date")));
//		} else {
//			paramList.add(DateUtils.strToDate(model.get("plannedStartDate")));
//			paramList.add(DateUtils.strToDate(model.get("plannedFinishDate")));
//		}
//		if (PDMSConstants.TASK_TYPE_NODE.equals(model.get("taskType"))) {
//			sql.append(",PARENT_ID = ?");
//			sql.append(",RELATION_TASK_ID = ?");
//			paramList.add(model.get("parentTaskId"));
//			paramList.add(model.get("relationTaskId"));
//		}
//		if (PDMSConstants.TASK_TYPE_NODE.equals(model.get("taskType")) ||
//				PDMSConstants.TASK_TYPE_MAIN_NODE.equals(model.get("taskType"))) {
//			sql.append(",TITLE_DISP_LOCATION_CODE = ?");
//			paramList.add(model.get("titleDispLocation"));
//		}
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		paramList.add(loginUser);
		paramList.add(model.get("taskId"));
		jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 时程表画面Resource编辑保存
	 */
	public void saveEditResource(Map<String, String> model) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 更新数据
		sql.append(" UPDATE PM_FUNCTION SET");
		sql.append(" DISPLAY_NAME = ?");
		sql.append(",SEQ_NO = ?");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		paramList.add(model.get("displayName"));
		paramList.add(model.get("seqNo"));
		paramList.add(model.get("functionId"));
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 基线类型判定
	 */
	private String resetBaselineId(String baselineId) {
		if (PDMSCommon.isNull(baselineId)) {
			return baselineId;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BASELINE_TYPE FROM PM_BASELINE WHERE ID = ?");
		Map<String, Object> blMap = null;
		try {
			blMap = this.jdbcTemplate.queryForMap(sql.toString(), baselineId);
		} catch(EmptyResultDataAccessException ex) {
			blMap = new HashMap<String, Object>();
		}
		String baselineType = PDMSCommon.nvl(blMap.get("BASELINE_TYPE"));
		// 如果是二级计划基线，则使用当前计划SOP
		if (baselineType.equals(PDMSConstants.BASELINE_TYPE_FN)) {
			return null;
		}
		return baselineId;
	}
}
