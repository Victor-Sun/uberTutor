package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.Ext301CheckpoinDAO;
import com.gnomon.pdms.entity.Ext301CheckpointEntity;

@Service
@Transactional
public class Ext301CheckpointService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Ext301CheckpoinDAO ext301CheckpoinDAO;
	
	public List<Map<String, Object>> getExt301CheckpointList(Long extProjectId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select T1.*,T2.USERNAME from PM_EXT301_CHECKPOINT T1,SYS_USER T2 WHERE T1.OWNER = T2.ID(+) AND T1.EXT_PROJECT_ID = ? ",extProjectId);
        return list;
    }
	
	public List<Map<String, Object>> getTempStdObsList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_TEMP_STD_OBS ");
        return list;
    }
	
	public void save(Ext301CheckpointEntity entity){
		ext301CheckpoinDAO.save(entity);
	}
	
	public Ext301CheckpointEntity get(Long id){
		return ext301CheckpoinDAO.get(id);
	}
	
}
