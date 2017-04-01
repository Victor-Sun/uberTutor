package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;

/**
 * 调用PKG_PM_DEPT_ISSUE包中的存储过程
 */
@Service
@Transactional
public class PkgPmDeptIssueDBProcedureServcie extends AbstractDBProcedureService {
	
	// 新建ISSUE ID
	public static final String KEY_ISSUE_ID = "ISSUE_ID";

	/**
	 * 提交/保存问题
	 */
	public Map<String, Object> updateIssue(final Long issueId, final String userId,
			final String deptId, final String subDeptId, final Long issueSourceId,
			final String issueSourceDescription, final String title,
			final String issueDescription, final String issueCause,
			final Date dueDate, final String raiseBy, final Date raiseDate,
			final String respDeptId, final String respUserId, final String issuePriorityCode,
			final String action, final String ownerComment, final String returnComment) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.UPDATE_ISSUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				if (issueId != null) {
					statement.setLong(1, issueId);
				} else {
					statement.registerOutParameter(1, Types.INTEGER);
				}
				statement.setString(2, userId);
				statement.setString(3, deptId);
				statement.setString(4, subDeptId);
				statement.setLong(5, issueSourceId);
				statement.setString(6, issueSourceDescription);
				statement.setString(7, title);
				statement.setString(8, issueDescription);
				statement.setString(9, issueCause);
				statement.setDate(10, PDMSCommon.toSqlDate(dueDate));
				statement.setString(11, raiseBy);
				statement.setDate(12, PDMSCommon.toSqlDate(raiseDate));
				statement.setString(13, respDeptId);
				statement.setString(14, respUserId);
				statement.setString(15, issuePriorityCode);
				statement.setString(16, action);
				statement.setString(17, ownerComment);
				statement.setString(18, returnComment);
				statement.registerOutParameter(19, Types.VARCHAR);
				statement.registerOutParameter(20, Types.VARCHAR);
				statement.executeUpdate();
				
				// 新建Issue
				if (issueId == null) {
					returnMap.put(KEY_ISSUE_ID, statement.getLong(1));
				} else {
					returnMap.put(KEY_ISSUE_ID, issueId);
				}

				//设置错误代码和错误信息
				returnCode = statement.getString(19);
				returnMsg = statement.getString(20);
			}});
	}
	
	/**
	 * 保存草稿
	 */
	public Map<String, Object> updateDraftIssue(final Long issueId, final String userId,
			final String deptId, final String subDeptId, final Long issueSourceId,
			final String issueSourceDescription, final String title,
			final String issueDescription, final String issueCause,
			final Date dueDate, final String raiseBy, final Date raiseDate,
			final String respDeptId, final String respUserId, final String issuePriorityCode) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.UPDATE_DRAFT_ISSUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				if (issueId != null) {
					statement.setLong(1, issueId);
				} else {
					statement.registerOutParameter(1, Types.INTEGER);
				}
				statement.setString(2, userId);
				statement.setString(3, deptId);
				statement.setString(4, subDeptId);
				statement.setLong(5, issueSourceId);
				statement.setString(6, issueSourceDescription);
				statement.setString(7, title);
				statement.setString(8, issueDescription);
				statement.setString(9, issueCause);
				statement.setDate(10, PDMSCommon.toSqlDate(dueDate));
				statement.setString(11, raiseBy);
				statement.setDate(12, PDMSCommon.toSqlDate(raiseDate));
				statement.setString(13, respDeptId);
				statement.setString(14, respUserId);
				statement.setString(15, issuePriorityCode);
				statement.registerOutParameter(16, Types.VARCHAR);
				statement.registerOutParameter(17, Types.VARCHAR);
				statement.executeUpdate();
				
				// 新建草稿Issue
				if (issueId == null) {
					returnMap.put(KEY_ISSUE_ID, statement.getLong(1));
				} else {
					returnMap.put(KEY_ISSUE_ID, issueId);
				}

				//设置错误代码和错误信息
				returnCode = statement.getString(16);
				returnMsg = statement.getString(17);
			}});
	}
	
	/**
	 * 删除草稿
	 */
	public Map<String, Object> deleteDraftIssue(final Long issueId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.DELETE_DRAFT_ISSUE(?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.registerOutParameter(2, Types.VARCHAR);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(2);
				returnMsg = statement.getString(3);
			}});
	}
	
	/**
	 * 更新进度
	 */
	public Map<String, Object> reportIssueProgress(final Long issueId, final String operatorId,
			final String description) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.REPORT_ISSUE_PROGRESS(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, operatorId);
				statement.setString(3, description);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}

	/**
	 * 删除问题
	 */
	public Map<String, Object> deleteIssue(final Long issueId, final String operatorId,
			final String description) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.UPDATE_ISSUE(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, operatorId);
				statement.setString(3, description);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 添加成员
	 */
	public Map<String, Object> addMember(final Long issueId, final String memberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.ADD_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, memberId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 成员失效
	 */
	public Map<String, Object> deactiveMember(final Long issueId, final String memberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.DEACTIVATE_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, memberId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 成员有效
	 */
	public Map<String, Object> activeMember(final Long issueId, final String memberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_DEPT_ISSUE.ACTIVATE_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, memberId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}

}
