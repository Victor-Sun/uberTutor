package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.page.SqlBuilder;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class MyCooperationService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/**
	 * 取得我参与的任务-部门OpenIssue列表
	 */
	public GTPage<Map<String, Object>> getMyCooperationDeptIssue(
			Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID IN(");
		sql.append(" SELECT DISTINCT ISSUE_ID FROM PM_DEPT_ISSUE_MEMBER WHERE USER_ID = ?");
		sql.append(" )");
		params.add(loginUser);
		sql.append(" AND STATUS_CODE <> ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTite"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTite") + "%");
			}
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
			}
			if ("true".equals(searchModel.get("searchComplete"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_COMPLETE);
			}
			if (subParams.size() > 0) {
				sql.append(" AND STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND STATUS_CODE IS NULL");
			}
			// 计划完成时间From
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			// 计划完成时间To
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			// 实际完成时间From
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateFrom"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateFrom"))));
			}
			// 实际完成时间To
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateTo"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateTo"))));
			}
			// 责任部门
			if (PDMSCommon.isNotNull(searchModel.get("searchRespDept"))) {
				sql.append(" AND UPPER(RESP_DEPT_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRespDept") + "%");
			}
			// 责任人
			if (PDMSCommon.isNotNull(searchModel.get("searchRespUser"))) {
				sql.append(" AND UPPER(RESP_USER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRespUser") + "%");
			}
			// 录入人
			if (PDMSCommon.isNotNull(searchModel.get("searchCreateBy"))) {
				sql.append(" AND UPPER(CREATE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchCreateBy") + "%");
			}
			// 提出人
			if (PDMSCommon.isNotNull(searchModel.get("searchRaiseBy"))) {
				sql.append(" AND UPPER(RAISE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRaiseBy") + "%");
			}
			// 问题来源
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueSource"))) {
				sql.append(" AND ISSUE_SOURCE_ID = ?");
				params.add(searchModel.get("searchIssueSource"));
			}
			// 进展状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchRed"))) {
				subParams.add("RED");
			}
			if ("true".equals(searchModel.get("searchYellow"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchGray"))) {
				subParams.add("GRAY");
			}
			if ("true".equals(searchModel.get("searchGreen"))) {
				subParams.add("GREEN");
			}
			if (subParams.size() > 0) {
				sql.append(" AND PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PROGRESS_STATUS IS NULL");
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " RAISE_DATE ", "ASC"));
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 取得我参与的任务-项目OpenIssue列表
	 */
	public GTPage<Map<String, Object>> getMyCooperationPmIssue(
			Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_ISSUE");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID IN(");
		sql.append(" SELECT DISTINCT ISSUE_ID FROM PM_ISSUE_MEMBER WHERE USER_ID = ?");
		sql.append(" )");
		params.add(loginUser);
		sql.append(" AND STATUS_CODE <> ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 问题标题
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTite"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTite") + "%");
			}
			// 状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
			}
			if ("true".equals(searchModel.get("searchComplete"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_COMPLETE);
			}
			if (subParams.size() > 0) {
				sql.append(" AND STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND STATUS_CODE IS NULL");
			}
			// 计划开始日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			// 计划开始日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			// 实际完成日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateFrom"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateFrom"))));
			}
			// 实际完成日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateTo"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateTo"))));
			}
			// 项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 责任组
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObs"))) {
				sql.append(" AND UPPER(RESP_OBS_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRespObs") + "%");
			}
			// 责任人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(RESP_USER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 录入人
			if (PDMSCommon.isNotNull(searchModel.get("searchCreateBy"))) {
				sql.append(" AND UPPER(CREATE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchCreateBy") + "%");
			}
			// 提出人
			if (PDMSCommon.isNotNull(searchModel.get("searchRaiseBy"))) {
				sql.append(" AND UPPER(RAISE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRaiseBy") + "%");
			}
			// 问题来源
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueSource"))) {
				sql.append(" AND ISSUE_SOURCE_ID = ?");
				params.add(searchModel.get("searchIssueSource"));
			}
			// 进展状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchRed"))) {
				subParams.add("RED");
			}
			if ("true".equals(searchModel.get("searchYellow"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchGray"))) {
				subParams.add("GRAY");
			}
			if ("true".equals(searchModel.get("searchGreen"))) {
				subParams.add("GREEN");
			}
			if (subParams.size() > 0) {
				sql.append(" AND PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PROGRESS_STATUS IS NULL");
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE,ISSUE_ID ", "DESC"));
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
}
