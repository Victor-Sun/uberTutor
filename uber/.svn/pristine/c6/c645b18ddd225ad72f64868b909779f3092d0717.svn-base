package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.pdms.dao.PmWorkGroupDAO;
import com.gnomon.pdms.entity.PMWorkGroupEntity;

@Service
@Transactional
public class PmWorkGroupService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PmWorkGroupDAO pmWorkGroupDAO;
	
	public PMWorkGroupEntity get (Long id){
		return pmWorkGroupDAO.get(id);
	}
	
	public void save(PMWorkGroupEntity entity){
		pmWorkGroupDAO.save(entity);
	}
	
	public void delete(Long id) throws Exception{
		List<Object> list = pmWorkGroupDAO.find("from PMWorkGroupMemberEntity where workGroupId=? ", id);
		if(list.size() > 0){
			throw new Exception("请先删除工作组成员！");
		}
		pmWorkGroupDAO.delete(id);
	}
	
	public GTPage<Map<String, Object>> getWorkGroupList(int pageNo, int pageSize,String programVehicleId){
		return jdbcTemplate.queryPagination(" select T1.*,T2.USERNAME OWNER_NAME from PM_WORK_GROUP T1,SYS_USER T2 WHERE T1.OWNER = T2.ID and T1.MODULE_CODE = 'EXT301' and T1.INSTANCE_ID = ? ",pageNo,pageSize,programVehicleId);
	}
}
