package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.utils.CommonUtils;
import com.gnomon.pdms.dao.ImsIssueDAO;
import com.gnomon.pdms.dao.IssueSourceDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.IssueSourceEntity;
import com.gnomon.pdms.entity.TestTypeEntity;

@Service
@Transactional
public class IssueSourceService {

	@Autowired
	private IssueSourceDAO issueSourceDAO;
	
	@Autowired
	private ImsIssueDAO imsIssueDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public IssueSourceEntity get (Long id){
		return issueSourceDAO.get(id);
	}
	
	public void save(IssueSourceEntity entity){
		issueSourceDAO.save(entity);
	}
	
	public void delete(Long id){
		List<GTIssueEntity> list = imsIssueDAO.findBy("issueSourceId", id.toString());
		if(list != null && list.size() > 0){
			throw new RuntimeException("试验类型已经被使用，不能删除！");
		}
		issueSourceDAO.delete(id);
	}

	public List<Map<String, Object>> getIssueSourceList(){
		return jdbcTemplate.queryForList(" select * from IMS_ISSUE_SOURCE ");
	}
}
