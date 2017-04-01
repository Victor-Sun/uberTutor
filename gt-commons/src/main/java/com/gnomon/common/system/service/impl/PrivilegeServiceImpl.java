package com.gnomon.common.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.web.SessionData;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 是否具有指定的系统权限
	 */
	@Override
	public boolean hasSystemPrivilege(String userId, String privilegeCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT");
		sql.append(" PKG_PERMISSION.HAS_SYSTEM_PRIV(?, ?) AS PRIVILEGE");
		sql.append(" FROM");
		sql.append(" DUAL");
		paramList.add(userId);
		paramList.add(privilegeCode);
		int privilege = this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
		return privilege == 1;
	}

	/**
	 * 是否具有指定的项目权限
	 */
	@Override
	public boolean hasProgramPrivilege(String userId, String programId, String privilegeCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT");
		sql.append(" PKG_PERMISSION.HAS_PROGRAM_PRIV(?, ?, ?) AS PROGRAM_PRIVILEGE");
		sql.append(" FROM");
		sql.append(" DUAL");
		paramList.add(userId);
		paramList.add(programId);
		paramList.add(privilegeCode);
		int privilege = this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray());
		return privilege == 1;
	}

	@Override
	public Map<String, List<String>> getProgramPrivileges(String userId, String programId) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<Map<String, Object>> list = null;
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		
		sql.append(" SELECT ");
//		sql.append(" T2.PROGRAM_VEHICLE_ID");
		sql.append(" T4.PRIVILEGE_CODE");
		sql.append(" FROM");
		sql.append(" PM_USER_OBS T1");
		sql.append(",PM_OBS T2");
		sql.append(",SYS_PROGRAM_PROFILE T3");
		sql.append(",SYS_PROGRAM_PROFILE_PRIVILEGE T4");
		sql.append(" WHERE");
		sql.append(" T1.OBS_ID = T2.ID");
		sql.append(" AND T1.PROFILE_ID = T3.ID");
		sql.append(" AND T3.ID = T4.PROFILE_ID");
		sql.append(" AND T1.USER_ID = ?");
		sql.append(" AND T2.PROGRAM_ID = ?");
		sql.append(" AND T4.ALLOW_FLAG = 'Y'");
		sql.append(" AND T1.DELETE_BY IS NULL");
		sql.append(" AND T2.DELETE_BY IS NULL");
		sql.append(" GROUP BY  T4.PRIVILEGE_CODE ");
		paramList.add(userId);
		paramList.add(programId);
		list = this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
		for (Map<String, Object> map : list) {
//			String programVehicleId = (String)map.get("PROGRAM_VEHICLE_ID");
			String privilegeCode = (String)map.get("PRIVILEGE_CODE");
			if(! result.containsKey(programId)) {
				result.put(programId, new ArrayList<String>());
			}
			result.get(programId).add(privilegeCode);
		}
		
		
		// 用户具有查看所有项目权限、则赋予其所有查看组件权限
		if (this.canViewAllProgram(userId)) {
			// 查询所有查看权限Code
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" SELECT DISTINCT");
			sql.append(" CODE");
			sql.append(" FROM");
			sql.append(" PM_PROGRAM_TREE");
			sql.append(" WHERE");
			sql.append(" (NAME LIKE ?");
			sql.append(" OR NAME LIKE ?");
			// 导出节点管理清单
			sql.append(" OR CODE IN('P0302'))");
			sql.append(" AND IS_ACTIVE = 'Y'");
			paramList.add("查看%");
			paramList.add("打印%");
			list = this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
			
			// 查询项目的所有车型
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" SELECT");
			sql.append(" ID");
			sql.append(" FROM");
			sql.append(" PM_PROGRAM_VEHICLE");
			sql.append(" WHERE");
			sql.append(" PROGRAM_ID = ?");
			sql.append(" AND DELETE_BY IS NULL");
			paramList.add(programId);
			List<Map<String, Object>> vehicleList =
					this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
			
			// 整合权限
			for (Map<String, Object> map : vehicleList) {
				List<String> privilegeList = null;
				if (result.get(map.get("ID")) != null) {
					privilegeList = result.get(map.get("ID"));
				} else {
					privilegeList = new ArrayList<String>();
					result.put((String)map.get("ID"), privilegeList);
				}
				for (Map<String, Object> viewMap : list) {
					if (privilegeList.indexOf(viewMap.get("CODE")) < 0) {
						privilegeList.add((String)viewMap.get("CODE"));
					}
				}
			}
		}
		// 权限Code返回
		return result;
	}

	/**
	 * 项目组织单位编辑权限
	 */
	public boolean hasObsPriv(String obsId, String privilegeCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT PKG_PERMISSION.HAS_OBS_PRIV(?, ?, ?) AS OBS_PRIV FROM DUAL");
		paramList.add(loginUser);
		paramList.add(obsId);
		paramList.add(privilegeCode);
		return this.jdbcTemplate.queryForInt(sql.toString(), paramList.toArray()) > 0;
	}
	
	/**
	 * 查看所有项目权限
	 */
	@Override
	public boolean canViewAllProgram(String userId) {
		String sql = "SELECT PKG_PERMISSION.CAN_VIEW_ALL_PROGRAM(?) FROM DUAL";
		int cnt = this.jdbcTemplate.queryForInt(sql, userId);
		return cnt == 1;
	}
	
	/**
	 * 所属项目成员
	 */
	@Override
	public boolean isProgramMember(String userId, String programId) {
		String sql = "SELECT PKG_PERMISSION.IS_PROGRAM_MEMBER(?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, userId, programId) == 1;
	}
	
	/**
	 * 所属项目领导
	 */
	@Override
	public boolean isProgramLeader(String userId, String programId) {
		String sql = "SELECT PKG_PERMISSION.IS_PROGRAM_LEADER(?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, userId, programId) == 1;
	}
	
	/**
	 * 是否具有指定的文件夹权限
	 */
	public boolean hasFolderPrivilege(String userId,
			Long folderId, String privilegeCode) {
		String sql = "SELECT PKG_PERMISSION.HAS_FOLDER_PRIV(?, ?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, userId, folderId, privilegeCode) == 1;
	}
	
	/**
	 * 获取挂牌权限
	 */
	public boolean canListIssue(String issueId, String userId) {
		String sql = "SELECT PKG_IMS.CHECK_PERMISION_BY_LIST_STATUS(?, ?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, issueId, userId, "ISSUE_LIST_STATUS_1") == 1;
	}
	
	/**
	 * 获取摘牌权限
	 */
	public boolean canDelListIssue(String issueId, String userId) {
		String sql = "SELECT PKG_IMS.CHECK_PERMISION_BY_LIST_STATUS(?, ?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, issueId, userId, "ISSUE_LIST_STATUS_2") == 1;
	}
	
	/**
	 * 获取取消挂牌权限
	 */
	public boolean canUndoListIssue(String issueId, String userId) {
		String sql = "SELECT PKG_IMS.CHECK_PERMISION_BY_LIST_STATUS(?, ?, ?) FROM DUAL";
		return this.jdbcTemplate.queryForInt(sql, issueId, userId, null) == 1;
	}

	@Override
	public Map<String, List<String>> getProgramVehiclePrivileges(String userId,
			String programId, String programVehicleId) {

		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<Map<String, Object>> list = null;
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT DISTINCT ");
		sql.append(" T2.PROGRAM_VEHICLE_ID");
		sql.append(",T4.PRIVILEGE_CODE");
		sql.append(" FROM");
		sql.append(" PM_USER_OBS T1");
		sql.append(",PM_OBS T2");
		sql.append(",SYS_PROGRAM_PROFILE T3");
		sql.append(",SYS_PROGRAM_PROFILE_PRIVILEGE T4");
		sql.append(" WHERE");
		sql.append(" T1.OBS_ID = T2.ID");
		sql.append(" AND T1.PROFILE_ID = T3.ID");
		sql.append(" AND T3.ID = T4.PROFILE_ID");
		sql.append(" AND T1.USER_ID = ?");
		sql.append(" AND T2.PROGRAM_ID = ?");
		sql.append(" AND T2.PROGRAM_VEHICLE_ID = ?");
		sql.append(" AND T4.ALLOW_FLAG = 'Y'");
		sql.append(" AND T1.DELETE_BY IS NULL");
		sql.append(" AND T2.DELETE_BY IS NULL");
		paramList.add(userId);
		paramList.add(programId);
		paramList.add(programVehicleId);
		list = this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
		for (Map<String, Object> map : list) {
//			String programVehicleId = (String)map.get("PROGRAM_VEHICLE_ID");
			String privilegeCode = (String)map.get("PRIVILEGE_CODE");
			if(! result.containsKey(programVehicleId)) {
				result.put(programVehicleId, new ArrayList<String>());
			}
			result.get(programVehicleId).add(privilegeCode);
		}
		
		
		// 用户具有查看所有项目权限、则赋予其所有查看组件权限
		if (this.canViewAllProgram(userId)) {
			// 查询所有查看权限Code
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" SELECT DISTINCT");
			sql.append(" CODE");
			sql.append(" FROM");
			sql.append(" PM_PROGRAM_TREE");
			sql.append(" WHERE");
			sql.append(" (NAME LIKE ?");
			sql.append(" OR NAME LIKE ?");
			// 导出节点管理清单
			sql.append(" OR CODE IN('P0302'))");
			sql.append(" AND IS_ACTIVE = 'Y'");
			paramList.add("查看%");
			paramList.add("打印%");
			list = this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
			
			// 查询项目的所有车型
			sql = new StringBuffer();
			paramList = new ArrayList<Object>();
			sql.append(" SELECT");
			sql.append(" ID");
			sql.append(" FROM");
			sql.append(" PM_PROGRAM_VEHICLE");
			sql.append(" WHERE");
			sql.append(" PROGRAM_ID = ?");
			sql.append(" AND DELETE_BY IS NULL");
			paramList.add(programId);
			List<Map<String, Object>> vehicleList =
					this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
			
			// 整合权限
			for (Map<String, Object> map : vehicleList) {
				List<String> privilegeList = null;
				if (result.get(map.get("ID")) != null) {
					privilegeList = result.get(map.get("ID"));
				} else {
					privilegeList = new ArrayList<String>();
					result.put((String)map.get("ID"), privilegeList);
				}
				for (Map<String, Object> viewMap : list) {
					if (privilegeList.indexOf(viewMap.get("CODE")) < 0) {
						privilegeList.add((String)viewMap.get("CODE"));
					}
				}
			}
		}
		// 权限Code返回
		return result;
	
	}
}
