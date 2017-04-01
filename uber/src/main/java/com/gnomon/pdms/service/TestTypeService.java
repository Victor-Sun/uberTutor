package com.gnomon.pdms.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.utils.CommonUtils;
import com.gnomon.pdms.dao.ImsIssueDAO;
import com.gnomon.pdms.dao.TestTypeDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.TestTypeEntity;
import com.gnomon.pdms.procedure.PkgPmWorkOrderDBProcedureServcie;

@Service
@Transactional
public class TestTypeService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private TestTypeDAO testTypeDAO;
	
	@Autowired
	private ImsIssueDAO imsIssueDAO;
	
	@Autowired
	private PkgPmWorkOrderDBProcedureServcie pkgPmWorkOrderDBProcedureServcie;
	
	public TestTypeEntity getTestType(String id){
		return testTypeDAO.get(id);
	}
	
	public void save(TestTypeEntity entity){
		if(StringUtils.isEmpty(entity.getId())){
			entity.setId(CommonUtils.getUUID());
		}
		testTypeDAO.save(entity);
	}
	
	public void delete(String id){
		List<GTIssueEntity> list = imsIssueDAO.findBy("testTypeId", id);
		if(list != null && list.size() > 0){
			throw new RuntimeException("试验类型已经被使用，不能删除！");
		}
		testTypeDAO.delete(id);
	}
}
