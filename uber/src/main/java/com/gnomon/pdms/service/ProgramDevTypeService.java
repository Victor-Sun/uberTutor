package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.PMProgramDevTypeDAO;
import com.gnomon.pdms.entity.PMProgramDevTypeEntity;

@Service
@Transactional
public class ProgramDevTypeService {

	@Autowired
	private PMProgramDevTypeDAO pmProgramDevTypeDAO;
	
	public List<PMProgramDevTypeEntity> getDevTypeList() {
		List<PMProgramDevTypeEntity> result =
				this.pmProgramDevTypeDAO.getAll();
        return result;
    }
}

