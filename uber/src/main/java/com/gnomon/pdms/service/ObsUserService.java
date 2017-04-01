package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.PMObsDAO;

@Service
@Transactional
public class ObsUserService {
	
	@Autowired
	private PMObsDAO pmobsDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 责任人信息取得
	 */
	public List<Map<String, Object>> getObsUserList(String programVehicleId,
			String obsId, boolean includeSubDept, String searchUserName) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		if (PDMSCommon.isNotNull(programVehicleId) && PDMSCommon.isNull(obsId)) {
			// 查询车型OBS下所有成员
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" V_PM_OBS_USER");
			sql.append(" WHERE");
			sql.append(" PROGRAM_VEHICLE_ID = ?");
			params.add(programVehicleId);
		} else {
			// 查询用户列表
			if (! includeSubDept) {
				// 不包含下级部门人员
				sql.append(" SELECT *");
				sql.append(" FROM");
				sql.append(" V_PM_OBS_USER");
				sql.append(" WHERE");
				sql.append(" OBS_ID = ?");
				params.add(obsId);
				if (searchUserName != null && searchUserName.length() > 0) {
					sql.append(" AND UPPER(USER_NAME) LIKE UPPER(?)");
					params.add("%" + searchUserName + "%");
				}
			} else {
				// 包含下级部门
				sql.append(" SELECT *");
				sql.append(" FROM");
				sql.append(" V_PM_OBS_MEMBER");
				sql.append(" WHERE");
				sql.append(" TOP_OBS_ID = ?");
				params.add(obsId);
				if (searchUserName != null && searchUserName.length() > 0) {
					sql.append(" AND UPPER(USER_NAME) LIKE UPPER(?)");
					params.add("%" + searchUserName + "%");
				}
			}
		}
		
		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

	/*
	 * 责任人组织信息取得
	 */
	public List<Map<String, Object>> getObsDepartmentList(
			String programId, String vehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_OBS_VEHICLE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		params.add(programId);
		if (PDMSCommon.isNotNull(vehicleId)) {
			sql.append(" AND PROGRAM_VEHICLE_ID = ?");
			params.add(vehicleId);
		}
		sql.append(" ORDER BY");
		sql.append(" VEHICLE_CODE");
		sql.append(",OBS_NAME");
		
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
	
	/**
	 * 所属专业领域下成员取得
	 */
	public List<Map<String, Object>> getFunctionObsUser(String obsId, String userObsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_OBS_MEMBER");
		sql.append(" WHERE");
		sql.append(" TOP_OBS_ID IN (SELECT FUNCTION_GROUP_ID FROM PM_OBS WHERE ID = ?)");
		params.add(obsId);
		if (PDMSCommon.isNotNull(userObsId)) {
			sql.append(" AND ID <> ?");
			params.add(userObsId);
		}
		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
}
