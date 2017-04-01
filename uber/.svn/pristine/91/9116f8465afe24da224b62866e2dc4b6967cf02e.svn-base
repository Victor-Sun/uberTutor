package com.gnomon.pdms.action.ims;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.QeNotifyService;
import com.gnomon.pdms.service.SubmissionService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class SubmissionAction extends PDMSCrudActionSupport<GTIssueEntity> {

	private static final Log log = LogFactory.getLog(SubmissionAction.class);
			
//	private String ProTitle;
	private String ProposedTime;
//	private String ProblemSource;
//	private String BeProject;
//	private String SubProject;
//	private String BuildPhase;
//	private String OccurrenceTime;
//	private String OccurrenceSite;
//	private String ExperimentType;
//	private String FaultNumber;
//	private String PartMileage;
//	private String ProgressExper;
//	private String QeDescription;
//	private String DisposalMeasures;
//	private String PreliminaryCause;
	private String taskId;
	private String ProcessInstanceId;
	private String keyId;
	private String formKey;
//	private String memo;

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}

	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private static final long serialVersionUID = 1L;

	private GTIssueEntity gtIssue;
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private QeNotifyService qeNotifyService;
	
	@Autowired
	private DocumentService documentService;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}
	
	private String model;
	
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * 质量问题管理-问题提交-保存草稿
	 */
//	public String saveMyDraft(){
//		try{
//			GTIssueEntity entity = null;
//			entity = new GTIssueEntity();
//
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			String userName = SessionData.getLoginUser().getUsername();
//			// JSON解析
//			Map<String, String> model = this.convertJson(this.model);
//			
//			String uuid = null;
//			if (PDMSCommon.isNull(keyId)) {
//				uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
//				entity.setId(uuid);
//			} else {
//				uuid = keyId;
//				entity.setId(uuid);
//			}
//
//			entity.setTitle(model.get("title"));                         	//问题标题
//			
//			entity.setProjectId(model.get("projectId"));                    	//所属项目
//			entity.setSubProjectId(model.get("subProjectId"));                	//子项目
//			entity.setStageId(model.get("stageId"));                     	//样车阶段
//			entity.setIssueSourceId(model.get("issueSourceId"));            	//问题来源
//			
//			Date date2 = DateUtils.strToDate(model.get("occurrenceDate"));  	//发生时间
//			entity.setOccurrenceDate(date2);
//			
//			entity.setOccurrenceSite(model.get("occurrenceSite"));          	//发生场地
//			entity.setTestTypeId(model.get("testTypeId"));              	//实验类型
//			entity.setSampleNumber(model.get("sampleNumber"));               	//样车编号
//			entity.setTroublePartMileage(model.get("troublePartMileage"));         	//故障零件里程
//			entity.setTestProgress(model.get("testProgress"));             	//实验进展
//			entity.setDescription(model.get("description"));              	//问题描述
//			entity.setDisposalMeasures(model.get("disposalMeasures"));      	//处置措施
//			entity.setFirstCauseAnalysis(model.get("firstCauseAnalysis"));    	//初步分析原因
//			entity.setMemo(model.get("memo"));                              	//备注
////			entity.setSubmitUserName(userName);                                 //问题提出人
//			entity.setSubmitUser(userId);                                       //问题提出人ID
//			entity.setIssueStatusCode("ISSUE_STATUS_50");                       //问题状态
//			if("ISSUE_LIFECYCLE_REOPEN".equals(entity.getIssueLifecycleCode())){
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_REDRAFT");
//			} else {
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_DRAFT");
//			}
////			SimpleDateFormat Date1 = new SimpleDateFormat("yyyy/MM/dd");		//提出时间
////			ProposedTime = Date1.format(System.currentTimeMillis());
////			Date date1 = Date1.parse(ProposedTime);
////			entity.setOpenDate(date1);
//
//			entity.setCreateBy(userId);
//			entity.setCreateDate(new Date());
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			if("000".equals(formKey)){
//				myTaskService.saveDraftIssue(userId ,ProcessInstanceId ,taskId);
//			}
//			submissionService.save(entity);
//			
//			this.writeSuccessResult(uuid);
//			
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			this.writeErrorResult(ex.getMessage());
//		}
//		return null;
//	}	
	/**
	 * 质量问题管理-问题提交-提交问题
	 */
//	public String saveSubmit(){
//		try{
//			GTIssueEntity entity = null;
//			entity = new GTIssueEntity();
//
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			// JSON解析
//			Map<String, String> model = this.convertJson(this.model);
//			
//			String userName = SessionData.getLoginUser().getUsername();
//			
//			String uuid = null;
//			if (PDMSCommon.isNull(keyId)) {
//				uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
//				entity.setId(uuid);
//			} else {
//				uuid = keyId;
//				entity.setId(uuid);
//			}
//			entity.setTitle(model.get("ProTitle"));                             //问题标题
//			entity.setSubmitUser(userId);                                       //问题提出人ID
////			entity.setSubmitUserName(userName);                             	//问题提出人
//			entity.setIssueStatusCode("ISSUE_STATUS_60");                       //问题状态
//			
//			SimpleDateFormat Date1 = new SimpleDateFormat("yyyy/MM/dd");		//提出时间
//			ProposedTime = Date1.format(System.currentTimeMillis());
//			Date date1 = Date1.parse(ProposedTime);
//			entity.setOpenDate(date1);
//			
//			entity.setIssueSourceId(model.get("ProblemSource"));                //问题来源
//			entity.setProjectId(model.get("BeProject"));                        //所属项目
//			entity.setSubProjectId(model.get("SubProject"));                    //子项目
//			entity.setStageId(model.get("BuildPhase"));                         //样车阶段
//			
//			Date date2 = DateUtils.strToDate(model.get("OccurrenceTime"));      //发生时间
//			entity.setOccurrenceDate(date2);
//			
//			entity.setOccurrenceSite(model.get("OccurrenceSite"));              //发生场地
//			
//			String typeId = submissionService.saveTypeId(model.get("ExperimentType"));
//			entity.setTestTypeId(typeId);                                       //实验类型
//			entity.setSampleNumber(model.get("FaultNumber"));                   //样车编号
//			entity.setTroublePartMileage(model.get("PartMileage"));             //故障零件里程
//			entity.setTestProgress(model.get("ProgressExper"));                 //实验进展
//			entity.setDescription(model.get("QeDescription"));                  //问题描述
//			entity.setDisposalMeasures(model.get("DisposalMeasures"));          //处置措施
//			entity.setFirstCauseAnalysis(model.get("PreliminaryCause"));        //初步分析原因
//			entity.setMemo(model.get("memo"));                                  //备注
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_OPEN");
////			entity.setFormKey("010");                                           //表单标识
//			
//			entity.setCreateBy(userId);
//			entity.setCreateDate(new Date());
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			log.info("saveSubmit() --> submissionService.submitIssue()......");
//			
//			if("000".equals(formKey)){
//				myTaskService.modifyIssue(SessionData.getUserId(), ProcessInstanceId, taskId, model.get("SubProject"));
//			} else {
//				ProcessInstanceId = submissionService.submitIssue(uuid, SessionData.getUserId(), model.get("SubProject"));
//			}
////			entity.setProcessInstanceId(ProcessInstanceId);
//			log.info("Process Instance Id in entity = " + ProcessInstanceId);
//			
//			submissionService.save(entity);
//			if (PDMSCommon.isNull(keyId)) {
//				qeNotifyService.saveSubmitP(uuid);
//			} else {
//				qeNotifyService.updateSubmitP(keyId);
//			}
//			this.writeSuccessResult(uuid);
//			
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			this.writeErrorResult(ex.getMessage());
//		}
//		return null;
//	}
	//删除问题
	public String deleteIssue(){
		try{
			JsonResult result = new JsonResult();
			GTIssueEntity entity= 
					this.submissionService.deleteIssue(keyId);
			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();
			entity.setDeleteBy(userId);
			entity.setDeleteDate(new Date());
			
			myTaskService.save(entity);
			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
