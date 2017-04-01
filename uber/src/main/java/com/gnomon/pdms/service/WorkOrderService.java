package com.gnomon.pdms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
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
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.WorkOrderDAO;
import com.gnomon.pdms.entity.WorkOrderEntity;
import com.gnomon.pdms.procedure.PkgPmWorkOrderDBProcedureServcie;

@Service
@Transactional
public class WorkOrderService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private WorkOrderDAO workOrderDAO;
	
	@Autowired
	private PkgPmWorkOrderDBProcedureServcie pkgPmWorkOrderDBProcedureServcie;
	
	public WorkOrderEntity getWorkOrder(Long id){
		return workOrderDAO.get(id);
	}
	
	public Map<String, Object> getWorkOrderForm(Long id){
		return jdbcTemplate.queryForMap("SELECT * FROM PM_WORK_ORDER where id =?",id);
	}
	
	public GTPage<Map<String, Object>> getWorkOrderDraftList(String userId,int pageNo,int pageSize) {
		GTPage<Map<String, Object>> list = jdbcTemplate.queryPagination("SELECT * FROM V_PM_WORKORDER_DRAFT where CREATE_BY=? ", pageNo, pageSize, userId);
        return list;
    }
	
	public GTPage<Map<String, Object>> getInBoundWorkOrderList(int pageNo,int pageSize) {
		GTPage<Map<String, Object>> list = jdbcTemplate.queryPagination("SELECT * FROM V_PM_INBOUND_WORKORDER ", pageNo, pageSize);
        return list;
    }
	
	public GTPage<Map<String, Object>> getOutBoundWorkOrderList(int pageNo,int pageSize) {
		GTPage<Map<String, Object>> list = jdbcTemplate.queryPagination("SELECT * FROM V_PM_OUTBOUND_WORKORDER ", pageNo, pageSize);
        return list;
    }
	
	/**
	 * 部门空间-工作联系单列表
	 */
	public GTPage<Map<String, Object>> getDeptWorkOrderList(
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_DEPT_WORK_ORDER_TASK");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_DEPT_WORKORDER(?, WORK_ORDER_ID) = 1");
		params.add(loginUser);
		sql.append(" AND STATUS_CODE <> ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTitle"))) {
				sql.append(" AND UPPER(TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTitle") + "%");
			}
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchIn"))) {
				subParams.add(PDMSConstants.WORKORDER_TYPE_INBOUND);
			}
			if ("true".equals(searchModel.get("searchOut"))) {
				subParams.add(PDMSConstants.WORKORDER_TYPE_OUTBOUND);
			}
			if (subParams.size() > 0) {
				sql.append(" AND WO_TYPE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND WO_TYPE IS NULL");
			}
			
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			
			if (PDMSCommon.isNotNull(searchModel.get("searchProgramName"))) {
				sql.append(" AND UPPER(PROGRAM_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchProgramName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchSourceDept"))) {
				sql.append(" AND UPPER(SOURCE_DEPT) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchSourceDept") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchTargetDept"))) {
				sql.append(" AND UPPER(TARGET_DEPT) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTargetDept") + "%");
			}
		}
		sql.append(" ORDER BY CREATE_DATE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	public List<Map<String, Object>> getWorkOrderTypeList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_WORK_ORDER_TYPE ");
        return list;
    }
	
	public List<Map<String, Object>> getWorkOrderWorkTypeList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_WORK_ORDER_WORK_TYPE ");
        return list;
    }
	
	public Map<String, Object> getWorkOrderWorkType(String code) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_WORK_ORDER_WORK_TYPE where code=? ",code);
        return list.get(0);
    }
	
	public List<Map<String, Object>> getWorkOrderWorkNoteList(Long workOrderId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_WORK_ORDER_PROCESS_LOG WHERE WORK_ORDER_ID = ? ORDER BY STEP_ID Desc ",workOrderId);
        return list;
    }
	
	public List<Map<String, Object>> getUserList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT USERNAME,ID FROM SYS_USER where IS_DISABLED = 'N' ");
        return list;
    }
	
	public  void importWorkOrder(File file){
		try {
			InputStream is = new FileInputStream(file);
			Workbook wb = POIUtils.createWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
//			List<String> list = new ArrayList<String>();
//			Map<String, Object> ext202Table = this.getExt202Table(projectId);
//			List<Map<String, Object>> excelHeader = this.getExt202ModelColumns(projectId);
			Row row1 = sheet.getRow(2);
			String sourceDept = POIUtils.getStringCellValue(row1.getCell(4));
			String targetDept = POIUtils.getStringCellValue(row1.getCell(6));
			Row row2 = sheet.getRow(3);
			String programName = POIUtils.getStringCellValue(row2.getCell(4));
			String workType = POIUtils.getStringCellValue(row2.getCell(6)); //名称需转为code
			Row row3 = sheet.getRow(4);
			String title = POIUtils.getStringCellValue(row3.getCell(4));//工作名称
//			String createBy = POIUtils.getStringCellValue(row3.getCell(6));//编制人编制日期
//			String createDate = POIUtils.getStringCellValue(row3.getCell(6));
			Row row4 = sheet.getRow(5);
			String workDescription = POIUtils.getStringCellValue(row4.getCell(3));
			Row row8 = sheet.getRow(9);
			String dueDate = POIUtils.getStringCellValue(row8.getCell(3));
			String contactName = POIUtils.getStringCellValue(row8.getCell(6));
//			String contactPhone = POIUtils.getStringCellValue(row3.getCell(6));
			Row row9 = sheet.getRow(10);
			String reviewByString = POIUtils.getStringCellValue(row9.getCell(3));
//			String reqReviewDate = POIUtils.getStringCellValue(row9.getCell(6));
			String approveByString = POIUtils.getStringCellValue(row9.getCell(6));
			Row row11 = sheet.getRow(12);
			String responseDescription = POIUtils.getStringCellValue(row11.getCell(3));
			Row row14 = sheet.getRow(15);
			String responseCreateDate = POIUtils.getStringCellValue(row14.getCell(3));
			String responseContactName = POIUtils.getStringCellValue(row14.getCell(6));
//			String responseContactPhone = POIUtils.getStringCellValue(row14.getCell(6));
			Row row15 = sheet.getRow(16);
//			String responseReviewDate = POIUtils.getStringCellValue(row15.getCell(6));
			String responseReviewBy = POIUtils.getStringCellValue(row15.getCell(3));
			String responseApproveBy = POIUtils.getStringCellValue(row15.getCell(6));
//			String responseApproveDate = POIUtils.getStringCellValue(row3.getCell(6));
			Row row17 = sheet.getRow(18);
			String validateDescription = POIUtils.getStringCellValue(row17.getCell(3));
			Row row18 = sheet.getRow(19);
			String validateBy = POIUtils.getStringCellValue(row3.getCell(3));
			String validateDate = POIUtils.getStringCellValue(row3.getCell(6));
			
			WorkOrderEntity workOrderEntity = new WorkOrderEntity();
			workOrderEntity.setSourceDept(sourceDept);
			workOrderEntity.setTargetDept(targetDept);
			workOrderEntity.setProgramName(programName);
			try {
				workOrderEntity.setWorkType(((BigDecimal)getWorkOrderWorkType(workType).get("id")).longValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workOrderEntity.setTitle(title);
//			workOrderEntity.setCreateBy(createBy);
//			workOrderEntity.setCreateDate(createDate);
			workOrderEntity.setWorkDescription(workDescription);
//			workOrderEntity.setDueDate(dueDate);
			workOrderEntity.setContactName(responseContactName);
//			workOrderEntity.setContactPhone(responseContactPhone);
			workOrderEntity.setReviewByString(reviewByString);
			workOrderEntity.setApproveByString(approveByString);
			workOrderEntity.setResponseDescription(responseDescription);
//			workOrderEntity.setResponseCreateDate(responseCreateDate);
			workOrderEntity.setContactName(responseContactName);
//			workOrderEntity.setContactPhone(responseContactPhone);
//			workOrderEntity.setResponseReviewDate(responseReviewDate);
			workOrderEntity.setResponseReviewBy(responseReviewBy);
			workOrderEntity.setResponseApproveBy(responseApproveBy);
//			workOrderEntity.setResponseApproveDate(responseApproveDate);
			workOrderEntity.setValidateDescription(validateDescription);
			workOrderEntity.setValidateBy(validateBy);
//			workOrderEntity.setValidateDate(validateDate);
			workOrderEntity.setAction("SAVE");
//			System.out.println("sourceDept==="+sourceDept+" targetDept==="+targetDept+"programName ==="+programName);
//			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
//				Row row = sheet.getRow(rowNum+1);
//				for (int i = 0; i < 8; i++) { 
//					String columnValue = POIUtils.getStringCellValue(row.getCell(i));
//					
//					System.out.println("rownum==="+rowNum+" i==="+i+"value ==="+columnValue);
//				}
//				
//				String sql = "";
//				String valueSql = " values ( ";
//			}
		
			//			this.batchExecute(list);
			String createBy = SessionData.getLoginUserId();
			workOrderEntity.setCreateBy(createBy);
			workOrderEntity.setCreateDate(new Date());
			pkgPmWorkOrderDBProcedureServcie.updateInboundWorkOrder(workOrderEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	
	}
	
	public static void main(String[] args) {
//		importWorkOrder("D:\\work\\gamc\\工作联系单\\F015 工作联系单.xls");
	}

}
