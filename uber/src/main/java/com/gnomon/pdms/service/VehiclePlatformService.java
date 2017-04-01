package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.pdms.dao.VehiclePlatformDAO;

@Service
@Transactional
public class VehiclePlatformService {

	@Autowired
	private VehiclePlatformDAO vehiclePlatformDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/**
	 * 车型平台取得
	 */
	public List<Map<String, Object>> getVehiclePlatformList(String programId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T1.*");
		sql.append(" FROM");
		sql.append(" PM_VEHICLE_PLATFORM T1 INNER JOIN PM_PROGRAM_BASEINFO T2");
		sql.append(" ON T1.PROGRAM_TYPE_ID = T2.PROGRAM_TYPE_ID");
		sql.append(" WHERE");
		sql.append(" T2.PROGRAM_ID = ?");
		
		List<Map<String, Object>> result =
				this.jdbcTemplate.queryForList(sql.toString(), programId);
		if (result.size() == 0) {
			// 如果项目类型对应车型不存在，则取得通用类型
			sql = new StringBuffer();
			sql.append(" SELECT *");
			sql.append(" FROM");
			sql.append(" PM_VEHICLE_PLATFORM");
			sql.append(" WHERE");
			sql.append(" PROGRAM_TYPE_ID IS NULL");
			result = this.jdbcTemplate.queryForList(sql.toString());
		}
		return result;
    }
}
