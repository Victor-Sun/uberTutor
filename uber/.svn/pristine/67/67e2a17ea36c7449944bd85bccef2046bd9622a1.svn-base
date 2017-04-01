package com.gnomon.pdms.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.pdms.dao.PMDeptIssueSourceDAO;
import com.gnomon.pdms.entity.PMDeptIssueSourceEntity;

@Service
@Transactional
public class PmDeptIssueService {

	@Autowired
	private PMDeptIssueSourceDAO pmDeptIssueSourceDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public PMDeptIssueSourceEntity get (Long id){
		return pmDeptIssueSourceDAO.get(id);
	}
	
	public void save(PMDeptIssueSourceEntity entity){
		pmDeptIssueSourceDAO.save(entity);
	}
	
	public void delete(Long id){
		pmDeptIssueSourceDAO.delete(id);
	}
	
	public GTPage<Map<String, Object>> getDeptIssueSourceList(int pageNo, int pageSize){
		return jdbcTemplate.queryPagination(" select * from PM_DEPT_ISSUE_SOURCE ",pageNo,pageSize);
	}
	
}
