package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.dao.SysProgramProfileDAO;
import com.gnomon.common.system.dao.SysProgramProfileVMDAO;
import com.gnomon.common.system.entity.SysProgramProfileEntity;
import com.gnomon.common.system.entity.SysProgramProfileVMEntity;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.dao.SysProgramProfilePriviVMDAO;
import com.gnomon.pdms.entity.SysProgramProfilePriviVMEntity;
@Service
@Transactional
public class ProjectmanagerService {
	
	@Autowired
	private SysProgramProfileVMDAO sysProgramProfileVMDAO;
	
	@Autowired
	private SysProgramProfileDAO sysProgramProfileDAO;
	
	@Autowired
	private SysProgramProfilePriviVMDAO sysProgramProfilePriviVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 【项目管理-角色管理】一数据取得
	 */
	public List<SysProgramProfileVMEntity> getProjectrolemanager() {
		List<SysProgramProfileVMEntity> myTaskList =
				this.sysProgramProfileVMDAO.getAll();
        return myTaskList;
	}
	
	public List<Map<String, Object>> getProjectRoleList(){
		return jdbcTemplate.queryForList("select * from V_SYS_PROGRAM_PROFILE ");
	}
	
	/**
	 * 【项目管理-角色管理】一数据更新
	 */
	public void updateProjectrolemanager(String id,Map<String, String> updModel) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		SysProgramProfileEntity sysProgramProfileEntity = sysProgramProfileDAO.get(id);
		sysProgramProfileEntity.setProfileName(updModel.get("profileName"));
		sysProgramProfileEntity.setPlanLevel(updModel.get("planLevel"));
		sysProgramProfileEntity.setUpdateBy(loginUser);
		sysProgramProfileEntity.setUpdateDate(new Date());
		sysProgramProfileDAO.save(sysProgramProfileEntity);
	}
	
	/**
	 * 【项目管理-角色管理】一数据插入
	 */
	public void insertProjectrolemanager(Map<String, String> updModel) {
		
		String profileId = com.gnomon.pdms.common.PDMSCommon.generateUUID();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();

		String insertProfileSql = 
				"INSERT INTO SYS_PROGRAM_PROFILE(ID,PROFILE_NAME,DEFAULT_FLAG,SUPERUSER_FLAG,SYSTEM_FLAG,CREATE_BY,CREATE_DATE,PLAN_LEVEL) VALUES(?,?,?,?,?,?,?,?)  ";
//		SysProgramProfileEntity sysProgramProfileEntity = new SysProgramProfileEntity();
//		sysProgramProfileEntity.setProfileName(updModel.get("profileName"));
//		sysProgramProfileEntity.setCreateBy(loginUser);
//		sysProgramProfileEntity.setCreateDate(new Date());
//		sysProgramProfileEntity.setDefaultFlag("N");
//		sysProgramProfileEntity.setSuperuserFlag("N");
//		sysProgramProfileEntity.setSystemFlag("N");
//		sysProgramProfileEntity.setId(profileId);

		jdbcTemplate.update(insertProfileSql,
				profileId,
				updModel.get("profileName"),
				"N",
				"N",
				"N",
				loginUser,
				new Date(),
				updModel.get("planLevel")
				);
//		sysProgramProfileDAO.save(sysProgramProfileEntity);
		
		String insertSql = 
				"INSERT INTO SYS_PROGRAM_PROFILE_PRIVILEGE(ID,PRIVILEGE_CODE,PROFILE_ID,ALLOW_FLAG) "
				+ "VALUES(?,?,?,?) ";
		String sql= "SELECT T1.PRIVILEGE_CODE FROM SYS_PRIVILEGE T1 WHERE IS_ACTIVE='Y' ORDER BY ORDER_SEQ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			String PRIVILEGE_CODE = ObjectConverter.convert2String(map.get("PRIVILEGE_CODE"));
			jdbcTemplate.update(insertSql,
					com.gnomon.pdms.common.PDMSCommon.generateUUID(),
					PRIVILEGE_CODE,
					profileId,
					"N"
					);
			
		}
		
	}
	
	/**
	 * 角色管理数据删除
	 */
	public void deleteProjectrolemanager(String sysId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		List<Object> params = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SYS_PROGRAM_PROFILE SET");
		sql.append(" DELETE_BY = ?");
		sql.append(",DELETE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(sysId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		StringBuffer sqlnn = new StringBuffer();
		sqlnn.append(" UPDATE SYS_PROGRAM_PROFILE_PRIVILEGE SET");
		sqlnn.append(" DELETE_BY = ?");
		sqlnn.append(",DELETE_DATE = SYSDATE");
		sqlnn.append(" WHERE");
		sqlnn.append(" PROFILE_ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(sysId);
		jdbcTemplate.update(sqlnn.toString(), params.toArray());
	}
	
	/**
	 * 【项目管理-角色管理】 默认项目角色编辑
	 */
	public void editeProProjectrolemanager(String sysId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		//		for (Map<String, String> array : updModel) {
			//更新
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE SYS_PROGRAM_PROFILE SET");
			sql.append(" UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(",DEFAULT_FLAG = 'N'");
			sql.append(" WHERE");
			sql.append(" DEFAULT_FLAG = 'Y'");
			List<Object> params = new ArrayList<Object>();
			params.add(loginUser);
			jdbcTemplate.update(sql.toString(), params.toArray());
			
			sql = new StringBuffer();
			sql.append(" UPDATE SYS_PROGRAM_PROFILE SET");
			sql.append(" UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(",DEFAULT_FLAG = 'Y'");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			params = new ArrayList<Object>();
			params.add(loginUser);
			params.add(sysId);
			jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 【项目管理-权限管理】一数据取得
	 */
	public List<SysProgramProfilePriviVMEntity> getProjectrolemanagerPower(String profileId) {
        String hql = "FROM SysProgramProfilePriviVMEntity WHERE profileId = ?";
		List<SysProgramProfilePriviVMEntity> result = 
				this.sysProgramProfilePriviVMDAO.find(hql, profileId);
		return result;
	}
	
	/**
	 * 权限编辑
	 */
	public void editeProjectrolemanagerPower(String sysId,String allowFlag) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();

		//更新
		List<Object> params = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SYS_PROGRAM_PROFILE_PRIVILEGE SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",ALLOW_FLAG = ?");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(allowFlag);
		params.add(sysId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	
	/**
	 * 权限删除
	 */
	public void deleteProjectrolemanagerPower(String profileId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();

		//更新
		List<Object> params = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SYS_PROGRAM_PROFILE_PRIVILEGE SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(",ALLOW_FLAG = 'N' ");
		sql.append(" WHERE");
		sql.append(" PROFILE_ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(profileId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	public void savePermission(String profileId,List<Map<String, String>> list) {
		this.deleteProjectrolemanagerPower(profileId);
		for(Map<String, String> map : list){
			if(StringUtils.isNotEmpty(map.get("profilePrivilegeId"))){
				this.editeProjectrolemanagerPower(map.get("profilePrivilegeId"), ObjectConverter.convertBoolean2String(map.get("checked")));
			}
		}

	}
	
	public List<Map<String, Object>> getPermissionMenuList(String parentId,String profileId){
		if(parentId == null){
			return jdbcTemplate.queryForList("SELECT * FROM V_PM_PROGRAM_TREE_PROFILE_PRIV  WHERE  PARENT_ID is null and PROFILE_ID is null order by SEQ  ");
		}else{
			return jdbcTemplate.queryForList("SELECT * FROM V_PM_PROGRAM_TREE_PROFILE_PRIV  WHERE  PARENT_ID = ? and (PROFILE_ID = ? or PROFILE_ID is null) order by SEQ  ",parentId,profileId);
		}
	}
	
}
