package com.gnomon.pdms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.ImsConstants;
import com.gnomon.pdms.dao.ImsIssueNotificationDAO;
import com.gnomon.pdms.dao.VImsIssueNotificationDAO;
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.entity.VImsIssueNotificationEntity;

@Service
@Transactional
public class QeNotifyService {
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	@Autowired
	private MyTaskService myTaskService;

	@Autowired
	private VImsIssueNotificationDAO vImsIssueNotificationDAO;

	@Autowired
	private ImsIssueNotificationDAO imsIssueNotificationDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<VImsIssueNotificationEntity> getNotifyList(String keyId) {
		String hql = "FROM VImsIssueNotificationEntity WHERE issueId = ? AND memberRoleCode != ?";
		List<VImsIssueNotificationEntity> getProgramVehicle =
				this.vImsIssueNotificationDAO.find(hql, keyId, ImsConstants.MEMBER_SUBMIT_USER);
		return getProgramVehicle;
	}
	//知会人存储
	public void saveNotify(String keyId, List<Map<String, String>> insModel){
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		String content = null;
        String returnStr = null;
		VImsIssueEntity entity = this.myTaskService.getIssueById(keyId);
		// 新增
		for (Map<String, String> array : insModel) {
			// 更新
			StringBuffer sql = new StringBuffer();
			sql.append(" INSERT INTO IMS_ISSUE_MEMBER(ID,ISSUE_ID,MEMBER_ROLE_CODE,MEMBER_USERID,CREATE_BY,CREATE_DATE) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?)");
			List<Object> params = new ArrayList<Object>();
			params.add(com.gnomon.pdms.common.PDMSCommon.generateUUID());
			params.add(keyId);
			params.add(ImsConstants.MEMBER_ROLE_INFO_USER);
			params.add(array.get("CurrentId"));
			params.add(loginUser);
			params.add(new Date());
			jdbcTemplate.update(sql.toString(), params.toArray());
			// 当前日期-yyyymmdd格式设定
	        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
	        Date date = new Date();
	        returnStr = f.format(date);
			// 通知提醒设定
			content = returnStr + "【" + entity.getSubmitUserName() + "发起知会】" + entity.getTitle();
			this.sysNoticeService.createIMSNotify("知会通知", content, array.get("CurrentId"), keyId);
		}
	}
	//提交人存储
	public void saveSubmitP(String keyId){
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 新增
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO IMS_ISSUE_MEMBER(ID,ISSUE_ID,MEMBER_ROLE_CODE,MEMBER_USERID,CREATE_BY,CREATE_DATE) ");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?)");
		List<Object> params = new ArrayList<Object>();
		params.add(com.gnomon.pdms.common.PDMSCommon.generateUUID());
		params.add(keyId);
		params.add(ImsConstants.MEMBER_SUBMIT_USER);
		params.add(loginUser);
		params.add(loginUser);
		params.add(new Date());
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	//提交人更新
	public void updateSubmitP(String keyId){
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 新增
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE IMS_ISSUE_MEMBER SET UPDATE_BY = ?,UPDATE_DATE = ? where ISSUE_ID = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(new Date());
		params.add(keyId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
}
