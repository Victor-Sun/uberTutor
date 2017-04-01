package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class DoneListService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;

	/**
	 * 已办任务列表取得
	 */
	public GTPage<Map<String, Object>> getDoneList(String userId, int bef,int pageNo,int pageSize) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_TODOLIST");
		sql.append(" WHERE");
		sql.append(" PROCESS_TASK_OWNER = ?");
		sql.append(" AND COMPLETE_FLAG = ?");
		params.add(userId);
		params.add(PDMSConstants.STATUS_Y);
		if (bef > 0) {
			sql.append(" AND ACTUAL_FINISH_DATE >= SYSDATE - ?");
			params.add(bef);
		}
		sql.append(" ORDER BY");
		sql.append(" TASK_PRIORITY_CODE DESC");
		sql.append(",PLANNED_FINISH_DATE");
		sql.append(",TASK_PROGRESS_CREATE_DATE");
		return jdbcTemplate.queryPagination(sql.toString(),pageNo,pageSize, params.toArray() );
	}
	
	/**
	 * 当前任务负责人取得
	 */
	public String getCurrentTaskOwner(String taskId, String processCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT T3.USERNAME");
		sql.append(" FROM");
		sql.append(" PM_PROCESS T1 INNER JOIN PM_PROCESS_TASK T2");
		sql.append(" ON T1.ID = T2.PROCESS_ID");
		sql.append(" AND T2.COMPLETE_FLAG = ?");
		sql.append(" LEFT JOIN SYS_USER T3");
		sql.append(" ON T2.TASK_OWNER = T3.ID");
		sql.append(" WHERE");
		sql.append(" T1.SOURCE_TASK_ID = ?");
		sql.append(" AND T1.PROCESS_CODE = ?");
		params.add(PDMSConstants.STATUS_N);
		params.add(taskId);
		params.add(processCode);
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
		if (list.size() > 0) {
			return PDMSCommon.nvl(list.get(0).get("USERNAME"));
		}
		return "";
	}
}
