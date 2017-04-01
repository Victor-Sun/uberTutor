package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用排序存储过程
 */
@Service
@Transactional
public class SpResequenceDBProcedureServcie extends AbstractDBProcedureService {
	
	/**
	 * PM_TASK排序
	 */
	public Map<String, Object> resequencePmTask(final String programId,
			final String programVehicleId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call SP_RESEQUENCE_PM_TASK(?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, programId);
				statement.setString(2, programVehicleId);
				statement.executeUpdate();
			}});
	}

}
