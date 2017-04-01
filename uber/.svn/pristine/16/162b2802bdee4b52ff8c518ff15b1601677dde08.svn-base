
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
import com.gnomon.pdms.entity.PMManufactureCompEntity;
import com.gnomon.pdms.entity.PMProgramDevTypeEntity;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.entity.ProgramTypeEntity;
import com.gnomon.pdms.service.ManufactureCompService;
import com.gnomon.pdms.service.ProgramDevTypeService;
import com.gnomon.pdms.service.ProgramTypeService;
import com.gnomon.pdms.service.ProjectBaseInfoService;
import com.gnomon.pdms.service.VehiclePlatformService;
import com.gnomon.pdms.service.VehicleTypeService;

@Namespace("/pm")
public class ProjectBaseInfoAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	
	@Autowired
	private ProgramTypeService programTypeService;
	
	@Autowired
	private VehicleTypeService vehicleTypeService;
	
	@Autowired
	private VehiclePlatformService vehiclePlatFormService;
	
	@Autowired
	private ManufactureCompService manufactureCompService;
	
	@Autowired
	private ProgramDevTypeService programDevTypeService;
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private boolean judgePrivilege;
	public void setJudgePrivilege(boolean judgePrivilege) {
		this.judgePrivilege = judgePrivilege;
	}

	/**
	 * 【项目管理】基本信息取得
	 */
	public void getProjectBaseInfo() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> map =
					this.projectBaseInfoService.getProjectBaseInfo(programId);
			Map<String, Object> data = new HashMap<String, Object>();
			// ID
			data.put("id", map.get("ID"));
			// 项目编号
			data.put("code", map.get("CODE"));
			// 项目名称
			data.put("programName", map.get("PROGRAM_NAME"));
			// 项目总监ID
			data.put("director", map.get("DIRECTOR"));
			// 项目总监姓名
			data.put("directorName", map.get("DIRECTOR_NAME"));
			// 项目经理ID
			data.put("pm", map.get("PM"));
			// 项目经理
			data.put("pmName", map.get("PM_NAME"));
			// 质量经理ID
			data.put("qm", map.get("QM"));
			// 质量经理
			data.put("qmName", map.get("QM_NAME"));
			// 项目类型
			data.put("programType", map.get("PROGRAM_TYPE_ID"));
			// 项目类型名称
			data.put("programTypeName", map.get("PROGRAM_TYPE_NAME"));
			// 项目类型Code
			data.put("programTypeCode", map.get("PROGRAM_TYPE_CODE"));
			// 生产单位
			data.put("ManufactureCompId", map.get("MANUFACTURE_COMP_ID"));
			//级别
			data.put("programLevel", map.get("PROGRAM_LEVEL"));
			//产品定位
			data.put("productPositioning", map.get("PRODUCT_POSITIONING"));
			//开发状态
			data.put("devStatusCode", map.get("DEV_STATUS_CODE"));
			//开发状态名称
			data.put("devStatusName", map.get("DEV_STATUS_NAME"));
			//开发类型
			data.put("devTypeId", map.get("DEV_TYPE_ID"));
			//创建人
			data.put("createByName", map.get("CREATE_BY_NAME"));
			//优先级
			data.put("programPriorityCode", map.get("PROGRAM_PRIORITY_CODE"));
			//创建时间
			data.put("createDate", DateUtils.change((Date)map.get("CREATE_DATE")));
			// 车型类型
			data.put("vehicleType", map.get("VEHICLE_TYPE_ID"));
			// 车型平台
			data.put("vehiclePlatform", map.get("VEHICLE_PLATFORM_ID"));
			// 是否签订合同
			data.put("isOutContract", "Y".equals(map.get("IS_OUT_CONTRACT")));
			// 备注
			data.put("programMemo", map.get("PROGRAM_MEMO"));
			// 项目车型
			List<Map<String, Object>> vehicleList =
					this.projectBaseInfoService.getProjectPlanSOP(programId, false);
			if (vehicleList.size() > 0) {
				data.put("hasVehicle", true);
			} else {
				data.put("hasVehicle", false);
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
	 * SOP日期信息取得
	 */
	public void getProgramVehicleSOPList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.projectBaseInfoService.getProjectPlanSOP(
							programId, judgePrivilege);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 车型ID
				dataMap.put("id", map.get("ID"));
				// 车型编码
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				// 车型名称
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				// 项目经理
				dataMap.put("pm", map.get("PM"));
				// 项目经理姓名
				dataMap.put("pmName", map.get("PM_NAME"));
				// 质量经理
				dataMap.put("qm", map.get("QM"));
				// 质量经理姓名
				dataMap.put("qmName", map.get("QM_NAME"));
				// SOP日期
				dataMap.put("sopDate", DateUtils.change((Date)map.get("SOP_DATE")));
				// 主计划状态
				dataMap.put("planStatusName", map.get("PLAN_STATUS_NAME"));
				// 整车SOP时间
				dataMap.put("zcSopDate", DateUtils.change((Date)map.get("ZC_SOP_DATE")));
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
	 * 项目类型信息取得
	 */
	public void getProgramTypeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ProgramTypeEntity> list =
					this.programTypeService.getProgramTypeList();
			for(ProgramTypeEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", entity.getId());
				// 名称
				dataMap.put("name", entity.getName());
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
	 * 车型类型信息取得
	 */
	public void getVehicleTypeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehicleTypeService.getVehicleTypeList(programId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 名称
				dataMap.put("name", map.get("NAME"));
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
	 * 车型平台信息取得
	 */
	public void getVehiclePlatformList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehiclePlatFormService.getVehiclePlatformList(programId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 名称
				dataMap.put("name", map.get("NAME"));
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
	 * 生产单位信息取得
	 */
	public void getManufactureComp() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PMManufactureCompEntity> list =
					this.manufactureCompService.getManufactureCompList();
			for(PMManufactureCompEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", entity.getId());
				// 名称
				dataMap.put("name", entity.getName());
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
	 * 开发类型信息取得
	 */
	public void getDevType() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PMProgramDevTypeEntity> list =
					this.programDevTypeService.getDevTypeList();
			for(PMProgramDevTypeEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", entity.getId());
				// 名称
				dataMap.put("name", entity.getName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//	/**
//	 * 发布基本信息
//	 */
//	public void deployBaseInfo() {
//		try {
//			// JSON解析
//			Map<String, String> model = this.convertJson(this.model);
//			this.projectBaseInfoService.deployBaseInfo(programId, model);
//			this.writeSuccessResult(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.writeErrorResult(e.getMessage());
//		}
//	}
	
	/**
	 * 保存基本信息
	 */
	public void saveBaseInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectBaseInfoService.saveBaseInfo(programId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 修改车型信息
	 */
	public void updateVehicle() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectBaseInfoService.updateVehicle(programId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 增加车型信息
	 */
	public void addVehicle() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectBaseInfoService.addVehicle(programId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除车型信息
	 */
	public void deleteVehicle() {
		try {
			this.projectBaseInfoService.deleteVehicle(vehicleId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
