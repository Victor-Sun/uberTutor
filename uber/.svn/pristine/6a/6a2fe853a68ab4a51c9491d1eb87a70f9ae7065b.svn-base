package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMProgramVehicleDAO;
import com.gnomon.pdms.entity.PMProgramVehicleEntity;

@Service
@Transactional
public class PMProgramVehicleService {

	@Autowired
	private PMProgramVehicleDAO pmProgramVehicleDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	
	public PMProgramVehicleEntity getPmProgramVehicle(String id){
		return pmProgramVehicleDAO.get(id);
	}
	
	/**
	 * 保存车型基本信息
	 */
	public void saveVehicleInfo(String programId, String programVehicleId,
			Map<String, String> model) throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		String loginUser = SessionData.getLoginUserId();
		
		this.projectBaseInfoService.checkVehicleInfo(programId, programVehicleId,
				model.get("code"), model.get("name"));
		
		// 修改车型信息
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_PROGRAM_VEHICLE");
		sql.append(" SET VEHICLE_CODE = ?");
		sql.append(",VEHICLE_NAME = ?");
		//sql.append(",SOP_DATE = ?");
		sql.append(",PM = ?");
		sql.append(",QM = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(model.get("code"));
		params.add(model.get("name"));
		//params.add(DateUtils.strToDate(model.get("sopDate")));
		params.add(model.get("pm"));
		params.add(model.get("qm"));
		params.add(loginUser);
		params.add(programVehicleId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 项目经理OBS
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_USER_OBS");
		sql.append(" SET USER_ID = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" OBS_ID IN");
		sql.append(" (SELECT ID FROM PM_OBS WHERE PROGRAM_VEHICLE_ID = ?)");
		sql.append(" AND PROFILE_ID = ?");
		params.add(model.get("pm"));
		params.add(loginUser);
		params.add(programVehicleId);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_PM);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 质量经理OBS
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_USER_OBS");
		sql.append(" SET USER_ID = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" OBS_ID IN");
		sql.append(" (SELECT ID FROM PM_OBS WHERE PROGRAM_VEHICLE_ID = ?)");
		sql.append(" AND PROFILE_ID = ?");
		params.add(model.get("qm"));
		params.add(loginUser);
		params.add(programVehicleId);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_QM);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// PM、QM
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM PM_PROGRAM_VEHICLE");
		sql.append(" WHERE PROGRAM_ID = ?");
		sql.append(" AND DELETE_BY IS NULL");
		sql.append(" ORDER BY CREATE_DATE");
		params.add(programId);
		List<Map<String, Object>> vehicleList = this.jdbcTemplate.queryForList(
				sql.toString(), params.toArray());
		if (vehicleList.size() > 0) {
			if (programVehicleId.equals(PDMSCommon.nvl(vehicleList.get(0).get("ID")))) {
				sql = new StringBuffer();
				params = new ArrayList<Object>();
				sql.append(" UPDATE PM_PROGRAM_BASEINFO");
				sql.append(" SET PM = ?");
				sql.append(",QM = ?");
				sql.append(" WHERE PROGRAM_ID = ?");
				params.add(PDMSCommon.nvl(vehicleList.get(0).get("PM")));
				params.add(PDMSCommon.nvl(vehicleList.get(0).get("QM")));
				params.add(programId);
				this.jdbcTemplate.update(sql.toString(), params.toArray());
			}
		}
	}
}

