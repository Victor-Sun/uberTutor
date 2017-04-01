package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用PKG_PERMISSION包中的存储过程
 */
@Service
@Transactional
public class PkgPermissionDBProcedureServcie extends AbstractDBProcedureService {
	
	public Map<String, Object> isQimsManager(final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{?=call PKG_PERMISSION.IS_QIMS_MANAGER(?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(2, userId);
				statement.registerOutParameter(1,Types.VARCHAR);
				statement.executeUpdate();
				String code = statement.getString(1); 
				returnMap.put("isQimsManager", code);
			}
		});
	}

}
