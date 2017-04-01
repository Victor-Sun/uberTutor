package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Date;
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
import com.gnomon.pdms.entity.PMProgramVehicleEntity;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.service.BaselineService;
import com.gnomon.pdms.service.DeptPlanService;
import com.gnomon.pdms.service.PMProgramVehicleService;
import com.gnomon.pdms.service.ProjectOrganizationService;
import com.gnomon.pdms.service.ProjectPlanService;

@Namespace("/pm")
public class BaselineAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BaselineService baselineService;
	
	@Autowired
	private PMProgramVehicleService pmProgramVehicleService;
	
	@Autowired
	private DeptPlanService deptPlanService;
	
	@Autowired
	private ProjectOrganizationService projectOrganizationService;
	
	@Autowired
	private ProjectPlanService projectPlanService;
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private String title;
	public void setTitle(String title) {
		this.title = title;
	}
	
	private String note;
	public void setNote(String note) {
		this.note = note;
	}
	
	private String node;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String baselineId;
	public void setBaselineId(String baselineId) {
		this.baselineId = baselineId;
	}
	
	private String baselineType;
	public void setBaselineType(String baselineType) {
		this.baselineType = baselineType;
	}
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	/**
	 * 创建基线
	 */
	public void createBaseline() {
		try {
			// 创建基线
			if ("BI".equals(baselineType)) {
				this.baselineService.createBiBaseline(programId, title, note);
			} else {
				this.baselineService.createBaseline(
						programVehicleId, obsId, title, note);
			}
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * 基线列表信息取得
	 */
	public void getBaselineList() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list = null;
			// 项目基本信息基线列表取得
			if ("BI".equals(baselineType)) {
				list = this.baselineService.getBiBaseline(programId);
			} else {
				list = this.baselineService.getBaselineList(programVehicleId, obsId);
			}
			for (Map<String,Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", map.get("ID"));
				//基线名称
				dataMap.put("title", map.get("TITLE"));
				//描述
				dataMap.put("note", map.get("NOTE"));
				//创建时间
				dataMap.put("createDate", DateUtils.formate(
						(Date) map.get("CREATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
				//当前基线
				dataMap.put("isDefault", map.get("IS_DEFAULT"));
				// 是否可设为默认基线（只有项目计划基线才有效）
				dataMap.put("isActive", map.get("IS_ACTIVE"));

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
	 * 设定默认基线(项目计划)
	 */
	public void setDefaultBaseline() {
		try {
			this.baselineService.setDefaultBaseline(programVehicleId, obsId,
					Integer.parseInt(baselineId));
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 设定默认基线(基本信息)
	 */
	public void setDefaultBiBaseline() {
		try {
			this.baselineService.setDefaultBiBaseline(programId,
					Integer.parseInt(baselineId));
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 项目基本信息基线详细取得
	 */
	public void getProjectBaseInfo4Baseline() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> baseInfo=
					this.baselineService.getBaseInfoBaseline(baselineId);
			Map<String, Object> data = new HashMap<String, Object>();
			// ID
			data.put("id", baseInfo.get("ID"));
			// 项目编号
			data.put("code", baseInfo.get("CODE"));
			// 项目名称
			data.put("programName", baseInfo.get("PROGRAM_NAME"));
			// 项目总监ID
			data.put("director", baseInfo.get("DIRECTOR"));
			// 项目总监姓名
			data.put("directorName", baseInfo.get("DIRECTOR_NAME"));
			// 项目经理
			data.put("pmName", baseInfo.get("PM_NAME"));
			// 项目类型
			data.put("programType", baseInfo.get("PROGRAM_TYPE_ID"));
			// 项目类型名称
			data.put("programTypeName", baseInfo.get("PROGRAM_TYPE_NAME"));
			// 生产单位
			data.put("ManufactureCompId", baseInfo.get("MANUFACTURE_COMP_ID"));
			//级别
			data.put("programLevel", baseInfo.get("PROGRAM_LEVEL"));
			//产品定位
			data.put("productPositioning", baseInfo.get("PRODUCT_POSITIONING"));
			//开发状态
			data.put("devStatusCode", baseInfo.get("DEV_STATUS_CODE"));
			//开发状态名称
			data.put("devStatusName", baseInfo.get("DEV_STATUS_NAME"));
			//开发类型
			data.put("devTypeId", baseInfo.get("DEV_TYPE_ID"));
			//创建人
			data.put("createByName", baseInfo.get("CREATE_BY_NAME"));
			//优先级
			data.put("programPriorityCode", baseInfo.get("PROGRAM_PRIORITY_CODE"));
			//创建时间
			data.put("createDate", DateUtils.change((Date)baseInfo.get("CREATE_DATE")));
			// 车型类型
			data.put("vehicleType", baseInfo.get("VEHICLE_TYPE_ID"));
			// 车型平台
			data.put("vehiclePlatform", baseInfo.get("VEHICLE_PLATFORM_ID"));
			// 是否签订合同
			data.put("isOutContract", "Y".equals(baseInfo.get("IS_OUT_CONTRACT")));
			// 备注
			data.put("programMemo", baseInfo.get("PROGRAM_MEMO"));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 查看基线tree信息取得
	 */
	public void getDisplayBaselineTree() {
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = null;
		
		PMProgramVehicleEntity vehicleEntity =
				this.pmProgramVehicleService.getPmProgramVehicle(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			// 组织名称取得
			Map<String, Object> obsMap =
					this.projectOrganizationService.getObsPlanInfo(obsId);
			if ("root".equals(node)) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", obsMap.get("OBS_NAME"));
				dataMap.put("iconCls", "x-fa fa-align-justify");
				dataMap.put("viewType", "timeLineBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("planLevel", "2");
				dataMap.put("planId", obsMap.get("PLAN_ID"));
				dataMap.put("expanded", true);
				data.add(dataMap);
			} else {
				// 节点计划
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("fnGroupId", obsId);
				dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
				dataMap.put("text", "节点计划");
				dataMap.put("iconCls", "x-fa fa-th-list");
				dataMap.put("viewType", "deptPlanTaskBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("planLevel", "2");
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 时程表设置
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("fnGroupId", obsId);
				dataMap.put("fnGroupName", obsMap.get("OBS_NAME"));
				dataMap.put("text", "时程表设置");
				dataMap.put("iconCls", "x-fa fa-tasks");
				dataMap.put("viewType", "deptPlanBase");
				dataMap.put("planLevel", "2");
				dataMap.put("planId", obsMap.get("PLAN_ID"));
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("leaf", true);
				data.add(dataMap);
			}
		} else {
			Map<String, Object> planInfo =
					this.projectPlanService.getMainPlanInfo(programVehicleId);
			if ("root".equals(node)) {
				// 主计划
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "项目主计划");
				dataMap.put("iconCls", "x-fa fa-align-justify");
				dataMap.put("viewType", "timeLineBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("planLevel", "1");
				dataMap.put("planId", planInfo.get("ID"));
				dataMap.put("expanded", true);
				data.add(dataMap);
			} else {
				// 主节点
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "主节点");
				dataMap.put("iconCls", "x-fa fa-caret-square-o-up");
				dataMap.put("viewType", "mainNodeBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 质量阀
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "质量阀");
				dataMap.put("iconCls", "x-fa fa-shirtsinbulk");
				dataMap.put("viewType", "qualityValveBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 节点计划
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "节点计划");
				dataMap.put("iconCls", "x-fa fa-th-list");
				dataMap.put("viewType", "deptPlanTaskBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("planLevel", "1");
				dataMap.put("leaf", true);
				data.add(dataMap);
				
				// 时程表设置
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", UUID.randomUUID());
				dataMap.put("text", "时程表设置");
				dataMap.put("iconCls", "x-fa fa-tasks");
				dataMap.put("viewType", "deptPlanBase");
				dataMap.put("vehicleId", programVehicleId);
				dataMap.put("vehicleName", vehicleEntity.getVehicleName());
				dataMap.put("planLevel", "1");
				dataMap.put("planId", planInfo.get("ID"));
				dataMap.put("leaf", true);
				data.add(dataMap);
			}
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	/**
	 * 项目计划基线取得（发布的有效基线）
	 */
	public void getBaselineTask() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> baseInfo=
					this.baselineService.getBaselineTask(programVehicleId, obsId);
			Map<String, Object> data = new HashMap<String, Object>();
			// ID
			data.put("id", baseInfo.get("ID"));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public TaskEntity getModel() {
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
