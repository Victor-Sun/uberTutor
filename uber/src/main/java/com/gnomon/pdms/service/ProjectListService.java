package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.page.SqlBuilder;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMProgramVMDAO;
import com.gnomon.pdms.dao.PMProgramVehicleDAO;
import com.gnomon.pdms.dao.ProgramBaseinfoDAO;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.TaskDAO;
import com.gnomon.pdms.dao.UserObsDAO;
import com.gnomon.pdms.dao.VPmTempProgramDAO;
import com.gnomon.pdms.entity.TaskEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class ProjectListService {

	@Autowired
	private PMProgramVMDAO pmProgramVMDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ProgramDAO programDAO;
	
	@Autowired
	private ProgramBaseinfoDAO programBaseinfoDAO;
	
	@Autowired
	private VPmTempProgramDAO vPmTempProgramDAO;
	
	@Autowired
	private PMProgramVehicleDAO pmProgramVehicleDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private UserObsDAO userObsDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	/*
	 * 项目列表信息取得
	 */
	public GTPage<Map<String, Object>> getProjectList(String userId, String projectCode,
			int pageNo, int pageSize,String filter,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM(?, ID) = 1");
		paramList.add(loginUser);
		if (PDMSCommon.isNotNull(userId)) {
			sql.append(" AND ID IN(");
			sql.append(" SELECT DISTINCT PROGRAM_ID");
			sql.append(" FROM");
			sql.append(" PM_PROGRAM_USUAL");
			sql.append(" WHERE");
			sql.append(" USER_ID = ?)");
			paramList.add(userId);
		}
		if (PDMSCommon.isNotNull(projectCode)) {
			sql.append(" AND UPPER(CODE) LIKE UPPER(?)");
			paramList.add("%" + projectCode + "%");
		}
		sql.append(SqlBuilder.getFilterSql(filter));
		sql.append(SqlBuilder.getSortSql(sort, " SEQ ", ""));
		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
				paramList.toArray());
    }
	
	/*
	 * 项目一览取得
	 */
	public List<Map<String, Object>> getProjectListNoPaging() {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM(?, ID) = 1");
		sql.append(" ORDER BY SEQ");
		paramList.add(loginUser);
		return jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/*
	 * 项目列表信息取得
	 */
	public List<Map<String, Object>> getProjectList(String userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM(?, ID) = 1");
		paramList.add(loginUser);
		if (PDMSCommon.isNotNull(userId)) {
			sql.append(" AND USER_ID_USUAL = ?");
			paramList.add(userId);
		}
		sql.append(" ORDER BY PROGRAM_PRIORITY_CODE, CODE");
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/*
	 * SOP节点时间取得
	 */
	public List<TaskEntity> getProjectPlanSOP(String programId) {
		String hql = "FROM TaskEntity WHERE"
				+ " programId = ?"
				+ " AND taskTypeCode = ?"
				+ " AND deleteBy IS NULL";
		List<TaskEntity> projectPlanSOP = this.taskDAO.find(
				hql, programId, PDMSConstants.TASK_TYPE_SOP_NODE);
        return projectPlanSOP;
    }
	
	/**
	 * 创建项目
	 */
	public String saveProjectInfo(Map<String, String> programInfo) throws Exception {
		// 项目代号重复验证
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" CODE = ?");
		int cnt = this.jdbcTemplate.queryForInt(sql.toString(),
				programInfo.get("projectCode"));
		if (cnt > 0) {
			throw new Exception("项目代号已被占用，请重新指定！");
		}
		
		// 新建项目
		Map<String, Object> result = this.pkgPmDBProcedureServcie.newProgram(
				programInfo.get("projectCode"), programInfo.get("projectName"),
				programInfo.get("programType"), programInfo.get("projectManager"),
				SessionData.getLoginUserId());
		// 返回项目ID
		return PDMSCommon.nvl(result.get(PkgPmDBProcedureServcie.KEY_NP_PROGRAM_ID));
	}
	
	/**
	 * 删除项目
	 */
	public void deleteProgram(String programId) {
		// 删除项目
		this.pkgPmDBProcedureServcie.deleteProgram(programId,
				SessionData.getLoginUserId());
	}
	
	/**
	 * 更新项目序列
	 */
	public void updateProjectSort (Map<String, String> model) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新主节点信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_PROGRAM SET");
		sql.append(" SEQ = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(model.get("seq"));
		params.add(loginUser);
		params.add(model.get("id"));
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 项目一览取得
	 */
	public List<Map<String, Object>> getAllProgram() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" ORDER BY SEQ");
		return jdbcTemplate.queryForList(sql.toString());
    }
	
	/**
	 * 车型一览取得
	 */
	public List<Map<String, Object>> getAllVehicleByProgram(String programId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE_BASE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		sql.append(" ORDER BY CREATE_BY");
		return jdbcTemplate.queryForList(sql.toString(), programId);
    }
	
}

