package com.gnomon.pdms.action.ims;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.UUID;











import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.MySubInfoService;
import com.gnomon.pdms.service.MyTaskService;
import com.gnomon.pdms.service.QeNotifyService;
import com.gnomon.pdms.service.SubmissionService;


@Namespace("/ims")
public class MySubInfoAction extends PDMSCrudActionSupport<GTIssueEntity> {

	private String ProTitle;
	private String QestionOut;
	private String ProposedTime;
	private String ProblemSource;
	private String BeProject;
	private String SubProject;
	private String BuildPhase;
	private String OccurrenceTime;
	private String OccurrenceSite;
	private String ExperimentType;
	private String FaultNumber;
	private String PartMileage;
	private String ProgressExper;
	private String QeDescription;
	private String DisposalMeasures;
	private String PreliminaryCause;
	private String ProcessInstanceId;
	private String taskId;
	private String memo;
	private String model;
	
	public void setModel(String model) {
		this.model = model;
	}

	public String getProTitle() {
		return ProTitle;
	}

	public void setProTitle(String proTitle) {
		ProTitle = proTitle;
	}

	public String getQestionOut() {
		return QestionOut;
	}

	public void setQestionOut(String qestionOut) {
		QestionOut = qestionOut;
	}

	public String getProposedTime() {
		return ProposedTime;
	}

	public void setProposedTime(String proposedTime) {
		ProposedTime = proposedTime;
	}

	public String getProblemSource() {
		return ProblemSource;
	}

	public void setProblemSource(String problemSource) {
		ProblemSource = problemSource;
	}

	public String getBeProject() {
		return BeProject;
	}

	public void setBeProject(String beProject) {
		BeProject = beProject;
	}

	public String getSubProject() {
		return SubProject;
	}

	public void setSubProject(String subProject) {
		SubProject = subProject;
	}

	public String getBuildPhase() {
		return BuildPhase;
	}

	public void setBuildPhase(String buildPhase) {
		BuildPhase = buildPhase;
	}

	public String getOccurrenceTime() {
		return OccurrenceTime;
	}

	public void setOccurrenceTime(String occurrenceTime) {
		OccurrenceTime = occurrenceTime;
	}

	public String getOccurrenceSite() {
		return OccurrenceSite;
	}

	public void setOccurrenceSite(String occurrenceSite) {
		OccurrenceSite = occurrenceSite;
	}

	public String getExperimentType() {
		return ExperimentType;
	}

	public void setExperimentType(String experimentType) {
		ExperimentType = experimentType;
	}

	public String getFaultNumber() {
		return FaultNumber;
	}

	public void setFaultNumber(String faultNumber) {
		FaultNumber = faultNumber;
	}

	public String getPartMileage() {
		return PartMileage;
	}

	public void setPartMileage(String partMileage) {
		PartMileage = partMileage;
	}

	public String getProgressExper() {
		return ProgressExper;
	}

	public void setProgressExper(String progressExper) {
		ProgressExper = progressExper;
	}

	public String getQeDescription() {
		return QeDescription;
	}

	public void setQeDescription(String qeDescription) {
		QeDescription = qeDescription;
	}

	public String getDisposalMeasures() {
		return DisposalMeasures;
	}

	public void setDisposalMeasures(String disposalMeasures) {
		DisposalMeasures = disposalMeasures;
	}

	public String getPreliminaryCause() {
		return PreliminaryCause;
	}

	public void setPreliminaryCause(String preliminaryCause) {
		PreliminaryCause = preliminaryCause;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}

	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	public MySubInfoService getMySubInfoService() {
		return mySubInfoService;
	}

	public void setMySubInfoService(MySubInfoService mySubInfoService) {
		this.mySubInfoService = mySubInfoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private static final long serialVersionUID = 1L;

	private GTIssueEntity gtIssue;
	
	@Autowired
	private MySubInfoService mySubInfoService;
	
	@Autowired
	private QeNotifyService qeNotifyService;
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private MyTaskService myTaskService;
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String keyId;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	/**
	 * 质量问题管理-我的草稿-详细信息
	 */
	public String updMyDraft(){
		try{
			JsonResult result = new JsonResult();
			GTIssueEntity entity = 
					this.mySubInfoService.getMySubInfo(keyId);
			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();
			
			//String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
			entity.setId(keyId);
			entity.setTitle(ProTitle);                                          //问题标题
//			entity.setSubmitUserName(QestionOut);                               //问题提出人
			
			entity.setIssueSourceId(ProblemSource);                             //问题来源
			entity.setProjectId(BeProject);                                     //所属项目
			entity.setSubProjectId(SubProject);                                 //子项目
			entity.setStageId(BuildPhase);                                      //样车阶段

			Date date2 = DateUtils.strToDate(OccurrenceTime);                   //发生时间
			entity.setOccurrenceDate(date2);
			
			entity.setOccurrenceSite(OccurrenceSite);                           //发生场地
			entity.setTestTypeId(ExperimentType);                               //实验类型
			entity.setSampleNumber(FaultNumber);                                //样车编号
			entity.setTroublePartMileage(PartMileage);                          //故障零件里程
			entity.setTestProgress(ProgressExper);                              //实验进展
			entity.setDescription(QeDescription);                               //问题描述
			entity.setDisposalMeasures(DisposalMeasures);                       //处置措施
			entity.setFirstCauseAnalysis(PreliminaryCause);                     //初步分析原因

			entity.setUpdateBy(userId);
			entity.setUpdateDate(new Date());
			
			mySubInfoService.save(entity);
			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);

		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}	
	/**
	 * 质量问题管理-问题提交-提交问题
	 */
	public String saveSubmit(){
		try{
			GTIssueEntity entity = null;
			entity = new GTIssueEntity();

			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			
			String userName = SessionData.getLoginUser().getUsername();
			
			String uuid = null;
			if (PDMSCommon.isNull(keyId)) {
				uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
				entity.setId(uuid);
			} else {
				uuid = keyId;
				entity.setId(uuid);
			}
			entity.setTitle(model.get("ProTitle"));                             //问题标题
			entity.setSubmitUser(userId);                                       //问题提出人ID
//			entity.setSubmitUserName(userName);                             	//问题提出人
			entity.setIssueStatusCode("ISSUE_STATUS_60");                       //问题状态
			
			SimpleDateFormat Date1 = new SimpleDateFormat("yyyy/MM/dd");		//提出时间
			ProposedTime = Date1.format(System.currentTimeMillis());
			Date date1 = Date1.parse(ProposedTime);
			entity.setOpenDate(date1);
			
			entity.setIssueSourceId(model.get("ProblemSource"));                //问题来源
			entity.setProjectId(model.get("BeProject"));                        //所属项目
			entity.setSubProjectId(model.get("SubProject"));                    //子项目
			entity.setStageId(model.get("BuildPhase"));                         //样车阶段
			
			Date date2 = DateUtils.strToDate(model.get("OccurrenceTime"));      //发生时间
			entity.setOccurrenceDate(date2);
			
			entity.setOccurrenceSite(model.get("OccurrenceSite"));              //发生场地
			
			String typeId = submissionService.saveTypeId(model.get("ExperimentType"));
			entity.setTestTypeId(typeId);                                       //实验类型
			entity.setSampleNumber(model.get("FaultNumber"));                   //样车编号
			entity.setTroublePartMileage(model.get("PartMileage"));             //故障零件里程
			entity.setTestProgress(model.get("ProgressExper"));                 //实验进展
			entity.setDescription(model.get("QeDescription"));                  //问题描述
			entity.setDisposalMeasures(model.get("DisposalMeasures"));          //处置措施
			entity.setFirstCauseAnalysis(model.get("PreliminaryCause"));        //初步分析原因
			entity.setMemo(model.get("memo"));                                  //备注
			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_OPEN");
//			entity.setFormKey("010");                                           //表单标识
			
			entity.setCreateBy(userId);
			entity.setCreateDate(new Date());
			entity.setUpdateBy(userId);
			entity.setUpdateDate(new Date());
			
//			ProcessInstanceId = submissionService.submitIssue(uuid, SessionData.getUserId(), model.get("SubProject"));
			
//			entity.setProcessInstanceId(ProcessInstanceId);
			
			submissionService.save(entity);
			qeNotifyService.saveSubmitP(uuid);
			
			this.writeSuccessResult(uuid);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
		return null;
	}
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

