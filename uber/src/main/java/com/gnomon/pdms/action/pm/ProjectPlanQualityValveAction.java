package com.gnomon.pdms.action.pm;

import java.math.BigDecimal;
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
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.ProjectPlanQualityValveService;

@Namespace("/pm")
public class ProjectPlanQualityValveAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private ProjectPlanQualityValveService projectPlanQualityValveService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	public String deliverableId;	
	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}
	
	public boolean isFit;
	public void setIsFit(boolean isFit) {
		this.isFit = isFit;
	}
	
	public boolean isKey;
	public void setIsKey(boolean isKey) {
		this.isKey = isKey;
	}

	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}
	
	private String notFitReason;
	public void setNotFitReason(String notFitReason) {
		this.notFitReason = notFitReason;
	}

	/**
	 * 质量阀信息一览取得
	 */
	public void getProjectPlanQualityValve() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectPlanQualityValveService.getProjectPlanQualityValve(
							programId, programVehicleId, baselineId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 阀门名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 时间
				dataMap.put("plannedFinishDate", DateUtils.change(
						(Date)map.get("PLANNED_FINISH_DATE")));
				// 交付物完成情况(1)
				BigDecimal totalCount = new BigDecimal(PDMSCommon.nvl(map.get("TOTAL_COUNT")));
				BigDecimal completeCount = new BigDecimal(PDMSCommon.nvl(map.get("COMPLETE_COUNT")));
				float percent = 0;
				if (totalCount.intValue() != 0) {
					percent = completeCount.divide(totalCount, 2, BigDecimal.ROUND_CEILING).floatValue();
				}
				dataMap.put("percent", percent);
				// 交付物完成情况(2)
				dataMap.put("count", completeCount.toString() + "/" + totalCount.toString());
				// 阀门状态灯
				dataMap.put("gateStatusCode", map.get("GATE_STATUS_CODE"));
				// 所属车型ID
				dataMap.put("vehicleId", map.get("VEHICLE_ID"));

				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 阀门交付物一览信息取得
	 */
	public void getGateDeliverable() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectPlanQualityValveService.getGateDeliverableInfo(
							taskId, baselineId);
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 编码
				dataMap.put("code", map.get("CODE"));
				// 名称
				dataMap.put("name", map.get("NAME"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 负责人
				dataMap.put("username", map.get("USERNAME"));
				// 状态灯
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				// 质量灯
				dataMap.put("delvQualityStatusCode", map.get("DELV_QUALITY_STATUS_CODE"));
				// 截止日期
				dataMap.put("submitDate", DateUtils.change((Date)map.get("SUBMIT_DATE")));
				// 是否适用本项目
				if (PDMSConstants.STATUS_Y.equals(map.get("IS_FIT"))) {
					dataMap.put("isFit", true);
				} else {
					dataMap.put("isFit", false);
				}
				// 是否是关键交付物
				if (PDMSConstants.STATUS_Y.equals(map.get("IS_KEY"))) {
					dataMap.put("isKey", true);
				} else {
					dataMap.put("isKey", false);
				}
				// 不适用原因
				dataMap.put("notFitReason", map.get("NOT_FIT_REASON"));
				// 关联任务
				dataMap.put("refTaskId", map.get("REF_TASK_ID"));
				// 标准交付物
				dataMap.put("isStandard", map.get("IS_STANDARD"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 质量阀信息一览更新
	 */
	public void updateProjectPlanQualityValve(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanQualityValveService.updateProjectPlanQualityValve(
					programVehicleId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 质量阀信息一览新增
	 */
	public void addProjectPlanQualityValve(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanQualityValveService.insertProjectPlanQualityValve(
					programId, programVehicleId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 质量阀删除
	 */
	public void deleteProjectPlanQualityValve(){
		try{
			this.projectPlanQualityValveService.deleteProjectPlanQualityValve(
					programVehicleId, taskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
//	/**
//	 * 阀门交付物一览信息更新
//	 */
//	public void updateDeliverable(){
//		try{
//			Map<String, String> model = this.convertJson(this.model);
//			this.projectPlanQualityValveService.updateDeliverable(model);
//			this.writeSuccessResult(null);
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			this.writeErrorResult(ex.getMessage());
//		}
//	}
	
	/**
	 * 更新交付物是否适用
	 */
	public void updateDeliverableFit(){
		try{
			this.projectPlanQualityValveService.updateDeliverableFit(
					programVehicleId, deliverableId, isFit, notFitReason);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 更新交付物是否为关键交付物
	 */
	public void updateDeliverableKey(){
		try{
			this.projectPlanQualityValveService.updateDeliverableKey(
					deliverableId, isKey);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
//	/**
//	 * 阀门交付物一览信息新增
//	 */
//	public void addDeliverable(){
//		try{
//			Map<String, String> model = this.convertJson(this.model);
//			this.projectPlanQualityValveService.insertDeliverable(taskId, model);
//			this.writeSuccessResult(null);
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			this.writeErrorResult(ex.getMessage());
//		}
//	}
	
	/**
	 * 质量阀交付物删除
	 */
	public void deleteDeliverable(){
		try{
			this.projectPlanQualityValveService.deleteDeliverable(
					programVehicleId, deliverableId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 阀门交付物负责主体更新
	 */
	public void updateDeliverableObs(){
		try{
			this.projectPlanQualityValveService.updateDeliverableObs(
					deliverableId, obsId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
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
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}