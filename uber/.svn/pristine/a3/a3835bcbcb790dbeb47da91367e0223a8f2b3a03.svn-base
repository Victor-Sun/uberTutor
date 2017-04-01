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
import com.gnomon.pdms.entity.PMProgramReportStatusVMEntity;
import com.gnomon.pdms.service.ProjectStatusReportService;
@Namespace("/pm")
public class ProjectStatusReportAction extends PDMSCrudActionSupport<PMProgramReportStatusVMEntity> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjectStatusReportService projectStatusReportService;

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
	
	private String statusReportId;
	public void setStatusReportId(String statusReportId) {
		this.statusReportId = statusReportId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	/**
	 * 项目状态信息取得
	 */
	public void getReportStatus() {
		try{
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			
			Map<String, Object> statusInfo =
					this.projectStatusReportService.getReportStatus(programVehicleId);
			// ID
			data.put("id", statusInfo.get("ID"));
			// 状态
			data.put("statusCodeTotal", statusInfo.get("STATUS_CODE_TOTAL"));
			// 备注
			data.put("memo", statusInfo.get("MEMO"));
			// 最近更新时间
			data.put("date", DateUtils.formate(
					(Date)statusInfo.get("CREATE_DATE"), "yyyy-MM-dd HH:mm:ss"));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 项目状态变更记录取得
	 */
	public void getReportStatusList() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.projectStatusReportService.getReportStatusList(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 变更者
				dataMap.put("createByName", map.get("CREATE_BY_NAME"));
				// 变更时间
				dataMap.put("createDate", DateUtils.formate((Date)map.get("CREATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
				// 状态
				dataMap.put("statusCodeTotal", map.get("STATUS_CODE_TOTAL"));
				// 备注
				dataMap.put("memo", map.get("MEMO"));
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
	 * 项目总体状态变更
	 */
	public void changeStatusReport() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectStatusReportService.changeReportStatus(
					programId, programVehicleId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	
	

	/**
	 * 状态报告-项目管理状态报告list取得
	 */
	public void getProjectDBStatusReport() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PMProgramReportStatusVMEntity> list =
					this.projectStatusReportService.getProjectDBStatusReport(
							programId, vehicleId);
			if (list.size() == 0) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", "");
				//项目进展
				dataMap.put("statusCodePs", "");
				//质量问题
				dataMap.put("statusCodeQi", "");
				//性能指标
				dataMap.put("statusCodePi", "");
				//整车成本
				dataMap.put("statusCodeVc", "");
				//投资预算
				dataMap.put("statusCodeIb", "");
				//车型ID
				dataMap.put("programVehicleId", vehicleId);
				// 项目ID
				dataMap.put("programId", programId);
				data.add(dataMap);
			} else {
				for (PMProgramReportStatusVMEntity entity : list) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", entity.getId());
					//项目进展
					dataMap.put("statusCodePs", entity.getStatusCodePs());
					//质量问题
					dataMap.put("statusCodeQi", entity.getStatusCodeQi());
					//性能指标
					dataMap.put("statusCodePi", entity.getStatusCodePi());
					//整车成本
					dataMap.put("statusCodeVc", entity.getStatusCodeVc());
					//投资预算
					dataMap.put("statusCodeIb", entity.getStatusCodeIb());
					//车型ID
					dataMap.put("programVehicleId", entity.getProgramVehicleId());
					// 项目ID
					dataMap.put("programId", entity.getProgramId());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 状态报告-项目管理状态报告list更新
	 */
	public void updateProjectStatusReport() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectStatusReportService.updateProjectStatusReport(
					programId, vehicleId, statusReportId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
