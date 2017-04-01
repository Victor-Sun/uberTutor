package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMIssueSourceDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.PMDeptIssueSourceEntity;
import com.gnomon.pdms.entity.PMIssueSourceEntity;

@Service
@Transactional
public class PmIssueSourceService {

	@Autowired
	private PMIssueSourceDAO pmIssueSourceDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public PMIssueSourceEntity get (Long id){
		return pmIssueSourceDAO.get(id);
	}
	
	public void save(PMIssueSourceEntity entity){
		pmIssueSourceDAO.save(entity);
	}
	
	public void delete(Long id){
		pmIssueSourceDAO.delete(id);
	}
	
	public GTPage<Map<String, Object>> getIssueSourceList(int pageNo, int pageSize){
		return jdbcTemplate.queryPagination(" select * from PM_ISSUE_SOURCE ",pageNo,pageSize);
	}
	
}
