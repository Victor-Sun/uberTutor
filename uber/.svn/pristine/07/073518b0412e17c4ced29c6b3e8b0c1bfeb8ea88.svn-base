package com.gnomon.pdms.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.service.BimDataSyncService;
import com.gnomon.pdms.service.SysDataSyncLogServcie;

/**
 * Bim数据同步任务
 * @author Frank
 *
 */
public class BimSyncJob {
	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private BimDataSyncService bimDataSyncService;
	
	@Autowired
	private SysDataSyncLogServcie sysDataSyncLogServcie;
	
	public void syncOrgAndUserFromBim(){
		String syncLogId = null;
		try{
			log.info("BIM数据同步服务启动");
			syncLogId = sysDataSyncLogServcie.beginSync(PDMSConstants.SYSTEM_NAME_BIM,PDMSConstants.DIRECTION_INPUT,"BimDataSyncService.syncOrgAndUserFromBim");
			bimDataSyncService.syncOrgAndUserFromBim();
			sysDataSyncLogServcie.throwExceptionIfSyncFailed(syncLogId);
			sysDataSyncLogServcie.finishSyncWithSuccess(syncLogId, "组织机构和用户同步成功");

		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
			if(syncLogId != null){
				sysDataSyncLogServcie.finishSyncWithException(syncLogId, e);
			}
		}finally{
			log.info("BIM数据同步服务停止");
		}
	}
}
