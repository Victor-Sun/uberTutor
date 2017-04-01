package com.gnomon.pdms.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.utils.POIUtils;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class ProgramPlanTempImportManager {
	Log log = LogFactory.getLog(ProgramPlanTempImportManager.class);

	private static final String TABNAME_1 = "1.项目组织模板";
	private static final String TABNAME_2 = "2、泳道模板";
	private static final String TABNAME_3 = "3.主计划主节点模板";
	private static final String TABNAME_4 = "4.质量阀模板";
	private static final String TABNAME_5 = "5.阀门交付物模板";
	private static final String TABNAME_6 = "6.主计划专业领域节点模板";// 专业领域节点和交付物

	private static final String TASK_TYPE_STR_MAINNODE = "主节点";
	private static final String TASK_TYPE_STR_SOP = "SOP节点";
	private static final String TASK_TYPE_STR_GATE = "质量阀";
	private static final String TASK_TYPE_STR_ACTIVITY = "虚拟活动";
	private static final String TASK_TYPE_STR_NODE = "节点";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;

	private List<ProgramOrgTemp> programOrgList = new ArrayList<ProgramOrgTemp>();
	private List<ProgramFunctionTemp> programFunctionList = new ArrayList<ProgramFunctionTemp>();
	private Map<String, Map<String, ProgramGateDelv>> gateDelvMap = new HashMap<String, Map<String, ProgramGateDelv>>();
	private Map<String, ProgramTask> programTaskMap = new HashMap<String, ProgramTask>();

	private List<String> errorMsgs = new ArrayList<String>();

	public void addErrorMsg(String msg) {
		errorMsgs.add(msg);
	}

	public String printErrorMsg() {
		StringBuffer sb = new StringBuffer();
		for (String errMsg : errorMsgs) {
			sb.append(errMsg).append("\n");
		}

		return sb.toString();
	}

	public void updateCreateUserAndCreateDate(String tableName, String id, String userId) {
		String sql = "UPDATE " + tableName + "  SET CREATE_BY=?,CREATE_DATE=? WHERE ID=?";
		jdbcTemplate.update(sql, userId, new Date(), id);
	}

	/**
	 * 创建项目模板，从Excel导入模板数据
	 * @param programTypeId
	 * @param programName
	 * @param programCode
	 * @param uploadFileName
	 * @param is
	 * @param userId
	 * @param planLevel
	 */
	public void createProgramFromExcel(String programTypeId,String programName, String programCode, String uploadFileName, InputStream is, String userId) {
		String sql="select count(*) from PM_TEMP_PROGRAM where CODE=?";
		int c =jdbcTemplate.queryForInt(sql,programCode);
		if(c > 0){
			throw new RuntimeException("项目模板编码（"+programCode+"）已存在，请输入其他编码！");
		}
		sql = "INSERT INTO PM_TEMP_PROGRAM(ID,CODE,NAME,PROGRAM_TYPE_ID) VALUES(?,?,?,?)";
		String programId = getUUID();
		jdbcTemplate.update(sql, programId, programCode, programName,programTypeId);
		updateCreateUserAndCreateDate("PM_TEMP_PROGRAM", programId, userId);
		importProgramFromExcel(programId, uploadFileName, is, userId);
	}
	
	/**
	 * 删除项目模板
	 * @param programId
	 */
	public void deleteProgramTemp(String programId){
		cleanProgramTemp(programId);
		String sql = "DELETE FROM PM_TEMP_PROGRAM WHERE ID=?";
		jdbcTemplate.update(sql, programId);	
	}

	/**
	 * 清除项目模板数据
	 * @param programId
	 */
	public void cleanProgramTemp(String programId){
		String sql = "DELETE FROM PM_TEMP_PRE_TASK WHERE TASK_ID IN (SELECT ID FROM PM_TEMP_TASK WHERE PROGRAM_ID=?) OR PRE_TASK_ID IN (SELECT ID FROM PM_TEMP_TASK WHERE PROGRAM_ID=?)";
		jdbcTemplate.update(sql, programId, programId);
		sql = "DELETE FROM PM_TEMP_DELIVERABLE_CHECKITEM WHERE DELIVERABLE_ID IN (SELECT ID FROM PM_TEMP_DELIVERABLE WHERE TASK_ID IN (SELECT ID FROM PM_TEMP_TASK WHERE PROGRAM_ID=?))";
		jdbcTemplate.update(sql, programId);	
//		sql = "DELETE FROM PM_TEMP_TASK_DELIVERABLE WHERE TASK_ID IN (SELECT ID FROM PM_TEMP_TASK WHERE PROGRAM_ID=?)";
//		jdbcTemplate.update(sql, programId);	
		sql = "DELETE FROM PM_TEMP_DELIVERABLE WHERE TASK_ID IN (SELECT ID FROM PM_TEMP_TASK WHERE PROGRAM_ID=?)";
		jdbcTemplate.update(sql, programId);	
		sql = "DELETE FROM PM_TEMP_TASK WHERE PROGRAM_ID=?";
		jdbcTemplate.update(sql, programId);	
		sql = "DELETE FROM PM_TEMP_OBS WHERE PROGRAM_ID=?";
		jdbcTemplate.update(sql, programId);	
		sql = "DELETE FROM PM_TEMP_FUNCTION_TASK WHERE FUNCTION_ID IN (SELECT ID FROM PM_TEMP_FUNCTION WHERE PROGRAM_ID=?)";
		jdbcTemplate.update(sql, programId);	
		sql = "DELETE FROM PM_TEMP_FUNCTION WHERE PROGRAM_ID=?";
		jdbcTemplate.update(sql, programId);	
	}
	
	/**
	 * 从Excel导入模板数据（导入前清除原有数据）
	 * @param programId
	 * @param fileName
	 * @param is
	 * @param userId
	 */
	public void importProgramFromExcel(String programId, String fileName, InputStream is, String userId) {

		errorMsgs = new ArrayList<String>();
		programOrgList = new ArrayList<ProgramOrgTemp>();
		programFunctionList = new ArrayList<ProgramFunctionTemp>();
		gateDelvMap = new HashMap<String, Map<String, ProgramGateDelv>>();
		programTaskMap = new HashMap<String, ProgramTask>();

		try {
			
			cleanProgramTemp(programId);
			

			Workbook wb = POIUtils.createWorkbook(fileName, is);

			// 1 导入项目组织
			Sheet sheet = wb.getSheet(TABNAME_1);
			if (sheet != null) {
				importProgramOBSTemp(programId,userId, sheet);
			} else {
				throw new RuntimeException("Excel中未找到项目组织模板数据，标签名称：" + TABNAME_1);
			}

			// 2 泳道模板
			sheet = wb.getSheet(TABNAME_2);
			if (sheet != null) {
				importProgramFunctionTemp(programId,userId, sheet);
			} else {
				throw new RuntimeException("Excel中未找到项目组织模板数据，标签名称：" + TABNAME_2);
			}

			
			// 3 导入主节点
			sheet = wb.getSheet(TABNAME_3);
			if (sheet != null) {
				importMainNode(programId,userId, sheet);
			} else {
				throw new RuntimeException("Excel中未找到主节点模板数据，标签名称：" + TABNAME_3);
			}
			// 4 导入质量阀
			sheet = wb.getSheet(TABNAME_4);
			if (sheet != null) {
				importGateTemp(programId,userId, sheet);
			} else {
				throw new RuntimeException("Excel中未找到阀门模板数据，标签名称：" + TABNAME_4);
			}
			// 5 阀门交付物
			sheet = wb.getSheet(TABNAME_5);
			if (sheet != null) {
				importGateDelvTemp(programId,userId, sheet);
			} else {
				throw new RuntimeException("Excel中未找阀门交付物模板数据，标签名称：" + TABNAME_5);
			}
			// 6 导入专业领域节点和交付物
			sheet = wb.getSheet(TABNAME_6);
			if (sheet != null) {
				importDeptNode(programId,userId, wb.getSheet(TABNAME_6),null);
			} else {
				throw new RuntimeException("Excel中未找到专业领域节点模板数据，标签名称：" + TABNAME_6);
			}
			
			// 导入专业领域计划(二级计划）
			List<ProgramOrgTemp> functionOrgTempList = getFunctionOrgTempList();
			for (ProgramOrgTemp programOrgTemp : functionOrgTempList) {
				String functionName = programOrgTemp.name;
				String tabName = functionName+"_二级计划";
				sheet = wb.getSheet(tabName);
				if (sheet != null) {
					importDeptNode(programId,userId, wb.getSheet(tabName),programOrgTemp);
				} else {
					System.err.println("未找到专业领域（"+functionName+"）的二级计划,工作表名称："+tabName);
				}
			}
			
			//PM_TEMP_OBS和PM_TEMP_TASK重新排序
			resequenceService.requencePmTempObs(programId);
			resequenceService.requencePmTempTask(programId);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}

		String errorMsgsStr = printErrorMsg();
		if (StringUtils.isNotEmpty(errorMsgsStr)) {
			throw new RuntimeException(errorMsgsStr);
		}

	}

	/**
	 * 导入泳道模板
	 * @param programId
	 * @param userId
	 * @param sheet
	 */
	private void importProgramFunctionTemp(String programId, String userId,
			Sheet sheet) {
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum + 1);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);

				// 项目组织序号
				if (StringUtils.isNotEmpty(firstCellValue)) {
					ProgramFunctionTemp programFunction = new ProgramFunctionTemp();
					programFunction.seq = rowNum+1;
					programFunction.name = POIUtils.getStringCellValue(row.getCell(1));
					String parentObsName =  POIUtils.getStringCellValue(row.getCell(2));	
					if(StringUtils.isEmpty(parentObsName)){
						programFunction.parentProgramOrg = getRootProgramOrg();
					}else{
						programFunction.parentProgramOrg=getFunctionGroup(parentObsName);
					}
							
					String obsName = POIUtils.getStringCellValue(row.getCell(3));;
					programFunction.programOrg=getProgramOrg(programFunction.parentProgramOrg.name,obsName);
					debugLog(programFunction);
					programFunctionList.add(programFunction);
				}

			}

		}

		// 保存泳道到数据库
		saveProgramFunctionTempList(programId,userId);
		
	}

	/**
	 * 导入项目组织模板
	 * 
	 * @param sheet
	 */

	private void importProgramOBSTemp(String programId, String userId,Sheet sheet) {
		ProgramOrgTemp programOrg = null;
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum + 1);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);

				// 项目组织序号
				if (StringUtils.isNotEmpty(firstCellValue)) {
					programOrg = new ProgramOrgTemp();
					programOrg.seq = firstCellValue;
					programOrg.code = POIUtils.getStringCellValue(row.getCell(1));
					programOrg.name = POIUtils.getStringCellValue(row.getCell(2));
					programOrg.isFunctionGroup = getBooleanValue(POIUtils.getStringCellValue(row.getCell(3)));
					programOrg.parentCode = POIUtils.getStringCellValue(row.getCell(4));
					programOrg.parent = getProgramOrgByCode(programOrg.parentCode);
					debugLog(programOrg);
					programOrgList.add(programOrg);
				}

			}

		}

		// 保存项目组织到数据库
		saveProgramOrgTempList(programId,userId);
	}

	/**
	 * 导入主节点模板
	 * 
	 * @param sheet
	 */
	private void importMainNode(String programId,String userId, Sheet sheet) {
		System.out.println("导主节点");
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum+1);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);

				// 导入主节点
				if (StringUtils.isNotEmpty(firstCellValue)) {
					ProgramTask mainNode = new ProgramTask();
					mainNode.code = POIUtils.getStringCellValue(row.getCell(1));// 节点编码
					mainNode.name = POIUtils.getStringCellValue(row.getCell(2));// 节点名称
					mainNode.finishDaysToSOP = POIUtils.getIntCellValue(row.getCell(3));// 计划完成周期（天）
					if (mainNode.finishDaysToSOP == 0) {
						mainNode.type = PDMSConstants.TASK_TYPE_SOP_NODE;
					} else {
						mainNode.type = PDMSConstants.TASK_TYPE_MAIN_NODE;
					}
					addTaskToMap(mainNode);
					debugLog(mainNode);
				}


			}
		}

		// 保存主节点
		saveMainNodeTemp(programId,userId);

	}
	
	/**
	 * 导入质量阀、阀门交付物和阶段
	 * 
	 * @param sheet
	 */
	private void importGateTemp(String programId,String userId, Sheet sheet) {
		System.out.println("导入质量阀");
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum+1);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);

				// 导入质量阀
				if (StringUtils.isNotEmpty(firstCellValue)) {
					ProgramTask gate = new ProgramTask();
					gate.code = POIUtils.getStringCellValue(row.getCell(1));// 阀门编码
					gate.name = POIUtils.getStringCellValue(row.getCell(2));// 阀门名称
					gate.finishDaysToSOP = POIUtils.getIntCellValue(row.getCell(3));// 计划完成周期（天）
					gate.type = PDMSConstants.TASK_TYPE_GATE;
					addTaskToMap(gate);
					debugLog(gate);
				}

			}
		}

		// 保存阀门
		saveGateTemp(programId,userId);

	}
	/**
	 * 导入阀门交付物
	 * 
	 * @param sheet
	 */
	private void importGateDelvTemp(String programId,String userId, Sheet sheet) {
		System.out.println("导入质量阀交付物");
		// 循环行Row
		for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum+1);
			if (!POIUtils.isEmpty(row)) {
				debugLog("行号=" + (rowNum + 1));
				Cell firstCell = row.getCell(0);
				String firstCellValue = POIUtils.getStringCellValue(firstCell);
				
				// 导入质量阀交付物
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
					if (!gateDelvMap.containsKey(programGateDelv.gate.code)) {
						gateDelvMap.put(programGateDelv.gate.code, new HashMap<String, ProgramGateDelv>());
					}
					Map<String, ProgramGateDelv> map = gateDelvMap.get(programGateDelv.gate.code);
					if (map.containsKey(programGateDelv.code)) {
						throw new RuntimeException("质量阀交付物编码重复。质量阀编码：" + gateCode + ",交付物编码：" + programGateDelv.code);
					}
					map.put(programGateDelv.code, programGateDelv);
					debugLog(programGateDelv);
				}
				
			}
		}
		
		// 保存阀门交付物
		saveGateDelvTemp(programId,userId);
		
	}


	private void saveProgramFunctionTempList(String programId,String userId) {
		for (int i=0;i<programFunctionList.size();i++) {
			ProgramFunctionTemp functionTemp = programFunctionList.get(i);
			String sql = "INSERT INTO PM_TEMP_FUNCTION(ID,DISPLAY_NAME,PARENT_OBS_ID,CHILD_OBS_ID,PROGRAM_ID,SEQ_NO) VALUES(?,?,?,?,?,?)";
			String uuid = getUUID();
			functionTemp.id = uuid;
			String parentObsId = (functionTemp.parentProgramOrg == null)?null:functionTemp.parentProgramOrg.id;
			jdbcTemplate.update(sql, uuid, functionTemp.name, parentObsId, functionTemp.programOrg.id, programId, i+1);

					//updateCreateUserAndCreateDate("PM_TEMP_FUNCTION", uuid, userId);

		}


	}
	
	private void saveProgramOrgTempList(String programId,String userId) {
		for (ProgramOrgTemp programOrg : programOrgList) {
			String sql = "INSERT INTO PM_TEMP_OBS(ID,OBS_CODE,OBS_NAME,PARENT_ID,PROGRAM_ID,OBS_TYPE_CODE) VALUES(?,?,?,?,?,?)";
			String uuid = getUUID();
			String type=null;
			if(programOrg.isFunctionGroup){
				type = PDMSConstants.OBS_TYPE_RESP_DEPT;
			}
			jdbcTemplate.update(sql, uuid, programOrg.code, programOrg.name, null, programId, type);
			programOrg.id = uuid;
			updateCreateUserAndCreateDate("PM_TEMP_OBS", uuid, userId);

		}

		// 保存上级组织架构
		for (ProgramOrgTemp programOrg : programOrgList) {
			String parentId = (programOrg.parent != null) ? programOrg.parent.id : null;
			if (parentId != null) {
				String sql = "UPDATE PM_TEMP_OBS SET PARENT_ID=? WHERE ID=?";
				jdbcTemplate.update(sql, parentId, programOrg.id);
			}

		}

	}

	private void saveMainNodeTemp(String programId,String userId) {
		Collection<ProgramTask> values = programTaskMap.values();
		for (ProgramTask mainNode : values) {
			if (mainNode.type.equals(PDMSConstants.TASK_TYPE_MAIN_NODE) || mainNode.type.equals(PDMSConstants.TASK_TYPE_SOP_NODE)) {
				String sql = "INSERT INTO PM_TEMP_TASK(ID,TASK_CODE,TASK_NAME,FINISH_DAYS_TO_SOP,TASK_TYPE_CODE,PROGRAM_ID,IS_SHOW) VALUES(?,?,?,?,?,?,?)";
				mainNode.id = getUUID();
				jdbcTemplate.update(sql, mainNode.id, mainNode.code, mainNode.name, mainNode.finishDaysToSOP, mainNode.type, programId, "Y");
				updateCreateUserAndCreateDate("PM_TEMP_TASK", mainNode.id, userId);

			}
		}

	}

	private void saveGateTemp(String programId,String userId) {
		Collection<ProgramTask> values = programTaskMap.values();
		for (ProgramTask gate : values) {
			if (gate.type.equals(PDMSConstants.TASK_TYPE_GATE)) {
				String sql = "INSERT INTO PM_TEMP_TASK(ID,TASK_CODE,TASK_NAME,FINISH_DAYS_TO_SOP,TASK_TYPE_CODE,PROGRAM_ID,IS_SHOW) VALUES(?,?,?,?,?,?,?)";
				gate.id = getUUID();
				jdbcTemplate.update(sql, gate.id, gate.code, gate.name, gate.finishDaysToSOP, gate.type, programId, "Y");
				updateCreateUserAndCreateDate("PM_TEMP_TASK", gate.id, userId);

			}
		}

	}
	
	private void saveGateDelvTemp(String programId,String userId) {
		Collection<Map<String, ProgramGateDelv>> gateDelvsMap = gateDelvMap.values();
		for (Map<String, ProgramGateDelv> gateDelv : gateDelvsMap) {
			Collection<ProgramGateDelv> gateDelvs = gateDelv.values();
			for (ProgramGateDelv delv : gateDelvs) {
				// 保存阀门交付物
				String sql = "INSERT INTO PM_TEMP_DELIVERABLE(ID,NAME,CODE,TASK_ID,OBS_ID,IS_KEY,PROGRAM_ID) VALUES(?,?,?,?,?,?,?)";
				delv.id = getUUID();
				jdbcTemplate.update(sql, delv.id, delv.name, delv.code, delv.gate.id,delv.programOrg.id, delv.isKey,programId);
				updateCreateUserAndCreateDate("PM_TEMP_DELIVERABLE", delv.id, userId);
				// 保存检查项
				sql = "INSERT INTO PM_TEMP_DELIVERABLE_CHECKITEM(ID,SEQ,NAME,CHECK_REQUIREMENT,DELIVERABLE_ID) VALUES(?,?,?,?,?)";
				String uuid = getUUID();
				jdbcTemplate.update(sql, uuid, 1, delv.checkitem, delv.checkRequirement, delv.id);
				updateCreateUserAndCreateDate("PM_TEMP_DELIVERABLE_CHECKITEM", uuid, userId);

			}

		}

	}


	private void importDeptNode(String programId,String userId, Sheet sheet,ProgramOrgTemp functionOrgTemp) {
		Integer planLevel = 1;
		if(functionOrgTemp != null){
			planLevel = 2;
			System.out.println("导入专业领域节点和交付物,专业领域名称="+functionOrgTemp.name);
		}
		Collection<ProgramTask> programTasks = new ArrayList<ProgramTask>();
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

				String delvTempCodesStr = POIUtils.getStringCellValue(row.getCell(8));
				if(StringUtils.isNotEmpty(delvTempCodesStr)){
					node.programGateDelvs = getProgramGateDelvs(node.gate.code, getStringList(delvTempCodesStr));// 阀门交付物
				}
				// node.swimlane =
				// getSwimlane(POIUtils.getStringCellValue(row.getCell(10)));//
				if(planLevel == 1){
					node.programOrg = getFunctionGroup(POIUtils.getStringCellValue(row.getCell(9)));// 责任组OBS
				}else{
					node.programOrg = getProgramOrg(POIUtils.getStringCellValue(row.getCell(9)));// 责任组OBS
				}
				String funcName = POIUtils.getStringCellValue(row.getCell(10));//泳道名称
				if(functionOrgTemp == null){
					node.programFunctionTemp = getProgramFunctionTemp(getRootProgramOrg(),funcName);
				}else{
					node.programFunctionTemp = getProgramFunctionTemp(functionOrgTemp,funcName);//泳道
				}
				//专业领域
				if(functionOrgTemp != null){
					node.functionOrg = functionOrgTemp;// 专业领域
					if(functionOrgTemp.name.equals(node.programOrg.name)){
//					addErrorMsg("节点的责任组("+node.programOrg.name+")不属于专业领域(" + programOrgName + ")。");
					}
				}else{
					node.functionOrg = node.programOrg;
				}
//				node.isShow = getBooleanValue(POIUtils.getStringCellValue(row.getCell(10)));// 是否显示在时程表
				node.startDaysToSOP = POIUtils.getIntCellValue(row.getCell(11));// 计划开始日期
				node.finishDaysToSOP = POIUtils.getIntCellValue(row.getCell(13));// 计划结束日期
				if(node.type.equals(PDMSConstants.TASK_TYPE_ACTIVITY)&& node.startDaysToSOP == null){
					addErrorMsg("活动(" + node.code + ")的计划开始周期不能为空！");
				}
				if(node.type.equals(PDMSConstants.TASK_TYPE_NODE)&& node.finishDaysToSOP == null){
					addErrorMsg("活动或节点(" + node.code + ")的计划完成周期不能为空！");

				}
				if(node.gate != null){
					if (node.finishDaysToSOP > node.gate.finishDaysToSOP) {
						addErrorMsg("节点(" + node.code + ")计划完成时间(" + node.finishDaysToSOP + ")必须早于后置阀门(" + node.gate.code + ")的计划完成时间(" + node.gate.finishDaysToSOP + ")。");
					}
				}
				
				node.planLevel = planLevel;

				addTaskToMap(node);
				programTasks.add(node);
				debugLog(node);

			} else {
				break;
			}
		}
		saveDeptNodes(programTasks,programId,userId);
	}
	
	private ProgramFunctionTemp getProgramFunctionTemp(
			ProgramOrgTemp programOrg, String functionName) {
		if(StringUtils.isNotEmpty(functionName)){
			for(ProgramFunctionTemp programFunctionTemp : programFunctionList){
				if(programFunctionTemp.name.equals(functionName)){
					if(programFunctionTemp.parentProgramOrg.equals(programOrg)){
						return programFunctionTemp;
					}
				}
			}
			throw new RuntimeException("没有找到项目组织（"+programOrg.name+"）相关的泳道（"+functionName+"）！");
		}else{
			return null;
		}
	}

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
	private void addTaskToMap(ProgramTask node) {
		if (programTaskMap.containsKey(node.code)) {
			ProgramTask task = programTaskMap.get(node.code);
			throw new RuntimeException("节点编码重复。编码=" + node.code + ",task=" + task);
		}
		programTaskMap.put(node.code, node);

	}
	
	private void saveDeptNodes(Collection<ProgramTask> programTasks,String programId,String userId) {
		for (ProgramTask node : programTasks) {
			if (node.type.equals(PDMSConstants.TASK_TYPE_ACTIVITY) || node.type.equals(PDMSConstants.TASK_TYPE_NODE)) {
				String sql = "INSERT INTO PM_TEMP_TASK(" + 
						"ID," + 
						"TASK_CODE," + 
						"TASK_NAME," + 
						"START_DAYS_TO_SOP," + 
						"FINISH_DAYS_TO_SOP," + 
						"TASK_TYPE_CODE," + 
						"PROGRAM_ID," + 
						"PARENT_ID,"+ 
						"GATE_ID," + 
						"OBS_ID," + 
						"IS_SHOW,PLAN_LEVEL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				node.id = getUUID();
				String parentId = node.parentTask != null ? node.parentTask.id : null;
				String gateId = (node.gate == null)?null:node.gate.id;
				jdbcTemplate.update(sql, 
						node.id, 
						node.code, 
						node.name, 
						node.startDaysToSOP, 
						node.finishDaysToSOP, 
						node.type, 
						programId, 
						parentId, 
						gateId, 
						node.programOrg.id, 
						node.isShow,
						node.planLevel);
				updateCreateUserAndCreateDate("PM_TEMP_TASK", node.id, userId);
				
				// 保存关联交付物
				if(node.programGateDelvs.size()>0){
					ProgramGateDelv programGateDelv = node.programGateDelvs.get(0);
					sql = "UPDATE PM_TEMP_DELIVERABLE SET RESP_TASK_ID=? WHERE ID=?";
					jdbcTemplate.update(sql, node.id, programGateDelv.id);
				}
				
				if(node.programFunctionTemp != null){
					//保存任务所属泳道
					sql = "INSERT INTO PM_TEMP_FUNCTION_TASK(FUNCTION_ID,TASK_ID) VALUES(?,?)";
					jdbcTemplate.update(sql, node.programFunctionTemp.id, node.id);
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

						String sql = "INSERT INTO PM_TEMP_PRE_TASK(ID,TASK_ID,PRE_TASK_ID,PRE_TASK_TYPE_CODE,LAG) VALUES(?,?,?,?,?)";
						String uuid =  getUUID();
						jdbcTemplate.update(sql, uuid, node.id, preTask.id, PDMSConstants.PRE_TASK_TYPE_FS, 0);
						updateCreateUserAndCreateDate("PM_TEMP_PRE_TASK", uuid, userId);

					}

				}

				// 更新关联节点
				if (StringUtils.isNotEmpty(node.relationTaskCode)) {
					// 检查当前节点时间必须早于关联节点
					ProgramTask relationTask = getProgramTask(node.relationTaskCode);
					if (node.finishDaysToSOP != null && node.finishDaysToSOP > relationTask.finishDaysToSOP) {
						addErrorMsg("当前节点(" + node.code + ")计划完成时间(" + node.finishDaysToSOP + ")必须早于关联节点(" + relationTask.code + ")的计划完成时间(" + relationTask.finishDaysToSOP + ")。");
					}
					node.relationTask = relationTask;
					String sql = "UPDATE PM_TEMP_TASK SET RELATION_TASK_ID=? WHERE ID=?";
					jdbcTemplate.update(sql, relationTask.id, node.id);

				}
			}
		}
	}

//	private void saveDeptNodes(String programId,String userId) {
//		Collection<ProgramTask> programTasks = programTaskMap.values();
//		saveDeptNode(programTasks,programId,userId);
//
//	}

	private ProgramOrgTemp getProgramOrgByCode(String code) {
		if (StringUtils.isNotEmpty(code)) {
			for (ProgramOrgTemp programOrg : programOrgList) {
				if (programOrg.code.equals(code)) {
					return programOrg;
				}
			}
			throw new RuntimeException("项目组未定义。编码=" + code);
		}
		return null;
	}

	private ProgramOrgTemp getProgramOrg(String parentProgramOrgName,String programOrgName) {
		if (StringUtils.isNotEmpty(programOrgName)) {
			for (ProgramOrgTemp programOrg : programOrgList) {
				if (programOrg.name.equals(programOrgName)) {
					if(null != programOrg.parent){
						if(programOrg.parent.name.equals(parentProgramOrgName)){
							return programOrg;
						}
					}
				}
			}
			throw new RuntimeException("项目组未定义。上级名称= "+parentProgramOrgName+",名称=" + programOrgName);
		}
		return null;
	}
	
	public ProgramOrgTemp getRootProgramOrg(){
		for (ProgramOrgTemp programOrg : programOrgList) {
			if (programOrg.parent == null) {
				return programOrg;
			}
		}
		return null;
	}
	private ProgramOrgTemp getProgramOrg(String programOrgName) {
		for (ProgramOrgTemp programOrg : programOrgList) {
			if (programOrg.name.equals(programOrgName) && !programOrg.isFunctionGroup) {
				return programOrg;
			}
		}
		throw new RuntimeException("项目组织未定义。名称=" + programOrgName);
	}
	private ProgramOrgTemp getFunctionGroup(String programOrgName) {
		for (ProgramOrgTemp programOrg : programOrgList) {
			if (programOrg.name.equals(programOrgName) && programOrg.isFunctionGroup) {
				return programOrg;
			}
		}
		throw new RuntimeException("责任组未定义。名称=" + programOrgName);
	}
	
	/**
	 * 查询所有专业领域
	 * @return
	 */
	private List<ProgramOrgTemp> getFunctionOrgTempList() {
		List<ProgramOrgTemp> functionProgramOrgTempList = new ArrayList<ProgramOrgTemp>();
		for (ProgramOrgTemp programOrg : programOrgList) {
			if (programOrg.isFunctionGroup) {
				functionProgramOrgTempList.add(programOrg);
			}
		}
		return functionProgramOrgTempList;
	}

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

	private ProgramTask getProgramTask(String code) {
		if (StringUtils.isNotEmpty(code)) {
			if (programTaskMap.containsKey(code)) {
				return programTaskMap.get(code);
			}
			throw new RuntimeException("任务未找到，代码：" + code);
		}
		return null;
	}

	private List<ProgramTask> getProgramTasks(List<String> codes) {
		List<ProgramTask> list = new ArrayList<ProgramTask>();
		for (String code : codes) {
			list.add(getProgramTask(code));
		}
		return list;
	}

	public void debugLog(Object obj) {
		System.out.println(obj);
	}

	private String generateErrorMsg(int lineNum, String message) {
		return message + ",出错行号：" + lineNum;
	}

	private ProgramOrgTemp findProgramOrgByName(String name) {
		for (ProgramOrgTemp programOrg : programOrgList) {
			if (programOrg.name.equals(name)) {
				return programOrg;
			}
		}

		throw new RuntimeException("专业组未找到，名称：" + name);
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
	public class ProgramOrgTemp {
		public String id = getUUID();;
		public String seq;// 序号
		public String code;// 项目组织编码
		public String name;// 项目组织名称
		//public String type;// 项目组织类型
		public ProgramOrgTemp parent;// 上级项目组织
		public String parentCode;// 上级项目组织编码
		public boolean isFunctionGroup;//是否是专业领域

		@Override
		public String toString() {
			String parentOrgSeq = (parent == null) ? null : parent.seq;
			return "【项目组织】序号：" + seq + "\t\t名称：" + name + "\t\t上级项目组织序号：" + parentOrgSeq;
		}
	}

	// 项目组织成员
	public class ProgramOrgMember {
		public ProgramOrgTemp programOrg;// 项目组织
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
	public class ProgramFunctionTemp {
		public String id;
		public int seq;// 序号
		public String code;// 编码
		public String name;// 名称
		//public String type;// 类型
		public ProgramOrgTemp parentProgramOrg;// 所属项目组织（所属计划）
		public ProgramOrgTemp programOrg;// 责任组
		

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
		public Integer startDaysToSOP;// 计划开始日期到SOP节点的天数
		public Integer finishDaysToSOP;// 计划完成日期到SOP节点的天数
		public ProgramTask gate;// 后置阀门编码
		public List<ProgramGateDelv> programGateDelvs = new ArrayList<ProgramGateDelv>();// 关联阀门交付物
		public ProgramOrgTemp programOrg;// 负责专业组代码（专业领域）
		public ProgramOrgTemp functionOrg;// 泳道
		public boolean isShow;// 是否显示在时程表
		public ProgramFunctionTemp programFunctionTemp;//泳道模板
		public Integer planLevel;//节点层级

		@Override
		public String toString() {
			return "【项目Task】类型：" + type + "\t\t编码：" + code + "\t\t名称：" + name + "\t\t开始周期（天）：" + startDaysToSOP + "\t\t完成周期（天）：" + finishDaysToSOP;
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
		public ProgramOrgTemp programOrg;// 负责专业组
		public String isKey;// 是否关键交付物

		@Override
		public String toString() {
			return "【阀门交付物】阀门：" + gate.code + "\t\t编码：" + code + "\t\t名称：" + name + "\t\t查项目：" + checkitem + "\t\t单项通过要求及验收办法：" + checkitem + "\t\t负责专业组：" + programOrg.name + "\t\t是否关键交付物：" + isKey
					;
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
	

	//导出模板到Excel
    public void export2Excel(String programTempId,OutputStream os) {  
        Workbook wb = new HSSFWorkbook();    
        
        try{
        	InputStream is = this.getClass().getResourceAsStream("/template/programTempExportTemp.xls");
        	wb =  POIUtils.createWorkbook(is);
        	
        	//1.项目组织模板
        	exportObsTemp(programTempId,wb);
        	//2、泳道模板
        	exportSwimlaneTemp(programTempId,wb);
        	//3.主计划主节点模板
        	exportMainNodeTemp(programTempId,wb);
        	//4.质量阀模板
        	exportGateTemp(programTempId,wb);
        	//5.阀门交付物模板
        	exportGateDelvTemp(programTempId,wb);
        	//6.主计划专业领域节点模板
        	exportManPlanDeptNode(programTempId,wb);
        	//各专业领域二级计划
        	exportSubPlanDeptNode(programTempId,wb);
        	
        	wb.write(os);
        	
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        
    }


	/**
     * 导出项目组织模板
     * @param wb
     */
	private void exportObsTemp(String programTempId,Workbook wb) {
		String sql = "select OBS.OBS_CODE,OBS.OBS_NAME,OBS.OBS_TYPE_CODE,P_OBS.OBS_CODE AS PARENT_OBS_CODE from PM_TEMP_OBS OBS"+
				"	LEFT JOIN PM_TEMP_OBS P_OBS ON OBS.PARENT_ID=P_OBS.ID"+
				"	WHERE OBS.PROGRAM_ID=?"+	
				"	ORDER BY OBS.LFT";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		
		Sheet sheet = wb.getSheet(TABNAME_1); 
		int rowNo = 1;
		for(Map<String, Object> m : list){
			String OBS_CODE = ObjectConverter.convert2String(m.get("OBS_CODE"));
			String OBS_NAME = ObjectConverter.convert2String(m.get("OBS_NAME"));
			String OBS_TYPE_CODE = ObjectConverter.convert2String(m.get("OBS_TYPE_CODE"));
			String PARENT_OBS_CODE = ObjectConverter.convert2String(m.get("PARENT_OBS_CODE"));
			String isFunctionGroup = "N";
			if(PDMSConstants.OBS_TYPE_RESP_DEPT.equals(OBS_TYPE_CODE)){
				isFunctionGroup = "Y";
			}
			Row row = sheet.createRow(rowNo);
			//序号
			row.createCell(0).setCellValue(""+rowNo);
			row.createCell(1).setCellValue(OBS_CODE);
			row.createCell(2).setCellValue(OBS_NAME);
			row.createCell(3).setCellValue(isFunctionGroup);
			row.createCell(4).setCellValue(PARENT_OBS_CODE);
			
			rowNo++;
			
		}
	} 
    
	private void exportSwimlaneTemp(String programTempId,Workbook wb) {
		String sql = "SELECT SEQ_NO,DISPLAY_NAME,PARENT_OBS_NAME,CHILD_OBS_NAME,PROGRAM_ID FROM V_PM_TEMP_FUNCTION"+
				"	WHERE PROGRAM_ID=?  ORDER BY SEQ_NO";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		
		Sheet sheet = wb.getSheet(TABNAME_2); 
		int rowNo = 1;
		for(Map<String, Object> m : list){
			String DISPLAY_NAME = ObjectConverter.convert2String(m.get("DISPLAY_NAME"));
			String PARENT_OBS_NAME = ObjectConverter.convert2String(m.get("PARENT_OBS_NAME"));
			String CHILD_OBS_NAME = ObjectConverter.convert2String(m.get("CHILD_OBS_NAME"));
			Row row = sheet.createRow(rowNo);
			//序号
			row.createCell(0).setCellValue(""+rowNo);
			row.createCell(1).setCellValue(DISPLAY_NAME);
			row.createCell(2).setCellValue(PARENT_OBS_NAME);
			row.createCell(3).setCellValue(CHILD_OBS_NAME);
			
			rowNo++;
		}

	}


	private void exportMainNodeTemp(String programTempId,Workbook wb) {
		String sql = "SELECT TASK_CODE,TASK_NAME,FINISH_DAYS_TO_SOP,ROUND(FINISH_DAYS_TO_SOP/30,2) FINISH_MONTH_TO_SOP FROM V_PM_TEMP_MAIN_NODE"+
				"	WHERE PROGRAM_ID=?  ORDER BY FINISH_DAYS_TO_SOP";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		
		Sheet sheet = wb.getSheet(TABNAME_3); 
		int rowNo = 1;
		for(Map<String, Object> m : list){
			String TASK_CODE = ObjectConverter.convert2String(m.get("TASK_CODE"));
			String TASK_NAME = ObjectConverter.convert2String(m.get("TASK_NAME"));
			String FINISH_DAYS_TO_SOP = ObjectConverter.convert2String(m.get("FINISH_DAYS_TO_SOP"));
			String FINISH_MONTH_TO_SOP = ObjectConverter.convert2String(m.get("FINISH_MONTH_TO_SOP"));
			Row row = sheet.createRow(rowNo);
			//序号
			row.createCell(0).setCellValue(""+rowNo);
			row.createCell(1).setCellValue(TASK_CODE);
			row.createCell(2).setCellValue(TASK_NAME);
			row.createCell(3).setCellValue(FINISH_DAYS_TO_SOP);
			row.createCell(4).setCellValue(FINISH_MONTH_TO_SOP);
			
			rowNo++;
		}
	}
	
	private void exportGateTemp(String programTempId,Workbook wb) {
		String sql = "SELECT TASK_CODE,TASK_NAME,FINISH_DAYS_TO_SOP,ROUND(FINISH_DAYS_TO_SOP/30,2) FINISH_MONTH_TO_SOP FROM V_PM_TEMP_GATE"+
				"	WHERE PROGRAM_ID=?  ORDER BY FINISH_DAYS_TO_SOP";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		
		Sheet sheet = wb.getSheet(TABNAME_4); 
		int rowNo = 1;
		for(Map<String, Object> m : list){
			String TASK_CODE = ObjectConverter.convert2String(m.get("TASK_CODE"));
			String TASK_NAME = ObjectConverter.convert2String(m.get("TASK_NAME"));
			String FINISH_DAYS_TO_SOP = ObjectConverter.convert2String(m.get("FINISH_DAYS_TO_SOP"));
			String FINISH_MONTH_TO_SOP = ObjectConverter.convert2String(m.get("FINISH_MONTH_TO_SOP"));
			Row row = sheet.createRow(rowNo);
			//序号
			row.createCell(0).setCellValue(""+rowNo);
			row.createCell(1).setCellValue(TASK_CODE);
			row.createCell(2).setCellValue(TASK_NAME);
			row.createCell(3).setCellValue(FINISH_DAYS_TO_SOP);
			row.createCell(4).setCellValue(FINISH_MONTH_TO_SOP);
			
			rowNo++;
		}
		
	}
	
	private void exportGateDelvTemp(String programTempId,Workbook wb) {
		String sql = "SELECT TASK_NAME,CODE,NAME,CHECKITEM_NAME,CHECK_REQUIREMENT,OBS_NAME,IS_KEY from V_PM_TEMP_DELIVERABLE"+
				"	WHERE PROGRAM_ID=?  ORDER BY TASK_CODE DESC, CODE";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
		
		Sheet sheet = wb.getSheet(TABNAME_5); 
		int rowNo = 1;
		for(Map<String, Object> m : list){
			String TASK_NAME = ObjectConverter.convert2String(m.get("TASK_NAME"));
			String CODE = ObjectConverter.convert2String(m.get("CODE"));
			String NAME = ObjectConverter.convert2String(m.get("NAME"));
			String CHECKITEM_NAME = ObjectConverter.convert2String(m.get("CHECKITEM_NAME"));
			String CHECK_REQUIREMENT = ObjectConverter.convert2String(m.get("CHECK_REQUIREMENT"));
			String OBS_NAME = ObjectConverter.convert2String(m.get("OBS_NAME"));
			String IS_KEY = ObjectConverter.convert2String(m.get("IS_KEY"));
			Row row = sheet.createRow(rowNo);
			//序号
			row.createCell(0).setCellValue(""+rowNo);
			row.createCell(1).setCellValue(TASK_NAME);
			row.createCell(2).setCellValue(CODE);
			row.createCell(3).setCellValue(NAME);
			row.createCell(4).setCellValue(CHECKITEM_NAME);
			row.createCell(4).setCellValue(CHECK_REQUIREMENT);
			row.createCell(4).setCellValue(OBS_NAME);
			row.createCell(4).setCellValue(IS_KEY);
			
			rowNo++;
		}
		
	}
	
	private void exportManPlanDeptNode(String programTempId,Workbook wb) {

//		String sql = "SELECT " + 
//						"ID," + 
//						"TASK_CODE," + 
//						"TASK_NAME," + 
//						"START_DAYS_TO_SOP," + 
//						"FINISH_DAYS_TO_SOP," + 
//						"TASK_TYPE_CODE," + 
//						"PROGRAM_ID," + 
//						"PARENT_ID,"+ 
//						"GATE_ID," + 
//						"OBS_ID," + 
//						"IS_SHOW," + 
//						"PLAN_LEVEL "+
//					 "FROM PM_TEMP_TASK"+
//					 "	WHERE PROGRAM_ID=? AND PLAN_LEVEL=1  ORDER BY LFT";
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,programTempId);
//		
//		Sheet sheet = wb.getSheet(TABNAME_6); 
//		int rowNo = 1;
//		for(Map<String, Object> m : list){
//			String TASK_CODE = ObjectConverter.convert2String(m.get("TASK_CODE"));
//			String TASK_NAME = ObjectConverter.convert2String(m.get("TASK_NAME"));
//			String START_DAYS_TO_SOP = ObjectConverter.convert2String(m.get("START_DAYS_TO_SOP"));
//			String FINISH_DAYS_TO_SOP = ObjectConverter.convert2String(m.get("FINISH_DAYS_TO_SOP"));
//			String TASK_TYPE_CODE = ObjectConverter.convert2String(m.get("TASK_TYPE_CODE"));
//			String PROGRAM_ID = ObjectConverter.convert2String(m.get("PROGRAM_ID"));
//			String GATE_ID = ObjectConverter.convert2String(m.get("GATE_ID"));
//			Row row = sheet.createRow(rowNo);
//			//序号
//			row.createCell(0).setCellValue(""+rowNo);
//			row.createCell(1).setCellValue(TASK_NAME);
//			row.createCell(2).setCellValue(CODE);
//			row.createCell(3).setCellValue(NAME);
//			row.createCell(4).setCellValue(CHECKITEM_NAME);
//			row.createCell(4).setCellValue(CHECK_REQUIREMENT);
//			row.createCell(4).setCellValue(OBS_NAME);
//			row.createCell(4).setCellValue(IS_KEY);
//			
//			rowNo++;
//		}
		
	}
	private void exportSubPlanDeptNode(String programTempId,Workbook wb) {
		// TODO Auto-generated method stub
		
	}
}
