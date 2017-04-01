package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ProgramTypeDAO;
import com.gnomon.pdms.entity.ProgramTypeEntity;

@Service
@Transactional
public class ProgramTypeService {

	@Autowired
	private ProgramTypeDAO programTypeDAO;
	
	public List<ProgramTypeEntity> getProgramTypeList(){
		List<ProgramTypeEntity> programTypeList =
				this.programTypeDAO.getAll("seq", true);
        return programTypeList;
    }
	
}

