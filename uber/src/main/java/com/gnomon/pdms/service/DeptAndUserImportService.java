package com.gnomon.pdms.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 导入部门和用户
 * @author dev
 *
 */
@Service
@Transactional
public class DeptAndUserImportService {

	/**
	 * 从Excel导入部门和用户信息
	 * @param programId
	 * @param excelFile
	 */
	public void importDeptAndUserFromExcel(String programId,File excelFile){
		
	}
	
	/**
	 * 从BAM同步部门和用户信息
	 * @param programId
	 */
	public void syncDeptAndUserFromBAM(String programId){
		
	}
}
