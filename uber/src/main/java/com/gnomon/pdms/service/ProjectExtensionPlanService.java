package com.gnomon.pdms.service;

import java.io.File;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.POIUtils;
import com.gnomon.dms.api.DMSDocumentAPI;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.Ext103ListDAO;
import com.gnomon.pdms.dao.ExtendedProjectColumnDefDAO;
import com.gnomon.pdms.dao.ExtendedProjectVMDAO;
import com.gnomon.pdms.entity.Ext103ListEntity;
import com.gnomon.pdms.entity.Ext201ItemEntity;
import com.gnomon.pdms.entity.ExtendedProjectColumnDefEntity;
import com.gnomon.pdms.entity.ExtendedProjectVMEntity;

@Service
@Transactional
public class ProjectExtensionPlanService {

	@Autowired
	private Ext103ListDAO ext103ListDAO;
	
	@Autowired
	private ExtendedProjectVMDAO extendedProjectVMDAO;
	
	@Autowired
	private ExtendedProjectColumnDefDAO extendedProjectColumnDefDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DocumentService documentService;

	/*
	 * 清单类专项列表取得
	 */
	public List<Ext103ListEntity> getProjectExtensionPlanList(String extProjectId){
		String hql = "FROM Ext103ListEntity WHERE extProjectId = ? order by seq";
		List<Ext103ListEntity> projectExtensionPlan =
				this.ext103ListDAO.find(hql, extProjectId);
        return projectExtensionPlan;
    }
	
	/*
	 * 清单类专项基本信息取得
	 */
	public ExtendedProjectVMEntity getProjectExtensionPlanInfo(String extProjectId){
		ExtendedProjectVMEntity result =
				this.extendedProjectVMDAO.findUniqueBy("id", extProjectId);
        return result;
    }
	
	/*
	 * 清单类专项列属性信息取得
	 */
	public List<ExtendedProjectColumnDefEntity> getColumnDefList(String extProjectId){
		String hql = "FROM ExtendedProjectColumnDefEntity WHERE extProjectId = ? order by columnIdx";
		List<ExtendedProjectColumnDefEntity> result =
				this.extendedProjectColumnDefDAO.find(hql, extProjectId);
        return result;
    }
	
	public Long saveExtendedProject(String vehicleId,String extCode,String appName,Date plannedStartDate,Date plannedFinishDate,Long modelId,String relationTaskId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT_INIT_PROCESS(?,?,?,?,?,?,?,?,?,?)}");
		if(modelId == null){
			modelId = 0l;
		}
		statement.setString(1, vehicleId);
		statement.setString(2, extCode);
		statement.setString(3, appName);
		statement.setDate(4, new java.sql.Date(plannedStartDate.getTime()));
		statement.setDate(5, new java.sql.Date(plannedFinishDate.getTime()));
		statement.setLong(6, modelId);
		statement.registerOutParameter(7, Types.INTEGER);
		statement.registerOutParameter(8, Types.VARCHAR);
		statement.registerOutParameter(9, Types.VARCHAR);
		statement.setString(10, relationTaskId);
		statement.executeUpdate();
		Long projectId = statement.getLong(7);
		String returnCode = statement.getString(8);
		String returnMsg = statement.getString(9);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
		return projectId;
	}
	
	public List<Map<String, Object>> getExtProcessList(){
		return jdbcTemplate.queryForList("select * from PM_EXT_PROCESS");
	}
	
	public List<Map<String, Object>> getExtProjectStageList(Long extProjectId){
		return jdbcTemplate.queryForList("select * from PM_EXT201_STAGE where EXT_PROJECT_ID =?",extProjectId);
	}
	
	public List<Map<String, Object>> getExtPartList(Long projectId){
		return jdbcTemplate.queryForList("select * from V_EXT201_STAGE_OBS_ITEM where EXT_PROJECT_ID =?",projectId);
	}
	
	public List<Map<String, Object>> getExt201ContralList(Long projectId){
		return jdbcTemplate.queryForList("select * from V_EXT201_CONTROL_LIST where PROJECT_ID =?",projectId);
	}
	
	public List<Map<String, Object>> getExtStagePartList(Long stageId){
		return jdbcTemplate.queryForList("select * from V_EXT201_STAGE_OBS_ITEM where STAGE_ID =?",stageId);
	}
	
	public List<Map<String, Object>> getExtStagePartList(Long stageId,String obsId){
		if(StringUtils.isEmpty(obsId)){
			return this.getExtStagePartList(stageId);
		}
		return jdbcTemplate.queryForList("select * from V_EXT201_STAGE_OBS_ITEM where STAGE_ID =? and OBS_ID=? ",stageId,obsId);
	}
	
	public List<Map<String, Object>> getExtStagePartProgressList(Long stageId){
		return jdbcTemplate.queryForList("select * from V_EXT201_ITEM_PROGRESS_BY_OBS where STAGE_ID =?",stageId);
	}
	
	public List<Map<String, Object>> getExtPartProgressList(Long projectId){
		return jdbcTemplate.queryForList("select * from V_EXT201_ITEM_PROGRESS_BY_PROJ where EXT_PROJECT_ID =?",projectId);
	}
	
	public void importProgramFromExcel(String projectId, String fileName, InputStream is, String userId) {
		try {
			Workbook wb = POIUtils.createWorkbook(fileName, is);
			Sheet sheet = wb.getSheetAt(0);
			List<Ext201ItemEntity> list = new ArrayList<Ext201ItemEntity>();
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum+3);
				if (!POIUtils.isEmpty(row)) {
//					debugLog("行号=" + (rowNum + 1));
					String num = POIUtils.getStringCellValue(row.getCell(0));
					// 导入主节点
					if (StringUtils.isNotEmpty(num)) {
						Ext201ItemEntity ext201ItemEntity = new Ext201ItemEntity();
						ext201ItemEntity.setSourcingType(POIUtils.getStringCellValue(row.getCell(1)));//采购类型
						ext201ItemEntity.setItemNo(POIUtils.getStringCellValue(row.getCell(2)));// 零件代号
						ext201ItemEntity.setItemName(POIUtils.getStringCellValue(row.getCell(3)));//零件名称
						ext201ItemEntity.setOwner(POIUtils.getStringCellValue(row.getCell(4)));//产品工程师
						ext201ItemEntity.setObsName(POIUtils.getStringCellValue(row.getCell(5)));//设计责任部门
						ext201ItemEntity.setSeverityLevel(POIUtils.getStringCellValue(row.getCell(6)));//重要度
						ext201ItemEntity.setDevType(POIUtils.getStringCellValue(row.getCell(7)));//开发类型
						ext201ItemEntity.setIsSe(POIUtils.getStringCellValue(row.getCell(8)));//是否同步开发
						ext201ItemEntity.setProductionToolingPeriod(POIUtils.getIntCellValue(row.getCell(9)));//是否同步开发
						ext201ItemEntity.setExtProjectId(projectId);
						list.add(ext201ItemEntity);
					}
				}
			}
			this.saveExt201ItemList(list);
			this.saveExt201InitProjectItem(projectId);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public void saveExt201ItemList(List<Ext201ItemEntity> list) throws Exception{
		for(Ext201ItemEntity ext201ItemEntity: list){
			this.saveExt201Item(ext201ItemEntity);
		}
	}
	
	public void saveExt201Item(Ext201ItemEntity entity) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_NEW_ITEM(?,?,?,?,?,?,?,?,?,?,?,?)}");

		statement.setString(1, entity.getExtProjectId());
		statement.setString(2, entity.getItemNo());
		statement.setString(3, entity.getItemName());
		statement.setString(4, entity.getObsName());
		statement.setString(5, entity.getOwner());
		statement.setString(6, entity.getSourcingType());
		statement.setString(7, entity.getSeverityLevel());
		statement.setString(8, entity.getDevType());
		statement.setString(9, entity.getIsSe());
		statement.setInt(10, entity.getProductionToolingPeriod());
		statement.registerOutParameter(11, Types.VARCHAR);
		statement.registerOutParameter(12, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(11);
		String returnMsg = statement.getString(12);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void saveExt201InitProjectItem(String projectId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_INIT_PROJECT_ITEM(?,?,?)}");

		statement.setString(1, projectId);
		statement.registerOutParameter(2, Types.VARCHAR);
		statement.registerOutParameter(3, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(2);
		String returnMsg = statement.getString(3);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void releaseExt201StageItem(Long projectId,Long stageId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_RELEASE_STAGE_ITEM(?,?,?,?)}");

		statement.setLong(1, projectId);
		statement.setLong(2, stageId);
		statement.registerOutParameter(3, Types.VARCHAR);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(3);
		String returnMsg = statement.getString(4);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void startExt201Task(Long taskId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_MARK_TASK_START(?,?,?)}");

		statement.setLong(1, taskId);
		statement.registerOutParameter(2, Types.VARCHAR);
		statement.registerOutParameter(3, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(2);
		String returnMsg = statement.getString(3);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void finishedExt201Task(Long taskId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_UPDATE_ITEM_STATUS(?,?,?,?)}");

		statement.setLong(1, taskId);
		statement.setString(2, "Y");
		statement.registerOutParameter(3, Types.VARCHAR);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(3);
		String returnMsg = statement.getString(4);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public List<Map<String, Object>> getExt201TaskCheckList(Long stageItemId){
		return jdbcTemplate.queryForList("select * from V_EXT201_TASK_CHECKITEM where STAGE_ITEM_ID =?",stageItemId);
	}
	
	public Map<String, Object> getExt201TaskForm(Long taskId){
		return jdbcTemplate.queryForMap("select * from V_EXT201_STAGE_ITEM_INFO where STAGE_ITEM_ID =?",taskId);
	}
	
	public void updateExt201TaskStatus(Long taskId,String isCompleted,String remark) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_UPDATE_TASK_STATUS(?,?,?,?,?)}");

		statement.setLong(1, taskId);
		statement.setString(2, isCompleted);
		statement.setString(3, remark);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void uploadChecklistDocument(Long revisionId,Long taskId,String fileName,File file) throws Exception{
		if(revisionId == null){
			String revisionIdTmp = documentService.checkinExtDocument(fileName, file);
			revisionId = Long.valueOf(revisionIdTmp);
		}
		
		DMSDocumentRevision dmsDocumentRevision = getDMSDocumentAPI().getRevision(revisionId);
		String documentId = PDMSCommon.nvl(dmsDocumentRevision.getDocument().getId());
		uploadEvidence(taskId,documentId,revisionId.toString());
	}
	
	private DMSDocumentAPI getDMSDocumentAPI(){
		return DmsAPIFactory.getDocumentAPI();
	}
	
	public void uploadEvidence(Long taskId,String documentId,String revisionId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call SP_EXT201_ADD_TASK_EVIDENCE(?,?,?,?,?)}");

		statement.setLong(1, taskId);
		statement.setString(2, documentId);
		statement.setString(3, revisionId);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public List<Map<String, Object>> getChecklistDocuments(Long taskId){
		return jdbcTemplate.queryForList("select * from PM_EXT201_TASK_EVIDENCE where TASK_ID =?",taskId);
	}
	
	
	public String getUserName(String userId) throws Exception{
		Session session = extendedProjectVMDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{?=call SYS_GET_USERNAME_BY_ID(?)}");
		statement.registerOutParameter(1, Types.VARCHAR);
		statement.setString(2, userId);
		statement.executeUpdate();
		String userName = statement.getString(1);
		return userName;
	}
	
	public List<Map<String, Object>> getExt201TaskCheckListNotes(Long taskId){
		return jdbcTemplate.queryForList("select * from PM_EXT201_TASK_NOTE where TASK_ID in (select id from V_EXT201_TASK_CHECKITEM where STAGE_ITEM_ID=?) ",taskId);
	}
	
	public List<Map<String, Object>> getChecklistAllDocuments(Long stageItemId){
		return jdbcTemplate.queryForList("select * from PM_EXT201_TASK_EVIDENCE where STAGE_ITEM_ID =?",stageItemId);
	}
	
	

}
