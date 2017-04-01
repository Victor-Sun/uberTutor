package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class ProjectTaskListService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;

	/**
	 * 任务列表信息取得
	 */
	public GTPage<Map<String, Object>> getProjectTaskList(String programVehicleId,
			String obsId, Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_LIST");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE = ?");
		paramList.add(programVehicleId);
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND PLAN_LEVEL = 2");
			sql.append(" AND FUNCTION_OBS_ID = ?");
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_OBS(?, OBS_ID) = 1");
			paramList.add(obsId);
			paramList.add(loginUser);
		} else {
			sql.append(" AND PLAN_LEVEL = 1");
		}
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				paramList.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObsId"))) {
				sql.append(" AND OBS_ID = ?");
				paramList.add(searchModel.get("searchRespObsId"));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				paramList.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				paramList.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
		}		
		sql.append(" ORDER BY");
		sql.append(" OBS_NAME");
		sql.append(",PLANNED_FINISH_DATE");
		return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize, paramList.toArray());
    }
	
	public List<Map<String, Object>> getPreTaskList(String programVehicleId){
		return jdbcTemplate.queryForList("select * from V_PM_PRE_TASK_RELATION  where PROGRAM_VEHICLE_ID=?",programVehicleId);
	}
	
	public List<Map<String, Object>> getTaskList(String obsId){
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(obsId);
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
//		paramList.add(loginUser);
		return jdbcTemplate.queryForList("select * from V_PM_FUNCTION_TASK_LIST WHERE  TOP_OBS_ID = ? AND TASK_TYPE_CODE = ? ",paramList.toArray());
	}
	
	public List<Map<String, Object>> getMemberList(){
		return jdbcTemplate.queryForList("select * from SYS_USER ");
	}
	
	/**
	 * 计划延期记录列表取得
	 */
	public GTPage<Map<String, Object>> getExtensionTaskList(String programVehicleId,
			String obsId, Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_CHANGE_LOG");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		paramList.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND PLAN_LEVEL = 2");
			sql.append(" AND FUNCTION_OBS_ID = ?");
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_OBS(?, OBS_ID) = 1");
			paramList.add(obsId);
			paramList.add(loginUser);
		} else {
			sql.append(" AND PLAN_LEVEL = 1");
		}
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				paramList.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObsId"))) {
				sql.append(" AND OBS_ID = ?");
				paramList.add(searchModel.get("searchRespObsId"));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				paramList.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				paramList.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
		}		
		sql.append(" ORDER BY");
		sql.append(" OBS_NAME");
		sql.append(",TASK_OWNER");
		sql.append(",CREATE_DATE");
		return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize, paramList.toArray());
    }
}

