package com.gnomon.pdms.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMDeliverableCheckitemDAO;
import com.gnomon.pdms.dao.PMDeliverableDAO;
import com.gnomon.pdms.dao.PMDeliverableTaskVMDAO;
import com.gnomon.pdms.dao.PMFunctionTaskDAO;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMObsVMDAO;
import com.gnomon.pdms.dao.PMPreTaskDAO;
import com.gnomon.pdms.dao.PMTaskDAO;
import com.gnomon.pdms.dao.PMTaskListVMDAO;
import com.gnomon.pdms.dao.PMTaskProgramVehicleDAO;
import com.gnomon.pdms.dao.PMTaskValveVMDAO;
import com.gnomon.pdms.dao.PreTaskVMDAO;
import com.gnomon.pdms.entity.PMDeliverableCheckitemEntity;
import com.gnomon.pdms.entity.PMDeliverableEntity;
import com.gnomon.pdms.entity.PMFunctionTaskEntity;
import com.gnomon.pdms.entity.PMPreTaskEntity;
import com.gnomon.pdms.entity.PMTaskEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;
import com.gnomon.pdms.procedure.SpResequenceDBProcedureServcie;

@Service
@Transactional
public class DeptPlanService {

	@Autowired
	private PMObsVMDAO pmObsVMDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO;

	@Autowired
	private PMTaskDAO pmTaskDAO;

	@Autowired
	private PreTaskVMDAO preTaskVMDAO;
	
	@Autowired
	private PMDeliverableTaskVMDAO pmDeliverableTaskVMDAO;
	
	@Autowired
	private PMTaskValveVMDAO pmTaskValveVMDAO;
	
	@Autowired
	private PMTaskProgramVehicleDAO pmTaskProgramVehicleDAO;
	
	@Autowired
	private PMPreTaskDAO pmPreTaskDAO;

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private PMTaskListVMDAO pmTaskListVMDAO;
	
	@Autowired
	private PMFunctionTaskDAO pmFunctionTaskDAO;

	@Autowired
	private PMDeliverableDAO pmDeliverableDAO;
	
	@Autowired
	private PMDeliverableCheckitemDAO pmDeliverableCheckitemDAO;
	
	@Autowired
	private SysCodeSequenceService sysCodeSequenceService;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	@Autowired
	private SpResequenceDBProcedureServcie spResequenceDBProcedureServcie;
	
	@Autowired
	private ProgramPlanImportManager programPlanImportManager;
	
	/**
	 * 专业领域/专业组 一览信息取得
	 */
	public List<Map<String, Object>> getDeptList(
			String programVehicleId, String planId, String obsId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_FUNCTION_PLAN_BL");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_FUNCTION_PLAN");
			sql.append(" WHERE");
		}
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		sql.append(" AND (PLAN_ID = ?");
		params.add(planId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" OR PLAN_LEVEL = 1");
			sql.append(" AND RESP_OBS_ID = ?");
			params.add(obsId);
		}
		sql.append(" )");
		sql.append(" ORDER BY");
		sql.append(" PLAN_LEVEL, SEQ_NO");
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

	/**
	 * 时程表设置-节点信息取得
	 */
	public GTPage<Map<String, Object>> getDeptPlanTaskList(
			String functionId, String baselineId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_NODE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_NODE");
			sql.append(" WHERE");
		}
		sql.append(" FUNCTION_ID = ?");
		params.add(functionId);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if ("true".equals(searchModel.get("searchTaskTypeNode")) &&
					! "true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE = ?");
				params.add(PDMSConstants.TASK_TYPE_NODE);
			} else if (! "true".equals(searchModel.get("searchTaskTypeNode")) &&
					"true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE = ?");
				params.add(PDMSConstants.TASK_TYPE_ACTIVITY);
			} else if (! "true".equals(searchModel.get("searchTaskTypeNode")) &&
					! "true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE IS NULL");
			}

			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateTo"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
			// 负责人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(TASK_OWNER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 负责人为空
			if ("true".equals(searchModel.get("searchTaskOwnerNull"))) {
				sql.append(" AND TASK_OWNER IS NULL");
			}
		}
		sql.append(" ORDER BY OBS_NAME, PLANNED_FINISH_DATE");
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 节点计划-节点信息取得
	 */
	public GTPage<Map<String, Object>> getDeptPlanTaskList(
			String programVehicleId, String obsId, String baselineId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
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
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FUNCTION_GROUP_ID = ?");
			params.add(obsId);
			sql.append(" AND PLAN_LEVEL = 2");
		} else {
			sql.append(" AND PLAN_LEVEL = 1");
		}
		sql.append(" AND TASK_TYPE_CODE IN (?, ?)");
		params.add(PDMSConstants.TASK_TYPE_NODE);
		params.add(PDMSConstants.TASK_TYPE_ACTIVITY);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if ("true".equals(searchModel.get("searchTaskTypeNode")) &&
					! "true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE = ?");
				params.add(PDMSConstants.TASK_TYPE_NODE);
			} else if (! "true".equals(searchModel.get("searchTaskTypeNode")) &&
					"true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE = ?");
				params.add(PDMSConstants.TASK_TYPE_ACTIVITY);
			} else if (! "true".equals(searchModel.get("searchTaskTypeNode")) &&
					! "true".equals(searchModel.get("searchTaskTypeActivity"))) {
				sql.append(" AND TASK_TYPE_CODE IS NULL");
			}

			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateTo"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
			// 负责主体
			if (PDMSCommon.isNotNull(searchModel.get("searchObsId"))) {
				sql.append(" AND OBS_ID = ?");
				params.add(searchModel.get("searchObsId"));
			}
			// 负责人
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskOwner"))) {
				sql.append(" AND UPPER(TASK_OWNER_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskOwner") + "%");
			}
			// 负责人为空
			if ("true".equals(searchModel.get("searchTaskOwnerNull"))) {
				sql.append(" AND TASK_OWNER IS NULL");
			}
		}
		sql.append(" ORDER BY OBS_NAME, PLANNED_FINISH_DATE");

        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}

	/**
	 * 节点信息新增
	 */
	public void addTask(String programId, String programVehicleId, String obsId,
			String functionId, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 开始时间、截止时间、周期（天）
		Map<String, Object> calculation = this.autoCalculate(model);
		
		// 保存Task数据
		PMTaskEntity task = new PMTaskEntity();
		String taskId = PDMSCommon.generateUUID();
		task.setId(taskId);
		task.setTaskCode(this.programPlanImportManager.generateTaskCode(
				programVehicleId, model.get("respObsId")));
		task.setTaskName(model.get("taskName"));
		task.setTaskOwner(model.get("taskOwner"));
		task.setProgramId(programId);
		task.setObsId(model.get("respObsId"));
		task.setPlannedStartDate((Date)calculation.get("stDate"));
		task.setPlannedFinishDate((Date)calculation.get("edDate"));
		task.setDurationDays(new Long(PDMSCommon.nvl(calculation.get("duration"), "0")));
		task.setTaskStatusCode(PDMSConstants.TASK_STATUS_NOT_START);
		task.setTaskTypeCode(model.get("taskTypeCode"));
		task.setPlanLevel(PDMSCommon.isNotNull(obsId) ?
				PDMSConstants.PROGRAM_PLAN_LEVEL_2.intValue() :
					PDMSConstants.PROGRAM_PLAN_LEVEL_1.intValue());
		task.setProgramVehicleId(programVehicleId);
		if (PDMSConstants.TASK_TYPE_NODE.equals(model.get("taskTypeCode"))) {
			if (PDMSCommon.isNotNull(model.get("deliverableName"))) {
				task.setGateId(model.get("gateId"));
			}
			task.setRelationTaskId(model.get("relationTaskId"));
			task.setParentId(model.get("parentId"));
			task.setTitleDispLocationCode(model.get("titleDispLocation"));
		}
		task.setCreateBy(loginUser);
		task.setCreateDate(new Date());
		this.pmTaskDAO.save(task);
		
		// 新建关联交付物信息
		if (PDMSConstants.TASK_TYPE_NODE.equals(model.get("taskTypeCode"))) {
			if (PDMSCommon.isNotNull(model.get("deliverableName"))) {
				// 关联标准交付物
				if (PDMSCommon.isNotNull(model.get("deliverableId"))) {
					PMDeliverableEntity deliverable =
							this.pmDeliverableDAO.get(model.get("deliverableId"));
					deliverable.setRespTaskId(taskId);
					this.pmDeliverableDAO.save(deliverable);
				} else {
					// 交付物信息
					String deliverableId = PDMSCommon.generateUUID();
					PMDeliverableEntity deliverable = new PMDeliverableEntity();
					deliverable.setId(deliverableId);
					deliverable.setCode(this.programPlanImportManager.generateDelvCode(
							programVehicleId, model.get("respObsId"), model.get("gateId")));
					deliverable.setName(model.get("deliverableName"));
					deliverable.setType(PDMSCommon.nvl(task.getPlanLevel()));
					deliverable.setObsId(model.get("respObsId"));
					deliverable.setTaskId(model.get("gateId"));
					deliverable.setUserid(model.get("taskOwner"));
					deliverable.setSubmitDate((Date)calculation.get("edDate"));
					deliverable.setIsFit(PDMSConstants.STATUS_Y);
					deliverable.setTaskProgressStatus(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
					deliverable.setProgramId(programId);
					deliverable.setProgramVehicleId(programVehicleId);
					deliverable.setRespTaskId(taskId);
					if ("true".equals(model.get("isKey"))) {
						deliverable.setIsKey(PDMSConstants.STATUS_Y);
					} else {
						deliverable.setIsKey(PDMSConstants.STATUS_N);
					}
					deliverable.setCreateBy(loginUser);
					deliverable.setCreateDate(new Date());
					deliverable.setIsStandard(PDMSConstants.STATUS_N);
					this.pmDeliverableDAO.save(deliverable);
					// 交付物检查项信息
					PMDeliverableCheckitemEntity checkItem = new PMDeliverableCheckitemEntity();
					checkItem.setId(PDMSCommon.generateUUID());
					checkItem.setSeq(new Long(1));
					checkItem.setName(model.get("checkitemName"));
					checkItem.setCheckRequirement(model.get("checkRequirement"));
					checkItem.setDeliverableId(deliverableId);
					checkItem.setCreateBy(loginUser);
					checkItem.setCreateDate(new Date());
					this.pmDeliverableCheckitemDAO.save(checkItem);
				}
			}
		}

		// 保存泳道信息
		if (PDMSCommon.isNotNull(functionId)) {
			// 保存FunctionTask数据
			PMFunctionTaskEntity fnTask = new PMFunctionTaskEntity();
			fnTask.setFunctionId(functionId);
			fnTask.setTaskId(taskId);
			fnTask.setIsVisible(PDMSConstants.STATUS_Y);
			this.pmFunctionTaskDAO.save(fnTask);
		}

		// 排序
		this.spResequenceDBProcedureServcie.resequencePmTask(programId, programVehicleId);
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(programVehicleId, loginUser);
	}
	
	/**
	 * 节点信息修改
	 */
	public void saveTaskDetail(String taskId, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 开始时间、截止时间、周期（天）
		Map<String, Object> calculation = this.autoCalculate(model);
		
		// 时间验证
		this.pkgPmDBProcedureServcie.pmTaskValidator(taskId,
				(Date)calculation.get("stDate"), (Date)calculation.get("edDate"));
		
		// 更新节点信息
		PMTaskEntity task = this.pmTaskDAO.get(taskId);
		task.setTaskName(model.get("taskName"));
		task.setTaskOwner(model.get("taskOwner"));
		task.setObsId(model.get("respObsId"));
		task.setPlannedStartDate((Date)calculation.get("stDate"));
		task.setPlannedFinishDate((Date)calculation.get("edDate"));
		task.setDurationDays(new Long(PDMSCommon.nvl(calculation.get("duration"), "0")));
		if (PDMSCommon.isNotNull(model.get("deliverableName"))) {
			task.setGateId(model.get("gateId"));
		} else {
			task.setGateId(null);
		}
		task.setRelationTaskId(model.get("relationTaskId"));
		task.setParentId(model.get("parentId"));
		task.setTitleDispLocationCode(model.get("titleDispLocation"));
		task.setUpdateBy(loginUser);
		task.setUpdateDate(new Date());
		this.pmTaskDAO.save(task);
		
		// 更新关联交付物信息
		if (PDMSConstants.TASK_TYPE_NODE.equals(model.get("taskTypeCode"))) {
			PMDeliverableEntity srcDeliverable = new PMDeliverableEntity();
			if (PDMSCommon.isNotNull(model.get("srcDeliverableId"))) {
				// 取得原有交付物信息
				srcDeliverable = this.pmDeliverableDAO.get(model.get("srcDeliverableId"));
				if (! srcDeliverable.getName().equals(model.get("deliverableName"))) {
					if (PDMSConstants.STATUS_Y.equals(srcDeliverable.getIsStandard())) {
						// 标准交付物设为不适用
						srcDeliverable.setIsFit(PDMSConstants.STATUS_N);
						srcDeliverable.setRespTaskId(null);
						this.pmDeliverableDAO.save(srcDeliverable);
					} else {
						// 交付物名称变更则删除原有交付物信息
						List<PMDeliverableCheckitemEntity> checkItemList =
								this.pmDeliverableCheckitemDAO.findBy(
										"deliverableId", model.get("srcDeliverableId"));
						for (PMDeliverableCheckitemEntity checkItem : checkItemList) {
							this.pmDeliverableCheckitemDAO.delete(checkItem.getId());
						}
						// 删除原有交付物
						this.pmDeliverableDAO.delete(model.get("srcDeliverableId"));
					}
				}
			}
			// 如果交付物信息存在
			if (PDMSCommon.isNotNull(model.get("deliverableName"))) {
				// 关联标准交付物
				if (PDMSCommon.isNotNull(model.get("deliverableId"))) {
					PMDeliverableEntity deliverable =
							this.pmDeliverableDAO.get(model.get("deliverableId"));
					deliverable.setRespTaskId(taskId);
					this.pmDeliverableDAO.save(deliverable);
				} else {
					// 如果与原有交付物不相同，则新建
					if (! model.get("deliverableName").equals(srcDeliverable.getName())) {
						// 交付物信息
						String deliverableId = PDMSCommon.generateUUID();
						PMDeliverableEntity deliverable = new PMDeliverableEntity();
						deliverable.setId(deliverableId);
						deliverable.setCode(this.programPlanImportManager.generateDelvCode(
								task.getProgramVehicleId(), model.get("respObsId"), model.get("gateId")));
						deliverable.setName(model.get("deliverableName"));
						deliverable.setType(PDMSCommon.nvl(task.getPlanLevel()));
						deliverable.setObsId(model.get("respObsId"));
						deliverable.setTaskId(model.get("gateId"));
						deliverable.setUserid(model.get("taskOwner"));
						deliverable.setSubmitDate((Date)calculation.get("edDate"));
						deliverable.setIsFit(PDMSConstants.STATUS_Y);
						deliverable.setTaskProgressStatus(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
						deliverable.setProgramId(task.getProgramId());
						deliverable.setProgramVehicleId(task.getProgramVehicleId());
						deliverable.setRespTaskId(taskId);
						if ("true".equals(model.get("isKey"))) {
							deliverable.setIsKey(PDMSConstants.STATUS_Y);
						} else {
							deliverable.setIsKey(PDMSConstants.STATUS_N);
						}
						deliverable.setCreateBy(loginUser);
						deliverable.setCreateDate(new Date());
						deliverable.setIsStandard(PDMSConstants.STATUS_N);
						this.pmDeliverableDAO.save(deliverable);
						// 交付物检查项信息
						PMDeliverableCheckitemEntity checkItem = new PMDeliverableCheckitemEntity();
						checkItem.setId(PDMSCommon.generateUUID());
						checkItem.setSeq(new Long(1));
						checkItem.setName(model.get("checkitemName"));
						checkItem.setCheckRequirement(model.get("checkRequirement"));
						checkItem.setDeliverableId(deliverableId);
						checkItem.setCreateBy(loginUser);
						checkItem.setCreateDate(new Date());
						this.pmDeliverableCheckitemDAO.save(checkItem);
					// 如果与原有交付物相同，则修改
					} else {
						// 交付物信息
						srcDeliverable.setObsId(model.get("respObsId"));
						srcDeliverable.setTaskId(model.get("gateId"));
						srcDeliverable.setUserid(model.get("taskOwner"));
						srcDeliverable.setSubmitDate((Date)calculation.get("edDate"));
						if ("true".equals(model.get("isKey"))) {
							srcDeliverable.setIsKey(PDMSConstants.STATUS_Y);
						} else {
							srcDeliverable.setIsKey(PDMSConstants.STATUS_N);
						}
						srcDeliverable.setUpdateBy(loginUser);
						srcDeliverable.setUpdateDate(new Date());
						this.pmDeliverableDAO.save(srcDeliverable);
						// 交付物检查项信息
						List<PMDeliverableCheckitemEntity> checkItemList =
								this.pmDeliverableCheckitemDAO.findBy(
										"deliverableId", srcDeliverable.getId());
						for (PMDeliverableCheckitemEntity checkItem : checkItemList) {
							checkItem.setName(model.get("checkitemName"));
							checkItem.setCheckRequirement(model.get("checkRequirement"));
							checkItem.setUpdateBy(loginUser);
							checkItem.setUpdateDate(new Date());
							this.pmDeliverableCheckitemDAO.save(checkItem);
						}
					}
				}
			}
		}
		// 更新计划开始、结束时间
		this.pkgPmDBProcedureServcie.updateVehiclePlanDate(task.getProgramVehicleId(), loginUser);
	}
	
	/**
	 * 节点信息引用
	 */
	public String importTask(final String functionId, List<Map<String, String>> taskList) {
		StringBuffer result = new StringBuffer();
		StringBuffer sql = null;
		
		// Function+Task重复Check
		List<Map<String, String>> newTaskList = new ArrayList<Map<String, String>>();
		for (Map<String, String> task : taskList) {
			sql = new StringBuffer();
			sql.append(" SELECT DISTINCT");
			sql.append(" ID");
			sql.append(",TASK_NAME");
			sql.append(" FROM");
			sql.append(" V_PM_TASK_NODE");
			sql.append(" WHERE");
			sql.append(" FUNCTION_ID = ?");
			sql.append(" AND ID = ?");
			List<Map<String, Object>> oldTaskList = this.jdbcTemplate.queryForList(
					sql.toString(), functionId, task.get("id"));
			if (oldTaskList == null || oldTaskList.size() == 0) {
				newTaskList.add(task);
			} else {
				for (Map<String, Object> oldTask : oldTaskList) {
					if (result.length() > 0) {
						result.append(",");
					}
					result.append(oldTask.get("TASK_NAME"));
				}
			}
		}
		
		if (newTaskList.size() == 0) {
			return result.toString();
		}
		
		final List<Map<String, String>> updTaskList = newTaskList;
		
		// 登录节点
		sql = new StringBuffer();
		sql.append(" INSERT INTO PM_FUNCTION_TASK(");
		sql.append(" FUNCTION_ID");
		sql.append(",TASK_ID");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?)");
		
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return updTaskList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, String> task = updTaskList.get(i);
            	ps.setString(1, functionId);
            	ps.setString(2, task.get("id"));
            }
        });
		
		return result.toString();
	}
	
	/**
	 * 节点信息删除
	 */
	public void deleteTask(String programVehicleId, String functionId, String taskId) {
		List<Object> params = null;
		StringBuffer sql = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		/*
		 * 时程表设置则删除泳道关系、节点计划则删除任务以及对应泳道关系
		 */
		if (PDMSCommon.isNotNull(functionId)) {
			// 删除FunctionTask信息
			sql = new StringBuffer();
			params = new ArrayList<Object>();
			sql.append(" DELETE FROM PM_FUNCTION_TASK");
			sql.append(" WHERE");
			sql.append(" FUNCTION_ID = ?");
			sql.append(" AND TASK_ID = ?");
			params.add(functionId);
			params.add(taskId);
			jdbcTemplate.update(sql.toString(), params.toArray());
		} else {
			// 删除节点
			this.pkgPmDBProcedureServcie.deleteTask(
					programVehicleId, taskId, loginUser);
		}
	}
	
	/**
	 * 节点信息批量删除
	 */
	public void batchDeleteTask(String programVehicleId, String functionId,
			List<Map<String, String>> modelList) {
		for (Map<String, String> model : modelList) {
			this.deleteTask(programVehicleId, functionId, model.get("id"));
		}
	}
	
	/**
	 * 更改显示在时程表
	 */
	public void updateVisible(String functionId, String taskId, boolean isVisible) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_FUNCTION_TASK SET");
		sql.append(" IS_VISIBLE = ?");
		sql.append(" WHERE");
		sql.append(" FUNCTION_ID = ?");
		sql.append(" AND TASK_ID = ?");
		List<Object> params = new ArrayList<Object>();
		if (isVisible) {
			params.add(PDMSConstants.STATUS_Y);
		} else {
			params.add(PDMSConstants.STATUS_N);
		}
		params.add(functionId);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 节点信息取得
	 */
	public Map<String, Object> getTaskDetail(
			String taskId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_TASK_NODE_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_TASK_NODE");
			sql.append(" WHERE");
		}
		sql.append(" ID = ?");
		params.add(taskId);
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (list.size() > 0) {
			return list.get(0);
		}
        return new HashMap<String, Object>();
	}

	/**
	 * 前置节点信息取得
	 */
	public List<Map<String, Object>> getPreTaskList(String taskId,
			String baselineId, String preType) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_PRE_TASK_BASELINE");
			sql.append(" WHERE");
			sql.append(" BASELINE_ID = ? AND");
			params.add(baselineId);
		} else {
			sql.append(" V_PM_PRE_TASK");
			sql.append(" WHERE");
		}
		if (PDMSConstants.PRE_TASK_TYPE_POST.equals(preType)) {
			sql.append(" PRE_TASK_ID = ?");
		} else {
			sql.append(" TASK_ID = ?");
		}
		params.add(taskId);

        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}

	/**
	 * 前置节点增加
	 */
	public void insertPreTask(String taskId,String preTaskId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		PMPreTaskEntity pmPreTaskEntity = new PMPreTaskEntity();
		pmPreTaskEntity.setId(PDMSCommon.generateUUID());
		pmPreTaskEntity.setTaskId(taskId);
		pmPreTaskEntity.setPreTaskId(preTaskId);
		pmPreTaskEntity.setPreTaskTypeCode(PDMSConstants.PRE_TASK_TYPE_FS);
		pmPreTaskEntity.setCreateBy(loginUser);
		pmPreTaskEntity.setCreateDate(new Date());
		pmPreTaskDAO.save(pmPreTaskEntity);
	}
	
	/**
	 * 前置节点删除
	 */
	public void deletePreTask(String preId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		List<Object> params = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_PRE_TASK SET");
		sql.append(" DELETE_BY = ?");
		sql.append(",DELETE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(preId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 更新负责人(批量)
	 */
	public void updateTaskOwner(String taskOwner, List<Map<String, String>> modelList) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新节点
		for (Map<String, String> model : modelList) {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE PM_TASK SET");
			sql.append(" TASK_OWNER = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(taskOwner);
			params.add(loginUser);
			params.add(model.get("id"));
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	}
	
	/**
	 * 阀门交付物列表取得
	 */
	public GTPage<Map<String, Object>> getDeliverableList(
			String programVehicleId, String obsId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT T1.*");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE_TASK T1");
		sql.append(" WHERE");
		sql.append(" T1.PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND T1.IS_FIT = ?");
		sql.append(" AND T1.REF_TASK_ID IS NULL");
		sql.append(" AND T1.OBS_ID IN(");
		sql.append(" SELECT FUNCTION_GROUP_ID");
		sql.append(" FROM");
		sql.append(" PM_OBS");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND ID = ?");
		sql.append(" AND DELETE_BY IS NULL)");
		params.add(programVehicleId);
		params.add(PDMSConstants.STATUS_Y);
		params.add(programVehicleId);
		params.add(obsId);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchDeliverableName"))) {
				sql.append(" AND UPPER(NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchDeliverableName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
		}
		sql.append(" ORDER BY TASK_NAME DESC");
		
        return this.jdbcTemplate.queryPagination(sql.toString(),
				pageNo, pageSize, params.toArray());
	}
	
	/**
	 * 节点列表取得
	 */
	public GTPage<Map<String, Object>> getTaskNodeList(
			String programVehicleId, String obsId, boolean relationFlag,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_VEHICLE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND TASK_TYPE_CODE IN(?, ?)");
		params.add(programVehicleId);
		params.add(PDMSConstants.TASK_TYPE_NODE);
		if (relationFlag) {
			params.add(PDMSConstants.TASK_TYPE_MAIN_NODE);
		} else {
			params.add(PDMSConstants.TASK_TYPE_ACTIVITY);
		}
		if (PDMSCommon.isNotNull(obsId)) {
			sql.append(" AND FUNCTION_GROUP_ID = ?");
			params.add(obsId);
		}
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchObsName"))) {
				sql.append(" AND UPPER(OBS_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchObsName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchFnGroupId"))) {
				sql.append(" AND FUNCTION_GROUP_ID = ?");
				params.add(searchModel.get("searchFnGroupId"));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
			// 主计划、二级计划
			List<Long> subParams = new ArrayList<Long>();
			if ("true".equals(searchModel.get("searchPlanLevel_1"))) {
				subParams.add(PDMSConstants.PROGRAM_PLAN_LEVEL_1);
			}
			if ("true".equals(searchModel.get("searchPlanLevel_2"))) {
				subParams.add(PDMSConstants.PROGRAM_PLAN_LEVEL_2);
			}
			if (subParams.size() > 0) {
				sql.append(" AND PLAN_LEVEL IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND PLAN_LEVEL IS NULL");
			}
			// 所属阀门
			if (PDMSCommon.isNotNull(searchModel.get("searchGateName"))) {
				sql.append(" AND UPPER(RESP_GATE_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchGateName") + "%");
			}
		}
		sql.append(" ORDER BY FUNCTION_GROUP_NAME, OBS_NAME, PLANNED_FINISH_DATE");
		
		return this.jdbcTemplate.queryPagination(sql.toString(),
				pageNo, pageSize, params.toArray());
	}
	
	/**
	 * 活动列表取得
	 */
	public GTPage<Map<String, Object>> getActivityList(String taskId, String functionId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_NODE");
		sql.append(" WHERE");
		sql.append(" TASK_TYPE_CODE = ?");
		params.add(PDMSConstants.TASK_TYPE_ACTIVITY);
		
		if (PDMSCommon.isNotNull(functionId)) {
			sql.append(" AND FUNCTION_ID = ?");
			params.add(functionId);
		} else if (PDMSCommon.isNotNull(taskId)) {
			sql.append(" AND FUNCTION_ID IN (");
			sql.append(" SELECT FUNCTION_ID");
			sql.append(" FROM PM_FUNCTION_TASK");
			sql.append(" WHERE TASK_ID = ?)");
			params.add(taskId);
		} else {
			// 查询参数为空，则不现实活动列表
			sql.append(" AND 1 = 0");
		}
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchTaskName"))) {
				sql.append(" AND UPPER(TASK_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchTaskName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPSDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_START_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPSDateTo"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateFrom"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') >= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateFrom"))));
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchPFDateTo"))) {
				sql.append(" AND TO_CHAR(PLANNED_FINISH_DATE, 'YYYY-MM-DD') <= ?");
				params.add(DateUtils.formate(
						DateUtils.strToDate(searchModel.get("searchPFDateTo"))));
			}
		}
		
		return this.jdbcTemplate.queryPagination(sql.toString(),
				pageNo, pageSize, params.toArray());
	}
	
	/**
	 * 新建泳道
	 */
	public void createFunction(String programId, String programVehicleId,
			String planId, Map<String, String> model) {
		List<Object> params = null;
		StringBuffer sql = null;
		
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" INSERT INTO PM_FUNCTION(");
		sql.append(" ID");
		sql.append(",PROGRAM_ID");
		sql.append(",PROGRAM_VEHICLE_ID");
		sql.append(",DISPLAY_NAME");
		sql.append(",SEQ_NO");
		sql.append(",PLAN_ID");
		sql.append(",IS_VISIBLE");
		sql.append(",RESP_OBS_ID");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?)");
		params.add(PDMSCommon.generateUUID());
		params.add(programId);
		params.add(programVehicleId);
		params.add(model.get("functionName"));
		params.add(model.get("seqNo"));
		params.add(planId);
		if ("true".equals(model.get("chkIsVisible"))) {
			params.add(PDMSConstants.STATUS_Y);
		} else {
			params.add(PDMSConstants.STATUS_N);
		}
		params.add(model.get("respObsId"));
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 删除泳道
	 */
	public void deleteFunction(String functionId) {
		this.pkgPmDBProcedureServcie.removePvFunction(functionId);
	}
	
	/**
	 * 修改泳道
	 */
	public void updateFunction(String functionId, Map<String, String> model) {
		List<Object> params = null;
		StringBuffer sql = null;
		
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_FUNCTION SET");
		sql.append(" DISPLAY_NAME = ?");
		sql.append(",SEQ_NO = ?");
		sql.append(",IS_VISIBLE = ?");
		sql.append(",RESP_OBS_ID = ?");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(model.get("functionName"));
		params.add(model.get("seqNo"));
		if ("true".equals(model.get("chkIsVisible"))) {
			params.add(PDMSConstants.STATUS_Y);
		} else {
			params.add(PDMSConstants.STATUS_N);
		}
		params.add(model.get("respObsId"));
		params.add(functionId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
//		// 修改对应任务的责任组ID
//		sql = new StringBuffer();
//		params = new ArrayList<Object>();
//		sql.append(" UPDATE PM_TASK SET");
//		if (PDMSCommon.isNotNull(model.get("respObsId"))) {
//			sql.append(" OBS_ID = ?");
//		} else {
//			sql.append(" OBS_ID = NULL");
//		}
//		sql.append(" WHERE");
//		sql.append(" ID IN (");
//		sql.append(" SELECT TASK_ID FROM PM_FUNCTION_TASK");
//		sql.append(" WHERE FUNCTION_ID = ?)");
//		if (PDMSCommon.isNotNull(model.get("respObsId"))) {
//			params.add(model.get("respObsId"));
//		}
//		params.add(functionId);
//		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 拖动泳道排序
	 */
	public void resequenceFunction(String programVehicleId, String planId,
			String functionId, String overSeqNo, String dropPosition) {
		List<Object> params = null;
		StringBuffer sql = null;
		
		// 更改其他泳道排序
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_FUNCTION SET");
		sql.append(" SEQ_NO = NVL(SEQ_NO, 0) + 1");
		sql.append(" WHERE");
		sql.append(" ID IN(");
			sql.append(" SELECT FUNCTION_ID");
			sql.append(" FROM");
			sql.append(" V_PM_FUNCTION_PLAN");
			sql.append(" WHERE");
			sql.append(" PROGRAM_VEHICLE_ID = ?");
			params.add(programVehicleId);
			sql.append(" AND PLAN_ID = ?");
			params.add(planId);
		sql.append(")");
		if ("after".equals(dropPosition)) {
			sql.append(" AND SEQ_NO > ?");
		} else {
			sql.append(" AND SEQ_NO >= ?");
		}
		params.add(overSeqNo);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更改移动对象
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_FUNCTION SET");
		sql.append(" SEQ_NO = ?");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		if ("after".equals(dropPosition)) {
			params.add(PDMSCommon.toInt(overSeqNo) + 1);
		} else {
			params.add(overSeqNo);
		}
		params.add(functionId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 取得泳道信息
	 */
	public Map<String, Object> getFunctionInfo(String functionId) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_FUNCTION_PLAN");
		sql.append(" WHERE");
		sql.append(" FUNCTION_ID = ?");
		params.add(functionId);
		List<Map<String, Object>> functionList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (functionList.size() > 0) {
			result = functionList.get(0);
		} else {
			result = new HashMap<String, Object>();
		}
		return result;
	}
	
	/**
	 * 开始时间、截止时间以及周期的计算
	 */
	private Map<String, Object> autoCalculate(Map<String, String> model) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (PDMSConstants.TASK_TYPE_ACTIVITY.equals(model.get("taskTypeCode"))) {
			if (PDMSCommon.isNotNull(model.get("durationDays")) &&
					Integer.parseInt(model.get("durationDays")) > 0 &&
					PDMSCommon.isNotNull(model.get("plannedStartDate"))) {
				// 计算截止时间
				Date plannedFinishDate = DateUtils.addDays(
						DateUtils.strToDate(model.get("plannedStartDate")),
						Integer.parseInt(model.get("durationDays")) - 1);
				params.put("stDate", DateUtils.strToDate(model.get("plannedStartDate")));
				params.put("edDate", plannedFinishDate);
				params.put("duration", model.get("durationDays"));
			} else if (PDMSCommon.isNotNull(model.get("durationDays")) &&
					Integer.parseInt(model.get("durationDays")) > 0 &&
					PDMSCommon.isNotNull(model.get("plannedFinishDate"))) {
				// 计算开始时间
				Date plannedStartDate = DateUtils.addDays(
						DateUtils.strToDate(model.get("plannedFinishDate")),
						-Integer.parseInt(model.get("durationDays")) + 1);
				params.put("stDate", plannedStartDate);
				params.put("edDate", DateUtils.strToDate(model.get("plannedFinishDate")));
				params.put("duration", model.get("durationDays"));
			} else if (PDMSCommon.isNotNull(model.get("plannedStartDate")) &&
					PDMSCommon.isNotNull(model.get("plannedFinishDate"))) {
				// 计算周期（天）
				int durationDays = DateUtils.daysBetween(
						DateUtils.strToDate(model.get("plannedStartDate")),
						DateUtils.strToDate(model.get("plannedFinishDate")))
						+ 1;
				params.put("stDate", DateUtils.strToDate(model.get("plannedStartDate")));
				params.put("edDate", DateUtils.strToDate(model.get("plannedFinishDate")));
				params.put("duration", durationDays);
			}
		} else {
			params.put("stDate", DateUtils.strToDate(model.get("plannedFinishDate")));
			params.put("edDate", DateUtils.strToDate(model.get("plannedFinishDate")));
			params.put("duration", "0");
		}
		// 返回值
		return params;
	}
}

	