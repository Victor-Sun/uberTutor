package com.gnomon.pdms.action.ims;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationVO;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.MyTaskService;
@Namespace("/ims")
public class MyTaskInfo06Action extends PDMSCrudActionSupport<ImsIssueVerificationEntity>{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private ImsIssueVerificationEntity imsIssueVerificationEntity;
	
	@Autowired
	private MyTaskService myTaskService;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;
	
	@Override
	public ImsIssueVerificationEntity getModel() {
		return imsIssueVerificationEntity;
	}

	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String formKey;
	private String verificationResult;
	private String uid;
	private String programVehicleId;
	private String vLineMgr;
	private String ownerComment;
	private String returnComment;

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}



	public String getVLineMgr() {
		return vLineMgr;
	}



	public String getReturnComment() {
		return returnComment;
	}



	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}



	public void setVLineMgr(String vLineMgr) {
		this.vLineMgr = vLineMgr;
	}



	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}



	

	public String getVerificationResult() {
		return verificationResult;
	}



	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}



	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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






	public String getOwnerComment() {
		return ownerComment;
	}



	public void setOwnerComment(String ownerComment) {
		this.ownerComment = ownerComment;
	}



	/**
	 * 质量问题管理-我的待办-效果验证-提交
	 */
	public String saveMyTask(){
		try{
			ImsIssueVerificationEntity entity = this.myTaskService.getVer(uid);
			ImsIssueVerificationVO imsIssueVerificationVO = new ImsIssueVerificationVO();
			PropertyUtils.copyProperties(imsIssueVerificationVO, entity);
			imsIssueVerificationVO.setAction("NEXT");
			imsIssueVerificationVO.setvLineMgr(vLineMgr);
			imsIssueVerificationVO.setOwnerComment(ownerComment);
			imsIssueVerificationVO.setVerificationResult(verificationResult);
			pkgImsDBProcedureServcie.updateImsVerification(imsIssueVerificationVO);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 质量问题管理-我的待办-效果验证-通过
	 */
	public String completeMyTask(){
		try{
			ImsIssueVerificationEntity entity = this.myTaskService.getVer(uid);
			ImsIssueVerificationVO imsIssueVerificationVO = new ImsIssueVerificationVO();
			PropertyUtils.copyProperties(imsIssueVerificationVO, entity);
			imsIssueVerificationVO.setAction("NEXT");
			imsIssueVerificationVO.setOwnerComment(ownerComment);
			pkgImsDBProcedureServcie.updateImsVerification(imsIssueVerificationVO);
			this.writeSuccessResult(null);

		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 质量问题管理-我的待办-效果验证-回退
	 */
	public String fallbackMyTask(){
		try{
			ImsIssueVerificationEntity entity = this.myTaskService.getVer(uid);
			ImsIssueVerificationVO imsIssueVerificationVO = new ImsIssueVerificationVO();
			PropertyUtils.copyProperties(imsIssueVerificationVO, entity);
			imsIssueVerificationVO.setAction("RETURN");
			imsIssueVerificationVO.setOwnerComment(returnComment);
			pkgImsDBProcedureServcie.updateImsVerification(imsIssueVerificationVO);
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
