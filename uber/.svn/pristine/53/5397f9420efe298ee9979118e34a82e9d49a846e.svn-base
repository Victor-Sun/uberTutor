package com.gnomon.pdms.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.POIUtils;

@Service
@Transactional
public class Ext201_1ItemService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getExt202ModelList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT202_MODEL ");
        return list;
    }
	
	public List<Map<String, Object>> getRelationTaskList(String vehicleId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_PM_TASK_LEAF where PROGRAM_VEHICLE_ID=? ",vehicleId);
        return list;
    }
	
	public List<Map<String, Object>> getExt202ModelColumns(Long projectId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_EXT202_COLUMN T1,PM_EXT202_TABLE T2 WHERE T1.TABLE_ID = T2.ID AND T2.EXT_PROJECT_ID=? AND T1.DATA_TYPE <> 'CALCULATED' ORDER BY SEQ_NO",projectId);
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
	
    public HSSFWorkbook export(Long modelId) {  
//    	String[] excelHeader = { "Sno", "Name", "Age"};    
        HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("sheet1");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        List<Map<String, Object>> excelHeader = this.getExt202ModelColumns(modelId);
        for (int i = 0; i < excelHeader.size(); i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader.get(i).get("DISPLAY_NAME").toString());    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(i);    
        }    
    
        return wb;    
    }   
    
	public void importFromExcel(Long projectId, String fileName, InputStream is) {
		try {
			Workbook wb = POIUtils.createWorkbook(fileName, is);
			Sheet sheet = wb.getSheetAt(0);
			List<String> list = new ArrayList<String>();
			Map<String, Object> ext202Table = this.getExt202Table(projectId);
			List<Map<String, Object>> excelHeader = this.getExt202ModelColumns(projectId);
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum+1);
				String sql = "";
				String columnSql = "insert into "+ext202Table.get("TABLE_NAME")+" (";
				String valueSql = " values ( ";
				if (!POIUtils.isEmpty(row)) {
					 for (int i = 0; i < excelHeader.size(); i++) {  
						 String columnName = excelHeader.get(i).get("COLUMN_NAME").toString();
						 String dataType = excelHeader.get(i).get("DATA_TYPE").toString();
						 String columnValue = null;
						 if("DATE".equals(dataType)){
							 columnValue = "to_date('"+DateUtils.formate(POIUtils.getDateCellValue(row.getCell(i)))+"','yyyy-mm-dd')";
						 }else{
							 columnValue = POIUtils.getStringCellValue(row.getCell(i));
						 }
						 
						 columnSql += columnName+",";
						 if("DATE".equals(dataType)){
							 valueSql += ""+columnValue+",";
						 }else{
							 valueSql += "'"+columnValue+"',";
						 }
						
					 }
					 columnSql = columnSql.substring(0, columnSql.length()-1);
					 columnSql += ")";
					 valueSql =  valueSql.substring(0, valueSql.length()-1);
					 valueSql += ")";
					 sql = columnSql + valueSql;
//					 System.out.println(sql);
					 list.add(sql);
				}
			}
		
			this.batchExecute(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public void batchExecute(List<String> sqlList){
		for(String sql :sqlList){
			jdbcTemplate.execute(sql);
		}
	}

}
