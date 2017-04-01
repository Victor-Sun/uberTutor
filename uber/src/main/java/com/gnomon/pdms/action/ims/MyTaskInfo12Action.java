package com.gnomon.pdms.action.ims;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo12Action extends PDMSCrudActionSupport<GTIssueEntity>{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private GTIssueEntity gtIssue;

	@Autowired
	private MyTaskService myTaskService;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}
	
	private String keyId;
	private String isUpdatedDfmea;
	private String isUpdatedTc;
	private String dfmeaNo;
	private String TechnicalStandardNo;
	private String otherMeasures;
	private String Suggestion;
	private String ProcessInstanceId;
	private String taskId;
	private String formKey;

	public String getIsUpdatedDfmea() {
		return isUpdatedDfmea;
	}

	public void setIsUpdatedDfmea(String isUpdatedDfmea) {
		this.isUpdatedDfmea = isUpdatedDfmea;
	}

	public String getIsUpdatedTc() {
		return isUpdatedTc;
	}

	public void setIsUpdatedTc(String isUpdatedTc) {
		this.isUpdatedTc = isUpdatedTc;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getDfmeaNo() {
		return dfmeaNo;
	}

	public void setDfmeaNo(String dfmeaNo) {
		this.dfmeaNo = dfmeaNo;
	}

	public String getTechnicalStandardNo() {
		return TechnicalStandardNo;
	}

	public void setTechnicalStandardNo(String technicalStandardNo) {
		TechnicalStandardNo = technicalStandardNo;
	}

	public String getOtherMeasures() {
		return otherMeasures;
	}

	public void setOtherMeasures(String otherMeasures) {
		this.otherMeasures = otherMeasures;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
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
	 * 质量问题管理-再发防止-提交
	 */
//	public String saveMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			
//			if("121".equals(formKey)){
//				entity.setIsUpdatedDfmea(isUpdatedDfmea);
//				entity.setIsUpdatedTc(isUpdatedTc);
//				entity.setDfmeaNo(dfmeaNo);
//				entity.setTechnicalStandardNo(TechnicalStandardNo);
//				entity.setOtherMeasures(otherMeasures);
////				entity.setFormKey("122");
//				myTaskService.defectPrevention(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}
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
	/**
	 * 质量问题管理-我的待办-再发防止确认-通过
	 */
//	public String completeMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			
//			if("122".equals(formKey)){
//				entity.setIssueStatusCode("ISSUE_STATUS_66");                   //问题状态
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_CLOSED");
//				myTaskService.defectPreventionConfirm(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//				//保存变更记录
////				this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_65", "ISSUE_STATUS_66", userId);
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
	/**
	 * 质量问题管理-我的待办-再发防止确认-回退
	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
//            					
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			if ("122".equals(formKey)) {                                        //表单标识
//				this.myTaskService.defectPreventionConfirmNo(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}
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
