package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用PKG_SYS_MY_WORGROUP包中的存储过程
 */
@Service
@Transactional
public class PkgSysMyWorkgroupDBProcedureServcie extends AbstractDBProcedureService {
	
	/**
	 * 创建工作组
	 */
	public Map<String, Object> createWorkgroup(
			final String workGroupName, final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_SYS_MY_WORGROUP.CREATE_WORKGROUP(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, workGroupName);
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
	 * 删除工作组
	 */
	public Map<String, Object> deleteWorkgroup(final Long workGroupId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_SYS_MY_WORGROUP.DELETE_WORKGROUP(?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workGroupId);
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
	 * 重命名工作组
	 */
	public Map<String, Object> renameWorkgroup(
			final Long workGroupId, final String workGroupName) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_SYS_MY_WORGROUP.RENAME_WORKGROUP(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workGroupId);
				statement.setString(2, workGroupName);
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
	 * 增加工作组成员
	 */
	public Map<String, Object> addWorkgroupMember(
			final Long workGroupId, final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_SYS_MY_WORGROUP.ADD_WORKGROUP_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workGroupId);
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
	 * 删除工作组成员
	 */
	public Map<String, Object> deleteWorkgroupMember(
			final Long workGroupMemberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_SYS_MY_WORGROUP.DELETE_WORKGROUP_MEMBER(?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, workGroupMemberId);
				statement.registerOutParameter(2, Types.VARCHAR);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(3);
			}
		});
	}

}
