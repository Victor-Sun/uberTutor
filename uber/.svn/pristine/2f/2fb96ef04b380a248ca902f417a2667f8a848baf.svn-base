package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;

@Service
@Transactional
public class TimeLineSettingService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 泳道一览取得
	 */
	public List<Map<String, Object>> getLaneList(
			String vehicleId, String parentObsId){
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM V_PM_FUNCTION");
		sql.append(" WHERE");
		if (PDMSCommon.isNull(parentObsId)) {
			sql.append(" PARENT_OBS_ID = PKG_PM_OBS.ROOT_OBS_ID(?)");
			paramList.add(vehicleId);
		} else {
			sql.append(" PARENT_OBS_ID = ?");
			paramList.add(parentObsId);
		}
		sql.append(" ORDER BY");
		sql.append(" SEQ_NO");
        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/**
	 * 新增泳道
	 */
	public void insertLane(String programId, String vehicleId, String parentObsId,
			Map<String, String> model) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 泳道信息新增
		String functionId = PDMSCommon.generateUUID();
		sql.append(" INSERT INTO PM_FUNCTION(");
		sql.append(" ID");
		sql.append(",PROGRAM_ID");
		sql.append(",PROGRAM_VEHICLE_ID");
		sql.append(",PARENT_OBS_ID");
		sql.append(",CHILD_OBS_ID");
		sql.append(",DISPLAY_NAME");
		sql.append(",SEQ_NO");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		if (PDMSCommon.isNotNull(parentObsId)) {
			sql.append(",?");
		} else {
			sql.append(",PKG_PM_OBS.ROOT_OBS_ID(?)");
		}
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(")");
		paramList.add(functionId);
		paramList.add(programId);
		paramList.add(vehicleId);
		if (PDMSCommon.isNotNull(parentObsId)) {
			paramList.add(parentObsId);
		} else {
			paramList.add(vehicleId);
		}
		paramList.add(model.get("childObsId"));
		paramList.add(model.get("displayName"));
		paramList.add(model.get("seqNo"));
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		// 关联组织任务信息新增
		sql = new StringBuffer();
		paramList = new ArrayList<Object>();
		sql.append(" INSERT INTO PM_FUNCTION_TASK (");
		sql.append(" FUNCTION_ID");
		sql.append(",TASK_ID");
		sql.append(")SELECT");
		sql.append(" '" + functionId + "'");
		sql.append(",ID");
		sql.append(" FROM");
		sql.append(" PM_TASK");
		sql.append(" WHERE");
		sql.append(" OBS_ID = ?");
		paramList.add(model.get("childObsId"));
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 更新泳道
	 */
	public void updateLane(Map<String, String> model) {
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
		paramList.add(model.get("id"));
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 删除泳道
	 */
	public void deleteLane(String functionId) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		// 删除泳道信息
		sql.append(" DELETE FROM PM_FUNCTION");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		paramList.add(functionId);
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		// 删除泳道节点信息
		sql = new StringBuffer();
		paramList = new ArrayList<Object>();
		sql.append(" DELETE FROM PM_FUNCTION_TASK");
		sql.append(" WHERE");
		sql.append(" FUNCTION_ID = ?");
		paramList.add(functionId);
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 泳道任务一览
	 */
	public List<Map<String, Object>> getFunctionTaskList(
			String functionId, String obsId) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" T1.ID");
		sql.append(",T1.TASK_NAME");
		sql.append(",T1.PLANNED_START_DATE");
		sql.append(",T1.PLANNED_FINISH_DATE");
		sql.append(",T2.FUNCTION_ID");
		sql.append(" FROM");
		sql.append(" PM_TASK T1");
		sql.append(" LEFT JOIN PM_FUNCTION_TASK T2 ON T1.ID = T2.TASK_ID");
		sql.append(" AND T2.FUNCTION_ID = ?");
		sql.append(" WHERE");
		sql.append(" T1.OBS_ID = ?");
		sql.append(" ORDER BY");
		sql.append(" PLANNED_FINISH_DATE");
		paramList.add(functionId);
		paramList.add(obsId);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 泳道任务信息更新
	 */
	public void updateFunctionTask(String functionId, String taskId, boolean displayFlg) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		if (displayFlg) {
			// 添加任务
			sql.append(" INSERT INTO PM_FUNCTION_TASK(");
			sql.append(" FUNCTION_ID");
			sql.append(",TASK_ID");
			sql.append(") VALUES (");
			sql.append(" ?");
			sql.append(",?");
			sql.append(")");
		} else {
			// 删除任务
			sql.append(" DELETE FROM PM_FUNCTION_TASK");
			sql.append(" WHERE");
			sql.append(" FUNCTION_ID = ?");
			sql.append(" AND TASK_ID = ?");
		}
		paramList.add(functionId);
		paramList.add(taskId);
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
	}
}
