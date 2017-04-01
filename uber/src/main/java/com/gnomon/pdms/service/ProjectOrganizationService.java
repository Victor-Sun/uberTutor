package com.gnomon.pdms.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.ObsUserVMDAO;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMObsTreeVMDAO;
import com.gnomon.pdms.dao.UserObsDAO;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;
import com.gnomon.pdms.procedure.PkgPmObsDBProcedureServcie;

@Service
@Transactional
public class ProjectOrganizationService {

	@Autowired
	private PMObsTreeVMDAO pmObsTreeVMDAO;

	@Autowired
	private ObsUserVMDAO obsUserVMDAO;
	
	@Autowired
	private UserObsDAO userObsDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private PkgPmObsDBProcedureServcie pkgPmObsDBProcedureServcie;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;

	/**
	 * 组织架构一览取得
	 */
	public List<Map<String, Object>> getObsTreeList(
			String programId, String programVehicleId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT T1.*");
		sql.append(",PKG_PERMISSION.CAN_VIEW_OBS(?, T1.ID) AS OBS_CAN_EDIT");
		paramList.add(loginUser);
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_OBS_TREE_BASELINE T1");
			sql.append(" WHERE");
			sql.append(" T1.BASELINE_ID = ?");
			paramList.add(baselineId);
		} else {
			sql.append(" V_PM_OBS_TREE T1");
			sql.append(" WHERE");
			sql.append(" T1.PROGRAM_ID = ?");
			sql.append(" AND T1.PROGRAM_VEHICLE_ID = ?");
			paramList.add(programId);
			paramList.add(programVehicleId);
		}

		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/**
	 * 二级组织架构一览取得
	 */
	public List<Map<String, Object>> getObsTreeFromDept(
			String programId, String programVehicleId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" SELECT T.*");
			sql.append(",PKG_PM_OBS.FN_GROUP_ID(T.OBS_ID) AS FUNCTION_ID");
			sql.append(",PKG_PERMISSION.CAN_VIEW_OBS(?, T.OBS_ID) AS OBS_CAN_EDIT");
			sql.append(" FROM(");
			sql.append(" SELECT DISTINCT");
			sql.append(" T1.*");
			sql.append(",LEVEL AS S_LEVEL");
			sql.append(" FROM");
			sql.append(" PM_OBS_BASELINE T1");
			sql.append(" WHERE");
			sql.append(" T1.DELETE_BY IS NULL START WITH T1.OBS_TYPE_CODE = ?");
			sql.append(" CONNECT BY PRIOR T1.OBS_ID = T1.PARENT_ID");
			sql.append(" ORDER BY LFT, S_LEVEL");
			sql.append(" ) T");
			sql.append(" WHERE");
			sql.append(" T.BASELINE_ID = ?");
			paramList.add(loginUser);
			paramList.add(PDMSConstants.OBS_TYPE_RESP_DEPT);
			paramList.add(baselineId);
		} else {
			sql.append(" SELECT T.*");
			sql.append(",PKG_PM_OBS.FN_GROUP_ID(T.ID) AS FUNCTION_ID");
			sql.append(",PKG_PERMISSION.CAN_VIEW_OBS(?, T.ID) AS OBS_CAN_EDIT");
			sql.append(" FROM(");
			sql.append(" SELECT");
			sql.append(" T1.*");
			sql.append(",LEVEL AS S_LEVEL");
			sql.append(" FROM");
			sql.append(" PM_OBS T1");
			sql.append(" WHERE");
			sql.append(" T1.DELETE_BY IS NULL START WITH T1.OBS_TYPE_CODE = ?");
			sql.append(" CONNECT BY PRIOR T1.ID = T1.PARENT_ID");
			sql.append(" ORDER BY LFT, S_LEVEL");
			sql.append(" ) T");
			sql.append(" WHERE");
			sql.append(" T.PROGRAM_ID = ?");
			sql.append(" AND PROGRAM_VEHICLE_ID = ?");
			paramList.add(loginUser);
			paramList.add(PDMSConstants.OBS_TYPE_RESP_DEPT);
			paramList.add(programId);
			paramList.add(programVehicleId);
		}
		
        return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/**
	 * 项目组织基本信息取得
	 */
	public Map<String, Object> getObsInfo(String obsId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_OBS_BASELINE");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			sql.append(" AND BASELINE_ID = ?");
			params.add(obsId);
			params.add(baselineId);
		} else {
			sql.append(" V_PM_OBS");
			sql.append(" WHERE");
			sql.append(" ID = ?");
			params.add(obsId);
		}
		return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
	}

	/**
	 * 成员一览取得
	 */
	public List<Map<String, Object>> getProjectOrganizationUser(
			String obsId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_OBS_USER_BASELINE");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ?");
			sql.append(" AND BASELINE_ID = ?");
			params.add(obsId);
			params.add(baselineId);
		} else {
			sql.append(" V_PM_OBS_USER");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ? AND (PLAN_LEVEL = ? OR  PLAN_LEVEL IS NULL)" );
			params.add(obsId);
		}
		sql.append(" AND (PROFILE_ID NOT IN(?, ?, ?, ?) OR PROFILE_ID IS NULL) ");
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_PM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_FM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_QM);
		
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public List<Map<String, Object>> getProjectOrganizationUserLevelOne(
			String obsId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_OBS_USER_BASELINE");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ?");
			sql.append(" AND BASELINE_ID = ?");
			params.add(obsId);
			params.add(baselineId);
		} else {
			sql.append(" V_PM_OBS_USER");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ? AND (PLAN_LEVEL = 1 OR  PLAN_LEVEL IS NULL)" );
			params.add(obsId);
		}
		sql.append(" AND (PROFILE_ID NOT IN(?, ?, ?, ?) OR PROFILE_ID IS NULL) ");
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_PM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_FM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_QM);
		
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public List<Map<String, Object>> getProjectOrganizationUserLevelTwo(
			String obsId, String baselineId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		if (PDMSCommon.isNotNull(baselineId)) {
			sql.append(" V_PM_OBS_USER_BASELINE");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ?");
			sql.append(" AND BASELINE_ID = ?");
			params.add(obsId);
			params.add(baselineId);
		} else {
			sql.append(" V_PM_OBS_USER");
			sql.append(" WHERE");
			sql.append(" OBS_ID = ? AND (PLAN_LEVEL = 2 OR  PLAN_LEVEL IS NULL)" );
			params.add(obsId);
		}
		sql.append(" AND (PROFILE_ID NOT IN(?, ?, ?, ?) OR PROFILE_ID IS NULL) ");
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_PM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_FM);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_QM);
		
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 保存组织基本信息
	 */
	public void updateObsInfo(String obsId, String programVehicleId,
			Map<String, String> obsInfo) throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 更新组织基本信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_OBS SET");
		sql.append(" OBS_CODE = ?");
	    sql.append(",OBS_NAME = ?");
		sql.append(",OBS_DESC = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		sql.append(" AND PROGRAM_VEHICLE_ID = ?");
		params = new ArrayList<Object>();
		params.add(obsInfo.get("obsCode"));
		params.add(obsInfo.get("obsName"));
		params.add(obsInfo.get("obsDesc"));
		params.add(loginUser);
		params.add(obsId);
		params.add(programVehicleId);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 项目组织成员更新
	 */
	public void updateObsUserInfo(String programId,String obsId,
			Map<String, String> obsUserInfo) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		
		// 用户+角色重复Check
		sql = new StringBuffer();
		sql.append(" SELECT DISTINCT");
		sql.append(" USER_ID");
		sql.append(",USER_NAME");
		sql.append(" FROM");
		sql.append(" V_PM_OBS_USER");
		sql.append(" WHERE");
		sql.append(" USER_ID = ?");
		sql.append(" AND OBS_ID = ?");
		sql.append(" AND PROFILE_ID = ?");
		List<Map<String, Object>> userList = this.jdbcTemplate.queryForList(
				sql.toString(), obsUserInfo.get("userId"), obsId, obsUserInfo.get("profileId"));
		if (userList != null && userList.size() > 0) {
			throw new RuntimeException("人员和角色重复！");
		} 
	
		
		// 更新组织基本信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_USER_OBS SET");
		sql.append(" PROFILE_ID = ?");
		sql.append(",UPDATE_BY = ?"); 
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(obsUserInfo.get("profileId"));
		params.add(loginUser);
		params.add(obsUserInfo.get("id"));
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		//判断更新的是项目经理  项目总监
		if (PDMSConstants.SYS_PROGRAM_PROFILE_PM.equals(
				obsUserInfo.get("profileId"))) {
			// 更新项目经理信息
			sql = new StringBuffer();
			sql.append(" UPDATE PM_PROGRAM_BASEINFO SET");
			sql.append(" PM = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" PROGRAM_ID = ?");
			params = new ArrayList<Object>();
			params.add(obsUserInfo.get("id"));
			params.add(loginUser);
			params.add(programId);
			jdbcTemplate.update(sql.toString(), params.toArray());
		} else if (PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR.equals(
				obsUserInfo.get("profileId"))) {
			// 更新项目总监信息
			sql = new StringBuffer();
			sql.append(" UPDATE PM_PROGRAM_BASEINFO SET");
			sql.append(" DIRECTOR = ?");
			sql.append(",UPDATE_BY = ?");
			sql.append(",UPDATE_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" PROGRAM_ID = ?");
			params = new ArrayList<Object>();
			params.add(obsUserInfo.get("id"));
			params.add(loginUser);
			params.add(programId);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	}
	
	/**
	 * 项目组织成员追加
	 */
	public String createObsUserInfo(String programId, final String obsId,
			List<Map<String, String>> obsUserList) {
		StringBuffer result = new StringBuffer();
		StringBuffer sql = null;
		// 登录用户取得
		final String loginUser = SessionData.getLoginUserId();
		
		final List<Map<String, String>> updUserList = obsUserList;
		
		// 登录用户
		sql = new StringBuffer();
		sql.append(" INSERT INTO PM_USER_OBS(");
		sql.append(" ID");
		sql.append(",USER_ID");
		sql.append(",OBS_ID");
		sql.append(",PROFILE_ID");
		sql.append(",DEFAULT_FLAG");
		sql.append(",CREATE_BY");
		sql.append(",CREATE_DATE");
		sql.append(") VALUES (");
		sql.append(" ?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",?");
		sql.append(",SYSDATE)");
		
		this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {  
            public int getBatchSize() {  
                return updUserList.size();
            }  
            public void setValues(PreparedStatement ps, int i) throws SQLException {  
            	Map<String, String> obsUser = updUserList.get(i);
            	ps.setString(1, PDMSCommon.generateUUID());
            	ps.setString(2, obsUser.get("id"));
            	ps.setString(3, obsId);
            	ps.setString(4, null);
            	ps.setString(5, "Y");
            	ps.setString(6, loginUser);
            }
        });
		
		return result.toString();
	}
	
	/**
	 * 删除人员信息
	 */
	public void deleteObsUserInfo(String id, Map<String, String> model) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		String targetUserId = null;
		if (PDMSCommon.isNotNull(model.get("taskOwner"))) {
			targetUserId = model.get("taskOwner");
		}
		// 删除用户
		this.pkgPmObsDBProcedureServcie.deleteObsUser(id, targetUserId, loginUser);
	}
	
	/**
	 * 项目组织追加
	 */
	public String createObsInfo(String programId, String parentId,
			String programVehicleId, Map<String, String> obsInfo) {
		// 新建组织
		Map<String, Object> result = this.pkgPmObsDBProcedureServcie.newObs(
				programId, programVehicleId, parentId, obsInfo.get("obsCode"),
				obsInfo.get("obsName"), null, null, null);
		// 返回新建组织ID
		return PDMSCommon.nvl(result.get(PkgPmObsDBProcedureServcie.KEY_NO_OBS_ID));
	}
	
	/**
	 * 删除组织信息
	 */
	public void deleteObsInfo(String obsId) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 删除组织
		this.pkgPmDBProcedureServcie.removePvObs(obsId, loginUser);

	}
	
	/**
	 * 项目组织关联任务信息取得
	 */
	public List<Map<String, Object>> getObsTaskList(String obsId, String taskOwner) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TASK_VEHICLE");
		sql.append(" WHERE");
		sql.append(" OBS_ID IN (SELECT ID FROM PM_OBS WHERE FUNCTION_GROUP_ID IN (");
		sql.append(" SELECT FUNCTION_GROUP_ID FROM PM_OBS WHERE ID = ?)");
		sql.append(" AND DELETE_BY IS NULL)");
		sql.append(" AND TASK_OWNER = ?");
		params.add(obsId);
		params.add(taskOwner);
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 专业领域一览取得
	 */
	public List<Map<String, Object>> getObsPlanList(String programVehicleId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT T1.*");
		sql.append(",PKG_PERMISSION.CAN_VIEW_OBS(?, ID) AS OBS_CAN_EDIT");
		sql.append(" FROM");
		sql.append(" V_PM_OBS_PLAN T1");
		sql.append(" WHERE");
		sql.append(" T1.PROGRAM_VEHICLE_ID = ?");
		params.add(loginUser);
		params.add(programVehicleId);
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 专业领域信息取得
	 */
	public Map<String, Object> getObsPlanInfo(String obsId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_OBS_PLAN");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params.add(obsId);
		try {
			return this.jdbcTemplate.queryForMap(sql.toString(), params.toArray());
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<String, Object>();
		}
	}
}