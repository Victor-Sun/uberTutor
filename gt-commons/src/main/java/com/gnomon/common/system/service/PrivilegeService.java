package com.gnomon.common.system.service;

import java.util.List;
import java.util.Map;

/**
 * 访问控制服务类
 * @author frank
 *
 */
public interface PrivilegeService {
	/**
	 * 是否具有指定的系统权限
	 */
	public boolean hasSystemPrivilege(String userId, String privilegeCode);

	/**
	 * 是否具有指定的权限
	 */
	public boolean hasProgramPrivilege(String userId, String programId, String privilegeCode);

	/**
	 * 查询项目权限
	 * @param userid
	 * @param programId
	 * @return 项目权限列表（Key in Map：programVehicleId，privilegeCode）
	 */
	public Map<String,List<String>> getProgramPrivileges(String userid,String programId);
	
	/**
	 * 查询车型权限
	 * @param userid
	 * @param programId
	 * @param programVehicleId
	 * @return 项目权限列表（Key in Map：programVehicleId，privilegeCode）
	 */
	public Map<String,List<String>> getProgramVehiclePrivileges(String userid,String programId,String programVehicleId);

	/**
	 * 项目组织单位编辑权限
	 */
	public boolean hasObsPriv(String obsId, String privilegeCode);
	
	/**
	 * 查看所有项目权限
	 */
	public boolean canViewAllProgram(String userId);
	
	/**
	 * 所属项目成员
	 */
	public boolean isProgramMember(String userId, String programId);
	
	/**
	 * 所属项目领导
	 */
	public boolean isProgramLeader(String userId, String programId);
	
	/**
	 * 是否具有指定的文件夹权限
	 */
	public boolean hasFolderPrivilege(String userId, Long folderId, String privilegeCode);
	
	/**
	 * 获取挂牌权限
	 */
	public boolean canListIssue(String issueId, String userId);
	
	/**
	 * 获取摘牌权限
	 */
	public boolean canDelListIssue(String issueId, String userId);
	
	/**
	 * 获取取消挂牌权限
	 */
	public boolean canUndoListIssue(String issueId, String userId);


}
