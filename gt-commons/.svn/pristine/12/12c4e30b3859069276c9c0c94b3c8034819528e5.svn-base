package com.gnomon.common.system.service;

import java.util.List;

import com.gnomon.common.system.entity.GTDmsAppObs;
import com.gnomon.common.system.entity.GTDmsResourceObs;

public interface DmsAuthorityService {
	
	/**
	 * 添加应用访问权限
	 * @param appId
	 * @param obsId
	 */
	public void addAppAuthority(GTDmsAppObs gtDmsAppObs);
	
	/**
	 * 删除应用访问权限
	 * @param appId
	 * @param obsId
	 */
	public void removeAppAuthority(Long appId,Long obsId);
	
	/**
	 * 删除应用访问权限
	 * @param id
	 */
	public void removeAppAuthority(Long id);
	
	/**
	 * 返回应用OBS授权列表
	 * @param appId
	 * @param obsId
	 * @return
	 */
	public List<GTDmsAppObs> getAppAuthority(Long appId);
	
	/**
	 * 添加资源（文件夹或文档）的访问权限
	 * @param appId
	 * @param obsId
	 */
	public void addResourceAuthority(GTDmsResourceObs gtDmsResourceObs);
	
	/**
	 * 删除资源（文件夹或文档）的访问权限
	 * @param appId
	 * @param obsId
	 */
	public void removeResourceAuthority(Long resourceId,String resourceType,Long obsId);
	
	/**
	 * 删除资源（文件夹或文档）的访问权限
	 * @param id
	 */
	public void removeResourceAuthority(Long id);

	/**
	 * 返回资源OBS授权列表
	 * @param appId
	 * @param obsId
	 * @return
	 */
	public List<GTDmsResourceObs> getResourceAuthority(Long resourceId,String resourceType);
}
