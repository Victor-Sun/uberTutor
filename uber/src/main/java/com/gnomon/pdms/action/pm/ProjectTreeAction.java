package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ExtendedProjectEntity;
import com.gnomon.pdms.entity.PMProgramVehicleEntity;
import com.gnomon.pdms.service.DeptPlanService;
import com.gnomon.pdms.service.Ext202ItemService;
import com.gnomon.pdms.service.Ext301Service;
import com.gnomon.pdms.service.GTUserManager;
import com.gnomon.pdms.service.PMProgramVehicleService;
import com.gnomon.pdms.service.ProjectBaseInfoService;
import com.gnomon.pdms.service.ProjectExtensionPlanService;
import com.gnomon.pdms.service.ProjectInfoService;
import com.gnomon.pdms.service.ProjectOpenIssueService;
import com.gnomon.pdms.service.ProjectOrganizationService;
import com.gnomon.pdms.service.ProjectPlanService;
import com.gnomon.pdms.service.ProjectTreeService;

@Namespace("/pm")
public class ProjectTreeAction extends PDMSCrudActionSupport<ExtendedProjectEntity> {

	private static final String PROJECT_TREE_TYPE_OPEN_ISSUE = "OPEN_ISSUE";

	private static final String PROJECT_TREE_TYPE_RISK = "RISK";

	private static final String PROJECT_TREE_TYPE_EXT_PLAN = "EXT_PLAN";
	
	private static final String PROJECT_TREE_TYPE_EXT_201_PLAN = "EXT_201_PLAN";
	
	private static final String PROJECT_TREE_TYPE_MAIN_PLAN = "MAIN_PLAN";
	
	private static final String PROJECT_TREE_TYPE_DEPT_PLAN = "DEPT_PLAN";
	
	private static final String PROJECT_TREE_TYPE_DEPT = "DEPT";

	private static final String PROJECT_TREE_TYPE_VEHICLE = "VEHICLE";

	private static final String PROJECT_TREE_TYPE_PROGRAM = "PROGRAM";
	
	private static final String PROJECT_TREE_TYPE_OTHERS = "OTHERS";

	private static final String PROJECT_TREE_TYPE_PART = "PART";

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjectTreeService projectTreeService;
	
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;	
	
	@Autowired
	private ProjectExtensionPlanService projectExtensionPlanService;
	
	@Autowired
	private Ext202ItemService ext202ItemService;
	
	@Autowired
	private ProjectOpenIssueService projectOpenIssueService;
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private PMProgramVehicleService pmProgramVehicleService;
	
	@Autowired
	private DeptPlanService deptPlanService;
	
	@Autowired
	private ProjectOrganizationService projectOrganizationService;
	
	@Autowired
	private ProjectPlanService projectPlanService;
	
	@Autowired
	private GTUserManager gtUserManager;
	
	@Autowired
	private Ext301Service ext301Service;
	
	private String node;
	private String type;
	private String pdmsProgramUuid;
	private String vehicleId;
	private String idValue;
	private String relationTaskId;
	private String extProcessCode;
	private String applicationName;
	private String plannedStartDate;
	private String plannedFinishDate;
	private Long modelId;
	public void setNode(String node) {
		this.node = node;
	}
	
	public String getType() {
		return type;
	}

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getRelationTaskId() {
		return relationTaskId;
	}

	public void setRelationTaskId(String relationTaskId) {
		this.relationTaskId = relationTaskId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getModelId() {
		return modelId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public String getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(String plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getPdmsProgramUuid() {
		return pdmsProgramUuid;
	}

	public void setPdmsProgramUuid(String pdmsProgramUuid) {
		this.pdmsProgramUuid = pdmsProgramUuid;
	}

	public String getExtProcessCode() {
		return extProcessCode;
	}

	public void setExtProcessCode(String extProcessCode) {
		this.extProcessCode = extProcessCode;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	private ExtendedProjectEntity entity;
	
	
	public ExtendedProjectEntity getEntity() {
		return entity;
	}

	public void setEntity(ExtendedProjectEntity entity) {
		this.entity = entity;
	}

	public String getNode() {
		return node;
	}

	public String getProgramId() {
		return programId;
	}

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private boolean obsCanEdit;
	public void setObsCanEdit(boolean obsCanEdit) {
		this.obsCanEdit = obsCanEdit;
	}

	public void getProjectTree() {
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = null;
		
		try {
			if ("root".equals(node)) {
				// 项目基本信息 由于是Tree的根节点，权限在页面进行判定
				Map<String, Object> map =
						this.projectBaseInfoService.getProjectBaseInfo(programId);
				// 项目车型
				List<Map<String, Object>> vehicleList =
						this.projectBaseInfoService.getProjectPlanSOP(programId, false);
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("text", map.get("PROGRAM_NAME"));
				dataMap.put("iconCls", "x-fa fa-feed");
				dataMap.put("viewType", "projectBaseInfoHomePage");
				dataMap.put("type", PROJECT_TREE_TYPE_PROGRAM);
				if (vehicleList.size() == 0) {
					// 新建车型时 创建人有权限点击项目根节点、编辑项目基本信息
					dataMap.put("programStauts", "draft");
				}
				dataMap.put("expanded", true);
				// 项目类型
				dataMap.put("programType", map.get("PROGRAM_TYPE_CODE"));
				data.add(dataMap);
			} else if (PROJECT_TREE_TYPE_PROGRAM.equals(type)) {
				// 项目车型
				List<Map<String, Object>> vehicleList =
						this.projectBaseInfoService.getProjectPlanSOP(programId, true);
				for (Map<String, Object> map :vehicleList) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", map.get("VEHICLE_NAME"));
					dataMap.put("iconCls", "x-fa fa-car");
					dataMap.put("viewType", "vehicleBase");
					dataMap.put("type", PROJECT_TREE_TYPE_VEHICLE);
					dataMap.put("vehicleId", map.get("ID"));
					dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
					dataMap.put("planStatus", map.get("PLAN_STATUS_CODE"));
					dataMap.put("leaf", false);
					data.add(dataMap);
				}
			}else if (PROJECT_TREE_TYPE_VEHICLE.equals(type)) {
				PMProgramVehicleEntity pmProgramVehicleEntity =
						pmProgramVehicleService.getPmProgramVehicle(vehicleId);
				
				Map<String, Object> planInfo =
						this.projectPlanService.getMainPlanInfo(vehicleId);
				
				// 项目组织
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P0601") ||
						this.projectTreeService.getProjectTreePrivilege(programId, "P0604")) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "项目组织");
					dataMap.put("iconCls", "x-fa fa-cubes");
					dataMap.put("viewType", "projectOrganizationTab");
					dataMap.put("type", PROJECT_TREE_TYPE_OTHERS);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
				
				// 项目计划
//			if (this.projectTreeService.getProjectTreePrivilege(programId, "P0801")) {
//				dataMap = new HashMap<String,Object>();
//				dataMap.put("id", UUID.randomUUID());
//				dataMap.put("text", "项目计划状态");
//				dataMap.put("iconCls", "x-fa fa-table");
//				dataMap.put("viewType", "deployPlan");
//				dataMap.put("type", PROJECT_TREE_TYPE_PROJECT_PLAN);
//				dataMap.put("vehicleId", vehicleId);
//				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
//				dataMap.put("leaf", true);
//				data.add(dataMap);
//			}
				
				// 主计划
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P0901")) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "项目主计划");
					dataMap.put("iconCls", "x-fa fa-align-justify");
					dataMap.put("viewType", "mainPlanBase");
					dataMap.put("type", PROJECT_TREE_TYPE_MAIN_PLAN);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("planLevel", "1");
					dataMap.put("planId", planInfo.get("ID"));
					dataMap.put("expanded", true);
					dataMap.put("leaf", false);
					data.add(dataMap);
				}
				
				// 二级计划
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P1001")) {
					dataMap = new HashMap<String,Object>();
					//dataMap.put("id", UUID.randomUUID());
					dataMap.put("id", vehicleId);
					dataMap.put("text", "项目二级计划");
					dataMap.put("iconCls", "x-fa fa-align-justify");
					dataMap.put("viewType", "");
					dataMap.put("type", PROJECT_TREE_TYPE_DEPT_PLAN);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("plannedStartDate", pmProgramVehicleEntity.getPlannedStartDate());
					dataMap.put("plannedFinishDate", pmProgramVehicleEntity.getPlannedFinishDate());
					dataMap.put("expanded", false);
					dataMap.put("leaf", false);
					data.add(dataMap);
				}
				
				// 专项
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P1101")) {
					// 新建专项计划权限取得
					boolean createEpPrivilege = this.projectTreeService.getProjectTreePrivilege(
							programId, "P1102");
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "专项计划");
					dataMap.put("iconCls", "x-fa fa-cubes");
					dataMap.put("viewType", "projectExtensionPlan");
					dataMap.put("type", PROJECT_TREE_TYPE_EXT_PLAN);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", false);
					dataMap.put("createEpPrivilege", createEpPrivilege);
					data.add(dataMap);
				}
				
				// 车型OpenIssue
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P1601")) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "Open Issue");
					dataMap.put("iconCls", "x-fa fa-question-circle");
					dataMap.put("viewType", "projectVehicleOpenIssue");
					dataMap.put("type", PROJECT_TREE_TYPE_OPEN_ISSUE);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("statusCode", pmProgramVehicleEntity.getStatusCode());
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
				
				// 节点管理清单
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P0301")) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "节点管理清单");
					dataMap.put("iconCls", "x-fa fa-list-ul");
					dataMap.put("viewType", "projectNodeList");
					dataMap.put("type", PROJECT_TREE_TYPE_OTHERS);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
				
				// 阀门交付物
				if (this.projectTreeService.getProjectTreePrivilege(programId, "P0401")) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "阀门交付物");
					dataMap.put("iconCls", "x-fa fa-list-alt");
					dataMap.put("viewType", "pmDeliverableBase");
					dataMap.put("type", PROJECT_TREE_TYPE_OTHERS);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
				
				// 零件开发管控
				if (true) {
					Long partProjectId = ext301Service.getPartProjectId(vehicleId);
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("text", "零件开发管控");
					dataMap.put("iconCls", "x-fa fa-list-alt");
					dataMap.put("viewType", "partControl");
					dataMap.put("type", PROJECT_TREE_TYPE_PART);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("partProjectId", partProjectId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", false);
					data.add(dataMap);
				}
				
				// 任务列表
//			if (this.projectTreeService.getProjectTreePrivilege(programId, "P0501")) {
//				dataMap = new HashMap<String,Object>();
//				dataMap.put("id", UUID.randomUUID());
//				dataMap.put("text", "任务列表");
//				dataMap.put("iconCls", "x-fa fa-list-ol");
//				dataMap.put("viewType", "projectTaskList");
//				dataMap.put("type", PROJECT_TREE_TYPE_OTHERS);
//				dataMap.put("vehicleId", vehicleId);
//				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
//				dataMap.put("leaf", true);
//				data.add(dataMap);
//			}
			} else if (PROJECT_TREE_TYPE_EXT_PLAN.equals(type)) {
				// 专项Tree信息取得
				List<Map<String, Object>> list =
						this.projectTreeService.getExtProjectList(vehicleId);
				for (Map<String, Object> map : list) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("idValue", map.get("ID"));
					dataMap.put("text", map.get("APPLICATION_NAME"));
					dataMap.put("iconCls", "x-fa fa-cogs");
					dataMap.put("viewType", map.get("View_Type"));
//				dataMap.put("parentId", "03");
					dataMap.put("leaf", false);
					if("ext201ProjectPanel".equals(map.get("View_Type"))){
						dataMap.put("type", PROJECT_TREE_TYPE_EXT_201_PLAN);
					}
					dataMap.put("plannedStartDate", map.get("PLANNED_START_DATE"));
					dataMap.put("plannedFinishDate", map.get("PLANNED_FINISH_DATE"));
					dataMap.put("vehicleId", vehicleId);
					data.add(dataMap);
				}
			} else if (PROJECT_TREE_TYPE_RISK.equals(type)) {
				// 项目风险管理表
//				if (this.projectTreeService.getProjectTreePrivilege(programId, "P1301")) {
//					dataMap = new HashMap<String,Object>();
//					dataMap.put("id", "0801");
//					dataMap.put("text", "项目风险管理表");
//					dataMap.put("iconCls", "x-fa fa-tint");
//					dataMap.put("viewType", "projectRiskList");
//					dataMap.put("vehicleId", vehicleId);
//					dataMap.put("leaf", true);
//					data.add(dataMap);
//				}
			} else if (PROJECT_TREE_TYPE_OPEN_ISSUE.equals(type)) {
				// 项目OpenIssue
//			List<Map<String, Object>> vehicleList = projectOpenIssueService.getVehicleList(programId);
//			for(Map<String, Object> map :vehicleList){
//				dataMap = new HashMap<String,Object>();
//				dataMap.put("id", map.get("ID"));
//				dataMap.put("text", map.get("VEHICLE_NAME"));
//				dataMap.put("iconCls", "x-fa fa-question-circle");
//				dataMap.put("viewType", "projectVehicleOpenIssue");
//				dataMap.put("leaf", true);
//				data.add(dataMap);
//			}
			} else if (PROJECT_TREE_TYPE_MAIN_PLAN.equals(type)) {
				PMProgramVehicleEntity pmProgramVehicleEntity =
						pmProgramVehicleService.getPmProgramVehicle(vehicleId);
				Map<String, Object> planInfo =
						this.projectPlanService.getMainPlanInfo(vehicleId);
				// 主节点
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "主节点");
				dataMap.put("iconCls", "x-fa fa-caret-square-o-up");
				dataMap.put("viewType", "mainNodeBase");
				dataMap.put("vehicleId", vehicleId);
				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 质量阀
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "质量阀");
				dataMap.put("iconCls", "x-fa fa-shirtsinbulk");
				dataMap.put("viewType", "qualityValveBase");
				dataMap.put("vehicleId", vehicleId);
				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 节点计划
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "节点计划");
				dataMap.put("iconCls", "x-fa fa-th-list");
				dataMap.put("viewType", "deptPlanTaskBase");
				dataMap.put("vehicleId", vehicleId);
				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
				dataMap.put("planLevel", "1");
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 时程表设置
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "时程表设置");
				dataMap.put("iconCls", "x-fa fa-tasks");
				dataMap.put("viewType", "deptPlanBase");
				dataMap.put("vehicleId", vehicleId);
				dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
				dataMap.put("planLevel", "1");
				dataMap.put("planId", planInfo.get("ID"));
				dataMap.put("leaf", true);
				data.add(dataMap);
			} else if (PROJECT_TREE_TYPE_DEPT_PLAN.equals(type)) {
				PMProgramVehicleEntity pmProgramVehicleEntity =
						pmProgramVehicleService.getPmProgramVehicle(vehicleId);
				List<Map<String, Object>> list =
						this.projectOrganizationService.getObsPlanList(vehicleId);
				for (Map<String, Object> map : list) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("id", map.get("ID"));
					dataMap.put("idValue", map.get("ID"));
					dataMap.put("text", map.get("OBS_NAME"));
					dataMap.put("iconCls", "x-fa fa-group");
					dataMap.put("viewType", "deptBase");
					dataMap.put("planLevel", "2");
					dataMap.put("planId", map.get("PLAN_ID"));
					dataMap.put("type", PROJECT_TREE_TYPE_DEPT);
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("plannedStartDate", pmProgramVehicleEntity.getPlannedStartDate());
					dataMap.put("plannedFinishDate", pmProgramVehicleEntity.getPlannedFinishDate());
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("expanded", false);
					dataMap.put("leaf", false);
					dataMap.put("obsCanEdit", "1".equals(PDMSCommon.nvl(map.get("OBS_CAN_EDIT"))));
					data.add(dataMap);
				}
			} else if (PROJECT_TREE_TYPE_DEPT.equals(type)) {
				if (obsCanEdit) {
					PMProgramVehicleEntity pmProgramVehicleEntity =
							pmProgramVehicleService.getPmProgramVehicle(vehicleId);
					// 专业领域名称取得
					Map<String, Object> obsMap =
							this.projectOrganizationService.getObsPlanInfo(idValue);
					// 主节点
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("fnGroupId", idValue);
					dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
					dataMap.put("text", "主节点");
					dataMap.put("iconCls", "x-fa fa-caret-square-o-up");
					dataMap.put("viewType", "mainNodeBase");
					dataMap.put("planLevel", "2");
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
					
					// 质量阀
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("fnGroupId", idValue);
					dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
					dataMap.put("text", "质量阀");
					dataMap.put("iconCls", "x-fa fa-shirtsinbulk");
					dataMap.put("viewType", "qualityValveBase");
					dataMap.put("planLevel", "2");
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
					
					// 节点计划
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("fnGroupId", idValue);
					dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
					dataMap.put("text", "节点计划");
					dataMap.put("iconCls", "x-fa fa-th-list");
					dataMap.put("viewType", "deptPlanTaskBase");
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("planLevel", "2");
					dataMap.put("leaf", true);
					data.add(dataMap);
					
					// 时程表设置
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("fnGroupId", idValue);
					dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
					dataMap.put("text", "时程表设置");
					dataMap.put("iconCls", "x-fa fa-tasks");
					dataMap.put("viewType", "deptPlanBase");
					dataMap.put("planLevel", "2");
					dataMap.put("planId", obsMap.get("PLAN_ID"));
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("vehicleName", pmProgramVehicleEntity.getVehicleName());
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
			} else if (PROJECT_TREE_TYPE_EXT_201_PLAN.equals(type)) {
				PMProgramVehicleEntity pmProgramVehicleEntity =
						pmProgramVehicleService.getPmProgramVehicle(vehicleId);
				List<Map<String, Object>> list =
						projectExtensionPlanService.getExtProjectStageList(Long.valueOf(node));
				for (Map<String, Object> map : list) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", UUID.randomUUID());
					dataMap.put("idValue", map.get("ID"));
					dataMap.put("stageId", map.get("ID"));
					dataMap.put("projectId", map.get("EXT_PROJECT_ID"));
					dataMap.put("text", map.get("TITLE"));
					dataMap.put("iconCls", "x-fa fa-cogs");
					dataMap.put("viewType", "ext201StagePanel");
					dataMap.put("vehicleId", vehicleId);
					dataMap.put("plannedStartDate", pmProgramVehicleEntity.getPlannedStartDate());
					dataMap.put("plannedFinishDate", pmProgramVehicleEntity.getPlannedFinishDate());
					dataMap.put("parentId", node);
					dataMap.put("leaf", true);
					data.add(dataMap);
				}
			} else if (PROJECT_TREE_TYPE_PART.equals(type)) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "工作组");
				dataMap.put("iconCls", "x-fa fa-list-alt");
				dataMap.put("viewType", "workGroupPanel");
//				dataMap.put("type", PROJECT_TREE_TYPE_PART);
				dataMap.put("vehicleId", vehicleId);
				dataMap.put("leaf", true);
				data.add(dataMap);
			} else {
				//继续增加
			}
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}

	@Override
	public String save() throws Exception {
		try {
			projectExtensionPlanService.saveExtendedProject(vehicleId,extProcessCode,applicationName,DateUtils.strToDate(plannedStartDate),DateUtils.strToDate(plannedFinishDate),modelId,relationTaskId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity = new ExtendedProjectEntity();
		
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
	public ExtendedProjectEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getExtProcessList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectExtensionPlanService.getExtProcessList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("id", map.get("CODE"));
			dataMap.put("name", map.get("TITLE"));
			data.add(dataMap);
		}
		
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getExt202ModelList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext202ItemService.getExt202ModelList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("DESCRIPTION"));
			data.add(dataMap);
		}
		
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getRelationTaskList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext202ItemService.getRelationTaskList(vehicleId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("id", map.get("TASK_ID"));
			dataMap.put("name", map.get("TASK_NAME"));
			data.add(dataMap);
		}
		
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getVehicle(){
		JsonResult result = new JsonResult();
		PMProgramVehicleEntity pmProgramVehicleEntity = pmProgramVehicleService.getPmProgramVehicle(vehicleId);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		dataMap.put("id", pmProgramVehicleEntity.getId());
		dataMap.put("name", pmProgramVehicleEntity.getVehicleName());
		dataMap.put("code", pmProgramVehicleEntity.getVehicleCode());
		dataMap.put("pm", pmProgramVehicleEntity.getPm());
		if(pmProgramVehicleEntity.getPm() != null){
			String userName = gtUserManager.getUser(pmProgramVehicleEntity.getPm()).getUsername();
			dataMap.put("pmName", userName);
		}
		if(pmProgramVehicleEntity.getDirector() != null){
			String userName = gtUserManager.getUser(pmProgramVehicleEntity.getDirector()).getUsername();
			dataMap.put("director", userName);
		}
		dataMap.put("qm", pmProgramVehicleEntity.getQm());
		if(pmProgramVehicleEntity.getQm() != null){
			String userName = gtUserManager.getUser(pmProgramVehicleEntity.getQm()).getUsername();
			dataMap.put("qmName", userName);
		}
		if(pmProgramVehicleEntity.getSopDate() != null){
			dataMap.put("sopDate", DateUtils.formate(pmProgramVehicleEntity.getSopDate()));
		}
		if(pmProgramVehicleEntity.getPlannedStartDate() != null){
			dataMap.put("plannedStartDate",  DateUtils.formate(pmProgramVehicleEntity.getPlannedStartDate()));
		}
		if(pmProgramVehicleEntity.getPlannedFinishDate() != null){
			dataMap.put("plannedFinishDate",  DateUtils.formate(pmProgramVehicleEntity.getPlannedFinishDate()));
		}
		result.buildSuccessResult(dataMap);
		Struts2Utils.renderJson(result);
		
	}
	
	/**
	 * 保存车型基本信息
	 */
	public void saveVehicleInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.pmProgramVehicleService.saveVehicleInfo(programId, vehicleId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
}
