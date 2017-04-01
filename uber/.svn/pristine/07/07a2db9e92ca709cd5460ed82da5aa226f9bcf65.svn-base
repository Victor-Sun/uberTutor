package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.gnomon.pdms.dao.PMProgramVehicleBaseVMDAO;
import com.gnomon.pdms.dao.PMTaskVehicleVMDAO;
import com.gnomon.pdms.entity.PMTaskVehicleVMEntity;

@Service
@Transactional
public class ProgressReportService {
	
	@Autowired
	private PMProgramVehicleBaseVMDAO pmProgramVehicleBaseVMDAO;
	
	@Autowired
	private PMTaskVehicleVMDAO pmTaskVehicleVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	

	/**
	 * 进展报表Project数据取得
	 */
	public Map<String, Object> getProcessProjectData() {
		Map<String, Object> result = new HashMap<String, Object>();
		// 开始时间取得
		Date stDate = DateUtils.addMonths(new Date(), -3);
		// 结束时间取得
		Date enDate = DateUtils.addMonths(new Date(), 9);
		// 结果返回
		result.put("start", stDate);
		result.put("end", enDate);
        return result;
    }
	
	/**
	 * 车型信息取得
	 */
	public List<Map<String, Object>> getProgressResources(
			String programId, String vehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE_BASE");
		sql.append(" WHERE");
		if (PDMSCommon.isNotNull(programId)) {
			sql.append(" PROGRAM_ID = ? AND");
			paramList.add(programId);
		}
		if (PDMSCommon.isNotNull(vehicleId)) {
			sql.append(" ID = ? AND");
			paramList.add(vehicleId);
		}
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, ID) = 1");
		paramList.add(loginUser);
		if (PDMSCommon.isNull(programId) && PDMSCommon.isNull(vehicleId)) {
			sql.append(" AND LIFECYCLE_STATUS = ?");
			paramList.add(PDMSConstants.LIFECYCLE_STATUS_IN_PROCESS);
		}
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/**
	 * 质量阀数据取得
	 */
	public List<PMTaskVehicleVMEntity> getProgressEvents(
			String programId, String vehicleId) {
		List<Object> paramList = new ArrayList<Object>();
		String hql = "FROM PMTaskVehicleVMEntity WHERE taskTypeCode = ?";
		paramList.add(PDMSConstants.TASK_TYPE_GATE);
		if (PDMSCommon.isNotNull(programId)) {
			hql += " AND programId = ?";
			paramList.add(programId);
		}
		if (PDMSCommon.isNotNull(vehicleId)) {
			hql += " AND vehicleId = ?";
			paramList.add(vehicleId);
		}
		hql += " order by plannedFinishDate";
		List<PMTaskVehicleVMEntity> result = this.pmTaskVehicleVMDAO.find(
				hql, paramList.toArray());
        return result;
	}
}
