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
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.TimeLineSettingService;

@Namespace("/pm")
public class TimeLineSettingAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public TimeLineSettingService timeLineSettingService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private String parentObsId;
	public void setParentObsId(String parentObsId) {
		this.parentObsId = parentObsId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String functionId;
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	private String childObsId;
	public void setChildObsId(String childObsId) {
		this.childObsId = childObsId;
	}
	
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private boolean displayFlg;
	public void setDisplayFlg(boolean displayFlg) {
		this.displayFlg = displayFlg;
	}

	/**
	 * 泳道一览取得
	 */
	public void getTimelineFunction() {
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;
			// 泳道取得
			List<Map<String, Object>> list =
					this.timeLineSettingService.getLaneList(vehicleId, parentObsId);
			for(Map<String, Object> map : list) {
				dataMap = new HashMap<String,Object>();
				// 泳道ID
				dataMap.put("id", map.get("FUNCTION_ID"));
				// 泳道名称
				dataMap.put("displayName", map.get("DISPLAY_NAME"));
				// 泳道表示顺序
				dataMap.put("seqNo", map.get("SEQ_NO"));
				// 关联组织ID
				dataMap.put("childObsId", map.get("CHILD_OBS_ID"));
				// 关联组织名称
				dataMap.put("childObsName", map.get("CHILD_OBS_NAME"));
				
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
	 * 添加泳道
	 */
	public void addLane() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.timeLineSettingService.insertLane(
					programId, vehicleId, parentObsId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 更新泳道
	 */
	public void updateLane() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.timeLineSettingService.updateLane(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 删除泳道
	 */
	public void deleteLane(){
		try{
			this.timeLineSettingService.deleteLane(functionId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 泳道任务一览取得
	 */
	public void getFunctionTask() {
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;
			// 泳道取得
			List<Map<String, Object>> list =
					this.timeLineSettingService.getFunctionTaskList(
							functionId, childObsId);
			for(Map<String, Object> map : list) {
				dataMap = new HashMap<String,Object>();
				// 任务ID
				dataMap.put("id", map.get("ID"));
				// 任务名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 任务开始时间
				dataMap.put("plannedStartDate",
						DateUtils.change((Date)map.get("PLANNED_START_DATE")));
				// 任务结束时间
				dataMap.put("plannedFinishDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 任务显示
				dataMap.put("displayFlg", map.get("FUNCTION_ID") == null ? false : true);
				
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
	 * 泳道任务信息更新
	 */
	public void updateFunctionTask() {
		try{
			this.timeLineSettingService.updateFunctionTask(
					functionId, taskId, displayFlg);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
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
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
