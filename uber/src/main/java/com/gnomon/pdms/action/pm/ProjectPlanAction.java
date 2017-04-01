package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMProgramPlanVMEntity;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.service.BaselineService;
import com.gnomon.pdms.service.ProjectPlanService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/pm")
public class ProjectPlanAction extends PDMSCrudActionSupport<TaskEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private ProjectPlanService projectPlanService;
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	@Autowired
	private BaselineService baselineService;

	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String obsId;
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	private String userId;
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private boolean chkStatus;
	public void setChkStatus(boolean chkStatus) {
		this.chkStatus = chkStatus;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 发布计划
	 */
	public void deployPlan() {
		try {
			List<Map<String, Object>> taskList = this.projectPlanService.deployPlan(
					programVehicleId, obsId);
//			// 项目状态更改通知
//			this.sysNoticeService.changeProgramStatusNotify(
//					programId, programVehicleId,
//					PDMSConstants.PROGRAM_STATUS_ACTIVE);
			// 任务下发通知
			this.sysNoticeService.deployTaskNotify(taskList);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更改主计划状态
	 */
	public void preDeployMainPlan() {
		try {
            String planStatus = PDMSConstants.PROGRAM_STATUS_INACTIVE;
            if (chkStatus) {
            	planStatus = PDMSConstants.PROGRAM_STATUS_PLANNED;
  		    }
			// 更改主计划状态
			this.projectPlanService.changePlanStatus(
					programVehicleId, null, planStatus);
			// 通知
			this.sysNoticeService.changeMajorPlanStatusNotify(
					programId, programVehicleId, planStatus);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更改二级计划状态
	 */
	public void preDeployDeptPlan() {
		try {
			String planStatus = PDMSConstants.PROGRAM_STATUS_INACTIVE;
            if (chkStatus) {
            	planStatus = PDMSConstants.PROGRAM_STATUS_PLANNED;
  		    }
			// 更改二级计划状态
			this.projectPlanService.changePlanStatus(
					programVehicleId, obsId, planStatus);
			// 通知
			this.sysNoticeService.changeDeptPlanStatusNotify(
					programId, obsId, planStatus);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 更改项目状态验证
	 */
	public void changeProgramStatusCheck() {
		try {
			// 非完成计划时，不需做验证
            if (! chkStatus) {
            	this.writeSuccessResult(null);
            	return;
  		    }
			// 更改项目状态验证
			int cnt = this.projectPlanService.changeProgramStatusCheck(programVehicleId);
			if (cnt > 0) {
				this.writeSuccessResult("存在没有指定关键活动的交付物，是否继续？");
			} else {
				this.writeSuccessResult(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
//	/**
//	 * 更改项目状态
//	 */
//	public void changeProgramStatus() {
//		try {
//			String planStatus = PDMSConstants.PROGRAM_STATUS_INACTIVE;
//            if (chkStatus) {
//            	planStatus = PDMSConstants.PROGRAM_STATUS_PLANNED;
//  		    }
//			// 更改项目状态
//			this.projectPlanService.changeProgramStatus(
//					programVehicleId, planStatus);
//			// 项目更改状态通知
//			this.sysNoticeService.changeProgramStatusNotify(
//					programId, programVehicleId, planStatus);
//			this.writeSuccessResult(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.writeErrorResult(e.getMessage());
//		}
//	}
	
	/**
	 * 计划变更
	 */
	public void changePlan() {
		try {
			// 计划变更
			this.projectPlanService.changePlan(programVehicleId, obsId);
			// 通知
			this.sysNoticeService.changePlanNotify(programId, programVehicleId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/**
	 * 项目计划取得
	 */
	public void getDeployPlan() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 主计划发布状况取得
			Page<PMProgramPlanVMEntity> planList =
					this.projectPlanService.getProgramPlanList(
							programVehicleId, this.getLimit(), this.getPage());
			
			for (PMProgramPlanVMEntity entity : planList.getResult()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//计划名称
				dataMap.put("planLevelName", entity.getPlanLevelName());
				
				dataMap.put("obsId", entity.getObsId());
				//专业领域
				if (entity.getPlanLevel() != null &&
						entity.getPlanLevel().intValue() ==
							PDMSConstants.PROGRAM_PLAN_LEVEL_2.intValue()) {
					dataMap.put("obsName", entity.getObsName());
				} else {
					dataMap.put("obsName", "");
				}
				
				//专业经理
				dataMap.put("obsManager", entity.getObsManager());
				//专业经理ID
				dataMap.put("obsManagerId", entity.getObsManagerId());
				//发布状态
				dataMap.put("planStatusName", entity.getPlanStatusName());
				//最近更新时间
				if (entity.getUpdateDate() == null) {
					dataMap.put("publishDate", DateUtils.formate(
							entity.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
				} else {
					dataMap.put("publishDate", DateUtils.formate(
							entity.getUpdateDate(), "yyyy/MM/dd HH:mm:ss"));
				}
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,Long.valueOf(planList.getTotalCount()).intValue());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得车型二级发布计划
	 */
	public void getDeptDeployPlan() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 主计划发布状况取得
			Page<PMProgramPlanVMEntity> planList =
					this.projectPlanService.getDeptPlanList(programVehicleId,this.getLimit(),this.getPage());
			
			for (PMProgramPlanVMEntity entity : planList.getResult()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//计划名称
				dataMap.put("planLevelName", entity.getPlanLevelName());
				
				dataMap.put("obsId", entity.getObsId());
				//专业领域
				if (entity.getPlanLevel() != null &&
						entity.getPlanLevel().intValue() ==
							PDMSConstants.PROGRAM_PLAN_LEVEL_2.intValue()) {
					dataMap.put("obsName", entity.getObsName());
				} else {
					dataMap.put("obsName", "");
				}
				
				//专业经理
				dataMap.put("obsManager", entity.getObsManager());
				//专业经理ID
				dataMap.put("obsManagerId", entity.getObsManagerId());
				//发布状态
				dataMap.put("planStatusName", entity.getPlanStatusName());
				//最近更新时间
				if (entity.getUpdateDate() == null) {
					dataMap.put("publishDate", DateUtils.formate(
							entity.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
				} else {
					dataMap.put("publishDate", DateUtils.formate(
							entity.getUpdateDate(), "yyyy/MM/dd HH:mm:ss"));
				}
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,Long.valueOf(planList.getTotalCount()).intValue());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 主计划发布状况取得
	 */
	public void getMainDeployPlan() {
		try{
			// 主计划发布状况取得
			Map<String, Object> planInfo =
					this.projectPlanService.getMainPlanInfo(programVehicleId);
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//ID
			dataMap.put("id", planInfo.get("ID"));
			//计划名称
			dataMap.put("planLevelName", planInfo.get("PLAN_LEVEL_NAME"));
			
			dataMap.put("obsId", planInfo.get("OBS_ID"));
			//专业领域
			if ("2".equals(PDMSCommon.nvl(planInfo.get("PLAN_LEVEL")))) {
				dataMap.put("obsName", planInfo.get("OBS_NAME"));
			} else {
				dataMap.put("obsName", "");
			}
			
			//专业经理
			dataMap.put("obsManager", planInfo.get("OBS_MANAGER"));
			//专业经理ID
			dataMap.put("obsManagerId", planInfo.get("OBS_MANAGER_ID"));
			//发布状态
			dataMap.put("planStatusName", planInfo.get("PLAN_STATUS_NAME"));
			//最近更新时间
			if (planInfo.get("UPDATE_DATE") == null) {
				dataMap.put("publishDate", DateUtils.formate(
						(Date)planInfo.get("CREATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
			} else {
				dataMap.put("publishDate", DateUtils.formate(
						(Date)planInfo.get("UPDATE_DATE"), "yyyy/MM/dd HH:mm:ss"));
			}
			if("ACTIVE".equals(planInfo.get("STATUS_CODE"))){
				// 主计划Title
				dataMap.put("title", baselineService.geDefaultBaseline(
						programVehicleId).get("TITLE"));
			}
			this.writeSuccessResult(dataMap);
			// 结果返回
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 项目计划状态取得
	 */
	public void getPlanStatus() {
		try{
			JsonResult result = new JsonResult();
			Map<String,Object> data = new HashMap<String,Object>();
			// 计划发布状况取得
			String planStatus = this.projectPlanService.getPlanStatus(
					programVehicleId, obsId);
//			// 项目状态取得
//			String programStatus = this.projectPlanService.getProgramStatus(
//					programVehicleId);
//			// 项目状态
//			data.put("programStatus", programStatus);
			// 计划状态
			data.put("planStatus", planStatus);
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
//	/**
//	 * 项目状态取得
//	 */
//	public void getProgramStatus() {
//		try{
//			JsonResult result = new JsonResult();
//			Map<String,Object> data = new HashMap<String,Object>();
//			// 项目状态取得
//			String status = this.projectPlanService.getProgramStatus(
//					programVehicleId);
//			// 项目状态
//			data.put("programStatus", status);
//			// 结果返回
//			result.buildSuccessResult(data);
//			Struts2Utils.renderJson(result);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 项目状态取得(全部)
//	 */
//	public void getProgramStatusList() {
//		try{
//			JsonResult result = new JsonResult();
//			// 项目状态取得
//			List<Map<String, Object>> data =
//					this.projectPlanService.getProgramStatusList(programId);
//			// 结果返回
//			result.buildSuccessResultForList(data, 1);
//			Struts2Utils.renderJson(result);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
	
	/**
	 * 导出计划模板
	 */
	public void exportProjectTemplate() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.projectPlanService.exportProjectTemplate(programVehicleId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 导入模板验证
	 */
	public void importPlanTemplateCheck() {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> sop =
					this.projectPlanService.importPlanTemplateCheck(programVehicleId);
			data.put("sop", DateUtils.change((Date)sop.get("PLANNED_FINISH_DATE")));
			this.writeSuccessResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
	
	public void updateFuncitonManager() {
		try {
			projectPlanService.updateFuncitonManager(obsId,userId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
}
