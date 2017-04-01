package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.service.ProgramPlanImportManager;
import com.gnomon.pdms.service.ProjectListService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/pm")
public class ProjectListAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private ProjectListService projectListService;

	@Autowired
	private ProgramPlanImportManager programPlanImportManager;
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	private String usualProject;
	public void setUsualProject(String usualProject) {
		this.usualProject = usualProject;
	}
	
	private String searchProjectCode;
	public void setSearchProjectCode(String searchProjectCode) {
		this.searchProjectCode = searchProjectCode;
	}

	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	/**
	 * 【项目管理-项目列表】一览数据取得
	 */
	public void getProjectList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 常用项目
			String userId = "";
			if ("2".equals(usualProject)) {
				// 登录用户ID取得
				userId = SessionData.getLoginUserId();
			}
			GTPage<Map<String, Object>> pageResult =
					this.projectListService.getProjectList(userId, searchProjectCode,
							this.getPage(), this.getLimit(),this.getFilter(),this.getSort());
			for(Map<String, Object> map : pageResult.getItems()) {
				// SOP时间取得
				List<TaskEntity> sopList = this.projectListService.getProjectPlanSOP(
						PDMSCommon.nvl(map.get("ID")));
				String strSop = "";
				for (int i = 0; i < sopList.size(); i++) {
					TaskEntity task = sopList.get(i);
					if (i > 0) {
						strSop += "<br>";
					}
					strSop += DateUtils.change(task.getPlannedFinishDate());
					strSop += "【" + task.getTaskName() + "】";
				}
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 车型平台
				dataMap.put("vehiclePlatformName", map.get("VEHICLE_PLATFORM_NAME"));
				// 级别
				dataMap.put("programLevel", map.get("PROGRAM_LEVEL"));
				// 开发状态
				dataMap.put("devStatusName", map.get("DEV_STATUS_NAME"));
				// 项目代号
				dataMap.put("code", map.get("CODE"));
				// 项目名称
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				// 产品定位
				dataMap.put("productPositioning", map.get("PRODUCT_POSITIONING"));
				// 开发类型
				dataMap.put("devTypeName", map.get("DEV_TYPE_NAME"));
				// SOP时间
				dataMap.put("SOP", strSop);
				// 项目总监
				dataMap.put("director", map.get("DIRECTOR_NAME"));
				// 项目经理
				dataMap.put("pm", map.get("PM_NAME"));
				// 车型类型
				dataMap.put("vehicleTypeName", map.get("VEHICLE_TYPE_NAME"));
				// 排序字段
				dataMap.put("seq", map.get("SEQ"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 项目一览数据取得（以及检索条件）
	 */
	public void getProjectListNoPaging() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

			List<Map<String, Object>> list =
					this.projectListService.getProjectListNoPaging();
			for(Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 车型平台
				dataMap.put("vehiclePlatformName", map.get("VEHICLE_PLATFORM_NAME"));
				// 级别
				dataMap.put("programLevel", map.get("PROGRAM_LEVEL"));
				// 开发状态
				dataMap.put("devStatusName", map.get("DEV_STATUS_NAME"));
				// 项目代号
				dataMap.put("code", map.get("CODE"));
				// 项目名称
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				// 产品定位
				dataMap.put("productPositioning", map.get("PRODUCT_POSITIONING"));
				// 开发类型
				dataMap.put("devTypeName", map.get("DEV_TYPE_NAME"));
				// 项目总监
				dataMap.put("director", map.get("DIRECTOR_NAME"));
				// 项目经理
				dataMap.put("pm", map.get("PM_NAME"));
				// 车型类型
				dataMap.put("vehicleTypeName", map.get("VEHICLE_TYPE_NAME"));
				// 排序字段
				dataMap.put("seq", map.get("SEQ"));
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
	 * 新建项目
	 */
	public void createProject() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			String programId = this.projectListService.saveProjectInfo(model);
			// 发送通知
			Map<String, Object> notifyInfo = new HashMap<String, Object>();
			notifyInfo.put("programName", model.get("projectName"));
			notifyInfo.put("projectManager", model.get("projectManager"));
			notifyInfo.put("programId", programId);
			this.sysNoticeService.createProjectNotify(notifyInfo);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * 删除项目
	 */
	public void deleteProgram() {
		try {
			this.projectListService.deleteProgram(this.programId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更新项目序列
	 */
	public void updateProjectSort() {
		try{
			Map<String, String> model = this.convertJson(this.model);
			this.projectListService.updateProjectSort(model);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 项目一览取得
	 */
	public void getAllProgram() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> list = this.projectListService.getAllProgram();
			for(Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 项目Code
				dataMap.put("programCode", map.get("CODE"));
				// 项目名称
				dataMap.put("programName", map.get("PROGRAM_NAME"));
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
	 * 车型一览取得
	 */
	public void getAllVehicleByProgram() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> list =
					this.projectListService.getAllVehicleByProgram(programId);
			for(Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 车型Code
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				// 车型名称
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
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
