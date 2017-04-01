package com.gnomon.pdms.action.mp;

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
import com.gnomon.pdms.entity.PMProgramReportStatusVMEntity;
import com.gnomon.pdms.entity.PMTaskVehicleVMEntity;
import com.gnomon.pdms.service.ProgressReportService;

@Namespace("/mp")
public class ProgressReportAction extends PDMSCrudActionSupport<PMProgramReportStatusVMEntity> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProgressReportService progressReportService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * 进展报表-时程表Project数据取得
	 */
	public void getProjectData() {
		JsonResult result = new JsonResult();
		Map<String, Object> projectData =
				this.progressReportService.getProcessProjectData();
		Map<String, Object> data = new HashMap<String, Object>();
		// 项目开始结束时间
		data.put("start", DateUtils.change((Date)projectData.get("start")));
		data.put("end", DateUtils.change((Date)projectData.get("end")));
		// Stage
		List<Map<String,Object>> stageList = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("start", DateUtils.change((Date)projectData.get("start")));
		stageList.add(dataMap);
		data.put("stages", stageList);
		
		// 结果返回
		result.buildSuccessResult(data);
		Struts2Utils.renderJson(result);
	}
	
	/**
	 * Resources一览数据取得
	 */
	public void getResourcesData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 车型信息取得
			List<Map<String, Object>> list =
					this.progressReportService.getProgressResources(programId, vehicleId);
			for(Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("Id", map.get("ID"));
				if (PDMSCommon.isNotNull(vehicleId)) {
					dataMap.put("Name", map.get("VEHICLE_NAME"));
				} else if (PDMSCommon.isNotNull(programId)) {
					dataMap.put("Name", map.get("VEHICLE_NAME"));
				} else {
					dataMap.put("Name", map.get("PROGRAM_CODE") + "_" + map.get("VEHICLE_NAME"));
				}
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("expanded", false);
				dataMap.put("leaf", true);
				dataMap.put("Type", "G");
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
	 * Events数据取得
	 */
	public void getEventsData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 质量阀数据取得
			List<PMTaskVehicleVMEntity> gateList =
					this.progressReportService.getProgressEvents(programId, vehicleId);
			for (PMTaskVehicleVMEntity gate : gateList) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("ResourceId", gate.getVehicleId());
				dataMap.put("Name", gate.getTaskName());
				dataMap.put("ShortName", gate.getTaskName());
				dataMap.put("StartDate", DateUtils.formate(gate.getPlannedFinishDate()));
				dataMap.put("EndDate", DateUtils.formate(gate.getPlannedFinishDate()));
				if (PDMSConstants.GATE_STATUS_GRAY.equals(gate.getGateStatusCode())) {
					dataMap.put("Color", "gray");
				} else if (PDMSConstants.GATE_STATUS_GREEN.equals(gate.getGateStatusCode())) {
					dataMap.put("Color", "green");
				} else if (PDMSConstants.GATE_STATUS_RED.equals(gate.getGateStatusCode())) {
					dataMap.put("Color", "red");
				} else if (PDMSConstants.GATE_STATUS_YELLOW.equals(gate.getGateStatusCode())) {
					dataMap.put("Color", "yellow");
				}
				data.add(dataMap);
			}
			
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public PMProgramReportStatusVMEntity getModel() {
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
