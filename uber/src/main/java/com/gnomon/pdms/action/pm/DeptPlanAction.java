package com.gnomon.pdms.action.pm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMObsVMEntity;
import com.gnomon.pdms.service.DeptPlanService;
import com.gnomon.pdms.service.ProgramPlanImportManager;

@Namespace("/pm")
public class DeptPlanAction extends PDMSCrudActionSupport<PMObsVMEntity> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DeptPlanService deptPlanService;
	
	@Autowired
	private ProgramPlanImportManager programPlanImportManager;
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private String preTaskId;
	public void setPreTaskId(String preTaskId) {
		this.preTaskId = preTaskId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String taskOwner;
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	
	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}
	
	private String preType;
	public void setPreType(String preType) {
		this.preType = preType;
	}
	
	private String planId;
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	private String functionId;
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	private String overSeqNo;
	public void setOverSeqNo(String overSeqNo) {
		this.overSeqNo = overSeqNo;
	}
	
	private String dropPosition;
	public void setDropPosition(String dropPosition) {
		this.dropPosition = dropPosition;
	}
	
	private boolean isVisible;
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	private boolean relationFlag;
	public void setRelationFlag(boolean relationFlag) {
		this.relationFlag = relationFlag;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	private File upload;
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * 泳道列表取得
	 */
	public void getDeptPlanTree() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list = this.deptPlanService.getDeptList(
					programVehicleId, planId, obsId, baselineId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("FUNCTION_ID"));
				StringBuffer text = new StringBuffer();
				if (! PDMSCommon.nvl(map.get("PLAN_ID")).equals(planId)) {
					text.append("<font color='Silver'>");
				}
				text.append(map.get("DISPLAY_NAME"));
				//text.append(" (" + map.get("IS_VISIBLE_NAME") + ")");
				if (PDMSCommon.isNotNull(PDMSCommon.nvl(map.get("RESP_OBS_NAME")))) {
					text.append("(");
					text.append(map.get("RESP_OBS_NAME"));
					text.append(")");
				}
				if (! PDMSCommon.nvl(map.get("PLAN_ID")).equals(planId)) {
					text.append("</font>");
				}
				if (PDMSConstants.STATUS_N.equals(map.get("IS_VISIBLE"))) {
					text.append("&nbsp;<img src=\"resources/images/freeze.png\" width=\"15px\" title=\"该泳道不显示在时程表！\"/>");
				}
				dataMap.put("text",  text.toString());
				dataMap.put("iconCls", "x-fa fa-angle-right");
				dataMap.put("expanded", true);
				dataMap.put("leaf", true);
				dataMap.put("seqNo", map.get("SEQ_NO"));
				dataMap.put("fnPlanLevel", map.get("PLAN_LEVEL"));
				dataMap.put("respObsId", map.get("RESP_OBS_ID"));
				data.add(dataMap);
			}
			
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 专业领域节点信息取得
	 */
	public void getDeptPlanTaskList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list = null;
			if (PDMSCommon.isNotNull(functionId)) {
				list = this.deptPlanService.getDeptPlanTaskList(functionId,
						baselineId, searchModel, this.getPage(), this.getLimit());
			} else {
				list = this.deptPlanService.getDeptPlanTaskList(programVehicleId,
						obsId, baselineId, searchModel, this.getPage(), this.getLimit());
			}
			
			for (Map<String, Object> map : list.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 节点名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 负责人ID
				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				// 负责人名称
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 类型
				dataMap.put("taskTypeCode", map.get("TASK_TYPE_CODE"));
				// 类型名称
				dataMap.put("taskTypeName", map.get("TASK_TYPE_NAME"));
				// 关联活动
				dataMap.put("parentTaskName", map.get("PARENT_TASK_NAME"));
				// 上级任务ID
				dataMap.put("parentId", map.get("PARENT_ID"));
				// 负责主体ID
				dataMap.put("obsId", map.get("OBS_ID"));
				// 负责主体名称
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 开始时间
				dataMap.put("plannedStartDate", DateUtils.change((Date)map.get("PLANNED_START_DATE")));
				// 截止时间
				dataMap.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 状态灯
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				// 关联任务ID
				dataMap.put("relationTaskId", map.get("RELATION_TASK_ID"));
				// 关联节点名称
				dataMap.put("relationTaskName", map.get("RELATION_TASK_NAME"));
				// 所属项目ID
				dataMap.put("programId", map.get("PROGRAM_ID"));
				// 计划所属层级
				dataMap.put("taskPlanLevel", map.get("PLAN_LEVEL"));
				// 周期（天）
				dataMap.put("durationDays", map.get("DURATION_DAYS"));
				// 显示在时程表
				if (PDMSConstants.STATUS_Y.equals(map.get("IS_VISIBLE"))) {
					dataMap.put("isVisible", true);
				} else {
					dataMap.put("isVisible", false);
				}
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				// 所属阀门
				dataMap.put("respGateName", map.get("RESP_GATE_NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 节点信息新增
	 */
	public void addTask() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.deptPlanService.addTask(
					programId, programVehicleId, obsId, functionId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 节点信息修改
	 */
	public void saveTaskDetail(){
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.deptPlanService.saveTaskDetail(taskId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 节点信息引用
	 */
	public void importTask() {
		try {
			// JSON解析
			List<Map<String, String>> model = this.convertJson2List(this.model);
			String result = this.deptPlanService.importTask(functionId, model);
			if (PDMSCommon.isNotNull(result)) {
				this.writeSuccessResult("下列节点已经存在在该泳道中、没有保存成功<BR>" + result);
			} else {
				this.writeSuccessResult(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * 节点信息删除
	 */
	public void deleteTask() {
		try{
			this.deptPlanService.deleteTask(programVehicleId, functionId, taskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 节点信息批量删除
	 */
	public void batchDeleteTask() {
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.deptPlanService.batchDeleteTask(programVehicleId, functionId, modelList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 更改显示在时程表
	 */
	public void updateVisible() {
		try{
			this.deptPlanService.updateVisible(functionId, taskId, isVisible);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 节点信息取得
	 */
	public void getTaskDetail() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			
			Map<String, Object> map = this.deptPlanService.getTaskDetail(
					taskId, baselineId);
			// ID
			data.put("id", map.get("ID"));
			// 节点名称
			data.put("taskName", map.get("TASK_NAME"));
			// 负责主体ID
			data.put("respObsId", map.get("OBS_ID"));
			// 负责主体
			data.put("obsName", map.get("OBS_NAME"));
			// 负责人ID
			data.put("taskOwner", map.get("TASK_OWNER"));
			// 负责人名称
			data.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
			// 类型
			data.put("taskTypeCode", map.get("TASK_TYPE_CODE"));
			// 类型名称
			data.put("taskTypeName", map.get("TASK_TYPE_NAME"));
			// 开始时间
			data.put("plannedStartDate", DateUtils.change((Date)map.get("PLANNED_START_DATE")));
			// 截止时间
			data.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
			// 周期（天）
			data.put("durationDays", map.get("DURATION_DAYS"));
			// 关联交付物ID（原始）
			data.put("srcDeliverableId", map.get("DELIVERABLE_ID"));
			// 关联交付物名称
			data.put("deliverableName", map.get("DELIVERABLE_NAME"));
			// 关联交付物所属阀门ID
			data.put("gateId", map.get("GATE_ID"));
			// 关联交付物所属阀门名称
			data.put("gateName", map.get("GATE_NAME"));
			// 关联任务ID
			data.put("relationTaskId", map.get("RELATION_TASK_ID"));
			// 关联节点名称
			data.put("relationTaskName", map.get("RELATION_TASK_NAME"));
			// 关联活动ID
			data.put("parentId", map.get("PARENT_ID"));
			// 关联活动名称
			data.put("parentTaskName", map.get("PARENT_TASK_NAME"));
			// 标题显示位置
			data.put("titleDispLocation", map.get("TITLE_DISP_LOCATION_CODE"));
			// 检查项目
			data.put("checkitemName", map.get("CHECKITEM_NAME"));
			// 单项通过要求及验收办法
			data.put("checkRequirement", map.get("CHECK_REQUIREMENT"));
			// 关键交付物
			data.put("isKey", PDMSConstants.STATUS_Y.equals(map.get("IS_KEY")));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 更新任务负责人(批量)
	 */
	public void updateTaskOwner(){
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.deptPlanService.updateTaskOwner(taskOwner, modelList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/**
	 * 前置节点信息取得
	 */
	public void getPreTask() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
 			List<Map<String, Object>> list =
 					this.deptPlanService.getPreTaskList(taskId, baselineId, preType);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				if (PDMSConstants.PRE_TASK_TYPE_POST.equals(preType)) {
					// 后置节点名称
					dataMap.put("preTaskName", map.get("POST_TASK_NAME"));
				} else {
					// 前置节点名称
					dataMap.put("preTaskName", map.get("PRE_TASK_NAME"));
				}
				// 滞后天数
				dataMap.put("lag", map.get("LAGS"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 前置节点增加
	 */
	public void insertPreTask(){
		try{
			this.deptPlanService.insertPreTask(taskId, preTaskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 前置节点删除
	 */
	public void deletePreTask(){
		try{
			this.deptPlanService.deletePreTask(preTaskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 质量阀交付物列表取得
	 */
	public void getDeliverableList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 阀门交付物信息取得
			GTPage<Map<String, Object>> list =
					this.deptPlanService.getDeliverableList(programVehicleId, obsId,
							searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 交付物ID
				dataMap.put("id", map.get("ID"));
				// 阀门ID
				dataMap.put("taskId", map.get("TASK_ID"));
				// 阀门名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 交付物编号
				dataMap.put("attachmentCode", map.get("CODE"));
				// 交付物名称
				dataMap.put("attachmentName", map.get("NAME"));
				// 负责主体ID
				dataMap.put("obsId", map.get("OBS_ID"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 负责人ID
				dataMap.put("userId", map.get("USERID"));
				// 负责人
				dataMap.put("username", map.get("USERNAME"));
				// 截止日期
				dataMap.put("submitDate", DateUtils.change((Date)map.get("SUBMIT_DATE")));
				// 对应质量阀日期
				dataMap.put("gateDate", DateUtils.change((Date)map.get("GATE_DATE")));
				// 检查项目
				dataMap.put("checkitemName", map.get("CHECKITEM_NAME"));
				// 单项通过要求及验收办法
				dataMap.put("checkRequirement", map.get("CHECK_REQUIREMENT"));
				// 关键交付物
				dataMap.put("isKey", PDMSConstants.STATUS_Y.equals(map.get("IS_KEY")));				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 节点列表取得
	 */
	public void getTaskNodeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 节点列表取得
			GTPage<Map<String, Object>> list =
					this.deptPlanService.getTaskNodeList(programVehicleId, obsId,
							relationFlag, searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 节点名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 计划完成时间
				dataMap.put("plannedFinishDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				// 节点车型ID
				dataMap.put("vehicleId", map.get("PROGRAM_VEHICLE_ID"));
				// 节点车型名称
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				// 所属专业领域ID
				dataMap.put("functionGroupId", map.get("FUNCTION_GROUP_ID"));
				// 所属专业领域名称
				dataMap.put("functionGroupName", map.get("FUNCTION_GROUP_NAME"));
				// 负责主体ID
				dataMap.put("obsId", map.get("OBS_ID"));
				// 负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				// 计划类别
				if ("1".equals(PDMSCommon.nvl(map.get("PLAN_LEVEL")))) {
					dataMap.put("planLevelName", "主计划");
				} else {
					dataMap.put("planLevelName", "二级计划");
				}
				// 所属阀门
				dataMap.put("respGateName", map.get("RESP_GATE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 活动列表取得
	 */
	public void getActivityList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			GTPage<Map<String, Object>> list =
					this.deptPlanService.getActivityList(taskId, functionId,
							searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 活动
				dataMap.put("taskName", map.get("TASK_NAME"));
				// 负责人 ID
				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				// 负责人 
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 计划开始时间
				dataMap.put("plannedStartDate", DateUtils.change((Date)map.get("PLANNED_START_DATE")));
				// 计划结束时间
				dataMap.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 新建泳道
	 */
	public void createFunction() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.deptPlanService.createFunction(
					programId, programVehicleId, planId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 删除泳道
	 */
	public void deleteFunction() {
		try{
			this.deptPlanService.deleteFunction(functionId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 修改泳道
	 */
	public void updateFunction() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.deptPlanService.updateFunction(functionId, model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 泳道拖动排序
	 */
	public void resequenceFunction() {
		try{
			this.deptPlanService.resequenceFunction(programVehicleId, planId,
					functionId, overSeqNo, dropPosition);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 取得泳道信息
	 */
	public void getFunctionInfo() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			
			Map<String, Object> map = this.deptPlanService.getFunctionInfo(functionId);
			// ID
			data.put("id", map.get("FUNCTION_ID"));
			// 名称
			data.put("functionName", map.get("DISPLAY_NAME"));
			// 责任组
			data.put("respObsId", map.get("RESP_OBS_ID"));
			// 排序
			data.put("seqNo", map.get("SEQ_NO"));
			// 是否显示在时程表
			if (PDMSConstants.STATUS_Y.equals(map.get("IS_VISIBLE"))) {
				data.put("chkIsVisible", true);
			} else {
				data.put("chkIsVisible", false);
			}
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 导入节点计划（二级计划）
	 */
	public void importTaskList() {
		try{
			programPlanImportManager.importProgramNodeFromExcel(
					programVehicleId, obsId, new FileInputStream(upload));
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 下载节点计划模板
	 */
	public void downloadTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/taskListTemplate.xls");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode("节点管理清单模板.xls", "UTF-8"));
			response.setContentType("application/msexcel");
			try {
				int l = -1;
				byte[] tmp = new byte[1024];
				while ((l = is.read(tmp)) != -1) {
					os.write(tmp, 0, l);
				}
				os.flush();
				os.close();
			} finally {
				is.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
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
	public PMObsVMEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}