package com.gnomon.pdms.action.mw;

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
import com.gnomon.pdms.service.MyCooperationService;

@Namespace("/mw")
public class MyCooperationAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MyCooperationService myCooperationService;
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	/**
	 * 取得我参与的任务-部门OpenIssue列表
	 */
	public void getMyCooperationDeptIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.myCooperationService.getMyCooperationDeptIssue(
							searchModel, this.getPage(), this.getLimit(),
							this.getFilter(),this.getSort());
			for (Map<String, Object> issueMap : list.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", issueMap.get("ISSUE_ID"));
				// UUID
				dataMap.put("uuid", issueMap.get("ISSUE_UUID"));
				// 问题标题
				dataMap.put("issueTitle", issueMap.get("ISSUE_TITLE"));
				// 进度状态
				dataMap.put("progressStatus", issueMap.get("PROGRESS_STATUS"));
				// 提出日期
				dataMap.put("raiseDate", DateUtils.change((Date)issueMap.get("RAISE_DATE")));
				// 偏离计划完成天数
				dataMap.put("daysDelayed", issueMap.get("DAYS_DELAYED"));
				// 部门ID
				dataMap.put("deptId", issueMap.get("DEPT_ID"));
				// 部门
				dataMap.put("deptName", issueMap.get("DEPT_NAME"));
				// 科室ID
				dataMap.put("subDeptId", issueMap.get("SUB_DEPT_ID"));
				// 科室
				dataMap.put("subDeptName", issueMap.get("SUB_DEPT_NAME"));
				// 问题来源ID
				dataMap.put("issueSourceId", issueMap.get("ISSUE_SOURCE_ID"));
				// 问题来源
				dataMap.put("issueSourceTitle", issueMap.get("TITLE"));
				// 提出人ID
				dataMap.put("raiseBy", issueMap.get("RAISE_BY"));
				// 提出人
				dataMap.put("raiseByName", issueMap.get("RAISE_BY_NAME"));
				// 责任科室ID
				dataMap.put("respDeptId", issueMap.get("RESP_DEPT_ID"));
				// 责任科室
				dataMap.put("respDeptName", issueMap.get("RESP_DEPT_NAME"));
				// 责任人ID
				dataMap.put("respUserId", issueMap.get("RESP_USER_ID"));
				// 责任人
				dataMap.put("respUserName", issueMap.get("RESP_USER_NAME"));
				// 当前任务负责人
				dataMap.put("taskOwner", issueMap.get("TASK_OWNER"));
				// 完成状态
				dataMap.put("statusCode", issueMap.get("STATUS_CODE"));
				// 完成状态名称
				dataMap.put("statusCodeName", issueMap.get("STATUS_CODE_NAME"));
				// 计划完成日期
				dataMap.put("dueDate", DateUtils.change((Date)issueMap.get("DUE_DATE")));
				// 实际完成日期
				dataMap.put("actualFinishaDate", DateUtils.change((Date)issueMap.get("ACTUAL_FINISH_DATE")));
				// 问题描述
				dataMap.put("issueDescription", issueMap.get("ISSUE_DESCRIPTION"));
				// 备注
				dataMap.put("issueCause", issueMap.get("ISSUE_CAUSE"));
				// 创建人ID
				dataMap.put("createBy", issueMap.get("CREATE_BY"));
				// 创建人
				dataMap.put("createByName", issueMap.get("CREATE_BY_NAME"));
				// 优先级
				dataMap.put("issuePriorityCode", issueMap.get("ISSUE_PRIORITY_CODE"));
				// 问题来源说明
				dataMap.put("issueSourceDescription", issueMap.get("ISSUE_SOURCE_DESCRIPTION"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得我参与的任务-项目OpenIssue列表
	 */
	public void getMyCooperationPmIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.myCooperationService.getMyCooperationPmIssue(
							searchModel, this.getPage(), this.getLimit(),
							this.getFilter(),this.getSort());
			for (Map<String, Object> map : list.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("ISSUE_ID"));
				dataMap.put("issueUuid", map.get("ISSUE_UUID"));
				// 标题
				dataMap.put("issueTitle", map.get("ISSUE_TITLE"));
				// 问题来源
				dataMap.put("issueSourceTitle", map.get("TITLE"));
				// 提出人
				dataMap.put("raiseByName", map.get("RAISE_BY_NAME"));
				// 提出日期
				if(map.get("RAISE_DATE") != null){
					dataMap.put("raiseDate", DateUtils.formate((Date)map.get("RAISE_DATE")));
				}
				// 任务
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				// 原因
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				// 责任组
				dataMap.put("respObsName", map.get("RESP_OBS_NAME"));
				// 责任人
				dataMap.put("respUserName", map.get("RESP_USER_NAME"));
				// 完成状态
				dataMap.put("statusCode", map.get("STATUS_CODE"));
				// 完成状态名称
				dataMap.put("statusCodeName", map.get("STATUS_CODE_NAME"));
				// 进度状态
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				// 计划完成日期
				if(map.get("DUE_DATE") != null){
					dataMap.put("dueDate", DateUtils.formate((Date)map.get("DUE_DATE")));
				}
				
				// 实际完成日期
				if(map.get("ACTUAL_FINISH_DATE") != null){
					dataMap.put("actualFinishDate", DateUtils.formate((Date)map.get("ACTUAL_FINISH_DATE")));
				}
				dataMap.put("daysDelayed", map.get("DAYS_DELAYED"));
				//车型状态
				dataMap.put("vehicleStatusCode", map.get("VEHICLE_STATUS_CODE"));
				//当前任务负责人
				dataMap.put("taskOwner", map.get("TASK_OWNER"));
				// 优先级
				dataMap.put("issuePriorityName", map.get("ISSUE_PRIORITY_NAME"));
				// 所属项目
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				// 所属车型
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("issueSourceDescription", map.get("ISSUE_SOURCE_DESCRIPTION"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Object getModel() {
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

