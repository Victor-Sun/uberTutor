package com.gnomon.pdms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.entity.GTIssueEntity;

@Service
@Transactional
public class MySubInfoService {

	@Autowired
	private GTIssueDAO gtIssueDAO;

	public GTIssueEntity getMySubInfo(String keyId) {

		GTIssueEntity myTaskResponConfirm =
				this.gtIssueDAO.findUniqueBy("id", keyId);
        return myTaskResponConfirm;
    }

	public void save (GTIssueEntity entity){
		gtIssueDAO.save(entity);
	}

}
