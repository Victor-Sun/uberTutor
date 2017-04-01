package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.service.SysUserDepartmentService;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMDeptIssueKBDAO;
import com.gnomon.pdms.entity.PMDeptIssueKBEntity;
import com.gnomon.pdms.procedure.PkgPmDeptIssueDBProcedureServcie;

@Service
@Transactional
public class DeptIssueService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmDeptIssueDBProcedureServcie pkgPmDeptIssueDBProcedureServcie;
	
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;
	
	@Autowired
	private PMDeptIssueKBDAO pmDeptIssueKBDAO;
	
	/**
	 * 取得部门自建任务列表数据
	 */
	public GTPage<Map<String, Object>> getDeptIssueList(
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_DEPT_ISSUE(?, ISSUE_ID) = 1");
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
		sql.append(" ORDER BY RAISE_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 取得部门自建任务表单数据
	 */
	public Map<String, Object> getDeptIssue(String issueId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_INFO");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		params.add(issueId);
		// 返回查询结果
        return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}

	/**
	 * 提交/保存问题信息
	 */
	public Long saveDeptIssue(String action, Map<String, String> model) {
		// 批示
		String ownerComment = model.get("issueCause");
		String returnComment = null;
		if ("RETURN".equals(action)) {
			ownerComment = null;
			returnComment = model.get("issueCause");
		}
		// 取得录入人科室
		String subDeptId = SessionData.getUserDeptId();
		// 保存信息
		Map<String, Object> result = this.pkgPmDeptIssueDBProcedureServcie.updateIssue(
				PDMSCommon.toLong(model.get("id")), model.get("createBy"), model.get("deptId"), subDeptId,
				PDMSCommon.toLong(PDMSCommon.nvl(model.get("issueSourceId"), "0")), model.get("issueSourceDescription"),
				model.get("issueTitle"), model.get("issueDescription"), model.get("issueCause"),
				DateUtils.strToDate(model.get("dueDate")), model.get("raiseBy"),
				DateUtils.strToDate(model.get("raiseDate")), model.get("respDeptId"), model.get("respUserId"),
				model.get("issuePriorityCode"), action, ownerComment, returnComment);
		// 返回IssueId
		return Long.valueOf(PDMSCommon.nvl(result.get(PkgPmDeptIssueDBProcedureServcie.KEY_ISSUE_ID)));		
	}
	
	/**
	 * 保存草稿
	 */
	public Long saveDraftDeptIssue(Map<String, String> model) {
		// 取得录入人科室
		String subDeptId = SessionData.getUserDeptId();
		// 保存信息
		Map<String, Object> result = this.pkgPmDeptIssueDBProcedureServcie.updateDraftIssue(
				PDMSCommon.toLong(model.get("id")), model.get("createBy"), model.get("deptId"), subDeptId,
				PDMSCommon.toLong(PDMSCommon.nvl(model.get("issueSourceId"), "0")), model.get("issueSourceDescription"),
				model.get("issueTitle"), model.get("issueDescription"), model.get("issueCause"),
				DateUtils.strToDate(model.get("dueDate")), model.get("raiseBy"),
				DateUtils.strToDate(model.get("raiseDate")), model.get("respDeptId"), model.get("respUserId"),
				model.get("issuePriorityCode"));
		// 返回IssueId
		return Long.valueOf(PDMSCommon.nvl(result.get(PkgPmDeptIssueDBProcedureServcie.KEY_ISSUE_ID)));	
	}
	
	/**
	 * 取得问题来源
	 */
	public List<Map<String, Object>> getDeptIssueSourceList() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" PM_DEPT_ISSUE_SOURCE");
		sql.append(" WHERE");
		sql.append(" IS_ACTIVE = ?");

		// 返回查询结果
        return this.jdbcTemplate.queryForList(sql.toString(), PDMSConstants.STATUS_Y);
	}
	
	/**
	 * 取得归档问题信息
	 */
	public List<Map<String, Object>> getDeptIssueKBList(String title) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_KB");
		sql.append(" WHERE");
		sql.append(" UPPER(TITLE) LIKE UPPER(?)");

		// 返回查询结果
        return this.jdbcTemplate.queryForList(sql.toString(), "%" + title + "%");
	}
	
	/**
	 * 取得更新进度列表信息
	 */
	public List<Map<String, Object>> getDeptIssueNotesList(String issueId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_PROGRESS");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		sql.append(" ORDER BY ID");

		// 返回查询结果
        return this.jdbcTemplate.queryForList(sql.toString(), issueId);
	}
	
	/**
	 * 更新进度
	 */
	public void reportDeptIssueProgress(String issueId, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新进度
		this.pkgPmDeptIssueDBProcedureServcie.reportIssueProgress(
				Long.valueOf(issueId), loginUser, model.get("remark"));
	}
	
	/**
	 * 取得责任人下拉列表信息
	 */
	public List<Map<String, Object>> getRespUserList(String issueId) {
		// 取得责任部门ID
		String respDeptId = "";
		if (PDMSCommon.isNotNull(issueId)) {
			Map<String, Object> issueInfo = this.getDeptIssue(issueId);
			respDeptId = PDMSCommon.nvl(issueInfo.get("RESP_DEPT_ID"));
		} else {
			// 新建问题、取得LoginUser的所在部门
			Map<String, Object> userInfo =
					this.sysUserDepartmentService.getDeptUserInfo(
							SessionData.getLoginUserId());
			respDeptId = PDMSCommon.nvl(userInfo.get("TOP_DEPARTMENT_ID"));
		}
		// 返回查询结果
        return this.sysUserDepartmentService.getSysUserList(respDeptId, true, null, null);
	}
	
	/**
	 * 取得提出人科室下拉列表信息
	 */
	public List<Map<String, Object>> getSubDeptList(String issueId) {
		// 取得问题部门ID
		String deptId = "";
		if (PDMSCommon.isNotNull(issueId)) {
			Map<String, Object> issueInfo = this.getDeptIssue(issueId);
			deptId = PDMSCommon.nvl(issueInfo.get("DEPT_ID"));
		} else {
			// 新建问题、取得LoginUser的所在部门
			Map<String, Object> userInfo =
					this.sysUserDepartmentService.getDeptUserInfo(
							SessionData.getLoginUserId());
			deptId = PDMSCommon.nvl(userInfo.get("TOP_DEPARTMENT_ID"));
		}
		// 返回查询结果
        return this.sysUserDepartmentService.getDeptListByRootDeptId(deptId);
	}
	
	/**
	 * 取得讨论组成员列表信息
	 */
	public List<Map<String, Object>> getDeptIssueMemberList(String issueId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_MEMBER");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");

		// 返回查询结果
        return this.jdbcTemplate.queryForList(sql.toString(), issueId);
	}
	
	/**
	 * 增加讨论组成员
	 */
	public void addDeptIssueMember(String issueId, List<Map<String, String>> modelList){
		for(Map<String, String> model : modelList){
			this.pkgPmDeptIssueDBProcedureServcie.addMember(
					Long.valueOf(issueId), model.get("id"));
		}
	}
	
	/**
	 * 归档
	 */
	public void saveArchiveDeptIssue(String issueId, Map<String, String> model) {
		String loginUser = SessionData.getLoginUserId();
		PMDeptIssueKBEntity entity = new PMDeptIssueKBEntity();
		entity.setOrigIssueId(Long.valueOf(issueId));
		entity.setTitle(model.get("issueTitle"));
		entity.setIssueDescription(model.get("issueDescription"));
		entity.setIssueCause(model.get("issueCause"));
		entity.setIssueResolution(model.get("issueResolution"));
		entity.setOperatorId(loginUser);
		entity.setCreateDate(new Date());
		entity.setUpdateDate(new Date());
		this.pmDeptIssueKBDAO.save(entity);
	}
	
	/**
	 * 取得部门自建任务归档列表数据
	 */
	public GTPage<Map<String, Object>> getArchiveDeptIssueList(
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_KB");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_DEPT_ISSUE(?, ISSUE_ID) = 1");
		params.add(loginUser);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTitle"))) {
				sql.append(" AND UPPER(TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTitle") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDateFrom"))) {
				sql.append(" AND TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDateTo"))) {
				sql.append(" AND TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDateTo"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchUser"))) {
				sql.append(" AND UPPER(USER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchUser") + "%");
			}
		}
		sql.append(" ORDER BY UPDATE_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 取得部门自建任务草稿列表数据
	 */
	public GTPage<Map<String, Object>> getDraftDeptIssueList(
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_INFO");
		sql.append(" WHERE");
		sql.append(" STATUS_CODE = ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		sql.append(" AND CREATE_BY = ?");
		params.add(loginUser);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTitle"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTitle") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
		}
		sql.append(" ORDER BY RAISE_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 删除草稿
	 */
	public void deleteDraftDeptIssue(String issueId) {
		this.pkgPmDeptIssueDBProcedureServcie.deleteDraftIssue(Long.valueOf(issueId));
	}
	
	/**
	 * 判断是否是讨论组成员
	 */
	public boolean isDeptIssueMember(String issueId, String userId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT COUNT(1)");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_MEMBER");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		sql.append(" AND USER_ID = ?");

		// 返回查询结果
        return this.jdbcTemplate.queryForInt(sql.toString(), issueId, userId) > 0;
	}
	
	/**
	 * 问题审批记录查询
	 */
	public List<Map<String, Object>> getProcessRecord(String issueId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T3.*");
		sql.append(" FROM PM_DEPT_ISSUE T1 INNER JOIN PM_PROCESS T2");
		sql.append(" ON T1.UUID = T2.SOURCE_TASK_ID");
		sql.append(" INNER JOIN V_PM_PROCESS_TASK T3");
		sql.append(" ON T2.ID = T3.PROCESS_ID");
		sql.append(" AND COMPLETE_FLAG = 'Y'");
		sql.append(" WHERE");
		sql.append(" T1.ID = ?");
		sql.append(" ORDER BY COMPLETE_DATE");
		
		return this.jdbcTemplate.queryForList(sql.toString(), issueId);
	}
}