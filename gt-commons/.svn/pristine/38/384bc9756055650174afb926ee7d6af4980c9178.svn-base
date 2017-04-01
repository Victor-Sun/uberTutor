package com.gnomon.common.system.service;

import java.util.List;
import java.util.Map;

import com.gnomon.common.exception.ServiceException;
import com.gnomon.common.system.entity.GTObs;

public interface GTObsService {
	
	public List<GTObs> getObsList() throws ServiceException;
	
	public GTObs getObs(String name) throws ServiceException;
	
	public List<GTObs> getChildrenList(String parentId) throws ServiceException;
	
	public List<GTObs> getRootObsList() throws ServiceException;
	
	public void saveObs(GTObs obs) throws ServiceException;
	
	public void deleteObs(String id) throws ServiceException;
	
	public boolean getChildren(String parentId) throws ServiceException;
	
	public GTObs getGTObs(String id) throws ServiceException;
	
	public Map<String,Object> getDropObs(Long left) throws ServiceException;
	
	public List getUserInfo(String obsId,String query) throws ServiceException;
	
	public List getUserList(String query,String obsId) throws ServiceException;
	
	public void addObsUser(String obsId,String uId,String profileId) throws ServiceException;
	
	public void deleteObsUser(String obsId,String uId,String profileId) throws ServiceException;


	public String getGTObsIdByUserId(String userId) throws ServiceException;
	

	public  List<Map<String,Object>> getProgramTaskList(String obsId) throws ServiceException;
	

	/**
	 * 返回用户有权限访问的OBS_ID列表
	 * @param userid
	 * @return
	 */
	List<String> getUserOBSList(String userid);
	
	
	/**
	 * 插入新节点后更新相关节点的序号
	 * @param previousNodeId 前一个节点的ID
	 * @param insertNodeId 新插入节点的ID
	 * @return
	 */
	void updateSequenceAfterInsert(String previousNodeId,String insertNodeId);
	
	
	/**
	 * 复制OBS
	 * @param sourceObsId
	 * @param targetParentObsId
	 * @param newObsName
	 * @param includeUserObs
	 * @param programId 需要更新OBS_ID的项目
	 * @return
	 */
	String copyObs(String sourceObsId,String targetParentObsId,String newObsName,boolean includeUserObs,String programId);
	
	/**
	 * 复制OBS
	 * @param sourceObsId
	 * @param targetParentObsId
	 * @param newObsName
	 * @return
	 */
	String copyObs(String sourceObsId,String targetParentObsId,String newObsName);
	
	/**
	 * 返回所有子节点
	 * @param parentId
	 * @return
	 */
	List<GTObs> getAllChildrenList(String parentId);

	/**
	 * 返回所有祖先节点
	 * @param obsId
	 * @return
	 */
	List<GTObs> getAllAncestryList(String obsId);
	
	/**
	 * 按名称返回所有子节点
	 * @param parentId
	 * @param obsName 名称
	 * @return
	 */
	List<GTObs> getAllChildrenList(String parentId,String obsName);

}
