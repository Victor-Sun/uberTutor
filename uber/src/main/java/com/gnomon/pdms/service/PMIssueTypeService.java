package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.pdms.dao.PMIssueTypeDAO;
import com.gnomon.pdms.entity.PMIssueTyeEntity;

@Service
@Transactional
public class PMIssueTypeService {

	@Autowired
	private PMIssueTypeDAO pmIssueTypeDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public PMIssueTyeEntity get (Long id){
		return pmIssueTypeDAO.get(id);
	}
	
	public void save(PMIssueTyeEntity entity){
		pmIssueTypeDAO.save(entity);
	}
	
	public void delete(Long id){
//		List<GTIssueEntity> list = pMIssueTypeDAO.findBy("issueSourceId", id.toString());
//		if(list != null && list.size() > 0){
//			throw new RuntimeException("试验类型已经被使用，不能删除！");
//		}
		pmIssueTypeDAO.delete(id);
	}

	public GTPage<Map<String, Object>> getIssueTypeList(int pageNo, int pageSize){
		return jdbcTemplate.queryPagination(" select * from PM_ISSUE_TYPE ",pageNo,pageSize);
	}
}
