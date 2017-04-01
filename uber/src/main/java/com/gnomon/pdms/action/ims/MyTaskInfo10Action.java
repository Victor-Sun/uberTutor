package com.gnomon.pdms.action.ims;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.gnomon.pdms.entity.ImsIssueVerificationEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;
import com.gnomon.pdms.service.ImsIssueService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo10Action extends PDMSCrudActionSupport<GTIssueVO>{

	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String deptId;
	private String uid;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public MyTaskService getMyTaskService() {
		return myTaskService;
	}

	public void setMyTaskService(MyTaskService myTaskService) {
		this.myTaskService = myTaskService;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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
	private String keyId;

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
	
	private String model;
	
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 验证分配1
	 * @return
	 */
	public String saveMyTask() {	
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			List<ImsIssueVerificationEntity> list = 
					this.myTaskService.getVScheme(id);
			
			int i = 0;
			String [] arrUId = uid.split(", ");
			String [] arrDept = deptId.split(", ");
			ArrayList<String> arrDeptPmId = new ArrayList<String>();
			//专业经理
			for(int m = 0; m < list.size(); m++ ){
				
				int j = m + 1;
				arrDeptPmId.add(model.get("deptPmId-" + j));
			}
			
			//String departmentId = arrDept[0];
//			ArrayList<String> taskIdList = myTaskService.verifyAssignment1(SessionData.getUserId(), ProcessInstanceId, taskId, arrDeptPmId);
			
			for(ImsIssueVerificationEntity entity : list){
				
				entity = this.myTaskService.getVer(arrUId[i]);
				//验证部门
				entity.setDeptId(arrDept[i]);
				entity.setvFm(arrDeptPmId.get(i));
				entity.setSteps("001");
//				entity.setTaskId(taskIdList.get(i));
			
				myTaskService.save(entity);
				i++;
			}
			
			pkgImsDBProcedureServcie.updateIssue(gtIssueVO);
			this.writeSuccessResult(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 质量问题管理-我的待办-验证分配1-回退
	 */
//	public String fallbackMyTask(){
//		try{
//			JsonResult result = new JsonResult();
//			GTIssueEntity entity = 
//					this.myTaskService.getMyTaskResponConfirm(keyId); 
//            
//			this.myTaskService.returnIssue10(SessionData.getUserId(), ProcessInstanceId, taskId);
//			
//			String userId = "";
//			// 登录用户ID取得
//			userId = SessionData.getLoginUserId();
////			entity.setFormKey("050");                                           //表单标识
//			entity.setIssueStatusCode("ISSUE_STATUS_62");                       //问题状态
//			entity.setIssueLifecycleCode("ISSUE_LIFECYCLE_PENDING");
//			entity.setUpdateBy(userId);
//			entity.setUpdateDate(new Date());
//			
//			myTaskService.save(entity);
//			//保存变更记录
////			this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_63", "ISSUE_STATUS_62", userId);
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
		gtIssue = imsIssueService.get(id);
		gtIssueVO = new GTIssueVO();
//		BeanUtils.copyProperties(gtIssueVO, gtIssue);
		PropertyUtils.copyProperties(gtIssueVO, gtIssue);
		gtIssueVO.setAction("NEXT");
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
