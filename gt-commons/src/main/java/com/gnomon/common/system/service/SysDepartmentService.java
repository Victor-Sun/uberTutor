package com.gnomon.common.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.dao.SysDepartmentDAO;
import com.gnomon.common.system.entity.SysDepartmentEntity;

@Service
@Transactional
public class SysDepartmentService {
	
	@Autowired
	private SysDepartmentDAO sysDepartmentDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;

	public SysDepartmentEntity get (String id){
		return sysDepartmentDAO.get(id);
	}
	
	public void save(SysDepartmentEntity entity){
		sysDepartmentDAO.save(entity);
	}
	
	public void delete(String id) throws Exception{
//		List<Object> list = sysDepartmentDAO.find("from PMWorkGroupMemberEntity where workGroupId=? ", id);
//		if(list.size() > 0){
//			throw new Exception("请先删除工作组成员！");
//		}
		sysDepartmentDAO.delete(id);
	}
	
	public GTPage<Map<String, Object>> getSysDepartmentList(int pageNo, int pageSize){
		return jdbcTemplate.queryPagination(" select T1.* from SYS_DEPARTMENT T1 ",pageNo,pageSize);
	}
}
