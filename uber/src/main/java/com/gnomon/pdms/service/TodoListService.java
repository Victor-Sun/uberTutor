package com.gnomon.pdms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.page.SqlBuilder;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.DocumentIndexVMDAO;
import com.gnomon.pdms.dao.PMProcessTaskVMDAO;
import com.gnomon.pdms.dao.PMTaskListVMDAO;
import com.gnomon.pdms.dao.PMTaskTimereportVMDAO;
import com.gnomon.pdms.dao.PMTaskTodoListVMDAO;
import com.gnomon.pdms.dao.TaskDAO;
import com.gnomon.pdms.entity.PMProcessTaskVMEntity;
import com.gnomon.pdms.entity.PMTaskListVMEntity;
import com.gnomon.pdms.entity.PMTaskTimereportVMEntity;
import com.gnomon.pdms.entity.PMTaskTodoListVMEntity;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.procedure.PkgPmProcessDBProcedureServcie;

@Service
@Transactional
public class TodoListService {

	@Autowired
	private PMTaskTodoListVMDAO pmTaskTodoListVMDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private DocumentIndexVMDAO documentIndexVMDAO;
	
	@Autowired
	private PMProcessTaskVMDAO pmProcessTaskVMDAO;

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PMTaskTimereportVMDAO pmTaskTimereportVMDAO;
	
	@Autowired
	private PMTaskListVMDAO pmTaskListVMDAO;

	@Autowired
	private PkgPmProcessDBProcedureServcie pkgPmProcessDBProcedureServcie;

	/**
	 * 待办任务列表取得（无分页）
	 */
	public List<Map<String, Object>> getTodoList(String userId, int bef) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_TODOLIST");
		sql.append(" WHERE");
		sql.append(" PROCESS_TASK_OWNER = ?");
		sql.append(" AND COMPLETE_FLAG = ?");
		params.add(userId);
		params.add(PDMSConstants.STATUS_N);
		if (bef > 0) {
			sql.append(" AND PLANNED_FINISH_DATE <= SYSDATE + ?");
			params.add(bef);
		}
		sql.append(" ORDER BY");
		sql.append(" TASK_PRIORITY_CODE DESC");
		sql.append(",PLANNED_FINISH_DATE");
		sql.append(",TASK_PROGRESS_CREATE_DATE");
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 待办任务列表取得
	 */
	public GTPage<Map<String, Object>> getTodoList(Map<String, String> searchModel,
			int pageNo, int pageSize,String filter,String sort) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_TODOLIST");
		sql.append(" WHERE");
		sql.append(" PROCESS_TASK_OWNER = ?");
		sql.append(" AND COMPLETE_FLAG = ?");
		params.add(loginUser);
		params.add(PDMSConstants.STATUS_N);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 平台项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 进度风险
			List<String> subParams = new ArrayList<String>();
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
					sql.append(" AND TASK_PROGRESS_STATUS_CODE IN(");
					for (int i = 0; i < subParams.size(); i++) {
						sql.append("?");
						params.add(subParams.get(i));
						if (i != subParams.size() - 1) {
							sql.append(", ");
						}
					}
					sql.append(")");
				} else {
					sql.append(" AND TASK_PROGRESS_STATUS_CODE = ?");
					params.add(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
				}
			}
			// 任务状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.TASK_STATUS_NOT_START);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.TASK_STATUS_IN_PROCESS);
			}
			if (subParams.size() > 0) {
				sql.append(" AND TASK_STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND TASK_STATUS_CODE NOT IN(?, ?)");
				params.add(PDMSConstants.TASK_STATUS_NOT_START);
				params.add(PDMSConstants.TASK_STATUS_IN_PROCESS);
			}
			// 任务名称
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			// 近X天
			if (PDMSCommon.isNotNull(searchModel.get("searchDaysVal"))) {
				int bef = Integer.parseInt(searchModel.get("searchDaysVal"));
				if (bef > 0) {
					sql.append(" AND PLANNED_FINISH_DATE <= SYSDATE + ?");
					params.add(bef);
				}
			}
			// 过期任务
			if (! "true".equals(searchModel.get("searchExpired"))) {
				sql.append(" AND PLANNED_FINISH_DATE >= SYSDATE");
			}
			// 计划完成日期
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " TASK_PRIORITY_CODE DESC,PLANNED_FINISH_DATE,TASK_PROGRESS_CREATE_DATE ", "DESC"));
//		sql.append(" ORDER BY");
//		sql.append(" TASK_PRIORITY_CODE DESC");
//		sql.append(",PLANNED_FINISH_DATE");
//		sql.append(",TASK_PROGRESS_CREATE_DATE");
		GTPage<Map<String, Object>> pageResult = jdbcTemplate.queryPagination(
				sql.toString(), pageNo, pageSize, params.toArray());
		return pageResult;
	}

	/**
	 * 待办任务表单信息取得
	 */
	public PMTaskTodoListVMEntity getTodoTaskInfo(Long processTaskId) {
		PMTaskTodoListVMEntity result = new PMTaskTodoListVMEntity();
		String hql = "from PMTaskTodoListVMEntity where id = ?";
		List<PMTaskTodoListVMEntity> list =
				this.pmTaskTodoListVMDAO.find(hql, processTaskId.toString());
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}
	
	/**
	 * 任务审批批示信息取得
	 */
	public List<PMProcessTaskVMEntity> getCommentInfo(Long processId) {
		String hql = "from PMProcessTaskVMEntity where"
				+ " completeFlag = 'Y' and processId = ?"
				+ " order by completeDate";
		List<PMProcessTaskVMEntity> result = 
				this.pmProcessTaskVMDAO.find(hql, processId);
		return result;
	}

	/**
	 * 待办任务交付物列表取得
	 */
	public List<Map<String, Object>> getDeliverableList(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE");
		sql.append(" WHERE");
		sql.append(" RESP_TASK_ID = ?");
		paramList.add(taskId);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}

	/**
	 * 任务完成提交专业经理审核
	 */
	public String completeTask(String taskId, Long processId) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新项目信息
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" PERCENT_COMPLETE = ?");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(0.8);
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 备注取得
		TaskEntity task = this.taskDAO.get(taskId);
		String memo = task.getMemo();
		
		// 提交专业经理审核
		String nextUser = this.comApproveTask(processId, memo);
		
		// 审批结束、任务完成
		if (PDMSCommon.isNull(nextUser)) {
			this.completeTask(taskId);
		}
		return nextUser;
	}
	
	/**
	 * 任务完成审核通过
	 */
	public String approveTask(String taskId, Long processId, String memo)
			throws SQLException {
		// 提交项目经理审核
		String nextUser = this.comApproveTask(processId, memo);
		
		// 审批结束、任务完成
		if (PDMSCommon.isNull(nextUser)) {
			this.completeTask(taskId);
		}
		return nextUser;
	}
	
	/**
	 * 任务完成审核不通过
	 */
	public String rejectTask(Long processStepId, Long processId,
						   String taskId, String memo) throws SQLException {
		// 流程退回
		return this.comRejectTask(processId, memo);
	}

	/**
	 * 待办任务开始
	 */
	public Map<String, Object> startTask(String taskId) {
		List<Object> params = null;
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 查询任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" PM_TASK");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(taskId);
		Map<String, Object> taskInfo =
				this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
		
		// 更新任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" ACTUAL_START_DATE = SYSDATE");
		sql.append(",TASK_STATUS_CODE = ?");
		if (PDMSCommon.isNull(PDMSCommon.nvl(
				taskInfo.get("TASK_PROGRESS_STATUS_CODE"))) ||
				PDMSConstants.TASK_PROGRESS_STATUS_GREY.equals(PDMSCommon.nvl(
						taskInfo.get("TASK_PROGRESS_STATUS_CODE")))) {
			sql.append(",TASK_PROGRESS_STATUS_CODE = ?");
		}		
		sql.append(",PERCENT_COMPLETE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(PDMSConstants.TASK_STATUS_IN_PROCESS);
		if (PDMSCommon.isNull(PDMSCommon.nvl(
				taskInfo.get("TASK_PROGRESS_STATUS_CODE"))) ||
				PDMSConstants.TASK_PROGRESS_STATUS_GREY.equals(PDMSCommon.nvl(
						taskInfo.get("TASK_PROGRESS_STATUS_CODE")))) {
			params.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
		}
		params.add(0.2);
		params.add(loginUser);
		params.add(taskId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 取得最新任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" PM_TASK");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(taskId);
		return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}
	
	/**
	 * 待办任务保存
	 */
	public void saveTask(String taskId, Map<String, String> model) {
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新任务信息
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" TASK_PRIORITY_CODE = ?");
		sql.append(",MEMO = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(model.get("taskPriorityCode"));
		params.add(model.get("memo"));
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 待办任务交付物备注信息保存
	 */
	public void saveDeliverable(String taskId, String deliverableId, String memo) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新交付物信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_DELIVERABLE SET");
		sql.append(" MEMO = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(memo);
		params.add(loginUser);
		params.add(deliverableId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更新任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 审批通过
	 */
	private String comApproveTask(Long processId, String memo) {
		// 提交项目经理审核
		Map<String, Object> result =
				this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
						SessionData.getLoginUserId(), processId,
						PkgPmProcessDBProcedureServcie.ACTION_NEXT, memo, null, null);
		return PDMSCommon.nvl(result.get(
				PkgPmProcessDBProcedureServcie.KEY_UPS_NEXT_STEP_USER_ID));
	}
	
	/**
	 * 审批不通过
	 */
	private String comRejectTask(Long processId, String memo) {
		// 返回给上一流程
		Map<String, Object> result =
				this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
						SessionData.getLoginUserId(), processId,
						PkgPmProcessDBProcedureServcie.ACTION_RETURN, null, memo, null);
		return PDMSCommon.nvl(result.get(
				PkgPmProcessDBProcedureServcie.KEY_UPS_NEXT_STEP_USER_ID));

	}
	
	/**
	 * 更新进度信息取得
	 */
	public List<PMTaskTimereportVMEntity> getTimeReportInfo(String taskId) {
		String hql = "FROM PMTaskTimereportVMEntity WHERE taskId= ? order by createDate desc";
		List<PMTaskTimereportVMEntity> result = 
				this.pmTaskTimereportVMDAO.find(hql, taskId);
		return result;
	}

	/**
	 * 延期变更记录信息取得
	 */
	public List<Map<String, Object>> getPmTaskExtensionLog(String taskId) {
		String sql = "SELECT * FROM V_PM_TASK_CHANGE_LOG WHERE TASK_ID = ? ORDER BY CREATE_DATE DESC";
		return jdbcTemplate.queryForList(sql,taskId);
	}
	
	/**
	 * 获取DocmentIndexId
	 */
	public String getDocmentIndexId (String docmentIndexId) {
		String sql = "SELECT ID FROM PM_DOCUMENT_INDEX WHERE SOURCE_ID = ?";
		try {
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, docmentIndexId);
			return PDMSCommon.nvl(result.get("ID"));
		} catch(EmptyResultDataAccessException ex) {
			return "";
		}
	}
	
	/**
	 * 任务信息取得
	 */
	public PMTaskListVMEntity getTaskInfo(String taskId) {
		return this.pmTaskListVMDAO.findUniqueBy("id", taskId);
	}
	
	/**
	 * 保存更新进度信息
	 */
	public Map<String, String> saveTimeReport(String taskId, Map<String, String> model) {
		Map<String, String> result = new HashMap<String, String>();
		List<Object> params = null;
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 进度
		BigDecimal pc = new BigDecimal(
				model.get("percentComplete")).divide(new BigDecimal(100));
		
		// 更新前进展状态取得
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" TASK_PROGRESS_STATUS_CODE");
		sql.append(" FROM");
		sql.append(" PM_TASK");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(taskId);
		Map<String, Object> taskInfo = this.jdbcTemplate.queryForMap(
				sql.toString(), params.toArray());
		
		
		// 增加更新进度信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" INSERT INTO PM_TASK_TIMEREPORT(");
		sql.append(" ID");
		sql.append(",TASK_ID");
		sql.append(",TASK_PROGRESS_STATUS_CODE");
		sql.append(",PERCENT_COMPLETE");
		sql.append(",MAN_HOUR");
		sql.append(",REMARK");
		sql.append(",CREATE_BY");
		sql.append(",CREATE_DATE");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",SYSDATE)");
		params.add(PDMSCommon.generateUUID());
		params.add(taskId);
		params.add(model.get("taskProgressStatusCode"));
		params.add(pc);
		params.add(model.get("manHours"));
		params.add(model.get("remark"));
		params.add(loginUser);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 当前任务信息取得
		PMTaskListVMEntity task = this.getTaskInfo(taskId);
		// 工时累计
		BigDecimal manHour = new BigDecimal(model.get("manHours"));
		if (task.getActualManhour() != null) {
			manHour = manHour.add(task.getActualManhour());
		}
		
		// 更改任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" TASK_PROGRESS_STATUS_CODE = ?");
		sql.append(",PERCENT_COMPLETE = ?");
		sql.append(",ACTUAL_MANHOUR = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(model.get("taskProgressStatusCode"));
		params.add(pc);
		params.add(manHour);
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 启动交付物状态审批流程
		if (! PDMSConstants.TASK_PROGRESS_STATUS_G.equals(
				model.get("taskProgressStatusCode")) &&
				! PDMSCommon.nvl(taskInfo.get("TASK_PROGRESS_STATUS_CODE")).equals(
						model.get("taskProgressStatusCode"))) {
			// 启动
			Map<String, Object> cnpResult =
					this.pkgPmProcessDBProcedureServcie.createNewProcess(
							PDMSConstants.PROCESS_CODE_PROGRESS_STATUS, taskId,
							loginUser, model.get("taskProgressStatusCode"));
			// Next
			int processId = Integer.parseInt(PDMSCommon.nvl(cnpResult.get(
					PkgPmProcessDBProcedureServcie.KEY_CNP_PROCESS_INSTANCE_ID)));
			Map<String, Object> upsResult =
					this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
							loginUser, processId, PkgPmProcessDBProcedureServcie.ACTION_NEXT,
							model.get("remark"), null, model.get("taskProgressStatusCode"));
			// 结果做成
			result.put("PROCESS_ID", String.valueOf(processId));
			result.put("NEXT_USER", PDMSCommon.nvl(upsResult.get(
					PkgPmProcessDBProcedureServcie.KEY_UPS_NEXT_STEP_USER_ID)));
		}
		return result;
	}
	
	public GTPage<Map<String, Object>> getTodoListExt(String userId,int pageNo, int pageSize) {
		GTPage<Map<String, Object>> pageResult = jdbcTemplate.queryPagination("select * from V_EXT_TASK where IS_COMPLETE ='N' and USER_ID=?", pageNo,pageSize, userId);
		return pageResult;
	}

	
	public GTPage<Map<String, Object>> getTodoListOpenIssue(Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_OPEN_ISSUE_TASK");
		sql.append(" WHERE");
		sql.append(" COMPLETE_FLAG = ?");
		sql.append(" AND STATUS_CODE <> ?");
		sql.append(" AND TASK_OWNER_ID = ?");
		params.add(PDMSConstants.STATUS_N);
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		params.add(loginUser);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 平台项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 进度状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchProgress_1"))) {
				subParams.add("GRAY");
				subParams.add("GREEN");
			}
			if ("true".equals(searchModel.get("searchProgress_2"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchProgress_3"))) {
				subParams.add("RED");
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
			// 问题状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
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
			// 问题标题
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTitle"))) {
				sql.append(" AND UPPER(TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTitle") + "%");
			}
			// 近X天
			if (PDMSCommon.isNotNull(searchModel.get("searchDaysVal"))) {
				int bef = Integer.parseInt(searchModel.get("searchDaysVal"));
				if (bef > 0) {
					sql.append(" AND DUE_DATE <= SYSDATE + ?");
					params.add(bef);
				}
			}
			// 过期任务
			if (! "true".equals(searchModel.get("searchExpired"))) {
				sql.append(" AND DUE_DATE >= SYSDATE");
			}
			// 计划完成日期
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE ", "DESC"));
//		sql.append(" ORDER BY ISSUE_PRIORITY_CODE DESC, DUE_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
		
	}
	
	public GTPage<Map<String, Object>> getTodoListWorkOrder(String userId,int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_WORK_ORDER_TASK  where COMPLETE_FLAG ='N' and TASK_OWNER_ID=?  ");
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " CREATE_DATE ", "DESC"));
		GTPage<Map<String, Object>> pageResult = jdbcTemplate.queryPagination(sql.toString(), pageNo,pageSize, userId);
		return pageResult;
	}
	
	/**
	 * 任务延期
	 */
	public String extensionTask(String taskId, Map<String, String> model) {
		List<Object> params = null;
		StringBuffer sql = new StringBuffer();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		String logId = PDMSCommon.generateUUID();
		
		// 增加更新进度信息
		sql.append(" INSERT INTO PM_TASK_CHANGE_LOG(");
		sql.append(" ID");
		sql.append(",TASK_ID");
		sql.append(",DELAY_DAYS");
		sql.append(",MEMO");
		sql.append(",CREATE_BY");
		sql.append(",CREATE_DATE");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",SYSDATE)");
		params = new ArrayList<Object>();
		params.add(logId);
		params.add(taskId);
		params.add(model.get("delayDays"));
		params.add(model.get("memo"));
		params.add(loginUser);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更改任务信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" PLANNED_FINISH_DATE = PLANNED_FINISH_DATE + ?");
		sql.append(",PLANNED_START_DATE = PLANNED_START_DATE + ?");
		sql.append(",DELAY_DAYS = NVL(DELAY_DAYS, 0) + ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(model.get("delayDays"));
		params.add(model.get("delayDays"));
		params.add(model.get("delayDays"));
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		return logId;
	}
	
	/**
	 * 更改交付物状态（临时）
	 */
	public void changeDeliverableStatus(Long processId, String deliverableStatus) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更改流程中的状态
		this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
				loginUser, processId, PkgPmProcessDBProcedureServcie.ACTION_SAVE,
				null, null, deliverableStatus);
	}
	
	/**
	 * 进展状态变更审核通过
	 */
	public String approveChangeStatus(String taskId, Long processId,
			String memo, String progressStatus) {
		List<Object> params = null;
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 提交审核
		Map<String, Object> result =
				this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
						loginUser, processId,
						PkgPmProcessDBProcedureServcie.ACTION_NEXT, memo, null,
						progressStatus);
		String nextUser = PDMSCommon.nvl(result.get(
				PkgPmProcessDBProcedureServcie.KEY_UPS_NEXT_STEP_USER_ID));
		
		// 审批结束、状态更改
		if (PDMSCommon.isNull(nextUser)) {
			// 更改交付物状态
			sql = new StringBuffer();
			params = new ArrayList<Object>();
			sql.append(" UPDATE PM_DELIVERABLE SET");
			sql.append(" TASK_PROGRESS_STATUS = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" RESP_TASK_ID = ?");
			params.add(progressStatus);
			params.add(loginUser);
			params.add(taskId);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
		return nextUser;
	}
	
	/**
	 * 进展状态变更审核不通过
	 */
	public String rejectChangeStatus(Long processStepId, Long processId,
						   String taskId, String memo, String progressStatus) {
		// 返回给上一流程
		Map<String, Object> result =
				this.pkgPmProcessDBProcedureServcie.updateProcessStatus(
						SessionData.getLoginUserId(), processId,
						PkgPmProcessDBProcedureServcie.ACTION_RETURN, null,
						memo, progressStatus);
		return PDMSCommon.nvl(result.get(
				PkgPmProcessDBProcedureServcie.KEY_UPS_NEXT_STEP_USER_ID));
	}
	
	/**
	 * 上传交付物附件CallBack
	 */
	public void uploadDeliverableFileCallBack(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更改任务信息
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 删除交付物附件CallBack
	 */
	public void deleteDeliverableFileCallBack(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更改任务信息
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 上传附件CallBack
	 */
	public void uploadAttachmentFileCallBack(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更改任务信息
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 删除附件CallBack
	 */
	public void deleteAttachmentFileCallBack(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更改任务信息
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",LAST_REPORT_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 完成任务
	 */
	private void completeTask(String taskId) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更改任务信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" ACTUAL_FINISH_DATE = SYSDATE");
		sql.append(",TASK_STATUS_CODE = ?");
		sql.append(",TASK_PROGRESS_STATUS_CODE = ?");
		sql.append(",PERCENT_COMPLETE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(PDMSConstants.TASK_STATUS_CLOSED);
		params.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
		params.add(1);
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更改关联交付物信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_DELIVERABLE SET");
		sql.append(" TASK_PROGRESS_STATUS = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" RESP_TASK_ID = ?");
		params.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 取得部门自建待办任务列表数据
	 */
	public GTPage<Map<String, Object>> getTodoListDeptIssue(
			Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_TASK");
		sql.append(" WHERE");
		sql.append(" COMPLETE_FLAG = ?");
		sql.append(" AND TASK_OWNER_ID = ?");
		params.add(PDMSConstants.STATUS_N);
		params.add(loginUser);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 科室
			if (PDMSCommon.isNotNull(searchModel.get("searchSubDept"))) {
				sql.append(" AND UPPER(SUB_DEPT_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchSubDept") + "%");
			}
			// 进度状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchProgress_1"))) {
				subParams.add("GRAY");
				subParams.add("GREEN");
			}
			if ("true".equals(searchModel.get("searchProgress_2"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchProgress_3"))) {
				subParams.add("RED");
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
			// 问题状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
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
			// 问题标题
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTitle"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTitle") + "%");
			}
			// 近X天
			if (PDMSCommon.isNotNull(searchModel.get("searchDaysVal"))) {
				int bef = Integer.parseInt(searchModel.get("searchDaysVal"));
				if (bef > 0) {
					sql.append(" AND DUE_DATE <= SYSDATE + ?");
					params.add(bef);
				}
			}
			// 过期任务
			if (! "true".equals(searchModel.get("searchExpired"))) {
				sql.append(" AND DUE_DATE >= SYSDATE");
			}
			// 计划完成日期
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " DUE_DATE ", "DESC"));
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
}