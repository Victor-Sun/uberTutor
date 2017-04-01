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
public class ProjectNodeListService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/**
	 * 节点管理清单信息取得
	 */
	public GTPage<Map<String, Object>> getProjectNodeList(String programVehicleId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
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
		sql.append(" AND PKG_PERMISSION.CAN_VIEW_OBS(?, OBS_ID) = 1");
		paramList.add(programVehicleId);
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
		paramList.add(loginUser);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				paramList.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObsId"))) {
				sql.append(" AND FUNCTION_OBS_ID = ?");
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
			// 主计划、二级计划
			List<Long> subParams = new ArrayList<Long>();
			if ("true".equals(searchModel.get("searchPlanLevel_1"))) {
				subParams.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
			}
			if ("true".equals(searchModel.get("searchPlanLevel_2"))) {
				subParams.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
			}
			if (subParams.size() > 0) {
				sql.append(" AND PLAN_LEVEL IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					paramList.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PLAN_LEVEL IS NULL");
			}
		}
		sql.append(" ORDER BY");
		sql.append(" FUNCTION_OBS_NAME");
		sql.append(",OBS_NAME");
		sql.append(",PLANNED_FINISH_DATE");
		
		// 查询
		return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
				paramList.toArray());
    }
}