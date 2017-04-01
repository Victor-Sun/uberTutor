package com.gnomon.pdms.action.ims;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

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
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.ImsIssueService;
import com.gnomon.pdms.service.MyTaskService;


@Namespace("/ims")
public class MyTaskInfo05Action extends PDMSCrudActionSupport<GTIssueVO> {

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private static final long serialVersionUID = 1L;

	private ImsIssueVerificationEntity imsIssueVerificationEntity;
	
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
	
	private String implementVehicle;
	private String verificationScheme;
	private String request;
	private String uid;
	private String keyId;
	private String ProcessInstanceId;
	private String taskId;
	private String programVehicleId;

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public String getImplementVehicle() {
		return implementVehicle;
	}

	public void setImplementVehicle(String implementVehicle) {
		this.implementVehicle = implementVehicle;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public GTIssueVO getGtIssueVO() {
		return gtIssueVO;
	}

	public void setGtIssueVO(GTIssueVO gtIssueVO) {
		this.gtIssueVO = gtIssueVO;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVerificationScheme() {
		return verificationScheme;
	}

	public void setVerificationScheme(String verificationScheme) {
		this.verificationScheme = verificationScheme;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public ImsIssueVerificationEntity getImsIssueVerification() {
		return imsIssueVerificationEntity;
	}

	public void setGtIssue(ImsIssueVerificationEntity imsIssueVerificationEntity) {
		this.imsIssueVerificationEntity = imsIssueVerificationEntity;
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

	/**
	 * 质量问题管理-我的待办-实施措施-提交
	 */
	public String saveMyTask(){
		try{
			List<ImsIssueVerificationEntity> list = 
					this.myTaskService.getVScheme(id);
			
		
			int i = 0;
			String [] arrUId = uid.split(", ");
			String [] arrIV = implementVehicle.split(", ");

			for(ImsIssueVerificationEntity entity : list){
				
				entity = this.myTaskService.getVer(arrUId[i]);

				entity.setImplementVehicle(arrIV[i]);                           //实施车辆
			
				myTaskService.save(entity);
				i++;
			}
			
			pkgImsDBProcedureServcie.updateIssue(gtIssueVO);
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
