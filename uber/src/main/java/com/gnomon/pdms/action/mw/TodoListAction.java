package com.gnomon.pdms.action.mw;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMProcessTaskVMEntity;
import com.gnomon.pdms.entity.PMTaskListVMEntity;
import com.gnomon.pdms.entity.PMTaskTimereportVMEntity;
import com.gnomon.pdms.entity.PMTaskTodoListVMEntity;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.procedure.PkgPmProcessDBProcedureServcie;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.SysNoticeService;
import com.gnomon.pdms.service.TodoListService;

@Namespace("/mw")
public class TodoListAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private TodoListService todoListService;
	
	@Autowired
	private SysNoticeService sysNoticeService;
		
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	PkgPmProcessDBProcedureServcie pkgPmProcessDBProcedureServcie;	

	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	private String remark;
	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String userTo;
	public void setUserTo(String userTo) {
		this.userTo = userTo;
	}

	private Long processId;
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	private Long processStepId;
	public void setProcessStepId(Long processStepId) {
		this.processStepId = processStepId;
	}
	
	private Long processTaskId;
	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}
	
	private String memo;
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String deliverableId;
	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}
	
	private File upload;
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	private String uploadFileName;
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String deliverableStatus;
	public void setDeliverableStatus(String deliverableStatus) {
		this.deliverableStatus = deliverableStatus;
	}
	
	private String progressStatus;
	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}

	/**
	 * 待办任务列表取得
	 */
	public void getTodoListPM() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.todoListService.getTodoList(searchModel, this.getPage(), this.getLimit(),this.getFilter(),this.getSort());
			for (Map<String, Object> map : list.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("taskId", map.get("TASK_ID"));
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				dataMap.put("attCnt", map.get("ATT_CNT"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("taskTypeName", PDMSCommon.getTaskTypeName(
						PDMSCommon.nvl(map.get("PROCESS_STEP_ID")),
						PDMSCommon.nvl(map.get("PROCESS_CODE"))));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("programVehicleCode", map.get("PROGRAM_VEHICLE_CODE"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("taskPriorityName", map.get("TASK_PRIORITY_NAME"));
				dataMap.put("taskStatusCode", map.get("TASK_STATUS_CODE"));
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				dataMap.put("delayDays", map.get("DAYS_DELAYED"));
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				dataMap.put("publishByName", map.get("PUBLISH_BY_NAME"));
				dataMap.put("publishDate", DateUtils.change((Date)map.get("PUBLISH_DATE")));
				dataMap.put("lastReportDate", DateUtils.formate((Date)map.get("LAST_REPORT_DATE"), "yyyy/MM/dd HH:mm:ss"));
				dataMap.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				dataMap.put("processStepId", map.get("PROCESS_STEP_ID"));
				dataMap.put("currentStepId", map.get("CURRENT_STEP_ID"));
				dataMap.put("processTaskOwner", map.get("PROCESS_TASK_OWNER"));
				dataMap.put("processId", map.get("PROCESS_ID"));
				dataMap.put("processCode", map.get("PROCESS_CODE"));
				dataMap.put("processTaskId", map.get("PROCESS_TASK_ID"));
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 待办任务表单信息取得
	 */
	public void getTodoTaskInfo() {
		try {
			JsonResult result = new JsonResult();
			PMTaskTodoListVMEntity entity =
					this.todoListService.getTodoTaskInfo(processTaskId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("taskProgressStatusCode", entity.getTaskProgressStatusCode());
			dataMap.put("programCode",entity.getProgramCode());
			dataMap.put("programId", entity.getProgramId());
			dataMap.put("obsName", entity.getObsName());
			dataMap.put("taskName", entity.getTaskName());
			dataMap.put("taskDescription", entity.getTaskDescription());
			dataMap.put("taskPriorityCode", entity.getTaskPriorityCode());
			dataMap.put("taskPriorityName", entity.getTaskPriorityName());
			dataMap.put("taskStatusCode", entity.getTaskStatusCode());
			dataMap.put("taskStatusName", entity.getTaskStatusName());
			dataMap.put("taskOwnerName", entity.getTaskOwnerName());
			dataMap.put("publishByName", entity.getPublishByName());
			dataMap.put("plannedStartDate",  DateUtils.change(entity.getPlannedStartDate()));			
			dataMap.put("plannedFinishDate",  DateUtils.change(entity.getPlannedFinishDate()));
			dataMap.put("actualStartDate",  DateUtils.change(entity.getActualStartDate()));			
			dataMap.put("actualFinishDate",  DateUtils.change(entity.getActualFinishDate()));
			dataMap.put("actualManhour", entity.getActualManhour());
			if(entity.getPercentComplete() != null){
				dataMap.put("percentComplete", entity.getPercentComplete().multiply(
						new BigDecimal("100")));
			}			
			dataMap.put("delayDays", entity.getDelayDays());
			dataMap.put("lastReportDate", DateUtils.change(entity.getLastReportDate()));
			dataMap.put("memo", entity.getMemo());
			dataMap.put("programVehicleName", entity.getProgramVehicleName());
			dataMap.put("professionalFieldName", entity.getProfessionalFieldName());
			dataMap.put("processCode", entity.getProcessCode());
			dataMap.put("processStepId", entity.getProcessStepId());
			dataMap.put("currentStepId", entity.getCurrentStepId());
			dataMap.put("processId", entity.getProcessId());
			dataMap.put("completeFlag", entity.getCompleteFlag());
			dataMap.put("deliverableId", entity.getDeliverableId());
			dataMap.put("deliverableStatus", entity.getDeliverableStatus());
			dataMap.put("processTaskProgressStatus", entity.getProcessTaskProgressStatus());

			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务交付物信息取得
	 */
	public void getDeliverableInfo() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.todoListService.getDeliverableList(taskId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("taskName", map.get("GATE_NAME"));
				dataMap.put("code", map.get("CODE"));
				dataMap.put("name", map.get("NAME"));
				dataMap.put("memo", map.get("MEMO"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务批示信息取得
	 */
	public void getCommentInfo() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PMProcessTaskVMEntity> list =
					this.todoListService.getCommentInfo(processId);
			for (PMProcessTaskVMEntity entity : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				String completeDate = DateUtils.formate(entity.getCompleteDate(), DateUtils.FORMAT_PATTEN_DATETIME);
				dataMap.put("id", entity.getId());
				dataMap.put("author", entity.getTaskOwnerName());
				dataMap.put("date", completeDate.substring(0, 10));
				dataMap.put("time", completeDate.substring(11));
				if (entity.getStepId() != null && entity.getStepId().intValue() > 1) {
					if (PDMSCommon.isNull(entity.getOwnerComment())) {
						dataMap.put("paragraph", entity.getReturnComment());
						dataMap.put("returnFlg", true);
						dataMap.put("resultIcon", "fa fa-remove");
						dataMap.put("resultMsg", "未通过");
					} else {
						dataMap.put("paragraph", entity.getOwnerComment());
						dataMap.put("returnFlg", false);
						dataMap.put("resultIcon", "fa fa-check");
						dataMap.put("resultMsg", "通过");
					}
				} else {
					// 提交者Comment
					dataMap.put("paragraph", entity.getOwnerComment());
				}
				dataMap.put("stepId", entity.getStepName());
				dataMap.put("roleId", entity.getRoleId());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务附件信息取得
	 */
	public void getAttachmentInfo() {

		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("fileName", "b11");
//			dataMap.put("uploadPerson", "李四");
//			dataMap.put("uploadDate", "2016/02/03");
//			data.add(dataMap);
//			
//			dataMap = new HashMap<String, Object>();
//			dataMap.put("fileName", "b12");
//			dataMap.put("uploadPerson", "王三");
//			dataMap.put("uploadDate", "2016/04/04");
//			data.add(dataMap);
			
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 待办任务申请变更（需会签专业组）
	 */
	public void getGridGroupInfo() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//			List<PMSwimlaneVMEntity> list =
//					this.todoListService.getGridGroupService();
//			for (PMSwimlaneVMEntity entity : list) {
//				Map<String, Object> dataMap = new HashMap<String, Object>();				
//				dataMap.put("id", entity.getId());
//				dataMap.put("name", entity.getObsName());
//				data.add(dataMap);
//			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务论区信息取得
	 */
	public void getTodoListPMMissionShow() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> dataMap = new HashMap<String, Object>();

			dataMap.put("task", "任务一");
			dataMap.put("planstart", "2016/02/01");
			dataMap.put("source", "广汽");
			
			dataMap.put("code", "A01");
			dataMap.put("planend", "2016/03/02");
			dataMap.put("organization", "汽一组");
			
			dataMap.put("taskname", "修改文件");
			dataMap.put("actualstart", "2016/02/01");
			dataMap.put("completionrate", "50%");
			
			dataMap.put("leader", "lisi");
			dataMap.put("actualend", "50%");
			
			dataMap.put("neirong", "确定任务内容");
			
			dataMap.put("pinglun", "留言");

			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 任务开始
	 */
	public void startTask() {
		try{
			Map<String, Object> taskInfo = this.todoListService.startTask(taskId);
			this.writeSuccessResult(taskInfo);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 任务完成
	 */
	public void completeTask() {
		try{
			// 提交审核
			String taskOwner = this.todoListService.completeTask(taskId, processId);
			// 发送通知
			this.sysNoticeService.taskSubmitNotify(taskId, taskOwner);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}	
	}
	
	/**
	 * 任务保存
	 */
	public void saveTask() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.todoListService.saveTask(taskId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 任务完成审核通过
	 */
	public void approve() {
		try{
			String taskOwner = this.todoListService.approveTask(
					taskId, processId, memo);
			// 发送通知
			this.sysNoticeService.taskSubmitNotify(taskId, taskOwner);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 任务完成审核不通过
	 */
	public void reject() {
		try{
			String taskOwner = this.todoListService.rejectTask(
					processStepId, processId, taskId, memo);
			// 发送通知
			this.sysNoticeService.taskRejectNotify(taskId, taskOwner);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务交付物备注保存
	 */
	public void saveDeliverable() {
		try {
			this.todoListService.saveDeliverable(taskId, deliverableId, memo);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更新进度信息取得
	 */
	public void getTimeReportInfo() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			List<PMTaskTimereportVMEntity> reportList =
					this.todoListService.getTimeReportInfo(taskId);
			PMTaskListVMEntity taskInfo = this.todoListService.getTaskInfo(taskId);
			if (reportList.size() > 0) {
				PMTaskTimereportVMEntity report = reportList.get(0);
				String fromDate = DateUtils.change(
						DateUtils.addDays(report.getCreateDate(), 1));
				String toDate = DateUtils.getCurrentDate();
				if (fromDate.compareTo(toDate) > 0) {
					fromDate = toDate;
				}
				dataMap.put("reportFrom", fromDate);
				dataMap.put("reportTo", toDate);
			} else {
				if (taskInfo.getActualStartDate() != null) {
					dataMap.put("reportFrom",
							DateUtils.change(taskInfo.getActualStartDate()));
				} else {
					dataMap.put("reportFrom", DateUtils.getCurrentDate());
				}
				dataMap.put("reportTo", DateUtils.getCurrentDate());
			}
			if (PDMSCommon.isNotNull(taskInfo.getTaskProgressStatusCode())) {
				dataMap.put("taskProgressStatusCode", taskInfo.getTaskProgressStatusCode());
			} else {
				dataMap.put("taskProgressStatusCode", PDMSConstants.TASK_PROGRESS_STATUS_G);
			}
			if (taskInfo.getPercentComplete() != null) {
				dataMap.put("percentComplete", taskInfo.getPercentComplete().multiply(
						new BigDecimal("100")));
			} else {
				dataMap.put("percentComplete", 0);
			}
			dataMap.put("manHours", 0);
			dataMap.put("remark", "");
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 保存更新进度
	 */
	public void saveTimeReport() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			Map<String, String> result = this.todoListService.saveTimeReport(taskId, model);
			// 发送通知
			this.sysNoticeService.taskProcessNotify(result.get("PROCESS_ID"),
					result.get("NEXT_USER"), "NEXT");
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更新进度列表信息取得
	 */
	public void getTimeReportList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PMTaskTimereportVMEntity> reportList =
					this.todoListService.getTimeReportInfo(taskId);
			for (PMTaskTimereportVMEntity entity : reportList) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				String createDate = DateUtils.formate(
						entity.getCreateDate(), DateUtils.FORMAT_PATTEN_DATETIME);
				dataMap.put("id", entity.getId());
//				dataMap.put("date", createDate.substring(0, 10));
				dataMap.put("time", createDate);
				// 备注
				dataMap.put("remark", entity.getRemark());
				// 工时
				dataMap.put("manHour", entity.getManHour());
				// 完成率
				dataMap.put("percentComplete", entity.getPercentComplete());
				// 进展状态
				dataMap.put("taskProgressStatusCode", entity.getTaskProgressStatusCode());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 任务延期变更
	 */
	public void extensionTask() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			String logId = this.todoListService.extensionTask(taskId, model);
			// 任务延期通知
			this.sysNoticeService.extensionTaskNotify(taskId);
			// 上传附件
			this.documentService.saveGTDocumentIndex(
					programId, PDMSConstants.SOURCE_TYPE_TASK_EXTENSION_EVIDENCE,
					logId, null, uploadFileName, null, upload,null);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * 延期变更记录列表信息取得
	 */
	public void getPmTaskExtensionLog() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> reportList =
					this.todoListService.getPmTaskExtensionLog(taskId);
			for (Map<String, Object> map : reportList) {
				String createDate = DateUtils.formate(
						(Date) map.get("CREATE_DATE"), DateUtils.FORMAT_PATTEN_DATETIME);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				String docmentIndexId = this.todoListService.getDocmentIndexId(
						PDMSCommon.nvl(map.get("ID")));
				// 获取PM_DOCUMENT_INDEX表ID
				dataMap.put("docmentIndexId", docmentIndexId);
				// 变更时间
				dataMap.put("time", createDate);
				// 备注
				dataMap.put("memo", map.get("MEMO"));
				// 延期天数
				dataMap.put("delayDays", map.get("DELAY_DAYS"));
				// 审批证据
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				// 文件ID
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 更改交付物状态（临时）
	 */
	public void changeDeliverableStatus() {
		try {
			this.todoListService.changeDeliverableStatus(
					processId, deliverableStatus);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 进展状态变更审核通过
	 */
	public void approveChangeStatus() {
		try{
			String taskOwner = this.todoListService.approveChangeStatus(
					taskId, processId, memo, progressStatus);
			// 发送通知
			this.sysNoticeService.taskProcessNotify(String.valueOf(processId),
					taskOwner, "NEXT");
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 进展状态变更审核不通过
	 */
	public void rejectChangeStatus() {
		try{
			String taskOwner = this.todoListService.rejectChangeStatus(
					processStepId, processId, taskId, memo, progressStatus);
			// 如果返回给申请人则流程结束，给申请人发送信息
			if (PDMSCommon.isNull(taskOwner)) {
				taskOwner = this.todoListService.getTaskInfo(taskId).getTaskOwner();
			}
			// 发送通知
			this.sysNoticeService.taskProcessNotify(String.valueOf(processId),
					taskOwner, "REJECT");
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 上传交付物附件CallBack
	 */
	public void uploadDeliverableFileCallBack() {
		try {
			this.todoListService.uploadDeliverableFileCallBack(taskId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除交付物附件CallBack
	 */
	public void deleteDeliverableFileCallBack() {
		try {
			this.todoListService.deleteDeliverableFileCallBack(taskId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 上传附件CallBack
	 */
	public void uploadAttachmentFileCallBack() {
		try {
			this.todoListService.uploadAttachmentFileCallBack(taskId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除附件CallBack
	 */
	public void deleteAttachmentFileCallBack() {
		try {
			this.todoListService.deleteAttachmentFileCallBack(taskId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	public void getTodoListExt() {
		try {
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			GTPage<Map<String, Object>> pageResult = this.todoListService.getTodoListExt(userId,this.getPage(),this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("taskId", map.get("ID"));
				dataMap.put("sourceName", map.get("SOURCE_NAME"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("taskType", map.get("TASK_TYPE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("stageName", map.get("STAGE_NAME"));
				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				dataMap.put("lifecycleStatusCode", map.get("LIFECYCLE_STATUS_CODE"));
//				dataMap.put("taskOwner", map.get("TASK_OWNER"));
//				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getTodoListOpenIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.todoListService.getTodoListOpenIssue(searchModel, this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("taskId", map.get("TASK_ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("issueUuid", map.get("ISSUE_UUID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("programCode", map.get("CODE"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("stepName", map.get("STEP_NAME"));
				dataMap.put("status", map.get("STATUS_CODE"));
				dataMap.put("statusName", map.get("STATUS_CODE_NAME"));
				dataMap.put("daysLate", map.get("DAYS_LATE"));
				dataMap.put("taskOwner", map.get("TASK_OWNER_NAME"));
				dataMap.put("stepId", map.get("STEP_ID"));
				dataMap.put("complete_date", DateUtils.change((Date)map.get("COMPLETE_DATE")));
				dataMap.put("raiseBy", map.get("RAISE_BY"));
				dataMap.put("issuePriorityName", map.get("ISSUE_PRIORITY_NAME"));
				// 进展状态
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				// 最近更新时间
				dataMap.put("lastUpdateDate", DateUtils.formate((Date)map.get("LAST_UPDATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
				// 计划完成时间
				dataMap.put("dueDate", DateUtils.change((Date)map.get("DUE_DATE")));
				// 问题来源
				dataMap.put("issueSourceTitle", map.get("ISSUE_SOURCE_TITLE"));
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getTodoListWorkOrder() {
		try {
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			GTPage<Map<String, Object>> pageResult =	this.todoListService.getTodoListWorkOrder(userId,this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("taskId", map.get("TASK_ID"));
				dataMap.put("workOrderId", map.get("WORK_ORDER_ID"));
				dataMap.put("woType", map.get("WO_TYPE"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("code", map.get("CODE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("stepName", map.get("STEP_NAME"));
				dataMap.put("processStatus", map.get("PROCESS_STATUS"));
				dataMap.put("daysLate", map.get("DAYS_LATE"));
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				dataMap.put("stepId", map.get("STEP_ID"));
				dataMap.put("formClassId", map.get("FORM_CLASS_ID"));
				if(map.get("DUE_DATE") != null){
					dataMap.put("dueDate", DateUtils.formate((Date)map.get("DUE_DATE")));
				}
				if(map.get("COMPLETE_DATE") != null){
					dataMap.put("complete_date", DateUtils.formate((Date)map.get("COMPLETE_DATE")));
				}
				dataMap.put("raiseBy", map.get("RAISE_BY"));
				dataMap.put("navImageName", map.get("NAV_IMAGE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得部门自建待办任务列表数据
	 */
	public void getTodoListDeptIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.todoListService.getTodoListDeptIssue(
							searchModel, this.getPage(), this.getLimit(),this.getFilter(),this.getSort());
			for (Map<String, Object> map : list.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ISSUE_ID"));
				// UUID
				dataMap.put("uuid", map.get("ISSUE_UUID"));
				// 问题标题
				dataMap.put("issueTitle", map.get("ISSUE_TITLE"));
				// 进度状态
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				// 提出日期
				dataMap.put("raiseDate", DateUtils.change((Date)map.get("RAISE_DATE")));
				// 偏离计划完成天数
				dataMap.put("daysDelayed", map.get("DAYS_DELAYED"));
				// 问题来源ID
				dataMap.put("issueSourceId", map.get("ISSUE_SOURCE_ID"));
				// 问题来源
				dataMap.put("issueSourceTitle", map.get("TITLE"));
				// 提出人ID
				dataMap.put("raiseBy", map.get("RAISE_BY"));
				// 提出人
				dataMap.put("raiseByName", map.get("RAISE_BY_NAME"));
				// 责任部门ID
				dataMap.put("respDeptId", map.get("RESP_DEPT_ID"));
				// 责任部门
				dataMap.put("respDeptName", map.get("RESP_DEPT_NAME"));
				// 责任人ID
				dataMap.put("respUserId", map.get("RESP_USER_ID"));
				// 责任人
				dataMap.put("respUserName", map.get("RESP_USER_NAME"));
				// 当前任务负责人
				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				dataMap.put("taskId", map.get("TASK_ID"));
				// 完成状态
				dataMap.put("statusCode", map.get("STATUS_CODE"));
				// 完成状态名称
				dataMap.put("statusCodeName", map.get("STATUS_CODE_NAME"));
				// 计划完成日期
				dataMap.put("dueDate", DateUtils.change((Date)map.get("DUE_DATE")));
				// 实际完成日期
				dataMap.put("actualFinishaDate", DateUtils.change((Date)map.get("ACTUAL_FINISH_DATE")));
				// 问题描述
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				// 备注
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				// 最近更新时间
				dataMap.put("updateDate", DateUtils.formate((Date)map.get("UPDATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
				dataMap.put("stepId", map.get("STEP_ID"));
				
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void transferTask() {
		try {
			List<String> taskList = new ArrayList<String>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				if(jsonArray.get(i) != null && !"null".equals(jsonArray.get(i).toString())){
					taskList.add(jsonArray.get(i).toString());
				}
			}
			String operatorId = SessionData.getLoginUserId();
			this.pkgPmProcessDBProcedureServcie.transferTask(taskList, remark, userTo, operatorId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
}

