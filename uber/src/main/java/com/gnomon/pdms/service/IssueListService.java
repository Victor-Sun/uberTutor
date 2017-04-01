package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.page.SqlBuilder;
import com.gnomon.common.system.dao.SysDepartmentDAO;
import com.gnomon.common.system.entity.SysDepartmentEntity;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.procedure.PkgPermissionDBProcedureServcie;

@Service
@Transactional
public class IssueListService {
	// 我的待办
	public final static String LIST_TYPE_MYTASK = "mytask";
	// 我的过任务
	public final static String LIST_TYPE_MY_PROCESS_TASK = "myprocesstask";
	// 我提出的问题
	public final static String LIST_TYPE_MYSUBMISSION = "mysubmission";
	// 我的草稿
	public final static String LIST_TYPE_MYDRAFT = "mydraft";
	// 我参与的问题
	public final static String LIST_TYPE_MYPARTICIPATIONA = "myparticipationA";
	// 知会给我的问题
	public final static String LIST_TYPE_MYPARTICIPATIONB = "myparticipationB";
	// 我关注的问题
	public final static String LIST_TYPE_MYFOCUS = "myfocus";
	// 管理员问题管理
	public final static String LIST_TYPE_ADMINISTRATORA = "administratorA";
	// 我挂牌的问题
	public final static String LIST_TYPE_ADMINISTRATORB = "administratorB";	
	// 问题查询
	public final static String LIST_TYPE_QUERY = "query";
	// 挂牌区
	public final static String LIST_TYPE_LISTINGAREA = "listingarea";
	// 新提出问题
	public final static String LIST_TYPE_NEWISSUELIST = "newIssueList";
	// 最近访问的问题
	public final static String LIST_TYPE_LASTACCESS = "lastaccess";
	// 热点问题
	public final static String LIST_TYPE_QMSDASHBOARDHOT = "qmsdashboardhot";
	// 部门空间-质量问题
	public final static String LIST_TYPE_DEPARTMENT = "department";
	//质量解析科Code
	public final static String DEPARMENT_QUALITY_ANALYSIS_SECTION_CODE = "990Q08";
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SysDepartmentDAO sysDepartmentDAO;
	
	@Autowired
	private PkgPermissionDBProcedureServcie pkgPermissionDBProcedureServcie;

	/**
	 * 问题列表查询
	 */
	public GTPage<Map<String, Object>> getIssueList(String listType,
			Map<String, String> searchModel, List<String> programIdList,
			int pageNo, int pageSize,String filter,String sort) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = this.getQuerysql(listType, searchModel, programIdList,paramList,filter,sort);
		// 查询
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize,
				paramList.toArray());
	}
	
	
	public String getQuerysql(String listType,Map<String, String> searchModel, List<String> programIdList,List<Object> paramList,String filter,String sort){
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 问题列表取得
		sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		
		// 我的待办
		if (LIST_TYPE_MYTASK.equals(listType)) {
			sql.append(" V_IMS_ISSUE_PROCESS");
//		// 我提交的问题
//		} else if () {
//			V_IMS_ISSUE_NOTLISTING
		// 我挂牌的问题、挂牌区
		} else if (LIST_TYPE_ADMINISTRATORB.equals(listType) ||
				LIST_TYPE_LISTINGAREA.equals(listType)) {
			sql.append(" V_IMS_ISSUE_LISTING");
		// 我关注的问题
		} else if (LIST_TYPE_MYFOCUS.equals(listType)) {
			sql.append(" V_IMS_ISSUE_MARK");
		// 我参与的问题
		} else if (LIST_TYPE_MYPARTICIPATIONA.equals(listType)) {
			sql.append(" V_IMS_PROCESS_MEMBER");
		// 知会给我的问题
		} else if (LIST_TYPE_MYPARTICIPATIONB.equals(listType)) {
			sql.append(" V_IMS_PARTICIPATION");
		// 最近访问的问题
		} else if (LIST_TYPE_LASTACCESS.equals(listType)) {
			sql.append(" V_ISSUE_LAST_ACCESS");
		//新提出问题
		} else if (LIST_TYPE_NEWISSUELIST.equals(listType)) {
			sql.append(" V_IMS_ISSUE_NEW");	
		// 我的草稿、我提交的问题、管理员问题管理、问题查询、热点问题、部门空间-质量问题
		} else {
			sql.append(" V_IMS_ISSUE");
		}

		sql.append(" WHERE");
		sql.append(" 1 = 1");
		
		// 我的待办
		if (LIST_TYPE_MYTASK.equals(listType)) {
			sql.append(" AND TASK_OWNER = ? AND CURRENT_STEP_ID !=  "+PDMSConstants.CURRENT_STEP_PROCESS_TRACE);
			paramList.add(loginUser);
		}// 我的过程问题待办
		else if (LIST_TYPE_MY_PROCESS_TASK.equals(listType)) {
			sql.append(" AND TASK_OWNER = ?  AND CURRENT_STEP_ID =  "+PDMSConstants.CURRENT_STEP_PROCESS_TRACE);
			paramList.add(loginUser);
		// 我的草稿
		}  else if (LIST_TYPE_MYDRAFT.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE = ?");
			sql.append(" AND CREATE_BY = ?");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_DRAFT);
			paramList.add(loginUser);
		// 我提交的问题
		} else if (LIST_TYPE_MYSUBMISSION.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?, ?)");
			sql.append(" AND SUBMIT_USER = ?");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
			paramList.add(loginUser);
		// 管理员问题管理
		} else if (LIST_TYPE_ADMINISTRATORA.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?, ?,?,?)");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_ARCHIVED);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CANCELED);
			sql.append(" AND PKG_PERMISSION.IS_QIMS_MANAGER(?) = 1");
			paramList.add(loginUser);
		// 我关注的问题
		} else if (LIST_TYPE_MYFOCUS.equals(listType)) {
			sql.append(" AND MARK_USER_ID = ?");
			paramList.add(loginUser);
		// 我参与的问题
		} else if (LIST_TYPE_MYPARTICIPATIONA.equals(listType)) {
			sql.append(" AND PARTICIPANT_USER_ID = ?");
			paramList.add(loginUser);
		// 知会给我的问题
		} else if (LIST_TYPE_MYPARTICIPATIONB.equals(listType)) {
			sql.append(" AND ROLE_CODE = ?");
			sql.append(" AND MEMBER_USERID = ?");
			sql.append(" AND  ISSUE_STATUS_CODE NOT IN (?,?) ");
			paramList.add(PDMSConstants.ROLE_CODE_INFO_USER);
			paramList.add(loginUser);
			paramList.add(PDMSConstants.ISSUE_STATUS_65);
			paramList.add(PDMSConstants.ISSUE_STATUS_66);
		// 最近访问的问题
		} else if (LIST_TYPE_LASTACCESS.equals(listType)) {
			sql.append(" AND USERID = ?");
			paramList.add(loginUser);
		// 我挂牌的问题
		} else if (LIST_TYPE_ADMINISTRATORB.equals(listType)) {
			sql.append(" AND LISTING_BY = ?");
			paramList.add(loginUser);
		// 挂牌区
		} else if (LIST_TYPE_LISTINGAREA.equals(listType)) {
			sql.append(" AND ISSUE_LIST_STATUS = ?");
			paramList.add(PDMSConstants.ISSUE_LIST_STATUS_1);
		// 问题查询
		} else if (LIST_TYPE_QUERY.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE <> ?");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_DRAFT);
//			sql.append(" AND PKG_IMS.CAN_VIEW_ISSUE(ID, ?) = 1");
			
			if(!isDeparmentQualityAnalysisSection()){
				sql.append(" AND ISSUE_SOURCE_ID <> '" + PDMSConstants.ISSUE_SOURCE_HEI + "' ");
			}
//			paramList.add(loginUser);
		// 热点问题
		} else if (LIST_TYPE_QMSDASHBOARDHOT.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?, ?)");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
			sql.append(" AND RISK_LEVEL_CODE IN(?, ?, ?)");
			paramList.add(PDMSConstants.RISK_LEVEL_1);
			paramList.add(PDMSConstants.RISK_LEVEL_2);
			paramList.add(PDMSConstants.RISK_LEVEL_3);
			sql.append(" AND ISSUE_STATUS_CODE IN(?, ?)");
			paramList.add(PDMSConstants.ISSUE_STATUS_61);
			paramList.add(PDMSConstants.ISSUE_STATUS_62);
			if(programIdList != null && programIdList.size() > 0){
				sql.append(" AND PROJECT_ID IN(");
				for (int i = 0; i < programIdList.size(); i++) {
					String programId  = programIdList.get(i);
					sql.append(" ?");
					paramList.add(programId);
					if(i < programIdList.size() - 1){
						sql.append(",");
					}else{
						sql.append(")");
					}
				}
			}
		// 部门空间-质量问题
		} else if (LIST_TYPE_DEPARTMENT.equals(listType)) {
			sql.append(" AND ISSUE_LIFECYCLE_CODE <> ?");
			paramList.add(PDMSConstants.ISSUE_LIFECYCLE_DRAFT);
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_DEPT_QM_ISSUE(?, ID) = 1");
			paramList.add(loginUser);
		}
		
		// 查询条件-进行中/已关闭/已归档
		if (PDMSCommon.isNotNull(searchModel.get("searchProcessing")) &&
				PDMSCommon.isNotNull(searchModel.get("searchClosed")) &&
				PDMSCommon.isNotNull(searchModel.get("searchArchived")) ) {
			if ("true".equals(searchModel.get("searchProcessing")) &&
					"true".equals(searchModel.get("searchClosed"))  &&
					"true".equals(searchModel.get("searchArchived"))) {
				sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?, ?, ?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_ARCHIVED);
			} else if ("true".equals(searchModel.get("searchProcessing")) &&
					 "true".equals(searchModel.get("searchClosed"))  &&
					! "true".equals(searchModel.get("searchArchived"))) {
				sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?,?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
			} else if ("true".equals(searchModel.get("searchProcessing")) &&
					! "true".equals(searchModel.get("searchClosed"))  &&
					! "true".equals(searchModel.get("searchArchived"))) {
				sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?, ?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
			} else if (! "true".equals(searchModel.get("searchProcessing")) &&
					"true".equals(searchModel.get("searchClosed"))  &&
					"true".equals(searchModel.get("searchArchived"))) {
				sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?,?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_ARCHIVED);
			} else if (! "true".equals(searchModel.get("searchProcessing")) &&
					!"true".equals(searchModel.get("searchClosed"))  &&
					"true".equals(searchModel.get("searchArchived"))) {
				sql.append(" AND ISSUE_LIFECYCLE_CODE IN(?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_ARCHIVED);
			} else {
				sql.append(" AND ISSUE_LIFECYCLE_CODE NOT IN(?, ?, ?,?)");
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_OPEN);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_PENDING);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_CLOSED);
				paramList.add(PDMSConstants.ISSUE_LIFECYCLE_ARCHIVED);
			}
		}
		// 查询条件-所属项目
		if (PDMSCommon.isNotNull(searchModel.get("searchProgramCode"))) {
			sql.append(" AND PROJECT_ID = ? ");
			paramList.add(searchModel.get("searchProgramCode"));
		}
		// 查询条件-所属子项目
		if (PDMSCommon.isNotNull(searchModel.get("searchSubProjectCode"))) {
			sql.append(" AND SUB_PROJECT_ID = ? ");
			paramList.add(searchModel.get("searchSubProjectCode"));
		}
		// 查询条件-责任部门
		if (PDMSCommon.isNotNull(searchModel.get("searchObsName"))) {
			sql.append(" AND DEPT_ID = ? ");
			paramList.add(searchModel.get("searchObsName"));
		}
		// 查询条件-样车编号
		if (PDMSCommon.isNotNull(searchModel.get("searchSampleNumber"))) {
			sql.append(" AND UPPER(SAMPLE_NUMBER) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchSampleNumber") + "%");
		}
		// 查询条件-故障零件编号
		if (PDMSCommon.isNotNull(searchModel.get("searchTroublePartCode"))) {
			sql.append(" AND ID IN (select ISSUE_ID from IMS_ISSUE_PART where UPPER(PART_NUMBER) LIKE UPPER(?)) ");
			paramList.add("%" + searchModel.get("searchTroublePartCode") + "%");
		}
		// 查询条件-故障零件名称
		if (PDMSCommon.isNotNull(searchModel.get("searchTroublePartName"))) {
			sql.append(" AND ID IN (select ISSUE_ID from IMS_ISSUE_PART where UPPER(PART_NAME) LIKE UPPER(?)) ");
			paramList.add("%" + searchModel.get("searchTroublePartName") + "%");
		}
		// 查询条件-当前任务名称
		if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
			sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchTaskName") + "%");
		}
		// 查询条件-当前任务负责人
		if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
			sql.append(" AND UPPER(TASK_OWNER_NAME) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchTaskOwner") + "%");
		}
		// 查询条件-问题编号
		if (PDMSCommon.isNotNull(searchModel.get("searchCode"))) {
			sql.append(" AND UPPER(CODE) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchCode") + "%");
		}
		// 查询条件-问题标题
		if (PDMSCommon.isNotNull(searchModel.get("searchTitleIn"))) {
			sql.append(" AND UPPER(TITLE) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchTitleIn") + "%");
		}
		// 查询条件-问题标题
		if (PDMSCommon.isNotNull(searchModel.get("searchTitle"))) {
			sql.append(" AND UPPER(TITLE) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchTitle") + "%");
		}
		// 查询条件-问题等级
		if (PDMSCommon.isNotNull(searchModel.get("searchIssueLevel"))) {
			sql.append(" AND ISSUE_LEVEL_CODE = ?");
			paramList.add(searchModel.get("searchIssueLevel"));
		}
		// 查询条件-风险等级
		if (PDMSCommon.isNotNull(searchModel.get("searchRiskLevelCode"))) {
			sql.append(" AND RISK_LEVEL_CODE = ?");
			paramList.add(searchModel.get("searchRiskLevelCode"));
		}
		// 查询条件-问题来源
		if (PDMSCommon.isNotNull(searchModel.get("searchIssueSourceId"))) {
			sql.append(" AND ISSUE_SOURCE_ID = ?");
			paramList.add(searchModel.get("searchIssueSourceId"));
		}
		// 查询条件-问题类型
		if (PDMSCommon.isNotNull(searchModel.get("searchIssueTypeId"))) {
			sql.append(" AND ISSUE_TYPE_ID = ?");//TODO
			paramList.add(searchModel.get("searchIssueTypeId"));
		}
		// 查询条件-试验类型
		if (PDMSCommon.isNotNull(searchModel.get("searchTestTypeId"))) {
			sql.append(" AND TEST_TYPE_ID = ?");//TODO
			paramList.add(searchModel.get("searchTestTypeId"));
		}
		// 查询条件-问题性质
		if (PDMSCommon.isNotNull(searchModel.get("searchIssueNatureId"))) {
			sql.append(" AND ISSUE_NATURE_ID = ?");//TODO
			paramList.add(searchModel.get("searchIssueNatureId"));
		}
		// 查询条件-问题状态
		if (PDMSCommon.isNotNull(searchModel.get("searchIssueStatus")) && !"[]".equals(searchModel.get("searchIssueStatus"))) {
			String searchIssueStatus = searchModel.get("searchIssueStatus");
			searchIssueStatus = searchIssueStatus.substring(1,searchIssueStatus.length()-1);
			searchIssueStatus = searchIssueStatus.replaceAll("\"", "'");
			sql.append(" AND ISSUE_STATUS_CODE in ( "+searchIssueStatus+" )");
//			paramList.add(searchIssueStatus);
		}
		
		// 查询条件-提出时间
		if (PDMSCommon.isNotNull(searchModel.get("searchCreateDateFrom"))) {
			sql.append(" >= TO_CHAR(CREATE_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchCreateDateFrom"))));
		}
		
		// 查询条件-提出时间
		if (PDMSCommon.isNotNull(searchModel.get("searchCreateDateTo"))) {
			sql.append(" <= TO_CHAR(CREATE_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchCreateDateTo"))));
		}
		
		// 查询条件-计划对策时间
		if (PDMSCommon.isNotNull(searchModel.get("searchExpectedDateFrom"))) {
			sql.append(" >= TO_CHAR(EXPECTED_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchExpectedDateFrom"))));
		}
		
		// 查询条件-计划对策时间
		if (PDMSCommon.isNotNull(searchModel.get("searchExpectedDateTo"))) {
			sql.append(" <= TO_CHAR(EXPECTED_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchExpectedDateTo"))));
		}
		
		// 查询条件-计划关闭时间
		if (PDMSCommon.isNotNull(searchModel.get("searchPlanClosedDateFrom"))) {
			sql.append(" >= TO_CHAR(PLAN_CLOSED_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchPlanClosedDateFrom"))));
		}
		
		// 查询条件-计划关闭时间
		if (PDMSCommon.isNotNull(searchModel.get("searchPlanClosedDateTo"))) {
			sql.append(" <= TO_CHAR(PLAN_CLOSED_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchPlanClosedDateTo"))));
		}

		// 查询条件-等待时间
		if (PDMSCommon.isNotNull(searchModel.get("searchDaysElapsed"))) {
			sql.append(" AND DAYS_ELAPSED = ?");
			paramList.add(searchModel.get("searchDaysElapsed"));
		}
		// 查询条件-挂牌时间
		if (PDMSCommon.isNotNull(searchModel.get("searchListingDate"))) {
			sql.append(" AND TO_CHAR(LISTING_DATE, 'YYYY-MM-DD') = ?");
			paramList.add(DateUtils.formate(DateUtils.strToDate(
					searchModel.get("searchListingDate"))));
		}
		
		// 查询条件-是否挂牌
		if (PDMSCommon.isNotNull(searchModel.get("isListing"))) {
			sql.append(" AND IS_MERGE = ?");
			paramList.add(searchModel.get("isListing"));
		}
		
		// 查询条件-问题提出人
		if (PDMSCommon.isNotNull(searchModel.get("searchSubmitUser"))) {
			sql.append(" AND UPPER(SUBMIT_USER_NAME) LIKE UPPER(?)");
			paramList.add("%" + searchModel.get("searchSubmitUser") + "%");
		}
		
		// 查询条件-是否合并
		if (PDMSCommon.isNotNull(searchModel.get("isMerge"))) {
			sql.append(" AND IS_LISTING = ?");
			paramList.add(searchModel.get("isMerge"));
		}
		
		sql.append(SqlBuilder.getFilterSql(filter));
		String defaultSort = " CREATE_DATE ";
		// 排序
		if (LIST_TYPE_MYTASK.equals(listType) || LIST_TYPE_MY_PROCESS_TASK.equals(listType)) {
			defaultSort = " TASK_CREATE_DATE ";
		}
		
		if (LIST_TYPE_LASTACCESS.equals(listType)) {
			defaultSort = "  LAST_ACCESS_DATE ";
		}
		sql.append(SqlBuilder.getSortSql(sort, defaultSort, "DESC"));
		
		// 排序
//		if (LIST_TYPE_MYTASK.equals(listType) || LIST_TYPE_MY_PROCESS_TASK.equals(listType)) {
//			sql.append(" ORDER BY TASK_CREATE_DATE DESC");
//		}
//		
//		if (LIST_TYPE_LASTACCESS.equals(listType)) {
//			sql.append(" ORDER BY LAST_ACCESS_DATE DESC");
//		}
		return sql.toString();
	}
	
	public boolean isDeparmentQualityAnalysisSection(){
		String userId = SessionData.getLoginUserId();
		Map<String, Object> isQimsManagerMap = pkgPermissionDBProcedureServcie.isQimsManager(userId );
		String isQimsManager = (String)isQimsManagerMap.get("isQimsManager");
		return "1".equals(isQimsManager);
	}
	
	public List<Map<String, Object>> getIssueList(String listType,
			Map<String, String> searchModel, List<String> programIdList) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = this.getQuerysql(listType, searchModel, programIdList,paramList,null,null);
		// 查询
		return jdbcTemplate.queryForList(sql,paramList.toArray());
	}
	
    public HSSFWorkbook exportExcel(String listType,Map<String, String> searchModel, List<String> programIdList) {  
    	List<Map<String, Object>> issueList = this.getIssueList(listType, searchModel, programIdList);
    	 // 第一步，创建一个webbook，对应一个Excel文件
    	HSSFWorkbook wb = new HSSFWorkbook();
    	// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
    	HSSFSheet sheet = wb.createSheet("sheet1");
    	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
    	HSSFRow row = sheet.createRow(0);
    	// 第四步，创建单元格，并设置值表头 设置表头居中
    	HSSFCellStyle style = wb.createCellStyle();
    	 HSSFCellStyle headerStyle = wb.createCellStyle();
    	
    	 
	    // 另一个字体样式   
	    HSSFFont columnHeadFont = wb.createFont();   
	    columnHeadFont.setFontName("宋体");   
	    columnHeadFont.setFontHeightInPoints((short) 10);   
	    columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	    // 列头的样式   
//	    HSSFCellStyle headerStyle = wb.createCellStyle();   
	    headerStyle.setFont(columnHeadFont);   
	    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
	    headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
	    headerStyle.setLocked(true);   
	    headerStyle.setWrapText(true);   
	    headerStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
	    headerStyle.setBorderLeft((short) 1);// 边框的大小   
	    headerStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
	    headerStyle.setBorderRight((short) 1);// 边框的大小   
	    headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
	    headerStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
	    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）   
	    headerStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);   
    	
    	   sheet.setColumnWidth(0, 3500);   
    	    sheet.setColumnWidth(1, 3500);   
    	    sheet.setColumnWidth(2, 3500);   
    	    sheet.setColumnWidth(3, 6500);   
    	    sheet.setColumnWidth(4, 6500);   
    	    sheet.setColumnWidth(5, 6500);   
    	    sheet.setColumnWidth(6, 6500);   
    	    sheet.setColumnWidth(7, 6500); 
    	    sheet.setColumnWidth(8, 6500);
    	    sheet.setColumnWidth(9, 6500);
    	    sheet.setColumnWidth(10, 6500);
    	    sheet.setColumnWidth(11, 6500);
    	    sheet.setColumnWidth(12, 6500);
    	    

    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCell cell = null;
        cell=row.createCell(0);cell.setCellValue("问题编号");cell.setCellStyle(headerStyle); 
        cell=row.createCell(1);cell.setCellValue("问题状态");cell.setCellStyle(headerStyle); 
        cell=row.createCell(2);cell.setCellValue("责任部门");cell.setCellStyle(headerStyle); 
        cell=row.createCell(3);cell.setCellValue("责任工程师");cell.setCellStyle(headerStyle); 
        cell=row.createCell(4);cell.setCellValue("问题等级");cell.setCellStyle(headerStyle); 
        cell=row.createCell(5);cell.setCellValue("问题标题");cell.setCellStyle(headerStyle); 
        cell=row.createCell(6);cell.setCellValue("问题描述");cell.setCellStyle(headerStyle); 
        cell=row.createCell(7);cell.setCellValue("问题提出人");cell.setCellStyle(headerStyle); 
        cell=row.createCell(8);cell.setCellValue("提出时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(9);cell.setCellValue("问题来源");cell.setCellStyle(headerStyle); 
        cell=row.createCell(10);cell.setCellValue("所属项目");cell.setCellStyle(headerStyle); 
        cell=row.createCell(11);cell.setCellValue("子项目");cell.setCellStyle(headerStyle); 
        cell=row.createCell(12);cell.setCellValue("样车阶段");cell.setCellStyle(headerStyle); 
        cell=row.createCell(13);cell.setCellValue("发生时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(14);cell.setCellValue("发生场地");cell.setCellStyle(headerStyle); 
        cell=row.createCell(15);cell.setCellValue("试验类型");cell.setCellStyle(headerStyle); 
        cell=row.createCell(16);cell.setCellValue("样车编号");cell.setCellStyle(headerStyle); 
        cell=row.createCell(17);cell.setCellValue("故障零件里程");cell.setCellStyle(headerStyle); 
        cell=row.createCell(18);cell.setCellValue("试验进展");cell.setCellStyle(headerStyle); 
        cell=row.createCell(19);cell.setCellValue("处置措施");cell.setCellStyle(headerStyle); 
        cell=row.createCell(20);cell.setCellValue("初步原因分析");cell.setCellStyle(headerStyle); 
        cell=row.createCell(21);cell.setCellValue("问题性质");cell.setCellStyle(headerStyle); 
        cell=row.createCell(22);cell.setCellValue("问题性质理由");cell.setCellStyle(headerStyle); 
        cell=row.createCell(23);cell.setCellValue("问题类型");cell.setCellStyle(headerStyle); 
        cell=row.createCell(24);cell.setCellValue("预计对策时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(25);cell.setCellValue("计划关闭时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(26);cell.setCellValue("根本原因");cell.setCellStyle(headerStyle); 
        cell=row.createCell(27);cell.setCellValue("是否对策");cell.setCellStyle(headerStyle); 
        cell=row.createCell(28);cell.setCellValue("不对策理由");cell.setCellStyle(headerStyle); 
//        cell=row.createCell(29);cell.setCellValue("计划对策时间");cell.setCellStyle(headerStyle); 
//        cell=row.createCell(30);cell.setCellValue("计划关闭时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(29);cell.setCellValue("恒久对策");cell.setCellStyle(headerStyle); 
        cell=row.createCell(30);cell.setCellValue("效果确认");cell.setCellStyle(headerStyle); 
        cell=row.createCell(31);cell.setCellValue("效果确认理由");cell.setCellStyle(headerStyle); 
        cell=row.createCell(32);cell.setCellValue("效果确认时间");cell.setCellStyle(headerStyle); 
        cell=row.createCell(33);cell.setCellValue("设变通知单（ECO）编号");cell.setCellStyle(headerStyle); 
        cell=row.createCell(34);cell.setCellValue("是否更新DFMEA");cell.setCellStyle(headerStyle); 
        cell=row.createCell(35);cell.setCellValue("DFMEA编号");cell.setCellStyle(headerStyle); 
        cell=row.createCell(36);cell.setCellValue("是否更新技术条件");cell.setCellStyle(headerStyle); 
        cell=row.createCell(37);cell.setCellValue("技术标准编号");cell.setCellStyle(headerStyle); 
        cell=row.createCell(38);cell.setCellValue("再发防止其他措施");cell.setCellStyle(headerStyle); 
        cell=row.createCell(39);cell.setCellValue("是否合并");cell.setCellStyle(headerStyle); 
        cell=row.createCell(40);cell.setCellValue("是否挂牌");cell.setCellStyle(headerStyle); 


        
        for (int i = 0; i < issueList.size(); i++) {    
        	 Map<String, Object> map = issueList.get(i);  
             row = sheet.createRow(i + 1); 
             
             cell=row.createCell(0);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("CODE") )));  //问题编号
             cell=row.createCell(1);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ISSUE_PROCESSING_STATUS") )));  //问题状态
             cell=row.createCell(2);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("DEPT_NAME") )));  //责任部门
             cell=row.createCell(3);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("OWNER_NAME") )));  //责任工程师
             cell=row.createCell(4);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ISSUE_LEVEL_NAME") )));  //问题等级
             cell=row.createCell(5);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("TITLE") )));  //问题标题
             cell=row.createCell(6);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("DESCRIPTION") )));  //问题描述
             cell=row.createCell(7);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("SUBMIT_USER_NAME") )));  //问题提出人
             cell=row.createCell(8);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("OPEN_DATE") )));  //提出时间
             cell=row.createCell(9);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ISSUE_SOURCE") )));  //问题来源
             cell=row.createCell(10);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("PROGRAM_CODE") )));  //所属项目
             cell=row.createCell(11);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("SUB_PROJECT_CODE") )));  //子项目
             cell=row.createCell(12);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("STAGE") )));  //样车阶段
             cell=row.createCell(13);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("OCCURRENCE_DATE") )));  //发生时间
             cell=row.createCell(14);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("OCCURRENCE_SITE") )));  //发生场地
             cell=row.createCell(15);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("TEST_TYPE") )));  //试验类型
             cell=row.createCell(16);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("SAMPLE_NUMBER") )));  //样车编号
             cell=row.createCell(17);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("TROUBLE_PART_MILEAGE") )));  //故障零件里程
             cell=row.createCell(18);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("TEST_PROGRESS") )));  //试验进展
             cell=row.createCell(19);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("DISPOSAL_MEASURES") )));  //处置措施
             cell=row.createCell(20);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("FIRST_CAUSE_ANALYSIS") )));  //初步原因分析
             cell=row.createCell(21);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ISSUE_NATURE_NAME") )));  //问题性质
             cell=row.createCell(22);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_ISSUE_REASON") )));  //问题性质理由
             cell=row.createCell(23);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ISSUE_TYPE") )));  //问题类型
             cell=row.createCell(24);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("EXPECTED_DATE") )));  //预计对策时间
             cell=row.createCell(25);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("PLAN_CLOSED_DATE") )));  //计划关闭时间
             cell=row.createCell(26);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ROOT_CAUSE") )));  //根本原因
             cell=row.createCell(27);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_ACTION") )));  //是否对策
             cell=row.createCell(28);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("NO_ACTION_REASON") )));  //不对策理由
//             cell=row.createCell(29);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("EXPECTED_DATE") )));  //计划对策时间
//             cell=row.createCell(30);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("PLAN_CLOSED_DATE") )));  //计划关闭时间
             cell=row.createCell(29);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("PERM_ACTION") )));  //恒久对策
             cell=row.createCell(30);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("EFFECT_CONFIRMATION") )));  //效果确认
             cell=row.createCell(31);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("REASON") )));  //效果确认理由
             cell=row.createCell(32);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("EFFECT_CONFIRMATION_DATE") )));  //效果确认时间
             cell=row.createCell(33);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("ECO_NO") )));  //设变通知单（ECO）编号
             cell=row.createCell(34);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_UPDATED_DFMEA") )));  //是否更新DFMEA
             cell=row.createCell(35);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("DFMEA_NO") )));  //DFMEA编号
             cell=row.createCell(36);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_UPDATED_TC") )));  //是否更新技术条件
             cell=row.createCell(37);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("TECHNICAL_STANDARD_NO") )));  //技术标准编号
             cell=row.createCell(38);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("OTHER_MEASURES") )));  //再发防止其他措施
             cell=row.createCell(39);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_MERGE") )));  //是否合并
             cell=row.createCell(40);cell.setCellStyle(style);  cell.setCellValue(new HSSFRichTextString(ObjectConverter.convert2String(map.get("IS_LISTING") )));  //是否挂牌

        }    
    
        return wb;    
    }  
	
	/**
	 * 当前节点任务信息取得
	 */
	public List<Map<String, Object>> getIssueProcess(String issueId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT");
		sql.append(" T2.TASK_OWNER");
		sql.append(",T3.STEP_NAME AS TASK_NAME");
		sql.append(",T4.USERNAME AS TASK_OWNER_NAME");
		sql.append(" FROM");
		sql.append(" PM_PROCESS T1 INNER JOIN PM_PROCESS_TASK T2");
		sql.append(" ON T1.ID = T2.PROCESS_ID");
		sql.append(" AND T1.CURRENT_STEP_ID = T2.STEP_ID");
		sql.append(" INNER JOIN PM_PROCESS_STEP_TEMPLATE T3");
		sql.append(" ON T2.STEP_ID = T3.STEP_ID");
		sql.append(" AND T1.PROCESS_CODE = T3.PROCESS_CODE");
		sql.append(" INNER JOIN SYS_USER T4");
		sql.append(" ON T2.TASK_OWNER = T4.ID");
		sql.append(" AND T4.DELETE_BY IS NULL");
		sql.append(" WHERE");
		sql.append(" T1.SOURCE_TASK_ID = ?");
		params.add(issueId);
		
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public String isReturn(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT CASE WHEN (PREV_STEP_ID > STEP_ID) THEN 'Y' ELSE 'N' END IS_RETURN FROM PM_PROCESS_TASK WHERE ID = ?    ");
		params.add(taskId);
		String  isReturn = this.jdbcTemplate.queryForMap(sql.toString(), params.toArray()).get("IS_RETURN").toString();
		return isReturn;
	}
	
}
