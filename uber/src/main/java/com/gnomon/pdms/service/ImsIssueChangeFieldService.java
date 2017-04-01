package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ImsIssueChangeFieldDAO;
import com.gnomon.pdms.entity.ImsIssueChangeFieldEntity;

@Service
@Transactional
public class ImsIssueChangeFieldService {

	@Autowired
	private ImsIssueChangeFieldDAO imsIssueChangeFieldDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<ImsIssueChangeFieldEntity> getChangeFieldList() {
		List<ImsIssueChangeFieldEntity> changeFieldList =
				this.imsIssueChangeFieldDAO.getAll();
        return changeFieldList;
    }
	
	public List<Map<String, Object>> getChangeFieldList(String issueId){
		return jdbcTemplate.queryForList("select * from V_IMS_ISSUE_CHANGE_FIELD where issue_id=?",issueId);
	}
	
}
