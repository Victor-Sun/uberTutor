package com.gnomon.pdms.action.ims;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo03Action extends PDMSCrudActionSupport<GTIssueEntity> {

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
	
	private String ProReason;
	private String issueNatrue;
	private String reconfirmDate;
	private String tempAction;
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

	public String getTempAction() {
		return tempAction;
	}

	public void setTempAction(String tempAction) {
		this.tempAction = tempAction;
	}

	public String getProReason() {
		return ProReason;
	}

	public void setProReason(String proReason) {
		ProReason = proReason;
	}

	public String getIssueNatrue() {
		return issueNatrue;
	}

	public void setIssueNatrue(String issueNatrue) {
		this.issueNatrue = issueNatrue;
	}

	public String getReconfirmDate() {
		return reconfirmDate;
	}

	public void setReconfirmDate(String reconfirmDate) {
		this.reconfirmDate = reconfirmDate;
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
//	 * 质量问题管理-我的待办-问题确认-提交
//	 */
//	public String saveMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			
//			entity.setIsIssueReason(ProReason);									//理由
//			entity.setIssueNatureId(issueNatrue);								//问题性质
//			if(reconfirmDate != null && !("".equals(reconfirmDate))){
//				SimpleDateFormat Date = new SimpleDateFormat("yyyy/MM/dd");		//再次确认时间
//				Date date = Date.parse(reconfirmDate);
//				entity.setReconfirmDate(date);
//			}
//			entity.setTempAction(tempAction);									//临时措施
////			entity.setFormKey("040");                                           //表单标识
//			entity.setConfirmFinishDate(new Date());                            //问题确认完成时间
//			entity.setRootCauseStartDate(new Date());                           //原因对策开始时间
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			this.myTaskService.confirmIssue(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, issueNatrue);
//			
//			myTaskService.save(entity);
//			
//			List<VImsParticipationEntity> listP = 
//					this.myTaskService.getMyParticipation(keyId, userId);
//			
//			if(listP.size() != 0){
//				myTaskService.updateMyParticipation(keyId);
//			} else {
//				myTaskService.insertMyParticipation(keyId);
//			}
//			Struts2Utils.renderJson(result);
//			this.writeSuccessResult(null);
//
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 质量问题管理-我的待办-问题确认-回退
//	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
//            
//			this.myTaskService.returnIssue03(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
////			entity.setFormKey("020");                                           //表单标识
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//			
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
