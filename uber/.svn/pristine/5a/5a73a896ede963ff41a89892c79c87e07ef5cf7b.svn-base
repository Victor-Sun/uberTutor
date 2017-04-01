package com.gnomon.pdms.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.POIUtils;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.ImsPartDAO;
import com.gnomon.pdms.entity.ImsPartEntity;

@Service
@Transactional
public class ImsPartService {

	@Autowired
	private ImsPartDAO imsPartDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public GTPage<Map<String, Object>> getImsPartList(int pageNo, int pageSize) {
		
		String sql = "select * from IMS_PART ";
		return jdbcTemplate.queryPagination(sql , pageNo, pageSize);
	}
	
	public void save (ImsPartEntity entity){
		imsPartDAO.save(entity);
	}
	
	public void importFromExcel(String fileName, InputStream is) {
		try {
			Workbook wb = POIUtils.createWorkbook(fileName, is);
			Sheet sheet = wb.getSheetAt(0);
			List<String> list = new ArrayList<String>();
//			Map<String, Object> ext202Table = this.getExt202Table(projectId);
//			List<Map<String, Object>> excelHeader = this.getExt202ModelColumns(projectId);
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum+1);
				if (!POIUtils.isEmpty(row)) {
					String code = POIUtils.getStringCellValue(row.getCell(0));
					List<ImsPartEntity> partlist = imsPartDAO.findBy("CODE", code);
					if(partlist != null && partlist.size() > 0){
						continue;
					}
					String name = POIUtils.getStringCellValue(row.getCell(1));
					String createBy = POIUtils.getStringCellValue(row.getCell(2));
					Date createDate = POIUtils.getDateCellValue(row.getCell(3));
					String updateBy = POIUtils.getStringCellValue(row.getCell(4));
					Date updateDate = POIUtils.getDateCellValue(row.getCell(5));
					String deleteBy = POIUtils.getStringCellValue(row.getCell(6));
					Date deleteDate = POIUtils.getDateCellValue(row.getCell(7));
					String groupNo = POIUtils.getStringCellValue(row.getCell(8));
					String systemNo = POIUtils.getStringCellValue(row.getCell(9));
					String systemName = POIUtils.getStringCellValue(row.getCell(10));
					String groupName = POIUtils.getStringCellValue(row.getCell(11));
					ImsPartEntity imsPartEntity = new ImsPartEntity();
					imsPartEntity.setCode(code);
					imsPartEntity.setName(name);
					imsPartEntity.setCreateBy(createBy);
					imsPartEntity.setCreateDate(createDate);
					imsPartEntity.setUpdateBy(updateBy);
					imsPartEntity.setUpdateDate(updateDate);
					imsPartEntity.setDeleteBy(deleteBy);
					imsPartEntity.setDeleteDate(deleteDate);
					imsPartEntity.setGroupNo(groupNo);
					imsPartEntity.setSystemNo(systemNo);
					imsPartEntity.setSystemName(systemName);
					imsPartEntity.setGroupName(groupName);
					imsPartEntity.setId(PDMSCommon.generateUUID());
					imsPartDAO.save(imsPartEntity);
				}
			}
//			this.batchExecute(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
