package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.Ext301CheckpoinTmpDAO;
import com.gnomon.pdms.entity.Ext301CheckpointTempEntity;

@Service
@Transactional
public class Ext301CheckpointTempService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Ext301CheckpoinTmpDAO ext301CheckpoinTmpDAO;
	
	public List<Map<String, Object>> getExt301CheckpointTmpList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT301_CHECKPOINT_TEMP ");
        return list;
    }
	
	public List<Map<String, Object>> getTempStdObsList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_TEMP_STD_OBS ");
        return list;
    }
	
	public List<Map<String, Object>> getObsList(String programVehicleId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_OBS  where program_vehicle_id = ? and plan_level = 2 ",programVehicleId);
        return list;
    }
	
	public void save(Ext301CheckpointTempEntity entity){
		ext301CheckpoinTmpDAO.save(entity);
	}
	
	public Ext301CheckpointTempEntity get(Long id){
		return ext301CheckpoinTmpDAO.get(id);
	}
	
}
