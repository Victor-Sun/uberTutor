package com.gnomon.pdms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.CommonUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.dao.ImsLastAccessLogDAO;
import com.gnomon.pdms.entity.ImsLastAccessLogEntity;

@Service
@Transactional
public class LastAccessLogService {

	@Autowired
	private ImsLastAccessLogDAO imsLastAccessLogDAO;

	//最近访问问题次数查询
	public void updateAccessLog(String keyId,String objectType) {

		String userId = "";
		// 登录用户ID取得
		userId = SessionData.getLoginUserId();

		String hql = "FROM ImsLastAccessLogEntity WHERE objectId = ? AND userid = ?";
		List<ImsLastAccessLogEntity> getLastLog =
				this.imsLastAccessLogDAO.find(hql, keyId, userId);

		if (getLastLog.size() == 0) {
			ImsLastAccessLogEntity entityE = null ;
			entityE = new ImsLastAccessLogEntity();

			String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
			entityE.setId(uuid);
			entityE.setObjectTypeCode(objectType);
			entityE.setUserid(userId);
			entityE.setObjectId(keyId);
			entityE.setAccessTimes((long) 1);
			entityE.setLastAccessDate(new Date());
			entityE.setCreateBy(userId);
			entityE.setCreateDate(new Date());
			entityE.setUpdateBy(userId);
			entityE.setUpdateDate(new Date());

			this.setLastLog(entityE);
		} else {
			// 更新
			for (ImsLastAccessLogEntity entityE : getLastLog) {
				entityE.setAccessTimes(entityE.getAccessTimes()+1);
				entityE.setLastAccessDate(new Date());
				entityE.setUpdateBy(userId);
				entityE.setUpdateDate(new Date());

				this.setLastLog(entityE);
			}
		}
    }

	//最近访问问题次数更新
	public void setLastLog(ImsLastAccessLogEntity entity) {

		imsLastAccessLogDAO.save(entity);
    }
}
