package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用PKG_PM_PROCESS包中的存储过程
 */
@Service
@Transactional
public class PkgPmProcessDBProcedureServcie extends AbstractDBProcedureService {
	
	// 流程Action
	public static final String ACTION_SAVE="SAVE";
	public static final String ACTION_NEXT="NEXT";
	public static final String ACTION_RETURN="RETURN";
	
	// 审批流程实例ID
	public static final String KEY_CNP_PROCESS_INSTANCE_ID = "PROCESS_INSTANCE_ID";
	// 下一流程审批人
	public static final String KEY_UPS_NEXT_STEP_USER_ID = "NEXT_STEP_USER_ID";
	
	/**
	 * 创建审批流程
	 */
	public Map<String, Object> createNewProcess(final String processCode,
			final String sourceId, final String userId, final String taskProgressStatus) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_PROCESS.CREATE_NEW_PROCESS(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, processCode);
				statement.setString(2, sourceId);
				statement.setString(3, userId);
				statement.setString(4, taskProgressStatus);
				statement.registerOutParameter(5, Types.INTEGER);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.executeUpdate();
				
				// 流程实例ID
				returnMap.put(KEY_CNP_PROCESS_INSTANCE_ID, statement.getInt(5));
				
				// 设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}});
	}
	
	/**
	 * 更新流程状态
	 */
	public Map<String, Object> updateProcessStatus(final String userId,
			final long processId, final String action, final String ownerComment,
			final String returnComment, final String taskProgressStatus) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_PROCESS.UPDATE_PROCESS_STATUS(?, ?, ? ,?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, userId);
				statement.setLong(2, processId);
				statement.setString(3, action);
				statement.setString(4, ownerComment);
				statement.setString(5, returnComment);
				statement.setString(6, taskProgressStatus);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.registerOutParameter(8, Types.VARCHAR);
				statement.registerOutParameter(9, Types.VARCHAR);
				statement.executeUpdate();
				
				//返回 流程审批人
				returnMap.put(KEY_UPS_NEXT_STEP_USER_ID, statement.getString(7));
				
				//设置错误代码和错误信息
				returnCode = statement.getString(8);
				returnMsg = statement.getString(9);
			}});
	}
	
	/**
	 * 批量移交任务
	 */
	public Map<String, Object> transferTask(final List<String> taskList,final String remark,final String userTo,final String operatorId){
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_PROCESS.TRANSFER_PROCESS_TASK(?,?,?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setArray(1, getStringArray(connection,taskList,"PROCESS_TASK_ARRAY"));
				statement.setString(2, remark);
				statement.setString(3, userTo);
				statement.setString(4, operatorId);
				statement.registerOutParameter(5,OracleTypes.VARCHAR);
				statement.registerOutParameter(6,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(5);
				returnMsg = statement.getString(6);
			}
		});
	}
}
