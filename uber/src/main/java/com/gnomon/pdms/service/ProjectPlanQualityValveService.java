package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMDeliverableDAO;
import com.gnomon.pdms.dao.PMDeliverableTaskVMDAO;
import com.gnomon.pdms.dao.PMProgramVehicleVMDAO;
import com.gnomon.pdms.dao.PMTaskProgramVehicleDAO;
import com.gnomon.pdms.dao.PMTaskValveVMDAO;
import com.gnomon.pdms.dao.TaskDAO;
import com.gnomon.pdms.entity.PMProgramVehicleVMEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class ProjectPlanQualityValveService {

	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private PMDeliverableTaskVMDAO pmDeliverableTaskVMDAO;
	
	@Autowired
	private PMTaskProgramVehicleDAO pmTaskProgramVehicleDAO;
	
	@Autowired
	private PMDeliverableDAO deliverableDAO;
	
	@Autowired
	private PMTaskValveVMDAO pmTaskValveVMDAO;
	
	@Autowired
	private PMProgramVehicleVMDAO pmProgramVehicleVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	@Autowired
	private DeptPlanService deptPlanService;
	
	/**
	 * 阀门信息一览取得
	 */
	public List<Map<String, Object>> getProjectPlanQualityValve(
			String programId, String programVehicleId, String baselineId){
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_VALVE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_VALVE");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_ID = ?");
		params.add(programId);
		if (PDMSCommon.isNotNull(programVehicleId)) {
			sql.append(" AND VEHICLE_ID = ?");
			params.add(programVehicleId);
		}
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
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
	 * 阀门交付物信息取得
	 */
	public List<Map<String, Object>> getGateDeliverableInfo(
			String taskId, String baselineId) {
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_DELIVERABLE_TASK_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_DELIVERABLE_TASK");
			sql.append(" WHERE");
		}
		sql.append(" TASK_ID = ?");
		params.add(taskId);
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 阀门信息更新
	 */
	public void updateProjectPlanQualityValve(String programVehicleId, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 阀门信息更新
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" TASK_CODE = ?");
		sql.append(",TASK_NAME = ?");
		sql.append(",PLANNED_FINISH_DATE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		
		List<Object> params = new ArrayList<Object>();
		params.add(model.get("taskName"));
		params.add(model.get("taskName"));
		params.add(DateUtils.strToDate(model.get("plannedFinishDate")));
		params.add(loginUser);
		params.add(model.get("id"));
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
//	/**
//	 * 阀门交付物信息更新
//	 */
//	public void updateDeliverable(Map<String, String> model) {
//		// 登录用户取得
//		String loginUser = SessionData.getLoginUserId();
//		// 更新节点
//		StringBuffer sql = new StringBuffer();
//		sql.append(" UPDATE PM_DELIVERABLE SET");
//		sql.append(" CODE = ?");
//		sql.append(",NAME = ?");
//		sql.append(",UPDATE_BY = ?");
//		sql.append(",UPDATE_DATE = SYSDATE");
//		sql.append(" WHERE");
//		sql.append(" ID = ?");
//		List<Object> params = new ArrayList<Object>();
//		params.add(model.get("code"));
//		params.add(model.get("name"));
//		params.add(loginUser);
//		params.add(model.get("id"));
//		jdbcTemplate.update(sql.toString(), params.toArray());
//	}
	
	/**
	 * 阀门交付物是否适用更新
	 */
	public void updateDeliverableFit(String programVehicleId, String deliverableId,
			boolean isFit, String notFitReason) {
		this.pkgPmDBProcedureServcie.setFitDeliverable(programVehicleId,
				deliverableId, SessionData.getLoginUserId(),
				(isFit ? PDMSConstants.STATUS_Y : PDMSConstants.STATUS_N),
				notFitReason);
	}
	
	/**
	 * 阀门交付物是否为关键交付物更新
	 */
	public void updateDeliverableKey(String id, boolean isKey) {
		
		// 更新节点
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_DELIVERABLE SET");
		sql.append(" IS_KEY = ?");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		List<Object> params = new ArrayList<Object>();
		if (isKey) {
			params.add(PDMSConstants.STATUS_Y);
		} else {
			params.add(PDMSConstants.STATUS_N);
		}
		params.add(id);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
//	/**
//	 * 阀门交付物信息新增
//	 */
//	public void insertDeliverable(String taskId, Map<String, String> model) {
//		// 登录用户取得
//		String loginUser = SessionData.getLoginUserId();
//		DeliverableEntity deliverableEntity = new DeliverableEntity();
//		deliverableEntity.setId(PDMSCommon.generateUUID());
//		deliverableEntity.setCode(model.get("code"));
//		deliverableEntity.setName(model.get("name"));
//		deliverableEntity.setTaskId(taskId);
//		deliverableEntity.setIsFit(PDMSConstants.STATUS_Y);
//		deliverableEntity.setCreateBy(loginUser);
//		deliverableEntity.setCreateDate(new Date());
//		deliverableDAO.save(deliverableEntity);
//	}
	
	/**  
	 * 阀门信息新增
	 */
	public void insertProjectPlanQualityValve(String programId,
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
		params.add(model.get("taskName"));
		params.add(model.get("taskName"));
		params.add(programId);
		params.add(DateUtils.strToDate(model.get("plannedFinishDate")));
		params.add(PDMSConstants.TASK_STATUS_NOT_START);
		params.add(PDMSConstants.TASK_TYPE_GATE);
		params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1.intValue());
		params.add(programVehicleId);
		params.add(loginUser);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		// 排序
		resequenceService.requencePmTask(programId, programVehicleId);
		
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
	/**
	 * 质量阀删除
	 */
	public void deleteProjectPlanQualityValve(String programVehicleId, String taskId) {
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
	 * 质量阀交付物删除
	 */
	public void deleteDeliverable(String programVehicleId, String deliverableId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		this.pkgPmDBProcedureServcie.deleteDeliverable(
				programVehicleId, deliverableId, loginUser);
	}
	
	/**
	 * 阀门交付物信息负责主体更新
	 */
	public void updateDeliverableObs(String deliverableId, String obsId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新节点
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_DELIVERABLE SET");
		sql.append(" OBS_ID = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(obsId);
		params.add(loginUser);
		params.add(deliverableId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
}