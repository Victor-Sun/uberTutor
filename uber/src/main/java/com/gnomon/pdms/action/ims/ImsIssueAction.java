package com.gnomon.pdms.action.ims;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.GTIssueVO;
import com.gnomon.pdms.entity.ImsLastAccessLogEntity;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.procedure.PkgPermissionDBProcedureServcie;
import com.gnomon.pdms.service.ImsIssueService;
import com.gnomon.pdms.service.LastAccessLogService;

@Namespace("/ims")
public class ImsIssueAction extends PDMSCrudActionSupport<GTIssueVO> {
	
	private static final Log log = LogFactory.getLog(ImsIssueAction.class);

	private static final long serialVersionUID = 1L;

	private String method;
	
	private String issueId;
	
	private String id;
	
	private String fromIssueId;
	
	private String toIssueId;
	
	private String comment;
	
	private String fromDate;
	
	private String toDate;
	
	private String userName;
	
	private String title;
	
	private String code;
	
	private String programName;
	
	private String partCode;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;
	
	@Autowired
	private PkgPermissionDBProcedureServcie pkgPermissionDBProcedureServcie;
	
	@Autowired
	private ImsIssueService imsIssueService;
	
	@Autowired
	private LastAccessLogService lastAccessLogService;
	
	public String getMethod() {
		return method;
	}
	public String getFromIssueId() {
		return fromIssueId;
	}
	public void setFromIssueId(String fromIssueId) {
		this.fromIssueId = fromIssueId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToIssueId() {
		return toIssueId;
	}
	public void setToIssueId(String toIssueId) {
		this.toIssueId = toIssueId;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}
	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	

	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	private boolean historyFlag;
	public void setHistoryFlag(boolean historyFlag) {
		this.historyFlag = historyFlag;
	}

	private GTIssueEntity gtIssue;
	
	private GTIssueVO gtIssueVO;
	
	private String programId;

	@Override
	public GTIssueVO getModel() {
		return gtIssueVO;
	}
	

	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public GTIssueVO getGtIssueVO() {
		return gtIssueVO;
	}
	public void setGtIssueVO(GTIssueVO gtIssueVO) {
		this.gtIssueVO = gtIssueVO;
	}
	@Override
	protected void prepareModel() throws Exception {
		if(StringUtils.isEmpty(id)){
			gtIssueVO = new GTIssueVO();
			String createBy = SessionData.getLoginUserId();
			gtIssueVO.setCreateBy(createBy);
			gtIssueVO.setCreateDate(new Date());
			gtIssueVO.setSubmitUser(createBy);
		}else{
			gtIssue = imsIssueService.get(id);
			gtIssueVO = new GTIssueVO();
//			BeanUtils.copyProperties(gtIssueVO, gtIssue);
			PropertyUtils.copyProperties(gtIssueVO, gtIssue);
			String createBy = SessionData.getLoginUserId();
			gtIssueVO.setCreateBy(createBy);
//			System.out.print("================="+gtIssueVO.getSubmitUser());
		}
	}
	
	private void processVo(GTIssueVO gtIssueVO){
		String openDateStr = gtIssueVO.getOpenDateStr();
		if(StringUtils.isNotEmpty(openDateStr)){
			gtIssueVO.setOpenDate(DateUtils.strToDate(openDateStr));
		}
		String rootCausePlanDateStr = gtIssueVO.getRootCausePlanDateStr();
		if(StringUtils.isNotEmpty(rootCausePlanDateStr)){
			gtIssueVO.setRootCausePlanDate(DateUtils.strToDate(rootCausePlanDateStr));
		}
		String rootCauseActualDateStr = gtIssueVO.getRootCauseActualDateStr();
		if(StringUtils.isNotEmpty(rootCauseActualDateStr)){
			gtIssueVO.setRootCauseActualDate(DateUtils.strToDate(rootCauseActualDateStr));
		}
		String closeReqActualDateStr = gtIssueVO.getCloseReqActualDateStr();
		if(StringUtils.isNotEmpty(closeReqActualDateStr)){
			gtIssueVO.setCloseReqActualDate(DateUtils.strToDate(closeReqActualDateStr));
		}
		String occurrenceDateStr = gtIssueVO.getOccurrenceDateStr();
		if(StringUtils.isNotEmpty(occurrenceDateStr)){
			gtIssueVO.setOccurrenceDate(DateUtils.strToDate(occurrenceDateStr));
		}
		String planClosedDateStr = gtIssueVO.getPlanClosedDateStr();
		if(StringUtils.isNotEmpty(planClosedDateStr)){
			gtIssueVO.setPlanClosedDate(DateUtils.strToDate(planClosedDateStr));
		}
		String expectedDateStr = gtIssueVO.getExpectedDateStr();
		if(StringUtils.isNotEmpty(expectedDateStr)){
			gtIssueVO.setExpectedDate(DateUtils.strToDate(expectedDateStr));
		}
		String reconfirmDateStr = gtIssueVO.getReconfirmDateStr();
		if(StringUtils.isNotEmpty(reconfirmDateStr)){
			gtIssueVO.setReconfirmDate(DateUtils.strToDate(reconfirmDateStr));
		}
		String effectConfirmationDateStr = gtIssueVO.getEffectConfirmationDateStr();
		if(StringUtils.isNotEmpty(effectConfirmationDateStr)){
			gtIssueVO.setEffectConfirmationDate(DateUtils.strToDate(effectConfirmationDateStr));
		}
//		String openDateStr = gtIssueVO.getOpenDateStr();
//		if(StringUtils.isNotEmpty(openDateStr)){
//			gtIssueVO.setOpenDate(DateUtils.strToDate(openDateStr));
//		}
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
		this.processVo(gtIssueVO);
		Method m = this.getClass().getDeclaredMethod(method);
		m.invoke(this);
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getImsIssueForm() {
		try {
//			Thread.sleep(1000);
			JsonResult result = new JsonResult();

			Map<String, Object> map = imsIssueService.getImsIssue(issueId, historyFlag);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 是否关注
//			dataMap.put("isMark", entity.getis);
			dataMap.put("id", map.get("ID"));
			// 问题标题
			dataMap.put("title", map.get("TITLE"));
			// 问题编号
			dataMap.put("code", map.get("CODE"));
			// 所属项目
			dataMap.put("projectId", map.get("PROJECT_ID"));
			dataMap.put("programCode", map.get("PROGRAM_CODE"));
			//样车阶段
			dataMap.put("stageId", map.get("STAGE_ID"));
			dataMap.put("stageName", map.get("stage"));
			// 问题来源
			dataMap.put("issueSourceId", map.get("ISSUE_SOURCE_ID"));
			dataMap.put("issueSource", map.get("ISSUE_SOURCE"));
			dataMap.put("issueSourceCode", map.get("ISSUE_SOURCE_CODE"));
			//问题类型
			dataMap.put("issueTypeId", map.get("ISSUE_TYPE_ID"));
			// 问题等级
			dataMap.put("issueLevelCode", map.get("ISSUE_LEVEL_CODE"));
			dataMap.put("issueLevelName", map.get("ISSUE_LEVEL_NAME"));
			// 问题状态
			dataMap.put("issueStatusCode", map.get("ISSUE_STATUS_CODE"));
			dataMap.put("issueStatusName", map.get("ISSUE_PROCESSING_STATUS"));
			// 问题描述
			dataMap.put("description", map.get("DESCRIPTION"));
			//提出人
			dataMap.put("submitUser", map.get("SUBMIT_USER"));
			//提出部门
			dataMap.put("submitDeptId", map.get("SUBMIT_DEPT_ID"));
			// 问题时间
			dataMap.put("openDate",ObjectConverter.convertDate2String(map.get("OPEN_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("openDateStr",ObjectConverter.convertDate2String(map.get("OPEN_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			// 责任工程师
			dataMap.put("owner", map.get("OWNER"));
			dataMap.put("ownerName", map.get("OWNER_NAME"));
			dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
			//责任部门科长
			dataMap.put("lineMgr", map.get("LINE_MGR"));
			// 责任部门ID
			dataMap.put("deptId", map.get("DEPT_ID"));
			// 责任部门ID
			dataMap.put("deptIdName", map.get("DEPT_NAME"));
			//根本原因
			dataMap.put("rootCause", map.get("ROOT_CAUSE"));
			//对策决定.提交日期
			dataMap.put("rootCausePlanDate", map.get("ROOT_CAUSE_PLAN_DATE"));
			// 对策决定.确认日期
			dataMap.put("rootCauseActualDate", map.get("ROOT_CAUSE_ACTUAL_DATE"));
			//临时措施
			dataMap.put("tempAction", map.get("TEMP_ACTION"));
			//恒久对策
			dataMap.put("permAction", map.get("PERM_ACTION"));
			//实际关闭时间
			dataMap.put("closeReqActualDate", map.get("CLOSE_REQ_ACTUAL_DATE"));
			//关闭说明
			dataMap.put("closeNote", map.get("CLOSE_NOTE"));
			//备注
			dataMap.put("memo", map.get("MEMO"));
			//对策决定.审核日期
			dataMap.put("lineMgrApprDueDate", ObjectConverter.convertDate2String(map.get("LINE_MGR_APPR_DUE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//对策决定.批准日期
			dataMap.put("secMgrApprDueDate", ObjectConverter.convertDate2String(map.get("SEC_MGR_APPR_DUE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("createBy", map.get("CREATE_BY"));
			dataMap.put("createDate", ObjectConverter.convertDate2String(map.get("CREATE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("updateBy", map.get("UPDATE_BY"));
			dataMap.put("updateDate", ObjectConverter.convertDate2String(map.get("UPDATE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//问题生命周期状态
			dataMap.put("issueLifecycleCode", map.get("ISSUE_LIFECYCLE_CODE"));
//			//子项目ID
			dataMap.put("subProjectId", map.get("SUB_PROJECT_ID"));
			dataMap.put("subProjectCode", map.get("SUB_PROJECT_CODE").toString());
			// 发生时间
			dataMap.put("occurrenceDateStr",ObjectConverter.convertDate2String(map.get("OCCURRENCE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			// 发生场地
			dataMap.put("occurrenceSite", map.get("OCCURRENCE_SITE"));
			//试验类型ID
			dataMap.put("testTypeId", map.get("TEST_TYPE_ID"));
			dataMap.put("testType", map.get("TEST_TYPE"));
			// 样车编号
			dataMap.put("sampleNumber", map.get("SAMPLE_NUMBER"));
			// 故障零件里程
			dataMap.put("troublePartMileage", map.get("TROUBLE_PART_MILEAGE"));
			// 实验进展
			dataMap.put("testProgress", map.get("TEST_PROGRESS"));
			// 处置措施
			dataMap.put("disposalMeasures", map.get("DISPOSAL_MEASURES"));
			// 初步分析原因
			dataMap.put("firstCauseAnalysis", map.get("FIRST_CAUSE_ANALYSIS"));
			//问题类型
//			dataMap.put("issueTypeId", map.get("TEST_TYPE_ID"));
			//是否是质量问题问题
			dataMap.put("isIssue", map.get("IS_ISSUE"));
			//理由
			dataMap.put("isIssueReason", map.get("IS_ISSUE_REASON"));
			//预计对策阶段
			dataMap.put("expectedStageId", map.get("EXPECTED_STAGE_ID"));
			//计划对策时间
			dataMap.put("expectedDate", ObjectConverter.convertDate2String(map.get("EXPECTED_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("expectedDateStr", ObjectConverter.convertDate2String(map.get("EXPECTED_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//是否有对策
			dataMap.put("isAction", map.get("IS_ACTION"));
//			//不对策原因
			dataMap.put("noActionReason", map.get("NO_ACTION_REASON"));
//			//删除时间
//			dataMap.put("deleteDate", ObjectConverter.convertDate2String(map.get("DELETE_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//问题风险等级
//			dataMap.put("riskLevelCode", map.get("RISK_LEVEL_CODE"));
//			dataMap.put("publishDate", ObjectConverter.convertDate2String(map.get("PUBLISH_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//问题性质
			dataMap.put("issueNatureId", map.get("ISSUE_NATURE_ID"));
			dataMap.put("issueNatureName", map.get("ISSUE_NATURE_NAME"));
			//再次确认时间
			dataMap.put("reconfirmDate", ObjectConverter.convertDate2String(map.get("RECONFIRM_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("reconfirmDateStr", ObjectConverter.convertDate2String(map.get("RECONFIRM_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//计划关闭时间
			dataMap.put("planClosedDate", ObjectConverter.convertDate2String(map.get("PLAN_CLOSED_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("planClosedDateStr", ObjectConverter.convertDate2String(map.get("PLAN_CLOSED_DATE"), DateUtils.FORMAT_PATTEN_DATE));
			//是否更新DFMEA
			dataMap.put("isUpdatedDfmea", map.get("IS_UPDATED_DFMEA"));
			//是否更新技术条件
			dataMap.put("isUpdatedTc", map.get("IS_UPDATED_TC"));
			//设变通知单（ECO）编号
			dataMap.put("ecoNo", map.get("ECO_NO"));
//			//是否列入再发防止
			dataMap.put("isDp", map.get("IS_DP"));
			//DFMEA编号
			dataMap.put("dfmeaNo", map.get("DFMEA_NO"));
			//技术标准编号
			dataMap.put("technicalStandardNo", map.get("TECHNICAL_STANDARD_NO"));
//			//其他措施
			dataMap.put("otherMeasures", map.get("OTHER_MEASURES"));
//			//效果确认
			dataMap.put("effectConfirmation", map.get("EFFECT_CONFIRMATION"));
			//效果确认时间
			dataMap.put("effectConfirmationDateStr", ObjectConverter.convertDate2String(map.get("EFFECT_CONFIRMATION_DATE"),DateUtils.FORMAT_PATTEN_DATE));
			
			//理由
			dataMap.put("reason", map.get("REASON"));
//			//问题发布.专业经理
//			dataMap.put("deptPmId", map.get("DEPT_PM_ID"));
//			//问题确认开始时间
//			dataMap.put("confirmStartDate", ObjectConverter.convertDate2String(map.get("CONFIRM_START_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//问题确认完成时间
//			dataMap.put("confirmFinishDate", ObjectConverter.convertDate2String(map.get("CONFIRM_FINISH_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//原因对策开始时间
//			dataMap.put("rootCauseStartDate", ObjectConverter.convertDate2String(map.get("ROOT_CAUSE_START_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//原因对策完成时间
//			dataMap.put("rootCauseFinishDate", ObjectConverter.convertDate2String(map.get("ROOT_CAUSE_FINISH_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//效果验证开始时间
//			dataMap.put("verificationStartDate", ObjectConverter.convertDate2String(map.get("VERIFICATION_START_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//效果验证完成时间
//			dataMap.put("verificationFinishDate", ObjectConverter.convertDate2String(map.get("VERIFICATION_FINISH_DATE"), DateUtils.FORMAT_PATTEN_DATE));
//			//责任部门部长
//			dataMap.put("deptMgr", map.get("DEPT_MGR"));
//			//质量经理
//			dataMap.put("qm", map.get("QM"));
//			//责任部门专业经理
			dataMap.put("fm", map.get("FM"));
			dataMap.put("fmName", map.get("FM_NAME"));
//			
//			
			dataMap.put("submitUserName", map.get("SUBMIT_USER_NAME"));
			if(map.get("FORM_CLASS_ID") != null){
				String formClassId = map.get("FORM_CLASS_ID").toString();
				if("myTaskInfo01".equals(formClassId)){
					String userId = SessionData.getLoginUserId();
					Map<String, Object> isQimsManagerMap = pkgPermissionDBProcedureServcie.isQimsManager(userId);
					String isQimsManager = (String)isQimsManagerMap.get("isQimsManager");
					dataMap.put("isQimsManager", isQimsManager);
				}
			}
			
			// 访问次数更新
			this.lastAccessLogService.updateAccessLog(issueId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE);
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			log.error("Exception",ex);
		}
	}
	
	public void getMyLastIssueForm() {
		try {
			JsonResult result = new JsonResult();

			String userId = SessionData.getLoginUserId();
			Map<String, Object> map = imsIssueService.getMyLastIssue(userId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("issueSourceId", map.get("ISSUE_SOURCE_ID"));
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateIssue(){
//		System.out.print("============="+gtIssueVO.getTitle());
		try {
			Map<String, Object> map = pkgImsDBProcedureServcie.updateIssue(gtIssueVO);
			this.writeSuccessResult(map.get("id"));
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getMergeIssueList() {

		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			GTPage<Map<String, Object>> queryResult = imsIssueService.getMergeIssueList(programId,this.getPage(),this.getLimit(),fromDate,toDate,userName,title,code);
			

			for (Map<String, Object> map : queryResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("issueId", map.get("ISSUE_ID"));
				//问题编号
				dataMap.put("code", map.get("CODE"));
				//问题标题
				dataMap.put("mergeTitle", map.get("TITLE"));
				//问题状态
				dataMap.put("issueStatus", map.get("ISSUE_PROCESSING_STATUS"));
				//问题等级
				dataMap.put("issueLevel", map.get("ISSUE_LEVEL_NAME"));
				//所属项目
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				
				dataMap.put("projectId", map.get("PROJECT_ID"));
				//创建人
				dataMap.put("mergeSubmitUserName", map.get("SUBMIT_USER_NAME"));
				//创建日期
				dataMap.put("mergeCreateDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("mergeDescription", map.get("DESCRIPTION"));
				dataMap.put("mergeDisposalMeasures", map.get("DISPOSAL_MEASURES"));
				dataMap.put("mergeRootCause", map.get("FIRST_CAUSE_ANALYSIS"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,queryResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void mergeIssue(){
		try {
			String userId = SessionData.getLoginUserId();
			Map<String, Object> map = pkgImsDBProcedureServcie.mergeIssue(fromIssueId,issueId, userId , comment);
			this.writeSuccessResult(map.get("id"));
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void cancelMergeIssue(){
		try {
			String userId = SessionData.getLoginUserId();
			Map<String, Object> map = pkgImsDBProcedureServcie.cancelMergeIssue(issueId, userId);
			this.writeSuccessResult(map.get("id"));
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getMergedIssueList() {

		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = imsIssueService.getMergedIssueList(issueId,fromDate,toDate,userName,title,code);
			List<Map<String, Object>> listTemp = imsIssueService.getMergedMainIssueList(issueId,fromDate,toDate,userName,title,code);
			list.addAll(listTemp);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("issueId", map.get("ISSUE_ID"));
				//问题编号
				dataMap.put("code", map.get("CODE"));
				//问题标题
				dataMap.put("mergeTitle", map.get("TITLE"));
				//问题状态
				dataMap.put("issueStatus", map.get("ISSUE_PROCESSING_STATUS"));
				//问题等级
				dataMap.put("issueLevel", map.get("ISSUE_LEVEL_NAME"));
				//所属项目
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				
				dataMap.put("projectId", map.get("PROJECT_ID"));
				//创建人
				dataMap.put("mergeSubmitUserName", map.get("SUBMIT_USER_NAME"));
				//创建日期
				dataMap.put("mergeCreateDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("mergeDescription", map.get("DESCRIPTION"));
				dataMap.put("mergeDisposalMeasures", map.get("DISPOSAL_MEASURES"));
				dataMap.put("mergeRootCause", map.get("ROOT_CAUSE"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,list.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getPartName() {
		try {
			if(StringUtils.isNotEmpty(partCode)){
				if(partCode.length()>7){
					partCode = partCode.substring(0, 7);
				}
			}
			List<Map<String, Object>> list = imsIssueService.getPartList(partCode);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(list != null){
				for (Map<String, Object> map : list) {
					// 零件名称
					dataMap.put("partName", map.get("PART_NAME"));
					break;
				}
			}
			this.writeSuccessResult(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getTodoListIMS() {
		try{
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String,Object>> list = imsIssueService.getTodoListIMS(userId);
			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("menuId", map.get("MENU_ID"));
				dataMap.put("displaySeq", map.get("DISPLAY_SEQ"));
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("text", map.get("TEXT"));
				dataMap.put("iconCls", map.get("ICON_CLS"));
				dataMap.put("xType", map.get("X_TYPE"));
				dataMap.put("widgetWidth", map.get("WIDGET_WIDTH"));
				dataMap.put("widgetType", map.get("WIDGET_TYPE"));
				dataMap.put("permission", map.get("PERMISSION"));

				data.add(dataMap);
			}
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);			
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
}
