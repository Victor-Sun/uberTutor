package com.gnomon.pdms.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.entity.ProgramEntity;


@Service
@Transactional
public class ProjectInfoService {
	
	@Autowired
	private ProgramDAO programDAO;
	
	public Map<String, String> getAllProject(){
		Map<String, String> projectMap = new HashMap();
		List<ProgramEntity> programList = this.programDAO.getAll();
		for (ProgramEntity programEntity : programList) {
			projectMap.put(programEntity.getId(), programEntity.getCode());
		}
		return projectMap;
	}
	
	public ProgramEntity get(String programId){
		return programDAO.get(programId);
	}

}
