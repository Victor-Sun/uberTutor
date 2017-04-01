package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsIssueApprovelogEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.service.ListingAreaService;
import com.gnomon.pdms.service.MyTaskService;


@Namespace("/ims")
public class MyTaskInfo01Action extends PDMSCrudActionSupport<GTIssueEntity> {

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
	
	private static final long serialVersionUID = 1L;
	
	private String RespDepartment;
	private String Qelevel;
	private String Suggestion;
	private String ReDepartment;
	private String DeptPmId;

	public void setDeptPmId(String deptPmId) {
		DeptPmId = deptPmId;
	}

	public String getReDepartment() {
		return ReDepartment;
	}

	public void setReDepartment(String reDepartment) {
		ReDepartment = reDepartment;
	}

	public String getRespDepartment() {
		return RespDepartment;
	}

	public void setRespDepartment(String respDepartment) {
		RespDepartment = respDepartment;
	}

	public String getQelevel() {
		return Qelevel;
	}

	public void setQelevel(String qelevel) {
		Qelevel = qelevel;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	private GTIssueEntity gtIssue;
	
	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private ListingAreaService listingAreaService;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String formKey;
	private String ProTitle;
	
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public void setProTitle(String proTitle) {
		ProTitle = proTitle;
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
//	 * 质量问题管理-我的待办-问题发布-回退
//	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			List<Map<String,Object>> formKeyList = new ArrayList<Map<String,Object>>();
//			String newFormKey = null;
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
//			if ("010".equals(formKey)) {
//				entity.setIssueStatusCode("ISSUE_STATUS_60");                       //问题状态
//				entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_OPEN");
////				entity.setFormKey("000");                                           //表单标识
//			} else {
//				formKeyList = this.listingAreaService.getListFormKey(keyId);
//				if (formKeyList.size() == 0) {
//					newFormKey = null;
//				} else {
//					for(Map<String, Object> map : formKeyList){
//						newFormKey = (String) map.get("FORM_KEY");
//					}
//				}
////				entity.setFormKey(newFormKey);                                           //表单标识
//			}			
//			
//			if (formKey.equals("010")) {
//				myTaskService.returnIssue(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			} else if (formKey.equals("011")) {
//				myTaskService.listIssueReviewReturn(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, newFormKey);
//			}
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
//	 * 质量问题管理-我的待办-问题发布-分配
//	 */
//	public String saveMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId);
//			String code = 
//					this.myTaskService.getMyTaskQeCode(keyId);
//
//			
//			if (formKey.equals("010")) {
//				entity.setCode(code);
//				entity.setDeptId(ReDepartment);
//				entity.setDeptPmId(DeptPmId);
//				entity.setIssueLevelCode(Qelevel);
//			    myTaskService.assignDepartment(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, DeptPmId);
////			} else if (formKey.equals("011")) {
////				String newTaskId = myTaskService.listIssueReview(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
////				this.listingAreaService.updateListing(newTaskId, keyId);
//			}
//			entity.setIssueStatusCode("ISSUE_STATUS_61");                       //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
////			entity.setFormKey("020");                                           //表单标识
//			entity.setPublishDate(new Date());                                  //问题发布时间
//			entity.setOpenDate(new Date());                                     //问题开启时间
//			entity.setConfirmStartDate(new Date());                             //问题确认开始时间
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			myTaskService.save(entity);
//			
//			ImsIssueApprovelogEntity entityA = null;
//			entityA = new ImsIssueApprovelogEntity();
//
//			String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
//			entityA.setId(uuid);
//			entityA.setIssueId(entity.getId());
//			entityA.setStepName(ProTitle);                                      //问题标题
//			entityA.setNote(Suggestion);                                        //意见
//			entityA.setApproveBy(userId);                      					//操作人
//			entityA.setApproveDate(new Date());
//			entityA.setTaskId(taskId);
//			entityA.setProcessInstanceId(ProcessInstanceId);
//			entityA.setCreateBy(userId);
//			entityA.setCreateDate(new Date());
//			entityA.setUpdateBy(userId);
//			entityA.setUpdateDate(new Date());
//			
//			myTaskService.save(entityA);
//			//保存变更记录
////			this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_60", "ISSUE_STATUS_61", userId);
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
	 * 质量问题管理-我的待办-问题发布-申请挂牌
	 */
	public String saveListingTask(){
		try{
			JsonResult result = new JsonResult();
			GTIssueEntity entity = 
					this.myTaskService.getMyTaskResponConfirm(keyId);
//			String code = 
//					this.myTaskService.getMyTaskQeCode(keyId);

//			entity.setCode(code);
//			if ("010".equals(formKey)) {
//				entity.setDeptId(ReDepartment);
//				if (PDMSCommon.isNotNull(DeptPmId)) {
//					entity.setDeptPmId(DeptPmId);
//				}
//				entity.setIssueLevelCode(Qelevel);
//			}
//			entity.setFormKey("011");                                           //表单标识
			
//			myTaskService.assignDepartment(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion, DeptPmId);
//			entity.setPublishDate(new Date());
			
			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();
			entity.setUpdateBy(userId);
			entity.setUpdateDate(new Date());
			myTaskService.save(entity);
			
//			ImsIssueApprovelogEntity entityA = null;
//			entityA = new ImsIssueApprovelogEntity();
//
//			String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
//			entityA.setId(uuid);
//			entityA.setIssueId(entity.getId());
//			entityA.setStepName(ProTitle);                                      //问题标题
//			entityA.setNote(Suggestion);                                        //意见
//			entityA.setApproveBy(userId);                      					//操作人
//			entityA.setApproveDate(new Date());
//			entityA.setTaskId(taskId);
//			entityA.setProcessInstanceId(ProcessInstanceId);
//			entityA.setCreateBy(userId);
//			entityA.setCreateDate(new Date());
//			entityA.setUpdateBy(userId);
//			entityA.setUpdateDate(new Date());
//			
//			myTaskService.save(entityA);
			
			//我参与的问题list
			List<VImsParticipationEntity> listP = 
					this.myTaskService.getMyParticipation(keyId, userId);
			
			if(listP.size() != 0){
				myTaskService.updateMyParticipation(keyId);
			} else {
				myTaskService.insertMyParticipation(keyId);
			}
			
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