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
public class MyTaskInfo02Action extends PDMSCrudActionSupport<GTIssueEntity> {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MyTaskService myTaskService;
	
	private GTIssueEntity gtIssue;
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}
	private String ResponEngineerId;
	private String Suggestion;
	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	
	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	public String getResponEngineerId() {
		return ResponEngineerId;
	}

	public void setResponEngineerId(String responEngineerId) {
		ResponEngineerId = responEngineerId;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
//	/**
//	 * 质量问题管理-我的待办-责任确认-提交
//	 */
//	public String saveMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//
//			entity.setOwner(ResponEngineerId);                                  //责任工程师
//			this.myTaskService.assignEngineer(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, ResponEngineerId);
////			entity.setFormKey("030");                                           //表单标识
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
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
//	 * 质量问题管理-我的待办-责任确认-回退
//	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
////			entity.setFormKey("010");                                            //表单标识
//			entity.setIssueStatusCode("ISSUE_STATUS_60");                       //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_OPEN");
//            
//			this.myTaskService.returnIssue02(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//
//			//保存变更记录
////			this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_61", "ISSUE_STATUS_60", userId);
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
