package com.gnomon.pdms.action.ims;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.GTIssueVO;
import com.gnomon.pdms.entity.ImsIssuePartEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.ImsIssueService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo04Action extends PDMSCrudActionSupport<GTIssueVO> {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;
	
	@Autowired
	private ImsIssueService imsIssueService;

	@Override
	public GTIssueVO getModel() {
		return gtIssueVO;
	}
	private GTIssueVO gtIssueVO;
	
	private GTIssueEntity gtIssue;
	
	private String id;
	
	private String method;
	
	private String partNumber;
	private String partNumberId;
	private String partName;
	private String partStatus;
	private String issueTypeId;
	private String expectedDate;
	private String Suggestion;
	private String uid;
	private String partId;
	private String PlanClosedDate;
	private String rootCause;
	private String isAction;
	private String permAction;
	private String verification;
	private String passRequest;
	
	private String lineMgrId04;
	private String sectionMgrId04;
//	private String vseId04;
	private String IsIssue;
	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String formKey;
	
	

	public void setPartNumberId(String partNumberId) {
		this.partNumberId = partNumberId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}




	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getPassRequest() {
		return passRequest;
	}

	public void setPassRequest(String passRequest) {
		this.passRequest = passRequest;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}

	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanClosedDate() {
		return PlanClosedDate;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPlanClosedDate(String planClosedDate) {
		PlanClosedDate = planClosedDate;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}



	public GTIssueVO getGtIssueVO() {
		return gtIssueVO;
	}

	public void setGtIssueVO(GTIssueVO gtIssueVO) {
		this.gtIssueVO = gtIssueVO;
	}

	public String getPartNumberId() {
		return partNumberId;
	}

	public MyTaskService getMyTaskService() {
		return myTaskService;
	}

	public void setMyTaskService(MyTaskService myTaskService) {
		this.myTaskService = myTaskService;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getIsIssue() {
		return IsIssue;
	}

	public void setIsIssue(String isIssue) {
		IsIssue = isIssue;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getIsAction() {
		return isAction;
	}

	public void setIsAction(String isAction) {
		this.isAction = isAction;
	}
	
	public String getPermAction() {
		return permAction;
	}

	public void setPermAction(String permAction) {
		this.permAction = permAction;
	}

	public String getLineMgrId04() {
		return lineMgrId04;
	}

	public void setLineMgrId04(String lineMgrId04) {
		this.lineMgrId04 = lineMgrId04;
	}

	public String getSectionMgrId04() {
		return sectionMgrId04;
	}

	public void setSectionMgrId04(String sectionMgrId04) {
		this.sectionMgrId04 = sectionMgrId04;
	}

//	public String getVseId04() {
//		return vseId04;
//	}
//
//	public void setVseId04(String vseId04) {
//		this.vseId04 = vseId04;
//	}



	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
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

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	/**
	 * 质量问题管理-我的待办-原因、对策方案制定-提交
	 */
	public String saveMyTask() {
		try {
			JsonResult result = new JsonResult();
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			String [] arrPart = partId.split(", ");
			String [] arrNUM = partNumber.split(", ");
//			String [] arrNUMId = partNumberId.split(", ");
			String [] arrNAME = partName.split(", ");
			String [] arrSTATUS = partStatus.split(", ");
			ImsIssuePartEntity entityp = new ImsIssuePartEntity();
			int f = 1;
			//保存零件
			for(int i = 0;i < arrNUM.length;i++){
				
				if(i < arrPart.length && arrPart.length != 0 && !("".equals(arrPart[i]))&&!("0".equals(arrPart[i]))){
					entityp = this.myTaskService.getPar(arrPart[i]);
					entityp.setId(arrPart[i]);
				} else {
					String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
					entityp.setId(uuid);
					entityp.setCreateBy(userId);
					entityp.setCreateDate(new Date());
				}
				entityp.setIssueId(id);
				entityp.setPartNumber(arrNUM[i]);                             //故障零件代号ID
//				entityp.setTroublePartNumber(arrNUM[i]);                        //故障零件代号
				entityp.setPartName(arrNAME[i]);                                //故障零件名称
				entityp.setPartStatus(arrSTATUS[i]);                            //故障零件状态
				entityp.setUpdateBy(userId);
				entityp.setUpdateDate(new Date());
				entityp.setSeq((long) (f));
				f++;
				myTaskService.save(entityp);
				entityp = new ImsIssuePartEntity();
			}
			if(passRequest != null){
				String [] arrR = passRequest.split(", ");
				String [] arrVS = verification.split(", ");
				String [] arrUId = uid.split(", ");
				ImsIssueVerificationEntity entityV = new ImsIssueVerificationEntity();
				int k = 1;
				//保存验证方案
				for(int j = 0;j < arrR.length;j++){
					if(j < arrUId.length && arrUId.length != 0 && !("".equals(arrUId[j]))){
						entityV = this.myTaskService.getVer(arrUId[j]);
						entityV.setId(arrUId[j]);
					} else {
						String uid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
						entityV.setId(uid);
						entityV.setCreateBy(userId);
						entityV.setCreateDate(new Date());
					}
					entityV.setVerification(arrVS[j]); 								//验证方案
					entityV.setPassRequest(arrR[j]);                             	//通过要求
					entityV.setIssueId(id);
					entityV.setUpdateBy(userId);
					entityV.setUpdateDate(new Date());
					entityV.setSeq((long) (k));
					k++;

					myTaskService.save(entityV);
					entityV = new ImsIssueVerificationEntity();
				}
			}
			
			pkgImsDBProcedureServcie.updateIssue(gtIssueVO);
			this.writeSuccessResult(null);

		} catch (Exception ex) {
			this.writeErrorResult(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

//	/**
//	 * 质量问题管理-我的待办-原因、对策方案制定-通过
//	 */
//	public String completeMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			
//			//表单标识
//			if("041".equals(formKey)){
////				entity.setFormKey("042");
//				this.myTaskService.dSReview(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("042".equals(formKey)){
////				entity.setFormKey("043");
//				this.myTaskService.dSApprove(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("043".equals(formKey)){
////				entity.setFormKey("050");
//				entity.setIssueStatusCode("ISSUE_STATUS_62");                   //问题状态
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
//				this.myTaskService.dSRecheck(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, isAction);
//				//保存变更记录
////				this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_61", "ISSUE_STATUS_62", userId);
//			}
//			
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//			
//			//我参与的问题list
//			List<VImsParticipationEntity> listP = 
//					this.myTaskService.getMyParticipation(keyId, userId);
//			
//			if(listP.size() != 0){
//				myTaskService.updateMyParticipation(keyId);
//			} else {
//				myTaskService.insertMyParticipation(keyId);
//			}
//			
//			Struts2Utils.renderJson(result);
//			this.writeSuccessResult(null);
//
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
	
//	/**
//	 * 质量问题管理-我的待办-原因、对策方案制定-回退
//	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
////			entity.setFormKey("040");
//			entity.setIssueStatusCode("ISSUE_STATUS_61");                   //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
//            
//			if("041".equals(formKey)){
//				this.myTaskService.dSReviewReturn(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("042".equals(formKey)){
//				this.myTaskService.dSApproveReturn(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("043".equals(formKey)){
//				this.myTaskService.dSRecheckReturn(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//			Struts2Utils.renderJson(result);
//			this.writeSuccessResult(null);
//
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
	//删除零件
	public String deletePart(){
		ImsIssuePartEntity entity= 
				this.myTaskService.deletePart(partId);
		if(entity != null){
			myTaskService.delete(entity);
		}
		return null;
	}
	//删除方案
	public String deletePlan(){
		ImsIssueVerificationEntity entity= 
				this.myTaskService.deletePlan(uid);
		if(entity != null){
			myTaskService.delete(entity);
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
//		String openDateStr = gtIssueVO.getOpenDateStr();
//		if(StringUtils.isNotEmpty(openDateStr)){
//			gtIssueVO.setOpenDate(DateUtils.strToDate(openDateStr));
//		}
//		String openDateStr = gtIssueVO.getOpenDateStr();
//		if(StringUtils.isNotEmpty(openDateStr)){
//			gtIssueVO.setOpenDate(DateUtils.strToDate(openDateStr));
//		}
	}
}
