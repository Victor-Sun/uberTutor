package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMProgramVehicleSopVMDAO;
import com.gnomon.pdms.dao.PMProgramVehicleVMDAO;
import com.gnomon.pdms.dao.PMTaskProgramVehicleDAO;
import com.gnomon.pdms.dao.PMTaskVehicleVMDAO;
import com.gnomon.pdms.dao.TaskDAO;
import com.gnomon.pdms.entity.PMProgramVehicleVMEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class ProjectPlanMajorNodeService {

	@Autowired
	private PMTaskVehicleVMDAO pmTaskVehicleVMDAO;
	
	@Autowired
	private PMProgramVehicleSopVMDAO pmProgramVehicleSopVMDAO;
	
	@Autowired
	private PMProgramVehicleVMDAO pmProgramVehicleVMDAO;
	
	@Autowired
	private PMTaskProgramVehicleDAO pmTaskProgramVehicleDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private SysCodeSequenceService sysCodeSequenceService;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	
	/**
	 * SOP节点信息取得
	 */
	public Map<String, Object> getProjectPlanSOP(
			String programVehicleId, String baselineId){
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_PROGRAM_VEHICLE_SOP_BL");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_PROGRAM_VEHICLE_SOP");
			sql.append(" WHERE");
		}
		sql.append(" ID = ?");
		params.add(programVehicleId);
		List<Map<String, Object>> sopList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (sopList.size() > 0) {
			return sopList.get(0);
		}
        return new HashMap<String, Object>();
    }
	
	/**
	 * 主节点信息取得
	 */
	public GTPage<Map<String, Object>> getProjectPlanMajorNode(
			String programId, String programVehicleId, String baselineId,int pageNo, int pageSize) {
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_VEHICLE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_VEHICLE");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_ID = ?");
		sql.append(" AND TASK_TYPE_CODE = ?");
		params.add(programId);
		params.add(PDMSConstants.TASK_TYPE_MAIN_NODE);
		if (PDMSCommon.isNotNull(programVehicleId)) {
			sql.append(" AND VEHICLE_ID = ?");
			params.add(programVehicleId);
		}
		return this.jdbcTemplate.queryPagination(sql.toString(),pageNo, pageSize, params.toArray());
	}
	
	/**
	 * 车型对应节点信息取得
	 */
	public List<PMProgramVehicleVMEntity> getProgramVehicleInfo(
			String programId, String taskId){
		String hql = "FROM PMProgramVehicleVMEntity WHERE programId = ? AND taskId = ?";
		List<PMProgramVehicleVMEntity> result = 
				this.pmProgramVehicleVMDAO.find(hql, programId, taskId);
		return result;
	}
	
	/**
	 * 主节点编辑
	 */
	public void updateMainNode(String programVehicleId, Map<String, String> model) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新主节点信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" TASK_NAME = ?");
		sql.append(",PLANNED_FINISH_DATE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(model.get("taskName"));
		params.add(DateUtils.strToDate(model.get("plannedFinishDate")));
		params.add(loginUser);
		params.add(model.get("id"));
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
	/**
	 * 主节点追加
	 */
	public void addMainNode(String programId,
			String programVehicleId, Map<String, String> model) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();

		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" INSERT INTO PM_TASK(");
		sql.append(" ID");
		sql.append(",TASK_CODE");
		sql.append(",TASK_NAME");
		sql.append(",PROGRAM_ID");
		sql.append(",PLANNED_FINISH_DATE");
		sql.append(",TASK_STATUS_CODE");
		sql.append(",TASK_TYPE_CODE");
		sql.append(",PLAN_LEVEL");
		sql.append(",PROGRAM_VEHICLE_ID");
		sql.append(",CREATE_BY");
		sql.append(",CREATE_DATE");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",SYSDATE)");
		params.add(PDMSCommon.generateUUID());
		params.add(this.sysCodeSequenceService.GenerateTaskCode());
		params.add(model.get("taskName"));
		params.add(programId);
		params.add(DateUtils.strToDate(model.get("plannedFinishDate")));
		params.add(PDMSConstants.TASK_STATUS_NOT_START);
		params.add(PDMSConstants.TASK_TYPE_MAIN_NODE);
		params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1.intValue());
		params.add(programVehicleId);
		params.add(loginUser);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 排序
		this.resequenceService.requencePmTask(programId, programVehicleId);
		
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
	/**
	 * 主节点删除
	 */
	public void deleteMainNode(String programVehicleId, String taskId) {
		// 登录用户取得
		List<Object> params = null;
		String loginUser = SessionData.getLoginUserId();
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" DELETE_BY = ?");
		sql.append(",DELETE_DATE = SYSDATE");		
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
	/**
	 * SOP节点编辑
	 */
	public void updateSOP(String programVehicleId, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新SOP
		this.pkgPmDBProcedureServcie.updateSOPSchedule(programVehicleId,
				model.get("taskName"), DateUtils.strToDate(model.get("plannedFinishDate")),
				("true".equals(model.get("chkUpdatePlan")) ? PDMSConstants.STATUS_Y : PDMSConstants.STATUS_N),
				loginUser);
	}
}
