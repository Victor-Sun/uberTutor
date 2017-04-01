package com.gnomon.pdms.service;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.gnomon.common.base.StringIdEntity;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMDeptPlanVMDAO;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMProgramPlanDAO;
import com.gnomon.pdms.dao.PMProgramPlanVMDAO;
import com.gnomon.pdms.dao.PMReleaseRecordDAO;
import com.gnomon.pdms.dao.TaskDAO;
import com.gnomon.pdms.entity.PMDeptPlanVMEntity;
import com.gnomon.pdms.entity.PMProgramPlanVMEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;
import com.gnomon.pdms.procedure.PkgPmObsDBProcedureServcie;
import com.gnomon.pdms.procedure.PkgPmProcessDBProcedureServcie;
import com.gnomon.pdms.procedure.PkgPmTemplateDBProcedureServcie;

@Service
@Transactional
public class ProjectPlanService extends StringIdEntity {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private PMProgramPlanDAO pmProgramPlanDAO;
	
	@Autowired
	private PMReleaseRecordDAO pmReleaseRecordDAO;
	
	@Autowired
	private PMProgramPlanVMDAO pmProgramPlanVMDAO;
	
	@Autowired
	private PMDeptPlanVMDAO pmDeptPlanVMDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	@Autowired
	private PkgPmProcessDBProcedureServcie pkgPmProcessDBProcedureServcie;
	
	@Autowired
	private PkgPmTemplateDBProcedureServcie pkgPmTemplateDBProcedureServcie;
	
	@Autowired
	private PkgPmObsDBProcedureServcie pkgPmObsDBProcedureServcie;
	
	/**
	 * 发布计划
	 */
	public List<Map<String, Object>> deployPlan(String programVehicleId, String obsId)
			throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		String loginUser = SessionData.getLoginUserId();
		
		String fnObsId = "";
		// 二级计划
		if (PDMSCommon.isNotNull(obsId)) {
			fnObsId = obsId;
			// 主计划是否计划完成Check
			sql = new StringBuffer();
			params = new ArrayList<Object>();
			sql.append("SELECT COUNT(1) FROM PM_PROGRAM_PLAN WHERE PROGRAM_VEHICLE_ID = ? AND PLAN_LEVEL = 1 AND IS_ACTIVE = ? AND STATUS_CODE = ?");
			params.add(programVehicleId);
			params.add(PDMSConstants.STATUS_Y);
			params.add(PDMSConstants.PROGRAM_STATUS_ACTIVE);
			int cnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
			if (cnt == 0) {
				throw new Exception("由于主计划尚未发布，二级计划暂时不能发布！");
			}
		} else {
			// 根据车型查询根节点组织
			sql = new StringBuffer();
			sql.append("SELECT PKG_PM_OBS.ROOT_OBS_ID(?) AS OBS_ID FROM DUAL");
			List<Map<String, Object>> obsList = this.jdbcTemplate.queryForList(
					sql.toString(), programVehicleId);
			if (obsList.size() > 0) {
				fnObsId = PDMSCommon.nvl(obsList.get(0).get("OBS_ID"));
			}
		}
		this.pkgPmDBProcedureServcie.updatePlanStatus(fnObsId,
				PDMSConstants.PROGRAM_STATUS_ACTIVE);
		
		// 查询已发布的任务
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" ID");
		sql.append(",TASK_NAME");
		sql.append(",TASK_OWNER");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_PUBLISHED");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FN_GROUP_ID = ?");
			params.add(obsId);
			sql.append(" AND PLAN_LEVEL = ?");
			params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
		} else {
			sql.append(" AND PLAN_LEVEL = ?");
			params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
		}
		final List<Map<String, Object>> publishedTaskList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		
		// 查询新发布的任务
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" ID");
		sql.append(",TASK_NAME");
		sql.append(",TASK_OWNER");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_NEWPUBLISH");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FN_GROUP_ID = ?");
			params.add(obsId);
			sql.append(" AND PLAN_LEVEL = ?");
			params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
		} else {
			sql.append(" AND PLAN_LEVEL = ?");
			params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
		}
		List<Map<String, Object>> newTaskList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		
		// 已发布任务-更改当前责任人
		sql = new StringBuffer();
		sql.append(" UPDATE PM_PROCESS_TASK SET");
		sql.append(" TASK_OWNER = ?");
		sql.append(" WHERE");
		sql.append(" PROCESS_ID IN");
		sql.append(" (SELECT ID FROM PM_PROCESS WHERE SOURCE_TASK_ID = ?)");
		sql.append(" AND STEP_ID = 1");
		sql.append(" AND COMPLETE_FLAG = ?");
		sql.append(" AND TASK_OWNER <> ?");
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return publishedTaskList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, Object> task = publishedTaskList.get(i);
            	ps.setString(1, PDMSCommon.nvl(task.get("TASK_OWNER")));
            	ps.setString(2, PDMSCommon.nvl(task.get("ID")));
            	ps.setString(3, PDMSConstants.STATUS_N);
            	ps.setString(4, PDMSCommon.nvl(task.get("TASK_OWNER")));
            }
        });
		
		// 新发布任务-启动流程
		StringBuffer sbTaskId = new StringBuffer();
		for (Map<String, Object> taskMap : newTaskList) {
			String taskId = PDMSCommon.nvl(taskMap.get("ID"));
			String taskOwner = PDMSCommon.nvl(taskMap.get("TASK_OWNER"));
			this.pkgPmProcessDBProcedureServcie.createNewProcess(
					PDMSConstants.PROCESS_CODE_DELIVERABLE, taskId, taskOwner, null);
			if (sbTaskId.length() > 0) {
				sbTaskId.append(", ");
			}
			sbTaskId.append("'" + taskId + "'");
		}
		if (sbTaskId.length() > 0) {
			sql = new StringBuffer();
			sql.append(" UPDATE PM_TASK SET");
			sql.append(" PUBLISH_BY = ?");
			sql.append(",PUBLISH_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID IN(");
			sql.append(sbTaskId);
			sql.append(")");
			this.jdbcTemplate.update(sql.toString(), loginUser);
		}

		// 返回值
		newTaskList.addAll(publishedTaskList);
		return newTaskList;
	}
	
	/**
	 * 更改项目状态验证
	 */
	public int changeProgramStatusCheck(String programVehicleId) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 验证是否所有交付物都有关键活动
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE_TASK");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND REF_TASK_ID IS NULL");
		params.add(programVehicleId);
		return this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
	}
	
//	/**
//	 * 更改项目状态
//	 */
//	public void changeProgramStatus(String programVehicleId, String planStatus)
//			throws Exception {
//		// 二级计划更改为完成时需要做如下Check
//        if (PDMSConstants.PROGRAM_STATUS_PLANNED.equals(planStatus)) {
//			// 项目计划是否Check全部完成Check
//			if (this.getInactivePlan(programVehicleId) > 0) {
//				throw new Exception("项目尚有未完成的计划（主计划或某二级计划），暂时不能更改为完成！");
//			}
//		}
//		
//		// 更改项目状态
//        this.pkgPmDBProcedureServcie.updatePgPlanStatus(programVehicleId, planStatus);
//	}

	/**
	 * 更改计划状态
	 */
	public void changePlanStatus(String programVehicleId, String obsId,
			String planStatus) throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		
		// 计划更改为完成时需要做如下Check
		if (PDMSConstants.PROGRAM_STATUS_PLANNED.equals(planStatus)) {
			// 任责任主体是否全部指定Check
			if (this.getNoObsTask(programVehicleId, obsId) > 0) {
				throw new Exception("计划中存在尚未分配责任主体的任务，暂时不能更改为完成！");
			}
			
			// 任务负责人是否全部指定Check
			if (this.getNoOwnerTask(programVehicleId, obsId) > 0) {
				throw new Exception("计划中存在尚未分配负责人的任务，暂时不能更改为完成！");
			}
			
			// 二级计划
			if(PDMSCommon.isNotNull(obsId)) {
				// 专业经理是否全部指定Check
				sql = new StringBuffer();
				sql.append("SELECT PKG_PM_OBS.OBS_FN_GROUP_MGR(?) AS FM FROM DUAL");
				Map<String, Object> result =
						this.jdbcTemplate.queryForMap(sql.toString(), obsId);
				if (PDMSCommon.isNull(PDMSCommon.nvl(result.get("FM")))) {
					throw new Exception("该专业领域尚未设置经理，暂时不能更改为完成！");
				}
				// 主计划是否计划完成Check
				sql = new StringBuffer();
				params = new ArrayList<Object>();
				sql.append("SELECT COUNT(1) FROM PM_PROGRAM_PLAN WHERE PROGRAM_VEHICLE_ID = ? AND PLAN_LEVEL = 1 AND IS_ACTIVE = ? AND STATUS_CODE = ?");
				params.add(programVehicleId);
				params.add(PDMSConstants.STATUS_Y);
				params.add(PDMSConstants.PROGRAM_STATUS_INACTIVE);
				int cnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
				if (cnt > 0) {
					throw new Exception("由于主计划尚未完成，二级计划暂时不能更改为完成！");
				}
			} else {
				sql = new StringBuffer();
				params = new ArrayList<Object>();
				sql.append(" SELECT COUNT(1)");
				sql.append(" FROM");
				sql.append(" V_PM_TASK_NEWPUBLISH");
				sql.append(" WHERE");
				sql.append(" PROGRAM_VEHICLE_ID = ?");
				sql.append(" AND PLAN_LEVEL = ?");
				sql.append(" AND PKG_PM_OBS.OBS_FN_GROUP_MGR(OBS_ID) IS NULL");
				params.add(programVehicleId);
				params.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
				if (this.jdbcTemplate.queryForInt(sql.toString(), params.toArray()) > 0) {
					throw new Exception("尚有专业领域未设置经理，暂时不能更改为完成！");
				}
			}
		}
		
//		// 二级计划更改为完成时需要做如下Check
//		if (PDMSCommon.isNotNull(obsId) &&
//				PDMSConstants.PROGRAM_STATUS_PLANNED.equals(planStatus)) {
//			// 取得专业领域经理
//			String sql = "SELECT PKG_PM_OBS.OBS_FN_GROUP_MGR(?) AS FM FROM DUAL";
//			Map<String, Object> result = this.jdbcTemplate.queryForMap(sql, obsId);
//			if (PDMSCommon.isNull(PDMSCommon.nvl(result.get("FM")))) {
//				throw new Exception("该专业领域尚未设置经理，暂时不能更改为完成！");
//			}
//			
//			// 任务负责人是否全部指定Check
//			if (this.getDeplannedTask(obsId) > 0) {
//				throw new Exception("计划中存在尚未分配负责人的任务，暂时不能更改为完成！");
//			}
//		// 主计划更改为完成时需要做如下Check
//		} else if (PDMSCommon.isNull(obsId) &&
//				PDMSConstants.PROGRAM_STATUS_PLANNED.equals(planStatus)) {
//			// 验证是否所有交付物都指定了负责主体
//			StringBuffer sql = new StringBuffer();
//			sql.append(" SELECT COUNT(*)");
//			sql.append(" FROM");
//			sql.append(" V_PM_DELIVERABLE_TASK");
//			sql.append(" WHERE");
//			sql.append(" PROGRAM_VEHICLE_ID = ?");
//			sql.append(" AND IS_FIT = ?");
//			sql.append(" AND OBS_ID IS NULL");
//			int cnt = this.jdbcTemplate.queryForInt(sql.toString(),
//					programVehicleId, PDMSConstants.STATUS_Y);
//			if (cnt > 0) {
//				throw new Exception("质量阀定义中存在没有指定负责主体的交付物，暂时不能更改为完成！");
//			}
//		}
		
		// 更改计划状态
		String fnObsId = "";
		if (PDMSCommon.isNotNull(obsId)) {
			fnObsId = obsId;
		} else {
			// 根据车型查询根节点组织
			sql = new StringBuffer();
			sql.append("SELECT PKG_PM_OBS.ROOT_OBS_ID(?) AS OBS_ID FROM DUAL");
			List<Map<String, Object>> obsList = this.jdbcTemplate.queryForList(
					sql.toString(), programVehicleId);
			if (obsList.size() > 0) {
				fnObsId = PDMSCommon.nvl(obsList.get(0).get("OBS_ID"));
			}
		}
		this.pkgPmDBProcedureServcie.updatePlanStatus(fnObsId, planStatus);
	}
	
	/**
	 * 计划变更
	 */
	public void changePlan(String programVehicleId, String obsId) throws Exception {
		String fnObsId = "";
		if (PDMSCommon.isNotNull(obsId)) {
			fnObsId = obsId;
		} else {
			// 根据车型查询根节点组织
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT PKG_PM_OBS.ROOT_OBS_ID(?) AS OBS_ID FROM DUAL");
			List<Map<String, Object>> obsList = this.jdbcTemplate.queryForList(
					sql.toString(), programVehicleId);
			if (obsList.size() > 0) {
				fnObsId = PDMSCommon.nvl(obsList.get(0).get("OBS_ID"));
			}
		}
		this.pkgPmDBProcedureServcie.updatePlanStatus(fnObsId,
				PDMSConstants.PROGRAM_STATUS_INACTIVE);
	}

	/**
	 * 项目计划取得
	 */
	public Page<PMProgramPlanVMEntity> getProgramPlanList(String programVehicleId,int pageSize,int pageNo) {
		String hql = "from PMProgramPlanVMEntity WHERE programVehicleId = ? order by lft";
		Page<PMProgramPlanVMEntity> page = new Page<PMProgramPlanVMEntity>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return this.pmProgramPlanVMDAO.findPage(page ,hql, programVehicleId);
	}
	
	/**
	 * 项目计划取得
	 */
	public Page<PMProgramPlanVMEntity> getDeptPlanList(String programVehicleId,int pageSize,int pageNo) {
		String hql = "from PMProgramPlanVMEntity WHERE programVehicleId = ? and planLevel = 2 order by lft";
		Page<PMProgramPlanVMEntity> page = new Page<PMProgramPlanVMEntity>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return this.pmProgramPlanVMDAO.findPage(page ,hql, programVehicleId);
	}
	
	/**
	 * 项目主计划取得
	 */
	public Map<String, Object> getMainPlanInfo(String programVehicleId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_PLAN");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND PLAN_LEVEL = 1");
		params.add(programVehicleId);
		List<Map<String, Object>> planList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (planList.size() > 0) {
			result = planList.get(0);
		} else {
			result = new HashMap<String, Object>();
		}
		return result;
	}
	
	/**
	 * 项目计划取得(专业领域计划)
	 */
	public List<PMDeptPlanVMEntity> getDeployPlan(
			String programId, String programVehicleId) {
		String hql = "FROM PMDeptPlanVMEntity"
				+ " WHERE programId = ?"
				+ " AND programVehicleId = ?";
		List<PMDeptPlanVMEntity> result =
				this.pmDeptPlanVMDAO.find(hql, programId, programVehicleId);
        return result;
	}
	
	/**
	 * 项目计划状态取得
	 */
	public String getPlanStatus(String programVehicleId, String obsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT STATUS_CODE");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_PLAN");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		paramList.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND OBS_ID = ?");
			paramList.add(obsId);
		} else {
			sql.append(" AND OBS_ID = PKG_PM_OBS.ROOT_OBS_ID(?)");
			paramList.add(programVehicleId);
		}
		List<Map<String, Object>> planMapList =
				this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
		if (planMapList.size() > 0) {
			Map<String, Object> planMap = planMapList.get(0);
			return PDMSCommon.nvl(planMap.get("STATUS_CODE"));
		}
		return PDMSConstants.PROGRAM_STATUS_INACTIVE;
	}
	
//	/**
//	 * 项目状态取得
//	 */
//	public String getProgramStatus(String programVehicleId) {
//		StringBuffer sql = new StringBuffer();
//		List<Object> paramList = new ArrayList<Object>();
//		
//		sql.append(" SELECT STATUS_CODE");
//		sql.append(" FROM");
//		sql.append(" V_PM_PROGRAM_VEHICLE");
//		sql.append(" WHERE");
//		sql.append(" ID = ?");
//		paramList.add(programVehicleId);
//		
//		List<Map<String, Object>> List =
//				this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
//		if (List.size() > 0) {
//			Map<String, Object> map = List.get(0);
//			return PDMSCommon.nvl(map.get("STATUS_CODE"));
//		}
//		return "";
//	}
	
//	/**
//	 * 项目状态取得（全部）
//	 */
//	public List<Map<String, Object>> getProgramStatusList(String programId) {
//		StringBuffer sql = new StringBuffer();
//		List<Object> paramList = new ArrayList<Object>();
//		
//		sql.append(" SELECT ID, STATUS_CODE");
//		sql.append(" FROM");
//		sql.append(" V_PM_PROGRAM_VEHICLE");
//		sql.append(" WHERE");
//		sql.append(" PROGRAM_ID = ?");
//		paramList.add(programId);
//		
//		List<Map<String, Object>> result =
//				this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
//		return result;
//	}
	
	public boolean getProgramEditStatusCode(String programId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" select COUNT(*) from V_PM_PROGRAM_VEHICLE where PROGRAM_ID = ? AND STATUS_CODE = 'ACTIVE' ");
		paramList.add(programId);
		
		int result = jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
		if(result > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 导出模板
	 */
	public void exportProjectTemplate(String programVehicleId, Map<String, String> model) {
		// 导出模板
		this.pkgPmTemplateDBProcedureServcie.saveAsTemplate(
				programVehicleId, model.get("templateCode"), model.get("templateName"));
	}
	
	/**
	 * 导入模板验证
	 */
	public Map<String, Object> importPlanTemplateCheck(String programVehicleId) throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		
		// 验证是否存在计划节点
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_LIST");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE <> ?");
		params.add(programVehicleId);
		params.add(PDMSConstants.TASK_TYPE_SOP_NODE);
		int taskCnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
		if (taskCnt > 0) {
			throw new Exception("项目计划已经存在，不允许重新导入模板！");
		}
		
		// 验证是否存在项目组织(根节点组织除外)
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_OBS");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND PARENT_ID IS NOT NULL");
		params.add(programVehicleId);
		int obsCnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
		if (obsCnt > 0) {
			throw new Exception("项目组织已经存在，不允许重新导入模板！");
		}
		
		// 取得SOP时间
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_LIST");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE = ?");
		params.add(programVehicleId);
		params.add(PDMSConstants.TASK_TYPE_SOP_NODE);
		List<Map<String, Object>> sopList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (sopList.size() > 0) {
			return sopList.get(0);
		}
		return new HashMap<String, Object>();
	}
	
	/**
	 * 任务责任主体未指定数据取得
	 */
	private int getNoObsTask(String programVehicleId, String obsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_LIST");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		paramList.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FUNCTION_OBS_ID = ?");
			paramList.add(obsId);
			sql.append(" AND PLAN_LEVEL = ?");
			paramList.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
		} else {
			sql.append(" AND PLAN_LEVEL = ?");
			paramList.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
		}
		
		sql.append(" AND TASK_TYPE_CODE = ?");
		sql.append(" AND OBS_ID IS NULL");
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
		return this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
	}

	/**
	 * 任务负责人未指定数据取得
	 */
	private int getNoOwnerTask(String programVehicleId, String obsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_LIST");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		paramList.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FUNCTION_OBS_ID = ?");
			paramList.add(obsId);
			sql.append(" AND PLAN_LEVEL = ?");
			paramList.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
		} else {
			sql.append(" AND PLAN_LEVEL = ?");
			paramList.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
		}
		
		sql.append(" AND TASK_TYPE_CODE = ?");
		sql.append(" AND TASK_OWNER IS NULL");
		paramList.add(PDMSConstants.TASK_TYPE_NODE);
		return this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
	}
	
//	/**
//	 * 项目计划INACTIVE数据取得
//	 */
//	private int getInactivePlan(String programVehicleId) {
//		StringBuffer sql = new StringBuffer();
//		List<Object> paramList = new ArrayList<Object>();
//		
//		sql.append(" SELECT COUNT(*)");
//		sql.append(" FROM");
//		sql.append(" V_PM_PROGRAM_PLAN");
//		sql.append(" WHERE");
//		sql.append(" PROGRAM_VEHICLE_ID = ?");
//		sql.append(" AND STATUS_CODE = ?");
//		paramList.add(programVehicleId);
//		paramList.add(PDMSConstants.PROGRAM_STATUS_INACTIVE);
//		return this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
//	}
	
	/**
	 * 更改专业经理
	 */
	public void updateFuncitonManager(String obsId, String userId) throws Exception {
		// 设定专业经理
		this.pkgPmObsDBProcedureServcie.assignFmMgr(obsId, userId);
	}
}