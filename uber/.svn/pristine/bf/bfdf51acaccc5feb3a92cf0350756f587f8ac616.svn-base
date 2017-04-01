package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.dao.SysProgramProfileVMDAO;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.ObsUserVMDAO;
import com.gnomon.pdms.entity.ObsUserVMEntity;

@Service
@Transactional
public class ImsOrgUserService {
	
	@Autowired
	private SysProgramProfileVMDAO sysProgramProfileVMDAO;
	
	@Autowired
	private ObsUserVMDAO obsUserVMDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 责任和验证工程师下拉列表框数据查询
	 * @param programVehicleId 项目车型ID（子项目ID）
	 * @param obsId 项目组织ID
	 */
	public List<ObsUserVMEntity> getEngineers(String programVehicleId, String programObsId) {
		//需要USERID and USERNAME
		List<ObsUserVMEntity> list = obsUserVMDAO.find("from ObsUserVMEntity where programVehicleId=? and obsId=? and profileName=?",programVehicleId,programObsId,"工程师");		
		return list;
	}
	
	/**
	 * 审核人 和 批准人下拉列表框 数据取得(责任工程师上级领导)
	 */
	public List<ObsUserVMEntity> getEngineerLeads(String programVehicleId, String programObsId) {
		List<ObsUserVMEntity> list = obsUserVMDAO.find("from ObsUserVMEntity where programVehicleId=? and obsId=?",programVehicleId,programObsId);		
		return list;
	}
	
	/**
	 * 质量经理USERID查询
	 * @param programId 项目ID
	 */
	public String getQualityManagerUserId(String programVehicleId) {
		
		String result = "";
		List<ObsUserVMEntity> list = obsUserVMDAO.find("from ObsUserVMEntity where programVehicleId=? and profileName=?",programVehicleId,"质量经理");		
		if(list.size()>0){
			result = list.get(0).getUserNo();
		}
        return result;
	}
	
	/**
	 * 责任和验证部门专业经理USERID查询
	 * @param programId 项目ID
	 */
	public String getDepartmentPMUserId(String programObsId) {
		
		/*
		 * 查询专业领域OBS下的专业经理
		 */

		String sql = "SELECT SYS_GET_USERID_BY_ID(PKG_PM_OBS.OBS_FN_GROUP_MGR(?)) AS MGR_USERID FROM DUAL";
		Map<String, Object> mgrMap =
				this.jdbcTemplate.queryForMap(sql, programObsId);
		return PDMSCommon.nvl(mgrMap.get("MGR_USERID"));
	}

	/**
	 * 工程师上级领导USERID查询
	 * @param programId 项目ID
	 */
	public String getEngineerLeadUserId(String programObsId) {
		/*
		 * 默认返回专业经理
		 */
		
        return getDepartmentPMUserId(programObsId);
	}
	
	/**
	 * 验证部门项目经理USERID查询
	 * @param programId 项目ID
	 */
	public String getPMUserId(String programObsId) {
		
		/*
		 * 查询专业领域OBS下的项目经理
		 */

//		String sql = "SELECT SYS_GET_USERID_BY_ID(PKG_PM_OBS.OBS_FN_GROUP_MGR(?)) AS MGR_USERID FROM DUAL";
//		Map<String, Object> mgrMap =
//				this.jdbcTemplate.queryForMap(sql, programObsId);
//		return PDMSCommon.nvl(mgrMap.get("MGR_USERID"));
		return "admin";
	}
	
}
