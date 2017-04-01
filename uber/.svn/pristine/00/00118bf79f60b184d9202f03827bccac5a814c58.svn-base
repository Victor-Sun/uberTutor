package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsLastAccessLogEntity;
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.entity.VImsIssueVerificationEntity;
import com.gnomon.pdms.service.CodeInfoService;
import com.gnomon.pdms.service.LastAccessLogService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskAction extends PDMSCrudActionSupport<GTIssueEntity> {
	
	private static final Log log = LogFactory.getLog(MyTaskAction.class);

	private static final long serialVersionUID = 1L;

	private int start;                                                          //当前Page
	private int limit;                                                          //PageSize
	private String query;                                                       //Search Field
	private String searchChoice;                                                //Search Choice
	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String ResponEngineer;
	private String formKey;
	private String vid;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSearchChoice() {
		return searchChoice;
	}
	public void setSearchChoice(String searchChoice) {
		this.searchChoice = searchChoice;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}

	private GTIssueEntity gtIssue;

	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private CodeInfoService codeInfoService;
	
	@Autowired
	private LastAccessLogService lastAccessLogService;

	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getResponEngineer() {
		return ResponEngineer;
	}

	public void setResponEngineer(String responEngineer) {
		ResponEngineer = responEngineer;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getKeyId() {
		return keyId;
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
	/**
	 * 返回TaskId
	 */
	public String returnTaskId(){
		return taskId;
	}
	
	/**
	 * 返回ProcessInstanceId
	 */
	public String returnProcessInstanceId(){
		return ProcessInstanceId;
	}
	
	private boolean historyFlag;
	public void setHistoryFlag(boolean historyFlag) {
		this.historyFlag = historyFlag;
	}
	/**
	 * 质量问题管理-待办任务-一览数据取得
	 */
//	public void getImsIssue() {
//
//		try{
//			JsonResult result = new JsonResult();
//			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();//v_ims_issue_process
//			Page<MyTaskInfo> list = this.myTaskService.getMyTaskList(SessionData.getUserId(), query, searchChoice, start, limit);
////	        if ((list != null) && (list.size() > 0)){
////	        	log.info("质量问题管理-待办任务-一览数据取得 : list.size() = " + list.size());
////	        };
//			for (MyTaskInfo myTaskInfo : list.getResult()) {
//				Map<String, Object> dataMap = new HashMap<String, Object>();
//				// ID
//				dataMap.put("keyId", myTaskInfo.getIssueId());
//				//问题编号
//				dataMap.put("code", myTaskInfo.getCode());
//				// 标题
//				dataMap.put("qeTitle", myTaskInfo.getIssueTitle());
//				// 问题状态
//				dataMap.put("qeStatus", myTaskInfo.getIssueStatus());
//				// 问题等级
//				dataMap.put("qeLevel", myTaskInfo.getIssueLevel());
//				// 所属项目
//				dataMap.put("beProject", myTaskInfo.getProject());
//				// 任务名称
//				dataMap.put("taskName", myTaskInfo.getTaskName());
//				//当前任务负责人
//				dataMap.put("taskOwner", myTaskInfo.getTaskOwner());
//				// 任务类型
//				dataMap.put("taskType", myTaskInfo.getTaskCategory());
//				// 等待时间
//				dataMap.put("waitDays", myTaskInfo.getWaitDays());
//				// 截止日期
//				//dataMap.put("closDate", myTaskInfo.getDueDate());
//				// 流程实例Id
//				dataMap.put("processInstanceId", myTaskInfo.getProcessInstanceId());
//				// 任务Id
//				dataMap.put("taskId", myTaskInfo.getTaskId());
//				// 表单标识
//				dataMap.put("formKey", myTaskInfo.getFormKey());
//				// 风险等级
//				dataMap.put("riskLevel", myTaskInfo.getRisk());
//				// 关注
//				dataMap.put("mark", myTaskInfo.getMark());
//				// 验证方案标识
//				dataMap.put("steps", myTaskInfo.getSteps());
//				data.add(dataMap);
//			}
//	        if ((data != null) && (data.size() > 0)){
//	        	log.info("质量问题管理-待办任务-一览数据取得 : data.size() = " + data.size());
//	        };
//			
//			// 结果返回
//			result.buildSuccessResultForList(data, Integer.valueOf(
//					String.valueOf(list.getTotalCount())));
//			Struts2Utils.renderJson(result);
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	/**
	 * 质量问题管理-待办任务-表单字段值取得
	 */
	public void getMyTaskVImsIssue() {
		try {
			Thread.sleep(1000);
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 是否关注
			dataMap.put("isMark", entity.getIsMark());
			// 问题编号
			dataMap.put("QestionId", entity.getCode());
			// 问题状态
//			dataMap.put("QeStatus", entity.getIssueStatus());
			if (formKey.equals("01")) {
				// 责任部门ID
				dataMap.put("ReDepartment", entity.getDeptName());
				// 问题等级
				dataMap.put("Qelevel", entity.getIssueLevelCode());
			} else {
				// 责任部门
				dataMap.put("ResDepartment", entity.getDeptName());
				// 问题等级
//				dataMap.put("QeLevel", entity.getIssueLevel());
			}
			// 专业经理
			dataMap.put("deptPm", entity.getDeptPmName());
			// 专业经理ID
			dataMap.put("deptPmId", entity.getDeptPmId());
			if (formKey.equals("02")||formKey.equals("04")) {
				dataMap.put("programObsId", entity.getDeptId());
			}
			// 责任工程师
			dataMap.put("ResponEngineerId", entity.getOwner());
			// 责任工程师
			dataMap.put("ResponEngineer", entity.getOwnerName());
			// 问题标题
			dataMap.put("ProTitle", entity.getTitle());
			// 问题提出人
			dataMap.put("QestionOut", entity.getSubmitUserName());
			// 问题时间
			if (entity.getOpenDate() != null) {
				dataMap.put("ProposedTime",
						DateUtils.formate(entity.getOpenDate()));
			} else {
				dataMap.put("ProposedTime", "");
			}
			// 发生时间
			if (entity.getOccurrenceDate() != null) {
				dataMap.put("OccurrenceTime",
						DateUtils.formate(entity.getOccurrenceDate()));
			} else {
				dataMap.put("OccurrenceTime", "");
			}
			// 发生场地
			dataMap.put("OccurrenceSite", entity.getOccurrenceSite());
			// 样车编号
			dataMap.put("FaultNumber", entity.getSampleNumber());
			// 故障零件里程
			dataMap.put("PartMileage", entity.getTroublePartMileage());
			// 实验进展
			dataMap.put("ProgressExper", entity.getTestProgress());
			// 问题描述
			dataMap.put("QeDescription", entity.getDescription());
			// 处置措施
			dataMap.put("DisposalMeasures", entity.getDisposalMeasures());
			// 初步分析原因
			dataMap.put("PreliminaryCause", entity.getFirstCauseAnalysis());
			// 备注
			dataMap.put("memo", entity.getMemo());
			//子项目ID
			dataMap.put("programVehicleId", entity.getSubProjectId());
			//子项目Code
			dataMap.put("programVehicleCode", entity.getSubProjectCode());
			//是否为修改问题表单页面
			if(formKey.equals("00")){
				// 问题来源
				dataMap.put("ProblemSource", entity.getIssueSourceId());
				// 所属项目
				dataMap.put("BeProject", entity.getProjectId());
				//子项目名
				dataMap.put("SubProject", entity.getSubProjectId());
				//样车阶段
				dataMap.put("BuildPhase", entity.getStageId());
				//实验类型
				dataMap.put("ExperimentType", entity.getTestTypeId());
			} else {
				// 问题来源
				dataMap.put("ProblemSource", entity.getIssueSource());
				// 所属项目
//				dataMap.put("BeProject", entity.getProjectName());
				//子项目名
				dataMap.put("SubProject", entity.getSubProjectName());
				//样车阶段
				dataMap.put("BuildPhase", entity.getStage());
				//实验类型
				dataMap.put("ExperimentType", entity.getTestType());
			}
			//问题等级
			dataMap.put("risk", entity.getRiskLevelCode());
			
			//MyTaskInfo03
			//问题性质
			if(formKey.equals("03")){
				dataMap.put("issueNatrue", entity.getIssueNatureId());
			}else{
				dataMap.put("issueNatrue", entity.getIssueNatureName());
			}
			//再次确认时间
			dataMap.put("reconfirmDate", DateUtils.formate(entity.getReconfirmDate()));
			//理由
			dataMap.put("ProReason", entity.getIsIssueReason());
			//临时措施
			dataMap.put("tempAction", entity.getTempAction());
			
			if(formKey.equals("04")){
				//MyTaskInfo04
				//问题类型
				dataMap.put("issueTypeId", entity.getIssueTypeId());
				//计划对策时间
				dataMap.put("expectedDate", DateUtils.formate(entity.getExpectedDate()));
				//计划关闭时间
				dataMap.put("PlanClosedDate", DateUtils.formate(entity.getPlanClosedDate()));
				//根本原因
				dataMap.put("rootCause", entity.getRootCause());
				//是否有对策
				dataMap.put("isAction", entity.getIsAction());
				//恒久对策
				dataMap.put("permAction", entity.getPermAction());
				//审核
				dataMap.put("lineMgr04", entity.getLineMgrName());
				//批准
//				dataMap.put("sectionMgr04", entity.getSectionMgrName());
				//复审
//				dataMap.put("vse04", entity.getVseName());
				//审核ID
				dataMap.put("lineMgrId04", entity.getLineMgrId());
				//批准ID
//				dataMap.put("sectionMgrId04", entity.getSectionMgrId());
				//复审ID
//				dataMap.put("vseId04", entity.getVseId());
			}
			
			//MyTaskInfo07
			if(formKey.equals("07")){
				// 责任部门ID
				dataMap.put("ResDepartmentId", entity.getDeptId());
				//效果确认
				dataMap.put("effectConfirmation", entity.getEffectConfirmation());
				//理由
				dataMap.put("reason", entity.getReason());
				//设变通知单（ECO）编号
				dataMap.put("ecoNo", entity.getEcoNo());
				//审核
				dataMap.put("lineMgr07", entity.getSubmitDeptName());
				//批准
//				dataMap.put("sectionMgr07", entity.getIncomeSectionName());
				//复审
//				dataMap.put("vse07", entity.getEwoNumberName());
				//审核ID
//				dataMap.put("lineMgrId07", entity.getSubmitDeptUserid());
				//批准ID
//				dataMap.put("sectionMgrId07", entity.getIncomeSectionId());
				//复审ID
//				dataMap.put("vseId07", entity.getEwoNumberId());
			}
			
			//MyTaskInfo08
			if(formKey.equals("08")){
				//效果确认
				dataMap.put("effectConfirmation", entity.getEffectConfirmation());
				//理由
				dataMap.put("reason", entity.getReason());
				//设变通知单（ECO）编号
				dataMap.put("ecoNo", entity.getEcoNo());
				//是否列入再发防止
				dataMap.put("isDp", entity.getIsDp());
			}
			
			//MyTaskInfo12
			if(formKey.equals("12")){
				//效果确认
				dataMap.put("effectConfirmation", entity.getEffectConfirmation());
				//理由
				dataMap.put("reason", entity.getReason());
				//是否更新DFMEA
				dataMap.put("isUpdatedDfmea", entity.getIsUpdatedDfmea());
				//是否更新技术条件
				dataMap.put("isUpdatedTc", entity.getIsUpdatedTc());
				//DFMEA编号
				dataMap.put("dfmeaNo", entity.getDfmeaNo());
				//技术标准编号
				dataMap.put("TechnicalStandardNo", entity.getTechnicalStandardNo());
				//其他措施
				dataMap.put("otherMeasures", entity.getOtherMeasures());
			}
			
			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-MyTaskInfoTo02表单字段值取得
	 */
	public void getMyTaskTo02VImsIssue() {
		try {
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
//			if(formKey.equals("02")){
//				// 责任工程师
//				dataMap.put("ResponEngineer", entity.getOwner());
//			} else {
			// 责任工程师
			dataMap.put("ResponEngineer", entity.getOwnerName());
//			}
			
			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-MyTaskInfoTo03表单字段值取得
	 */
	public void getMyTaskTo03VImsIssue() {
		try {
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			if(formKey.equals("03")){
				//问题性质
				dataMap.put("issueNatrue", entity.getIssueNatureId());
			}else{
				//MyTaskInfoTo03
				//问题性质
				dataMap.put("issueNatrue", entity.getIssueNatureName());
			}
			//MyTaskInfoTo03
			//再次确认时间
			dataMap.put("reconfirmDate", DateUtils.formate(entity.getReconfirmDate()));
			//理由
			dataMap.put("ProReason", entity.getIsIssueReason());
			//临时措施
			dataMap.put("tempAction", entity.getTempAction());
			
			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-MyTaskInfoTo04表单字段值取得
	 */
	public void getMyTaskTo04VImsIssue() {
		try {
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			//问题类型
			dataMap.put("issueTypeId", entity.getIssueTypeId());
			//计划对策时间
			dataMap.put("expectedDate", DateUtils.formate(entity.getExpectedDate()));
			//计划关闭时间
			dataMap.put("PlanClosedDate", DateUtils.formate(entity.getPlanClosedDate()));
			//根本原因
			dataMap.put("rootCause", entity.getRootCause());
			//是否有对策
			dataMap.put("isAction", entity.getIsAction());
			//恒久对策
			dataMap.put("permAction", entity.getPermAction());
			
			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-MyTaskInfoTo06表单字段值取得
	 */
	public void getMyTaskTo06VImsIssue() {
		try {
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			//设变通知单（ECO）编号
			dataMap.put("ecoNo", entity.getEcoNo());
			//是否列入再发防止
			dataMap.put("isDp", entity.getIsDp());
			
			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-验证方案-表单字段值取得
	 */
	public void getVerificationLine() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.myTaskService.getVerification(keyId, historyFlag);

			if(list.size() > 0){
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// ID
				dataMap.put("uid", map.get("ID"));
				// 验证方案
				dataMap.put("verification", map.get("VERIFICATION"));
				// 通过要求
				dataMap.put("passRequest", map.get("PASS_REQUEST"));
				// 实施信息
				dataMap.put("implementVehicle", map.get("IMPLEMENT_VEHICLE"));	
				
				// 验证部门
				dataMap.put("deptId", map.get("DEPT_ID"));
				dataMap.put("deptName", map.get("DEPT_NAME"));
				// 专业经理
				dataMap.put("deptPm", map.get("V_FM_NAME"));
				// 专业经理ID
				dataMap.put("deptPmId", map.get("V_FM"));
				
//				if(formKey.equals("100")){
//					// 验证部门
//					dataMap.put("deptId", entity.getDeptId());
//					// 专业经理
//					dataMap.put("deptPm", entity.getDeptPmName());
//					// 专业经理ID
//					dataMap.put("deptPmId", entity.getDeptPmId());
//				}else{
//					// 验证部门
//					dataMap.put("deptId", entity.getDeptName());
//					dataMap.put("programObsId", entity.getDeptId());
//				}
				// 验证工程师
				dataMap.put("verificationEngineer", map.get("VERIFICATION_ENGINEER"));
				// 验证工程师ID
				dataMap.put("verificationEngineerName", map.get("VERIFICATION_ENGINEER_NAME"));
				// 验证效果
				dataMap.put("verificationResult", map.get("VERIFICATION_RESULT"));
				// 验证方案标识
				dataMap.put("steps", map.get("STEPS"));
				// 验证效果
				dataMap.put("vLineMgr", map.get("V_LINE_MGR"));
				
				
				data.add(dataMap);
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getVerificationLineForm() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.myTaskService.getVerificationById(vid);

			if(list.size() > 0){
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// ID
				dataMap.put("uid", map.get("ID"));
				// 验证方案
				dataMap.put("verification", map.get("VERIFICATION"));
				// 通过要求
				dataMap.put("passRequest", map.get("PASS_REQUEST"));
				// 实施信息
				dataMap.put("implementVehicle", map.get("IMPLEMENT_VEHICLE"));	
				
				// 验证部门
				dataMap.put("deptId", map.get("DEPT_ID"));
				dataMap.put("deptName", map.get("DEPT_NAME"));
				// 专业经理
				dataMap.put("deptPm", map.get("V_FM_NAME"));
				// 专业经理ID
				dataMap.put("deptPmId", map.get("V_FM"));
				
//				if(formKey.equals("100")){
//					// 验证部门
//					dataMap.put("deptId", entity.getDeptId());
//					// 专业经理
//					dataMap.put("deptPm", entity.getDeptPmName());
//					// 专业经理ID
//					dataMap.put("deptPmId", entity.getDeptPmId());
//				}else{
//					// 验证部门
//					dataMap.put("deptId", entity.getDeptName());
//					dataMap.put("programObsId", entity.getDeptId());
//				}
				// 验证工程师
				dataMap.put("verificationEngineer", map.get("VERIFICATION_ENGINEER"));
				// 验证工程师ID
				dataMap.put("verificationEngineerName", map.get("VERIFICATION_ENGINEER_NAME"));
				// 验证效果
				dataMap.put("verificationResult", map.get("VERIFICATION_RESULT"));
				// 验证方案标识
				dataMap.put("steps", map.get("STEPS"));
				// 验证效果
				dataMap.put("vLineMgr", map.get("V_LINE_MGR"));
				
				
				data.add(dataMap);
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-验证方案-MyTaskInfoTo04表单字段值取得
	 */
	public void getVerificationLineTo04() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VImsIssueVerificationEntity> list = this.myTaskService.getVerificationScheme(keyId);

			if(list.size() > 0){
			for (VImsIssueVerificationEntity entity : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// ID
				dataMap.put("uId", entity.getId());
				// 验证方案
				dataMap.put("VerificationScheme", entity.getVerification());
				// 通过要求
				dataMap.put("Request", entity.getPassRequest());
				
				data.add(dataMap);
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-验证方案-MyTaskInfoTo05表单字段值取得
	 */
	public void getVerificationLineTo05() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VImsIssueVerificationEntity> list = this.myTaskService.getVerificationScheme(keyId);
			int i = 0;
			if(list.size() > 0){
			for (VImsIssueVerificationEntity entity : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("sequence", i);
				// ID
				dataMap.put("uId", entity.getId());
				// 验证方案
				dataMap.put("VerificationScheme", entity.getVerification());
				// 通过要求
				dataMap.put("Request", entity.getPassRequest());
				// 实施信息
				dataMap.put("ImplementVehicle", entity.getImplementVehicle());	
				
				if(formKey.equals("100")){
					// 验证部门
					dataMap.put("deptId", entity.getDeptId());
					// 专业经理
					dataMap.put("DeptPm", entity.getDeptPmName());
					// 专业经理ID
					dataMap.put("DeptPmId", entity.getDeptPmId());
				}else{
					// 验证部门
					dataMap.put("deptId", entity.getDeptName());
					dataMap.put("programObsId", entity.getDeptId());
				}
				
				data.add(dataMap);
				i++;
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-验证方案-MyTaskInfoTo06表单字段值取得
	 */
	public void getVerificationLineTo06() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VImsIssueVerificationEntity> list = this.myTaskService.getVerificationScheme(keyId);

			if(list.size() > 0){
			for (VImsIssueVerificationEntity entity : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// ID
				dataMap.put("uId", entity.getId());
				// 验证方案
				dataMap.put("VerificationScheme", entity.getVerification());
				// 通过要求
				dataMap.put("Request", entity.getPassRequest());
				// 实施信息
				dataMap.put("ImplementVehicle", entity.getImplementVehicle());	
				
				if(formKey.equals("100")){
					// 验证部门
					dataMap.put("deptId", entity.getDeptId());
					// 专业经理
					dataMap.put("DeptPm", entity.getDeptPmName());
					// 专业经理ID
					dataMap.put("DeptPmId", entity.getDeptPmId());
				}else{
					// 验证部门
					dataMap.put("deptId", entity.getDeptName());
					dataMap.put("programObsId", entity.getDeptId());
				}
				// 验证工程师
				dataMap.put("VerificationEngineerId", entity.getVerificationEngineer());
				dataMap.put("VerificationEngineer", entity.getVerificationEngineerName());
				// 验证效果
				dataMap.put("VerificationResult", entity.getVerificationResult());
				
				data.add(dataMap);
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-验证方案-并发表单字段值取得
	 */
	public void getOneVerificationLine() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VImsIssueVerificationEntity> list = this.myTaskService.getOneVerificationScheme(keyId, taskId);

			if(list.size() > 0){
				for (VImsIssueVerificationEntity entity : list) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					// ID
					dataMap.put("uId", entity.getId());
					// 验证方案
					dataMap.put("VerificationScheme", entity.getVerification());
					// 通过要求
					dataMap.put("Request", entity.getPassRequest());
					// 实施信息
					dataMap.put("ImplementVehicle", entity.getImplementVehicle());	
					
					if(formKey.equals("100")){
						// 验证部门
						dataMap.put("deptId", entity.getDeptId());
						// 专业经理
						dataMap.put("DeptPm", entity.getDeptPmName());
						// 专业经理ID
						dataMap.put("DeptPmId", entity.getDeptPmId());
					}else{
						// 验证部门
						dataMap.put("deptId", entity.getDeptName());
						dataMap.put("programObsId", entity.getDeptId());
					}
					// 验证工程师ID
					dataMap.put("VerificationEngineerId", entity.getVerificationEngineer());
					// 验证工程师
					dataMap.put("VerificationEngineer", entity.getVerificationEngineerName());
					// 验证效果
					dataMap.put("VerificationResult", entity.getVerificationResult());
					// 顺序
					dataMap.put("seq", entity.getSeq());
//					// 验证效果
//					dataMap.put("lineMgr06", entity.getSeverityName());
//					// 验证效果
//					dataMap.put("lineMgrId06", entity.getSeverityId());
					data.add(dataMap);
				}
				// 结果返回
				result.buildSuccessResultForList(data, 1);
				Struts2Utils.renderJson(result);
			} else {
				List<VImsIssueVerificationEntity> listV = this.myTaskService.getVerificationScheme(keyId);

				if(listV.size() > 0){
				for (VImsIssueVerificationEntity entity : listV) {
					Map<String, Object> dataMap = new HashMap<String, Object>();

					// ID
					dataMap.put("uId", entity.getId());
					// 验证方案
					dataMap.put("VerificationScheme", entity.getVerification());
					// 通过要求
					dataMap.put("Request", entity.getPassRequest());
					// 实施信息
					dataMap.put("ImplementVehicle", entity.getImplementVehicle());	
					
					if(formKey.equals("100")){
						// 验证部门
						dataMap.put("deptId", entity.getDeptId());
						// 专业经理
						dataMap.put("DeptPm", entity.getDeptPmName());
						// 专业经理ID
						dataMap.put("DeptPmId", entity.getDeptPmId());
					}else{
						// 验证部门
						dataMap.put("deptId", entity.getDeptName());
						dataMap.put("programObsId", entity.getDeptId());
					}
					// 验证工程师ID
					dataMap.put("VerificationEngineerId", entity.getVerificationEngineer());
					// 验证工程师
					dataMap.put("VerificationEngineer", entity.getVerificationEngineerName());
					// 验证效果
					dataMap.put("VerificationResult", entity.getVerificationResult());	
					// 验证效果
					dataMap.put("seq", entity.getSeq());
					
					data.add(dataMap);
				}

				// 结果返回
				result.buildSuccessResultForList(data, 1);
				Struts2Utils.renderJson(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-待办任务-零件表单取得
	 */
	public void getPartLine() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.myTaskService.getPart(keyId, historyFlag);

			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("partId", map.get("ID"));
				// 故障零件代号ID
				dataMap.put("partNumber", map.get("PART_NUMBER"));
				// 故障零件代号
				dataMap.put("troublePartNumber", map.get("TROUBLE_PART_NUMBER"));
				// 故障零件名称
				dataMap.put("partName", map.get("PART_NAME"));
				// 故障零件状态
				dataMap.put("partStatus", map.get("PART_STATUS"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-通知提醒表单信息取得
	 */
	public void getOneVImsIssue() {
		try {
			JsonResult result = new JsonResult();

			VImsIssueEntity entity = this.myTaskService
					.getMyTaskOneRep(keyId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (PDMSCommon.isNull(formKey)) {
				// 如果通过通知提醒弹表单,返回表单标识
				dataMap.put("formKey", entity.getFormKey());
			}
			// 问题标题
			dataMap.put("title", entity.getTitle());

			this.lastAccessLogService.updateAccessLog(keyId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
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
	
}
