/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.vault.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.intergration.vault.VaultFile;
import com.gnomon.intergration.vault.VaultService;
import com.gnomon.servicewapper.aws.api.WappedAWSBOInstanceAPI;

/**
 * 使用AWS BO表实现的文件库<br>
 * 无版本功能
 * 
 * @author frank
 * 
 */
// @Service(value="AWSVaultService")
@Transactional
public class AWSVaultService implements VaultService {

	private static final String BO_TABLE_FILE = "BO_GT_FILE";// 保存附件表的BO
	private static final String FILE_FIELD_UUID = "fe595af97f51f2132577657cf190e766";// 保存附件表的BO文件附件字段的UUID

	private String boFileTableName = BO_TABLE_FILE;
	private String fileFieldUUID = FILE_FIELD_UUID;

	public String getBoFileTableName() {
		return boFileTableName;
	}

	public void setBoFileTableName(String boFileTableName) {
		this.boFileTableName = boFileTableName;
	}

	public String getFileFieldUUID() {
		return fileFieldUUID;
	}

	public void setFileFieldUUID(String fileFieldUUID) {
		this.fileFieldUUID = fileFieldUUID;
	}

	@Autowired
	private WappedAWSBOInstanceAPI wappedAWSBOInstanceAPI;

	public static void main(String[] args) throws Exception {
		// //init env
		// String userid = "admin";
		// AWSVaultService service = new AWSVaultService();
		// service.setAwsWappedService(new AWSWappedServiceImpl());
		//
		// //begin test
		// byte[] content =
		// "test content test content test content test content test content".getBytes();
		//
		// long fileId = service.createFile("test.txt", content,userid);
		//
		// VaultFile file = service.getFile(fileId);
		// System.out.println(new String(file.getContent()));
		//
		// byte[] contentUpdate =
		// "test content test content test content test content test content updated!!!!!!!!!!!!!!!!"
		// .getBytes();
		//
		// service.updateFile(fileId, contentUpdate,userid);
		// file = service.getFile(fileId);
		// System.out.println(new String(file.getContent()));
		//
		// List<VaultFile> searchFile = service.searchFile("te");
		// for (Iterator iterator = searchFile.iterator(); iterator.hasNext();)
		// {
		// VaultFile gtFile = (VaultFile) iterator.next();
		// System.out.println(gtFile.getFilename());
		// }
		//
		// service.deleteFile(fileId);
		// System.out.println("delete file success.fileId=" + fileId);
	}

	/*
	 * (non-Javadoc)+
	 * 
	 * @see
	 * com.gnomon.common.interfaces.vault.service.FileService#createFile(java
	 * .lang.String, byte[])
	 */
	// @Override
	// public String createFile(String workspaceName,String filename, byte[]
	// content,String userid) throws Exception {
	// int fileId = 0;
	// try {
	// // 创建BO实例
	// Hashtable<String, Object> recordData = new Hashtable<String, Object>();
	// recordData.put("FILENAME", filename);
	// fileId = wappedAWSBOInstanceAPI.createBOData(getBoFileTableName(),
	// recordData, userid);
	// if (fileId > 0) {
	// // 上传附件
	// wappedAWSBOInstanceAPI.upFileByFiled(fileId, content, getFileFieldUUID(),
	// filename);
	// } else {
	// throw new Exception("create file failed! filename=" + filename);
	// }
	// } catch (Exception e) {
	// if(fileId != 0){
	// wappedAWSBOInstanceAPI.removeBOData(getBoFileTableName(), fileId);
	// }
	// throw e;
	// }
	//
	// return String.valueOf(fileId);
	// }

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * com.gnomon.common.interfaces.vault.service.FileService#updateFile(long,
	// * byte[])
	// */
	// @Override
	// public void updateFile(String workspaceName,String fileId, byte[]
	// content,String userid) throws Exception {
	// // 查询O实例
	// Vector v = wappedAWSBOInstanceAPI.getBODatasBySQL(getBoFileTableName(),
	// "where id=" + fileId);
	// if (v.size() == 0) {
	// throw new Exception("File not found. fileId=" + fileId);
	// }
	// Hashtable recordData = (Hashtable) v.get(0);
	// String filename = (String) recordData.get("FILENAME");
	// String boId = (String) recordData.get("ID");
	//
	// // 上传文件
	// wappedAWSBOInstanceAPI.upFileByFiled(Integer.parseInt(fileId), content,
	// getFileFieldUUID(), filename);
	//
	// //更新BO表
	// wappedAWSBOInstanceAPI.updateBOData(BO_TABLE_FILE, recordData,
	// Integer.parseInt(boId));
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gnomon.common.interfaces.vault.service.FileService#getFile(long)
	 */
	// @Override
	// public VaultFile getFile(String workspaceName,String fileId) throws
	// Exception {
	// // 查询O实例
	// Vector v = wappedAWSBOInstanceAPI.getBODatasBySQL(getBoFileTableName(),
	// "where id=" + fileId);
	// if (v.size() == 0) {
	// throw new Exception("文件没有找到。fileId=" + fileId);
	// }
	// Hashtable recordData = (Hashtable) v.get(0);
	// String filename = (String) recordData.get("FILENAME");
	//
	// // 下载文件
	// byte[] content =
	// wappedAWSBOInstanceAPI.downloadFileByFiled(Integer.parseInt(fileId),
	// getFileFieldUUID(), filename);
	// VaultFile file = new VaultFile(fileId, filename, content);
	// return file;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gnomon.common.interfaces.vault.service.FileService#deleteFile(long)
	 */
	@Override
	public void deleteFile(String workspaceName, String fileId) {
		try {
			int r = wappedAWSBOInstanceAPI.removeBOData(getBoFileTableName(), Integer.parseInt(fileId));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 暂时不验证删除的文件是否存在
		// if (r != 1) {
		// throw new Exception("删除文件失败，文件ID：" + fileId);
		// }

	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * com.gnomon.common.vault.service.FileStoreService#searchFile(java.lang
	// * .String)
	// */
	// @SuppressWarnings("rawtypes")
	// @Override
	// public List<VaultFile> searchFile(String workspaceName,String
	// filenameFilter) throws Exception {
	// List<VaultFile> files = new ArrayList<VaultFile>();
	// // 查询O实例
	// @SuppressWarnings("unchecked")
	// Vector<Hashtable> v =
	// wappedAWSBOInstanceAPI.getBODatasBySQL(getBoFileTableName(),
	// "where filename like '%" + filenameFilter + "%'");
	//
	// for (Hashtable recordData : v) {
	//
	// String fileId = (String) recordData.get("ID");
	// String filename = (String) recordData.get("FILENAME");
	//
	// // 下载文件
	// byte[] content = wappedAWSBOInstanceAPI
	// .downloadFileByFiled(Integer.parseInt(fileId), getFileFieldUUID(),
	// filename);
	// VaultFile file = new VaultFile(fileId, filename, content);
	// files.add(file);
	// }
	//
	// return files;
	// }

	@Override
	public String createFile(String workspaceName, String filename, InputStream is, String userid) {
		int fileId = 0;
		try {
			// 创建BO实例
			Hashtable<String, Object> recordData = new Hashtable<String, Object>();
			recordData.put("FILENAME", filename);
			fileId = wappedAWSBOInstanceAPI.createBOData(getBoFileTableName(), recordData, userid);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (is.available() > 0) {
				baos.write(is.read());
			}
			is.close();

			if (fileId > 0) {
				// 上传附件
				wappedAWSBOInstanceAPI.upFileByFiled(fileId, baos.toByteArray(), getFileFieldUUID(), filename);
			} else {
				throw new Exception("create file failed! filename=" + filename);
			}
		} catch (Exception e) {
			if (fileId != 0) {
				try {
					wappedAWSBOInstanceAPI.removeBOData(getBoFileTableName(), fileId);
				} catch (Exception e1) {
					throw new RuntimeException(e);

				}
			}
			throw new RuntimeException(e);

		}

		return String.valueOf(fileId);
	}

	@Override
	public void updateFileContent(String workspaceName, String fileId, InputStream is, String userid) {
		// 查询O实例
		try {
			Vector v = wappedAWSBOInstanceAPI.getBODatasBySQL(getBoFileTableName(), "where id=" + fileId);
			if (v.size() == 0) {
				throw new Exception("File not found. fileId=" + fileId);
			}
			Hashtable recordData = (Hashtable) v.get(0);
			String filename = (String) recordData.get("FILENAME");
			String boId = (String) recordData.get("ID");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (is.available() > 0) {
				baos.write(is.read());
			}
			is.close();
			// 上传文件
			wappedAWSBOInstanceAPI.upFileByFiled(Integer.parseInt(fileId), baos.toByteArray(), getFileFieldUUID(),
					filename);

			// 更新BO表
			wappedAWSBOInstanceAPI.updateBOData(BO_TABLE_FILE, recordData, Integer.parseInt(boId));
		} catch (Exception e) {
			throw new RuntimeException(e);

		}

	}

	@Override
	public void getFileContent(String workspaceName, String fileId, OutputStream os) {
		// TODO Auto-generated method stub

	}

	@Override
	public VaultFile getFile(String workspaceName, String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	// /* (non-Javadoc)
	// * @see com.gnomon.intergration.vault.VaultService#getFileURL(long)
	// */
	// @Override
	// public String getFileDownloadLinkURL(long fileId,String userId,String
	// sessionId) throws Exception {
	// String url = null ;
	// String[] urlAry =
	// wappedAWSBOInstanceAPI.getFileDownloadLinkURLByFiled(userId,sessionId,
	// (int)fileId, getFileFieldUUID());
	// if(urlAry != null && urlAry.length > 0){
	// String tmpUrl = urlAry[0];
	// //旧： <a
	// href='./downfile.wf?sid=&flag1=12528&flag2=7540&filename=antlr-2.7.7.jar&rootDir=FormFile'
	// target=_blank>antlr-2.7.7.jar</a>
	// //新： <a
	// href='/portal/workflow/downfile.wf?sid=&flag1=12528&flag2=7540&filename=antlr-2.7.7.jar&rootDir=FormFile'
	// target=_blank>antlr-2.7.7.jar</a>
	// url = tmpUrl.replace("./downfile.wf", "/portal/workflow/downfile.wf");
	// }
	// return url;
	// }

	/*
	 * // * (non-Javadoc) // * @see
	 * com.gnomon.intergration.vault.VaultService#getFileDownloadURL(long,
	 * java.lang.String) //
	 */
	// @Override
	// public URL getFileDownloadURL(long fileId,String userId,String sessionId)
	// throws Exception{
	// URL url = null;
	// String link = getFileDownloadLinkURL(fileId,userId,sessionId);
	// String urlStr = "";
	// urlStr = link.substring(link.indexOf('\'')+1,link.lastIndexOf('\''));
	// url = new URL(urlStr);
	// return url;
	// }
}
