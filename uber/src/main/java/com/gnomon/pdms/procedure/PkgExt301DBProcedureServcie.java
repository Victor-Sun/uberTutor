package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
	/**
	 * 调用PKGEXT301包中的存储过程
	 */
	@Service
	@Transactional
	public class PkgExt301DBProcedureServcie extends AbstractDBProcedureService {
		
		/**
		 * 计划下发
		 */
		public Map<String, Object> initSchedule(final String programVehicleId) {
			
			return executeProcess(new DBProcedureWork() {
				@Override
				public void execute(Connection connection) throws SQLException {
					String procedure = "{call PKG_EXT_301.INIT_SCHEDULE(?,?,?)}";
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, programVehicleId);
					statement.registerOutParameter(2, Types.VARCHAR);
					statement.registerOutParameter(3, Types.VARCHAR);
					statement.executeUpdate();
					//设置错误代码和错误信息
					returnCode = statement.getString(2);
					returnMsg = statement.getString(3);
				}
			});
		}
		
		/**
		 * 提交计划
		 */
		public Map<String, Object> submitSchedule(final String programVehicleId,final String userId) {
			
			return executeProcess(new DBProcedureWork() {
				@Override
				public void execute(Connection connection) throws SQLException {
					String procedure = "{call PKG_EXT_301.PROCESS_SCHEDULE(?,?,?,?)}";
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, programVehicleId);
					statement.setString(2, userId);
					statement.registerOutParameter(3, Types.VARCHAR);
					statement.registerOutParameter(4, Types.VARCHAR);
					statement.executeUpdate();
					//设置错误代码和错误信息
					returnCode = statement.getString(3);
					returnMsg = statement.getString(4);
				}
			});
		}
		
		/**
		 * 检查计划
		 */
		public Map<String, Object> checkSchedule(final String programVehicleId,final String userId) {
			
			return executeProcess(new DBProcedureWork() {
				@Override
				public void execute(Connection connection) throws SQLException {
					String procedure = "{call PKG_EXT_301.CHECK_SCHEDULE(?,?,?,?)}";
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, programVehicleId);
					statement.setString(2, userId);
					statement.registerOutParameter(3, Types.VARCHAR);
					statement.registerOutParameter(4, Types.VARCHAR);
					statement.executeUpdate();
					//设置错误代码和错误信息
					returnCode = statement.getString(3);
					returnMsg = statement.getString(4);
				}
			});
		}

		/**
		 * 发布计划
		 */
		public Map<String, Object> releaseSchedule(final String programVehicleId,final String userId) {
			
			return executeProcess(new DBProcedureWork() {
				@Override
				public void execute(Connection connection) throws SQLException {
					String procedure = "{call PKG_EXT_301.RELEASE_SCHEDULE(?,?,?,?)}";
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, programVehicleId);
					statement.setString(2, userId);
					statement.registerOutParameter(3, Types.VARCHAR);
					statement.registerOutParameter(4, Types.VARCHAR);
					statement.executeUpdate();
					//设置错误代码和错误信息
					returnCode = statement.getString(3);
					returnMsg = statement.getString(4);
				}
			});
		}

	public Map<String, Object> processSchedule(final String programVehicleId,final String userId, final List<Integer> itemList,final String action,final String ownerComment,final String returnComment) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_EXT_301.PROCESS_SCHEDULE(?,?,?,?,?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, userId);
				statement.setArray(3, getArray(connection,itemList,"EXT301_NUMBER_ARRAY"));
				statement.setString(4, action);
				statement.setString(5, ownerComment);
				statement.setString(6, returnComment);
				statement.registerOutParameter(7,OracleTypes.VARCHAR);
				statement.registerOutParameter(8,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(7);
				returnMsg = statement.getString(8);
				
				
			}
		});
	}
	
	public Map<String, Object> getCurrentCheckpoint(final String programVehicleId,final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{?=call PKG_EXT_301.GET_CURRENT_CHECKPOINT_CODE(?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(2, programVehicleId);
				statement.setString(3, userId);
				statement.registerOutParameter(1,Types.VARCHAR);
				statement.executeUpdate();
				String code = statement.getString(1); 
				returnMap.put("code", code);
			}
		});
	}
	
	public Map<String, Object> updateTaskComplete(final List<Integer> taskList,final String remark) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_EXT_301.UPDATE_TASK_COMPLETE(?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setArray(1, getArray(connection,taskList,"EXT301_NUMBER_ARRAY"));
				statement.setString(2, remark);
				statement.registerOutParameter(3,OracleTypes.VARCHAR);
				statement.registerOutParameter(4,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}
	
	public Map<String, Object> batchProcessSchedule(final String programVehicleId,final String nodeCode,final Date plannedFinishDate,final String userId,final List<Integer> itemList) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_EXT_301.BATCH_PROCESS_SCHEDULE(?,?,?,?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, nodeCode);
				statement.setDate(3, new java.sql.Date(plannedFinishDate.getTime()));
				statement.setString(4, userId);
				statement.setArray(5, getArray(connection,itemList,"EXT301_NUMBER_ARRAY"));
				statement.registerOutParameter(6,OracleTypes.VARCHAR);
				statement.registerOutParameter(7,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}
		});
	}
	
	public Map<String, Object> batchCompleteSchedule(final Date plannedFinishDate,final List<Integer> itemList) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_EXT_301.BATCH_COMPLETE_SCHEDULE(?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setDate(1, new java.sql.Date(plannedFinishDate.getTime()));
				statement.setArray(2, getArray(connection,itemList,"EXT301_NUMBER_ARRAY"));
				statement.registerOutParameter(3,OracleTypes.VARCHAR);
				statement.registerOutParameter(4,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}

	public Map<String, Object> updateCheckpointOwner(final String owner,final Long checkpointId) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_EXT_301.UPDATE_CHECKPOINT_OWNER(?,?,?,?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, owner);
				statement.setLong(2, checkpointId);
				statement.registerOutParameter(3,OracleTypes.VARCHAR);
				statement.registerOutParameter(4,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}

}