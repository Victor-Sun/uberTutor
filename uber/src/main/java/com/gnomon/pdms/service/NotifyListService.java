package com.gnomon.pdms.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.dao.SysNoticeVMDAO;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMTaskTodoListVMDAO;
import com.gnomon.pdms.entity.PMTaskTodoListVMEntity;

@Service
@Transactional
public class NotifyListService {

	@Autowired
	private SysNoticeVMDAO sysNoticeVMDAO;
	
	@Autowired
	private PMTaskTodoListVMDAO pmTaskTodoListVMDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;

	/**
	 * 通知提醒列表取得
	 */
	public GTPage<Map<String, Object>> getNotifyListService(boolean searchUnopen,
			boolean searchOpened, int pageNo, int pageSize) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 登录用户ID取得
		String userId = SessionData.getLoginUserId();

		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_NOTICE");
		sql.append(" WHERE");
		sql.append(" USERID = ?");
		params.add(userId);
		if (! searchUnopen && searchOpened) {
			sql.append(" AND IS_NEW = ?");
			params.add(PDMSConstants.STATUS_N);
		} else if (searchUnopen && ! searchOpened) {
			sql.append(" AND IS_NEW = ?");
			params.add(PDMSConstants.STATUS_Y);
		} else if (! searchUnopen && ! searchOpened) {
			sql.append(" AND 1 = 2");
		}
		sql.append(" ORDER BY");
		sql.append(" CREATE_DATE DESC");
		return jdbcTemplate.queryPagination(sql.toString(),
				pageNo, pageSize, params.toArray());
	}

	/**
	 * 通知提醒列表件数取得(无分页处理)
	 */
	public int getNotifyListCount() {
		StringBuffer sql = new StringBuffer();
		// 登录用户ID取得
		String userId = SessionData.getLoginUserId();
		
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_SYS_NOTICE");
		sql.append(" WHERE");
		sql.append(" USERID = ?");
		sql.append(" AND IS_NEW = ?");
		sql.append(" ORDER BY");
		sql.append(" CREATE_DATE DESC");
		return jdbcTemplate.queryForInt(sql.toString(), userId, PDMSConstants.STATUS_Y);
	}
	
	/**
	 * 通知开封
	 */
	public void openNotification(String notifyId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新截止日期
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SYS_NOTICE SET");
		sql.append(" IS_NEW = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(PDMSConstants.STATUS_N);
		params.add(loginUser);
		params.add(notifyId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 任务表单信息取得
	 */
	public PMTaskTodoListVMEntity getTodoTaskInfo(String taskId, String userId)
			throws Exception {
		List<String> params = new ArrayList<String>();
		String hql = "from PMTaskTodoListVMEntity where processCode = ? and taskId = ?";
		params.add(PDMSConstants.PROCESS_CODE_DELIVERABLE);
		params.add(taskId);
		if (PDMSCommon.isNotNull(userId)) {
			hql += " and processTaskOwner = ?";
			params.add(userId);
		}
		hql += " order by taskProgressCreateDate desc";
		List<PMTaskTodoListVMEntity> list =
				this.pmTaskTodoListVMDAO.find(hql, params.toArray());
		if (list.size() == 0) {
			throw new Exception("该任务已经不存在！");
		}
		return list.get(0);
	}
	
	/**
	 * 任务表单信息取得（状态变更流程）
	 */
	public PMTaskTodoListVMEntity getTodoTaskInfo4Process(Long processId)
			throws Exception {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		String hql = "from PMTaskTodoListVMEntity where processCode = ?"
				+ " and processId = ?"
				+ " and processTaskOwner = ?"
				+ " order by taskProgressCreateDate desc";
		
		List<PMTaskTodoListVMEntity> list = this.pmTaskTodoListVMDAO.find(hql,
				PDMSConstants.PROCESS_CODE_PROGRESS_STATUS, processId, loginUser);
		if (list.size() == 0) {
			throw new Exception("该任务已经不存在！");
		}
		return list.get(0);
	}
	
	/**
	 * 标记为已读
	 */
	public void setOpened(final List<Map<String, String>> modelList) {
		// 登录用户取得
		final String loginUser = SessionData.getLoginUserId();
		
		// 更新项目信息
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SYS_NOTICE SET");
		sql.append(" IS_NEW = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return modelList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, String> model = modelList.get(i);
            	ps.setString(1, PDMSConstants.STATUS_N);
            	ps.setString(2, loginUser);
            	ps.setString(3, model.get("id"));
            }
        });
	}
	
	/**
	 * 批量删除
	 */
	public void deleteMails(final List<Map<String, String>> modelList) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM SYS_NOTICE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return modelList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, String> model = modelList.get(i);
            	ps.setString(1, model.get("id"));
            }
        });
	}
	
	/**
	 * 项目OpenIssue通知-信息取得
	 */
	public Map<String, Object> getDeptIssueInfo(String issueId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		Map<String, Object> result = new HashMap<String, Object>();
		
		sql.append(" SELECT T1.*, T2.STEP_ID AS STEP_ID");
		sql.append(" FROM");
		sql.append(" V_PM_DEPT_ISSUE_INFO T1 LEFT JOIN V_PM_DEPT_ISSUE_TASK T2");
		sql.append(" ON T1.ISSUE_ID = T2.ISSUE_ID");
		sql.append(" AND T2.COMPLETE_FLAG = ?");
		sql.append(" AND T2.TASK_OWNER_ID = ?");
		sql.append(" WHERE");
		sql.append(" T1.ISSUE_ID = ?");
		params.add(PDMSConstants.STATUS_N);
		params.add(loginUser);
		params.add(issueId);
		List<Map<String, Object>> issueList = jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (issueList.size() > 0) {
			result = issueList.get(0);
		}
		return result;
	}

	/**
	 * 项目OpenIssue通知-信息取得
	 */
	public Map<String, Object> getPmIssueInfo(String issueId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		Map<String, Object> result = new HashMap<String, Object>();
		
		sql.append(" SELECT T1.*, T2.STEP_ID AS STEP_ID");
		sql.append(" FROM");
		sql.append(" V_PM_ISSUE T1 LEFT JOIN V_PM_OPEN_ISSUE_TASK T2");
		sql.append(" ON T1.ISSUE_ID = T2.ISSUE_ID");
		sql.append(" AND T2.COMPLETE_FLAG = ?");
		sql.append(" AND T2.TASK_OWNER_ID = ?");
		sql.append(" WHERE");
		sql.append(" T1.ISSUE_ID = ?");
		params.add(PDMSConstants.STATUS_N);
		params.add(loginUser);
		params.add(issueId);
		List<Map<String, Object>> issueList = jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (issueList.size() > 0) {
			result = issueList.get(0);
		}
		return result;
	}
}