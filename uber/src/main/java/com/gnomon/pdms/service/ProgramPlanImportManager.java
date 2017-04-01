package com.gnomon.pdms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.utils.POIUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.service.ProgramPlanTempImportManager.ProgramOrgTemp;

@Service
@Transactional
public class ProgramPlanImportManager {
	Log log = LogFactory.getLog(ProgramPlanImportManager.class);

	private static final String TABNAME_TEMP = "0.阀门和交付物模板";// 模板
	private static final String TABNAME_OBS = "1.项目组织架构和泳道";// 项目组织架构
	private static final String TABNAME_NODE = "2.车型、主节点、质量阀、阀门交付物和阶段";// 车型、主节点、质量阀、阀门交付物和阶段
	private static final String TABINAME_DEPT_NODE = "3.专业领域节点和交付物 ";// 专业领域节点和交付物

	private static final String TASK_TYPE_STR_MAINNODE = "主节点";
	private static final String TASK_TYPE_STR_SOP = "SOP节点";
	private static final String TASK_TYPE_STR_GATE = "质量阀";
	private static final String TASK_TYPE_STR_ACTIVITY = "虚拟活动";
	private static final String TASK_TYPE_STR_NODE = "节点";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private DeptPlanService deptPlanService;

	private List<ProgramOrgTemplate> programOrgTemplateList = new ArrayList<ProgramOrgTemplate>();
	private List<GateTemplate> gateTemplateList = new ArrayList<GateTemplate>();
	private List<GateDeliverableTemplate> gateDeliverableTemplateList = new ArrayList<GateDeliverableTemplate>();

	private List<ProgramOrg> programOrgList = new ArrayList<ProgramOrg>();
	private List<ProgramOrgMember> programOrgMemberList = new ArrayList<ProgramOrgMember>();
	private Map<String, Map<String, ProgramGateDelv>> gateDelvMap = new HashMap<String, Map<String, ProgramGateDelv>>();
	private Map<String, ProgramVehicle> programVehicleMap = new HashMap<String, ProgramVehicle>();
	private Map<String, ProgramTask> programTaskMap = new HashMap<String, ProgramTask>();
	
	private List<String> errorMsgs = new ArrayList<String>();
	
	public void addErrorMsg(String msg){
		errorMsgs.add(msg);
	}
	
	public String printErrorMsg(){
		StringBuffer sb = new StringBuffer();
		for (String errMsg : errorMsgs) {
			sb.append(errMsg).append("\n");
		}
		
		return sb.toString();
	}

	public void importProgramFromTemplate(String programId,String programVehicleCode,String programVehicleName,Date sopDate,String programTempId){
		//新建车型
		ProgramVehicle programVehicle = new ProgramVehicle();
		String uuid = getUUID();
		programVehicle.id = uuid;
		programVehicle.code = programVehicleCode;
		programVehicle.name = programVehicleName;
		programVehicle.seq = 1;
		String sql = "INSERT INTO PM_PROGRAM_VEHICLE(ID,PROGRAM_ID,VEHICLE_CODE,VEHICLE_NAME,SOP_DATE)  VALUES(?,?,?,?,?)";
		jdbcTemplate.update(sql, programVehicle.id,programId,programVehicle.code,programVehicle.name,sopDate);
		programVehicleMap.put(programVehicleCode, programVehicle);
		
		importProgramFromTemplate(programId,programVehicle.id,sopDate,programTempId);
	}
	
	public void importProgramFromTemplate(String programId,String programVehicleId,Date sopDate,String programTempId){		
		String userId = SessionData.getLoginUserId();//user uuid
		//更新车型的SOP日期
		String sql = "update PM_PROGRAM_VEHICLE set SOP_DATE=? WHERE ID=?";
		jdbcTemplate.update(sql,sopDate,programVehicleId);
		
		Map<String,String> oldIdAndNewIdMap = new HashMap<String,String>();
		//根据模板创建项目组织
		sql = "select * from PM_TEMP_OBS WHERE PROGRAM_ID=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		for (Map<String, Object> map : list) {
			ProgramOrg programOrg = new ProgramOrg();
			String oldId = ObjectConverter.convert2String(map.get("ID"));
			String newId = getUUID();
			programOrg.id = newId;
			programOrg.code= ObjectConverter.convert2String(map.get("OBS_CODE"));
			programOrg.name = ObjectConverter.convert2String(map.get("OBS_NAME"));
			programOrg.type = ObjectConverter.convert2String(map.get("OBS_TYPE_CODE"));
			String oldParentId = ObjectConverter.convert2String(map.get("PARENT_ID"));
			programOrg.oldParentId = oldParentId;
			
			boolean updateFlag = false;
			// upd by Lisa Bug修改
			if(PDMSCommon.isNull(oldParentId)){
				//如果已经创建的默认项目组织根节点，则不再导入模板的根节点,使用已存在的根节点
				sql = "select * from PM_OBS WHERE PROGRAM_VEHICLE_ID = ? AND PARENT_ID IS NULL ";
				List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql,programVehicleId);
				if(list1.size()>0){
					// upd by Lisa Bug修改
					Map<String, Object> rootOrgMap = list1.get(0);
					newId = ObjectConverter.convert2String(rootOrgMap.get("ID"));
					programOrg.id = newId;
					updateFlag = true;
				}
			}
			
			int planLevel = 1;
			if(updateFlag){
				planLevel = 1;
				//更新已存在的节点名称为模板中的节点名称
				sql = "UPDATE PM_OBS SET OBS_CODE=?,OBS_NAME=?,OBS_TYPE_CODE=?,PLAN_LEVEL=?,IS_LEAF='N' WHERE ID=?";
				jdbcTemplate.update(sql, programOrg.code, programOrg.name,programOrg.type, planLevel,programOrg.id);
			}else{
				//插入非根节点
				planLevel = -1;
				if(PDMSConstants.OBS_TYPE_RESP_DEPT.equals(programOrg.type)){
					planLevel = 2;
				}
				sql = "INSERT INTO PM_OBS(ID,OBS_CODE,OBS_NAME,PARENT_ID,PROGRAM_ID,OBS_TYPE_CODE,PLAN_LEVEL,PROGRAM_VEHICLE_ID) VALUES(?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(sql, programOrg.id,programOrg.code, programOrg.name,null, programId, programOrg.type, planLevel,programVehicleId);
				updateCreateUserAndCreateDate("PM_OBS", programOrg.id, userId);
			}
			if(planLevel > 1){
				sql="INSERT INTO PM_PROGRAM_PLAN(ID, PROGRAM_ID, PROGRAM_VEHICLE_ID, PLAN_LEVEL, OBS_ID, STATUS_CODE, CREATE_BY, CREATE_DATE, IS_ACTIVE)"+
				     " SELECT SYS_GUID(), ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL";
				jdbcTemplate.update(sql,programId,programVehicleId,planLevel,programOrg.id,PDMSConstants.PROGRAM_STATUS_INACTIVE,"SYS",new Date(),"Y");
			}
			
			oldIdAndNewIdMap.put(oldId, newId);

			programOrgList.add(programOrg);
			
		}
		
		//根据模板创建泳道
		sql = "select * from PM_TEMP_FUNCTION WHERE PROGRAM_ID=?";
		list = jdbcTemplate.queryForList(sql,programTempId);
		for (Map<String, Object> map : list) {
			ProgramFunction programFunction = new ProgramFunction();
			programFunction.id  = getUUID();
			String oldId = ObjectConverter.convert2String(map.get("ID"));
			oldIdAndNewIdMap.put(oldId, programFunction.id);
			
			programFunction.name = ObjectConverter.convert2String(map.get("DISPLAY_NAME"));
			programFunction.parentProgramOrgId = oldIdAndNewIdMap.get(ObjectConverter.convert2String(map.get("PARENT_OBS_ID")));//old id转换为new id
			programFunction.programOrgId = oldIdAndNewIdMap.get(ObjectConverter.convert2String(map.get("CHILD_OBS_ID")));//old id转换为new id
			programFunction.seq = ObjectConverter.convert2Integer(map.get("SEQ_NO"));
			
			sql = "INSERT INTO PM_FUNCTION(ID,DISPLAY_NAME,PARENT_OBS_ID,CHILD_OBS_ID,PROGRAM_ID,PROGRAM_VEHICLE_ID,SEQ_NO) VALUES(?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, programFunction.id,programFunction.name, programFunction.parentProgramOrgId,programFunction.programOrgId,programId,programVehicleId,programFunction.seq);
			
		}
		
		//update parent 项目组织
		for(ProgramOrg programOrg:programOrgList){
			String newParentId = oldIdAndNewIdMap.get(programOrg.oldParentId);
			sql = "UPDATE PM_OBS SET PARENT_ID = ? WHERE ID=?";
			jdbcTemplate.update(sql, newParentId,programOrg.id);
		}
		
		//根据模板创建主节点(SOP节点）、质量阀、专业领域活动和节点
		sql = "select * from PM_TEMP_TASK WHERE PROGRAM_ID=?  order by create_date";
		list = jdbcTemplate.queryForList(sql,programTempId);
		Map<String,String> newIdAndOldParentIdList = new HashMap<String,String>();
		for (Map<String, Object> map : list) {
			ProgramTask programTask = new ProgramTask();
			String oldId = ObjectConverter.convert2String(map.get("ID"));
			String newId = getUUID();
			oldIdAndNewIdMap.put(oldId, newId);
			programTask.id = newId;
			programTask.code = ObjectConverter.convert2String(map.get("TASK_CODE"));
			programTask.name = ObjectConverter.convert2String(map.get("TASK_NAME"));
			programTask.isShow = getBooleanValue(ObjectConverter.convert2String(map.get("IS_SHOW")));
			programTask.type = ObjectConverter.convert2String(map.get("TASK_TYPE_CODE"));
			String oldParentId = ObjectConverter.convert2String(map.get("PARENT_ID"));
			if(StringUtils.isNotEmpty(oldParentId)){
				newIdAndOldParentIdList.put(newId,oldParentId);
			}
			
			Integer START_DAYS_TO_SOP = ObjectConverter.convert2Integer(map.get("START_DAYS_TO_SOP"));
			if(START_DAYS_TO_SOP != null){
				programTask.planStartDate = getDateBySOPDateAndDays(sopDate,START_DAYS_TO_SOP);
			}
			Integer FINISH_DAYS_TO_SOP = ObjectConverter.convert2Integer(map.get("FINISH_DAYS_TO_SOP"));
			if(FINISH_DAYS_TO_SOP != null){
				programTask.planFinishDate = getDateBySOPDateAndDays(sopDate,FINISH_DAYS_TO_SOP);
				if(programTask.planStartDate == null){
					programTask.planStartDate = programTask.planFinishDate;//计划开始日期如果为空，默认与计划完成日期相同
				}
			}
			
			String oldObsId = ObjectConverter.convert2String(map.get("OBS_ID"));
			String obsId = null;
			if(StringUtils.isNotEmpty(oldObsId)){
				obsId = oldIdAndNewIdMap.get(oldObsId);
			}
			String oldGateId = ObjectConverter.convert2String(map.get("GATE_ID"));
			String gateId  = null;
			if(StringUtils.isNotEmpty(oldGateId)){
				gateId = oldIdAndNewIdMap.get(oldGateId);
			}
			String oldRelationTaskId = ObjectConverter.convert2String(map.get("RELATION_TASK_ID"));
			String relationTaskId  = null;
			if(StringUtils.isNotEmpty(oldRelationTaskId)){
				relationTaskId = oldIdAndNewIdMap.get(oldRelationTaskId);
			}
			
			programTask.planLevel = ObjectConverter.convert2Integer(map.get("PLAN_LEVEL"));
			
			//save task
			sql = "INSERT INTO PM_TASK(ID,TASK_CODE,TASK_NAME,PLANNED_START_DATE,PLANNED_FINISH_DATE,GATE_ID,OBS_ID,RELATION_TASK_ID,TASK_TYPE_CODE,TASK_STATUS_CODE,PROGRAM_ID,PROGRAM_VEHICLE_ID,PLAN_LEVEL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, newId, programTask.code, programTask.name,programTask.planStartDate, programTask.planFinishDate,gateId,obsId,relationTaskId, programTask.type, PDMSConstants.TASK_STATUS_NOT_START, programId,programVehicleId,programTask.planLevel);
			updateCreateUserAndCreateDate("PM_TASK", newId, userId);
			
		}
		
		//保存上级任务
		for(String newTaskId : newIdAndOldParentIdList.keySet()){
			String oldParentTaskId = newIdAndOldParentIdList.get(newTaskId);
			String newParentTaskId = oldIdAndNewIdMap.get(oldParentTaskId);
			sql = "UPDATE PM_TASK SET PARENT_ID = ? WHERE ID=?";
			jdbcTemplate.update(sql, newParentTaskId,newTaskId);
		}
		
		// 保存质量阀关联交付物
		sql = "SELECT T1.* FROM PM_TEMP_DELIVERABLE T1,PM_TEMP_TASK T2 WHERE T1.TASK_ID=T2.ID AND T2.PROGRAM_ID=?";
		list = jdbcTemplate.queryForList(sql, programTempId);
		for (Map<String, Object> map : list) {
			String oldDelvId = ObjectConverter.convert2String(map.get("ID"));
			String newDelvId = getUUID();
			oldIdAndNewIdMap.put(oldDelvId, newDelvId);
			String CODE = ObjectConverter.convert2String(map.get("CODE"));
			String NAME = ObjectConverter.convert2String(map.get("NAME"));
			//String DESCRIPTION = ObjectConverter.convert2String(map.get("DESCRIPTION"));
			String TEMP_OBS_ID = ObjectConverter.convert2String(map.get("OBS_ID"));
			String obsId = oldIdAndNewIdMap.get(TEMP_OBS_ID);
			String TEMP_GATE_ID = ObjectConverter.convert2String(map.get("TASK_ID"));
			String gateId = oldIdAndNewIdMap.get(TEMP_GATE_ID);
			String IS_KEY = ObjectConverter.convert2String(map.get("IS_KEY"));
			String HELP_URL = ObjectConverter.convert2String(map.get("HELP_URL"));
			// 保存阀门交付物
			sql = "INSERT INTO PM_DELIVERABLE(ID,NAME,CODE,TASK_ID,OBS_ID,IS_KEY,IS_FIT,HELP_URL) VALUES(?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, newDelvId, NAME, CODE, gateId, obsId, IS_KEY, "Y",HELP_URL);
			updateCreateUserAndCreateDate("PM_DELIVERABLE", newDelvId, userId);

			// 保存检查项
			sql = "SELECT * FROM PM_TEMP_DELIVERABLE_CHECKITEM T1,PM_TEMP_DELIVERABLE T2 WHERE T1.DELIVERABLE_ID=?";
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql, oldDelvId);
			for (Map<String, Object> checkitemMap : list1) {
				Integer seq = ObjectConverter.convert2Integer(checkitemMap.get("SEQ"));
				String checkitemName = ObjectConverter.convert2String(checkitemMap.get("NAME"));
				String checkRequirement = ObjectConverter.convert2String(checkitemMap.get("CHECK_REQUIREMENT"));
				sql = "INSERT INTO PM_DELIVERABLE_CHECKITEM(ID,SEQ,NAME,CHECK_REQUIREMENT,DELIVERABLE_ID) VALUES(?,?,?,?,?)";
				String id = getUUID();
				jdbcTemplate.update(sql, id, seq, checkitemName, checkRequirement, newDelvId);
				updateCreateUserAndCreateDate("PM_DELIVERABLE_CHECKITEM", id, userId);

			}
		}
		
		//保存任务与阀门交付物的关联
		sql = "SELECT T2.* FROM PM_TEMP_TASK_DELIVERABLE T2,PM_TEMP_TASK T3 WHERE T2.TASK_ID=T3.ID AND T3.PROGRAM_ID=?";
		list = jdbcTemplate.queryForList(sql, programTempId);
		for (Map<String, Object> map : list) {
			String oldTaskId = ObjectConverter.convert2String(map.get("TASK_ID"));
			String taskId = oldIdAndNewIdMap.get(oldTaskId);
			String oldDeliverableId = ObjectConverter.convert2String(map.get("DELIVERABLE_ID"));
			String deliverableId = oldIdAndNewIdMap.get(oldDeliverableId);
			sql = "INSERT INTO PM_TASK_DELIVERABLE(ID,TASK_ID,DELIVERABLE_ID) VALUES(?,?,?)";
			String id = getUUID();
			jdbcTemplate.update(sql, id, taskId, deliverableId);
			updateCreateUserAndCreateDate("PM_TASK_DELIVERABLE", id, userId);
		}
		//保存任务关联的泳道
		sql = "SELECT T2.* FROM PM_TEMP_FUNCTION T1,PM_TEMP_FUNCTION_TASK T2 WHERE T1.PROGRAM_ID=? AND T1.ID=T2.FUNCTION_ID";
		list = jdbcTemplate.queryForList(sql, programTempId);
		for (Map<String, Object> map : list) {
			String functionId = oldIdAndNewIdMap.get(ObjectConverter.convert2String(map.get("FUNCTION_ID")));//新旧ID转换
			String taskId = oldIdAndNewIdMap.get(ObjectConverter.convert2String(map.get("TASK_ID")));//新旧ID转换
			sql = "INSERT INTO PM_FUNCTION_TASK(FUNCTION_ID,TASK_ID) VALUES(?,?)";
			jdbcTemplate.update(sql, functionId, taskId);
		}
		
		//OBS和TASK树节点排序
		resequenceService.requencePmObs(programId, programVehicleId);
		resequenceService.requencePmTask(programId, programVehicleId);
		
	}

//	private Integer getPlanLevelByTaskTypeCode(String type) {
//		Integer planLevel = 1;
//		if(PDMSConstants.OBS_TYPE_RESP_DEPT.equals(type) || PDMSConstants.OBS_TYPE_KEY_DEPT_SYSTEM.equals(type)){
//			planLevel = 1;
//		}else{
//			planLevel = 2;
//		}
//		return planLevel;
//	}

	private Date getDateBySOPDateAndDays(Date sopDate,int days){
		return DateUtils.addDays(sopDate, days);
	}
	
	
	/**
	 * 导入节点管理清单
	 */
	public void importProgramNodeFromExcel(String programVehicleId, String obsId, InputStream input) {
		try {
			Workbook wb = POIUtils.createWorkbook(input);
			// 解析项目模板
			Sheet sheet = wb.getSheet("二级节点管理清单");
			if(sheet != null){
				importProgramNode(sheet, programVehicleId, obsId);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	//private List<String> exceptionList = new ArrayList<String>();

	/**
	 * 导入二级计划
	 */
	private void importProgramNode(Sheet sheet, String programVehicleId, String functionGroupId) {
		List<String> errorList = new ArrayList<String>();
		List<Map<String, String>> modelList = new ArrayList<Map<String, String>>();
		Map<String,Object> nodeMap = new HashMap<String,Object>();
		int lineNo = 0;
		List<String> ErrorListByRow = null;
		Map<String, String> model = null;
		
		// 节点类型
		List<String> taskTypeName = new ArrayList<String>();
		taskTypeName.add("节点");
		taskTypeName.add("活动");
		
		String programId = jdbcTemplate.queryForObject("SELECT PROGRAM_ID FROM PM_PROGRAM_VEHICLE WHERE ID=?", String.class, programVehicleId);
		Map<String, Object> functionGroupMap = jdbcTemplate.queryForMap("SELECT * FROM PM_OBS WHERE ID=?", functionGroupId);
		String functionGrouptName = ObjectConverter.convert2String(functionGroupMap.get("OBS_NAME"));

		// 循环行Row
		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (!POIUtils.isEmpty(row)) {
				model = new HashMap<String, String>();
				ErrorListByRow = new ArrayList<String>();
				lineNo = rowNum + 1;
				debugLog("验证行号=" + (lineNo));
				String taskName = POIUtils.getStringCellValue(row.getCell(1));//节点名称
				String taskType = POIUtils.getStringCellValue(row.getCell(2));//节点类型
				String respObsName = POIUtils.getStringCellValue(row.getCell(3));//负责主体
				String respUsername = POIUtils.getStringCellValue(row.getCell(4));//负责人
				Date planStartDate = POIUtils.getDateCellValue(row.getCell(5));//计划开始时间
				Date planFinishDate = POIUtils.getDateCellValue(row.getCell(6));//计划截止时间
				String durationDays = POIUtils.getStringCellValue(row.getCell(7));//周期（天）
				String delvName = POIUtils.getStringCellValue(row.getCell(8));//交付物名称
				String checkitem = POIUtils.getStringCellValue(row.getCell(9));//检查项目
				String requirement = POIUtils.getStringCellValue(row.getCell(10));//通过要求

				/*-------------- 计划信息Check --------------*/
				// 节点名称Check
				if (PDMSCommon.isNull(taskName)) {
					ErrorListByRow.add("第"+lineNo+"行：节点名称不允许为空。");
				} else {
					if (nodeMap.containsKey(taskName)) {
						ErrorListByRow.add("第"+lineNo+"行：节点名称不允许重复。节点名称=" + taskName);
					} else {
						nodeMap.put(taskName, null);
					}
				}
				
				// 节点类型
				if (PDMSCommon.isNull(taskType)) {
					ErrorListByRow.add("第"+lineNo+"行：节点类型不允许为空。");
				} else if (taskTypeName.indexOf(taskType) < 0) {
					ErrorListByRow.add("第"+lineNo+"行：节点类型只能输入【节点】【活动】。节点类型=" + taskType);
				}
				
				// 负责主体
				if (PDMSCommon.isNull(respObsName)) {
					ErrorListByRow.add("第"+lineNo+"行：负责主体不允许为空。");
				} else {
					int obsCnt = this.jdbcTemplate.queryForInt(
							"SELECT COUNT(ID) FROM PM_OBS WHERE OBS_NAME = ? AND FUNCTION_GROUP_ID = ?",
							respObsName, functionGroupId);
					if(obsCnt == 0){
						ErrorListByRow.add("第"+lineNo+"行：负责主体不属于本专业领域。专业领域=" + functionGrouptName + ", 负责主体=" + respObsName);
					}
				}
				
				// 负责人
				if (PDMSCommon.isNull(respUsername)) {
					ErrorListByRow.add("第"+lineNo+"行：负责人不允许为空。");
				} else {
					int userCnt = this.jdbcTemplate.queryForInt(
							"SELECT COUNT(ID) FROM V_PM_OBS_MEMBER WHERE USER_NAME = ? AND TOP_OBS_ID = ?",
							respUsername, functionGroupId);
					if(userCnt == 0){
						ErrorListByRow.add("第"+lineNo+"行：负责人不属于本专业领域。专业领域=" + functionGrouptName + ", 负责人=" + respUsername);
					}
				}
				
				// 计划时间
				if ("节点".equals(taskType)) {
					if (planFinishDate == null) {
						ErrorListByRow.add("第"+lineNo+"行：截止时间不允许为空。");
					}
				} else if ("活动".equals(taskType)) {
					if (planStartDate == null && planFinishDate == null ||
							planStartDate == null && PDMSCommon.isNull(durationDays) ||
							planFinishDate == null && PDMSCommon.isNull(durationDays)) {
						ErrorListByRow.add("第"+lineNo+"行：必须输入开始时间、截止时间、周期（天）中的任意两项。");
					} else if (planStartDate != null && planFinishDate != null &&
							planStartDate.compareTo(planFinishDate) > 0) {
						ErrorListByRow.add("第"+lineNo+"行：活动开始时间不允许晚于结束时间。");
					}
				}
				
				if (ErrorListByRow.size() > 0) {
					errorList.addAll(ErrorListByRow);
				} else {
					/*-------------- 计划信息Model做成 --------------*/
					// 节点名称
					model.put("taskName", taskName);
					// 节点类型
					if ("活动".equals(taskType)) {
						model.put("taskTypeCode", PDMSConstants.TASK_TYPE_ACTIVITY);
					} else {
						model.put("taskTypeCode", PDMSConstants.TASK_TYPE_NODE);
					}
					// 负责主体
					String respObsId = "";
					List<Map<String, Object>> obsList = this.jdbcTemplate.queryForList(
							"SELECT ID FROM PM_OBS WHERE OBS_NAME = ? AND FUNCTION_GROUP_ID = ?",
							respObsName, functionGroupId);
					if (obsList.size() > 0) {
						respObsId = PDMSCommon.nvl(obsList.get(0).get("ID"));
						model.put("respObsId", respObsId);
					}
					// 负责人
					List<Map<String, Object>> userList = this.jdbcTemplate.queryForList(
							"SELECT USER_ID FROM V_PM_OBS_MEMBER WHERE USER_NAME = ? AND TOP_OBS_ID = ?",
							respUsername, functionGroupId);
					if (userList.size() > 0) {
						model.put("taskOwner", PDMSCommon.nvl(userList.get(0).get("USER_ID")));
					}
					// 计划时间
					model.put("durationDays", durationDays);
					if (planStartDate != null) {
						model.put("plannedStartDate", DateUtils.change(planStartDate));
					}
					if (planFinishDate != null) {
						model.put("plannedFinishDate", DateUtils.change(planFinishDate));
					}
					// 交付物名称
					model.put("deliverableName", delvName);
					// 交付物检查项
					model.put("checkitemName", checkitem);
					// 交付物通过要求
					model.put("checkRequirement", requirement);
					// 时程表Title位置
					model.put("titleDispLocation", "TITLE_DISP_LOCATION_UP");
					modelList.add(model);
				}
			} 
		}
		
		/*-------------- 错误信息抛出 --------------*/
		if (errorList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String msg : errorList) {
				sb.append(msg).append(";\n");
			}
			throw new RuntimeException(sb.toString());
		}
		
		/*-------------- 计划信息导入 --------------*/
		for (Map<String, String> taskModel : modelList) {
			List<Map<String, Object>> taskList = this.jdbcTemplate.queryForList(
					"SELECT ID, TASK_DELIVERABLE_ID FROM V_PM_TASK_LIST WHERE TASK_NAME = ? AND FUNCTION_OBS_ID = ?",
					taskModel.get("taskName"), functionGroupId);
			if(taskList.size() > 0){
				debugLog("修改节点名称=" + taskModel.get("taskName"));
				taskModel.put("srcDeliverableId", PDMSCommon.nvl(taskList.get(0).get("TASK_DELIVERABLE_ID")));
				// 节点修改
				this.deptPlanService.saveTaskDetail(PDMSCommon.nvl(taskList.get(0).get("ID")), taskModel);
			} else {
				debugLog("新增节点名称=" + taskModel.get("taskName"));
				// 节点新增
				this.deptPlanService.addTask(programId, programVehicleId, functionGroupId, null, taskModel);
			}
		}
	}

	public synchronized String generateDelvCode(String programVehicleId,String functionGroupId, String gateId) {
		String functionGroupCode = jdbcTemplate.queryForObject("select obs_code from pm_obs where id=?", String.class,functionGroupId);
		String gateCode = null;
		if(gateId != null && gateId.length() > 0){
			gateCode = jdbcTemplate.queryForObject("select TASK_CODE from pm_task where id=?", String.class,gateId);
		}
		String sql="select nvl(max(to_number(SUBSTR(t1.code,INSTR(t1.code,'_',-1)+1))),0) from "
				+ "pm_deliverable t1,pm_task t2 where t1.program_vehicle_id = ? and t1.obs_id=? and t1.task_id=t2.id";
		Integer seqNumber = jdbcTemplate.queryForInt(sql,programVehicleId,functionGroupId) + 1;
		String seq = StringUtils.leftPad(seqNumber.toString(),3,"0");
		String code = "";
		if(StringUtils.isNotEmpty(gateCode)){
			code = gateCode+"_"+functionGroupCode+"_"+seq;
		}else{
			code = functionGroupCode+"_"+seq;
		}
		return code;
	}


	public synchronized String generateTaskCode(String programVehicleId,String functionGroupId) {
		String functionGroupCode = jdbcTemplate.queryForObject("select obs_code from pm_obs where id=?", String.class,functionGroupId);
		String sql="select nvl(max(to_number(SUBSTR(T1.TASK_CODE,INSTR(T1.TASK_CODE,'_',-1)+1))),0) from pm_task t1,pm_obs t2,pm_obs t3 where t1.obs_id=t2.id and t2.function_group_id=t3.id and t3.id=? and t1.program_vehicle_id=?";
		Integer seqNumber = jdbcTemplate.queryForInt(sql,functionGroupId,programVehicleId) + 1;
		String seq = StringUtils.leftPad(seqNumber.toString(),3,"0");
		return functionGroupCode+"_"+seq;
	}

	public void importProgramFromExcel(String programId, String fileName,InputStream is, String userid) {
		
		programOrgTemplateList = new ArrayList<ProgramOrgTemplate>();
		gateTemplateList = new ArrayList<GateTemplate>();
		gateDeliverableTemplateList = new ArrayList<GateDeliverableTemplate>();
		programOrgList = new ArrayList<ProgramOrg>();
		programOrgMemberList = new ArrayList<ProgramOrgMember>();
		gateDelvMap = new HashMap<String, Map<String, ProgramGateDelv>>();
		programVehicleMap = new HashMap<String, ProgramVehicle>();
		programTaskMap = new HashMap<String, ProgramTask>();
		
		try {

			Workbook wb = POIUtils.createWorkbook(fileName,is);

			// 解析项目模板
			Sheet sheet = wb.getSheet(TABNAME_TEMP);
			if(sheet != null){
				importProgramTemplate(sheet);
			}else{
				loadProgramTemplate();
			}

			// 导入项目组织和成员
			sheet = wb.getSheet(TABNAME_OBS);
			if(sheet != null){
				importProgramOBSAndMember(programId, sheet);
			}else{
				loadProgramOBSAndMember(programId);
			}

			// 导入车型、主节点、质量阀、阀门交付物和阶段
			sheet = wb.getSheet(TABNAME_NODE);
			if(sheet != null){
				importMainNode(programId, sheet);
			}else{
				//LOAD FROM DB
			}

			// 导入专业领域节点和交付物
			sheet = wb.getSheet(TABINAME_DEPT_NODE);
			if(sheet != null){
				importDeptNode(programId, wb.getSheet(TABINAME_DEPT_NODE));
			}else{
				//LOAD FROM DB
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		String errorMsgsStr = printErrorMsg();
		if(StringUtils.isNotEmpty(errorMsgsStr)){
			throw new RuntimeException(errorMsgsStr);
		}


	}

	/**
	 * 导入模板
	 * 
	 * @param sheet
	 */
	private void importProgramTemplate(Sheet sheet) {
		
		boolean found1 = false;
		String flag1 = "专业组模板：";
		int idx1 = 2;

		boolean found2 = false;
		String flag2 = "阀门模板：";
		int idx2 = 2;

		boolean found3 = false;
		String flag3 = "阀门交付物模板：";
		int idx3 = 2;

		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);
				if (firstCellValue.startsWith(flag1)) {
					found1 = true;
					found2 = false;
					found3 = false;
					rowNum += idx1 - 1;
					continue;
				}
				if (firstCellValue.startsWith(flag2)) {
					found1 = false;
					found2 = true;
					found3 = false;
					rowNum += idx2 - 1;
					continue;
				}
				if (firstCellValue.startsWith(flag3)) {
					found1 = false;
					found2 = false;
					found3 = true;
					rowNum += idx3 - 1;
					continue;
				}

				// 导入专业组模板
				if (found1) {
					// 项目组织序号
					if (StringUtils.isNotEmpty(firstCellValue)) {
						ProgramOrgTemplate programOrgTemplate = new ProgramOrgTemplate();
						programOrgTemplate.name = POIUtils.getStringCellValue(row.getCell(1));
						debugLog(programOrgTemplate);
						programOrgTemplateList.add(programOrgTemplate);
					}

				}

				// 阀门模板：
				if (found2) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						GateTemplate gateTemplate = new GateTemplate();
						gateTemplate.code = POIUtils.getStringCellValue(row.getCell(1));
						gateTemplate.name = POIUtils.getStringCellValue(row.getCell(2));
						debugLog(gateTemplate);
						gateTemplateList.add(gateTemplate);
					}

				}

				// 阀门交付物模板：
				if (found3) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						GateDeliverableTemplate gateDeliverableTemplate = new GateDeliverableTemplate();
						gateDeliverableTemplate.gateTemplate = getGateTemplate(POIUtils.getStringCellValue(row.getCell(1)));
						gateDeliverableTemplate.code = POIUtils.getStringCellValue(row.getCell(2));
						gateDeliverableTemplate.name = POIUtils.getStringCellValue(row.getCell(3));
						gateDeliverableTemplate.checkitem = POIUtils.getStringCellValue(row.getCell(4));
						gateDeliverableTemplate.checkReq = POIUtils.getStringCellValue(row.getCell(5));
						gateDeliverableTemplate.programOrgTemplate = getProgramOrgTemplate(POIUtils.getStringCellValue(row.getCell(6)));
						gateDeliverableTemplate.isKey = getBooleanValue(POIUtils.getStringCellValue(row.getCell(7)));
						debugLog(gateDeliverableTemplate);
						gateDeliverableTemplateList.add(gateDeliverableTemplate);
					}

				}

			} else {
				found1 = false;
				found2 = false;
				found3 = false;
			}

		}

//		// 删除原来所有的模板
//		cleanAllTemplate();
//		// 保存专业组模板
//		saveProgramOrgTemplate();
//		// 保存阀门模板
//		saveGateTemplate();
//		// 保存阀门交付物模板
//		saveGateDelvTemplate();

	}
	
	private void loadProgramTemplate() {
		String sql = "SELECT * FROM PM_OBS_TEMP";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			ProgramOrgTemplate programOrgTemplate = new ProgramOrgTemplate();
			programOrgTemplate.id = ObjectConverter.convert2String(map.get("ID"));
			programOrgTemplate.name = ObjectConverter.convert2String(map.get("NAME"));
			programOrgTemplateList.add(programOrgTemplate);
		}
		
		sql = "SELECT * FROM PM_GATE_TEMP";
		list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			GateTemplate gateTemplate = new GateTemplate();
			gateTemplate.id = ObjectConverter.convert2String(map.get("ID"));
			gateTemplate.code = ObjectConverter.convert2String(map.get("CODE"));
			gateTemplate.name = ObjectConverter.convert2String(map.get("NAME"));
			gateTemplateList.add(gateTemplate);
		}
		
//		sql = "SELECT * FROM PM_DELIVERABLE_TEMP";
//		list = jdbcTemplate.queryForList(sql);
//		for (Map<String, Object> map : list) {
//			//TODO NOT FINISHED.
//		}
	}

//	private void cleanAllTemplate() {
//		jdbcTemplate.update("DELETE FROM PM_DELIVERABLE_CHECKITEM_TEMP");
//		jdbcTemplate.update("DELETE FROM PM_DELIVERABLE_TEMP");
//		jdbcTemplate.update("DELETE FROM PM_GATE_TEMP");
//		jdbcTemplate.update("DELETE FROM PM_OBS_TEMP");
//	}

//	private void saveGateDelvTemplate() {
//		for (GateDeliverableTemplate gateDeliverableTemplate : gateDeliverableTemplateList) {
//			gateDeliverableTemplate.id = getUUID();
//			String isKeyStr = gateDeliverableTemplate.isKey?"Y":"N";
//			String sql = "INSERT INTO PM_DELIVERABLE_TEMP(ID,GATE_TEMP_ID,CODE,NAME,OBS_TEMP_ID,IS_KEY) VALUES(?,?,?,?,?,?)";
//			jdbcTemplate.update(sql, gateDeliverableTemplate.id, gateDeliverableTemplate.gateTemplate.id, gateDeliverableTemplate.code, gateDeliverableTemplate.name,
//					gateDeliverableTemplate.programOrgTemplate.id,isKeyStr);
//			sql = "INSERT INTO PM_DELIVERABLE_CHECKITEM_TEMP(ID,SEQ,NAME,CHECK_REQUIREMENT,DELIVERABLE_TEMP_ID) VALUES(?,?,?,?,?)";
//			jdbcTemplate.update(sql, getUUID(), 1, gateDeliverableTemplate.checkitem, gateDeliverableTemplate.checkReq, gateDeliverableTemplate.id);
//		}
//	}

//	private void saveGateTemplate() {
//		for (GateTemplate gateTemplate : gateTemplateList) {
//			gateTemplate.id = getUUID();
//			String sql = "INSERT INTO PM_GATE_TEMP(ID,CODE,NAME) VALUES(?,?,?)";
//			jdbcTemplate.update(sql, gateTemplate.id, gateTemplate.code, gateTemplate.name);
//		}
//	}
//
//	private void saveProgramOrgTemplate() {
//		for (ProgramOrgTemplate programOrgTemplate : programOrgTemplateList) {
//			programOrgTemplate.id = getUUID();
//			String sql = "INSERT INTO PM_OBS_TEMP(ID,NAME) VALUES(?,?)";
//			jdbcTemplate.update(sql, programOrgTemplate.id, programOrgTemplate.name);
//		}
//	}

	private GateTemplate getGateTemplate(String code) {
		for (GateTemplate gateTemplate : gateTemplateList) {
			if (gateTemplate.code.equals(code)) {
				return gateTemplate;
			}
		}
		throw new RuntimeException("阀门模板未找到。编码=" + code);
	}

	private ProgramOrgTemplate getProgramOrgTemplate(String name) {
		for (ProgramOrgTemplate programOrgTemplate : programOrgTemplateList) {
			if (programOrgTemplate.name.equals(name)) {
				return programOrgTemplate;
			}
		}
		throw new RuntimeException("专业领域模板未找到。名称=" + name);
	}

	private void importProgramOBSAndMember(String programId, Sheet sheet) {
		boolean foundProgramOrgFlag = false;
		String flag1 = "项目一级组织架构";
		int idx1 = 3;

//		boolean foundSwimlaneFlag = false;
//		String flag2 = "泳道定义";
//		int idx2 = 2;

		ProgramOrg programOrg = null;
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);
				if (firstCellValue.startsWith(flag1)) {
					foundProgramOrgFlag = true;
					rowNum += idx1 - 1;
					continue;
				}
//				if (firstCellValue.startsWith(flag2)) {
//					foundProgramOrgFlag = false;
//					foundSwimlaneFlag = true;
//					rowNum += idx2 - 1;
//					continue;
//				}

				// 导入项目组织和成员
				if (foundProgramOrgFlag) {

					// 项目组织序号
					if (StringUtils.isNotEmpty(firstCellValue)) {
						programOrg = new ProgramOrg();
						programOrg.seq = firstCellValue;
						String parentSeq = getParentProgramOrgSeq(programOrg.seq);
						programOrg.parent = getProgramOrgBySeq(parentSeq);
						System.out.println(parentSeq);
						programOrg.code = POIUtils.getStringCellValue(row.getCell(1));
						programOrg.name = POIUtils.getStringCellValue(row.getCell(2));
						debugLog(programOrg);
						programOrgList.add(programOrg);
					}

					// 导入项目组织成员
					ProgramOrgMember programOrgMember = new ProgramOrgMember();
					programOrgMember.programOrg = programOrg;
					programOrgMember.roleName = POIUtils.getStringCellValue(row.getCell(4));// 项目角色
					if(StringUtils.isEmpty(programOrgMember.roleName)){
						continue;
					}
					programOrgMember.roleId = getProgramRoleIdByName(programOrgMember.roleName);
					programOrgMember.no = POIUtils.getStringCellValue(row.getCell(5));// 工号
					programOrgMember.name = POIUtils.getStringCellValue(row.getCell(6));// 姓名
					programOrgMember.userId = getUserIdByEmployeeNo(programOrgMember.no);
					debugLog(programOrgMember);
					programOrgMemberList.add(programOrgMember);

				}

//				// 导入泳道
//				if (foundSwimlaneFlag) {
//					Swimlane swimlane = new Swimlane();
//					swimlane.seq = POIUtils.getIntCellValue(row.getCell(0));
//					swimlane.code = POIUtils.getStringCellValue(row.getCell(1));
//					swimlane.name = POIUtils.getStringCellValue(row.getCell(2));
//					swimlane.type = POIUtils.getStringCellValue(row.getCell(3));
//					String deptName = POIUtils.getStringCellValue(row.getCell(4));
//					ProgramOrg po = findProgramOrgByName(deptName);
//					if (po == null) {
//						throw new RuntimeException(generateErrorMsg(rowNum + 1, "泳道责任组未定义，名称：" + deptName));
//					}
//					po.isDept = true;
//					swimlane.programOrg = po;
//					swimlaneList.add(swimlane);
//					debugLog(swimlane);
//				}
			}

		}

		// 保存项目组织到数据库
		saveProgramOrgList(programId);
		saveProgramOrgMemberList(programId);
//		saveSwimlaneList(programId);
	}
	
	public void loadProgramOBSAndMember(String programId){
		String sql = "SELECT * FROM PM_OBS";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			ProgramOrg programOrg = new ProgramOrg();
			programOrg.id = ObjectConverter.convert2String(map.get("ID"));
			programOrg.name = ObjectConverter.convert2String(map.get("OBS_NAME"));
			programOrg.parent = getProgramOrgById(ObjectConverter.convert2String(map.get("PARENT_ID")));
			programOrgList.add(programOrg);
		}
		
		//TODO LOAD PROGRAM_MEMBER
	}
	
	public ProgramOrg getProgramOrgById(String id){
		String sql = "SELECT * FROM PM_OBS WHERE ID=?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql,id);
		ProgramOrg programOrg = new ProgramOrg();
		programOrg.id = ObjectConverter.convert2String(map.get("ID"));
		programOrg.name = ObjectConverter.convert2String(map.get("OBS_NAME"));
		return programOrg;
	}

	private String getProgramRoleIdByName(String roleName) {
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap("SELECT * FROM SYS_PROGRAM_PROFILE WHERE PROFILE_NAME=?", roleName);
			if (map.isEmpty()) {
				throw new RuntimeException("角色名称无效，未找到对应角色！，角色名称=" + roleName);
			}
			return (String) map.get("ID");
		} catch (Exception e) {
			throw new RuntimeException("角色名称无效，未找到对应角色！，角色名称=" + roleName, e);
		}
	}

	private String getUserIdByEmployeeNo(String no) {
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap("SELECT * FROM SYS_USER WHERE EMPLOYEE_NO=?", no);
			if (map.isEmpty()) {
				throw new RuntimeException("用户编号无效，未找到对应用户！，编号=" + no);
			}
			return (String) map.get("ID");
		} catch (Exception e) {
			throw new RuntimeException("用户编号无效，未找到对应用户！，编号=" + no, e);
		}
	}

	private String getParentProgramOrgSeq(String seq) {
		String parentSeq = null;
		int lastIndexOfPoint = seq.lastIndexOf(".");
		if (lastIndexOfPoint > 0) {
			parentSeq = seq.substring(0, lastIndexOfPoint);
		}
		return parentSeq;
	}

	/**
	 * 导入车型、主节点、质量阀、阀门交付物和阶段
	 * 
	 * @param sheet
	 */
	private void importMainNode(String programId, Sheet sheet) {
		System.out.println("导入车型、主节点、质量阀、阀门交付物和阶段");
		boolean found1 = false;
		String flag1 = "车型：";
		int idx1 = 2;

		boolean found2 = false;
		String flag2 = "主节点：";
		int idx2 = 2;

		boolean found3 = false;
		String flag3 = "质量阀：";
		int idx3 = 2;

		boolean found4 = false;
		String flag4 = "阀门交付物：";
		int idx4 = 2;

		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);

				// 设置导入开始标志
				if (firstCellValue.startsWith(flag1)) {
					found1 = true;
					found2 = false;
					found3 = false;
					found4 = false;
					rowNum += idx1 - 1;
					continue;
				} else if (firstCellValue.startsWith(flag2)) {
					found1 = false;
					found2 = true;
					found3 = false;
					found4 = false;
					rowNum += idx2 - 1;
					continue;
				} else if (firstCellValue.startsWith(flag3)) {
					found1 = false;
					found2 = false;
					found3 = true;
					found4 = false;
					rowNum += idx3 - 1;
					continue;
				} else if (firstCellValue.startsWith(flag4)) {
					found1 = false;
					found2 = false;
					found3 = false;
					found4 = true;
					rowNum += idx4 - 1;
					continue;
				}

				// 导入车型
				if (found1) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						ProgramVehicle programVehicle = new ProgramVehicle();
						programVehicle.code = POIUtils.getStringCellValue(row.getCell(1));
						programVehicle.name = POIUtils.getStringCellValue(row.getCell(2));
						programVehicleMap.put(programVehicle.code, programVehicle);
						debugLog(programVehicle);
					}

				}

				// 导入主节点
				if (found2) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						ProgramTask mainNode = new ProgramTask();
						mainNode.code = POIUtils.getStringCellValue(row.getCell(1));// 节点编码
						mainNode.name = POIUtils.getStringCellValue(row.getCell(2));// 节点名称
						mainNode.type = getTaskTypeCodeByName(POIUtils.getStringCellValue(row.getCell(3)));// 类型
						String vehiclesCodeStr = POIUtils.getStringCellValue(row.getCell(4));// 车型
						mainNode.programVehicle = getProgramVehicle(vehiclesCodeStr);
						mainNode.planFinishDate = POIUtils.getDateCellValue(row.getCell(5));// 计划完成日期
						addTaskToMap(mainNode);
						debugLog(mainNode);
					}
				}

				// 导入质量阀
				if (found3) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						ProgramTask gate = new ProgramTask();
						gate.code = POIUtils.getStringCellValue(row.getCell(1));// 阀门编码
						gate.name = getGateTemplate(POIUtils.getStringCellValue(row.getCell(2))).name;// 阀门名称
						String vehiclesCodeStr = POIUtils.getStringCellValue(row.getCell(3));// 车型
						gate.programVehicle = getProgramVehicle(vehiclesCodeStr);
						gate.planFinishDate = POIUtils.getDateCellValue(row.getCell(4));// 计划完成日期
						gate.type = getTaskTypeCodeByName(TASK_TYPE_STR_GATE);
						addTaskToMap(gate);
						debugLog(gate);
					}
				}

				// 导入质量阀交付物
				if (found4) {
					if (StringUtils.isNotEmpty(firstCellValue)) {
						ProgramGateDelv programGateDelv = new ProgramGateDelv();
						String gateCode = POIUtils.getStringCellValue(row.getCell(1));// 阀门编码
						programGateDelv.gate = getProgramTask(gateCode);
						programGateDelv.code = POIUtils.getStringCellValue(row.getCell(2));// 交付物编码
						programGateDelv.name = POIUtils.getStringCellValue(row.getCell(3));// 交付物名称
						programGateDelv.checkitem = POIUtils.getStringCellValue(row.getCell(4));// 检查项目
						programGateDelv.checkRequirement = POIUtils.getStringCellValue(row.getCell(5));// 单项通过要求及验收办法
						String deptName = POIUtils.getStringCellValue(row.getCell(6));// 检查项目
						programGateDelv.programOrg = findProgramOrgByName(deptName);// 负责专业组
						programGateDelv.isKey = POIUtils.getStringCellValue(row.getCell(7));// 是否关键交付物
						programGateDelv.isFit = POIUtils.getStringCellValue(row.getCell(8));// 本项目是否适用
						programGateDelv.notFitReason = POIUtils.getStringCellValue(row.getCell(9));// 不适用理由
						if (!gateDelvMap.containsKey(programGateDelv.gate.code)) {
							gateDelvMap.put(programGateDelv.gate.code, new HashMap<String, ProgramGateDelv>());
						}
						Map<String, ProgramGateDelv> map = gateDelvMap.get(programGateDelv.gate.code);
						if(map.containsKey(programGateDelv.code)){
							throw new RuntimeException("质量阀交付物编码重复。质量阀编码："+gateCode+",交付物编码："+programGateDelv.code);
						}
						map.put(programGateDelv.code, programGateDelv);
						debugLog(programGateDelv);
					}
				}

				// 导入阶段
//				if (found5) {
//					if (StringUtils.isNotEmpty(firstCellValue)) {
//						if (firstStageBeginNode == null) {
//							// 首阶段开始标志：
//							String firstStageBeginNodeCode = POIUtils.getStringCellValue(row.getCell(1));
//							firstStageBeginNode = getProgramTask(firstStageBeginNodeCode);
//							rowNum = rowNum + 1;
//							continue;
//
//						}
//
//						ProgramStage programStage = new ProgramStage();
//						programStage.seq = POIUtils.getIntCellValue(row.getCell(0));// 阶段序号
//						programStage.name = POIUtils.getStringCellValue(row.getCell(1));// 阶段名称
//
//						String endNodeCode = POIUtils.getStringCellValue(row.getCell(2));// 结束标志
//						programStage.endNode = getProgramTask(endNodeCode);
//						programStage.endDate = programStage.endNode.planFinishDate;
//
//						if (programStage.seq == 1) {
//							programStage.beginNode = firstStageBeginNode;
//							programStage.beginDate = firstStageBeginNode.planFinishDate;
//
//						} else {
//
//							// 根据上一阶段结束节点计算当前阶段的开始日期
//							if (lastStageEndNode != null) {
//								programStage.beginDate = DateUtils.addDays(lastStageEndNode.planFinishDate, 1);
//							}
//
//						}
//						lastStageEndNode = programStage.endNode;
//
//						programStageList.add(programStage);
//						debugLog(programStage);
//					}
//				}

			} else {
				found1 = false;
				found2 = false;
				found3 = false;
				found4 = false;
			}

		}

		// 车型、主节点、质量阀、阀门交付物和阶段
		saveProgramVehicle(programId);
		saveMainNode(programId);
		saveGateAndDelv(programId);
//		saveProgramStage(programId);

	}

//	private void saveProgramStage(String programId) {
//		for (ProgramStage programStage : programStageList) {
//			programStage.id = getUUID();
//			String sql = "INSERT INTO PM_PROGRAM_STAGE(ID,NAME,PROGRAM_ID,BEGIN_DATE,END_DATE,SEQ,START_NODE_ID,END_NODE_ID) VALUES(?,?,?,?,?,?,?,?)";
//			jdbcTemplate.update(sql, programStage.id, programStage.name, programId, programStage.beginDate, programStage.endDate, programStage.seq,
//					programStage.beginNode != null ? programStage.beginNode.id : null, programStage.endNode != null ? programStage.endNode.id : null);
//
//		}
//
//	}

	private void saveProgramOrgList(String programId) {
		for (ProgramOrg programOrg : programOrgList) {
			String sql = "INSERT INTO PM_OBS(ID,OBS_NAME,PARENT_ID,PROGRAM_ID,OBS_TYPE_CODE,PLAN_LEVEL,IS_LEAF) VALUES(?,?,?,?,?,?,?)";
			String uuid = getUUID();
			jdbcTemplate.update(sql, uuid, programOrg.name, null, programId, programOrg.type, 1, programOrg.isDept ? "Y" : "N");
			programOrg.id = uuid;
		}

		// 保存上级组织架构
		for (ProgramOrg programOrg : programOrgList) {
			String parentId = (programOrg.parent != null) ? programOrg.parent.id : null;
			if (parentId != null) {
				String sql = "UPDATE PM_OBS SET PARENT_ID=? WHERE ID=?";
				jdbcTemplate.update(sql, parentId, programOrg.id);
			}

		}

	}

	private void saveProgramOrgMemberList(String programId) {
		for (ProgramOrgMember programOrgMember : programOrgMemberList) {
			String sql = "INSERT INTO PM_USER_OBS(ID,USER_ID,OBS_ID,PROFILE_ID,DEFAULT_FLAG) VALUES(?,?,?,?,?)";
			jdbcTemplate.update(sql, getUUID(), programOrgMember.userId, programOrgMember.programOrg.id, programOrgMember.roleId, "Y");
		}
		//更新项目基本信息中的项目总监和项目经理 
		//TODO 这里现在是根据角色名称写死的，将来应该在保存基本信息时插入总监和项目经理  --Frank 2016-8-5
		String sql = "select T1.ID from PM_USER_OBS T1,SYS_PROGRAM_PROFILE T2,PM_OBS T3 where T1.PROFILE_ID=T2.ID AND T3.ID=T1.OBS_ID AND T3.PROGRAM_ID=? AND T2.PROFILE_NAME=? ";
		Map<String, Object> map  = jdbcTemplate.queryForMap(sql,programId,"项目总监");
		String leaderId= (String)map.get("ID");
		map = jdbcTemplate.queryForMap(sql,programId,"项目经理");
		String pmId= (String)map.get("ID");
		sql="UPDATE PM_PROGRAM_BASEINFO SET PM=?,DIRECTOR=? WHERE ID=?";
		jdbcTemplate.update(sql,pmId,leaderId,programId);

	}

//	private void saveSwimlaneList(String programId) {
//		for (Swimlane swimlane : swimlaneList) {
//			String sql = "INSERT INTO PM_SWIMLANE(ID,PROGRAM_ID,NAME,PLAN_LEVEL,OBS_ID) VALUES(?,?,?,?,?)";
//			swimlane.id = getUUID();
//			jdbcTemplate.update(sql, swimlane.id, programId, swimlane.name, 1, swimlane.programOrg.id);
//		}
//	}

	private void saveProgramVehicle(String programId) {
		for (String code : programVehicleMap.keySet()) {
			ProgramVehicle pv = programVehicleMap.get(code);
			pv.id = getUUID();
			String sql = "INSERT INTO PM_PROGRAM_VEHICLE(ID,PROGRAM_ID,VEHICLE_CODE,VEHICLE_NAME) VALUES(?,?,?,?)";
			jdbcTemplate.update(sql, pv.id, programId, pv.code, pv.name);
		}
	}

	private void saveMainNode(String programId) {
		Collection<ProgramTask> values = programTaskMap.values();
		for (ProgramTask mainNode : values) {
			if (mainNode.type.equals(PDMSConstants.TASK_TYPE_MAIN_NODE) || mainNode.type.equals(PDMSConstants.TASK_TYPE_SOP_NODE)) {
				String sql = "INSERT INTO PM_TASK(ID,TASK_CODE,TASK_NAME,PLANNED_FINISH_DATE,TASK_TYPE_CODE,TASK_STATUS_CODE ,PROGRAM_ID,PROGRAM_VEHICLE_ID) VALUES(?,?,?,?,?,?,?,?)";
				mainNode.id = getUUID();
				jdbcTemplate.update(sql, mainNode.id, mainNode.code, mainNode.name, mainNode.planFinishDate, mainNode.type, PDMSConstants.TASK_STATUS_NOT_START, programId,mainNode.programVehicle.id);
				// 更新车型SOP日期
				if (mainNode.type.equals(PDMSConstants.TASK_TYPE_SOP_NODE)) {
					if (mainNode.programVehicle == null) {
						throw new RuntimeException("SOP节点只能关联一个车型。节点名称：" + mainNode.name);
					}
					ProgramVehicle pv = mainNode.programVehicle;
					sql = "UPDATE PM_PROGRAM_VEHICLE SET SOP_DATE=? WHERE ID=?";
					jdbcTemplate.update(sql, mainNode.planFinishDate, pv.id);

				}
			}
		}

	}

	private void saveGateAndDelv(String programId) {
		Collection<ProgramTask> values = programTaskMap.values();
		for (ProgramTask gate : values) {
			if (gate.type.equals(PDMSConstants.TASK_TYPE_GATE)) {
				String sql = "INSERT INTO PM_TASK(ID,TASK_CODE,TASK_NAME,PLANNED_FINISH_DATE,TASK_TYPE_CODE,TASK_STATUS_CODE ,PROGRAM_ID,PROGRAM_VEHICLE_ID) VALUES(?,?,?,?,?,?,?,?)";
				gate.id = getUUID();
				jdbcTemplate.update(sql, gate.id, gate.code, gate.name, gate.planFinishDate, gate.type, PDMSConstants.TASK_STATUS_NOT_START, programId,gate.programVehicle.id);
			}
		}

		Collection<Map<String, ProgramGateDelv>> gateDelvsMap = gateDelvMap.values();
		for (Map<String, ProgramGateDelv> gateDelv : gateDelvsMap) {
			Collection<ProgramGateDelv> gateDelvs = gateDelv.values();
			for (ProgramGateDelv delv : gateDelvs) {
				// 保存阀门交付物
				String sql = "INSERT INTO PM_DELIVERABLE(ID,NAME,CODE,TASK_ID,OBS_ID,IS_KEY,IS_FIT,NOT_FIT_REASON) VALUES(?,?,?,?,?,?,?,?)";
				delv.id = getUUID();
				jdbcTemplate.update(sql, delv.id, delv.name, delv.code, delv.gate.id, delv.programOrg.id, delv.isKey, delv.isKey, delv.notFitReason);
				// 保存检查项
				sql = "INSERT INTO PM_DELIVERABLE_CHECKITEM(ID,SEQ,NAME,CHECK_REQUIREMENT,DELIVERABLE_ID) VALUES(?,?,?,?,?)";
				String uuid = getUUID();
				jdbcTemplate.update(sql, uuid, 1, delv.checkitem, delv.checkRequirement, delv.id);
			}

		}

	}

//	private void saveTaskVehicles(String taskId, List<ProgramVehicle> programVehicles) {
//		for (ProgramVehicle programVehicle : programVehicles) {
//			String sql = "INSERT INTO PM_TASK_PROGRAM_VEHICLE(ID,PROGRAM_VEHICLE_ID,TASK_ID) VALUES(?,?,?)";
//			jdbcTemplate.update(sql, getUUID(), programVehicle.id, taskId);
//		}
//
//	}

	private String getTaskTypeCodeByName(String name) {
		String code = null;
		if (name.equals(TASK_TYPE_STR_MAINNODE)) {
			code = PDMSConstants.TASK_TYPE_MAIN_NODE;
		} else if (name.equals(TASK_TYPE_STR_SOP)) {
			code = PDMSConstants.TASK_TYPE_SOP_NODE;
		} else if (name.equals(TASK_TYPE_STR_GATE)) {
			code = PDMSConstants.TASK_TYPE_GATE;
		} else if (name.equals(TASK_TYPE_STR_ACTIVITY)) {
			code = PDMSConstants.TASK_TYPE_ACTIVITY;
		} else if (name.equals(TASK_TYPE_STR_NODE)) {
			code = PDMSConstants.TASK_TYPE_NODE;
		} else {
			throw new RuntimeException("类型未定义。名称=" + name);
		}
		return code;
	}

	private void importDeptNode(String programId, Sheet sheet) {
		System.out.println("导入专业领域节点和交付物");
		// 循环行Row
		for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				ProgramTask node = new ProgramTask();
				node.code = POIUtils.getStringCellValue(row.getCell(1));// 编码
				node.type = getTaskTypeCodeByName(POIUtils.getStringCellValue(row.getCell(2)));// 类型
				node.name = POIUtils.getStringCellValue(row.getCell(3));// 名称
				node.parentTask = getProgramTask(POIUtils.getStringCellValue(row.getCell(4)));// 关联虚拟活动
				node.pretaskCodes = getStringList(POIUtils.getStringCellValue(row.getCell(5)));// 前置节点Code
																								// List
				node.relationTaskCode = POIUtils.getStringCellValue(row.getCell(6));// 关联节点Code
				node.gate = getProgramTask(POIUtils.getStringCellValue(row.getCell(7)));// 后置阀门
				
				node.programGateDelvs = getProgramGateDelvs(node.gate.code, getStringList(POIUtils.getStringCellValue(row.getCell(8))));// 阀门交付物
				node.programVehicle = getProgramVehicle(POIUtils.getStringCellValue(row.getCell(9)));// 车型
//				node.swimlane = getSwimlane(POIUtils.getStringCellValue(row.getCell(10)));// 泳道
				node.programOrg = getProgramOrg(POIUtils.getStringCellValue(row.getCell(10)));// 责任组
				node.programOrgMember = getProgramOrgMember(node.programOrg.name, POIUtils.getStringCellValue(row.getCell(11)));// 责任组
				node.planStartDate = POIUtils.getDateCellValue(row.getCell(12));// 计划开始日期
				node.planFinishDate = POIUtils.getDateCellValue(row.getCell(13));// 计划结束日期
				node.isShow = getBooleanValue(POIUtils.getStringCellValue(row.getCell(14)));// 是否显示在时程表

				if(node.planFinishDate.after(node.gate.planFinishDate)){
					addErrorMsg("节点("+node.code+")计划完成时间("+formatDate(node.planFinishDate)+")必须早于后置阀门("+node.gate.code+")的计划完成时间("+formatDate(node.gate.planFinishDate)+")。");
				}
				
				addTaskToMap(node);
				debugLog(node);

			} else {
				break;
			}
		}
		saveDeptNodes(programId);
	}

	private void addTaskToMap(ProgramTask node) {
		if(programTaskMap.containsKey(node.code)){
			ProgramTask task = programTaskMap.get(node.code);
			throw new RuntimeException("节点编码重复。编码="+node.code+",task="+task);
		}
		programTaskMap.put(node.code, node);

		
	}

	private void saveDeptNodes(String programId) {
		Collection<ProgramTask> programTasks = programTaskMap.values();
		for (ProgramTask node : programTasks) {
			if (node.type.equals(PDMSConstants.TASK_TYPE_ACTIVITY) || node.type.equals(PDMSConstants.TASK_TYPE_NODE)) {
				String sql = "INSERT INTO PM_TASK("
						+ "ID,"
						+ "TASK_CODE,"
						+ "TASK_NAME,"
						+ "PLANNED_START_DATE,"
						+ "PLANNED_FINISH_DATE,"
						+ "TASK_TYPE_CODE,"
						+ "TASK_STATUS_CODE ,"
						+ "PROGRAM_ID,"
						+ "PARENT_ID,"
						+ "GATE_ID,"
						+ "OBS_ID,"
						+ "TASK_OWNER,"
						+ "IS_SHOW,"
						+ "PLAN_LEVEL,"
						+ "PROGRAM_VEHICLE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				node.id = getUUID();
				String parentId = node.parentTask != null ? node.parentTask.id : null;
				jdbcTemplate.update(sql, 
						node.id, 
						node.code, 
						node.name, 
						node.planStartDate, 
						node.planFinishDate, 
						node.type, 
						PDMSConstants.TASK_STATUS_NOT_START, 
						programId, 
						parentId, 
						node.gate.id,
						node.programOrg.id, 
						node.programOrgMember.userId, 
						node.isShow, 
						1,
						node.programVehicle.id);
				// 保存关联交付物
				for (ProgramGateDelv programGateDelv : node.programGateDelvs) {
					sql = "INSERT INTO PM_TASK_DELIVERABLE(ID,TASK_ID,DELIVERABLE_ID) VALUES(?,?,?)";
					jdbcTemplate.update(sql, getUUID(), node.id, programGateDelv.id);
				}

			}
		}

		// 保存前置节点和关联节点
		for (ProgramTask node : programTasks) {
			if (node.type.equals(PDMSConstants.TASK_TYPE_ACTIVITY) || node.type.equals(PDMSConstants.TASK_TYPE_NODE)) {

				// 保存前置节点
				if (node.pretaskCodes != null && node.pretaskCodes.size() > 0) {
					for (String pretaskCode : node.pretaskCodes) {
						ProgramTask preTask = getProgramTask(pretaskCode);

						String sql = "INSERT INTO PM_PRE_TASK(ID,TASK_ID,PRE_TASK_ID,PRE_TASK_TYPE_CODE,LAG) VALUES(?,?,?,?,?)";
						jdbcTemplate.update(sql, getUUID(), node.id, preTask.id, PDMSConstants.PRE_TASK_TYPE_FS, 0);
					}

				}
				
				
				// 更新关联节点
				if (StringUtils.isNotEmpty(node.relationTaskCode)) {
					//检查当前节点时间必须早于关联节点
					ProgramTask relationTask = getProgramTask(node.relationTaskCode);
					if(node.planFinishDate.after(relationTask.planFinishDate)){
						addErrorMsg("当前节点("+node.code+")计划完成时间("+formatDate(node.planFinishDate)+")必须早于关联节点("+relationTask.code+")的计划完成时间("+formatDate(relationTask.planFinishDate)+")。");
					}
					node.relationTask = relationTask;
					String sql = "UPDATE PM_TASK SET RELATION_TASK_ID=? WHERE ID=?";
					jdbcTemplate.update(sql, relationTask.id, node.id);

				}
			}
		}

	}
	
	private String formatDate(Date date){
		return DateUtils.formate(date);
	}

	private ProgramOrg getProgramOrgBySeq(String seq) {
		if (StringUtils.isNotEmpty(seq)) {
			for (ProgramOrg programOrg : programOrgList) {
				if (programOrg.seq.equals(seq)) {
					return programOrg;
				}
			}
			throw new RuntimeException("项目组未定义。序号=" + seq);
		}
		return null;
	}

	private ProgramOrg getProgramOrg(String programOrgName) {
		for (ProgramOrg programOrg : programOrgList) {
			if (programOrg.name.equals(programOrgName)) {
				return programOrg;
			}
		}
		throw new RuntimeException("责任组未定义。名称=" + programOrgName);
	}
//	private ProgramOrg getProgramOrgByCode(String code) {
//		for (ProgramOrg programOrg : programOrgList) {
//			if (programOrg.code.equals(code)) {
//				return programOrg;
//			}
//		}
//		throw new RuntimeException("责任组未定义。code=" + code);
//	}

	private ProgramOrgMember getProgramOrgMember(String programOrgName, String programOrgMemberName) {
		for (ProgramOrgMember programOrgMember : programOrgMemberList) {
			if (programOrgMember.name.equals(programOrgMemberName) && programOrgMember.programOrg.name.equals(programOrgName)) {
				return programOrgMember;
			}
		}
		throw new RuntimeException("责任组下未找到责任人。责任组=" + programOrgName + ",责任人=" + programOrgMemberName);
	}

//	private Swimlane getSwimlane(String swimlaneName) {
//		for (Swimlane swimlane : swimlaneList) {
//			if (swimlane.name.equals(swimlaneName)) {
//				return swimlane;
//			}
//		}
//		throw new RuntimeException("泳道未定义。名称=" + swimlaneName);
//	}

	private List<ProgramGateDelv> getProgramGateDelvs(String gateCode, List<String> gateDelvCodes) {
		List<ProgramGateDelv> list = new ArrayList<ProgramGateDelv>();
		if (!gateDelvCodes.isEmpty()) {
			if (gateDelvMap.containsKey(gateCode)) {
				Map<String, ProgramGateDelv> map = gateDelvMap.get(gateCode);
				for (String gateDelvCode : gateDelvCodes) {
					if (map.containsKey(gateDelvCode)) {
						list.add(map.get(gateDelvCode));
					} else {
						throw new RuntimeException("阀门未找到交付物定义，阀门代码：" + gateCode + ",交付物代码=" + gateDelvCode);
					}
				}

			}
		}
		return list;
	}

	private List<String> getStringList(String stringValue) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotEmpty(stringValue)) {
			String[] split = stringValue.split(",");
			for (String string : split) {
				list.add(string);
			}
		}
		return list;
	}

	private boolean getBooleanValue(String stringCellValue) {
		boolean b = false;
		if ("Y".equals(stringCellValue.toUpperCase())) {
			b = true;
		}
		return b;
	}
	
	private ProgramVehicle getProgramVehicle(String code) {
		if (programVehicleMap.containsKey(code)) {
			return programVehicleMap.get(code);
		}
		return null;
	}

//	private List<ProgramVehicle> getProgramVehicles(String vehiclesCodeStr) {
//		List<ProgramVehicle> list = new ArrayList<ProgramVehicle>();
//		String[] strVehiclesAry = vehiclesCodeStr.split(",");
//		for (int i = 0; i < strVehiclesAry.length; i++) {
//			String code = strVehiclesAry[i];
//			if ("ALL".equals(code)) {
//				Collection<ProgramVehicle> pvs = programVehicleMap.values();
//				for (ProgramVehicle pv : pvs) {
//					list.add(pv);
//				}
//			} else if (programVehicleMap.containsKey(code)) {
//				list.add(programVehicleMap.get(code));
//			}
//		}
//		return list;
//	}

	private ProgramTask getProgramTask(String code) {
		if (StringUtils.isNotEmpty(code)) {
			if (programTaskMap.containsKey(code)) {
				return programTaskMap.get(code);
			}
			throw new RuntimeException("任务未找到，代码：" + code);
		}
		return null;
	}

//	private List<ProgramTask> getProgramTasks(List<String> codes) {
//		List<ProgramTask> list = new ArrayList<ProgramTask>();
//		for (String code : codes) {
//			list.add(getProgramTask(code));
//		}
//		return list;
//	}

	public void debugLog(Object obj) {
		System.out.println(obj);
	}

//	private String generateErrorMsg(int lineNum, String message) {
//		return message + ",出错行号：" + lineNum;
//	}

	private ProgramOrg findProgramOrgByName(String name) {
		for (ProgramOrg programOrg : programOrgList) {
			if (programOrg.name.equals(name)) {
				return programOrg;
			}
		}

		throw new RuntimeException("专业组未找到，名称：" + name);
	}

	public void updateCreateUserAndCreateDate(String tableName, String id, String userId) {
		String sql = "UPDATE " + tableName + "  SET CREATE_BY=?,CREATE_DATE=? WHERE ID=?";
		jdbcTemplate.update(sql, userId, new Date(), id);
	}
	
	public String getUUID() {
		return com.gnomon.pdms.common.PDMSCommon.generateUUID();
	}

	// 项目组织模板
	public class ProgramOrgTemplate {
		public String id;
		public String code;// 编码
		public String name;// 项目组织名称
	}

	// 阀门模板
	public class GateTemplate {
		public String id;
		public int seq;// 序号
		public String code;// 阀门模板编码
		public String name;// 阀门名称
	}

	// 阀门交付物模板
	public class GateDeliverableTemplate {
		public String id;
		public int seq;// 序号
		public GateTemplate gateTemplate;
		public String code;// 交付物编码
		public String name;// 交付物名称
		public String checkitem;// 检查项目
		public String checkReq;// 单项通过要求及验收办法
		public ProgramOrgTemplate programOrgTemplate;// 负责专业组
		public boolean isKey;// 是否关键交付物
	}

	// 项目组织
	public class ProgramOrg {
		public String id = getUUID();;
		public String seq;// 序号
		public String code;// 项目组织编码
		public String name;// 项目组织名称
		public String oldParentId;// 上级项目组织ID
		public String type;// 项目组织类型
		public boolean isDept;// 是否是专业领域
		public ProgramOrg parent;// 上级项目组织
		public ProgramVehicle programVehicle;//车型

		@Override
		public String toString() {
			String parentOrgSeq = (parent == null) ? null : parent.seq;
			return "【项目组织】序号：" + seq + "\t\t名称：" + name + "\t\t是否专业领域：" + isDept + "\t\t上级项目组织序号：" + parentOrgSeq;
		}
	}

	// 项目组织成员
	public class ProgramOrgMember {
		public ProgramOrg programOrg;// 项目组织
		public String roleName;// 项目角色名称
		public String roleId;// 项目角色ID
		public String no;// 工号
		public String name;// 项目组织名称
		public String userId;// 用户ID

		@Override
		public String toString() {
			return "【项目组织成员】所属项目组织序号:" + programOrg.seq + "\t\t角色名称：" + roleName + "\t\t工号：" + no + "\t\t姓名：" + name;
		}
	}

	// 泳道
	public class ProgramFunction {
		public String id;
		public int seq;// 序号
		public String code;// 编码
		public String name;// 名称
		public String type;// 类型
		public ProgramOrgTemp parentProgramOrg;// 所属项目组织（所属计划）
		public String parentProgramOrgId;// 所属项目组织（所属计划）ID
		public ProgramOrg programOrg;// 责任组
		public String programOrgId;// 责任组ID

		@Override
		public String toString() {
			return "【泳道】序号:" + seq + "\t\t编码：" + code + "\t\t名称：" + name + "\t\t所属计划:"+parentProgramOrg.name+"\t\t责任组：" + programOrg.name;
		}
	}

	// 项目车型
	public class ProgramVehicle {
		public String id;
		public int seq;// 序号
		public String code;// 编码
		public String name;// 名称

		@Override
		public String toString() {
			return "【项目车型】序号:" + seq + "\t\t编码：" + code + "\t\t名称：" + name;
		}
	}

	// Task:主节点、质量阀、虚拟活动、节点
	public class ProgramTask {
		public String id;
		public int seq;// 序号
		public String code;// 编码
		public String name;// 名称
		public String type;// 类型
		public ProgramTask parentTask;// 上级任务（关联虚拟活动）
		public String parentCode;// 上级任务（关联虚拟活动）编码
		public List<String> pretaskCodes;// 前置任务Code List
		public List<ProgramTask> pretasks;// 前置节点
		public String relationTaskCode;// 关联节点Code
		public ProgramTask relationTask;// 关联节点
		public Date planStartDate;// 计划开始日期
		public Date planFinishDate;// 计划完成日期
		public ProgramVehicle programVehicle; //关联车型
//		public List<ProgramVehicle> programVehicles;// 车型
		public ProgramTask gate;// 后置阀门编码
		public List<ProgramGateDelv> programGateDelvs;// 关联阀门交付物
//		public Swimlane swimlane;// 所属泳道
		public ProgramOrg programOrg;// 负责专业组代码
		public ProgramOrgMember programOrgMember;// 责任人
		public boolean isShow;// 是否显示在时程表
		public Integer planLevel;

		@Override
		public String toString() {
			String sv = "";
			if (programVehicle != null) {
				sv = programVehicle.code;
			}
			return "【项目Task】类型：" + type + "\t\t编码：" + code + "\t\t名称：" + name + "\t\t计划完成日期：" + planFinishDate + "\t\t关联车型：" + sv;
		}
	}

	// 阀门交付物
	public class ProgramGateDelv {
		public String id;
		public ProgramTask gate;// 阀门
		public String code;// 交付物编码
		public String name;// 交付物名称
		public String checkitem;// 检查项目
		public String checkRequirement;// 单项通过要求及验收办法
		public ProgramOrg programOrg;// 负责专业组
		public String isKey;// 是否关键交付物
		public String isFit;// 是否适用本项目
		public String notFitReason;// 不适用原因

		@Override
		public String toString() {
			return "【阀门交付物】阀门：" + gate.code + "\t\t编码：" + code + "\t\t名称：" + name + "\t\t查项目：" + checkitem + "\t\t单项通过要求及验收办法：" + checkitem + "\t\t负责专业组：" + programOrg.name + "\t\t是否关键交付物：" + isKey
					+ "\t\t是否适用本项目：" + isFit + "\t\t不适用原因：" + notFitReason;
		}
	}

	// 阶段
	public class ProgramStage {
		public String id;
		public int seq;// 序号
		public String name;// 名称
		public Date beginDate;// 开始日期
		public Date endDate;// 完成日期
		public ProgramTask beginNode;// 开始节点
		public ProgramTask endNode;// 结束节点

		@Override
		public String toString() {
			return "【阶段】序号：" + seq + "\t\t名称：" + name + "\t\t开始日期：" + beginDate + "\t\t完成日期：" + endDate + "\t\t关联开始节点：" + (beginNode == null ? "" : beginNode.code) + "\t\t关联结束节点："
					+ ((endNode == null) ? "" : endNode.code);
		}

	}

}
