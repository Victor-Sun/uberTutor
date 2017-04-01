package com.gnomon.pdms.action.dashboard;

import java.math.BigDecimal;
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
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMProgramVMEntity;
import com.gnomon.pdms.service.DashboardPMSService;
import com.gnomon.pdms.service.PmReportService;
import com.gnomon.pdms.service.TodoListService;

@Namespace("/dashboard")
public class DashboardPMSAction extends PDMSCrudActionSupport<PMProgramVMEntity> {

	private static final long serialVersionUID = 3137875303793582703L;
	
	@Autowired
	private DashboardPMSService dashboardPMSService;

	@Autowired
	private TodoListService todoListService;

	@Autowired
	private PmReportService pmReportService;
	
	/**
	  * 首页-常用项目list取得
	 */
	public void getDashCommonProject() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.dashboardPMSService.getDashCommonProject();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("code", map.get("CODE"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
		        dataMap.put("pmName", map.get("PM_NAME"));
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
	  * 首页-代办任务list取得
	 */
	public void getDashTodoList() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			String userId = SessionData.getLoginUserId();
			
			List<Map<String, Object>> list =
					this.todoListService.getTodoList(userId, 0);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("taskId", map.get("TASK_ID"));
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				dataMap.put("attCnt", map.get("ATT_CNT"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("taskTypeName", PDMSCommon.getTaskTypeName(
						PDMSCommon.nvl(map.get("PROCESS_STEP_ID")),
						PDMSCommon.nvl(map.get("PROCESS_CODE"))));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("taskPriorityName", map.get("TASK_PRIORITY_NAME"));
				dataMap.put("taskStatusCode", map.get("TASK_STATUS_CODE"));
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				dataMap.put("delayDays", map.get("DELAY_DAYS"));
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				dataMap.put("publishByName", map.get("PUBLISH_BY_NAME"));
				dataMap.put("lastReportDate", DateUtils.change((Date)map.get("LAST_REPORT_DATE")));
				dataMap.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				dataMap.put("processStepId", map.get("PROCESS_STEP_ID"));
				dataMap.put("currentStepId", map.get("CURRENT_STEP_ID"));
				dataMap.put("processTaskOwner", map.get("PROCESS_TASK_OWNER"));
				dataMap.put("processId", map.get("PROCESS_ID"));
				dataMap.put("processCode", map.get("PROCESS_CODE"));
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
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
	  * 多项目管理状态报告list取得
	 */
	public void getProjectReport() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =
					this.dashboardPMSService.getProjectReport(this.getPage(),this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", map.get("ID"));
				//车型名称
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				//项目ID
				dataMap.put("programId", map.get("PROGRAM_ID"));
				// 项目名称
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				//项目代号
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				//项目经理姓名
				dataMap.put("pmName", map.get("PM_NAME"));
				//项目总监
				dataMap.put("directorName", map.get("DIRECTOR_NAME"));
				//质量经理
				dataMap.put("qmName", map.get("QM_NAME"));
				//下一阀门
				dataMap.put("nextGate", map.get("NEXT_GATE"));
				// 项目状态
				dataMap.put("statusCodeTotal", map.get("STATUS_CODE_TOTAL"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	  * 首页-多项目管理状态报告list取得(无分页处理)
	 */
	public void getProjectReportNoPaging() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.dashboardPMSService.getProjectReportNoPaging();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", map.get("ID"));
				//车型名称
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				//项目ID
				dataMap.put("programId", map.get("PROGRAM_ID"));
				// 项目名称
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				//项目代号
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				//项目经理姓名
				dataMap.put("pmName", map.get("PM_NAME"));
				//项目总监
				dataMap.put("directorName", map.get("DIRECTOR_NAME"));
				//质量经理
				dataMap.put("qmName", map.get("QM_NAME"));
				//下一阀门
				dataMap.put("nextGate", map.get("NEXT_GATE"));
				// 项目状态
				dataMap.put("statusCodeTotal", map.get("STATUS_CODE_TOTAL"));
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
	 * 首页-节点完成报告取得
	 */
	public void getTaskCompleteCount() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<Map<String,Object>> list = this.pmReportService.getTaskCompleteCount();

			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 项目编号
				dataMap.put("process", map.get(PmReportService.KEY_CODE));
				// 计划完成
				dataMap.put("PlannedRatio", this.getRate(new BigDecimal(
						String.valueOf(map.get(PmReportService.KEY_TOTAL_COUNT))),
						String.valueOf(map.get(PmReportService.KEY_PLAN_COMPLETE_COUNT))));
				// 实际完成
				dataMap.put("ActualRatio", this.getRate(new BigDecimal(
						String.valueOf(map.get(PmReportService.KEY_TOTAL_COUNT))),
						String.valueOf(map.get(PmReportService.KEY_ACTUAL_COMPLETE_COUNT))));
				// 计划完成件数
				dataMap.put("plannedCnt", map.get(PmReportService.KEY_PLAN_COMPLETE_COUNT));
				// 实际完成件数
				dataMap.put("actualCnt", map.get(PmReportService.KEY_ACTUAL_COMPLETE_COUNT));
				data.add(dataMap);
			}

			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 首页权限取得
	 */
	public void getDashboardPMS() {
		try{
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String,Object>> list = this.dashboardPMSService.getDashboardPMS(userId);
			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("menuId", map.get("MENU_ID"));
				dataMap.put("displaySeq", map.get("DISPLAY_SEQ"));
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("text", map.get("TEXT"));
				dataMap.put("iconCls", map.get("ICON_CLS"));
				dataMap.put("xType", map.get("X_TYPE"));
				dataMap.put("widgetWidth", map.get("WIDGET_WIDTH"));
				dataMap.put("widgetType", map.get("WIDGET_TYPE"));
				dataMap.put("permission", map.get("PERMISSION"));

				data.add(dataMap);
			}
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);			
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	//完成率计算
	public float getRate(BigDecimal count, String issueCount) {
		float rate = 0;
		if (count.intValue() != 0) {
			rate = new BigDecimal(issueCount).
					multiply(new BigDecimal(100)).
					divide(count, 1, BigDecimal.ROUND_CEILING).floatValue();
		}
		return rate;
	}
	
	@Override
	public PMProgramVMEntity getModel() {
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
