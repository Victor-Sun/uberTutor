package com.gnomon.pdms.action.dws;

import java.io.File;
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
import com.gnomon.pdms.entity.VIssueSourceEntity;
import com.gnomon.pdms.service.DeptworkspaceService;

@Namespace("/dws")
public class DeptworkspaceAction extends PDMSCrudActionSupport<VIssueSourceEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private VIssueSourceEntity vissueSourceEntity;

	@Autowired
	private DeptworkspaceService deptworkspaceService;
	
	@Override
	public VIssueSourceEntity getModel() {
		return vissueSourceEntity;
	}
	
	private String issueId;
	private String taskName;
	
	private String projectName;
	
	private String respUser;
	
	private String issueDescription;
	
	private String raiseBy;
	
	private File upload;
	
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	

	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public VIssueSourceEntity getVissueSourceEntity() {
		return vissueSourceEntity;
	}


	public void setVissueSourceEntity(VIssueSourceEntity vissueSourceEntity) {
		this.vissueSourceEntity = vissueSourceEntity;
	}


	public String getIssueDescription() {
		return issueDescription;
	}


	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}


	public String getRaiseBy() {
		return raiseBy;
	}


	public void setRaiseBy(String raiseBy) {
		this.raiseBy = raiseBy;
	}


	public String getIssueId() {
		return issueId;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getRespUser() {
		return respUser;
	}


	public void setRespUser(String respUser) {
		this.respUser = respUser;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}


	public void getDeptmanagementOpenIssueGrid() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = deptworkspaceService.getDeptmanagementOpenIssueGridService(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// 工作进展描述
//				dataMap.put("progressDesc", entity.getProgressDesc());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getDeptProjectTaskBarChart() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			String departmentId = SessionData.getLoginUser().getDepartmentId();
			List<Map<String, Object>> list =	this.deptworkspaceService.getDeptProjectTaskService(departmentId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("program", map.get("PROGRAM_CODE"));
				dataMap.put("data1", map.get("COMPLETE_PERCENT"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getDeptOpenIssueBarChart() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			String userId = SessionData.getLoginUserId();
			List<Map<String, Object>> list =	this.deptworkspaceService.getDeptOpenIssueBarChart(userId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("program", map.get("PROGRAM_CODE"));
				dataMap.put("data1", map.get("RAISED"));
				dataMap.put("data2", map.get("CLOSED"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getDeptProjectTaskList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.deptworkspaceService.getDeptProjectTaskList(
							searchModel, this.getPage(), this.getLimit());
			// 整理数据
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("taskId", map.get("ID"));
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				dataMap.put("attCnt", map.get("PROGRAM_CODE"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("taskTypeName", map.get("TASK_TYPE_NAME"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				//dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("taskPriorityName", map.get("TASK_PRIORITY_NAME"));
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				dataMap.put("publishByName", map.get("PUBLISH_BY_NAME"));
				dataMap.put("lastReportDate", DateUtils.formate((Date)map.get("LAST_REPORT_DATE"), "yyyy/MM/dd HH:mm:ss"));
				dataMap.put("plannedFinishDate", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
				//dataMap.put("programVehicleName", map.get("VEHICLE_NAME"));
				//dataMap.put("professionalFieldName", map.get("PROGRAM_VEHICLE_ID"));
				dataMap.put("delayDays", map.get("DAYS_DELAYED"));
				dataMap.put("actualFinishDate", DateUtils.formate((Date)map.get("ACTUAL_FINISH_DATE")));
				// 当前任务负责人
				dataMap.put("currentTaskOwner", map.get("CURRENT_TASK_OWNER"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
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
}
