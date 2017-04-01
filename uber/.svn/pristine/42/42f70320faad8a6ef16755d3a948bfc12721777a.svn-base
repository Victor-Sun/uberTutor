package com.gnomon.pdms.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.pdms.dao.PmWorkGroupMemberDAO;
import com.gnomon.pdms.entity.PMWorkGroupMemberEntity;

@Service
@Transactional
public class PmWorkGroupMemberService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PmWorkGroupMemberDAO pmWorkGroupMemberDAO;
	
	public PMWorkGroupMemberEntity get (Long id){
		return pmWorkGroupMemberDAO.get(id);
	}
	
	public void save(PMWorkGroupMemberEntity entity){
		pmWorkGroupMemberDAO.save(entity);
	}
	
	public void delete(Long id){
		pmWorkGroupMemberDAO.delete(id);
	}
	
	public GTPage<Map<String, Object>> getWorkGroupMemberList(Long workGroupId,int pageNo, int pageSize){
		return jdbcTemplate.queryPagination(" select T1.*,T2.USERNAME from PM_WORK_GROUP_MEMBER T1,SYS_USER T2 WHERE T1.USER_ID = T2.ID AND WORK_GROUP_ID = ? ",pageNo,pageSize,workGroupId);
	}
}
