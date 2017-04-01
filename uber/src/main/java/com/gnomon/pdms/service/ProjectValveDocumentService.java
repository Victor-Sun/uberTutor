package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.DeliverableGateVMDAO;
import com.gnomon.pdms.dao.PMTaskListVMDAO;
import com.gnomon.pdms.entity.DeliverableGateVMEntity;
import com.gnomon.pdms.entity.PMTaskListVMEntity;

@Service
@Transactional
public class ProjectValveDocumentService {
	
	@Autowired
	private DeliverableGateVMDAO deliverableGateVMDAO;
	
	@Autowired
	private PMTaskListVMDAO pmTaskListVMDAO;

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/*
	 * 取得阀门交付物信息
	 */
	public List<Map<String, Object>> getProjectValveDocument(String gateId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE_GATE");
		sql.append(" WHERE");
		sql.append(" TASK_ID = ?");
		sql.append(" AND (OBS_ID IS NULL");
		sql.append(" OR PKG_PERMISSION.CAN_VIEW_OBS(?, OBS_ID) = 1)");
		paramList.add(gateId);
		paramList.add(loginUser);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/*
	 * 取得阀门交付物关联任务信息
	 */
	public List<Map<String, Object>> getProjectValveDocumentTask(String deliverableId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE_GATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		sql.append(" AND PKG_PERMISSION.CAN_VIEW_OBS(?, REF_OBS_ID) = 1");
		paramList.add(deliverableId);
		paramList.add(loginUser);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }

	/*
	 * 取得阀门交付物详细信息
	 */
	public DeliverableGateVMEntity getDeliverableInfo(String deliverableId) {
		DeliverableGateVMEntity projectValveDocumentWinForm =
				this.deliverableGateVMDAO.findUniqueBy("id", deliverableId);
        return projectValveDocumentWinForm;
    }
	
	/*
	 * 取得阀门信息
	 */
	public PMTaskListVMEntity getQualityValveInfo(String taskId) {
		PMTaskListVMEntity result =
				this.pmTaskListVMDAO.findUniqueBy("id", taskId);
        return result;
    }

	/*
	 * 阀门状态更新
	 */
	public void updateGateStatus(String taskId, String gateStatus) {
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 更新阀门状态
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PM_TASK SET");
		sql.append(" GATE_STATUS_CODE = ?");
		sql.append(",IS_GATE_PASS = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(gateStatus);
		if (PDMSConstants.GATE_STATUS_GREEN.equals(gateStatus) ||
				PDMSConstants.GATE_STATUS_YELLOW.equals(gateStatus)) {
			params.add(PDMSConstants.STATUS_Y);
		} else {
			params.add(PDMSConstants.STATUS_N);
		}
		params.add(loginUser);
		params.add(taskId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}

//	/*
//	 * 阀门状态更新(通过)
//	 */
//	public String updateGateStatusForward(
//			String taskId, String programId, String vehicleId) {
//		String nextGateId = "";
//		// 登录用户取得
//		String loginUser = SessionData.getLoginUserId();
//		// 更新通过阀门信息
//		StringBuffer sql = new StringBuffer();
//		sql.append(" UPDATE PM_TASK SET");
//		sql.append(" GATE_STATUS_CODE = ?");
//		sql.append(",IS_GATE_PASS = ?");
//		sql.append(",UPDATE_BY = ?");
//		sql.append(",UPDATE_DATE = SYSDATE");
//		sql.append(" WHERE");
//		sql.append(" ID = ?");
//		List<Object> params = new ArrayList<Object>();
//		params.add(PDMSConstants.GATE_STATUS_GREEN);
//		params.add(PDMSConstants.STATUS_Y);
//		params.add(loginUser);
//		params.add(taskId);
//		jdbcTemplate.update(sql.toString(), params.toArray());
//		
//		//获取下一阀门ID
//		String strSql = "SELECT ID FROM PM_TASK WHERE ID = PM_GET_NEXT_GATE_ID(?, ?)";
//		Map<String, Object> task = jdbcTemplate.queryForMap(strSql, programId, vehicleId);
//		nextGateId = PDMSCommon.nvl(task.get("ID"));
//		
//		if (PDMSCommon.isNull(nextGateId)) {
//			return "";
//		}
//
//		// 更新下一阀门状态
//		sql = new StringBuffer();
//		sql.append(" UPDATE PM_TASK SET");
//		sql.append(" GATE_STATUS_CODE = ?");
//		sql.append(",UPDATE_BY = ?");
//		sql.append(",UPDATE_DATE = SYSDATE");
//		sql.append(" WHERE");
//		sql.append(" ID = ?");
//		params = new ArrayList<Object>();
//		params.add(PDMSConstants.GATE_STATUS_GREEN);
//		params.add(loginUser);
//		params.add(nextGateId);
//		jdbcTemplate.update(sql.toString(), params.toArray());
//		
//		// 当前阀门返回
//		return nextGateId;
//	}
	
	/**
	 * 阀门交付物列表取得
	 */
	public GTPage<Map<String, Object>> getPmDeliverableList(String programVehicleId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE_LIST");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_OBS(?, OBS_ID) = 1");
		params.add(loginUser);
		sql.append(" AND PROGRAM_VEHICLE_ID = ?");
		params.add(programVehicleId);
		
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
			if (PDMSCommon.isNotNull(searchModel.get("searchDeliverableCode"))) {
				sql.append(" AND UPPER(CODE) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchDeliverableCode") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDeliverableName"))) {
				sql.append(" AND UPPER(NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchDeliverableName") + "%");
			}
			// 状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchStatus_1"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
			}
			if ("true".equals(searchModel.get("searchStatus_2"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
			}
			if ("true".equals(searchModel.get("searchStatus_3"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_GY);
			}
			if ("true".equals(searchModel.get("searchStatus_4"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_Y);
			}
			if ("true".equals(searchModel.get("searchStatus_5"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_R);
			}
			if ("true".equals(searchModel.get("searchStatus_6"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_B);
			}
			if (subParams.size() > 0) {
				sql.append(" AND TASK_PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND TASK_PROGRESS_STATUS IS NULL");
			}
			// 适用/不适用
			if ("true".equals(searchModel.get("searchIsFit")) &&
					! "true".equals(searchModel.get("searchIsNotFit"))) {
				sql.append(" AND IS_FIT = ?");
				params.add(PDMSConstants.STATUS_Y);
			} else if (! "true".equals(searchModel.get("searchIsFit")) &&
					 		"true".equals(searchModel.get("searchIsNotFit"))) {
				sql.append(" AND IS_FIT = ?");
				params.add(PDMSConstants.STATUS_N);
			} else if (! "true".equals(searchModel.get("searchIsFit")) &&
			 		! "true".equals(searchModel.get("searchIsNotFit"))) {
				sql.append(" AND IS_FIT = IS NULL");
			}
		}
		sql.append(" ORDER BY TASK_NAME DESC, OBS_NAME, CODE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
	
	/**
	 * 二级交付物列表取得
	 */
	public GTPage<Map<String, Object>> getDeptDeliverableList(String obsId,
			Map<String, String> searchModel, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DELIVERABLE");
		sql.append(" WHERE");
		sql.append(" PLAN_LEVEL = 2");
		sql.append(" AND RESP_OBS_ID = ?");
		params.add(obsId);
		
		// 查询条件
		if (searchModel.size() > 0) {
			if (PDMSCommon.isNotNull(searchModel.get("searchObsName"))) {
				sql.append(" AND UPPER(OBS_NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchObsName") + "%");
			}
			if (PDMSCommon.isNotNull(searchModel.get("searchDeliverableName"))) {
				sql.append(" AND UPPER(NAME) LIKE UPPER(?)");
				params.add("%" + searchModel.get("searchDeliverableName") + "%");
			}
			// 状态
			List<String> subParams = new ArrayList<String>();
			if ("true".equals(searchModel.get("searchStatus_1"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_GREY);
			}
			if ("true".equals(searchModel.get("searchStatus_2"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_G);
			}
			if ("true".equals(searchModel.get("searchStatus_3"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_GY);
			}
			if ("true".equals(searchModel.get("searchStatus_4"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_Y);
			}
			if ("true".equals(searchModel.get("searchStatus_5"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_R);
			}
			if ("true".equals(searchModel.get("searchStatus_6"))) {
				subParams.add(PDMSConstants.TASK_PROGRESS_STATUS_B);
			}
			if (subParams.size() > 0) {
				sql.append(" AND TASK_PROGRESS_STATUS IN(");
				for (int i = 0; i < subParams.size(); i++) {
					sql.append("?");
					params.add(subParams.get(i));
					if (i != subParams.size() - 1) {
						sql.append(", ");
					}
				}
				sql.append(")");
			} else {
				sql.append(" AND TASK_PROGRESS_STATUS IS NULL");
			}
		}
		sql.append(" ORDER BY CODE");
		// 返回查询结果
        return this.jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
        		params.toArray());
	}
}

