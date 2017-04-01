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
 * 调用PKG_PM_POST包中的存储过程
 */
@Service
@Transactional
public class PkgPmPostDBProcedureServcie extends AbstractDBProcedureService {
	
	//讨论区来源类型
	public static final String POST_SOURCE_TYPE_PM_ISSUE = "PM-ISSUE";
	
	public static final String POST_SOURCE_TYPE_PM_TASK = "PM-TASK";
	
	public static final String POST_SOURCE_TYPE_IMS_ISSUE = "IMS-ISSUE";
	
	/**
	 * 新建Post
	 */
	public Map<String, Object> newPost(final String userId,
			final String sourceType, final String sourceId,
			final String title, final String content) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_POST.NEW_POST(?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, userId);
				statement.setString(2, sourceType);
				statement.setString(3, sourceId);
				statement.setString(4, title);
				statement.setString(5, content);
				statement.registerOutParameter(6, Types.INTEGER);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.registerOutParameter(8, Types.VARCHAR);
				statement.executeUpdate();
				returnMap.put("postId", statement.getLong(6));
				//设置错误代码和错误信息
				returnCode = statement.getString(7);
				returnMsg = statement.getString(8);
			}});
	}

	/**
	 * 回复Post
	 */
	public Map<String, Object> replyPost(final String userId,final Long parentPostId,final String content) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_PM_POST.REPLY_POST(?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, userId);
				statement.setLong(2, parentPostId);
				statement.setString(3, content);
				statement.registerOutParameter(4, Types.BIGINT);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.executeUpdate();
				returnMap.put("postId", statement.getLong(4));
				//设置错误代码和错误信息
				returnCode = statement.getString(5);
				returnMsg = statement.getString(6);
			}});
	}

}
