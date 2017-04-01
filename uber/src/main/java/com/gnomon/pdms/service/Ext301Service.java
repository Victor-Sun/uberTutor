package com.gnomon.pdms.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bios.report.api.manager.ReportBean;
import bios.report.api.manager.ReportManager;
import bios.report.api.model.ReportTemplet;
import bios.report.core.model.docket.Parameter;
import bios.report.core.model.docket.Variable;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.FileUtils;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.utils.POIUtils;
import com.gnomon.pdms.dao.Ext301ItemDAO;
import com.gnomon.pdms.dao.Ext301TaskDAO;
import com.gnomon.pdms.entity.Ext301ItemEntity;
import com.gnomon.pdms.entity.Ext301TaskEntity;

@Service
@Transactional
public class Ext301Service {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Ext301ItemDAO ext301ItemDAO;
	
	@Autowired
	private Ext301TaskDAO ext301TaskDAO;
	
	@Autowired
	private PmObsService pmObsService;
	
	@Autowired
	private GTUserManager gtUserManager;
	
	@Autowired
	private DocumentService documentService;
	
	public List<Map<String, Object>> getExt202ModelList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT202_MODEL ");
        return list;
    }
	
	public List<Map<String, Object>> getRelationTaskList(String vehicleId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_PM_TASK_LEAF where PROGRAM_VEHICLE_ID=? ",vehicleId);
        return list;
    }
	
	public List<Map<String, Object>> getPartNodeItemList(Long itemId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_EXT301_ITEM_TASK WHERE ITEM_ID = ?",itemId);
        return list;
    }
	
	public List<Map<String, Object>> getPartNodeList(Long projectId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_EXT301_ITEM_TASK WHERE ITEM_ID = ? ",projectId);
        return list;
    }
	
	public List<Map<String, Object>> getPartSchedulePool(String programVehicleId,String taskOwner) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_EXT301_SCHEDULE_POOL WHERE STEP_CODE = 'STEP_1' AND PROGRAM_VEHICLE_ID = ? AND TASK_OWNER = ? ",programVehicleId,taskOwner);
        return list;
    }
	
	public Long getModelId(Long projectId){
		return jdbcTemplate.queryForLong("select MODEL_ID from PM_EXT202_TABLE where EXT_PROJECT_ID=? ",projectId);
	}
	
	public Map<String, Object> getExt202Table(Long projectId){
		return jdbcTemplate.queryForMap("select * from PM_EXT202_TABLE where EXT_PROJECT_ID=?",projectId);
	}
	
	public List<Map<String, Object>> getExt202TableData(Long projectId){
		Map<String, Object> ext202Table = this.getExt202Table(projectId);
		return jdbcTemplate.queryForList("select * from "+ext202Table.get("TABLE_NAME"));
	}
	
	public void saveNodeInfo(Long taskId,String plannedFinishDate,String actualFinishDate){
		Ext301TaskEntity entity = ext301TaskDAO.get(taskId);
		entity.setPlannedFinishDate(DateUtils.strToDate(plannedFinishDate));
		entity.setActualFinishDate(DateUtils.strToDate(actualFinishDate));
		ext301TaskDAO.save(entity);
	}
	
    public HSSFWorkbook export(Long modelId) {  
//    	String[] excelHeader = { "Sno", "Name", "Age"};    
        HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("sheet1");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
//        List<Map<String, Object>> excelHeader = this.getExt202ModelColumns(modelId);
//        for (int i = 0; i < excelHeader.size(); i++) {    
//            HSSFCell cell = row.createCell(i);    
//            cell.setCellValue(excelHeader.get(i).get("DISPLAY_NAME").toString());    
//            cell.setCellStyle(style);    
//            sheet.autoSizeColumn(i);    
//        }    
    
        return wb;    
    }   
    
	public void importFromExcel(Long extProjectId,String progarmVehicleId, String fileName, InputStream is) {
		try {
			List<Ext301ItemEntity> ext301ItemEntityList = new ArrayList<Ext301ItemEntity>();
			Map<String,String> ext301ItemEntityMap = new HashMap<String,String>();
			Workbook wb = POIUtils.createWorkbook(fileName, is);
			Sheet sheet = wb.getSheetAt(0);
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum+1);
				if (!POIUtils.isEmpty(row)) {
					String partNumber = POIUtils.getStringCellValue(row.getCell(1));//零件编号
					if(StringUtils.isEmpty(partNumber)){
						throw new RuntimeException("第"+(rowNum+2)+"行"+"零件编码不能为空！");
					}
					try {
						BigDecimal bd = new BigDecimal(partNumber);  
						partNumber = bd.toPlainString();
					} catch (Exception e) {
						//如果不是数据也没有错误
						e.printStackTrace();
					}
					Ext301ItemEntity ext301ItemEntity = null;
					Ext301ItemEntity entity = getExt301ItemEntity(extProjectId,partNumber);
					if(entity != null){
						ext301ItemEntity = entity;
					}else{
						ext301ItemEntity = new Ext301ItemEntity();
					}
					
					
					String partName = POIUtils.getStringCellValue(row.getCell(2));//零件名称
					String kcrp = POIUtils.getStringCellValue(row.getCell(3));//IS_KCRP
					String obsName = POIUtils.getStringCellValue(row.getCell(4));//负责主体OBS_ID
					
					List<Map<String, Object>> obsList = pmObsService.getObsList(obsName,progarmVehicleId);
					if(obsList != null && obsList.size() > 0){
						ext301ItemEntity.setObsId(obsList.get(0).get("ID").toString());
					}else{
						throw new RuntimeException("第"+(rowNum+2)+"行"+"负责主体不存在！");
					}
					String owner = POIUtils.getStringCellValue(row.getCell(5));//责任工程师OWNER 
					List<UserEntity> userList = gtUserManager.getUserList(owner);
					if(userList != null &&  userList.size() > 0){
						ext301ItemEntity.setOwner(userList.get(0).getId());
					}else{
						throw new RuntimeException("第"+(rowNum+2)+"行"+"责任工程师不存在！");
					}
					String sourcingType = POIUtils.getStringCellValue(row.getCell(6));//采购类型
					String severityLevel = POIUtils.getStringCellValue(row.getCell(7));//重要度
					ext301ItemEntity.setItemNo(partNumber);
					ext301ItemEntity.setItemName(partName);
					if(StringUtils.isNotEmpty(kcrp)){
						if(kcrp.equals("是")){
							ext301ItemEntity.setIsKcrp("Y");
						}else{
							ext301ItemEntity.setIsKcrp("N");
						}
					}
					ext301ItemEntity.setExtProjectId(extProjectId);
					ext301ItemEntity.setSourcingType(sourcingType);
					ext301ItemEntity.setSeverityLevel(severityLevel);
					ext301ItemEntityList.add(ext301ItemEntity);
					ext301ItemEntityMap.put(ext301ItemEntity.getItemNo(), ext301ItemEntity.getItemNo());
//					ext301ItemDAO.save(ext301ItemEntity);
				}
				if(ext301ItemEntityMap.size() < ext301ItemEntityList.size()){
					throw new RuntimeException("零件号不能重复！");
				}
				this.saveExt301ItemEntityList(ext301ItemEntityList);
			}
		
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	public Ext301ItemEntity getExt301ItemEntity(Long extProjectId,String itemNo){
		List<Ext301ItemEntity> ext301ItemEntityList = ext301ItemDAO.find("from Ext301ItemEntity where extProjectId = ? and itemNo = ?", extProjectId,itemNo);
		if(ext301ItemEntityList == null || ext301ItemEntityList.size() < 1){
			return null;
		}
		return ext301ItemEntityList.get(0);
	}
	
	public void saveExt301ItemEntityList(List<Ext301ItemEntity> ext301ItemEntityList){
		for(Ext301ItemEntity ext301ItemEntity : ext301ItemEntityList){
			ext301ItemDAO.save(ext301ItemEntity);
		}
	}
	
	public Long getPartProjectId(String programVehicleId){
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(" select * from PM_EXT301  where PROGRAM_VEHICLE_ID = ? ",programVehicleId);
			return ((BigDecimal)map.get("ID")).longValue(); 
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public List<Map<String, Object>> getExt301GridTitle(Long parentId){
		String sql = " select * from V_EXT301_GRID_TITLE ";
		if(parentId == null){
			sql += " where PARENT_ID is null order by SEQ_NO ASC ";
		}else{
			sql += " where PARENT_ID = "+parentId+" order by SEQ_NO ASC ";
		}
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String,Object>> getExt301ItemList(String programVehicleId,String status){
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select * from v_ext301_item_cp_list where program_vehicle_id=? ";
		paramList.add(programVehicleId);
		if(StringUtils.isNotEmpty(status)){
			sql += " and STATUS_CODE=? ";
			paramList.add(status);
		}
		sql += " order by item_id,seq_no ";
		List<Map<String, Object>> itemCpList = jdbcTemplate.queryForList(sql,paramList.toArray());
		String lastItemId = null;
		Map<String,Object> itemMap = null;
		for(Map<String,Object> itemCp : itemCpList){
			String itemId = ObjectConverter.convert2String(itemCp.get("ITEM_ID"));
			if(!itemId.equals(lastItemId)){
				//添加零件信息
				lastItemId = itemId;
				itemMap = new HashMap<String,Object>();
				itemList.add(itemMap);
				itemMap.put("ITEM_ID", itemId);
				itemMap.put("ITEM_NO", ObjectConverter.convert2String(itemCp.get("ITEM_NO")));
				itemMap.put("ITEM_NAME", ObjectConverter.convert2String(itemCp.get("ITEM_NAME")));
				itemMap.put("OBS_NAME", ObjectConverter.convert2String(itemCp.get("OBS_NAME")));
				itemMap.put("ITEM_OWNER", ObjectConverter.convert2String(itemCp.get("ITEM_OWNER")));
				itemMap.put("SOURCING_TYPE", ObjectConverter.convert2String(itemCp.get("SOURCING_TYPE")));
				itemMap.put("SEVERITY_LEVEL", ObjectConverter.convert2String(itemCp.get("SEVERITY_LEVEL")));
				itemMap.put("IS_KCRP", ObjectConverter.convert2String(itemCp.get("IS_KCRP")));
				itemMap.put("PROGRESS", ObjectConverter.convert2String(itemCp.get("PROGRESS")));
				itemMap.put("PROGRESS_STATUS", ObjectConverter.convert2String(itemCp.get("PROGRESS_STATUS")));
			}
			//添加节点
			String taskCode = ObjectConverter.convert2String(itemCp.get("CODE"));
			String planFinishDate = ObjectConverter.convertDate2String(itemCp.get("PLANNED_FINISH_DATE"),"yyyy-MM-dd");
			String actualFinishDate = ObjectConverter.convertDate2String(itemCp.get("ACTUAL_FINISH_DATE"),"yyyy-MM-dd");
			itemMap.put(taskCode+"-PF", planFinishDate);
			itemMap.put(taskCode+"-AF", actualFinishDate);
			
		}
		
		return itemList;
	}
	
	public List<Map<String, Object>> getVehicleList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_PROGRAM_VEHICLE  ");
        return list;
    }
	
	public List<Map<String,Object>> getPartCheckPoolList(String programVehicleId,String userId,String obsName){
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		
		String sql=" SELECT * FROM V_EXT301_ITEM_CP_LIST WHERE ITEM_ID IN (SELECT T1.ID ITEM_ID FROM V_EXT301_SCHEDULE_POOL T1 WHERE T1.STEP_CODE = 'STEP_2' ";
		sql += " AND T1.PROGRAM_VEHICLE_ID = ? AND (FM=? OR ? ";
		sql += " IN (SELECT USER_ID FROM V_WORK_GROUP WHERE MODULE_CODE = 'EXT301' AND INSTANCE_ID=T1.PROGRAM_VEHICLE_ID))) ";
		if(StringUtils.isNotEmpty(obsName)){
			sql += " AND OBS_NAME LIKE '%"+obsName+"%'  ";
		}
		sql += " order by item_id  ";
		List<Map<String, Object>> itemCpList = jdbcTemplate.queryForList(sql,programVehicleId,userId,userId);
		String lastItemId = null;
		Map<String,Object> itemMap = null;
		for(Map<String,Object>itemCp : itemCpList){
			String itemId = ObjectConverter.convert2String(itemCp.get("ITEM_ID"));
			if(!itemId.equals(lastItemId)){
				//添加零件信息
				lastItemId = itemId;
				itemMap = new HashMap<String,Object>();
				itemList.add(itemMap);
				itemMap.put("ITEM_ID", itemId);
				itemMap.put("ITEM_NO", ObjectConverter.convert2String(itemCp.get("ITEM_NO")));
				itemMap.put("ITEM_NAME", ObjectConverter.convert2String(itemCp.get("ITEM_NAME")));
				itemMap.put("OBS_NAME", ObjectConverter.convert2String(itemCp.get("OBS_NAME")));
				itemMap.put("ITEM_OWNER", ObjectConverter.convert2String(itemCp.get("ITEM_OWNER")));
				itemMap.put("SOURCING_TYPE", ObjectConverter.convert2String(itemCp.get("SOURCING_TYPE")));
				itemMap.put("SEVERITY_LEVEL", ObjectConverter.convert2String(itemCp.get("SEVERITY_LEVEL")));
				itemMap.put("IS_KCRP", ObjectConverter.convert2String(itemCp.get("IS_KCRP")));
				itemMap.put("PROGRESS", ObjectConverter.convert2String(itemCp.get("PROGRESS")));
				itemMap.put("PROGRESS_STATUS", ObjectConverter.convert2String(itemCp.get("PROGRESS_STATUS")));
			}
			//添加节点
			String taskCode = ObjectConverter.convert2String(itemCp.get("CODE"));
			String planFinishDate = ObjectConverter.convertDate2String(itemCp.get("PLANNED_FINISH_DATE"),"yyyy-MM-dd");
			String actualFinishDate = ObjectConverter.convertDate2String(itemCp.get("ACTUAL_FINISH_DATE"),"yyyy-MM-dd");
			itemMap.put(taskCode+"-PF", planFinishDate);
			itemMap.put(taskCode+"-AF", actualFinishDate);
			
		}
		
		return itemList;
	}
	
	public List<Map<String,Object>> getPartConfirmPoolList(String programVehicleId,String userId,String obsName){
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		
		String sql=" SELECT * FROM V_EXT301_ITEM_CP_LIST WHERE ITEM_ID IN (SELECT T1.ID ITEM_ID FROM V_EXT301_SCHEDULE_POOL T1 WHERE T1.STEP_CODE = 'STEP_3' ";
		sql += " AND T1.PROGRAM_VEHICLE_ID = ? AND (FM=? OR ? ";
		sql += " IN (SELECT USER_ID FROM V_WORK_GROUP WHERE MODULE_CODE = 'EXT301' AND INSTANCE_ID=T1.PROGRAM_VEHICLE_ID)))  ";
		if(StringUtils.isNotEmpty(obsName)){
			sql += " AND OBS_NAME LIKE '%"+obsName+"%'  ";
		}
		sql += " order by item_id  ";
		List<Map<String, Object>> itemCpList = jdbcTemplate.queryForList(sql,programVehicleId,userId,userId);
		String lastItemId = null;
		Map<String,Object> itemMap = null;
		for(Map<String,Object>itemCp : itemCpList){
			String itemId = ObjectConverter.convert2String(itemCp.get("ITEM_ID"));
			if(!itemId.equals(lastItemId)){
				//添加零件信息
				lastItemId = itemId;
				itemMap = new HashMap<String,Object>();
				itemList.add(itemMap);
				itemMap.put("ITEM_ID", itemId);
				itemMap.put("ITEM_NO", ObjectConverter.convert2String(itemCp.get("ITEM_NO")));
				itemMap.put("ITEM_NAME", ObjectConverter.convert2String(itemCp.get("ITEM_NAME")));
				itemMap.put("OBS_NAME", ObjectConverter.convert2String(itemCp.get("OBS_NAME")));
				itemMap.put("ITEM_OWNER", ObjectConverter.convert2String(itemCp.get("ITEM_OWNER")));
				itemMap.put("SOURCING_TYPE", ObjectConverter.convert2String(itemCp.get("SOURCING_TYPE")));
				itemMap.put("SEVERITY_LEVEL", ObjectConverter.convert2String(itemCp.get("SEVERITY_LEVEL")));
				itemMap.put("IS_KCRP", ObjectConverter.convert2String(itemCp.get("IS_KCRP")));
				itemMap.put("PROGRESS", ObjectConverter.convert2String(itemCp.get("PROGRESS")));
				itemMap.put("PROGRESS_STATUS", ObjectConverter.convert2String(itemCp.get("PROGRESS_STATUS")));
			}
			//添加节点
			String taskCode = ObjectConverter.convert2String(itemCp.get("CODE"));
			String planFinishDate = ObjectConverter.convertDate2String(itemCp.get("PLANNED_FINISH_DATE"),"yyyy-MM-dd");
			String actualFinishDate = ObjectConverter.convertDate2String(itemCp.get("ACTUAL_FINISH_DATE"),"yyyy-MM-dd");
			itemMap.put(taskCode+"-PF", planFinishDate);
			itemMap.put(taskCode+"-AF", actualFinishDate);
			
		}
		
		return itemList;
	}
	
	public List<Map<String,Object>> getPartExcutePoolList(String programVehicleId,String userId,String code){
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		
		String sql="SELECT * FROM V_EXT301_ACTIVE_TASK_BY_CP T1 WHERE T1.PROGRAM_VEHICLE_ID=? AND T1.CHECKPOINT_CODE=? AND ( T1.TASK_OWNER=? OR ? IN (	SELECT USER_ID FROM V_WORK_GROUP WHERE MODULE_CODE= 'EXT301' AND INSTANCE_ID=T1.PROGRAM_VEHICLE_ID AND OWNER = T1.TASK_OWNER) ) ";
		List<Map<String, Object>> itemCpList = jdbcTemplate.queryForList(sql,programVehicleId,code,userId,userId);
		for(Map<String,Object>itemCp : itemCpList){
			Map<String,Object> itemMap = new HashMap<String,Object>();
			itemList.add(itemMap);
			itemMap.put("ITEM_ID", ObjectConverter.convert2String(itemCp.get("ITEM_ID")));
			itemMap.put("ITEM_NO", ObjectConverter.convert2String(itemCp.get("ITEM_NO")));
			itemMap.put("ITEM_NAME", ObjectConverter.convert2String(itemCp.get("ITEM_NAME")));
			itemMap.put("OBS_NAME", ObjectConverter.convert2String(itemCp.get("OBS_NAME")));
			itemMap.put("ITEM_OWNER", ObjectConverter.convert2String(itemCp.get("ITEM_OWNER")));
			itemMap.put("SOURCING_TYPE", ObjectConverter.convert2String(itemCp.get("SOURCING_TYPE")));
			itemMap.put("SEVERITY_LEVEL", ObjectConverter.convert2String(itemCp.get("SEVERITY_LEVEL")));
			itemMap.put("IS_KCRP", ObjectConverter.convert2String(itemCp.get("IS_KCRP")));
			itemMap.put("PROGRESS", ObjectConverter.convert2String(itemCp.get("PROGRESS")));
			itemMap.put("PROGRESS_STATUS", ObjectConverter.convert2String(itemCp.get("PROGRESS_STATUS")));
			//添加节点
//			String taskCode = ObjectConverter.convert2String(itemCp.get("CODE"));
			String planFinishDate = ObjectConverter.convertDate2String(itemCp.get("PLANNED_FINISH_DATE"),"yyyy/MM/dd");
			String actualFinishDate = ObjectConverter.convertDate2String(itemCp.get("ACTUAL_FINISH_DATE"),"yyyy/MM/dd");
			itemMap.put("planFinishDate", planFinishDate);
			itemMap.put("actualFinishDate", actualFinishDate);
			itemMap.put("taskId", ObjectConverter.convert2String(itemCp.get("TASK_ID")));
			
		}
		
		return itemList;
	}
	
	public List<Map<String, Object>> getPartExcuteHeader(String programVehicleId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT T2.CODE, T2.TITLE FROM PM_EXT301 T1, PM_EXT301_CHECKPOINT T2 WHERE T1.ID = T2.EXT_PROJECT_ID AND T1.PROGRAM_VEHICLE_ID = ? ORDER BY T2.SEQ_NO ",programVehicleId);
        return list;
    }
	
	public  void importPartList(File file,Long extProjectId,String progarmVehicleId){
		try {
			InputStream is = new FileInputStream(file);
			Workbook wb = POIUtils.createWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
			List<Ext301ItemEntity> ext301ItemEntityList = new ArrayList<Ext301ItemEntity>();
			Map<String,String> ext301ItemEntityMap = new HashMap<String,String>();
			
			List<Map<String,Object>> taskList = new ArrayList<Map<String,Object>>();
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum+4);
				if (!POIUtils.isEmpty(row)) {
					String partNumber = POIUtils.getStringCellValue(row.getCell(0));//零件编号
					if(StringUtils.isEmpty(partNumber)){
						throw new RuntimeException("第"+(rowNum+4)+"行"+"零件编码不能为空！");
					}
					try {
						BigDecimal bd = new BigDecimal(partNumber);  
						partNumber = bd.toPlainString();
					} catch (Exception e) {
						continue;
					}
					Ext301ItemEntity ext301ItemEntity = null;
					Ext301ItemEntity entity = getExt301ItemEntity(extProjectId,partNumber);
					if(entity != null){
						ext301ItemEntity = entity;
					}else{
						ext301ItemEntity = new Ext301ItemEntity();
					}
					
					
					String partName = POIUtils.getStringCellValue(row.getCell(1));//零件名称
					String kcrp = POIUtils.getStringCellValue(row.getCell(2));//IS_KCRP
					String obsName = POIUtils.getStringCellValue(row.getCell(3));//负责主体OBS_ID
					
					List<Map<String, Object>> obsList = pmObsService.getObsList(obsName,progarmVehicleId);
					if(obsList != null && obsList.size() > 0){
						ext301ItemEntity.setObsId(obsList.get(0).get("ID").toString());
					}else{
						throw new RuntimeException("第"+(rowNum+4)+"行"+"负责主体不存在！");
					}
					String owner = POIUtils.getStringCellValue(row.getCell(4));//责任工程师OWNER 
					List<UserEntity> userList = gtUserManager.getUserList(owner);
					if(userList != null &&  userList.size() > 0){
						ext301ItemEntity.setOwner(userList.get(0).getId());
					}else{
						throw new RuntimeException("第"+(rowNum+4)+"行"+"责任工程师不存在！");
					}
					String progress = POIUtils.getStringCellValue(row.getCell(5));//进度 
//					String sourcingType = POIUtils.getStringCellValue(row.getCell(6));//采购类型
//					String severityLevel = POIUtils.getStringCellValue(row.getCell(7));//重要度
					ext301ItemEntity.setItemNo(partNumber);
					ext301ItemEntity.setItemName(partName);
					if(StringUtils.isNotEmpty(kcrp)){
						if(kcrp.equals("是") || kcrp.equals("Y")){
							ext301ItemEntity.setIsKcrp("Y");
						}else{
							ext301ItemEntity.setIsKcrp("N");
						}
					}
					if(StringUtils.isNotEmpty(progress)){
						ext301ItemEntity.setProgress(new BigDecimal(progress));
					}
					
					ext301ItemEntity.setExtProjectId(extProjectId);
//					ext301ItemEntity.setSourcingType(sourcingType);
//					ext301ItemEntity.setSeverityLevel(severityLevel);
					ext301ItemEntityList.add(ext301ItemEntity);
					ext301ItemEntityMap.put(ext301ItemEntity.getItemNo(), ext301ItemEntity.getItemNo());
					for (int i = 0; i < row.getLastCellNum(); i++) { 
						if(i > 5){
							Row titleRow = sheet.getRow(2);
							String checkpointTitle =  POIUtils.getStringCellValue(titleRow.getCell(i));
							Date plannedFinishDate =  POIUtils.getDateCellValue(row.getCell(i));
							Date actualFinishDate =  POIUtils.getDateCellValue(row.getCell(i+1));
							
							Map<String,Object> taskMap = new HashMap<String,Object>();
							taskMap.put("itemNo", ext301ItemEntity.getItemNo());
							taskMap.put("extProjectId", extProjectId);
							taskMap.put("checkpointTitle", checkpointTitle);
							taskMap.put("plannedFinishDate", plannedFinishDate);
							taskMap.put("actualFinishDate", actualFinishDate);
							taskList.add(taskMap);
//							System.out.println("rownum==="+rowNum+" i==="+i+"value ==="+columnValue);
						}
						i++;
					}
				}
				
			}
			if(ext301ItemEntityMap.size() < ext301ItemEntityList.size()){
				throw new RuntimeException("零件号不能重复！");
			}
			this.saveExt301ItemEntityList(ext301ItemEntityList);
			this.saveTaskList(taskList);
			//			this.batchExecute(list);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		
	}
	
	public void saveTaskList(List<Map<String,Object>> taskList){
		for(Map<String,Object> map : taskList){
			Ext301ItemEntity item =  getExt301ItemEntity((Long)map.get("extProjectId"),map.get("itemNo").toString());
			Long itemId = item.getId();
			Long extProjectId = item.getExtProjectId();
			Map<String, Object> checkpiontMap = getCheckpoint(item.getExtProjectId().toString(),map.get("checkpointTitle").toString());
			Long checkpointId = ((BigDecimal)checkpiontMap.get("ID")).longValue();
			List<Ext301TaskEntity> list = ext301TaskDAO.find(" from Ext301TaskEntity where extProjectId=? and itemId = ? and checkpointId = ? ", extProjectId,itemId,checkpointId);
			if(list != null && list.size() > 0){
				Ext301TaskEntity ext301TaskEntity = list.get(0);
				ext301TaskEntity.setPlannedFinishDate((Date)map.get("plannedFinishDate"));
				ext301TaskEntity.setActualFinishDate((Date)map.get("actualFinishDate"));
				ext301TaskDAO.save(ext301TaskEntity);
			}
		}
	}
	
	public Map<String, Object> getCheckpoint(String extProjectId,String title) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_EXT301_CHECKPOINT t where EXT_PROJECT_ID = ? and title = ?",extProjectId,title);
        return list.get(0);
    }
	
	public void uploadPartControllReport(String programVehicleId,String documentName){
		InputStream in = this.getClass().getResourceAsStream("/report/partControlRpt.brt");
		ReportTemplet template = new ReportTemplet(in);
		try {
			in.close();
			FileOutputStream out;
			Map params = new HashMap();
			List<Parameter> paramList = template.getParameters();
			for (Parameter param : paramList) {
				params.put(param.getName(), programVehicleId); 
			}
			Map vars = new HashMap();
			List<Variable> varList = template.getVariables();
			for (Variable var : varList) {
				vars.put(var.getName(), "");
			}
			
			ReportManager manager = new ReportManager(template, params, vars);
			
			manager.setConnection(jdbcTemplate.getDataSource().getConnection());
			
			
			ReportBean reportBean = null;
			
			reportBean = manager.calc();
			File file = FileUtils.getTmpFile(documentName);
			out = new FileOutputStream(file);
			reportBean.toExcel(out);
//			ByteArrayInputStream input = FileUtils.parse(out);
			documentService.saveGTDocumentIndex(null, "SOURCE_TYPE_PART_CONTROLL", programVehicleId, null, documentName, null, file, null);
			out.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		new Ext301Service().importPartList("D:\\work\\广汽研究院\\03开发实施\\零部件开发管理\\partControlRpt.xls",173L,"7651CB101F514CABBAC1F1E127127F52");
	}
}