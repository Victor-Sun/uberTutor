package com.gnomon.pdms.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCommon;

@Service
@Transactional
public class SysDataSyncLogServcie {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String,Object> getSyncLog(String syncLogId){
		Map<String,Object> resultMap = null;
		String sql="SELECT * FROM SYS_DATA_SYNC_LOG where ID=?";
		resultMap = jdbcTemplate.queryForMap(sql,syncLogId);
		return resultMap;
	}
	
	public void finishSyncWithException(String syncLogId,Exception e){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream s = new PrintStream(out);
		e.printStackTrace(s);
		if(syncLogId != null){
			finisheSync(syncLogId, false,out.toString());
		}
	}
	
	public String beginSync(String systemName,String direction,String operationName){
		String sql="INSERT INTO SYS_DATA_SYNC_LOG(ID,SYSTEM_NAME,DIRECTION,OPERATION_NAME) VALUES(?,?,?,?)";
		String id = PDMSCommon.generateUUID();
		jdbcTemplate.update(sql,id,systemName,direction,operationName);
		return id;
	}
	
	public void finisheSync(String syncLogId,boolean success,String errorMsg){
		String isSuccess = success?"Y":"N";
		String sql="update SYS_DATA_SYNC_LOG SET FINISH_DATE=SYSDATE,IS_SUCCESS=?,ERROR_MSG=? WHERE ID=?";
		jdbcTemplate.update(sql,isSuccess,errorMsg,syncLogId);
	}
	
	public void finishSyncWithSuccess(String syncLogId,String message){
		finisheSync(syncLogId, true, message);
	}
	
	public void throwExceptionIfSyncFailed(String syncLogId){
		Map<String, Object> syncLog = getSyncLog(syncLogId);
		String isSuccess = ObjectConverter.convert2String(syncLog.get("IS_SUCCESS"));
		if("N".equals(isSuccess)){
			String errorMsg = ObjectConverter.convert2String(syncLog.get("ERROR_MSG"));
			throw new RuntimeException(errorMsg);
		}
	}
}
