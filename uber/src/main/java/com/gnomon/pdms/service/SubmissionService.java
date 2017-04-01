package com.gnomon.pdms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.action.ims.SubmissionAction;
import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.dao.TestTypeDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.TestTypeEntity;

@Service
@Transactional
public class SubmissionService {
	private static final Log log = LogFactory.getLog(SubmissionAction.class);
	
//	private GTWorkFlowService workflowService = new GTWorkFlowService();
	
	@Autowired
	private ImsOrgUserService imsOrgUserService;

	@Autowired
	private GTIssueDAO gtIssueDAO;
	
	@Autowired
	private TestTypeDAO testTypeDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public GTIssueEntity getMyDraft(String keyId) {
		GTIssueEntity submission =
				this.gtIssueDAO.findUniqueBy("id", keyId);
        return submission;
    }
	
	public void save (GTIssueEntity entity){
		gtIssueDAO.save(entity);
	}
	
	public String saveTypeId (String typeId){
		String testTypeId = typeId;

//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		String sql = "select * from IMS_TEST_TYPE where ID = ?";
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql, typeId);
		if (data.size() == 0) {

			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();
			TestTypeEntity entity = new TestTypeEntity();
			String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
			entity.setId(uuid);
			entity.setName(typeId);

			entity.setCreateBy(userId);
			entity.setCreateDate(new Date());
			testTypeDAO.save(entity);

			testTypeId = uuid;
		}
		return testTypeId;
	}
	public GTIssueEntity deleteIssue(String keyId){
		GTIssueEntity myTaskImsIssueVerification =
				this.gtIssueDAO.findUniqueBy("id", keyId);
		return myTaskImsIssueVerification;
	}
	public void delete (GTIssueEntity entity){
		gtIssueDAO.delete(entity);
	}
//	public String submitIssue(String issueId, String userId, String projectId) {
//		log.info("问题提交.提交 start....................................................");
//		log.info("issueId = " + issueId);
//		log.info("userId = " + userId);
//		log.info("子项目 Id = " + projectId);
//		Map<String, Object> variableMap = new HashMap<String, Object>();
//		variableMap.put("name", "Activiti");
//		
//		//问题提出人
//		variableMap.put("SubmitterId", userId);
//		variableMap.put("submitionFlag", 1);
//		
//		String qmUserId = imsOrgUserService.getQualityManagerUserId(projectId);
//		log.info("质量经理 User Id = " + qmUserId);
//		
//		//质量经理
//		variableMap.put("QMId", qmUserId);
//		variableMap.put("publishFlag", 1);
//		
//		//责任部门专业经理
//		variableMap.put("RDPMId", userId);
//		variableMap.put("confirmResponsibilityFlag", 1);
//		
//		//责任工程师
//		variableMap.put("EngineerId", "");
//		variableMap.put("issueConfirmFlag", 1);
//		
//		//责任工程师
////		variableMap.put("EngineerId", userId);
//		variableMap.put("decideStrategyFlag", 1);		
//
//		//责任工程师
////		variableMap.put("EngineerId", userId);
//		variableMap.put("implementMeasureFlag", 1);
//		
//		//质量经理
////		variableMap.put("QMId", userId);
//		variableMap.put("verifyAssignment1Flag", 1);
//		
//		//验证部门项目经理
//		//variableMap.put("VDPMId", userId);
//		List<String> assigneeList = Arrays.asList(userId, userId, userId); 
//		variableMap.put("VDPMIdList", assigneeList);
//		variableMap.put("verifyAssignment2Flag", 1);		
//		
//		//验证工程师
//		variableMap.put("VEngineerId", userId);
//		variableMap.put("verifyEffectFlag", 1);
//		
//		//质量经理
////		variableMap.put("QMId", userId);
//		variableMap.put("confirmEffectFlag", 1);
//		
//		//责任工程师上级领导审核人
//		variableMap.put("EgnLdReviewerId", userId);
//		variableMap.put("dSReviewFlag", 1);
//		
//		//责任工程师上级领导批准人
//		variableMap.put("EgnLdApproverId", userId);
//		variableMap.put("dSApproveFlag", 1);
//		
//		//质量经理或上级领导复审人
//		variableMap.put("EgnLdRecheckerId", userId);
//		variableMap.put("dSRecheckFlag", 1);
//		
//		//验证工程师科长或项目经理审核人
//		variableMap.put("VEngineerReviewerId", userId);
//		variableMap.put("verifyEffectReviewFlag", 1);		
//
//		
//		//责任工程师上级
//		variableMap.put("EngineerLId", userId);
//		variableMap.put("closeAuditFlag", 1);
//		
//		//责任工程师上级领导
//		variableMap.put("EngineerLApproverId", userId);
//		variableMap.put("closeAuditApproveFlag", 1);
//
//		//挂牌审核人
//		variableMap.put("LRId", userId);
//		variableMap.put("listReviewFlag", 1);
//		
//	
//		ProcessInstance processInstance = workflowService.startProcess(userId, "QIMProcess", issueId, variableMap);
//		        
//		String processInstanceId = processInstance.getId();
//		
//		log.info("processDefinitionId = " + processInstance.getProcessDefinitionId());
//        log.info("Process Instance Id = " + processInstance.getId());
//		log.info("Process Instance Business Key = " + processInstance.getBusinessKey());     
//		log.info("Process Instance Process Variables = " + processInstance.getProcessVariables());
//			
//        List<Task> tasks = workflowService.taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey("submitIssue").list(); 
//        for (Task task : tasks) {
//        	task.setName("填写问题表单");
//        	log.info("Task ID = " + task.getId());
//        	log.info("Task Name = " + task.getName());
//        	workflowService.identityService.setAuthenticatedUserId(SessionData.getLoginUser().getUserid());
//            workflowService.complete(task.getId(), variableMap);
//            log.info("Task Completed.........................");
//        }
//        
//        //Process Instance starter tasks
//        //workflowService.logUserTasks(log, userId);
//        log.info("问题提交.提交 end......................................................");       
//        return processInstanceId;
//
//        
////        log.info("gaidongcheng的任务还有: "  
////                + workflowService.taskService.createTaskQuery().taskAssignee("gaidongcheng").count()); 
////         
////        tasks = workflowService.taskService.createTaskQuery().taskCandidateGroup("动力总成部").list(); 
////        for (Task task : tasks) { 
////        	log.info(task.getName() + " 分配给 动力总成部"); 
////            workflowService.taskService.claim(task.getId(), "tanlong"); 
////            log.info("Task ID = " + task.getId() + " --> tanlong"); 
////        } 
////        
////        for (Task task : tasks) { 
////        	log.info("tanlong完成任务: " + task.getId());
////            workflowService.taskService.complete(task.getId()); 
////          
////        } 
////         
////        HistoricProcessInstance historicProcessInstance =  
////        		workflowService.historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult(); 
////        log.info("Process Instance " + procId + " Business Key: " + historicProcessInstance.getBusinessKey());
////        log.info("Process Instance " + procId + " Description: " + historicProcessInstance.getDescription());
////        log.info("Process Instance " + procId + " Start User Id: " + historicProcessInstance.getStartUserId());
////        log.info("Process Instance " + procId + " Start Time: " + historicProcessInstance.getStartTime());
////        log.info("Process Instance " + procId + " End Time: " + historicProcessInstance.getEndTime());
////        log.info("Process Instance " + procId + " Duration In Millis: " + historicProcessInstance.getDurationInMillis());
////        log.info("Process Instance " + procId + " Process Variables: " + historicProcessInstance.getProcessVariables());
//	
//	}	

}
