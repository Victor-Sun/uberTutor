package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用PKG_PM_ISSUE包中的存储过程
 */
@Service
@Transactional
public class PkgPmIssueDBProcedureServcie extends AbstractDBProcedureService {
	
	/**
	 * 新增成员
	 */
	public Map<String, Object> addMember(final Long issueId,
			final String memberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_ISSUE.ADD_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setLong(1, issueId);
				statement.setString(2, memberId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}
	


}
