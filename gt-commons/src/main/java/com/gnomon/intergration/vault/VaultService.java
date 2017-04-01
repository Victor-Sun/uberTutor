/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.vault;

import java.io.InputStream;
import java.io.OutputStream;




/**
 * @author frank
 *
 */
public interface VaultService {

	public static final String DEFAULT_VAULT_WORKSPACE="vault";

//	/**
//	 * 创建文件
//	 * @param file
//	 * @param content
//	 * @param userid
//	 * @return fileId 如果创建成功返回文件ID，如果失败返回0
//	 * @throws Exception 创建失败抛出异常
//	 */
//	String createFile(String workspaceName,String filename,byte[]content,String userid) throws Exception;

	/**
	 * 创建文件
	 * @param workspaceName
	 * @param filename
	 * @param is
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	String createFile(String workspaceName,String filename,InputStream is,String userid);
	
//	/**
//	 * 更新文件
//	 * @param file
//	 * @param content
//	 * @throws Exception 更新失败抛出异常
//	 */
//	void updateFile(String workspaceName,String fileId,byte[]content,String userid) throws Exception;
	
	/**
	 * 更新文件内容
	 * @param workspaceName
	 * @param fileId
	 * @param is
	 * @param userid
	 * @throws Exception
	 */
	void updateFileContent(String workspaceName,String fileId,InputStream is,String userid);

//	/**
//	 * 取得文件
//	 * @param fileId
//	 * @return
//	 * @throws Exception 获取失败抛出异常
//	 * @deprecated
//	 * @see com.gnomon.intergration.vault.VaultService#getFileContent(String, String, OutputStream)
//	 */
//	VaultFile getFile(String workspaceName,String fileId) throws Exception;
	
	/**
	 * 取得文件内容
	 * @param workspaceName
	 * @param fileId
	 * @param os
	 * @throws Exception
	 */
	void getFileContent(String workspaceName,String fileId,OutputStream os);

	/**
	 * Get File 
	 * @param workspaceName
	 * @param fileId
	 * @return
	 */
	VaultFile getFile(String workspaceName,String fileId);
	/**
	 * 删除文件
	 * @param fileId
	 * @throws Exeption
	 */
	void deleteFile(String workspaceName,String fileId);
	
	/**
	 * 查询文件
	 * @param filenameFilter
	 * @return
	 */
//	List<VaultFile> searchFile(String workspaceName,String filenameFilter) throws Exception;
	
//	/**
//	 * 得到文件的下载URL 带A标记
//	 * @param fileId
//	 * @param userId
//	 * @param sessionId
//	 * @return
//	 * @throws Exception
//	 */
//	String getFileDownloadLinkURL(long fileId,String userId,String sessionId) throws Exception;
	
	
//	/**
//	 * 得到文件的下载URL
//	 * @param fileId
//	 * @param userId
//	 * @param sessionId
//	 * @return
//	 * @throws Exception
//	 */
//	URL getFileDownloadURL(long fileId,String userId,String sessionId) throws Exception;

	 
}
