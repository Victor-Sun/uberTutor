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
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.service.ProjectPlanMajorNodeService;

@Namespace("/pm")
public class ProjectPlanMajorNodeAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private ProjectPlanMajorNodeService projectPlanMajorNodeService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}

	/**
	 * 主节点SOP时间节点信息取得
	 */
	public void getProjectPlanSOP() {
		try{
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> map =
					this.projectPlanMajorNodeService.getProjectPlanSOP(
							programVehicleId, baselineId);
			// 车型ID
			data.put("id", map.get("ID"));
			// 项目ID
			data.put("programId", map.get("PROGRAM_ID"));
			// 车型名称
			data.put("vehicleName", map.get("VEHICLE_NAME"));
			// 节点ID
			data.put("taskId", map.get("TASK_ID"));
			// 节点名称
			data.put("taskName", map.get("TASK_NAME"));
			// SOP日期
			data.put("plannedFinishDate", DateUtils.change(
					(Date)map.get("PLANNED_FINISH_DATE")));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 主节点信息取得
	 */
	public void getProjectPlanMajorNode() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			GTPage<Map<String, Object>> pageResult =
					this.projectPlanMajorNodeService.getProjectPlanMajorNode(
							programId, programVehicleId, baselineId,this.getPage(),this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 项目ID
				dataMap.put("programId", map.get("PROGRAM_ID"));
				// 节点名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 时间
				dataMap.put("plannedFinishDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 状态灯
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				// 所属车型ID
				dataMap.put("vehicleId", map.get("VEHICLE_ID"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 主节点 节点信息更新
	 */
	public void updateMainNode(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanMajorNodeService.updateMainNode(programVehicleId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 主节点 节点信息追加
	 */
	public void addMainNode(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanMajorNodeService.addMainNode(
					programId, programVehicleId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 主节点 节点信息删除
	 */
	public void deleteNode(){
		try{
			this.projectPlanMajorNodeService.deleteMainNode(programVehicleId, taskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 主节点 SOP节点信息更新
	 */
	public void updateSOP(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanMajorNodeService.updateSOP(programVehicleId, model);
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
	public TaskEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
