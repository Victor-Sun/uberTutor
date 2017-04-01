package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class DeptworkspaceService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PrivilegeService privilegeService;

	public List<Map<String, Object>> getDeptmanagementOpenIssueGridService(String issueId){
		return jdbcTemplate.queryForList("");
    }
	
	public List<Map<String, Object>> getDeptProjectTaskService(String departmentId){
		return jdbcTemplate.queryForList("select * from V_PM_TASK_DEPT_PM_RPT where TOP_DEPARTMENT_ID=?",departmentId);
    }
	
	public List<Map<String, Object>> getDeptOpenIssueBarChart(String userId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_DEPT_PM_RPT");
    }

	/**
	 * 取得部门空间-任务列表数据
	 */
	public GTPage<Map<String, Object>> getDeptProjectTaskList(
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		// 判断是否是领导
		if (this.privilegeService.canViewAllProgram(loginUser)) {
			sql.append(" SELECT T1.*");
			sql.append(" FROM");
			sql.append(" V_PM_TASK_DEPT T1");
			sql.append(" WHERE");
			sql.append(" 1 = 1");
		} else {
			sql.append(" SELECT T1.*");
			sql.append(" FROM");
			sql.append(" V_PM_TASK_DEPT T1 JOIN(");
			sql.append(" SELECT DT.TOP_DEPARTMENT_ID");
			sql.append(" FROM ");
			sql.append(" SYS_USER U, V_SYS_DEPARTMENT_TREE DT");
			sql.append(" WHERE");
			sql.append(" U.DEPARTMENT_ID = DT.ID");
			sql.append(" AND U.ID = ?");
			sql.append(" )");
			sql.append(" T2 ON T1.TOP_DEPARTMENT_ID = T2.TOP_DEPARTMENT_ID");
			sql.append(" WHERE");
			sql.append(" 1 = 1");
			params.add(loginUser);
		}
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 任务名称
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(T1.TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			// 计划 完成时间From
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(T1.PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			// 计划完成时间To
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(T1.PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			// 实际完成时间From
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateFrom"))) {
				sql.append(" AND TO_CHAR(T1.ACTUAL_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateFrom"))));
			}
			// 实际完成时间To
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateTo"))) {
				sql.append(" AND TO_CHAR(T1.ACTUAL_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateTo"))));
			}
			// 项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND T1.PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND T1.PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 责任人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(T1.TASK_OWNER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 任务状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchNotStart"))) {
				subParams.add(PDMSConstants.TASK_STATUS_NOT_START);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.TASK_STATUS_IN_PROCESS);
			}
			if ("true".equals(searchModel.get("searchComplete"))) {
				subParams.add(PDMSConstants.TASK_STATUS_CLOSED);
			}
			if (subParams.size() > 0) {
				sql.append(" AND T1.TASK_STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND T1.TASK_STATUS_CODE IS NULL");
			}
			
			// 进展状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchProgress_1"))) {
				subParams.add("GREEN");
				subParams.add("GRAY");
			}
			if ("true".equals(searchModel.get("searchProgress_2"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchProgress_3"))) {
				subParams.add("RED");
			}
			if (subParams.size() > 0) {
				sql.append(" AND T1.PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND T1.PROGRESS_STATUS IS NULL");
			}
			
			// 风险状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchRisk_1"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
			}
			if ("true".equals(searchModel.get("searchRisk_2"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_GY);
			}
			if ("true".equals(searchModel.get("searchRisk_3"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_Y);
			}
			if ("true".equals(searchModel.get("searchRisk_4"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_R);
			}
			if ("true".equals(searchModel.get("searchRisk_5"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_B);
			}
			if (subParams.size() < 5) {
				if (subParams.size() > 0) {
					sql.append(" AND T1.TASK_PROGRESS_STATUS_CODE IN(");
					for (int i = 0; i < subParams.size(); i++) {
						sql.append("?");
						params.add(subParams.get(i));
						if (i != subParams.size() - 1) {
							sql.append(", ");
						}
					}
					sql.append(")");
				} else {
					sql.append(" AND T1.TASK_PROGRESS_STATUS_CODE = ?");
					params.add(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
				}
			}
		}
		sql.append(" ORDER BY T1.PLANNED_FINISH_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
}
