package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.entity.WorkOrderEntity;

/**
 * 调用PKG_PM_WORK_ORDER包中的存储过程
 */
@Service
@Transactional
public class PkgPmWorkOrderDBProcedureServcie extends AbstractDBProcedureService {
	
	/**
	 * 更新接收工作联系单
	 */
	public Map<String, Object> updateInboundWorkOrder(final WorkOrderEntity workOrderEntity) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_WORK_ORDER.UPDATE_INBOUND_WORK_ORDER(?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				if(workOrderEntity.getId() == null){
					statement.setLong(1, 0l);
				}else{
					statement.setLong(1, workOrderEntity.getId());
				}
				statement.setString(2, workOrderEntity.getCreateBy());
				statement.setString(3, workOrderEntity.getAction());
				statement.setString(4, workOrderEntity.getOwnerCommont());
				statement.setString(5, workOrderEntity.getReturnComment());
				statement.setString(6, workOrderEntity.getSourceDept());
				statement.setString(7, workOrderEntity.getTargetDept());
				statement.setString(8, workOrderEntity.getProgramName());
				if(workOrderEntity.getWorkType() == null){
					statement.setLong(9, 0l);
				}else{
					statement.setLong(9, workOrderEntity.getWorkType());
				}
				statement.setString(10, workOrderEntity.getIsProjectType());
				statement.setString(11, workOrderEntity.getTitle());
				statement.setString(12, workOrderEntity.getWorkDescription());
				if(workOrderEntity.getDueDate() == null){
					statement.setDate(13, null);
				}else{
					statement.setDate(13, new java.sql.Date(workOrderEntity.getDueDate().getTime()));
				}
				statement.setString(14, workOrderEntity.getContactName());
				statement.setString(15, workOrderEntity.getContactPhone());
				statement.setString(16, workOrderEntity.getReviewByString());
				statement.setString(17, workOrderEntity.getApproveByString());
				statement.setString(18, workOrderEntity.getWoAgent());
				statement.setString(19, workOrderEntity.getPm());
				statement.setString(20, workOrderEntity.getEng());
				statement.setString(21, workOrderEntity.getLineMgr());
				statement.setString(22, workOrderEntity.getDeptMgr());
				statement.setString(23, workOrderEntity.getDeptDirector());
				statement.setString(24, workOrderEntity.getPgDirector());
				statement.setString(25, workOrderEntity.getOnlineMode());
				statement.setString(26, workOrderEntity.getResponseDescription());
				statement.setString(27, workOrderEntity.getResponseContactPhone());
				statement.registerOutParameter(28, Types.VARCHAR);
				statement.registerOutParameter(29, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(28);
				returnMsg = statement.getString(29);
			}
		});
	}
	
	/**
	 * 更新发送工作联系单
	 */
	public Map<String, Object> updateOutboundWorkOrder(final WorkOrderEntity workOrderEntity) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_WORK_ORDER.UPDATE_OUTBOUND_WORK_ORDER(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				if(workOrderEntity.getId() == null){
					statement.setLong(1, 0l);
				}else{
					statement.setLong(1, workOrderEntity.getId());
				}
				statement.setString(2, workOrderEntity.getCreateBy());
				statement.setString(3, workOrderEntity.getAction());
				statement.setString(4, workOrderEntity.getOwnerCommont());
				statement.setString(5, workOrderEntity.getReturnComment());
				statement.setString(6, workOrderEntity.getSourceDept());
				statement.setString(7, workOrderEntity.getTargetDept());
				statement.setString(8, workOrderEntity.getProgramName());
				if(workOrderEntity.getWorkType() == null){
					statement.setLong(9, 0l);
				}else{
					statement.setLong(9, workOrderEntity.getWorkType());
				}
				statement.setString(10, workOrderEntity.getIsProjectType());
				statement.setString(11, workOrderEntity.getTitle());
				statement.setString(12, workOrderEntity.getWorkDescription());
				if(workOrderEntity.getDueDate() == null){
					statement.setDate(13, null);
				}else{
					statement.setDate(13, new java.sql.Date(workOrderEntity.getDueDate().getTime()));
				}
				statement.setString(14, workOrderEntity.getContactName());
				statement.setString(15, workOrderEntity.getContactPhone());
				statement.setString(16, workOrderEntity.getReviewByString());
				statement.setString(17, workOrderEntity.getApproveByString());
				statement.setString(18, workOrderEntity.getWoAgent());
				statement.setString(19, workOrderEntity.getPm());
				statement.setString(20, workOrderEntity.getEng());
				statement.setString(21, workOrderEntity.getLineMgr());
				statement.setString(22, workOrderEntity.getDeptMgr());
				statement.setString(23, workOrderEntity.getDeptDirector());
				statement.setString(24, workOrderEntity.getPgDirector());
				statement.setString(25, workOrderEntity.getOnlineMode());
				statement.registerOutParameter(1, Types.INTEGER);
				statement.registerOutParameter(26, Types.VARCHAR);
				statement.registerOutParameter(27, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(26);
				returnMsg = statement.getString(27);
				
				returnMap.put("id", statement.getInt(1));
			}
		});
	}

	/**
	 * 删除工作联系单
	 */
	public Map<String, Object> deleteWorkOrder(final Long workOrderId,
			final String operator,final String description) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_WORK_ORDER.DELETE_WORK_ORDER(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workOrderId);
				statement.setString(2, operator);
				statement.setString(3, description);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}
		});
	}
	
	/**
	 * 处理发送工作联系单流程
	 */
	public Map<String, Object> processOutboundStep(final WorkOrderEntity workOrderEntity) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_WORK_ORDER.PROCESS_OUTBOUND_STEP(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workOrderEntity.getId());
				statement.setString(2, workOrderEntity.getCreateBy());
				statement.setString(3, workOrderEntity.getAction());
				statement.setString(4, workOrderEntity.getOwnerCommont());
				statement.setString(5, workOrderEntity.getReturnComment());
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}
		});
	}
	
	/**
	 * 处理接收工作联系单流程
	 */
	public Map<String, Object> processInboundStep(final WorkOrderEntity workOrderEntity) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_WORK_ORDER.PROCESS_INBOUND_STEP(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workOrderEntity.getId());
				statement.setString(2, workOrderEntity.getCreateBy());
				statement.setString(3, workOrderEntity.getAction());
				statement.setString(4, workOrderEntity.getOwnerCommont());
				statement.setString(5, workOrderEntity.getReturnComment());
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(6);
				returnMsg = statement.getString(7);
			}
		});
	}

}
