package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.constant.PDMSConstants;


@Service
@Transactional
public class PmReportService {
	
	public static final String KEY_ACTUAL_COMPLETE_COUNT="ACTUAL_COMPLETE_COUNT";//实际完成任务数
	public static final String KEY_PLAN_COMPLETE_COUNT="PLAN_COMPLETE_COUNT";//计划完成任务数
	public static final String KEY_TOTAL_COUNT="TOTAL_COUNT";//任务总数
	public static final String KEY_CODE="CODE";//项目编号

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 节点完成率报表
	 */
	public List<Map<String,Object>> getTaskCompleteCount() {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT");
		sql.append(" (T2.CODE || '-' || T3.VEHICLE_CODE) CODE");
		sql.append(",COUNT (CASE WHEN TASK_STATUS_CODE = ? THEN 1 ELSE NULL END) ACTUAL_COMPLETE_COUNT");
		sql.append(",COUNT (CASE WHEN T1.PLANNED_FINISH_DATE <= SYSDATE THEN 1 ELSE NULL END) PLAN_COMPLETE_COUNT");
		sql.append(",COUNT (T1.ID) TOTAL_COUNT");
		sql.append(" FROM");
		sql.append(" PM_TASK T1");
		sql.append(",PM_PROGRAM T2");
		sql.append(",PM_PROGRAM_VEHICLE T3");
		sql.append(" WHERE");
		sql.append(" T1.PROGRAM_ID = T2.ID");
		sql.append(" AND T2.ID = T3.PROGRAM_ID");
		sql.append(" AND T1.TASK_TYPE_CODE = ?");
		sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, T3.ID) = 1");
		sql.append(" AND T3.LIFECYCLE_STATUS = ?");
		sql.append(" GROUP BY T2.CODE, T3.VEHICLE_CODE");
		sql.append(" ORDER BY T2.CODE, T3.VEHICLE_CODE");
		paramList.add(PDMSConstants.TASK_STATUS_CLOSED);
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
		paramList.add(loginUser);
		paramList.add(PDMSConstants.LIFECYCLE_STATUS_IN_PROCESS);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}
}
