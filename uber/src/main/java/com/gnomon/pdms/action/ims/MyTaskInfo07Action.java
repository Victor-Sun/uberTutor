package com.gnomon.pdms.action.ims;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationVO;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.MyTaskService;
@Namespace("/ims")
public class MyTaskInfo07Action extends PDMSCrudActionSupport<GTIssueEntity> {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private GTIssueEntity gtIssue;

	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;

	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String effectConfirmation;
	private String reason;
	private String ecoNo;
	private String ownerComment;
	private String Suggestion;
	private String uid;
	private String ResDepartmentId;
	private String lineMgrId07;
	private String sectionMgrId07;
//	private String vseId07;
	private String formKey;

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getResDepartmentId() {
		return ResDepartmentId;
	}

	public String getOwnerComment() {
		return ownerComment;
	}

	public void setOwnerComment(String ownerComment) {
		this.ownerComment = ownerComment;
	}

	public void setResDepartmentId(String resDepartmentId) {
		ResDepartmentId = resDepartmentId;
	}



	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	public String getEffectConfirmation() {
		return effectConfirmation;
	}

	public void setEffectConfirmation(String effectConfirmation) {
		this.effectConfirmation = effectConfirmation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEcoNo() {
		return ecoNo;
	}

	public void setEcoNo(String ecoNo) {
		this.ecoNo = ecoNo;
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

	public String getLineMgrId07() {
		return lineMgrId07;
	}

	public void setLineMgrId07(String lineMgrId07) {
		this.lineMgrId07 = lineMgrId07;
	}

	public String getSectionMgrId07() {
		return sectionMgrId07;
	}

	public void setSectionMgrId07(String sectionMgrId07) {
		this.sectionMgrId07 = sectionMgrId07;
	}

//	public String getVseId07() {
//		return vseId07;
//	}
//
//	public void setVseId07(String vseId07) {
//		this.vseId07 = vseId07;
//	}

	/**
	 * 质量问题管理-我的待办-效果确认-申请关闭
	 */
	public String saveMyTask() {
		try {ImsIssueVerificationEntity entity = this.myTaskService.getVer(uid);
		ImsIssueVerificationVO imsIssueVerificationVO = new ImsIssueVerificationVO();
		PropertyUtils.copyProperties(imsIssueVerificationVO, entity);
		imsIssueVerificationVO.setAction("NEXT");
		imsIssueVerificationVO.setEffectConfirmation(effectConfirmation);
		imsIssueVerificationVO.setReason(reason);
		imsIssueVerificationVO.setEcoNo(ecoNo);
		imsIssueVerificationVO.setOwnerComment(ownerComment);
		pkgImsDBProcedureServcie.updateImsVerification(imsIssueVerificationVO );
		this.writeSuccessResult(null);} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
//	/**
//	 * 质量问题管理-我的待办-效果确认-验证NG
//	 */
//	public String typeMyTask() {
//		try {
//			JsonResult result = new JsonResult();
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
//			
////			entity.setFormKey("040");                                           //表单标识
//			entity.setIssueStatusCode("ISSUE_STATUS_61");                       //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
//			
//			myTaskService.confirmEffectNG(SessionData.getUserId(), ProcessInstanceId, taskId, Suggestion);
//			
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
//		} catch (Exception ex) {
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
