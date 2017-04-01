package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.PMManufactureCompDAO;
import com.gnomon.pdms.entity.PMManufactureCompEntity;

@Service
@Transactional
public class ManufactureCompService {

	@Autowired
	private PMManufactureCompDAO pmManufactureCompDAO;
	
	public List<PMManufactureCompEntity> getManufactureCompList() {
		List<PMManufactureCompEntity> result =
				this.pmManufactureCompDAO.getAll();
        return result;
    }
}

