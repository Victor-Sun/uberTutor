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
import com.gnomon.pdms.entity.DeliverableGateVMEntity;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.ProjectPlanQualityValveService;
import com.gnomon.pdms.service.ProjectValveDocumentService;

@Namespace("/pm")
public class ProjectValveDocumentAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private ProjectValveDocumentService projectValveDocumentService;
	
	@Autowired
	private ProjectPlanQualityValveService projectPlanQualityValveService;
	
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
	private String gateStatus;
	public void setGateStatus(String gateStatus) {
		this.gateStatus = gateStatus;
	}

	private String deliverableId;
	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	/**
	 * 阀门一览数据取得
	 */
	public void getProjectPlanQualityValve() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectPlanQualityValveService.getProjectPlanQualityValve(
							programId, programVehicleId, null);
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 阀门名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 阀门状态灯
				dataMap.put("gateStatusCode", map.get("GATE_STATUS_CODE"));
				// 阀门截止时间
				dataMap.put("plannedFinishDate", DateUtils.change(
						(Date)map.get("PLANNED_FINISH_DATE")));
				// 阀门通过状态
				dataMap.put("isGatePass",
						PDMSConstants.STATUS_Y.equals(map.get("IS_GATE_PASS")));
				
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
	 * 阀门交付物取得
	 */
	public void getProjectValveDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectValveDocumentService.getProjectValveDocument(taskId);
			List<String> deliverableIdList = new ArrayList<String>();
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				if (deliverableIdList.indexOf(PDMSCommon.nvl(map.get("ID"))) >= 0) {
					continue;
				}
				deliverableIdList.add(PDMSCommon.nvl(map.get("ID")));
				// ID
				dataMap.put("id", map.get("ID"));
				// 交付物编码
				dataMap.put("code", map.get("CODE"));
				// 交付物名称
				dataMap.put("name", map.get("NAME"));
				// 负责主体ID
				dataMap.put("obsId", map.get("OBS_ID"));				
				// 负责主体名称
				dataMap.put("obsName", map.get("OBS_NAME"));
				if (PDMSCommon.isNull(PDMSCommon.nvl(map.get("OBS_NAME")))) {
					dataMap.put("obsName", "未指定负责主体的交付物");
				}
				// 状态灯
				dataMap.put("progress", map.get("DELIVERABLE_PROGRESS_STATUS"));
				// 交付物文件件数
				dataMap.put("attcnt", map.get("ATT_CNT"));
				// 关联任务ID
				dataMap.put("refTaskId", map.get("REF_TASK_ID"));
				// 关键交付物
				dataMap.put("isKey", map.get("IS_KEY"));
				// 适用交付物
				dataMap.put("isFit", map.get("IS_FIT"));
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
	 * 阀门交付物关联任务列表取得
	 */
	public void getProjectValveDocumentTask() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectValveDocumentService.getProjectValveDocumentTask(
							deliverableId);
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// 任务ID
				dataMap.put("id", map.get("REF_TASK_ID"));
				// 任务名称
				dataMap.put("refTaskName", map.get("REF_TASK_NAME"));
				// 所属专业领域
				dataMap.put("taskFnGroupName", map.get("TASK_FN_GROUP_NAME"));
				// 任务状态
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				// 任务进展状态
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
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
	 * 阀门交付物详细信息取得
	 */
	public void getDeliverableInfo() {
		try {
			JsonResult result = new JsonResult();
			DeliverableGateVMEntity entity =
					this.projectValveDocumentService.getDeliverableInfo(deliverableId);
			Map<String, Object> data = new HashMap<String, Object>();
			// ID
			data.put("id", entity.getId());
			// 交付物编码
			data.put("code", entity.getCode());
			// 交付物名称
			data.put("name", entity.getName());
			// 阀门名称
			data.put("taskName", entity.getTaskName());
			// 负责主体ID
			data.put("obsId", entity.getObsId());
			// 关键交付物
			data.put("isKey", "Y".equals(entity.getIsKey()));
			// 负责主体名称
			data.put("obsName", entity.getObsName());
			// 检查项目
			data.put("checkitemName", entity.getCheckitemName());
			// 单项通过要求及验收办法
			data.put("checkRequirement", entity.getCheckRequirement());
			// 质量状态
			data.put("delvQualityStatusCode", entity.getDelvQualityStatusCode());
			// 质量状态名称
			data.put("delvQualityStatusName", entity.getDelvQualityStatusName());
			// 进度状态
			data.put("taskProgressStatusCode", entity.getDeliverableProgressStatus());
			
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 阀门状态更改
	 */
	public void updateGateStatus() {
		try{
			this.projectValveDocumentService.updateGateStatus(taskId, gateStatus);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
//	/**
//	 * 阀门通过
//	 */
//	public void updateGateStatusForward() {
//		try{
//			String nextGateId =
//					this.projectValveDocumentService.updateGateStatusForward(
//							taskId, programId, programVehicleId);
//			this.writeSuccessResult(nextGateId);
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			this.writeErrorResult(ex.getMessage());
//		}
//	}
	
	/**
	 * 阀门交付物列表取得
	 */
	public void getPmDeliverableList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageList =
					this.projectValveDocumentService.getPmDeliverableList(
							programVehicleId, searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageList.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 交付物编码
				dataMap.put("code", map.get("CODE"));
				// 交付物名称
				dataMap.put("name", map.get("NAME"));
				// 是否试用
				dataMap.put("isFitName", map.get("IS_FIT_NAME"));
				// 完成状况
				dataMap.put("taskProgressStatus", map.get("TASK_PROGRESS_STATUS"));
				// 阀门
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 单项目通过要求及验收办法
				dataMap.put("checkRequirement", map.get("CHECK_REQUIREMENT"));
				// 检查项目
				dataMap.put("checkName", map.get("CHECK_NAME"));
				// 关联任务
				dataMap.put("refTaskId", map.get("RESP_TASK_ID"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageList.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 二级交付物列表取得
	 */
	public void getDeptDeliverableList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageList =
					this.projectValveDocumentService.getDeptDeliverableList(
							obsId, searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageList.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 交付物名称
				dataMap.put("name", map.get("NAME"));
				// 完成状况
				dataMap.put("taskProgressStatus", map.get("TASK_PROGRESS_STATUS"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 单项目通过要求及验收办法
				dataMap.put("checkRequirement", map.get("CHECK_REQUIREMENT"));
				// 检查项目
				dataMap.put("checkName", map.get("CHECK_NAME"));
				// 关联任务
				dataMap.put("refTaskId", map.get("RESP_TASK_ID"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageList.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
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
