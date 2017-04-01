package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.SysUserEntity;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.ImsConstants;
import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.dao.ImsIssueApprovelogDAO;
import com.gnomon.pdms.dao.ImsIssuePartDAO;
import com.gnomon.pdms.dao.ImsIssueVerificationDAO;
import com.gnomon.pdms.dao.SysUserDAO;
import com.gnomon.pdms.dao.VImsIssueDAO;
import com.gnomon.pdms.dao.VImsIssuePartDAO;
import com.gnomon.pdms.dao.VImsIssueTaskDAO;
import com.gnomon.pdms.dao.VImsIssueVerificationDAO;
import com.gnomon.pdms.dao.VImsParticipationDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsIssueApprovelogEntity;
import com.gnomon.pdms.entity.ImsIssuePartEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.entity.VImsIssueVerificationEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;

@Service
@Transactional
public class MyTaskService {
	private static final Log log = LogFactory.getLog(MyTaskService.class);
	
//	private GTWorkFlowService workflowService = new GTWorkFlowService();
	
	@Autowired
	private ImsOrgUserService imsOrgUserService;
	
	@Autowired
	private CodeInfoService codeInfoService;
	
	@Autowired
	private	ProjectInfoService projectInfoService;
	
	@Autowired
	private	ImsIssueMarkService imsIssueMarkService;

	@Autowired
	private VImsIssueDAO vImsIssueDAO;

	@Autowired
	private VImsIssueTaskDAO vImsIssueTaskDAO;

	@Autowired
	private GTIssueDAO gtIssueDAO;
	
	@Autowired
	private ImsIssuePartDAO imsIssuePartDAO;

	@Autowired
	private ImsIssueVerificationDAO imsIssueVerificationDAO;
	
	@Autowired
	private VImsIssueVerificationDAO vImsIssueVerificationDAO;
	
	@Autowired
	private ImsIssueApprovelogDAO imsIssueApprovelogDAO;
	
	@Autowired
	private VImsIssuePartDAO vImsIssuePartDAO;
	
	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Autowired
	private VImsParticipationDAO vImsParticipationDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//删除零件
	public ImsIssuePartEntity deletePart(String partId){
		ImsIssuePartEntity deletePart =
				this.imsIssuePartDAO.findUniqueBy("id", partId);
        return deletePart;
	}
	//删除方案
	public ImsIssueVerificationEntity deletePlan(String UId){
		ImsIssueVerificationEntity deletePart =
				this.imsIssueVerificationDAO.findUniqueBy("id", UId);
        return deletePart;
	}
	//我的待办
	public List<GTIssueEntity> getMyTaskList() {
		List<GTIssueEntity> myTaskList =
				this.gtIssueDAO.getAll();
        return myTaskList;
	}
	//验证方案-KeyId
	public List<VImsIssueVerificationEntity> getIssueVerList(String keyId) {
		List<VImsIssueVerificationEntity> myIssueVerList =
				this.vImsIssueVerificationDAO.getAll();
        return myIssueVerList;
    }
	//验证方案-view
	public VImsIssueVerificationEntity getIssueVer(String UId) {

		VImsIssueVerificationEntity issueVer =
				this.vImsIssueVerificationDAO.findUniqueBy("id", UId);
        return issueVer;
    }
	//验证方案-表UId
	public ImsIssueVerificationEntity getVer(String UId) {

		ImsIssueVerificationEntity issueVer =
				this.imsIssueVerificationDAO.findUniqueBy("id", UId);
        return issueVer;
    }
	//一页纸报告
	public VImsIssueEntity getMyTaskOneRep(String keyId) {

		VImsIssueEntity myTaskOneRep =
				this.vImsIssueDAO.findUniqueBy("id", keyId);
        return myTaskOneRep;
    }
	//其它
	public GTIssueEntity getMyTaskResponConfirm(String keyId) {

		GTIssueEntity myTaskResponConfirm =
				this.gtIssueDAO.findUniqueBy("id", keyId);
		
        return myTaskResponConfirm;
    }
  	public SysUserEntity getSysUser(String keyId) {
  		SysUserEntity sysUser =
  				this.sysUserDAO.findUniqueBy("id", keyId);
          return sysUser;
    }
  	
  	//查找本人是否已经参与此问题
  	public List<VImsParticipationEntity> getMyParticipation(String keyId, String userId){
  		String hql = "FROM VImsParticipationEntity WHERE id = ? and memberUserid = ?";
  		List<VImsParticipationEntity> list =
  				this.vImsParticipationDAO.find( hql, keyId, userId);
        return list;
  	}
  	//添加我参与的问题List
  	public void insertMyParticipation(String keyId){
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
  	//更新我参与的问题List
  	public void updateMyParticipation(String keyId){
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 新增
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE IMS_ISSUE_MEMBER SET UPDATE_BY = ?,UPDATE_DATE = ? where ISSUE_ID = ? and MEMBER_USERID = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(new Date());
		params.add(keyId);
		params.add(loginUser);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	//问题编号生成
//	public String getMyTaskQeCode(String keyId) {
//
//		VImsIssueEntity myTaskQeCode =
//				this.vImsIssueDAO.findUniqueBy("id", keyId);
//		//问题编号之序列号生成
//// 		String projectName = myTaskQeCode.getProjectName();
//		String subProjectCode = myTaskQeCode.getSubProjectCode();
//		List<String> params = new ArrayList<String>();
//		
//		String hql = "FROM VImsIssueEntity WHERE projectName = ? AND code is not null";
////		hql += " AND issueLifecycleCode != 'ISSUE_LIFECYCLE_DRAFT'";
////		params.add(projectName);
//		String sCode = null ;
//
//		if (PDMSCommon.isNotNull(subProjectCode)) {
//			hql += " AND subProjectCode = ?";
//			params.add(subProjectCode);
//			List<VImsIssueEntity> getCountList =
//					this.vImsIssueDAO.find(hql, params.toArray());
//			getCountList.size();
//
////			sCode = projectName + "-" + subProjectCode.trim() + "-"
////						+ StringUtils.leftPad(String.valueOf(getCountList.size()+1), 4, "0");
//		} else {
//			hql += " AND subProjectCode is null";
//			List<VImsIssueEntity> getCountList =
//					this.vImsIssueDAO.find(hql, params.toArray());
//			getCountList.size();
//
////			sCode = projectName + "-" + StringUtils.leftPad(String.valueOf(getCountList.size()+1), 4, "0");
//		}
//		
//        return sCode;
//    }
	
	//对策决定-get
	public ImsIssuePartEntity getMyTaskImsIssuePart(String keyId) {

		ImsIssuePartEntity myTaskImsIssue =
				this.imsIssuePartDAO.findUniqueBy("id", keyId);
        return myTaskImsIssue;
    }
	//IMS_ISSUE_VERIFICATION表存取数据，对策决定～效果确认
	public ImsIssueVerificationEntity getImsIssueVerification(String keyId) {

		ImsIssueVerificationEntity myTaskImsIssueVerification =
				this.imsIssueVerificationDAO.findUniqueBy("id", keyId);
        return myTaskImsIssueVerification;
    }
	//验证方案-view
	public List<VImsIssueVerificationEntity> getVerificationScheme(String keyId) {
		
		String hql = "FROM VImsIssueVerificationEntity WHERE issueId = ? order by seq asc";
		List<VImsIssueVerificationEntity> verificationScheme =
				this.vImsIssueVerificationDAO.find(hql, keyId);
		return verificationScheme;
	}
	
	//验证方案-view
	public List<Map<String, Object>> getVerification(String keyId, boolean historyFlag) {
		String sql = "";
		if (historyFlag) {
			sql = "select * from V_IMS_HST_ISSUE_VERIFICATION where HST_ISSUE_ID = ?";
		} else {
			sql = "select * from V_IMS_ISSUE_VERIFICATION where ISSUE_ID = ?";
		}
		return jdbcTemplate.queryForList(sql, keyId);
	}
	
	//验证方案-view
	public List<Map<String, Object>> getVerificationById(String vid) {
		return jdbcTemplate.queryForList("select * from V_IMS_ISSUE_VERIFICATION where ID=? ",vid);
	}
	
	//并发验证方案-view
	public List<VImsIssueVerificationEntity> getOneVerificationScheme(String keyId, String taskId) {
		
		String hql = "FROM VImsIssueVerificationEntity WHERE issueId = ? and taskId = ? order by seq asc";
		List<VImsIssueVerificationEntity> verificationScheme =
				this.vImsIssueVerificationDAO.find(hql, keyId, taskId);
		return verificationScheme;
	}
	//零件-view
	public List<Map<String, Object>> getPart(String keyId, boolean historyFlag) {
		String sql = "";
		if (historyFlag) {
			sql = "SELECT * FROM V_IMS_HST_ISSUE_PART WHERE HST_ISSUE_ID = ? ORDER BY SEQ";
		} else {
			sql = "SELECT * FROM V_IMS_ISSUE_PART WHERE ISSUE_ID = ? ORDER BY SEQ";
		}
		return this.jdbcTemplate.queryForList(sql, keyId);
	}
	//零件-list
	public ImsIssuePartEntity getPar(String UId) {
		
		ImsIssuePartEntity issuePar =
				this.imsIssuePartDAO.findUniqueBy("id", UId);
        return issuePar;
	}
	//验证方案-list
	public List<ImsIssueVerificationEntity> getVScheme(String keyId) {
		
		String hql = "FROM ImsIssueVerificationEntity WHERE issueId = ?";
		List<ImsIssueVerificationEntity> verificationScheme =
				this.imsIssueVerificationDAO.find(hql, keyId);
		return verificationScheme;
	}
	//get 主表一条数据
	public VImsIssueEntity getIssueById(String keyId) {

		VImsIssueEntity oneDate =
				this.vImsIssueDAO.findUniqueBy("id", keyId);

		return oneDate;
	}

	public void save (GTIssueEntity entity){
		gtIssueDAO.save(entity);
	}
	
	public void save (ImsIssuePartEntity entity){
		imsIssuePartDAO.save(entity);
	}

	public void save (ImsIssueVerificationEntity entity){
		imsIssueVerificationDAO.save(entity);
	}
	
	public void save (ImsIssueApprovelogEntity entity){
		imsIssueApprovelogDAO.save(entity);
	}
	
	public void delete (ImsIssuePartEntity entity){
		imsIssuePartDAO.delete(entity);
	}
	public void delete (ImsIssueVerificationEntity entity){
		imsIssueVerificationDAO.delete(entity);
	}
	
//	public List<MyTaskInfo> getMyTaskList(String userId, String query, String searchChoice, int start,int limit) throws UnsupportedEncodingException{
//		log.info("待办事项 start....................................................");
//		//workflowService.logUserTasks(log, userId);
//		log.info("userId = " + userId);
//		log.info("query = " + query);
//		log.info("searchChoice = " + searchChoice);
//		log.info("start = " + start);
//		log.info("limit = " + limit);
//		
//		Page<VImsIssueEntity> vImsIssueEntityList = null ;
////		Page<VImsIssueEntity> page = new Page<VImsIssueEntity>(limit);
////		page.setPageNo(start);
////
//        List<Task> tasklist = workflowService.getTasks(userId);
////        
////        String hql = "from VImsIssueEntity WHERE ID IN (";
////        
////        for (Task task : tasklist) {
////			String processinsId = task.getProcessInstanceId();
////			ProcessInstance pi = workflowService.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
////			String businesskey = pi.getBusinessKey();
////			//log.info("Issue Id = " + businesskey);
////			hql += "'" + businesskey + "', ";
////        }
////        if (!hql.equals("from VImsIssueEntity WHERE ID IN (")){
////	        hql = hql.substring(0, hql.length() - 2) + ")";
////	        //log.info("hql = " + hql);
////			if (PDMSCommon.isNotNull(query)) {
////				String strQuery = new String(query.getBytes("ISO-8859-1"), "UTF-8");
////			
////				hql += " AND " + searchChoice + " like ?";
////				log.info("query is not null: hql = " + hql);
////				vImsIssueEntityList = this.vImsIssueDAO.findPage(page, hql, "%" + strQuery + "%");
////		
////			} else {
////				vImsIssueEntityList = this.vImsIssueDAO.findPage(page, hql);
////			}
////	        //log.info("hql = " + hql);
////
////        }
//        
//        //All tasks
//        //workflowService.logAllTasks(log);
//
//        log.info("待办事项 end......................................................");
//        return this.queryMyTaskPage(tasklist, vImsIssueEntityList, start, limit);
//	}
//	
//	
//	private List<MyTaskInfo> queryMyTaskPage(List<Task> tasklist, Page<VImsIssueEntity> vImsIssueEntityList, int start, int limit){
//		if ((tasklist != null) && (tasklist.size() > 0)){
//			log.info("tasklist.size() = " + tasklist.size());
//		}
////		if (vImsIssueEntityList != null){
////			log.info("vImsIssueEntityList.getTotalCount() = " + vImsIssueEntityList.getTotalCount());
////			log.info("vImsIssueEntityList.getTotalPages() = " + vImsIssueEntityList.getTotalPages());
////			log.info("vImsIssueEntityList.getPageNo() = " + vImsIssueEntityList.getPageNo());
////			log.info("vImsIssueEntityList.getPageSize() = " + vImsIssueEntityList.getPageSize());
////		}
//		log.info("start = " + start);
//		log.info("limit = " + limit);
////		Page<MyTaskInfo> myTaskInfoList = new Page<MyTaskInfo>() ;
//		// 登录用户ID取得
//		String userId = SessionData.getLoginUserId();
//		
////		List<VImsIssueEntity> issueList = vImsIssueEntityList.getResult();
//		List<MyTaskInfo> myTaskList = new ArrayList<MyTaskInfo>();
//		MyTaskInfo myTaskInfo = null;
//		log.info("myTaskList.size() = " + myTaskList.size());
////        myTaskInfoList.setResult(myTaskList);
////        log.info("myTaskInfoList.getPageSize() = " + myTaskInfoList.getPageSize());
////		if (myTaskInfoList != null){
////			log.info("vImsIssueEntityList.getTotalCount() = " + vImsIssueEntityList.getTotalCount());
////			log.info("vImsIssueEntityList.getTotalPages() = " + vImsIssueEntityList.getTotalPages());
////			log.info("vImsIssueEntityList.getPageNo() = " + vImsIssueEntityList.getPageNo());
////			log.info("vImsIssueEntityList.getPageSize() = " + vImsIssueEntityList.getPageSize());
//			
////			myTaskInfoList.setPageNo(1);
////			myTaskInfoList.setPageSize(myTaskList.size());
////			myTaskInfoList.setTotalCount(myTaskList.size());
//			
////			log.info("myTaskInfoList.getTotalCount() = " + myTaskInfoList.getTotalCount());
////			log.info("myTaskInfoList.getTotalPages() = " + myTaskInfoList.getTotalPages());
////			log.info("myTaskInfoList.getPageNo() = " + myTaskInfoList.getPageNo());
////			log.info("myTaskInfoList.getPageSize() = " + myTaskInfoList.getPageSize());
////		}
//		return myTaskList;
//	}
//	
//	public Page<MyTaskInfo> getMyTaskList(String userId, String query, String searchChoice, int start,int limit) throws UnsupportedEncodingException{
////		log.info("待办事项 start....................................................");
////		//workflowService.logUserTasks(log, userId);
////		log.info("userId = " + userId);
////		log.info("query = " + query);
////		log.info("searchChoice = " + searchChoice);
////		log.info("start = " + start);
////		log.info("limit = " + limit);
////		
////		Page<VImsIssueTaskEntity> vImsIssueTaskEntityList = null ;
////		Page<VImsIssueTaskEntity> page = new Page<VImsIssueTaskEntity>(limit);
////		page.setPageNo(start);
////
////        List<Task> tasklist = workflowService.getTasks(userId);
////        
////        String hql = "from VImsIssueTaskEntity WHERE ID IN (";
////        //查询在工作流里存在的待办任务
////        for (Task task : tasklist) {
////			String processinsId = task.getProcessInstanceId();
////			ProcessInstance pi = workflowService.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
////			String businesskey = pi.getBusinessKey();
////			List<VImsIssueVerificationEntity> list_Verification = this.getVerificationScheme(businesskey);
////			if(list_Verification.size() > 0){
////				for (VImsIssueVerificationEntity entity : list_Verification) {
////					hql += "'" + businesskey + "-";
////					if (PDMSCommon.isNotNull(entity.getTaskId()) && PDMSCommon.isNotNull(entity.getSteps())) {
////						hql += entity.getTaskId();
////					}
////					hql += "', ";
////				}
////			} else {
////				hql += "'" + businesskey + "-', ";
////			}
////			//log.info("Issue Id = " + businesskey);
////        }
////        if (!hql.equals("from VImsIssueTaskEntity WHERE ID IN (")){
////	        hql = hql.substring(0, hql.length() - 2) + ")";
////	        //log.info("hql = " + hql);
////			if (PDMSCommon.isNotNull(query)) {
////				String strQuery = new String(query.getBytes("ISO-8859-1"), "UTF-8");
////			
////				hql += " AND " + searchChoice + " like ?";
////				hql += " order by rownum";
////				log.info("query is not null: hql = " + hql);
////				vImsIssueTaskEntityList = this.vImsIssueTaskDAO.findPage(page, hql, "%" + strQuery + "%");
////		
////			} else {
////				hql += " order by rownum";
////				vImsIssueTaskEntityList = this.vImsIssueTaskDAO.findPage(page, hql);
////			}
////	        //log.info("hql = " + hql);
////
////        }
////        
////        //All tasks
////        //workflowService.logAllTasks(log);
////
////        log.info("待办事项 end......................................................");
////        return this.queryMyTaskPage(tasklist, vImsIssueTaskEntityList, start, limit);
//		return null;
//	}
	
	
//	private Page<MyTaskInfo> queryMyTaskPage(List<Task> tasklist, Page<VImsIssueTaskEntity> vImsIssueTaskEntityList, int start, int limit){
//		if ((tasklist != null) && (tasklist.size() > 0)){
//			log.info("tasklist.size() = " + tasklist.size());
//		}
//		if (vImsIssueTaskEntityList != null){
//			log.info("vImsIssueTaskEntityList.getTotalCount() = " + vImsIssueTaskEntityList.getTotalCount());
//			log.info("vImsIssueTaskEntityList.getTotalPages() = " + vImsIssueTaskEntityList.getTotalPages());
//			log.info("vImsIssueTaskEntityList.getPageNo() = " + vImsIssueTaskEntityList.getPageNo());
//			log.info("vImsIssueTaskEntityList.getPageSize() = " + vImsIssueTaskEntityList.getPageSize());
//		}
//		log.info("start = " + start);
//		log.info("limit = " + limit);
//		Page<MyTaskInfo> myTaskInfoList = new Page<MyTaskInfo>() ;
//		// 登录用户ID取得
//		String userId = SessionData.getLoginUserId();
//		if (vImsIssueTaskEntityList != null) {
//			
//			List<VImsIssueTaskEntity> issueList = vImsIssueTaskEntityList.getResult();
//			List<MyTaskInfo> myTaskList = new ArrayList<MyTaskInfo>();
//	    	List<String> workList = new ArrayList<String>();
//			MyTaskInfo myTaskInfo = null;
//	
//			for (Task task : tasklist) {
//				log.info("完美分隔..............................................................");
//				String processinsId = task.getProcessInstanceId();
//				ProcessInstance pi = workflowService.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
//				String businesskey = pi.getBusinessKey();
//				log.info("businesskey = "+ businesskey);
//				myTaskInfo = new MyTaskInfo();
//		
//				//log.info("Process Definition Id: "+ task.getProcessDefinitionId());
//				myTaskInfo.setProcessInstanceId(processinsId);
//				log.info("Process Instance Id = " + processinsId);
//				myTaskInfo.setTaskId(task.getId());
//				log.info("Task Id = " + task.getId());
//				myTaskInfo.setFormKey(task.getFormKey());
//				myTaskInfo.setCreateDate(task.getCreateTime());
//		    	myTaskInfo.setTaskName(task.getName());
//		    	myTaskInfo.setTaskCategory(task.getCategory());
//		        
//	//	    	VImsIssueEntity issue = this.getIssueById(businesskey);
//				for (VImsIssueTaskEntity entityIms : issueList) {
//					String keyId = entityIms.getIssueId();
//					if (keyId.equals(businesskey)){
//						if (workList.indexOf(entityIms.getTaskId()) >= 0) {
//				        	continue;
//						} else {
//							log.info("Issue Id =    " + entityIms.getId());
//				        	myTaskInfo.setIssueId(keyId);
//							myTaskInfo.setIssueTitle(entityIms.getTitle());
//							log.info("Issue.Title = " + entityIms.getTitle());
//							myTaskInfo.setIssueStatus(entityIms.getIssueStatus());
//							myTaskInfo.setIssueLevel(entityIms.getIssueLevel());
//							myTaskInfo.setProject(entityIms.getProjectName());
//							myTaskInfo.setRisk(entityIms.getRiskLevelCode());
//							myTaskInfo.setMark(entityIms.getIsMark());
//							myTaskInfo.setSteps(entityIms.getSteps());
//							myTaskInfo.setCode(entityIms.getCode());
//							// TODO:当前任务负责人
//							//是否关注取得
//							List<ImsIssueMarkEntity> list = this.imsIssueMarkService.getImsIssueMark(entityIms.getId(),userId);
//							for(ImsIssueMarkEntity entity : list){
//								myTaskInfo.setMark(entity.getIsConcern());
//							}
//							myTaskList.add(myTaskInfo);
//							if (PDMSCommon.isNotNull(entityIms.getTaskId())){
//								workList.add(entityIms.getTaskId());
//							}
//							break;
//						}
//					}
//				}
//		    }
//	//        for (VImsIssueEntity issue : issueList) {
//	//        	myTaskInfo = new MyTaskInfo();
//	//        	//log.info("Issue Id = " + issue.getId());
//	//        	myTaskInfo.setIssueId(issue.getId());
//	//			myTaskInfo.setIssueTitle(issue.getTitle());
//	//			myTaskInfo.setIssueStatus(issue.getIssueStatus());
//	//			myTaskInfo.setIssueLevel(issue.getIssueLevel());
//	//			myTaskInfo.setProject(issue.getProjectName());
//	//			myTaskInfo.setRisk(issue.getRiskLevelCode());
//	//			//是否关注取得
//	//			List<ImsIssueMarkEntity> list = this.imsIssueMarkService.getImsIssueMark(issue.getId(),userId);
//	//			for(ImsIssueMarkEntity entity : list){
//	//				myTaskInfo.setMark(entity.getIsConcern());
//	//			}
//	//			
//	//			for (Task task : tasklist) {
//	//				String processinsId = task.getProcessInstanceId();
//	//				ProcessInstance pi = workflowService.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
//	//				String businesskey = pi.getBusinessKey();
//	//				if (issue.getId().equals(businesskey)){
//	//					//log.info("Process Definition Id: "+ task.getProcessDefinitionId());
//	//					myTaskInfo.setProcessInstanceId(processinsId);
//	//					//log.info("Process Instance Id = " + processinsId);
//	//					myTaskInfo.setTaskId(task.getId());
//	//					//log.info("Task Id = " + task.getId());
//	//					myTaskInfo.setFormKey(task.getFormKey());
//	//					myTaskInfo.setCreateDate(task.getCreateTime());
//	//		        	myTaskInfo.setTaskName(task.getName());
//	//		        	myTaskInfo.setTaskCategory(task.getCategory());
//	//				}
//	//				
//	//			}
//	//			
//	//			myTaskList.add(myTaskInfo);
//	//
//	//        }
//	        myTaskInfoList.setResult(myTaskList);
//			if (myTaskInfoList != null){
//	//			log.info("vImsIssueEntityList.getTotalCount() = " + vImsIssueEntityList.getTotalCount());
//	//			log.info("vImsIssueEntityList.getTotalPages() = " + vImsIssueEntityList.getTotalPages());
//	//			log.info("vImsIssueEntityList.getPageNo() = " + vImsIssueEntityList.getPageNo());
//	//			log.info("vImsIssueEntityList.getPageSize() = " + vImsIssueEntityList.getPageSize());
//				
//				myTaskInfoList.setPageNo(vImsIssueTaskEntityList.getPageNo());
//				myTaskInfoList.setPageSize(vImsIssueTaskEntityList.getPageSize());
//				myTaskInfoList.setTotalCount(vImsIssueTaskEntityList.getTotalCount());
//				
//	//			log.info("myTaskInfoList.getTotalCount() = " + myTaskInfoList.getTotalCount());
//	//			log.info("myTaskInfoList.getTotalPages() = " + myTaskInfoList.getTotalPages());
//	//			log.info("myTaskInfoList.getPageNo() = " + myTaskInfoList.getPageNo());
//	//			log.info("myTaskInfoList.getPageSize() = " + myTaskInfoList.getPageSize());
//			}
//		}
//		return myTaskInfoList;
//	}
//
//	public void modifyIssue(String userId, String processInstanceId, String taskId, String projectId) {
//		log.info("问题提交.提交 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("子项目 Id = " + projectId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//		String qmUserId = imsOrgUserService.getQualityManagerUserId(projectId);
//		log.info("质量经理 User Id = " + qmUserId);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//		//质量经理
//		variableMap.put("QMId", qmUserId);
//		variableMap.put("submitionFlag", 1);
//		
//    	//workflowService.setTaskVar(task.getId(), "publishFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任部门专业经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题提交.提交 end......................................................");
//    }
//	
//	public void deleteIssue(String userId, String processInstanceId, String taskId) {
//		log.info("问题提交.删除 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//		variableMap.put("submitionFlag", 2);
//		
//    	//workflowService.setTaskVar(task.getId(), "publishFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任部门专业经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题提交.删除 end......................................................");
//    }
//	
//	public void saveDraftIssue(String userId, String processInstanceId, String taskId) {
//		log.info("问题提交.保存草稿 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//		variableMap.put("submitionFlag", 3);
//		
//    	//workflowService.setTaskVar(task.getId(), "publishFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任部门专业经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题提交.保存草稿 end......................................................");
//    }
//	
//	public void assignDepartment(String userId, String processInstanceId, String taskId, String suggestion, String deptPmId) {
//		log.info("问题发布.分配 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("deptPmId = " + deptPmId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//    	//String rdPMId = imsOrgUserService.getDepartmentPMUserId(departmentId);
//    	log.info("责任部门专业经理 User Id = " + deptPmId);
//    	
//    	variableMap.put("RDPMId", deptPmId);
//    	variableMap.put("publishFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "publishFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任部门专业经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题发布.分配 end......................................................");
//    }
//
//
//	public void returnIssue(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("问题发布.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("publishFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "publishFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //Process Instance starter tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题发布.回退 end......................................................");
//	}
//	
//	public String listIssueReview(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("挂牌.审核.通过 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//    	variableMap.put("EngineerId", "");
//    	variableMap.put("listReviewFlag", 0);
//    	
//        workflowService.complete(task.getId(), variableMap);
//        
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("confirmIssue").list(); 
//        String taskIdN = tasks.get(0).getId(); 
//        log.info("taskId Next = " + taskIdN);
//
//        //Process Instance starter tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("挂牌.审核.通过 end......................................................");
//	    return taskIdN;
//	}
//	
//	public void listIssueReviewReturn(String userId, String processInstanceId, String taskId, String suggestion, String formKey) {
//		log.info("挂牌.审核.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//    	//variableMap.put("EngineerId", "");
//    	if (formKey.equals("010")){
//    	    variableMap.put("listReviewFlag", 1);
//    	} else if (formKey.equals("020")){
//    	    variableMap.put("listReviewFlag", 2);
//    	} else if (formKey.equals("030")){
//    	    variableMap.put("listReviewFlag", 3);
//    	} else if (formKey.equals("040")){
//    	    variableMap.put("listReviewFlag", 4);
//    	}
//    	
//        workflowService.complete(task.getId(), variableMap);            
//
//        //Process Instance starter tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("挂牌.审核.回退 end......................................................");
//	}
//	
//	public void delistIssue(String userId, String processInstanceId, String taskId) {
//		log.info("摘牌 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("userId = " + userId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	//workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	
//    	task.setAssignee(userId);
////    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	
////    	variableMap.put("EngineerId", "");
////    	variableMap.put("listReviewFlag", 1);
////    	
////        workflowService.complete(task.getId(), variableMap);            
//
//        //Process Instance starter tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("摘牌 end......................................................");
//	}
//	
//	public void assignEngineer(String userId, String processInstanceId, String taskId, String suggestion, String engineerId) {
//		log.info("责任确认.提交 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("engineerId = " + engineerId);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("EngineerId", engineerId);
//    	variableMap.put("confirmResponsibilityFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "confirmResponsibilityFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("责任确认.提交 end......................................................");
//    }
//
//
//	public void returnIssue02(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("责任确认.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("confirmResponsibilityFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "confirmResponsibilityFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("责任确认.回退 end......................................................");
//	}
//	
//	public void confirmIssue(String userId, String processInstanceId, String taskId, String suggestion, String issueNature) {
//		log.info("问题确认.提交 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("issueNature = " + issueNature);
//		
//		if (issueNature.equals("ISSUE_NATURE_1")){
//			Task task = workflowService.getTask(taskId);
//	
//	    	workflowService.identityService.setAuthenticatedUserId(userId);
//	    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//	    	Map<String, Object> variableMap = new HashMap<String, Object>();
//	    	variableMap.put("EngineerId", userId);
//	    	variableMap.put("issueConfirmFlag", 1);
//	    	//workflowService.setTaskVar(task.getId(), "issueConfirmFlag", 1);
//	        workflowService.complete(task.getId(), variableMap);
//	        log.info("Task Completed.........................");
//	
//	        //责任工程师 tasks
//	        //workflowService.logUserTasks(log, userId);
//	        log.info("问题确认.提交 end......................................................");
//		}
//    }
//
//
//	public void returnIssue03(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("问题确认.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("issueConfirmFlag", 4);
//    	//workflowService.taskService.setVariable(taskId, "issueConfirmFlag", 4);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题确认.回退 end......................................................");
//	}
//	
//	public void decideStrategy(String userId, String processInstanceId, String taskId, String suggestion, String reviewer, String approver) {
//		log.info("对策决定.提交 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("reviewer = " + reviewer);
//		log.info("approver = " + approver);
////		log.info("rechecker = " + rechecker);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("EgnLdReviewerId", reviewer);
//    	variableMap.put("EgnLdApproverId", approver);
////    	variableMap.put("EgnLdRecheckerId", rechecker);
//    	variableMap.put("decideStrategyFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.提交 end......................................................");
//    }
//	
//	public void dSReview(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("对策决定.审核.通过 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("dSReviewFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.审核.通过 end......................................................");
//    }
//	
//	public void dSReviewReturn(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("对策决定.审核.回退 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("dSReviewFlag", 2);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.审核.回退 end......................................................");
//    }
//	
//	public void dSApprove(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("对策决定.批准.通过 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("dSApproveFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.批准.通过 end......................................................");
//    }
//	
//	public void dSApproveReturn(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("对策决定.批准.回退 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("dSApproveFlag", 2);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.批准.回退 end......................................................");
//    }
//	
//	
//	public void dSRecheck(String userId, String processInstanceId, String taskId, String suggestion, String isStrategy) {
//		log.info("对策决定.确认.通过 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("isStrategy = " + isStrategy);
//		
//		Task task = workflowService.getTask(taskId);
//
//		if (isStrategy.equals("Y")){
//	    	workflowService.identityService.setAuthenticatedUserId(userId);
//	    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//	    	Map<String, Object> variableMap = new HashMap<String, Object>();
//	    	variableMap.put("dSRecheckFlag", 1);
//	    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//	        workflowService.complete(task.getId(), variableMap);
//	        log.info("Task Completed.........................");
//	
//	        //责任工程师 tasks
//	        //workflowService.logUserTasks(log, userId);
//	        log.info("对策决定.确认.通过 end......................................................");
//		}
//    }
//	
//	public void dSRecheckReturn(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("对策决定.确认.回退 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("dSRecheckFlag", 2);
//    	//workflowService.setTaskVar(task.getId(), "decideStrategyFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("对策决定.确认.回退 end......................................................");
//    }	
//
//	public void implementMeasure(String userId, String processInstanceId, String taskId, String projectId) {
//		log.info("措施实施.提交 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("子项目 Id = " + projectId);
//				
//		Task task = workflowService.getTask(taskId);
//		
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
//		String qmUserId = imsOrgUserService.getQualityManagerUserId(projectId);
//		log.info("质量经理 User Id = " + qmUserId);
//		
//		//质量经理
//		variableMap.put("QMId", qmUserId);
//    	variableMap.put("implementMeasureFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "implementMeasureFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        log.info("措施实施.提交 end......................................................");
//    }
//	
//	public ArrayList<String> verifyAssignment1(String userId, String processInstanceId, String taskId, ArrayList<String> departmentPMId) {
//		log.info("验证分配（1）.分配 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//						
//		Task task = workflowService.getTask(taskId);
//		log.info("Before completed.........................................................");
//    	log.info("Task ID = " + task.getId());
//    	log.info("Task Name = " + task.getName());
//    	log.info("Task Assignee = " + task.getAssignee());
//    	log.info("Task Execution Id = " + task.getExecutionId());
//    	log.info("Task Form Key = " + task.getFormKey());
//    	log.info("Task Owner = " + task.getOwner());
//    	log.info("Task Parent Task Id = " + task.getParentTaskId());
//    	log.info("Task Process Instance Id = " + task.getProcessInstanceId());
//    	log.info("Task Task Definition Key = " + task.getTaskDefinitionKey());
//    	log.info("Task Create Time = " + task.getCreateTime());
//
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();    	
//    	
//    	//String vdPMId = "";
//    	
//    	ArrayList<String> assigneeList = new ArrayList<String>();
//
//    	for (int i = 0; i < departmentPMId.size(); i++) {
//    		log.info("departmentPMId.get( " + i +" ) = " + departmentPMId.get(i));
//    		//vdPMId = imsOrgUserService.getDepartmentPMUserId(departmentPMId.get(i));
//    		assigneeList.add(departmentPMId.get(i));
//    		log.info("验证部门项目经理 User Id = " + assigneeList.get(i));
//    	}
//
//		variableMap.put("VDPMIdList", assigneeList);
//    	
//    	//variableMap.put("VDPMId", vdPMId);
//    	variableMap.put("verifyAssignment1Flag", 1);
//    	//workflowService.setTaskVar(task.getId(), "verifyAssignment1Flag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//        
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).orderByTaskId().asc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//        
//        log.info("验证分配（1）.分配 end......................................................");
//        return taskIdList;
//    }
//	
//	public void returnIssue10(String userId, String processInstanceId, String taskId) {
//		log.info("验证分配（1）.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("EngineerId", userId);
//    	variableMap.put("verifyAssignment1Flag", 2);
//    	//workflowService.taskService.setVariable(taskId, "verifyAssignment1Flag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //责任工程师 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("验证分配（1）.回退 end......................................................");
//	}
//	
//	public ArrayList<String> verifyAssignment2(String userId, String processInstanceId, String taskId, String suggestion, String engineerId) {
//		log.info("验证分配（2）.分配 start....................................................");
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("验证工程师 User Id = " + engineerId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	variableMap.put("VEngineerId", engineerId);
//    	variableMap.put("verifyAssignment2Flag", 1);
//    	//workflowService.setTaskVar(task.getId(), "verifyAssignment2Flag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("verifyEffect").orderByTaskId().desc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//        
//        //验证部门项目经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("验证分配（2）.分配 end......................................................");
//        return taskIdList;
//    }
//	
//	public void returnIssue11(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("验证分配（2）.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//				
//		Task task = workflowService.getTask(taskId);
//		log.info("Task's Name: "+ task.getName());
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("QMId", userId);
//    	variableMap.put("verifyAssignment2Flag", 2);
//    	//workflowService.taskService.setVariable(taskId, "verifyAssignment2Flag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("验证分配（2）.回退 end......................................................");
//	}
//	
//	public ArrayList<String> verifyEffect(String userId, String processInstanceId, String taskId, String projectId, String reviewer, String suggestion) {
//		log.info("效果验证.提交 start....................................................");
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("子项目 Id = " + projectId);
//		log.info("reviewer = " + reviewer);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////		String qmUserId = imsOrgUserService.getQualityManagerUserId(projectId);
////		log.info("质量经理 User Id = " + qmUserId);
//		
//		//验证工程师科长或项目经理审核人
//		variableMap.put("VEngineerReviewerId", reviewer);
//    	variableMap.put("verifyEffectFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "verifyEffectFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("verifyEffectReview").orderByTaskId().desc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//        
//        //验证工程师
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果验证.提交 end......................................................");
//        return taskIdList;
//    }
//	
//	public ArrayList<String> returnIssue06(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("效果验证.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("VDPMId", userId);
//    	variableMap.put("verifyEffectFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "verifyEffectFlag", 2);  
//        workflowService.complete(task.getId(), variableMap); 
//        
//        log.info("Task Completed.........................");
//
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("verifyAssignment2").orderByTaskId().desc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//
//        //验证部门项目经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果验证.回退 end......................................................");
//        return taskIdList;
//	}
//	
//	public ArrayList<String> verifyEffectReview(String userId, String processInstanceId, String taskId, String projectId, String suggestion) {
//		log.info("效果验证.审核.通过 start....................................................");
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("子项目 Id = " + projectId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//		
//    	variableMap.put("verifyEffectReviewFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "verifyEffectFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("confirmEffect").orderByTaskId().desc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//        
//        //验证工程师
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果验证.审核.通过 end......................................................");
//        return taskIdList;
//    }
//	
//	public ArrayList<String> returnIssue061(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("效果验证.审核.回退 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("VDPMId", userId);
//    	variableMap.put("verifyEffectReviewFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "verifyEffectFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//        
//        log.info("Task Completed.........................");
//
//        log.info("After completed.........................................................");
//        ArrayList<String> taskIdList = new ArrayList<String>();
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("verifyEffect").orderByTaskId().desc().list();
//        for (Task taskV : tasks) {
//        	taskIdList.add(taskV.getId());
//        	log.info("Task ID = " + taskV.getId());
//        	log.info("Task Name = " + taskV.getName());
//        	log.info("Task Assignee = " + taskV.getAssignee());
//        	log.info("Task Execution Id = " + taskV.getExecutionId());
//        	log.info("Task Form Key = " + taskV.getFormKey());
//        	log.info("Task Owner = " + taskV.getOwner());
//        	log.info("Task Parent Task Id = " + taskV.getParentTaskId());
//        	log.info("Task Process Instance Id = " + taskV.getProcessInstanceId());
//        	log.info("Task Definition Key = " + taskV.getTaskDefinitionKey());
//        	log.info("Task Create Time = " + taskV.getCreateTime());
//        	log.info("                                           ");
//        }
//        //验证部门项目经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果验证.审核.回退 end......................................................");
//        return taskIdList;
//	}
//	
//	
//	public void confirmEffect(String userId, String processInstanceId, String taskId, String suggestion, String reviewer, String approver) {
//		log.info("效果确认.申请关闭 start....................................................");
//		
//		log.info("userId = " + userId);
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("reviewer = " + reviewer);
//		log.info("approver = " + approver);
//		//log.info("confirmer = " + confirmer);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	
////    	String engineerLId = imsOrgUserService.getEngineerLeadUserId(departmentId);
////    	log.info("责任工程师上级 User Id = " + engineerLId);
//    	
//    	variableMap.put("EngineerLId", reviewer);
//    	variableMap.put("EngineerLApproverId", approver);
//    	//variableMap.put("EngineerLConfirmerId", confirmer);
//    	variableMap.put("confirmEffectFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "confirmEffectFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //验证工程师
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果确认.申请关闭 end......................................................");
//    }
//	
//	public void confirmEffectNG(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("效果确认.验证NG start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("VEngineerId", userId);
//    	variableMap.put("confirmEffectFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "confirmEffectFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //验证部门项目经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("效果确认.验证NG end......................................................");
//	}
//	
//	public void closeAudit(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("关闭审核.同意关闭 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//    	variableMap.put("closeAuditFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭审核.同意关闭 end......................................................");
//    }
//	
//	public void closeAuditNo(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("关闭审核.不同意关闭 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("QMId", userId);
//    	variableMap.put("closeAuditFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "closeAuditFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭审核.不同意关闭 end......................................................");
//	}
//
//	public void closeAuditApprove(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("关闭批准.同意关闭 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//    	variableMap.put("closeAuditApproveFlag", 1);
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭批准.同意关闭 end......................................................");
//    }
//	
//	public void closeAuditApproveNo(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("关闭批准.不同意关闭 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("QMId", userId);
//    	variableMap.put("closeAuditApproveFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "closeAuditFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭批准.不同意关闭 end......................................................");
//	}
//	
//	public void closeAuditConfirm(String userId, String processInstanceId, String taskId, String suggestion, String isDP) {
//		log.info("关闭审核 (质量经理).同意关闭 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		log.info("是否列入再发防止 = " + isDP);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//    	if (isDP.equals("Y")){
//    	    variableMap.put("closeAuditConfirmFlag", 1);
//    	} else if (isDP.equals("N")){
//    		variableMap.put("closeAuditConfirmFlag", 3);
//    	}
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭审核 (质量经理).同意关闭 end......................................................");
//    }
//	
//	public void closeAuditConfirmNo(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("关闭审核 (质量经理).不同意关闭 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	//variableMap.put("QMId", userId);
//    	variableMap.put("closeAuditConfirmFlag", 2);
//    	//workflowService.taskService.setVariable(taskId, "closeAuditFlag", 2);  
//        workflowService.complete(task.getId(), variableMap);            
//
//        //质量经理 tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("关闭审核 (质量经理).不同意关闭 end......................................................");
//	}
//	
//	public void defectPrevention(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("再发防止.提交 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//
//    	variableMap.put("dPFlag", 1);
//
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("再发防止.提交 end......................................................");
//    }
//	
//	public void defectPreventionNo(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("再发防止.回退 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//
//    	variableMap.put("dPFlag", 2);
//
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("再发防止.回退 end......................................................");
//    }
//	
//	public void defectPreventionConfirm(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("再发防止确认.通过 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//
//    	variableMap.put("dPConfirmFlag", 1);
//
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("再发防止确认.通过 end......................................................");
//    }
//	
//	public void defectPreventionConfirmNo(String userId, String processInstanceId, String taskId, String suggestion) {
//		log.info("再发防止确认.回退 start....................................................");
//		
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//		log.info("suggestion = " + suggestion);
//		
//		Task task = workflowService.getTask(taskId);
//
//    	workflowService.identityService.setAuthenticatedUserId(userId);
//    	workflowService.taskService.addComment(task.getId(), processInstanceId, suggestion);
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
////    	variableMap.put("EngineerLId", userId);
//
//    	variableMap.put("dPConfirmFlag", 2);
//
//    	//workflowService.setTaskVar(task.getId(), "closeAuditFlag", 1);
//        workflowService.complete(task.getId(), variableMap);
//        log.info("Task Completed.........................");
//
//        //责任工程师上级
//        //workflowService.logUserTasks(log, userId);
//        log.info("再发防止确认.回退 end......................................................");
//    }
}