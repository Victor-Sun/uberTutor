package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;

/**
 * 调用PKG_PM_OBS包中的存储过程
 */
@Service
@Transactional
public class PkgPmObsDBProcedureServcie extends AbstractDBProcedureService {
	
	// 新建OBS ID
	public static final String KEY_NO_OBS_ID = "OBS_ID";
	
	/**
	 * 新建OBS
	 */
	public Map<String, Object> newObs(final String programId,
			final String programVehicleId, final String parentId,
			final String obsCode, final String obsName, final String curnNode,
			final String insertFlag, final String obsId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_OBS.NEW_OBS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programId);
				statement.setString(2, programVehicleId);
				statement.setString(3, parentId);
				statement.setString(4, obsCode);
				statement.setString(5, obsName);
				statement.setString(6, curnNode);
				statement.setString(7, insertFlag);
				if (PDMSCommon.isNotNull(obsId)) {
					statement.setString(8, obsId);
				} else {
					statement.registerOutParameter(8, Types.VARCHAR);
				}
				statement.registerOutParameter(9, Types.VARCHAR);
				statement.registerOutParameter(10, Types.VARCHAR);
				statement.executeUpdate();
				
				// 新建OBS ID
				if (PDMSCommon.isNull(obsId)) {
					returnMap.put(KEY_NO_OBS_ID, statement.getString(8));
				}
				
				//设置错误代码和错误信息
				returnCode = statement.getString(9);
				returnMsg = statement.getString(10);
			}});
	}

	/**
	 * 设定专业经理
	 */
	public Map<String, Object> assignFmMgr(final String obsId, final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_OBS.ASSIGN_FM_MGR(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, obsId);
				statement.setString(2, userId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 删除OBS成员
	 */
	public Map<String, Object> deleteObsUser(final String id, final String targetUserId,
			final String operatorId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_OBS.DELETE_OBS_USER(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, id);
				statement.setString(2, targetUserId);
				statement.setString(3, operatorId);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();
				
				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}

}
