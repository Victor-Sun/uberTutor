package com.gnomon.pdms.action.mw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.entity.SysNoticeVMEntity;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMTaskTodoListVMEntity;
import com.gnomon.pdms.service.NotifyListService;

@Namespace("/mw")
public class NotifyListAction extends PDMSCrudActionSupport<SysNoticeVMEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private NotifyListService notifyListService;
	
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private String notifyId;
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	
	private boolean searchUnopen;
	public void setSearchUnopen(boolean searchUnopen) {
		this.searchUnopen = searchUnopen;
	}
	
	private boolean searchOpened;
	public void setSearchOpened(boolean searchOpened) {
		this.searchOpened = searchOpened;
	}

	private Long processId;
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	/**
	 * 通知提醒列表取得
	 */
	public void getNotifyList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =
					this.notifyListService.getNotifyListService(searchUnopen,
							searchOpened, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("noticeSourceCode", map.get("NOTICE_SOURCE_CODE"));
				dataMap.put("noticeSourceName", map.get("NOTICE_SOURCE_NAME"));
				dataMap.put("content", map.get("CONTENT"));
				dataMap.put("sourceId", map.get("SOURCE_ID"));
				dataMap.put("noticeTypeCode", map.get("NOTICE_TYPE_CODE"));
				dataMap.put("isNew", "Y".equals(map.get("IS_NEW")));
				if("Y".equals(map.get("IS_NEW"))){
					dataMap.put("isNewCls", "notify-icon-new new");
					dataMap.put("isNewClsOne", "notify-data-new");
				}else{
					dataMap.put("isNewCls", "notify-icon");
					dataMap.put("isNewClsOne", "notify-data");
				}
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, Long.valueOf(pageResult.getItemCount()).intValue());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 通知提醒列表件数取得
	 */
	public void getNotifyListCount() {
		try {
			JsonResult result = new JsonResult();				
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			dataMap.put("count", this.notifyListService.getNotifyListCount());
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 通知开封
	 */
	public void openNotification() {
		try {
			this.notifyListService.openNotification(notifyId);
			this.writeSuccessResult(null);
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
			// 登录用户取得
			String loginUser = SessionData.getLoginUserId();
			PMTaskTodoListVMEntity entity =
					this.notifyListService.getTodoTaskInfo(taskId, loginUser);
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
			dataMap.put("completionRate", entity.getPercentComplete());
			dataMap.put("delayDays", entity.getDelayDays());
			dataMap.put("lastReportDate", DateUtils.change(entity.getLastReportDate()));
			dataMap.put("memo", entity.getMemo());
			dataMap.put("programVehicleName", entity.getProgramVehicleName());
			dataMap.put("professionalFieldName", entity.getProfessionalFieldName());
			dataMap.put("id", entity.getId());
			dataMap.put("taskId", entity.getTaskId());
			dataMap.put("processStepId", entity.getProcessStepId());
			dataMap.put("currentStepId", entity.getCurrentStepId());
			dataMap.put("processTaskOwner", entity.getProcessTaskOwner());
			dataMap.put("processId", entity.getProcessId());
			dataMap.put("processCode", entity.getProcessCode());
			dataMap.put("deliverableId", entity.getDeliverableId());
			dataMap.put("deliverableStatus", entity.getDeliverableStatus());
			dataMap.put("processTaskProgressStatus", entity.getProcessTaskProgressStatus());
			dataMap.put("completeFlag", entity.getCompleteFlag());

			this.writeSuccessResult(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务表单信息取得（只读）
	 */
	public void getTodoTaskInfo4Readonly() {
		try {
			PMTaskTodoListVMEntity entity =
					this.notifyListService.getTodoTaskInfo(taskId, null);
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
			dataMap.put("completionRate", entity.getPercentComplete());
			dataMap.put("delayDays", entity.getDelayDays());
			dataMap.put("lastReportDate", DateUtils.change(entity.getLastReportDate()));
			dataMap.put("memo", entity.getMemo());
			dataMap.put("programVehicleName", entity.getProgramVehicleName());
			dataMap.put("professionalFieldName", entity.getProfessionalFieldName());
			dataMap.put("id", entity.getId());
			dataMap.put("taskId", entity.getTaskId());
			dataMap.put("processStepId", entity.getProcessStepId());
			dataMap.put("currentStepId", entity.getCurrentStepId());
			dataMap.put("processTaskOwner", entity.getProcessTaskOwner());
			dataMap.put("processId", entity.getProcessId());
			dataMap.put("processCode", entity.getProcessCode());
			dataMap.put("deliverableId", entity.getDeliverableId());
			dataMap.put("deliverableStatus", entity.getDeliverableStatus());
			dataMap.put("processTaskProgressStatus", entity.getProcessTaskProgressStatus());
			dataMap.put("completeFlag", entity.getCompleteFlag());

			this.writeSuccessResult(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 待办任务表单信息取得（状态变更流程）
	 */
	public void getTodoTaskInfo4Process() {
		try {
			PMTaskTodoListVMEntity entity =
					this.notifyListService.getTodoTaskInfo4Process(processId);
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
			dataMap.put("completionRate", entity.getPercentComplete());
			dataMap.put("delayDays", entity.getDelayDays());
			dataMap.put("lastReportDate", DateUtils.change(entity.getLastReportDate()));
			dataMap.put("memo", entity.getMemo());
			dataMap.put("programVehicleName", entity.getProgramVehicleName());
			dataMap.put("professionalFieldName", entity.getProfessionalFieldName());
			dataMap.put("id", entity.getId());
			dataMap.put("taskId", entity.getTaskId());
			dataMap.put("processStepId", entity.getProcessStepId());
			dataMap.put("currentStepId", entity.getCurrentStepId());
			dataMap.put("processTaskOwner", entity.getProcessTaskOwner());
			dataMap.put("processId", entity.getProcessId());
			dataMap.put("processCode", entity.getProcessCode());
			dataMap.put("deliverableId", entity.getDeliverableId());
			dataMap.put("deliverableStatus", entity.getDeliverableStatus());
			dataMap.put("processTaskProgressStatus", entity.getProcessTaskProgressStatus());
			dataMap.put("completeFlag", entity.getCompleteFlag());

			this.writeSuccessResult(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 标记为已读
	 */
	public void setOpened() {
		try {
			// JSON解析
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.notifyListService.setOpened(modelList);
			this.writeSuccessResult(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 批量删除
	 */
	public void deleteMails() {
		try {
			// JSON解析
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.notifyListService.deleteMails(modelList);
			this.writeSuccessResult(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 通知表单打开用Issue信息取得-部门OpenIssue
	 */
	public void getDeptIssueInfo() {
		try {
			Map<String, Object> issueMap =
					this.notifyListService.getDeptIssueInfo(issueId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// ID
			dataMap.put("id", issueMap.get("ISSUE_ID"));
			// UUID
			dataMap.put("uuid", issueMap.get("ISSUE_UUID"));
			// 完成状态
			dataMap.put("statusCode", issueMap.get("STATUS_CODE"));
			// STEP_ID
			dataMap.put("stepId", issueMap.get("STEP_ID"));

			this.writeSuccessResult(dataMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 通知表单打开用Issue信息取得-项目OpenIssue
	 */
	public void getPmIssueInfo() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> issueMap =
					this.notifyListService.getPmIssueInfo(issueId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			dataMap.put("id", issueMap.get("ISSUE_ID"));
			dataMap.put("programId", issueMap.get("PROGRAM_ID"));
			dataMap.put("status", issueMap.get("STATUS_CODE"));
			dataMap.put("issueUuid", issueMap.get("ISSUE_UUID"));
			dataMap.put("stepId", issueMap.get("STEP_ID"));
			// 结果返回
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public SysNoticeVMEntity getModel() {
		// TODO Auto-generated method stub
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