package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.PMObsTreeVMDAO;
import com.gnomon.pdms.entity.PMObsTreeVMEntity;

@Service
@Transactional
public class PmObsService {
	
	@Autowired
	private PMObsTreeVMDAO pmObsTreeVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 组织架构一览取得
	 */
	public List<PMObsTreeVMEntity> getPmObsTree(String programId, String vehicleId) {
		String hql = "FROM PMObsTreeVMEntity WHERE programId = ? AND programVehicleId = ?";
		List<PMObsTreeVMEntity> result =
				this.pmObsTreeVMDAO.find(hql, programId, vehicleId);
        return result;
    }
	
	/**
	 * 专业领域及其下级组织一览取得
	 */
	public List<Map<String, Object>> getRespDeptChildList(
			String programVehicleId, String rootObsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT");
		sql.append(" T1.ID");
		sql.append(",T1.LFT");
		sql.append(",T1.OBS_CODE");
		sql.append(",T1.OBS_NAME");
		sql.append(" FROM");
		sql.append(" PM_OBS T1");
		sql.append(" WHERE");
		sql.append(" PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND T1.DELETE_BY IS NULL");
		sql.append(" START WITH T1.PARENT_ID = ? CONNECT BY PRIOR T1. ID = T1.PARENT_ID");
		sql.append(" UNION");
		sql.append(" SELECT");
		sql.append(" T1.ID");
		sql.append(",T1.LFT");
		sql.append(",T1.OBS_CODE");
		sql.append(",T1.OBS_NAME");
		sql.append(" FROM");
		sql.append(" PM_OBS T1");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		sql.append(" ORDER BY LFT");
		paramList.add(programVehicleId);
		paramList.add(rootObsId);
		paramList.add(rootObsId);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	public List<Map<String, Object>> getObsList(String obsName,String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from PM_OBS where OBS_NAME = ? and PROGRAM_VEHICLE_ID = ? ");
		return this.jdbcTemplate.queryForList(sql.toString(), obsName,programVehicleId);
    }
	
}
