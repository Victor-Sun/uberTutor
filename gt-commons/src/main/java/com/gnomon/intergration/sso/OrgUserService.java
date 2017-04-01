/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

import java.util.List;
import java.util.Map;

/**
 * @author frank
 *
 */
public interface OrgUserService {

	/**
	 * 获得用户信息
	 * @param userId
	 * @return
	 */
	SSOUserInfo getUserInfo(String userId)  throws SSOIntergrationException;
	
	/**
	 * 获得用户信息
	 * @param username
	 * @return
	 * @throws SSOIntergrationException
	 */
	String getUseridByName(String username)  throws SSOIntergrationException;
	
	/**
	 * 获得用户所在部门
	 * @param userId
	 * @return
	 * @throws SSOIntergrationException
	 */
	SSODepartment getUserDepartmen(String userId) throws SSOIntergrationException;
	
	/**
	 * 获得角色
	 * @param roleId
	 * @return
	 * @throws SSOIntergrationException
	 */
	SSORole getRole(String roleId)   throws SSOIntergrationException;
	
	/**
	 * 获得部门
	 * @param departmentId
	 * @return
	 * @throws SSOIntergrationException
	 */
	SSODepartment getDepartment(String departmentId)   throws SSOIntergrationException;
	
	/**
	 * 获得用户角色列表
	 * @param userId
	 * @return
	 */
	List<SSORole> getUserRoleList(String userId)  throws SSOIntergrationException;
	
	/**
	 * 获取部门中的用户
	 * @param departmentId
	 * @return
	 */
	List<SSOUserInfo> getUserListByDepartmentId(String departmentId)  throws SSOIntergrationException;
	
	/**
	 * 获取部门的子部门
	 * @param parentDepartmentId 
	 * @return 如果参数为空，返回最上级部门
	 */
	List<SSODepartment> getSubDepartmentList(String departmentId)  throws SSOIntergrationException;
	/**
	 * 获取部门的子部门
	 * @param parentDepartmentId 
	 * @return 如果参数为空，返回最上级部门
	 */
	List<SSODepartment> getAllSubDepartmentList(String departmentId)  throws SSOIntergrationException;
	
	/**
	 * 获得所有角色
	 * @return
	 */
	List<SSORole> getRoleList()  throws SSOIntergrationException;

	/**
	 * 获得用户所在部门和角色（包括兼任）
	 * @param userId
	 * @return
	 * @throws SSOIntergrationException
	 */
	Map<SSODepartment,List<SSORole>> getUserDepartmentAndRole(String userId) throws SSOIntergrationException;
	
	/**
	 * 获得用户所在部门（包括兼职部门）
	 * @param userId
	 * @return
	 * @throws SSOIntergrationException
	 */
	List<SSODepartment> getUserDepartmentList(String userId) throws SSOIntergrationException;
	
	/**
	 * 获得用户所有的角色（包括兼任角色）
	 * @param userId
	 * @return
	 * @throws SSOIntergrationException
	 */
	List<SSORole> getUserAllRoleList(String userId) throws SSOIntergrationException;

}
