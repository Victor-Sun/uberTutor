package com.gnomon.common.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.exception.ServiceException;
import com.gnomon.common.system.dao.GTProgramProfileDao;
import com.gnomon.common.system.entity.GTProgramProfile;
import com.gnomon.common.system.service.GTProgramProfileService;

@Service
@Transactional
public class GTProgramProfileServiceImpl implements GTProgramProfileService {
	
	@Autowired
	private GTProgramProfileDao profileDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<GTProgramProfile> getProfileList() throws ServiceException {
		return profileDao.getAll();
	}

	@Override
	public List<Map<String, Object>> getPrivilegeList(String profileId) throws ServiceException {
		String sql="select gp.ID,gp.ALLOW_FLAG,p.PRIVILEGE_NAME,p.PRIVILEGE_CODE from GT_PRIVILEGE p left join GT_PROGRAM_PROFILE_PRIVILEGE gp on p.PRIVILEGE_CODE=gp.PRIVILEGE_CODE and gp.PROFILE_ID='"+profileId+"' where p.PRIVILEGE_CODE like 'P%' and p.IS_ACTIVE = 'Y'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void updateProfilePrivilege(String id, String flag) throws ServiceException {
		String sql="update GT_PROGRAM_PROFILE_PRIVILEGE set ALLOW_FLAG ='"+flag+"' where ID="+id;
		jdbcTemplate.execute(sql);
	}

	@Override
	public void setProfile(String id, String type) throws ServiceException {
		String sql="update GT_PROGRAM_PROFILE set DEFAULT_FLAG='"+type+"' where ID="+id;
		jdbcTemplate.execute(sql);
	}

	@Override
	public void deleteProfile(String id) throws ServiceException {
		String sql="SELECT count(*) FROM GT_USER_OBS WHERE PROFILE_ID=? ";
		int c = 0;
		c = jdbcTemplate.queryForInt(sql,id);
		if(c > 0){
			throw new ServiceException("不能删除已使用的角色！");
		}
		deleteProgramProfilePrivilege(id);
		profileDao.delete(id);
	}

	@Override
	public void saveProfile(GTProgramProfile programProfile) throws ServiceException {
		profileDao.save(programProfile);
	}

	@Override
	public List<Map<String, Object>> getPrivilegeList() throws ServiceException {
		String sql="select * from GT_PRIVILEGE where MODULE_CODE = 'PM' and PRIVILEGE_CODE like 'P%' and IS_ACTIVE='Y' ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void insertProfilePrivilege(String profileId, String privilageCode, String allowFlag) throws ServiceException {
		String sql = "INSERT INTO [GT_PROGRAM_PROFILE_PRIVILEGE] ([PROFILE_ID],[PRIVILEGE_CODE],[ALLOW_FLAG])VALUES("+profileId+",'"+privilageCode+"','"+allowFlag+"')";
		jdbcTemplate.execute(sql);
	}


	@Override
	public void initialProgramProfileState() throws ServiceException {
		String sql = "update GT_PROGRAM_PROFILE set DEFAULT_FLAG = 'N'";
		jdbcTemplate.execute(sql);
	}


	@Override
	public void saveProfileAllPrivilege(String profileId, String allowFlag) throws ServiceException {
		List<Map<String, Object>> list = getPrivilegeList();
		for(Map<String, Object> map:list){
			String proviligeCode = (String)map.get("PRIVILEGE_CODE");
			int size = getProfilePrivilegeCount(profileId,proviligeCode);
			if(size > 0){
				Map<String, Object> profilePrivilegeMap = getProfilePrivilege(profileId,proviligeCode);
				String profilePrivilegeId = (String)profilePrivilegeMap.get("ID");
				updateProfilePrivilege(profilePrivilegeId,allowFlag);
			}else{
				insertProfilePrivilege(profileId,proviligeCode,allowFlag);
			}
		}
		
	}

	
	public int getProfilePrivilegeCount(String profileId,String proviligeCode){
		String sql = "select count(*) from GT_PROGRAM_PROFILE_PRIVILEGE where PROFILE_ID=? and PRIVILEGE_CODE=?";
		return jdbcTemplate.queryForInt(sql, profileId,proviligeCode);
	}
	
	public Map<String, Object> getProfilePrivilege(String profileId,String proviligeCode){
		String sql = "select * from GT_PROGRAM_PROFILE_PRIVILEGE where PROFILE_ID=? and PRIVILEGE_CODE=?";
		return jdbcTemplate.queryForMap(sql, profileId,proviligeCode);
	}

	@Override
	public void deleteProgramProfilePrivilege(String profileId) throws ServiceException {
		String sql = "delete from  GT_PROGRAM_PROFILE_PRIVILEGE where PROFILE_ID = "+profileId;
		jdbcTemplate.execute(sql);
	}
	

}
