package com.gnomon.pdms.service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.page.SqlBuilder;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.GTIssueKBDAO;
import com.gnomon.pdms.dao.IssueProgressDAO;
import com.gnomon.pdms.dao.VIssueSourceDAO;
import com.gnomon.pdms.entity.IssueKBEntity;
import com.gnomon.pdms.entity.IssueProgressEntity;
import com.gnomon.pdms.entity.VIssueSourceEntity;
import com.gnomon.pdms.procedure.PkgPmIssueDBProcedureServcie;

@Service
@Transactional
public class ProjectOpenIssueService {

	@Autowired
	private VIssueSourceDAO vissueSourceDAO;
	
	@Autowired
	private IssueProgressDAO issueProgressDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmIssueDBProcedureServcie pkgPmIssueDBProcedureServcie;
	
	@Autowired
	private GTIssueKBDAO gtIssueKBDAO;

	public List<VIssueSourceEntity> getProjectOpenIssueService(String programId){
		String hql = "FROM VIssueSourceEntity WHERE programId = ? AND issueTypeCode = ?";
		List<VIssueSourceEntity> projectOpenIssue =
				this.vissueSourceDAO.find(hql, programId, "ISSUE_TYPE_1");
        return projectOpenIssue;
    }

	/**
	 * 【项目管理-项目列表】一OpenIssue览详细form数据取得Service
	 */
	public VIssueSourceEntity getProjectOpenIssueFormService(Long issueId){
		VIssueSourceEntity result =
				this.vissueSourceDAO.findUniqueBy("id",issueId);
        return result;
    }	

	/**
	 * 【项目管理-项目列表】一OpenIssue览详细grid数据取得Service
	 */
	public List<IssueProgressEntity> getProjectOpenIssueGridService(Long issueId){
		String hql = "FROM IssueProgressEntity WHERE issueId = ?";
		List<IssueProgressEntity> projectOpenIssue =
				this.issueProgressDAO.find(hql, issueId);
        return projectOpenIssue;
    }
	
	/**
	 * 查询问题来源
	 * @return
	 */
	public List<Map<String, Object>> getIssueSourceList(){
		return jdbcTemplate.queryForList("select * from PM_ISSUE_SOURCE order by SEQ");
	}

	/**
	 * 查询项目车型
	 * @param programId
	 * @return
	 */
	public List<Map<String, Object>> getVehicleList(String programId){
		if(StringUtils.isEmpty(programId) || "undefined".equals(programId)){
			return getVehicleList();
		}
		return jdbcTemplate.queryForList("select * from PM_PROGRAM_VEHICLE where PROGRAM_ID=?",programId);
	}
	
//	/**
//	 * 可提出问题项目一览取得
//	 */
//	public List<Map<String, Object>> getProgramList4RaiseIssue() {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT *");
//		sql.append(" FROM");
//		sql.append(" V_PM_PROCESSING_PROGRAM");
//		sql.append(" ORDER BY CODE");
//		return this.jdbcTemplate.queryForList(sql.toString());
//    }
	
	/**
	 * 可提出问题项目一览/车型一览取得
	 */
	public List<Map<String, Object>> getVehicleList4RaiseIssue(String programId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE_BASE");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, ID) = 1");
		sql.append(" AND LIFECYCLE_STATUS = ?");
		paramList.add(loginUser);
		paramList.add(PDMSConstants.LIFECYCLE_STATUS_IN_PROCESS);
		if (PDMSCommon.isNotNull(programId)) {
			sql.append(" AND PROGRAM_ID = ?");
			paramList.add(programId);
//		} else {
//			sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM(?, PROGRAM_ID) = 1");
//			paramList.add(loginUser);
		}
		sql.append(" ORDER BY PROGRAM_ID, CREATE_DATE");
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	public List<Map<String, Object>> getVehicleList(){
		return jdbcTemplate.queryForList("select * from PM_PROGRAM_VEHICLE");
	}
	
	/**
	 * 查询责任组
	 * @param programVehicleId
	 * @return
	 */
	public List<Map<String, Object>> getResponsibleObsList(String programVehicleId){
		return jdbcTemplate.queryForList("select * from V_PROGRAM_VEHICLE_OBS where PROGRAM_VEHICLE_ID=?",programVehicleId);
	}
	
	/**
	 * 查询责任人
	 * @param obsId
	 * @return
	 */
	public List<Map<String, Object>> getResponsibleUserList(String obsId){
		return jdbcTemplate.queryForList("select * from V_PROGRAM_VEHICLE_MEMBER where OBS_ID=?",obsId);
	}
	
	public Long saveIssue(Long issueId,String userId,String programVehicleId,String issueSourceId,String issueTypeId,String title,String issueDescription,String issueCause,
			String dueDate,String raiseBy,String raiseDate,String obsId,String respUserId, String issuePriorityCode,String issueSourceDescription, String action,String ownerComment,String returnComment) throws Exception{
		Session session = issueProgressDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call PKG_PM_ISSUE.UPDATE_ISSUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		if(issueId == null){
			issueId = 0l;
		}
		statement.setLong(1, issueId);
		statement.setString(2, userId);
		statement.setString(3, programVehicleId);
		statement.setLong(4, PDMSCommon.toLong(issueSourceId));
		if(issueTypeId != null){
			statement.setLong(5, PDMSCommon.toLong(issueTypeId));
		}else{
			statement.setLong(5, 1l);
		}
		
		statement.setString(6, title);
		statement.setString(7, issueDescription);
		statement.setString(8, issueCause);
		statement.setDate(9, PDMSCommon.toSqlDate(DateUtils.strToDate(dueDate)));		
		statement.setString(10, raiseBy);
		statement.setDate(11, PDMSCommon.toSqlDate(DateUtils.strToDate(raiseDate)));
		statement.setString(12, obsId);
		statement.setString(13, respUserId);
		statement.setString(14, issuePriorityCode);
		statement.setString(15, issueSourceDescription);
		statement.setString(16, action);
		statement.setString(17, ownerComment);
		statement.setString(18, returnComment);
		statement.registerOutParameter(19, Types.VARCHAR);
		statement.registerOutParameter(20, Types.VARCHAR);
		statement.registerOutParameter(1, Types.INTEGER);
		statement.executeUpdate();
		issueId = statement.getLong(1);
		String returnCode = statement.getString(19);
		String returnMsg = statement.getString(20);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
		return issueId;
	}
	
	public void saveArchiveIssue(Long issueId,String title,String issueDescription,String issueCause,
			String issueResolution,String userId) throws Exception{
		IssueKBEntity entity = new IssueKBEntity();
		entity.setOrigIssueId(issueId);
		entity.setTitle(title);
		entity.setIssueDescription(issueDescription);
		entity.setIssueCause(issueCause);
		entity.setIssueResolution(issueResolution);
		entity.setOperatorId(userId);
		entity.setCreateDate(new Date());
		entity.setUpdateDate(new Date());
		gtIssueKBDAO.save(entity);
	}
	
	public GTPage<Map<String, Object>> getOpenIssueList(String programVehicleId,int pageNo, int pageSize,String filter,String sort){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_PM_ISSUE where PROGRAM_VEHICLE_ID=? and STATUS_CODE <> 'DRAFT' and PKG_PERMISSION.CAN_VIEW_OPEN_ISSUE(?, ISSUE_ID) = 1 ");
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE,ISSUE_ID ", "DESC"));
		return jdbcTemplate.queryPagination(sql.toString(),pageNo, pageSize, programVehicleId, SessionData.getLoginUserId());
	}
	
	public GTPage<Map<String, Object>> getOpenIssueListByProgramId(String programId,int pageNo, int pageSize,String filter,String sort){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_PM_ISSUE where  PROGRAM_ID=? and STATUS_CODE <> 'DRAFT' and PKG_PERMISSION.CAN_VIEW_OPEN_ISSUE(?, ISSUE_ID) = 1 ");
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE,ISSUE_ID ", "DESC"));
		return jdbcTemplate.queryPagination(sql.toString(),
				pageNo, pageSize, programId, SessionData.getLoginUserId());
	}
	
	public GTPage<Map<String, Object>> getOpenIssueList(Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_ISSUE");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_OPEN_ISSUE(?, ISSUE_ID) = 1");
		params.add(loginUser);
		sql.append(" AND STATUS_CODE <> ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 问题标题
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTite"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTite") + "%");
			}
			// 状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
			}
			if ("true".equals(searchModel.get("searchComplete"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_COMPLETE);
			}
			if (subParams.size() > 0) {
				sql.append(" AND STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND STATUS_CODE IS NULL");
			}
			// 计划开始日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			// 计划开始日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			// 实际完成日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateFrom"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateFrom"))));
			}
			// 实际完成日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateTo"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateTo"))));
			}
			// 项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 责任组
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObs"))) {
				sql.append(" AND UPPER(RESP_OBS_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRespObs") + "%");
			}
			// 责任人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(RESP_USER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 录入人
			if (PDMSCommon.isNotNull(searchModel.get("searchCreateBy"))) {
				sql.append(" AND UPPER(CREATE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchCreateBy") + "%");
			}
			// 提出人
			if (PDMSCommon.isNotNull(searchModel.get("searchRaiseBy"))) {
				sql.append(" AND UPPER(RAISE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRaiseBy") + "%");
			}
			// 问题来源
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueSource"))) {
				sql.append(" AND ISSUE_SOURCE_ID = ?");
				params.add(searchModel.get("searchIssueSource"));
			}
			// 进展状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchRed"))) {
				subParams.add("RED");
			}
			if ("true".equals(searchModel.get("searchYellow"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchGray"))) {
				subParams.add("GRAY");
			}
			if ("true".equals(searchModel.get("searchGreen"))) {
				subParams.add("GREEN");
			}
			if (subParams.size() > 0) {
				sql.append(" AND PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PROGRESS_STATUS IS NULL");
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE,ISSUE_ID ", "DESC"));
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	

	
	/**
	 * 取得部门空间-项目OpenIssue列表数据
	 */
	public GTPage<Map<String, Object>> getDeptOpenIssueList(
			Map<String, String> searchModel, int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_ISSUE");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_DEPT_PM_ISSUE(?, ISSUE_ID) = 1");
		params.add(loginUser);
		sql.append(" AND STATUS_CODE <> ?");
		params.add(PDMSConstants.PROCESS_STATUS_DRAFT);
		
		// 查询条件
		if (searchModel.size() > 0) {
			// 问题标题
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueTite"))) {
				sql.append(" AND UPPER(ISSUE_TITLE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchIssueTite") + "%");
			}
			// 状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchWaiting"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_OPEN);
			}
			if ("true".equals(searchModel.get("searchProcessing"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_PENDING);
			}
			if ("true".equals(searchModel.get("searchComplete"))) {
				subParams.add(PDMSConstants.PROCESS_STATUS_COMPLETE);
			}
			if (subParams.size() > 0) {
				sql.append(" AND STATUS_CODE IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND STATUS_CODE IS NULL");
			}
			// 计划开始日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateFrom"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateFrom"))));
			}
			// 计划开始日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchDueDateTo"))) {
				sql.append(" AND TO_CHAR(DUE_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchDueDateTo"))));
			}
			// 实际完成日期From
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateFrom"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateFrom"))));
			}
			// 实际完成日期To
			if (PDMSCommon.isNotNull(searchModel.get("searchAFDateTo"))) {
				sql.append(" AND TO_CHAR(ACTUAL_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchAFDateTo"))));
			}
			// 项目
			if (PDMSCommon.isNotNull(searchModel.get("searchProgram"))) {
				sql.append(" AND PROGRAM_ID = ?");
				params.add(searchModel.get("searchProgram"));
			}
			// 车型
			if (PDMSCommon.isNotNull(searchModel.get("searchVechile"))) {
				sql.append(" AND PROGRAM_VEHICLE_ID = ?");
				params.add(searchModel.get("searchVechile"));
			}
			// 责任组
			if (PDMSCommon.isNotNull(searchModel.get("searchRespObs"))) {
				sql.append(" AND UPPER(RESP_OBS_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRespObs") + "%");
			}
			// 责任人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(RESP_USER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 录入人
			if (PDMSCommon.isNotNull(searchModel.get("searchCreateBy"))) {
				sql.append(" AND UPPER(CREATE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchCreateBy") + "%");
			}
			// 提出人
			if (PDMSCommon.isNotNull(searchModel.get("searchRaiseBy"))) {
				sql.append(" AND UPPER(RAISE_BY_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchRaiseBy") + "%");
			}
			// 问题来源
			if (PDMSCommon.isNotNull(searchModel.get("searchIssueSource"))) {
				sql.append(" AND ISSUE_SOURCE_ID = ?");
				params.add(searchModel.get("searchIssueSource"));
			}
			// 进展状态
			subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchRed"))) {
				subParams.add("RED");
			}
			if ("true".equals(searchModel.get("searchYellow"))) {
				subParams.add("YELLOW");
			}
			if ("true".equals(searchModel.get("searchGray"))) {
				subParams.add("GRAY");
			}
			if ("true".equals(searchModel.get("searchGreen"))) {
				subParams.add("GREEN");
			}
			if (subParams.size() > 0) {
				sql.append(" AND PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PROGRESS_STATUS IS NULL");
			}
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " ISSUE_PRIORITY_CODE,ISSUE_ID DESC,RAISE_DATE", ""));
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	public GTPage<Map<String, Object>> getDraftOpenIssueList(int pageNo, int pageSize){
		return jdbcTemplate.queryPagination("select * from V_PM_ISSUE WHERE STATUS_CODE = 'DRAFT' and CREATE_BY = ? ORDER BY ISSUE_PRIORITY_CODE DESC",
				pageNo, pageSize, SessionData.getLoginUserId());
	}
	
	public GTPage<Map<String, Object>> getArchiveOpenIssueList(String fromDate,String toDate,String userName,String title,String programName,int pageNo, int pageSize){
		String sql = "select * from V_PM_ISSUE_KB WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(fromDate)){
			sql += " and UPDATE_DATE > ? ";
			paramList.add(DateUtils.strToDate(fromDate));
		}
		if(StringUtils.isNotEmpty(toDate)){
			sql += " and UPDATE_DATE > ? ";
			paramList.add(DateUtils.strToDate(toDate));
		}
		if(StringUtils.isNotEmpty(userName)){
			sql += " and USER_NAME=? ";
			paramList.add(userName);
		}
		if(StringUtils.isNotEmpty(title)){
			sql += " and TITLE like ? ";
			paramList.add("%"+title+"%");
		}
		if(StringUtils.isNotEmpty(programName)){
			sql += " and (PROGRAM_NAME like ? OR PROGRAM_CODE LIKE ?)";
			paramList.add("%"+programName+"%");
			paramList.add("%"+programName+"%");
		}
		return jdbcTemplate.queryPagination(sql,
				pageNo,pageSize,paramList.toArray());
	}
	
	public Map<String, Object> getIssueStatisticMap(String programId){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from V_PM_ISSUE_STATS_BY_PROGRAM  where PROGRAM_ID=? ",programId);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new HashMap<String, Object>();
		}
	}
	
	public Map<String, Object> getIssueStatisticMapByVehicle(String programVehicleId){
		//return jdbcTemplate.queryForMap("select * from V_PM_ISSUE_STATS_BY_VEHICLE  where PROGRAM_VEHICLE_ID=?",programVehicleId);
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from V_PM_ISSUE_STATS_BY_VEHICLE  where PROGRAM_VEHICLE_ID=?",programVehicleId);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new HashMap<String, Object>();
		}
	}
	
	public Long reportIssueProgress(Long issueId,String operatorId,String description) throws Exception{
		Session session = issueProgressDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call PKG_PM_ISSUE.REPORT_ISSUE_PROGRESS(?,?,?,?,?)}");
		if(issueId == null){
			issueId = 0l;
			throw new RuntimeException("问题提交后才能更新进度！");
		}
		statement.setLong(1, issueId);
		statement.setString(2, operatorId);
		statement.setString(3, description);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
		return issueId;
	}
	
	public Long deleteOpenIssue(Long issueId,String operatorId,String description) throws Exception{
		Session session = issueProgressDAO.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call PKG_PM_ISSUE.DELETE_ISSUE(?,?,?,?,?)}");
		if(issueId == null){
			issueId = 0l;
		}
		statement.setLong(1, issueId);
		statement.setString(2, operatorId);
		statement.setString(3, description);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
		return issueId;
	}
	
	public List<Map<String, Object>> getProjectOpenIssueLine(String programId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_PM_M_TREND where PROGRAM_ID=? ",programId);
	}
	
	public List<Map<String, Object>> getProjectOpenIssueBar(String programId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_PS_BY_P_FUNC where PROGRAM_ID=? ",programId);
	}
	
	public List<Map<String, Object>> getProjectOpenIssuePie(String programId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_PS_BY_P where PROGRAM_ID=? ",programId);
	}
	
	public List<Map<String, Object>> getProjectVehicleOpenIssueLine(String programVehicleId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_PV_M_TREND where PROGRAM_VEHICLE_ID=? ",programVehicleId);
	}
	
	public Map<String, Object> getOpenIssueForm(Long issueId){
		return jdbcTemplate.queryForMap("select * from V_PM_ISSUE where ISSUE_ID=? ",issueId);
	}
	
	public Map<String, Object> getOpenIssue(Long issueId){
		return jdbcTemplate.queryForMap("select * from PM_ISSUE where ID=? ",issueId);
	}
	
	public List<Map<String, Object>> getProjectOpenIssueNotes(Long issueId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_PROGRESS where ISSUE_ID=? ORDER BY ID", issueId);
	}
	
	public List<Map<String, Object>> getIssueKB(String title){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_KB where TITLE like '%"+title+"%'");
	}
	
	public void addMembers(Long issueId,List<Map<String, String>> memberIdlist){
		for(Map<String, String> map:memberIdlist){
			pkgPmIssueDBProcedureServcie.addMember(issueId, map.get("id"));
		}
	}
	
	public List<Map<String, Object>> getIssueMemberList(Long issueId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_MEMBER where ISSUE_ID = ?",issueId);
	}
	
	public List<Map<String, Object>> getForumList(Long issueId){
		return jdbcTemplate.queryForList("select * from V_PM_ISSUE_POST where ISSUE_ID = ?",issueId);
	}
	
	/**
	 * 问题类型查询
	 */
	public List<Map<String, Object>> getIssueTypeList(){
		return this.jdbcTemplate.queryForList("SELECT * FROM PM_ISSUE_TYPE WHERE TYPE = 'P' AND IS_ACTIVE = 'Y'");
	}
	
	/**
	 * 问题审批记录查询
	 */
	public List<Map<String, Object>> getProcessRecord(Long issueId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T3.*");
		sql.append(" FROM PM_ISSUE T1 INNER JOIN PM_PROCESS T2");
		sql.append(" ON T1.UUID = T2.SOURCE_TASK_ID");
		sql.append(" INNER JOIN V_PM_PROCESS_TASK T3");
		sql.append(" ON T2.ID = T3.PROCESS_ID");
		sql.append(" AND COMPLETE_FLAG = 'Y'");
		sql.append(" WHERE");
		sql.append(" T1.ID = ?");
		sql.append(" ORDER BY COMPLETE_DATE");
		
		return this.jdbcTemplate.queryForList(sql.toString(), issueId);
	}
}