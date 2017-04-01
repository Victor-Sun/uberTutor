package com.gnomon.pdms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResequenceService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void requencePmObs(String programId,String programVehicleId){
		jdbcTemplate.update("{CALL SP_RESEQUENCE_PM_OBS(?,?)}",programId,programVehicleId);
	}
	
	public void requencePmTempObs(String programTempId){
		jdbcTemplate.update("{CALL SP_RESEQUENCE_PM_TEMP_OBS(?)}",programTempId);
	}
	
	public void requencePmTask(String programId,String programVehicleId){
		jdbcTemplate.update("{CALL SP_RESEQUENCE_PM_TASK(?,?)}",programId,programVehicleId);
	}
	
	public void requencePmTempTask(String programTempId){
		jdbcTemplate.update("{CALL SP_RESEQUENCE_PM_TEMP_TASK(?)}",programTempId);
	}
	
}
