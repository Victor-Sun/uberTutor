package com.gnomon.pdms.action.ims;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.GTIssueVO;
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.ImsIssueVerificationVO;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.ImsIssueService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo11Action extends PDMSCrudActionSupport<GTIssueVO>{
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private GTIssueEntity gtIssue;

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
	
	private String id;
	
	private String method;

	public GTIssueEntity getGtIssue() {
		return gtIssue;
	}

	public void setGtIssue(GTIssueEntity gtIssue) {
		this.gtIssue = gtIssue;
	}

	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String verificationEngineer;
	private String uid; 


	public GTIssueVO getGtIssueVO() {
		return gtIssueVO;
	}

	public void setGtIssueVO(GTIssueVO gtIssueVO) {
		this.gtIssueVO = gtIssueVO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVerificationEngineer() {
		return verificationEngineer;
	}

	public void setVerificationEngineer(String verificationEngineer) {
		this.verificationEngineer = verificationEngineer;
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

	/**
	 * 验证分配2
	 * @return
	 */
	public String saveMyTask() {
		try {
			ImsIssueVerificationEntity entity = this.myTaskService.getVer(uid);

			ImsIssueVerificationVO imsIssueVerificationVO = new ImsIssueVerificationVO();
			PropertyUtils.copyProperties(imsIssueVerificationVO, entity);
			imsIssueVerificationVO.setVerificationEngineer(verificationEngineer);
			imsIssueVerificationVO.setSteps("002");
			imsIssueVerificationVO.setAction("NEXT");
			
			pkgImsDBProcedureServcie.updateImsVerification(imsIssueVerificationVO );
			this.writeSuccessResult(null);

		} catch (Exception ex) {
			this.writeErrorResult(ex.getMessage());
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
