package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;

/**
 * 调用PKG_PM包中的存储过程
 */
@Service
@Transactional
public class PkgPmDBProcedureServcie extends AbstractDBProcedureService {
	
	// 新建车型ID
	public static final String KEY_NPV_TARGET_VEHICLE_ID = "TARGET_VEHICLE_ID";
	// 新建项目ID
	public static final String KEY_NP_PROGRAM_ID = "PROGRAM_ID";
	
	/**
	 * 新建项目
	 */
	public Map<String, Object> newProgram(final String programCode,
			final String programName, final String programType,
			final String pmId, final String createBy) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.NEW_PROGRAM(?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programCode);
				statement.setString(2, programName);
				statement.setString(3, programType);
				statement.setString(4, pmId);
				statement.setString(5, createBy);
				statement.registerOutParameter(6,OracleTypes.VARCHAR);
				statement.registerOutParameter(7,OracleTypes.VARCHAR);
				statement.registerOutParameter(8,OracleTypes.VARCHAR);
				statement.executeUpdate();
				
				// 新建项目ID
				returnMap.put(KEY_NP_PROGRAM_ID, statement.getString(6));
				
				//设置错误代码和错误信息
				returnCode = statement.getString(7);
				returnMsg = statement.getString(8);
			}});
	}
	
	/**
	 * 删除项目
	 */
	public Map<String, Object> deleteProgram(
			final String programId, final String deleteBy) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.DELETE_PROGRAM(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programId);
				statement.setString(2, deleteBy);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 新建车型
	 */
	public Map<String, Object> newProgramVehicle(final String targetProgramId,
			final String vehicleCode, final String vehicleName, final String programMgrId,
			final String qm, final String createBy, final String cloneBaseObs,
			final String cloneBasePlan, final String cloneVehicleId,
			final String templateId, final Date sopDate) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.NEW_PROGRAM_VEHICLE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, targetProgramId);
				statement.setString(2, vehicleCode);
				statement.setString(3, vehicleName);
				statement.setString(4, programMgrId);
				statement.setString(5, qm);
				statement.setString(6, createBy);
				statement.registerOutParameter(7,OracleTypes.VARCHAR);
				statement.registerOutParameter(8,OracleTypes.VARCHAR);
				statement.registerOutParameter(9,OracleTypes.VARCHAR);
				statement.setString(10, cloneBaseObs);
				statement.setString(11, cloneBasePlan);
				statement.setString(12, cloneVehicleId);
				statement.setString(13, templateId);
				statement.setDate(14, PDMSCommon.toSqlDate(sopDate));
				statement.executeUpdate();
				
				// 新建车型ID
				returnMap.put(KEY_NPV_TARGET_VEHICLE_ID, statement.getString(7));
				
				//设置错误代码和错误信息
				returnCode = statement.getString(8);
				returnMsg = statement.getString(9);
			}});
	}
	
	/**
	 * 删除车型
	 */
	public Map<String, Object> deleteProgramVehicle(
			final String programVehicleId, final String deleteBy) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.DELETE_PROGRAM_VEHICLE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, deleteBy);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}

//	/**
//	 * 更改项目状态
//	 */
//	public Map<String, Object> updatePgPlanStatus(
//			final String programVehicleId, final String statusCode) {
//		
//		return executeProcess(new DBProcedureWork() {
//			@Override
//			public void execute(Connection connection) throws SQLException {
//				String procedure = "{call PKG_PM.UPDATE_PG_PLAN_STATUS(?, ?, ?, ?)}";
//				CallableStatement statement = connection.prepareCall(procedure);
//				statement.setString(1, programVehicleId);
//				statement.setString(2, statusCode);
//				statement.registerOutParameter(3, Types.VARCHAR);
//				statement.registerOutParameter(4, Types.VARCHAR);
//				statement.executeUpdate();
//				
//				//设置错误代码和错误信息
//				returnCode = statement.getString(3);
//				returnMsg = statement.getString(4);
//			}});
//	}
//	
//	/**
//	 * 更改计划状态
//	 */
//	public Map<String, Object> updateFnPlanStatus(
//			final String fnObsId, final String statusCode) {
//		
//		return executeProcess(new DBProcedureWork() {
//			@Override
//			public void execute(Connection connection) throws SQLException {
//				String procedure = "{call PKG_PM.UPDATE_FN_PLAN_STATUS(?, ?, ?, ?)}";
//				CallableStatement statement = connection.prepareCall(procedure);
//				statement.setString(1, fnObsId);
//				statement.setString(2, statusCode);
//				statement.registerOutParameter(3, Types.VARCHAR);
//				statement.registerOutParameter(4, Types.VARCHAR);
//				statement.executeUpdate();
//				
//				//设置错误代码和错误信息
//				returnCode = statement.getString(3);
//				returnMsg = statement.getString(4);
//			}});
//	}
	
	/**
	 * 更改计划状态
	 */
	public Map<String, Object> updatePlanStatus(
			final String fnObsId, final String statusCode) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.UPDATE_PLAN_STATUS(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, fnObsId);
				statement.setString(2, statusCode);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
//	/**
//	 * 创建基线（车型计划）
//	 */
//	public Map<String, Object> createBaseline(
//			final String programVehicleId, final String title,
//			final String note, final String baselineType) {
//		
//		return executeProcess(new DBProcedureWork() {
//			@Override
//			public void execute(Connection connection) throws SQLException {
//				String procedure = "{call PKG_PM.CREATE_BASELINE(?, ?, ?, ?, ?, ?)}";
//				CallableStatement statement = connection.prepareCall(procedure);
//				statement.setString(1, programVehicleId);
//				statement.setString(2, title);
//				statement.setString(3, note);
//				statement.registerOutParameter(4, Types.VARCHAR);
//				statement.registerOutParameter(5, Types.VARCHAR);
//				statement.setString(6, baselineType);
//				statement.executeUpdate();
//				
//				//设置错误代码和错误信息
//				returnCode = statement.getString(4);
//				returnMsg = statement.getString(5);
//			}});
//	}
	
	/**
	 * 创建基线（主计划）
	 */
	public Map<String, Object> createBaselinePvMainPlan(
			final String programVehicleId, final String title, final String note) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.CREATE_BASELINE_PV_MAIN_PLAN(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, title);
				statement.setString(3, note);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 创建基线（二级计划）
	 */
	public Map<String, Object> createBaselinePvFnPlan(
			final String programVehicleId, final String title,
			final String note, final String obsId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.CREATE_BASELINE_PV_FN_PLAN(?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, title);
				statement.setString(3, note);
				statement.setString(4, obsId);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(5);
				returnMsg = statement.getString(6);
			}});
	}
	
	/**
	 * 创建基线（基本信息）
	 */
	public Map<String, Object> createPmBiBaseline(
			final String programId, final String title, final String note) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.CREATE_PM_BI_BASELINE(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programId);
				statement.setString(2, title);
				statement.setString(3, note);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
//	/**
//	 * 设定默认基线(车型计划)
//	 */
//	public Map<String, Object> setDefaultBaseline(
//			final String programVehicleId, final int baselineId) {
//		
//		return executeProcess(new DBProcedureWork() {
//			@Override
//			public void execute(Connection connection) throws SQLException {
//				String procedure = "{call PKG_PM.SET_DEFAULT_BASELINE(?, ?, ?, ?)}";
//				CallableStatement statement = connection.prepareCall(procedure);
//				statement.setString(1, programVehicleId);
//				statement.setInt(2, baselineId);
//				statement.registerOutParameter(3, Types.VARCHAR);
//				statement.registerOutParameter(4, Types.VARCHAR);
//				statement.executeUpdate();
//				
//				//设置错误代码和错误信息
//				returnCode = statement.getString(3);
//				returnMsg = statement.getString(4);
//			}});
//	}
	
	/**
	 * 设定默认基线(车型主计划)
	 */
	public Map<String, Object> setBaselinePvMainPlan(
			final String programVehicleId, final int baselineId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.SET_BASELINE_PV_MAIN_PLAN(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setInt(2, baselineId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 设定默认基线(车型二级计划)
	 */
	public Map<String, Object> setBaselinePvFnPlan(
			final String programVehicleId, final String obsId, final int baselineId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.SET_BASELINE_PV_FN_PLAN(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, obsId);
				statement.setInt(3, baselineId);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 设定默认基线(基本信息)
	 */
	public Map<String, Object> setDefaultPmBiBaseline(
			final String programId, final int baselineId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.SET_DEFAULT_PM_BI_BASELINE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programId);
				statement.setInt(2, baselineId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 删除项目组织
	 */
	public Map<String, Object> removePvObs(final String obsId, final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.REMOVE_PV_OBS(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, obsId);
				statement.setString(2, operatorId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 删除泳道
	 */
	public Map<String, Object> removePvFunction(final String functionId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.REMOVE_PV_FUNCTION(?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, functionId);
				statement.registerOutParameter(2, Types.VARCHAR);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(2);
				returnMsg = statement.getString(3);
			}});
	}
	
	/**
	 * 设定车型计划的开始、结束时间
	 */
	public Map<String, Object> updateVehiclePlanDate(
			final String programVehicleId, final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.UPDATE_VEHICLE_PLAN_DATE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, operatorId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 更新节点时间验证
	 */
	public Map<String, Object> pmTaskValidator(
			final String taskId, final Date plannedStartDate, final Date plannedFinishDate) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.PM_TASK_VALIDATOR(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, taskId);
				statement.setDate(2, PDMSCommon.toSqlDate(plannedStartDate));
				statement.setDate(3, PDMSCommon.toSqlDate(plannedFinishDate));
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 更新SOP时间
	 */
	public Map<String, Object> updateSOPSchedule(final String programVehicleId,
			final String newSOPName, final Date newSOPDate, final String autoUpdate,
			final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.UPDATE_SOP_SCHEDULE(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, newSOPName);
				statement.setDate(3, PDMSCommon.toSqlDate(newSOPDate));
				statement.setString(4, autoUpdate);
				statement.setString(5, operatorId);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}});
	}
	
	/**
	 * 删除节点
	 */
	public Map<String, Object> deleteTask(final String programVehicleId,
			final String taskId, final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.DELETE_TASK(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, taskId);
				statement.setString(3, operatorId);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 删除交付物
	 */
	public Map<String, Object> deleteDeliverable(final String programVehicleId,
			final String deliverableId, final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.DELETE_DELIVERABLE(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, deliverableId);
				statement.setString(3, operatorId);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 设置交付物为不适用
	 */
	public Map<String, Object> setFitDeliverable(final String programVehicleId,
			final String deliverableId, final String operatorId, final String isFit,
			final String notFitReason) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM.SET_FIT_DELIVERABLE(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, deliverableId);
				statement.setString(3, operatorId);
				statement.setString(4, isFit);
				statement.setString(5, notFitReason);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}});
	}
}
