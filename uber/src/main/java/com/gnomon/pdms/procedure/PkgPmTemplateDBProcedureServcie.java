package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用PKG_PM_TEMPLATE包中的存储过程
 */
@Service
@Transactional
public class PkgPmTemplateDBProcedureServcie extends AbstractDBProcedureService {
	
	// 导出的模板ID
	public static final String KEY_SAT_TEMPLATE_ID = "TEMPLATE_ID";
	
	/**
	 * 导入项目模板
	 */
	public Map<String, Object> importTemplate(final String programVehicleId,
			final Date sopDate, final String templateId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_TEMPLATE.IMPORT_TEMPLATE(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setDate(2, new java.sql.Date(sopDate.getTime()));
				statement.setString(3, templateId);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();

				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 导出为模板
	 */
	public Map<String, Object> saveAsTemplate(final String programVehicleId,
			final String templateCode, final String templateName) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_TEMPLATE.SAVE_AS_TEMPLATE(?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programVehicleId);
				statement.setString(2, templateCode);
				statement.setString(3, templateName);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.executeUpdate();
				
				// 导出的模板ID
				returnMap.put(KEY_SAT_TEMPLATE_ID, statement.getString(4));

				//设置错误代码和错误信息
				returnCode = statement.getString(5);
				returnMsg = statement.getString(6);
			}});
	}
}
