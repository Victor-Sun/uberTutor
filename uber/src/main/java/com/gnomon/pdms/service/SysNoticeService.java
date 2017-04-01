package com.gnomon.pdms.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.dao.SysNoticeDAO;
import com.gnomon.common.system.entity.SysNoticeEntity;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMProgramVMDAO;
import com.gnomon.pdms.entity.PMObsEntity;
import com.gnomon.pdms.entity.PMProgramVMEntity;

@Service
@Transactional
public class SysNoticeService {
	
	@Autowired
	private SysNoticeDAO sysNoticeDAO;
	
	@Autowired
	private PMProgramVMDAO pmProgramVMDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DeptIssueService deptIssueService;
	
	@Autowired
	private ProjectOpenIssueService projectOpenIssueService;
	
	/**
	 * 新建项目通知
	 */
	public void createProjectNotify(Map<String, Object> notifyInfo) {
		List<Map<String, String>> notifyList =
				new ArrayList<Map<String, String>>();
		Map<String, String> notify = new HashMap<String, String>();
		notify.put("content",
				"【" + notifyInfo.get("programName") + "】项目已建成，请及时补充计划信息。");
		notify.put("id", PDMSCommon.nvl(notifyInfo.get("projectManager")));
		notify.put("srcId", PDMSCommon.nvl(notifyInfo.get("programId")));
		notifyList.add(notify);
		// 登录通知
		this.insertPMNotice(
				"新建项目通知",
				PDMSConstants.NOTICE_SOURCE_PM,
				PDMSConstants.NOTICE_TYPE_PLAN,
				notifyList);
	}
	

	
//	/**
//	 * 计划发布通知
//	 */
//	public void deployNotify(String programId, String programVehicleId) {
//		List<Map<String, String>> notifyList =
//				new ArrayList<Map<String, String>>();
//		
//		// 项目名称取得
//		String programName = "";
//		String hql = "FROM PMProgramVMEntity WHERE id = ?";
//		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
//		if (list.size() > 0) {
//			programName = list.get(0).getProgramName();
//		}
//		
//		// 取得通知对象
//		for (PMDeptPlanVMEntity entity : deptList) {
//			// 取得对象组织ID的专业经理
//			String sql = "SELECT PKG_PM_OBS.OBS_FN_GROUP_MGR(?) AS FN_MGR FROM DUAL";
//			Map<String, Object> mgrMap =
//					this.jdbcTemplate.queryForMap(sql, entity.getId());
//			Map<String, String> notify = new HashMap<String, String>();
//			notify.put("content", "【" + programName + "】项目计划已发布。");
//			notify.put("id", PDMSCommon.nvl(mgrMap.get("FN_MGR")));
//			notify.put("srcId", programId);
//			notifyList.add(notify);
//		}
//		
//		// 批量登录通知-发给专业经理
//		this.insertPMNotice(
//				"计划发布通知",
//				PDMSConstants.NOTICE_SOURCE_PM,
//				PDMSConstants.NOTICE_TYPE_PLAN,
//				notifyList);
//	}
	
	/**
	 * 项目主计划更改状态通知
	 */
	public void changeMajorPlanStatusNotify(String programId,
			String programVehicleId, String planStatus) {
		List<Map<String, String>> notifyList = new ArrayList<Map<String, String>>();
		
		// 项目名称取得
		String programName = "";
		String hql = "FROM PMProgramVMEntity WHERE id = ?";
		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
		if (list.size() > 0) {
			programName = list.get(0).getProgramName();
		}
		
		// 取得所有专业领域经理
		String sql = "SELECT * FROM V_PM_FN_GROUP_MGR WHERE VEHICLE_PROGRAM_ID = ?";
		List<Map<String, Object>> mgrList =
				this.jdbcTemplate.queryForList(sql, programVehicleId);
		List<String> userList = new ArrayList<String>();
		for (Map<String, Object> mgr : mgrList) {
			if (PDMSCommon.isNotNull(PDMSCommon.nvl(mgr.get("USER_ID")))) {
				if (userList.indexOf(PDMSCommon.nvl(mgr.get("USER_ID"))) >= 0) {
					continue;
				}
				userList.add(PDMSCommon.nvl(mgr.get("USER_ID")));
				Map<String, String> notify = new HashMap<String, String>();
				if (PDMSConstants.PROGRAM_STATUS_INACTIVE.equals(planStatus)) {
					notify.put("content", "【" + programName + "】项目主计划编制中。");
				} else {
					notify.put("content", "【" + programName + "】项目主计划已完成。");
				}
				notify.put("id", PDMSCommon.nvl(mgr.get("USER_ID")));
				notify.put("srcId", programId);
				notifyList.add(notify);
			}
		}
		if (notifyList.size() == 0) {
			return;
		}
		
		// 批量登录通知
		this.insertPMNotice(
				"项目主计划更改状态通知",
				PDMSConstants.NOTICE_SOURCE_PM,
				PDMSConstants.NOTICE_TYPE_PLAN,
				notifyList);
	}
	
//	/**
//	 * 项目主计划预发布取消通知
//	 */
//	public void dePreDeployMainNotify(String programId, String programVehicleId) {
//		List<Map<String, String>> notifyList =
//				this.getMajorPlanNotifyList(programId, programVehicleId, 1);
//		if (notifyList.size() == 0) {
//			return;
//		}
//		// 批量登录通知
//		this.insertPMNotice(
//				"主计划预发布取消通知",
//				PDMSConstants.NOTICE_SOURCE_PM,
//				PDMSConstants.NOTICE_TYPE_PLAN,
//				notifyList);
//	}
	
	/**
	 * 项目二级计划更改状态通知
	 */
	public void changeDeptPlanStatusNotify(String programId, String obsId,
			String planStatus) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 专业领域名称取得
		PMObsEntity obsEntity = this.pmObsDAO.get(obsId);
		String obsName = obsEntity.getObsName();
		
		// 项目经理ID取得
		String pm = "";
		String hql = "FROM PMProgramVMEntity WHERE id = ?";
		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
		if (list.size() > 0) {
			pm = list.get(0).getPm();
		}
		
		// 登录通知
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle("二级计划更改状态通知");
		if (PDMSConstants.PROGRAM_STATUS_INACTIVE.equals(planStatus)) {
			entity.setContent("【" + obsName + "】二级计划编制中。");
		} else {
			entity.setContent("【" + obsName + "】二级计划已完成。");
		}
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(programId);
		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_PLAN);
		entity.setUserid(pm);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
//	/**
//	 * 项目二级计划预发布取消通知
//	 */
//	public void dePreDeployDeptNotify(String programId, String obsId) {
//		this.deployDeptNotify(programId, obsId, 1);
//	}
	
//	/**
//	 * 项目更改状态通知
//	 */
//	public void changeProgramStatusNotify(String programId,
//			String programVehicleId, String planStatus) {
//		List<Map<String, String>> notifyList = new ArrayList<Map<String, String>>();
//		
//		// 项目名称取得
//		String programName = "";
//		String hql = "FROM PMProgramVMEntity WHERE id = ?";
//		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
//		if (list.size() > 0) {
//			programName = list.get(0).getProgramName();
//		}
//		
//		// 取得所有专业领域经理
//		String sql = "SELECT * FROM V_PM_FN_GROUP_MGR WHERE VEHICLE_PROGRAM_ID = ?";
//		List<Map<String, Object>> mgrList =
//				this.jdbcTemplate.queryForList(sql, programVehicleId);
//		for (Map<String, Object> mgr : mgrList) {
//			if (PDMSCommon.isNotNull(PDMSCommon.nvl(mgr.get("USER_ID")))) {
//				Map<String, String> notify = new HashMap<String, String>();
//				if (PDMSConstants.PROGRAM_STATUS_INACTIVE.equals(planStatus)) {
//					notify.put("content", "【" + programName + "】项目计划编制中。");
//				} else if (PDMSConstants.PROGRAM_STATUS_ACTIVE.equals(planStatus)) {
//					notify.put("content", "【" + programName + "】项目计划已发布。");
//				} else {
//					notify.put("content", "【" + programName + "】项目计划已完成。");
//				}
//				notify.put("id", PDMSCommon.nvl(mgr.get("USER_ID")));
//				notify.put("srcId", programId);
//				notifyList.add(notify);
//			}
//		}
//		if (notifyList.size() == 0) {
//			return;
//		}
//		
//		// 批量登录通知
//		this.insertPMNotice(
//				"项目更改状态通知",
//				PDMSConstants.NOTICE_SOURCE_PM,
//				PDMSConstants.NOTICE_TYPE_PLAN,
//				notifyList);
//	}
	
	/**
	 * 计划变更通知
	 */
	public void changePlanNotify(String programId, String programVehicleId) {
		List<Map<String, String>> notifyList = new ArrayList<Map<String, String>>();
		
		// 项目名称取得
		String programName = "";
		String hql = "FROM PMProgramVMEntity WHERE id = ?";
		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
		if (list.size() > 0) {
			programName = list.get(0).getProgramName();
		}
		
		// 取得所有专业领域经理
		String sql = "SELECT * FROM V_PM_FN_GROUP_MGR WHERE VEHICLE_PROGRAM_ID = ?";
		List<Map<String, Object>> mgrList =
				this.jdbcTemplate.queryForList(sql, programVehicleId);
		List<String> userList = new ArrayList<String>();
		for (Map<String, Object> mgr : mgrList) {
			if (PDMSCommon.isNotNull(PDMSCommon.nvl(mgr.get("USER_ID")))) {
				if (userList.indexOf(PDMSCommon.nvl(mgr.get("USER_ID"))) >= 0) {
					continue;
				}
				userList.add(PDMSCommon.nvl(mgr.get("USER_ID")));
				Map<String, String> notify = new HashMap<String, String>();
				notify.put("content", "【" + programName + "】项目计划开始变更。");
				notify.put("id", PDMSCommon.nvl(mgr.get("USER_ID")));
				notify.put("srcId", programId);
				notifyList.add(notify);
			}
		}
		if (notifyList.size() == 0) {
			return;
		}
		
		// 批量登录通知
		this.insertPMNotice(
				"计划变更通知",
				PDMSConstants.NOTICE_SOURCE_PM,
				PDMSConstants.NOTICE_TYPE_PLAN,
				notifyList);
	}
	
	/**
	 * 项目任务下发通知
	 */
	public void deployTaskNotify(List<Map<String, Object>> taskList) {
		List<Map<String, String>> notifyList =
				new ArrayList<Map<String, String>>();
		
		for (Map<String, Object> map : taskList) {
			if (PDMSCommon.isNotNull(PDMSCommon.nvl(map.get("TASK_OWNER")))) {
				Map<String, String> notify = new HashMap<String, String>();
				notify.put("content", "您有一个任务【" + map.get("TASK_NAME") + "】待处理。");
				notify.put("id", PDMSCommon.nvl(map.get("TASK_OWNER")));
				notify.put("srcId", PDMSCommon.nvl(map.get("ID")));
				notifyList.add(notify);
			}
		}
		
		// 批量登录通知-发给任务负责人
		this.insertPMNotice(
				"待办任务通知",
				PDMSConstants.NOTICE_SOURCE_PM,
				PDMSConstants.NOTICE_TYPE_TASK,
				notifyList);
	}
	
	/**
	 * 项目任务审核提交通知(下一节点)
	 */
	public void taskSubmitNotify(String taskId, String userId) {
		if (PDMSCommon.isNull(userId)) {
			return;
		}
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 登录通知		
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle("流程处理通知");
		entity.setContent("您有一个流程待审核。");
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(taskId);
		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_TASK);
		entity.setUserid(userId);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * 项目任务审核不通过通知
	 */
	public void taskRejectNotify(String taskId, String userId) {
		if (PDMSCommon.isNull(userId)) {
			return;
		}
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 登录通知		
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle("流程退回通知");
		entity.setContent("您有一个流程被退回。");
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(taskId);
		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_TASK);
		entity.setUserid(userId);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * 状态变更流程通知
	 */
	public void taskProcessNotify(String processId, String userId, String action) {
		if (PDMSCommon.isNull(userId)) {
			return;
		}
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 登录通知		
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		if ("NEXT".equals(action)) {
			entity.setTitle("流程处理通知");
			entity.setContent("您有一个状态变更审批流程需要处理。");
		} else {
			entity.setTitle("流程退回通知");
			entity.setContent("您有一个状态变更审流程被退回。");
		}
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(processId);
		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_TASK_PROCESS);
		entity.setUserid(userId);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * 新建质量问题通知
	 */
	public void createIMSNotify(String title,String content,String notifyUserId,String issueId) {
		List<Map<String, String>> notifyList =
				new ArrayList<Map<String, String>>();
		Map<String, String> notify = new HashMap<String, String>();
		notify.put("content",
				content);
		notify.put("id", notifyUserId);
		notify.put("srcId", issueId);
		notifyList.add(notify);
		// 登录通知
		this.insertPMNotice(
				title,
				PDMSConstants.NOTICE_SOURCE_QIM,
				PDMSConstants.NOTICE_TYPE_ISSUE,
				notifyList);
	}
	
	/**
	 * 任务延期通知
	 */
	public void extensionTaskNotify(String taskId) {
		// 任务延期发送通知给关联节点负责人
		StringBuffer sql = null;
		List<Object> params = null;
		List<Map<String, String>> notifyList = null;
		
		// 关联节点延期通知（后置）
		sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" T1.TASK_NAME");
		sql.append(",T2.TASK_NAME AS RELATION_TASK_NAME");
		sql.append(",T2.TASK_OWNER AS RELATION_TASK_OWNER");
		sql.append(" FROM");
		sql.append(" PM_TASK T1 LEFT JOIN PM_TASK T2");
		sql.append(" ON T1.RELATION_TASK_ID = T2.ID");
		sql.append(" AND T2.DELETE_BY IS NULL");
		sql.append(" WHERE");
		sql.append(" T1.ID = ?");
		String relationTaskOwner = "";
		String relationTaskName = "";
		String taskName = "";
		try {
			Map<String, Object> taskMap = this.jdbcTemplate.queryForMap(sql.toString(), taskId);
			relationTaskOwner = PDMSCommon.nvl(taskMap.get("RELATION_TASK_OWNER"));
			relationTaskName = PDMSCommon.nvl(taskMap.get("RELATION_TASK_NAME"));
			taskName = PDMSCommon.nvl(taskMap.get("TASK_NAME"));
		} catch(EmptyResultDataAccessException ex) {
		}
		if (PDMSCommon.isNotNull(relationTaskOwner)) {
			notifyList = new ArrayList<Map<String, String>>();
			Map<String, String> notifyMap = new HashMap<String, String>();
			notifyMap.put("content", "与您的任务【" + relationTaskName + "】相关联的任务【" + taskName + "】有延期变更。");
			notifyMap.put("srcId", taskId);
			notifyMap.put("id", relationTaskOwner);
			notifyList.add(notifyMap);
			// 延期通知
			this.insertPMNotice("关联节点延期通知（后置）",
					PDMSConstants.NOTICE_SOURCE_PM,
					PDMSConstants.NOTICE_TYPE_TASK_READONLY,
					notifyList);
		}
		
		// 关联节点延期通知（前置）
		sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" TASK_NAME");
		sql.append(",TASK_OWNER");
		sql.append(" FROM");
		sql.append(" PM_TASK");
		sql.append(" WHERE");
		sql.append(" RELATION_TASK_ID = ?");
		sql.append(" AND DELETE_BY IS NULL");
		List<Map<String, Object>> taskList = this.jdbcTemplate.queryForList(sql.toString(), taskId);
		notifyList = new ArrayList<Map<String, String>>();
		for (Map<String, Object> task : taskList) {
			if (PDMSCommon.isNull(PDMSCommon.nvl(task.get("TASK_OWNER")))) {
				continue;
			}
			Map<String, String> notifyMap = new HashMap<String, String>();
			notifyMap.put("content", "与您的任务【" + PDMSCommon.nvl(task.get("TASK_NAME")) + "】相关联的任务【" + taskName + "】有延期变更。");
			notifyMap.put("srcId", taskId);
			notifyMap.put("id", PDMSCommon.nvl(task.get("TASK_OWNER")));
			notifyList.add(notifyMap);
		}
		if (notifyList.size() > 0) {
			// 延期通知
			this.insertPMNotice("关联节点延期通知（前置）",
					PDMSConstants.NOTICE_SOURCE_PM,
					PDMSConstants.NOTICE_TYPE_TASK_READONLY,
					notifyList);
		}
		
		// 前置任务延期通知
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" PRE_TASK_NAME");
		sql.append(",POST_TASK_NAME");
		sql.append(",POST_TASK_OWNER");
		sql.append(" FROM");
		sql.append(" V_PM_PRE_TASK");
		sql.append(" WHERE");
		sql.append(" PRE_TASK_ID = ?");
		params.add(taskId);
		List<Map<String, Object>> postList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		// 通知信息
		notifyList = new ArrayList<Map<String, String>>();
		for (Map<String, Object> map : postList) {
			if (PDMSCommon.isNull(PDMSCommon.nvl(map.get("POST_TASK_OWNER")))) {
				continue;
			}
			Map<String, String> notifyMap = new HashMap<String, String>();
			notifyMap.put("content", "您的任务【" + map.get("POST_TASK_NAME") + "】的前置任务【" + map.get("PRE_TASK_NAME") + "】有延期变更。");
			notifyMap.put("srcId", taskId);
			notifyMap.put("id", PDMSCommon.nvl(map.get("POST_TASK_OWNER")));
			notifyList.add(notifyMap);
		}
		// 延期通知
		if (notifyList.size() > 0) {
			this.insertPMNotice("前置任务延期通知",
					PDMSConstants.NOTICE_SOURCE_PM,
					PDMSConstants.NOTICE_TYPE_TASK_READONLY,
					notifyList);
		}
		
		// 后置任务延期通知
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" PRE_TASK_NAME");
		sql.append(",PRE_TASK_OWNER");
		sql.append(",POST_TASK_NAME");
		sql.append(" FROM");
		sql.append(" V_PM_PRE_TASK");
		sql.append(" WHERE");
		sql.append(" TASK_ID = ?");
		params.add(taskId);
		List<Map<String, Object>> preList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		// 通知信息
		notifyList = new ArrayList<Map<String, String>>();
		for (Map<String, Object> map : preList) {
			if (PDMSCommon.isNull(PDMSCommon.nvl(map.get("PRE_TASK_OWNER")))) {
				continue;
			}
			Map<String, String> notifyMap = new HashMap<String, String>();
			notifyMap.put("content", "您的任务【" + map.get("PRE_TASK_NAME") + "】的后置任务【" + map.get("POST_TASK_NAME") + "】有延期变更。");
			notifyMap.put("srcId", taskId);
			notifyMap.put("id", PDMSCommon.nvl(map.get("PRE_TASK_OWNER")));
			notifyList.add(notifyMap);
		}
		// 延期通知
		if (notifyList.size() > 0) {
			this.insertPMNotice("后置任务延期通知",
					PDMSConstants.NOTICE_SOURCE_PM,
					PDMSConstants.NOTICE_TYPE_TASK_READONLY,
					notifyList);
		}
	}
	
	/**
	 * 摘牌通知
	 */
	public void delListingIssueNotice(String issueId) {
		StringBuffer sql = new StringBuffer();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 挂牌人取得
		String listingBy = "";
		sql.append(" SELECT");
		sql.append(" LISTING_BY");
		sql.append(" FROM");
		sql.append(" IMS_ISSUE_LISTING");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		sql.append(" AND IS_ACTIVE = ?");
		List<Map<String, Object>> listingInfo = this.jdbcTemplate.queryForList(
				sql.toString(), issueId, PDMSConstants.STATUS_Y);
		if (listingInfo.size() > 0) {
			listingBy = PDMSCommon.nvl(listingInfo.get(0).get("LISTING_BY"));
		}
		if (PDMSCommon.isNull(listingBy)) {
			return;
		}
		
		// 摘牌通知
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle("摘牌通知");
		entity.setContent("您有一个问题已被摘牌。");
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_QIM);
		entity.setIsNew("Y");
		entity.setSourceId(issueId);
		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_ISSUE);
		entity.setUserid(listingBy);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * OpenIssue任务通知
	 */
	public void openIssueNotify(String noticeType, Long issueId) {
		StringBuffer sql = null;
		String content = "";
		String taskOwner = "";
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		if (PDMSConstants.NOTICE_TYPE_DEPT_ISSUE.equals(noticeType)) {
			// 部门OpenIssue信息取得
			sql = new StringBuffer();
			sql.append(" SELECT");
			sql.append(" *");
			sql.append(" FROM");
			sql.append(" V_PM_DEPT_ISSUE");
			sql.append(" WHERE");
			sql.append(" ISSUE_ID = ?");
			List<Map<String, Object>> issueList = this.jdbcTemplate.queryForList(
					sql.toString(), issueId);
			if (issueList.size() > 0) {
				Map<String, Object> issue = issueList.get(0);
				content = "您有一个部门OpenIssue【" + issue.get("ISSUE_TITLE") + "】需要处理。";
				taskOwner = PDMSCommon.nvl(issue.get("TASK_OWER_ID"));
			}
		} else if (PDMSConstants.NOTICE_TYPE_PM_ISSUE.equals(noticeType)) {
			// 项目OpenIssue信息取得
			sql = new StringBuffer();
			sql.append(" SELECT");
			sql.append(" *");
			sql.append(" FROM");
			sql.append(" V_PM_ISSUE");
			sql.append(" WHERE");
			sql.append(" ISSUE_ID = ?");
			List<Map<String, Object>> issueList = this.jdbcTemplate.queryForList(
					sql.toString(), issueId);
			if (issueList.size() > 0) {
				Map<String, Object> issue = issueList.get(0);
				content = "您有一个项目OpenIssue【" + issue.get("ISSUE_TITLE") + "】需要处理。";
				taskOwner = PDMSCommon.nvl(issue.get("TASK_OWER_ID"));
			}
		}
		// 通知
		if (PDMSCommon.isNotNull(taskOwner)) {
			// 通知
			SysNoticeEntity entity = new SysNoticeEntity();
			entity.setId(PDMSCommon.generateUUID());
			entity.setTitle("OpenIssue处理通知");
			entity.setContent(content);
			entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
			entity.setIsNew("Y");
			entity.setSourceId(PDMSCommon.nvl(issueId));
			entity.setNoticeTypeCode(noticeType);
			entity.setUserid(taskOwner);
			entity.setCreateBy(loginUser);
			entity.setCreateDate(new Date());
			this.sysNoticeDAO.save(entity);
		}
	}
	
	/**
	 * 协作成员添加通知
	 */
	public void cooperationNotify(String noticeType, String issueId, List<Map<String, String>> memberList) {
		List<Map<String, String>> notifyList =
				new ArrayList<Map<String, String>>();
		String content = "";
		
		if (PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CT.equals(noticeType)) {
			// 部门OpenIssue信息取得
			Map<String, Object> issue = this.deptIssueService.getDeptIssue(issueId);
			content = "您有一个部门OpenIssue【" + issue.get("ISSUE_TITLE") + "】需要参与协作。";
		} else if (PDMSConstants.NOTICE_TYPE_PM_ISSUE_CT.equals(noticeType)) {
			// 项目OpenIssue信息取得
			Map<String, Object> issue = this.projectOpenIssueService.getOpenIssue(
					PDMSCommon.toLong(issueId));
			content = "您有一个项目OpenIssue【" + issue.get("TITLE") + "】需要参与协作。";
		}
		
		for (Map<String, String> map : memberList) {
			if (PDMSCommon.isNotNull(map.get("id"))) {
				Map<String, String> notify = new HashMap<String, String>();
				notify.put("content", content);
				notify.put("id", map.get("id"));
				notify.put("srcId", issueId);
				notifyList.add(notify);
			}
		}
		
		// 批量登录通知
		this.insertPMNotice(
				"协作通知",
				PDMSConstants.NOTICE_SOURCE_PM,
				noticeType,
				notifyList);
	}
	
	/**
	 * 发表留言通知
	 */
	public void postNotice(String userId, String source, String sourceId) {
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 问题信息取得
		sql = new StringBuffer();
		String notifyUser = "";
		String issueTitle = "";
		String issueId = "";
		if ("PM-ISSUE".equals(source) || "PM-ISSUE-COMMENT".equals(source)) {
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" PM_ISSUE");
			sql.append(" WHERE");
			sql.append(" UUID = ?");
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
					sql.toString(), sourceId);
			if (list.size() > 0) {
				issueId = PDMSCommon.nvl(list.get(0).get("ID"));
				issueTitle = PDMSCommon.nvl(list.get(0).get("TITLE"));
				notifyUser = PDMSCommon.nvl(list.get(0).get("RESP_USER_ID"));
			}
		} else if ("PM-DEPT-ISSUE".equals(source) || "PM-DEPT-ISSUE-COMMENT".equals(source)) {
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" PM_DEPT_ISSUE");
			sql.append(" WHERE");
			sql.append(" UUID = ?");
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
					sql.toString(), sourceId);
			if (list.size() > 0) {
				issueId = PDMSCommon.nvl(list.get(0).get("ID"));
				issueTitle = PDMSCommon.nvl(list.get(0).get("TITLE"));
				notifyUser = PDMSCommon.nvl(list.get(0).get("RESP_USER_ID"));
			}
		}
		if (PDMSCommon.isNull(notifyUser)) {
			return;
		}
		
		// 发表人姓名取得
		String userName = "";
		sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" USERNAME");
		sql.append(" FROM");
		sql.append(" SYS_USER");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
				sql.toString(), userId);
		if (list.size() > 0) {
			userName = PDMSCommon.nvl(list.get(0).get("USERNAME"));
		}
		
		String title = "";
		String content = "";
		String noticeTypeCode = "";
		if ("PM-ISSUE".equals(source)) {
			title = "项目OpenIssue 协作空间留言发表通知";
			content = userName + "对您的问题【" + issueTitle +"】发表了协作留言。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_PM_ISSUE_CT;
		} else if ("PM-ISSUE-COMMENT".equals(source)) {
			title = "项目OpenIssue 评论空间留言发表通知";
			content = userName + "对您的问题【" + issueTitle +"】发表了评论留言。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_PM_ISSUE_CMT;
		} else if ("PM-DEPT-ISSUE".equals(source)) {
			title = "部门OpenIssue 协作空间留言发表通知";
			content = userName + "对您的问题【" + issueTitle +"】发表了协作留言。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CT;
		} else if ("PM-DEPT-ISSUE-COMMENT".equals(source)) {
			title = "部门OpenIssue 评论空间留言发表通知";
			content = userName + "对您的问题【" + issueTitle +"】发表了评论留言。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CMT;
		}
		
		// 通知
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle(title);
		entity.setContent(content);
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(issueId);
		entity.setNoticeTypeCode(noticeTypeCode);
		entity.setUserid(notifyUser);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * 回复留言通知
	 */
	public void replyPostNotice(String userId, Long parentPostId) {
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 留言信息取得
		sql = new StringBuffer();
		Map<String, Object> postMap = null;
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" PM_POST");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
				sql.toString(), parentPostId);
		if (list.size() > 0) {
			postMap = list.get(0);
		}
		if (postMap == null) {
			return;
		}
		String source = PDMSCommon.nvl(postMap.get("SOURCE"));
		String sourceId = PDMSCommon.nvl(postMap.get("SOURCE_INSTANCE_ID"));
		String notifyUser = PDMSCommon.nvl(postMap.get("USER_ID"));
		if (PDMSCommon.isNull(notifyUser)) {
			return;
		}
		
		// 问题信息取得
		sql = new StringBuffer();
		String issueTitle = "";
		String issueId = "";
		if ("PM-ISSUE".equals(source) || "PM-ISSUE-COMMENT".equals(source)) {
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" PM_ISSUE");
			sql.append(" WHERE");
			sql.append(" UUID = ?");
			list = this.jdbcTemplate.queryForList(sql.toString(), sourceId);
			if (list.size() > 0) {
				issueId = PDMSCommon.nvl(list.get(0).get("ID"));
				issueTitle = PDMSCommon.nvl(list.get(0).get("TITLE"));
			}
		} else if ("PM-DEPT-ISSUE".equals(source) || "PM-DEPT-ISSUE-COMMENT".equals(source)) {
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" PM_DEPT_ISSUE");
			sql.append(" WHERE");
			sql.append(" UUID = ?");
			list = this.jdbcTemplate.queryForList(sql.toString(), sourceId);
			if (list.size() > 0) {
				issueId = PDMSCommon.nvl(list.get(0).get("ID"));
				issueTitle = PDMSCommon.nvl(list.get(0).get("TITLE"));
			}
		}
		
		// 发表人姓名取得
		String userName = "";
		sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" USERNAME");
		sql.append(" FROM");
		sql.append(" SYS_USER");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		list = this.jdbcTemplate.queryForList(sql.toString(), userId);
		if (list.size() > 0) {
			userName = PDMSCommon.nvl(list.get(0).get("USERNAME"));
		}
		
		String title = "";
		String content = "";
		String noticeTypeCode = "";
		if ("PM-ISSUE".equals(source)) {
			title = "项目OpenIssue 协作空间留言回复通知";
			content = userName + "对您在问题【" + issueTitle +"】中的留言进行了回复。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_PM_ISSUE_CT;
		} else if ("PM-ISSUE-COMMENT".equals(source)) {
			title = "项目OpenIssue 评论空间留言回复通知";
			content = userName + "对您在问题【" + issueTitle +"】中的留言进行了回复。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_PM_ISSUE_CMT;
		} else if ("PM-DEPT-ISSUE".equals(source)) {
			title = "部门OpenIssue 协作空间留言回复通知";
			content = userName + "对您在问题【" + issueTitle +"】中的留言进行了回复。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CT;
		} else if ("PM-DEPT-ISSUE-COMMENT".equals(source)) {
			title = "部门OpenIssue 评论空间留言回复通知";
			content = userName + "对您在问题【" + issueTitle +"】中的留言进行了回复。";
			noticeTypeCode = PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CMT;
		}
		
		// 通知
		SysNoticeEntity entity = new SysNoticeEntity();
		entity.setId(PDMSCommon.generateUUID());
		entity.setTitle(title);
		entity.setContent(content);
		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
		entity.setIsNew("Y");
		entity.setSourceId(issueId);
		entity.setNoticeTypeCode(noticeTypeCode);
		entity.setUserid(notifyUser);
		entity.setCreateBy(loginUser);
		entity.setCreateDate(new Date());
		this.sysNoticeDAO.save(entity);
	}
	
	/**
	 * 批量登录通知
	 */
	private void insertPMNotice(final String title,
			final String src, final String typeCode,
			final List<Map<String, String>> notifyList) {
		// 登录用户取得
		final String loginUser = SessionData.getLoginUserId();
		
		// 更新项目信息
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO SYS_NOTICE(");
		sql.append(" ID");
		sql.append(",TITLE");
		sql.append(",CONTENT");
		sql.append(",NOTICE_SOURCE_CODE");
		sql.append(",SOURCE_ID");
		sql.append(",NOTICE_TYPE_CODE");
		sql.append(",IS_NEW");
		sql.append(",USERID");
		sql.append(",CREATE_BY");
		sql.append(",CREATE_DATE");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",SYSDATE)");
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return notifyList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, String> notify = notifyList.get(i);
            	ps.setString(1, com.gnomon.pdms.common.PDMSCommon.generateUUID());
            	ps.setString(2, title);
            	ps.setString(3, notify.get("content"));
            	ps.setString(4, src);
            	ps.setString(5, notify.get("srcId"));
            	ps.setString(6, typeCode);
            	ps.setString(7, "Y");
            	ps.setString(8, notify.get("id"));
            	ps.setString(9, loginUser);
            }
        });
	}
	
//	/**
//	 * 主计划计划完成/计划完成取消通知对象取得
//	 */
//	private List<Map<String, String>> getMajorPlanNotifyList(
//			String programId, String programVehicleId, String planStatus) {
//		List<Map<String, String>> notifyList =
//				new ArrayList<Map<String, String>>();
//		// 项目名称取得
//		String programName = "";
//		String hql = "FROM PMProgramVMEntity WHERE id = ?";
//		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
//		if (list.size() > 0) {
//			programName = list.get(0).getProgramName();
//		}
//		
//		// 取得所有专业领域经理
//		String sql = "SELECT * FROM V_PM_FN_GROUP_MGR WHERE VEHICLE_PROGRAM_ID = ?";
//		List<Map<String, Object>> mgrList =
//				this.jdbcTemplate.queryForList(sql, programVehicleId);
//		for (Map<String, Object> mgr : mgrList) {
//			if (PDMSCommon.isNotNull(PDMSCommon.nvl(mgr.get("USER_ID")))) {
//				Map<String, String> notify = new HashMap<String, String>();
//				if (PDMSConstants.PROGRAM_STATUS_INACTIVE.equals(planStatus)) {
//					notify.put("content", "【" + programName + "】项目主计划编制中。");
//				} else if (PDMSConstants.PROGRAM_STATUS_ACTIVE.equals(planStatus)) {
//					notify.put("content", "【" + programName + "】项目主计划已发布。");
//				} else {
//					notify.put("content", "【" + programName + "】项目主计划已完成。");
//				}
//				notify.put("id", PDMSCommon.nvl(mgr.get("USER_ID")));
//				notify.put("srcId", programId);
//				notifyList.add(notify);
//			}
//		}
//		return notifyList;
//	}
	
//	/**
//	 * 二级计划完成/计划完成取消通知
//	 */
//	private void deployDeptNotify(String programId, String obsId, String planStatus) {
//		// 登录用户取得
//		String loginUser = SessionData.getLoginUserId();
//		
//		// 专业领域名称取得
//		PMObsEntity obsEntity = this.pmObsDAO.get(obsId);
//		String obsName = obsEntity.getObsName();
//		
//		// 项目经理ID取得
//		String pm = "";
//		String hql = "FROM PMProgramVMEntity WHERE id = ?";
//		List<PMProgramVMEntity> list = this.pmProgramVMDAO.find(hql, programId);
//		if (list.size() > 0) {
//			pm = list.get(0).getPm();
//		}
//		
//		// 登录通知
//		SysNoticeEntity entity = new SysNoticeEntity();
//		entity.setId(PDMSCommon.generateUUID());
//		if (PDMSConstants.PROGRAM_STATUS_INACTIVE.equals(planStatus)) {
//			entity.setTitle("二级计划更改状态通知");
//			entity.setContent("【" + obsName + "】二级计划取消完成状态。");
//		} else {
//			entity.setTitle("二级计划更改状态通知");
//			entity.setContent("【" + obsName + "】二级计划已完成。");
//		}
//		entity.setNoticeSourceCode(PDMSConstants.NOTICE_SOURCE_PM);
//		entity.setIsNew("Y");
//		entity.setSourceId(programId);
//		entity.setNoticeTypeCode(PDMSConstants.NOTICE_TYPE_PLAN);
//		entity.setUserid(pm);
//		entity.setCreateBy(loginUser);
//		entity.setCreateDate(new Date());
//		this.sysNoticeDAO.save(entity);
//	}
}