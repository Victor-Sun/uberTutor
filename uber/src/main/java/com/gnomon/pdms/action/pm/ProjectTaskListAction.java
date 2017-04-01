package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMTaskListVMEntity;
import com.gnomon.pdms.service.DeptPlanService;
import com.gnomon.pdms.service.ProjectTaskListService;
import com.gnomon.pdms.service.TodoListService;

@Namespace("/pm")
public class ProjectTaskListAction extends PDMSCrudActionSupport<PMTaskListVMEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjectTaskListService projectTaskListService;
	
	@Autowired
	private DeptPlanService deptPlanService;
	
	@Autowired
	private TodoListService todoListService;

	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}

	/**
	 * 专业领域任务列表信息取得
	 */
	public void getProjectTaskList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.projectTaskListService.getProjectTaskList(
							programVehicleId, obsId, searchModel, this.getPage(), this.getLimit());
			
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 进展状态
				dataMap.put("taskProgressStatusCode",
						map.get("TASK_PROGRESS_STATUS_CODE"));
				// 任务名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 责任人
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 专业组
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 计划完成日期
				dataMap.put("plannedFinishDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 实际完成日期
				dataMap.put("actualFinishDate",
						DateUtils.change((Date)map.get("ACTUAL_FINISH_DATE")));
				// 最新更新时间
				dataMap.put("lastReportDate",
						DateUtils.change((Date)map.get("LAST_REPORT_DATE")));
				// 关联任务名称
				dataMap.put("rtName", map.get("RT_NAME"));
				// 关联任务所属专业领域
				dataMap.put("rtFunctionObsName", map.get("RT_FUNCTION_OBS_NAME"));
				// 关联任务所属专业组
				dataMap.put("rtObsName", map.get("RT_OBS_NAME"));
				// 前置节点信息
	 			List<Map<String, Object>> preTasklist =
	 					this.deptPlanService.getPreTaskList(PDMSCommon.nvl(map.get("ID")),
	 							null, PDMSConstants.PRE_TASK_TYPE_PRE);
	 			StringBuffer preTask = new StringBuffer();
	 			for (Map<String, Object> task : preTasklist) {
	 				preTask.append(task.get("PRE_TASK_NAME"));
	 				preTask.append("<br>");
	 			}
	 			dataMap.put("preTaskName", (preTask.length() > 0 ? preTask.substring(0, preTask.length() - 4) : ""));
				// 后置节点信息
	 			List<Map<String, Object>> postTasklist =
	 					this.deptPlanService.getPreTaskList(PDMSCommon.nvl(map.get("ID")),
	 							null, PDMSConstants.PRE_TASK_TYPE_POST);
	 			StringBuffer postTask = new StringBuffer();
	 			for (Map<String, Object> task : postTasklist) {
	 				postTask.append(task.get("PRE_TASK_NAME"));
	 				postTask.append("<br>");
	 			}
	 			dataMap.put("postTaskName", (postTask.length() > 0 ? postTask.substring(0, postTask.length() - 4) : ""));
	 			dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
	 			// 结果
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
	 * 甘特图数据取得
	 */
	public void getGantt(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("success", true);
			Map<String,Object> calendarsMap = new HashMap<String,Object>();
			Map<String,Object> metaDataMap = new HashMap<String,Object>();
			metaDataMap.put("projectCalendar", "General");
			calendarsMap.put("metaData", metaDataMap);
			List<Map<String,Object>> calendarsRowsData = new ArrayList<Map<String,Object>>();
			if(true){
				Map<String,Object> rowMap = new HashMap<String,Object>();
				rowMap.put("Id", "General");
				rowMap.put("Name", "General");
				rowMap.put("parentId", null);
				rowMap.put("DaysPerWeek", 7);
				rowMap.put("DaysPerMonth", 30);
				rowMap.put("HoursPerDay", 24);
				rowMap.put("WeekendFirstDay", 6);
				rowMap.put("WeekendSecondDay", 0);
				rowMap.put("WeekendsAreWorkdays", false);
				List<String> defaultAvailability = new ArrayList<String>();
				defaultAvailability.add("00:00-24:00");
				rowMap.put("DefaultAvailability", defaultAvailability);
				rowMap.put("leaf", true);
				List<Map<String,Object>> daysRowsData = new ArrayList<Map<String,Object>>();
				if(true){
					Map<String,Object> daysRowMap = new HashMap<String,Object>();
					daysRowMap.put("Id", 1);
					daysRowMap.put("Cls", "gnt-national-holiday");
					daysRowMap.put("Date", "2010-01-14");
					daysRowMap.put("Name", "Some big holiday");
					daysRowsData.add(daysRowMap);
				}
				if(true){
					Map<String,Object> daysRowMap = new HashMap<String,Object>();
					daysRowMap.put("Id", 2);
					daysRowMap.put("Cls", "gnt-chinese-holiday");
					daysRowMap.put("Date", "2010-02-14");
					daysRowMap.put("Name", "Chinese New Year");
					daysRowsData.add(daysRowMap);
				}
				rowMap.put("Days", daysRowsData);
				calendarsRowsData.add(rowMap);
			}
			
			if(true){
				Map<String,Object> rowMap = new HashMap<String,Object>();
				rowMap.put("Id", "NightShift");
				rowMap.put("Name", "Night Shift");
				rowMap.put("parentId", null);
				rowMap.put("DaysPerWeek", 5);
				rowMap.put("DaysPerMonth", 20);
				rowMap.put("HoursPerDay", 8);
				rowMap.put("WeekendFirstDay", 6);
				rowMap.put("WeekendSecondDay", 0);
				rowMap.put("WeekendsAreWorkdays", false);
				List<String> defaultAvailability = new ArrayList<String>();
				defaultAvailability.add("00:00-06:00");
				defaultAvailability.add("22:00-24:00");
				rowMap.put("DefaultAvailability", defaultAvailability);
				
				calendarsRowsData.add(rowMap);
			}
			calendarsMap.put("rows", calendarsRowsData);
			result.put("calendars", calendarsMap);
			
			List<Map<String, Object>> dependences = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> dependencelist = projectTaskListService.getPreTaskList(programVehicleId);
			for(Map<String, Object> map:dependencelist){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("From", map.get("PRE_TASK_ID"));
				dataMap.put("To", map.get("TASK_ID"));
				dependences.add(dataMap);
			}
			Map<String,Object> dependencesMap = new HashMap<String,Object>();
			dependencesMap.put("rows", dependences);
			result.put("dependencies", dependencesMap);
			
			List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> assignmentList = projectTaskListService.getTaskList(obsId);
			for(Map<String, Object> map:assignmentList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("TaskId", map.get("ID"));
				dataMap.put("ResourceId", map.get("TASK_OWNER"));
				dataMap.put("Units", 100);
				assignments.add(dataMap);
			}
			Map<String,Object> assignmentMap = new HashMap<String,Object>();
			assignmentMap.put("rows", assignments);
			result.put("assignments", assignmentMap);
			
			List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> resourcesList = projectTaskListService.getMemberList();
			for(Map<String, Object> map:resourcesList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("Id", map.get("USERID"));
//				dataMap.put("TaskId", map.get("ID"));
				dataMap.put("Name", map.get("USERNAME"));
//				dataMap.put("Units", 100);
				resources.add(dataMap);
			}
			Map<String,Object> resourcesMap = new HashMap<String,Object>();
			resourcesMap.put("rows", resources);
			result.put("resources", resourcesMap);
			
			Map<String,Object> taskMap = new HashMap<String,Object>();
			List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> taskList = projectTaskListService.getTaskList(obsId);
			for(Map<String, Object> map:taskList){
				Map<String, Object> task = this.setTaskMap(map);
//				this.getTaskTree(Long.valueOf(projectId), ((BigDecimal)map.get("ID")).longValue(), task);
				tasks.add(task);
			}
			taskMap.put("rows", tasks);
			result.put("tasks", taskMap);
//		result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> setTaskMap(Map<String, Object> map){
		Map<String,Object> taskMap = new HashMap<String,Object>();
		taskMap.put("BaselineEndDate", map.get("ID"));
		taskMap.put("Id", map.get("ID"));
		taskMap.put("Name", map.get("TASK_NAME"));
		taskMap.put("PercentDone", map.get("PERCENT_COMPLETE"));
		taskMap.put("StartDate", DateUtils.formate((Date)map.get("PLANNED_START_DATE")));
		taskMap.put("BaselineStartDate", DateUtils.formate((Date)map.get("PLANNED_START_DATE")));
		taskMap.put("Duration", map.get("DURATION_DAYS"));
//		taskMap.put("expanded", true);
		taskMap.put("leaf", this.getIsLeaf(map.get("IS_LEAF")));
		taskMap.put("functionObsName", map.get("FUNCTION_OBS_NAME"));
		taskMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
//		taskMap.put("Rollup", true);
		
		return taskMap;
	}
	
	private boolean getIsLeaf(Object isLeaf){
		if("Y".equals(isLeaf) || isLeaf == null){
			return true;
		}else{
			return false;
		}
	}
	
//	public void getTaskTree(Long projectId,Long parentId,Map<String,Object> parentTaskMap ) {
//		List<Map<String, Object>> list = projectTaskListService.getTaskList(obsId);
//		
//		if(list != null && list.size() > 0){
//			List<Map<String, Object>> tasklist = new ArrayList<Map<String, Object>>();
//			for(Map<String, Object> map:list){
//				Map<String, Object> taskMap = setTaskMap(map);
//				if(!((Boolean)taskMap.get("leaf") == true)){
//					getTaskTree(projectId,(Long)taskMap.get("ID"),taskMap);
//				}
//				
//				tasklist.add(taskMap);
//			}
//			parentTaskMap.put("children", tasklist);
//		}
//	}
	
	/**
	 * 计划延期记录列表取得
	 */
	public void getExtensionTaskList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.projectTaskListService.getExtensionTaskList(
							programVehicleId, obsId, searchModel, this.getPage(), this.getLimit());
			
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 任务名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 责任人
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 计划完成日期
				dataMap.put("plannedFinishDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 实际完成日期
				dataMap.put("actualFinishDate",
						DateUtils.change((Date)map.get("ACTUAL_FINISH_DATE")));
				// 延期天数
				dataMap.put("delayDays", map.get("DELAY_DAYS"));
				// 延期日期
				dataMap.put("createDate",
						DateUtils.change((Date)map.get("CREATE_DATE")));
				// 备注
				dataMap.put("memo", map.get("MEMO"));
				// 获取PM_DOCUMENT_INDEX表ID
				String docmentIndexId = this.todoListService.getDocmentIndexId(
						PDMSCommon.nvl(map.get("ID")));
				dataMap.put("docmentIndexId", docmentIndexId);
				// 审批证据
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				// 文件ID
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
	 			// 结果
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public PMTaskListVMEntity getModel() {
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