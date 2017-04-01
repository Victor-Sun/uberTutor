package com.gnomon.pdms.action.pm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.Ext203ItemEntity;
import com.gnomon.pdms.entity.Ext203PreTask;
import com.gnomon.pdms.service.Ext203ItemService;
import com.gnomon.pdms.service.Ext203PreTaskService;

@Namespace("/pm")
public class Ext203itemAction extends PDMSCrudActionSupport<Ext203ItemEntity> {

	private static final long serialVersionUID = 3137875303793582703L;


	
	@Autowired
	private Ext203ItemService ext203ItemService;
	
	@Autowired
	private Ext203PreTaskService ext203PreTaskService;
	

	private String projectId;
	
	private Long stageId;
	
	private String obsId;
	
	private File upload;//导入文件
	
	private Long taskId;
	
	private Long id;
	
	private String json;
	
	public Long getId() {
		return id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long revisionId;
	
	private String isCompleted;
	private String remark;
	
	public File getUpload() {
		return upload;
	}

	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	private String uploadFileName;


	
	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}





	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public Ext203ItemEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getGantt(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("success", true);
			Map<String,Object> calendarsMap = new HashMap<String,Object>();
			Map<String,Object> metaDataMap = new HashMap<String,Object>();
			metaDataMap.put("projectCalendar", "General");
			calendarsMap.put("metaData", metaDataMap);
			List<Map<String,Object>> calendarsRowsData = new ArrayList<Map<String,Object>>();
			if(true){
				Map<String,Object> rowMap = new HashMap<String,Object>();
				rowMap.put("Id", "General");
				rowMap.put("Name", "General");
				rowMap.put("parentId", null);
				rowMap.put("DaysPerWeek", 7);
				rowMap.put("DaysPerMonth", 30);
				rowMap.put("HoursPerDay", 24);
				rowMap.put("WeekendFirstDay", 6);
				rowMap.put("WeekendSecondDay", 0);
				rowMap.put("WeekendsAreWorkdays", false);
				List<String> defaultAvailability = new ArrayList<String>();
				defaultAvailability.add("00:00-24:00");
				rowMap.put("DefaultAvailability", defaultAvailability);
				rowMap.put("leaf", true);
				List<Map<String,Object>> daysRowsData = new ArrayList<Map<String,Object>>();
				if(true){
					Map<String,Object> daysRowMap = new HashMap<String,Object>();
					daysRowMap.put("Id", 1);
					daysRowMap.put("Cls", "gnt-national-holiday");
					daysRowMap.put("Date", "2010-01-14");
					daysRowMap.put("Name", "Some big holiday");
					daysRowsData.add(daysRowMap);
				}
				if(true){
					Map<String,Object> daysRowMap = new HashMap<String,Object>();
					daysRowMap.put("Id", 2);
					daysRowMap.put("Cls", "gnt-chinese-holiday");
					daysRowMap.put("Date", "2010-02-14");
					daysRowMap.put("Name", "Chinese New Year");
					daysRowsData.add(daysRowMap);
				}
				rowMap.put("Days", daysRowsData);
				calendarsRowsData.add(rowMap);
			}
			
			if(true){
				Map<String,Object> rowMap = new HashMap<String,Object>();
				rowMap.put("Id", "NightShift");
				rowMap.put("Name", "Night Shift");
				rowMap.put("parentId", null);
				rowMap.put("DaysPerWeek", 5);
				rowMap.put("DaysPerMonth", 20);
				rowMap.put("HoursPerDay", 8);
				rowMap.put("WeekendFirstDay", 6);
				rowMap.put("WeekendSecondDay", 0);
				rowMap.put("WeekendsAreWorkdays", false);
				List<String> defaultAvailability = new ArrayList<String>();
				defaultAvailability.add("00:00-06:00");
				defaultAvailability.add("22:00-24:00");
				rowMap.put("DefaultAvailability", defaultAvailability);
				
				calendarsRowsData.add(rowMap);
			}
			calendarsMap.put("rows", calendarsRowsData);
			result.put("calendars", calendarsMap);
			
			List<Map<String, Object>> dependences = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> dependencelist = ext203ItemService.getExt203DependenceList();
			for(Map<String, Object> map:dependencelist){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("From", map.get("PRE_TASK_ID"));
				dataMap.put("To", map.get("TASK_ID"));
				dependences.add(dataMap);
			}
			Map<String,Object> dependencesMap = new HashMap<String,Object>();
			dependencesMap.put("rows", dependences);
			result.put("dependencies", dependencesMap);
			
			List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> assignmentList = ext203ItemService.getExt203AssignmentList(Long.valueOf(projectId));
			for(Map<String, Object> map:assignmentList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("TaskId", map.get("ID"));
				dataMap.put("ResourceId", map.get("TASK_OWNER"));
				dataMap.put("Units", 100);
				assignments.add(dataMap);
			}
			Map<String,Object> assignmentMap = new HashMap<String,Object>();
			assignmentMap.put("rows", assignments);
			result.put("assignments", assignmentMap);
			
			List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> resourcesList = ext203ItemService.getExt203ResourcesList();
			for(Map<String, Object> map:resourcesList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("Id", map.get("ID"));
//				dataMap.put("TaskId", map.get("ID"));
				dataMap.put("Name", map.get("USERNAME"));
//				dataMap.put("Units", 100);
				resources.add(dataMap);
			}
			Map<String,Object> resourcesMap = new HashMap<String,Object>();
			resourcesMap.put("rows", resources);
			result.put("resources", resourcesMap);
			
			Map<String,Object> taskMap = new HashMap<String,Object>();
			List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> taskList = ext203ItemService.getExt203TaskList(Long.valueOf(projectId),null);
			for(Map<String, Object> map:taskList){
				Map<String, Object> task = this.setTaskMap(map);
				this.getTaskTree(Long.valueOf(projectId), ((BigDecimal)map.get("ID")).longValue(), task);
				tasks.add(task);
			}
			taskMap.put("rows", tasks);
			result.put("tasks", taskMap);
//		result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void getTaskTree(Long projectId,Long parentId,Map<String,Object> parentTaskMap ) {
		List<Map<String, Object>> list = ext203ItemService.getExt203TaskList(projectId,parentId);
		
		if(list != null && list.size() > 0){
			List<Map<String, Object>> tasklist = new ArrayList<Map<String, Object>>();
			for(Map<String, Object> map:list){
				Map<String, Object> taskMap = setTaskMap(map);
				if(!((Boolean)taskMap.get("leaf") == true)){
					getTaskTree(projectId,(Long)taskMap.get("ID"),taskMap);
				}
				
				tasklist.add(taskMap);
			}
			parentTaskMap.put("children", tasklist);
		}
	}
	
	public Map<String, Object> setTaskMap(Map<String, Object> map){
		Map<String,Object> taskMap = new HashMap<String,Object>();
//		taskMap.put("BaselineEndDate", map.get("ID"));
		taskMap.put("Id", map.get("ID"));
		taskMap.put("Name", map.get("TASK_NAME"));
		taskMap.put("PercentDone", map.get("PERCENT_COMPLETE"));
		taskMap.put("StartDate", ObjectConverter.convertDate2String(map.get("PLANNED_START_DATE"), DateUtils.FORMAT_PATTEN_DATE));
		taskMap.put("BaselineStartDate", ObjectConverter.convertDate2String(map.get("PLANNED_START_DATE"), DateUtils.FORMAT_PATTEN_DATE));
		taskMap.put("Duration", 5);
//		taskMap.put("expanded", true);
		if(map.get("PARENT_ID") != null){
			taskMap.put("leaf", true);
		}
//		taskMap.put("Rollup", true);
		
		return taskMap;
	}
	

	
	public Map<String, Object> setTaskMap(Ext203ItemEntity entity){
		Map<String,Object> taskMap = new HashMap<String,Object>();
//		taskMap.put("BaselineEndDate", map.get("ID"));
		taskMap.put("Id", entity.getId());
//		taskMap.put("Name", entity.getTaskName());
//		taskMap.put("PercentDone", entity.getPercentComplete());
//		taskMap.put("StartDate", entity.getPlannedStartDate());
////		taskMap.put("BaselineStartDate", map.get("PLANNED_START_DATE"));
//		taskMap.put("Duration", entity.getPlannedDuration());
//		taskMap.put("expanded", true);
//		taskMap.put("Rollup", true);
		
		return taskMap;
	}
	
	public void saveGantt(){
		
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			String projectId = request.getParameter("projectId");
			String json = getJsonInput(request);
			JSONObject  jsonObj = JSONObject.fromObject(json);
			String type = jsonObj.get("type").toString();
			String requestId = jsonObj.get("requestId").toString();
			String revision = "";
			if(jsonObj.get("revision") != null){
				revision = jsonObj.get("revision").toString();
			}
			List<Map<String,Object>> responseAddTasks = new ArrayList<Map<String,Object>>();
			JSONObject tasks = (JSONObject)jsonObj.get("tasks");
			if(tasks != null){
				JSONArray addedTasks = (JSONArray)tasks.get("added");
				if(addedTasks != null){
					for(int i=0;i<addedTasks.size();i++){
						JSONObject jsonObject = (JSONObject)addedTasks.get(i);
						Ext203ItemEntity ext203ItemEntity = new Ext203ItemEntity();
						String parentId = jsonObject.get("parentId").toString();
						Boolean leaf = (Boolean)jsonObject.get("leaf");
						String startDate = jsonObject.get("StartDate").toString();
						if(!"null".equals(startDate)){
							
							try {
								ext203ItemEntity.setPlannedStartDate(DateUtils.strToDate(startDate));
							} catch (Exception e) {
								// TODO Auto-generated catch block
//								e.printStackTrace();
							}
						}
						String endDate = jsonObject.get("EndDate").toString();
						if(!"null".equals(endDate)){
							
							try {
								ext203ItemEntity.setPlannedFinishDate(DateUtils.strToDate(endDate));
							} catch (Exception e) {
								// TODO Auto-generated catch block
//								e.printStackTrace();
							}
						}
						
						String name = jsonObject.get("Name").toString();
						String duration = jsonObject.get("Duration").toString();
						String effort = jsonObject.get("Effort").toString();
						String effortUnit = jsonObject.get("EffortUnit").toString();
						String note = jsonObject.get("Note").toString();
						String durationUnit = jsonObject.get("DurationUnit").toString();
						String percentDone = jsonObject.get("PercentDone").toString();
						String deadlineDate = jsonObject.get("DeadlineDate").toString();
						if(StringUtils.isNotEmpty(parentId) && !"root".equals(parentId)){
							ext203ItemEntity.setParentId(Long.valueOf(parentId));
						}
						
						ext203ItemEntity.setTaskName(name);
						if(!"null".equals(duration)){
							ext203ItemEntity.setPlannedDuration(Long.valueOf(duration));
						}
						if(!"null".equals(effort)){
							ext203ItemEntity.setPlannedManhour(Long.valueOf(effort));
						}
						ext203ItemEntity.setExtProjectId(Long.valueOf(projectId));
						ext203ItemService.save(ext203ItemEntity);
						Map<String, Object> responseAddTaskMap = this.setTaskMap(ext203ItemEntity);
						if(jsonObject.get("PhantomId") != null){
							responseAddTaskMap.put("PhantomId", jsonObject.get("PhantomId").toString());
						}
						
						responseAddTasks.add(responseAddTaskMap);
					}
				}
				
//				ext203ItemService.addTaskList(list);
				
				
				JSONArray updatedTasks = (JSONArray)tasks.get("updated");
				List<Ext203ItemEntity> updatedList = new ArrayList<Ext203ItemEntity>();
				if(updatedTasks != null){
					for(int i=0;i<updatedTasks.size();i++){
						JSONObject jsonObject = (JSONObject)updatedTasks.get(i);
						String id = jsonObject.get("Id").toString();
						Ext203ItemEntity ext203ItemEntity = ext203ItemService.get(Long.valueOf(id));
						if(jsonObject.get("parentId") != null){
							String parentId = jsonObject.get("parentId").toString();
							ext203ItemEntity.setParentId(Long.valueOf(parentId));
						}
						String leaf = "N";
						if(jsonObject.get("leaf") == null){
							leaf = "";
						}else{
							leaf = jsonObject.get("leaf").toString();
						}
						if(jsonObject.get("StartDate") != null){
							ext203ItemEntity.setPlannedStartDate(DateUtils.strToDate(jsonObject.get("StartDate").toString()));
						}
						if(jsonObject.get("EndDate") != null){
							ext203ItemEntity.setPlannedFinishDate(DateUtils.strToDate(jsonObject.get("EndDate").toString()));
						}
						if(jsonObject.get("Name") != null){
							ext203ItemEntity.setTaskName(jsonObject.get("Name").toString());
						}
						if(jsonObject.get("Duration") != null){
							ext203ItemEntity.setPlannedDuration(Long.valueOf(jsonObject.get("Duration").toString()));
						}
						if(jsonObject.get("Note") != null){
							
						}
//						String durationUnit = jsonObject.get("DurationUnit").toString();
//						String percentDone = jsonObject.get("PercentDone").toString();
//						String deadlineDate = jsonObject.get("DeadlineDate").toString();
						if(jsonObject.get("PercentDone") != null){
							ext203ItemEntity.setPercentComplete(Double.valueOf(jsonObject.get("PercentDone").toString()));
						}
						
						
						if(jsonObject.get("Effort")!= null){
							ext203ItemEntity.setPlannedManhour(Long.valueOf(jsonObject.get("Effort").toString()));
						}
						
						updatedList.add(ext203ItemEntity);
					}
				}
				
				ext203ItemService.updateTaskList(updatedList);
			}
			
			
			
			List<Map<String,Object>> responseAddDependencies = new ArrayList<Map<String,Object>>();
			JSONObject dependencies = (JSONObject)jsonObj.get("dependencies");
			if(dependencies != null){
				JSONArray addedDependencies = (JSONArray)dependencies.get("added");
				if(addedDependencies != null){
					for(int i=0;i<addedDependencies.size();i++){
						JSONObject jsonObject = (JSONObject)addedDependencies.get(i);
						Ext203PreTask ext203PreTask = new Ext203PreTask();
						if(jsonObject.get("From") != null){
							ext203PreTask.setPreTaskId(Long.valueOf(jsonObject.get("From").toString()));
						}
						if(jsonObject.get("To") != null){
							ext203PreTask.setTaskId(Long.valueOf(jsonObject.get("To").toString()));
						}
						
						if(jsonObject.get("Lag") != null){
							ext203PreTask.setLagPeriod(Long.valueOf(jsonObject.get("Lag").toString()));
						}
						
						ext203PreTaskService.save(ext203PreTask);
						Map<String, Object> responseAddPreTaskMap = new HashMap<String, Object>();
						if(jsonObject.get("PhantomId") != null){
							responseAddPreTaskMap.put("$PhantomId", jsonObject.get("$PhantomId").toString());
						}
						responseAddPreTaskMap.put("Id", ext203PreTask.getId());
						responseAddDependencies.add(responseAddPreTaskMap);
					}
				}
			}	
			
			List<Map<String,Object>> responseAddAssignments = new ArrayList<Map<String,Object>>();
			JSONObject assignments = (JSONObject)jsonObj.get("assignments");
			if(assignments != null){
				JSONArray addedAssignments = (JSONArray)assignments.get("added");
				if(addedAssignments != null){
					for(int i=0;i<addedAssignments.size();i++){
						JSONObject jsonObject = (JSONObject)addedAssignments.get(i);
						if(jsonObject.get("ResourceId") != null){
							if(jsonObject.get("TaskId") != null){
								Ext203ItemEntity ext203ItemEntity = ext203ItemService.get(Long.valueOf(jsonObject.get("TaskId").toString()));
								ext203ItemEntity.setTaskOwner(jsonObject.get("ResourceId").toString());
								ext203ItemService.save(ext203ItemEntity);
//								Map<String, Object> responseAddAssignmentsMap = new HashMap<String, Object>();
//								if(jsonObject.get("PhantomId") != null){
//									responseAddAssignmentsMap.put("$PhantomId", jsonObject.get("$PhantomId").toString());
//								}
//								responseAddAssignmentsMap.put("Id", ext203PreTask.getId());
//								responseAddAssignments.add(responseAddAssignmentsMap);
							}
						}
						
						
					
					}
				}
			}	
//			System.out.println(projectId);
//			this.writeSuccessResult(null);
			if( tasks != null){
				this.getGanttSync(Long.valueOf(projectId),requestId,requestId,responseAddTasks,responseAddDependencies);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getGanttSync(Long projectId,String requestId,String revision,List<Map<String,Object>> responseAddTasks,List<Map<String,Object>> responseAddDependencies){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("success", true);
			result.put("requestId", requestId);
			result.put("revision", revision);	
//			Map<String,Object> calendarsMap = new HashMap<String,Object>();
//			Map<String,Object> metaDataMap = new HashMap<String,Object>();
//			metaDataMap.put("projectCalendar", "General");
//			calendarsMap.put("metaData", metaDataMap);
//			List<Map<String,Object>> calendarsRowsData = new ArrayList<Map<String,Object>>();
//			if(true){
//				Map<String,Object> rowMap = new HashMap<String,Object>();
//				rowMap.put("Id", "General");
//				rowMap.put("Name", "General");
//				rowMap.put("parentId", null);
//				rowMap.put("DaysPerWeek", 7);
//				rowMap.put("DaysPerMonth", 30);
//				rowMap.put("HoursPerDay", 24);
//				rowMap.put("WeekendFirstDay", 6);
//				rowMap.put("WeekendSecondDay", 0);
//				rowMap.put("WeekendsAreWorkdays", false);
//				List<String> defaultAvailability = new ArrayList<String>();
//				defaultAvailability.add("00:00-24:00");
//				rowMap.put("DefaultAvailability", defaultAvailability);
//				rowMap.put("leaf", true);
//				List<Map<String,Object>> daysRowsData = new ArrayList<Map<String,Object>>();
//				if(true){
//					Map<String,Object> daysRowMap = new HashMap<String,Object>();
//					daysRowMap.put("Id", 1);
//					daysRowMap.put("Cls", "gnt-national-holiday");
//					daysRowMap.put("Date", "2010-01-14");
//					daysRowMap.put("Name", "Some big holiday");
//					daysRowsData.add(daysRowMap);
//				}
//				if(true){
//					Map<String,Object> daysRowMap = new HashMap<String,Object>();
//					daysRowMap.put("Id", 2);
//					daysRowMap.put("Cls", "gnt-chinese-holiday");
//					daysRowMap.put("Date", "2010-02-14");
//					daysRowMap.put("Name", "Chinese New Year");
//					daysRowsData.add(daysRowMap);
//				}
//				rowMap.put("Days", daysRowsData);
//				calendarsRowsData.add(rowMap);
//			}
//			
//			if(true){
//				Map<String,Object> rowMap = new HashMap<String,Object>();
//				rowMap.put("Id", "NightShift");
//				rowMap.put("Name", "Night Shift");
//				rowMap.put("parentId", null);
//				rowMap.put("DaysPerWeek", 5);
//				rowMap.put("DaysPerMonth", 20);
//				rowMap.put("HoursPerDay", 8);
//				rowMap.put("WeekendFirstDay", 6);
//				rowMap.put("WeekendSecondDay", 0);
//				rowMap.put("WeekendsAreWorkdays", false);
//				List<String> defaultAvailability = new ArrayList<String>();
//				defaultAvailability.add("00:00-06:00");
//				defaultAvailability.add("22:00-24:00");
//				rowMap.put("DefaultAvailability", defaultAvailability);
//				
//				calendarsRowsData.add(rowMap);
//			}
//			calendarsMap.put("rows", calendarsRowsData);
//			result.put("calendars", calendarsMap);
//			
//			List<Map<String, Object>> dependences = new ArrayList<Map<String, Object>>();
//			List<Map<String, Object>> dependencelist = ext203ItemService.getExt203DependenceList();
//			for(Map<String, Object> map:dependencelist){
//				Map<String,Object> dataMap = new HashMap<String,Object>();
//				dataMap.put("id", map.get("ID"));
//				dataMap.put("From", map.get("PRE_TASK_ID"));
//				dataMap.put("To", map.get("TASK_ID"));
//				dependences.add(dataMap);
//			}
//			Map<String,Object> dependencesMap = new HashMap<String,Object>();
//			dependencesMap.put("rows", dependences);
//			result.put("dependencies", dependencesMap);
//			
//			List<Map<String, Object>> assignments = new ArrayList<Map<String, Object>>();
//			List<Map<String, Object>> assignmentList = ext203ItemService.getExt203AssignmentList(Long.valueOf(projectId));
//			for(Map<String, Object> map:assignmentList){
//				Map<String,Object> dataMap = new HashMap<String,Object>();
//				dataMap.put("id", map.get("ID"));
//				dataMap.put("TaskId", map.get("ID"));
//				dataMap.put("ResourceId", map.get("TASK_OWNER"));
//				dataMap.put("Units", 100);
//				assignments.add(dataMap);
//			}
//			Map<String,Object> assignmentMap = new HashMap<String,Object>();
//			assignmentMap.put("rows", assignments);
//			result.put("assignments", assignmentMap);
			
//			List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
//			List<Map<String, Object>> resourcesList = ext203ItemService.getExt203ResourcesList();
//			for(Map<String, Object> map:resourcesList){
//				Map<String,Object> dataMap = new HashMap<String,Object>();
//				dataMap.put("id", map.get("ID"));
//				dataMap.put("TaskId", map.get("ID"));
//				dataMap.put("ResourceId", map.get("TASK_OWNER"));
//				dataMap.put("Units", 100);
//				resources.add(dataMap);
//			}
//			Map<String,Object> resourcesMap = new HashMap<String,Object>();
//			resourcesMap.put("rows", resources);
//			result.put("resources", resourcesMap);
			
			Map<String,Object> taskMap = new HashMap<String,Object>();
//			List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
//			List<Map<String, Object>> taskList = ext203ItemService.getExt203TaskList(Long.valueOf(projectId),null);
//			for(Map<String, Object> map:taskList){
//				Map<String, Object> task = this.setTaskMap(map);
//				this.getTaskTree(Long.valueOf(projectId), ((BigDecimal)map.get("ID")).longValue(), task);
//				tasks.add(task);
//			}
			taskMap.put("rows", responseAddTasks);
			result.put("tasks", taskMap);
//			Map<String,Object> preTaskMap = new HashMap<String,Object>();
//			preTaskMap.put("rows", responseAddDependencies);
//			result.put("dependencies", preTaskMap);
//		result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	protected String getJsonInput(HttpServletRequest request) throws IOException{
	    BufferedReader br = new BufferedReader(new InputStreamReader(
	            (ServletInputStream) request.getInputStream()));
	    String line = null;
	    StringBuilder sb = new StringBuilder();
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    return sb.toString();
	}
	
	public void importProject() {
		try{
			String loginUserId = SessionData.getLoginUserId();
			ext203ItemService.importMSProject(upload,Long.valueOf(projectId),loginUserId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
  
}
