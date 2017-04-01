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
public class MyTaskInfo08Action extends PDMSCrudActionSupport<GTIssueEntity>{

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
	private String isDp;
	private String Suggestion;
	private String formKey;
	
	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}

	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	public MyTaskService getMyTaskService() {
		return myTaskService;
	}

	public void setMyTaskService(MyTaskService myTaskService) {
		this.myTaskService = myTaskService;
	}

	public String getIsDp() {
		return isDp;
	}

	public void setIsDp(String isDp) {
		this.isDp = isDp;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	private String ProcessInstanceId;
	private String taskId;

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

//	/**
//	 * 质量问题管理-我的待办-关闭审核-同意关闭
//	 */
//	public String saveMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			if("080".equals(formKey)){
//				//关闭批准
////				entity.setFormKey("081");
//				myTaskService.closeAudit(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("081".equals(formKey)){
//				//关闭确认
////				entity.setFormKey("082");
//				myTaskService.closeAuditApprove(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("082".equals(formKey)){
//				//再发防止
////				entity.setFormKey("121");
//				entity.setIssueStatusCode("ISSUE_STATUS_65");                   //问题状态
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_CLOSED");
//				entity.setCloseReqActualDate(new Date());                       //问题关闭时间
//				myTaskService.closeAuditConfirm(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, isDp);
//				//保存变更记录
////				this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_64", "ISSUE_STATUS_65", userId);
//			}
//			entity.setIsDp(isDp);
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
	 * 质量问题管理-我的待办-关闭审核-不同意关闭
	 */
//	public String closeMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			entity.setIssueStatusCode("ISSUE_STATUS_61");                   //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
////			entity.setFormKey("040");                                       //表单标识
//
//			if("080".equals(formKey)){
//				
//				myTaskService.closeAuditNo(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("081".equals(formKey)){
//				
//				myTaskService.closeAuditApproveNo(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}else if("082".equals(formKey)){
//				
//				myTaskService.closeAuditConfirmNo(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			}
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//			//保存变更记录
////			this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_64", "ISSUE_STATUS_61", userId);
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
