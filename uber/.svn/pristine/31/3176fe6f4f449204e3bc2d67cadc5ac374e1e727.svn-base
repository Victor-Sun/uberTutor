package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMTaskVehicleVMEntity;
import com.gnomon.pdms.service.ProjectTimeLineService;

@Namespace("/pm")
public class ProjectTimeLineAction extends PDMSCrudActionSupport<PMTaskVehicleVMEntity> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ProjectTimeLineService projectTimeLineService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String mileStonesModel;
	public void setMileStonesModel(String mileStonesModel) {
		this.mileStonesModel = mileStonesModel;
	}
	
	private String invalidMileStonesModel;
	public void setInvalidMileStonesModel(String invalidMileStonesModel) {
		this.invalidMileStonesModel = invalidMileStonesModel;
	}
	
	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}
	
	private String planId;
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * 时程表Project数据取得
	 */
	public void getTimelineProject() {
		JsonResult result = new JsonResult();
		Map<String, Object> projectData =
				this.projectTimeLineService.getTimelineProjectData(
						vehicleId, baselineId);
		Map<String, Object> data = new HashMap<String, Object>();
		if (projectData.get("start") == null) {
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
			return;
		}
		// 项目开始结束时间
		data.put("start", DateUtils.change((Date)projectData.get("start")));
		data.put("end", DateUtils.change((Date)projectData.get("end")));
		// Stage
		List<Map<String,Object>> stageList = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("start", DateUtils.change((Date)projectData.get("start")));
		//dataMap.put("headerCls", "stage1");
		stageList.add(dataMap);
		data.put("stages", stageList);
		
		// 结果返回
		result.buildSuccessResult(data);
		Struts2Utils.renderJson(result);
	}
	
	/**
	 * Resources一览数据取得(主计划)
	 */
	public void getTimelineResources() {
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;
			//节点取得
			dataMap = new HashMap<String,Object>();
			dataMap.put("Id", "Major");
			dataMap.put("Name", "主节点");
			dataMap.put("expanded", false);
			dataMap.put("leaf", true);
			dataMap.put("Type", "M");
			data.add(dataMap);
			//阀门取得
			dataMap = new HashMap<String,Object>();
			dataMap.put("Id", "Gate");
			dataMap.put("Name", "质量阀");
			dataMap.put("expanded", false);
			dataMap.put("leaf", true);
			dataMap.put("Type", "G");
			data.add(dataMap);
			// 泳道取得
			List<Map<String, Object>> list =
					this.projectTimeLineService.getTimelineResources(
							vehicleId, planId, null, baselineId);
			for(Map<String, Object> map : list) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("Id", map.get("FUNCTION_ID"));
				dataMap.put("Name", map.get("DISPLAY_NAME"));
				dataMap.put("expanded", false);
				dataMap.put("leaf", true);
				dataMap.put("Type", "F");
				dataMap.put("SeqNo", map.get("SEQ_NO"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Events一览数据取得(主计划)
	 */
	public void getTimelineEvents() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			// 主节点信息取得
			Map<String, Object> mainNode =
					this.getMainNodeInfo(programId, vehicleId, baselineId);
			if (mainNode == null) {
				// 结果返回
				result.buildSuccessResultForList(data, 1);
				Struts2Utils.renderJson(result);
				return;
			}
			data.add(mainNode);
			
			//质量阀数据取得
			List<Map<String,Object>> valveList = this.getValveInfo(
					programId, vehicleId, baselineId);
			data.addAll(valveList);
			
			//泳道数据取得
			List<Map<String, Object>> list =
					this.projectTimeLineService.getTimelineResources(
							vehicleId, planId, null, baselineId);
			for (Map<String, Object> map : list) {
				// 泳道数据取得
				List<Map<String,Object>> swimlaneList = this.getSwimlaneData(
						PDMSCommon.nvl(map.get("FUNCTION_ID")), baselineId);
				data.addAll(swimlaneList);
			}
			
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Resources一览数据取得(二级计划)
	 */
	public void getTimelineResourcesDept() {
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			//节点取得
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("Id", "Major");
			dataMap.put("Name", "主节点");
			dataMap.put("expanded", false);
			dataMap.put("leaf", true);
			dataMap.put("Type", "M");
			data.add(dataMap);
			//阀门取得
			dataMap = new HashMap<String,Object>();
			dataMap.put("Id", "Gate");
			dataMap.put("Name", "质量阀");
			dataMap.put("expanded", false);
			dataMap.put("leaf", true);
			dataMap.put("Type", "G");
			data.add(dataMap);
//			// 一级计划泳道取得
//			List<Map<String, Object>> mainList =
//					this.projectTimeLineService.getTimelineResources(
//							vehicleId, planId, obsId, baselineId);
//			for(Map<String, Object> workMap : mainList) {
//				dataMap = new HashMap<String,Object>();
//				dataMap.put("Id", workMap.get("FUNCTION_ID"));
//				dataMap.put("Name", workMap.get("DISPLAY_NAME"));
//				dataMap.put("expanded", false);
//				dataMap.put("leaf", true);
//				dataMap.put("Type", "F");
//				dataMap.put("PlanLevel", PDMSConstants.PROGRAM_PLAN_LEVEL_1.toString());
//				data.add(dataMap);
//			}
			
			// 泳道取得
			List<Map<String, Object>> list =
				this.projectTimeLineService.getTimelineResources(
						vehicleId, planId, obsId, baselineId);
			for(Map<String, Object> workMap : list) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("Id", workMap.get("FUNCTION_ID"));
				dataMap.put("Name", workMap.get("DISPLAY_NAME"));
				dataMap.put("expanded", false);
				dataMap.put("leaf", true);
				dataMap.put("Type", "F");
				dataMap.put("SeqNo", workMap.get("SEQ_NO"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Events一览数据取得(二级计划)
	 */
	public void getTimelineEventsDept() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			// 主节点信息取得
			Map<String, Object> mainNode =
					this.getMainNodeInfo(programId, vehicleId, baselineId);
			if (mainNode == null) {
				// 结果返回
				result.buildSuccessResultForList(data, 1);
				Struts2Utils.renderJson(result);
				return;
			}
			data.add(mainNode);
			
			//质量阀数据取得
			List<Map<String,Object>> valveList = this.getValveInfo(
					programId, vehicleId, baselineId);
			data.addAll(valveList);
			
//			// 一级计划所设定泳道节点数据
//			List<Map<String, Object>> mainList =
//					this.projectTimeLineService.getTimelineResources(
//							programId, vehicleId, baselineId, obsId);
//			for(Map<String, Object> map : mainList) {
//				// 泳道数据取得
//				List<Map<String,Object>> swimlaneList = this.getSwimlaneData(
//						PDMSCommon.nvl(map.get("FUNCTION_ID")), baselineId);
//				data.addAll(swimlaneList);
//			}
			
			// 泳道数据取得
			List<Map<String, Object>> list =
					this.projectTimeLineService.getTimelineResources(
							vehicleId, planId, obsId, baselineId);
			for (Map<String, Object> map : list) {
				// 泳道数据取得
				List<Map<String,Object>> swimlaneList = this.getSwimlaneData(
						PDMSCommon.nvl(map.get("FUNCTION_ID")), baselineId);
				data.addAll(swimlaneList);
			}

			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 拖动时程表Event
	 */
	public void dragDropEvent() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			List<Map<String, String>> mileStonesModel =
					this.convertJson2List(this.mileStonesModel);
			this.projectTimeLineService.saveDragDropEvent(model, mileStonesModel);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 拖动时程表活动Event(拖动开始/结束时间)
	 */
	public void dragDropActivityEvent() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			List<Map<String, String>> mileStonesModel =
					this.convertJson2List(this.mileStonesModel);
			List<Map<String, String>> invalidMileStonesModel =
					this.convertJson2List(this.invalidMileStonesModel);
			this.projectTimeLineService.saveDragDropActivityEvent(
					model, mileStonesModel, invalidMileStonesModel);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 编辑程表Event
	 */
	public void editEvent() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectTimeLineService.saveEditEvent(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 编辑程表Resource
	 */
	public void editResource() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectTimeLineService.saveEditResource(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 主节点信息取得
	 */
	private Map<String,Object> getMainNodeInfo(
			String programId, String vehicleId, String baselineId) {
		// 返回结果定义
		Map<String, Object> result = null;
		// 主节点信息取得
		Map<String, Object> projectData =
				this.projectTimeLineService.getTimelineProjectData(
						vehicleId, baselineId);
		if (projectData.get("start") == null) {
			return result;
		}
		result = new HashMap<String,Object>();
		result.put("ResourceId", "Major");
		result.put("StartDate", DateUtils.formate((Date)projectData.get("start")));
		result.put("EndDate", DateUtils.formate((Date)projectData.get("end")));
		// 主节点数据取得
		List<Map<String,Object>> mainNodeList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> majorList =
				this.projectTimeLineService.getTimelineEventsMajor(
						programId, vehicleId, baselineId);
		for(Map<String, Object> major : majorList) {
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("TaskId", major.get("ID"));
			node.put("ProgramId", major.get("PROGRAM_ID"));
			node.put("ProgramVehicleId", major.get("VEHICLE_ID"));
			node.put("TaskType", major.get("TASK_TYPE_CODE"));
			node.put("TaskName", major.get("TASK_NAME"));
			node.put("TitleDispLocation", major.get("TITLE_DISP_LOCATION_CODE"));
			if (PDMSConstants.TITLE_DISP_LOCATION_UP.equals(major.get("TITLE_DISP_LOCATION_CODE"))) {
				node.put("LabelPos", "top");
			} else if (PDMSConstants.TITLE_DISP_LOCATION_DOWN.equals(major.get("TITLE_DISP_LOCATION_CODE"))) {
				node.put("LabelPos", "bottom");
			} else if (PDMSConstants.TITLE_DISP_LOCATION_RIGHT.equals(major.get("TITLE_DISP_LOCATION_CODE"))) {
				node.put("LabelPos", "right");
			} else {
				node.put("LabelPos", "left");
			}
			node.put("Date", DateUtils.formate((Date)major.get("PLANNED_FINISH_DATE")));
			node.put("Name", major.get("TASK_NAME"));
			node.put("ShortName", major.get("TASK_NAME"));
			if (PDMSConstants.TASK_TYPE_SOP_NODE.equals(major.get("TASK_TYPE_CODE"))) {
				node.put("Color", "red");
			} else {
				node.put("Color", "green");
			}
			mainNodeList.add(node);
		}
		result.put("MileStones", mainNodeList);
		// 返回结果
		return result;
	}
	
	/**
	 * 质量阀信息取得
	 */
	private List<Map<String, Object>> getValveInfo(String programId,
			String vehicleId, String baselineId) {
		// 返回结果定义
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> gateList =
				this.projectTimeLineService.getTimelineEventsGate(
						programId, vehicleId, baselineId);
		for(Map<String, Object> gate : gateList) {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("TaskId", gate.get("ID"));
			dataMap.put("ProgramId", gate.get("PROGRAM_ID"));
			dataMap.put("ProgramVehicleId", gate.get("VEHICLE_ID"));
			dataMap.put("TaskType", gate.get("TASK_TYPE_CODE"));
			dataMap.put("TaskName", gate.get("TASK_NAME"));
			dataMap.put("ResourceId", "Gate");
			dataMap.put("Name", gate.get("TASK_NAME"));
			dataMap.put("ShortName", gate.get("TASK_NAME"));
			dataMap.put("StartDate", DateUtils.formate((Date)gate.get("PLANNED_FINISH_DATE")));
			dataMap.put("EndDate", DateUtils.formate((Date)gate.get("PLANNED_FINISH_DATE")));
			result.add(dataMap);
		}
		return result;
	}
	
	/**
	 * 泳道数据取得
	 */
	private List<Map<String,Object>> getSwimlaneData(
			String functionId, String baselineId) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = null;
		
		// 泳道数据取得
		List<Map<String, Object>> deptTaskList =
				this.projectTimeLineService.getTimelineEventsObs(
						functionId, baselineId);
		
		// 添加活动
		for(Map<String, Object> map : deptTaskList) {
			if (! PDMSConstants.TASK_TYPE_ACTIVITY.equals(
					map.get("TASK_TYPE_CODE"))) {
				continue;
			}
			dataMap = new HashMap<String,Object>();
			dataMap.put("TaskId", map.get("ID"));
			dataMap.put("ProgramId", map.get("PROGRAM_ID"));
			dataMap.put("ProgramVehicleId", map.get("PROGRAM_VEHICLE_ID"));
			dataMap.put("ObsId", map.get("OBS_ID"));
			dataMap.put("PlanLevel", map.get("PLAN_LEVEL"));
			dataMap.put("TaskType", map.get("TASK_TYPE_CODE"));
			dataMap.put("TaskName", map.get("TASK_NAME"));
			dataMap.put("RelationTaskId", map.get("RELATION_TASK_ID"));
			dataMap.put("ParentTaskId", map.get("PARENT_ID"));
			dataMap.put("RelationTaskName", map.get("RELATION_TASK_NAME"));
			dataMap.put("ParentTaskName", map.get("PARENT_TASK_NAME"));
			dataMap.put("ResourceId", functionId);
			dataMap.put("Name", map.get("TASK_NAME"));
			dataMap.put("ShortName", map.get("TASK_NAME"));
			dataMap.put("Cls", "sch-node-text-black");
			dataMap.put("StartDate", DateUtils.formate((Date)map.get("PLANNED_START_DATE")));
			dataMap.put("EndDate", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
			result.add(dataMap);
		}
		
		// 添加节点
		for(Map<String, Object> map : deptTaskList) {
			if (! PDMSConstants.TASK_TYPE_NODE.equals(
					map.get("TASK_TYPE_CODE"))) {
				continue;
			}
			boolean isAdd = false;
			if (PDMSCommon.isNotNull(PDMSCommon.nvl(map.get("PARENT_ID")))) {
				for (Map<String,Object> workMap : result) {
					if (workMap.get("TaskId") != null &&
							workMap.get("TaskId").equals(map.get("PARENT_ID"))) {
						List<Map<String,Object>> mileStones =
								(List<Map<String,Object>>)workMap.get("MileStones");
						if (mileStones == null) {
							mileStones = new ArrayList<Map<String,Object>>();
						}
						dataMap = new HashMap<String,Object>();
						dataMap.put("TaskId", map.get("ID"));
						dataMap.put("ProgramId", map.get("PROGRAM_ID"));
						dataMap.put("ProgramVehicleId", map.get("PROGRAM_VEHICLE_ID"));
						dataMap.put("ObsId", map.get("OBS_ID"));
						dataMap.put("PlanLevel", map.get("PLAN_LEVEL"));
						dataMap.put("TaskType", map.get("TASK_TYPE_CODE"));
						dataMap.put("TaskName", map.get("TASK_NAME"));
						dataMap.put("RelationTaskId", map.get("RELATION_TASK_ID"));
						dataMap.put("ParentTaskId", map.get("PARENT_ID"));
						dataMap.put("RelationTaskName", map.get("RELATION_TASK_NAME"));
						dataMap.put("ParentTaskName", map.get("PARENT_TASK_NAME"));
						dataMap.put("TitleDispLocation", map.get("TITLE_DISP_LOCATION_CODE"));
						if (PDMSConstants.TITLE_DISP_LOCATION_UP.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
							dataMap.put("LabelPos", "top");
						} else if (PDMSConstants.TITLE_DISP_LOCATION_DOWN.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
							dataMap.put("LabelPos", "bottom");
						} else if (PDMSConstants.TITLE_DISP_LOCATION_RIGHT.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
							dataMap.put("LabelPos", "right");
						} else {
							dataMap.put("LabelPos", "left");
						}
						dataMap.put("Date", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
						dataMap.put("Name", map.get("TASK_NAME"));
						dataMap.put("ShortName", map.get("TASK_NAME"));
						dataMap.put("Color", map.get("TASK_PROGRESS_LIGHT"));
						mileStones.add(dataMap);
						workMap.put("MileStones", mileStones);
						isAdd = true;
						break;
					}
				}
			}
			if (! isAdd) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("TaskId", map.get("ID"));
				dataMap.put("ProgramId", map.get("PROGRAM_ID"));
				dataMap.put("ProgramVehicleId", map.get("PROGRAM_VEHICLE_ID"));
				dataMap.put("ObsId", map.get("OBS_ID"));
				dataMap.put("PlanLevel", map.get("PLAN_LEVEL"));
				dataMap.put("TaskType", map.get("TASK_TYPE_CODE"));
				dataMap.put("TaskName", map.get("TASK_NAME"));
				dataMap.put("RelationTaskId", map.get("RELATION_TASK_ID"));
				dataMap.put("ParentTaskId", map.get("PARENT_ID"));
				dataMap.put("RelationTaskName", map.get("RELATION_TASK_NAME"));
				dataMap.put("ParentTaskName", map.get("PARENT_TASK_NAME"));
				dataMap.put("TitleDispLocation", map.get("TITLE_DISP_LOCATION_CODE"));
				if (PDMSConstants.TITLE_DISP_LOCATION_UP.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
					dataMap.put("LabelPos", "top");
				} else if (PDMSConstants.TITLE_DISP_LOCATION_DOWN.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
					dataMap.put("LabelPos", "bottom");
				} else if (PDMSConstants.TITLE_DISP_LOCATION_RIGHT.equals(map.get("TITLE_DISP_LOCATION_CODE"))) {
					dataMap.put("LabelPos", "right");
				} else {
					dataMap.put("LabelPos", "left");
				}
				dataMap.put("ResourceId", functionId);
				dataMap.put("Name", map.get("TASK_NAME"));
				dataMap.put("ShortName", map.get("TASK_NAME"));
				dataMap.put("Color", map.get("TASK_PROGRESS_LIGHT"));
				dataMap.put("StartDate", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
				dataMap.put("EndDate", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
				result.add(dataMap);
			}
		}
		
		// 返回结果
		return result;
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
	public PMTaskVehicleVMEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
