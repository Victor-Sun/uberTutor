/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.integration.sso;

import java.util.List;
import java.util.Map;

/**
 * @author frank
 *
 */
public interface OrgUserService {

	/**
	 * 鑾峰緱鐢ㄦ埛淇℃�?
	 * @param userId
	 * @return
	 */
	SSOUserInfo getUserInfo(String userId)  throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鐢ㄦ埛淇℃�?
	 * @param username
	 * @return
	 * @throws SSOIntegrationException
	 */
	String getUseridByName(String username)  throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鐢ㄦ埛鎵�鍦ㄩ儴闂�?
	 * @param userId
	 * @return
	 * @throws SSOIntegrationException
	 */
	SSODepartment getUserDepartmen(String userId) throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱瑙掕壊
	 * @param roleId
	 * @return
	 * @throws SSOIntegrationException
	 */
	SSORole getRole(String roleId)   throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱閮ㄩ棬
	 * @param departmentId
	 * @return
	 * @throws SSOIntegrationException
	 */
	SSODepartment getDepartment(String departmentId)   throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鐢ㄦ埛瑙掕壊鍒楄�??
	 * @param userId
	 * @return
	 */
	List<SSORole> getUserRoleList(String userId)  throws SSOIntegrationException;
	
	/**
	 * 鑾峰彇閮ㄩ棬涓殑鐢ㄦ埛
	 * @param departmentId
	 * @return
	 */
	List<SSOUserInfo> getUserListByDepartmentId(String departmentId)  throws SSOIntegrationException;
	
	/**
	 * 鑾峰彇閮ㄩ棬鐨勫瓙閮ㄩ棬
	 * @param parentDepartmentId 
	 * @return 濡傛灉鍙傛暟涓虹┖锛岃繑鍥炴渶涓婄骇閮ㄩ�?
	 */
	List<SSODepartment> getSubDepartmentList(String departmentId)  throws SSOIntegrationException;
	/**
	 * 鑾峰彇閮ㄩ棬鐨勫瓙閮ㄩ棬
	 * @param parentDepartmentId 
	 * @return 濡傛灉鍙傛暟涓虹┖锛岃繑鍥炴渶涓婄骇閮ㄩ�?
	 */
	List<SSODepartment> getAllSubDepartmentList(String departmentId)  throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鎵�鏈夎鑹�
	 * @return
	 */
	List<SSORole> getRoleList()  throws SSOIntegrationException;

	/**
	 * 鑾峰緱鐢ㄦ埛鎵�鍦ㄩ儴闂ㄥ拰瑙掕壊锛堝寘鎷吋浠伙級
	 * @param userId
	 * @return
	 * @throws SSOIntegrationException
	 */
	Map<SSODepartment,List<SSORole>> getUserDepartmentAndRole(String userId) throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鐢ㄦ埛鎵�鍦ㄩ儴闂紙鍖呮嫭鍏艰亴閮ㄩ棬锛�?
	 * @param userId
	 * @return
	 * @throws SSOIntegrationException
	 */
	List<SSODepartment> getUserDepartmentList(String userId) throws SSOIntegrationException;
	
	/**
	 * 鑾峰緱鐢ㄦ埛鎵�鏈夌殑瑙掕壊锛堝寘鎷�?吋浠昏鑹诧�?
	 * @param userId
	 * @return
	 * @throws SSOIntegrationException
	 */
	List<SSORole> getUserAllRoleList(String userId) throws SSOIntegrationException;

}
