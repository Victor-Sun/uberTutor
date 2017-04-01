package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.PMProgramStageDAO;
import com.gnomon.pdms.entity.PMProgramStageEntity;

@Service
@Transactional
public class ProjectPlanStageService {
	
	@Autowired
	private PMProgramStageDAO pmprogramStageDAO;
	
	public List<PMProgramStageEntity> getProjectPlanStage(String keyId) {
		String hql = "FROM PMProgramStageEntity WHERE programId = ?";
		List<PMProgramStageEntity> projectPlanStage =
				this.pmprogramStageDAO.find(hql, keyId);
        return projectPlanStage;
    }

}
