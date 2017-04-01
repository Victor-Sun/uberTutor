package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.DocumentIndexVMDAO;
import com.gnomon.pdms.dao.PMObsDAO;
import com.gnomon.pdms.dao.PMProgramVMDAO;
import com.gnomon.pdms.dao.PMProgramVehicleDAO;
import com.gnomon.pdms.dao.ProgramBaseinfoDAO;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.UserObsDAO;
import com.gnomon.pdms.entity.DocumentIndexVMEntity;
import com.gnomon.pdms.entity.ProgramBaseinfoEntity;
import com.gnomon.pdms.procedure.PkgPmDBProcedureServcie;

@Service
@Transactional
public class ProjectBaseInfoService {

	@Autowired
	private PMProgramVMDAO pmProgramVMDAO;
	
	@Autowired
	private PMProgramVehicleDAO pmProgramVehicleDAO;
	
	@Autowired
	private ProgramBaseinfoDAO programBaseinfoDAO;
	
	@Autowired
	private ProgramDAO programDAO;
	
	@Autowired
	private DocumentIndexVMDAO documentIndexVMDAO;
	
	@Autowired
	private PMObsDAO pmObsDAO; 
	
	@Autowired
	private UserObsDAO userObsDAO;
	
	@Autowired
	private ResequenceService resequenceService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmDBProcedureServcie pkgPmDBProcedureServcie;
	
	/**
	 * 项目基本信息取得
	 */
	public Map<String, Object> getProjectBaseInfo(String programId) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), programId);
		if (list.size() == 0) {
			throw new Exception("该项目已经不存在！");
		}
		return list.get(0);
    }
	
	/*
	 * SOP时间取得
	 */
	public List<Map<String, Object>> getProjectPlanSOP(
			String programId, boolean judgePrivilege) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE_BASE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		paramList.add(programId);
		if (judgePrivilege) {
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, ID) = 1");
			paramList.add(loginUser);
		}
		sql.append(" AND DELETE_BY IS NULL");
		sql.append(" ORDER BY CREATE_DATE");
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
	
	/*
	 * 开发范围信息取得
	 */
	public List<DocumentIndexVMEntity> getProgramDevelopmentScope(String programId) {
		String hql = "FROM DocumentIndexVMEntity WHERE programId = ? AND sourceType = ?";
		List<DocumentIndexVMEntity> result =
				this.documentIndexVMDAO.find(hql, programId, "DOCUMENT_SOURCE_TYPE_1");
        return result;
    }
	
	/*
	 * 备案记录信息取得
	 */
	public List<DocumentIndexVMEntity> getProgramRecordList(String programId) {
		String hql = "FROM DocumentIndexVMEntity WHERE programId = ? AND sourceType = ?";
		List<DocumentIndexVMEntity> result =
				this.documentIndexVMDAO.find(hql, programId, "DOCUMENT_SOURCE_TYPE_2");
        return result;
    }
	
	/*
	 * 附件
	 */
	public List<DocumentIndexVMEntity> getProgramEnclosureList(String programId) {
		String hql = "FROM DocumentIndexVMEntity WHERE programId = ? AND sourceType = ?";
		List<DocumentIndexVMEntity> result =
				this.documentIndexVMDAO.find(hql, programId, "DOCUMENT_SOURCE_TYPE_3");
        return result;
    }

	/**
	 *保存基本信息
	 */
	public void saveBaseInfo(String programId, Map<String, String> projectInfo) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 保存历史记录
		this.pkgPmDBProcedureServcie.createPmBiBaseline(programId, "系统保存变更记录", "自动保存");
		
		// 更新项目信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_PROGRAM SET");
		sql.append(" PROGRAM_NAME = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(projectInfo.get("programName"));
		params.add(loginUser);
		params.add(programId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 更新项目基本信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_PROGRAM_BASEINFO SET");
		sql.append(" UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		// 画面信息
		sql.append(",DIRECTOR = ?");// 项目总监
		sql.append(",VEHICLE_TYPE_ID = ?");// 车型类型
		sql.append(",VEHICLE_PLATFORM_ID = ?");// 车型平台
		sql.append(",MANUFACTURE_COMP_ID = ?");// 生产单位
		sql.append(",PROGRAM_LEVEL = ?");// 级别
		sql.append(",DEV_STATUS_CODE = ?");// 开发状态
		sql.append(",PRODUCT_POSITIONING = ?");// 产品定位
		sql.append(",DEV_TYPE_ID = ?");// 开发类型
		//sql.append(",PROGRAM_PRIORITY_CODE = ?");// 优先级
		sql.append(",IS_OUT_CONTRACT = ?");// 签订合同
		sql.append(",MEMO = ?");// 备注
		// 条件
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(projectInfo.get("director"));
		params.add(projectInfo.get("vehicleType"));
		params.add(projectInfo.get("vehiclePlatform"));
		params.add(projectInfo.get("ManufactureCompId"));
		params.add(projectInfo.get("programLevel"));
		params.add(projectInfo.get("devStatusCode"));
		params.add(projectInfo.get("productPositioning"));
		params.add(projectInfo.get("devTypeId"));
		//params.add(projectInfo.get("programPriorityCode"));
		if ("true".equals(projectInfo.get("isOutContract"))) {
			params.add("Y");
		} else {
			params.add("N");
		}
		params.add(projectInfo.get("programMemo"));
		params.add(programId);
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		//项目总监保存
		this.saveDirector(programId, projectInfo.get("director"));
	}
	
	/**
	 * 更新车型信息
	 */
	public void updateVehicle(String programId, Map<String, String> vehicleInfo) throws Exception {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		// 车型信息验证
		this.checkVehicleInfo(programId, vehicleInfo.get("id"),
				vehicleInfo.get("vehicleCode"),
				vehicleInfo.get("vehicleName"));
		
		// 更新车型信息
		sql = new StringBuffer();
		sql.append(" UPDATE PM_PROGRAM_VEHICLE SET");
		sql.append(" VEHICLE_CODE = ?");
		sql.append(",VEHICLE_NAME = ?");
		sql.append(",ZC_SOP_DATE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(vehicleInfo.get("vehicleCode"));
		params.add(vehicleInfo.get("vehicleName"));
		params.add(DateUtils.strToDate(vehicleInfo.get("zcSopDate")));
		params.add(loginUser);
		params.add(vehicleInfo.get("id"));
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 追加车型信息
	 */
	public void addVehicle(String programId, Map<String, String> vehicleInfo) throws Exception {
		// 车型信息验证
		this.checkVehicleInfo(programId, null, vehicleInfo.get("vehicleCode"),
				vehicleInfo.get("vehicleName"));
		
		// 模板ID
		String templateId = null;
		if ("true".equals(vehicleInfo.get("chkImportPlan"))) {
			templateId = vehicleInfo.get("programTempId");
        }
		Date sopDate = DateUtils.strToDate(vehicleInfo.get("sopDate"));
		// 新建车型
		this.pkgPmDBProcedureServcie.newProgramVehicle(programId,
				vehicleInfo.get("vehicleCode"), vehicleInfo.get("vehicleName"),
				vehicleInfo.get("pm"), vehicleInfo.get("qm"), SessionData.getLoginUserId(),
				"true".equals(vehicleInfo.get("chkImportObs")) ? "Y" : "N",
				"true".equals(vehicleInfo.get("chkImportVeichlePlan")) ? "Y" : "N",
				vehicleInfo.get("cloneVehicleId"),
				templateId, sopDate);
	}
	
	/**
	 * 删除车型信息
	 */
	public void deleteVehicle(String vehicleId) {
		// 删除车型
		this.pkgPmDBProcedureServcie.deleteProgramVehicle(
				vehicleId, SessionData.getLoginUserId());
	}

	/**
	 * 项目总监保存
	 */
	private void saveDirector(String programId, String director) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		if (PDMSCommon.isNull(director)) {
			return;
		}
		
		// 删除既存项目总监
		sql = new StringBuffer();
		sql.append(" UPDATE PM_USER_OBS SET");
		sql.append(" DELETE_BY = ?");
		sql.append(",DELETE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" PROFILE_ID = ?");
		params = new ArrayList<Object>();
		params.add(loginUser);
		params.add(PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
		
		// 将项目总监保存到所有车型的OBS根节点中
		String sqlObsId = "SELECT ID, PROGRAM_VEHICLE_ID"
						+ " FROM PM_OBS WHERE PROGRAM_ID = ?"
						+ " AND PARENT_ID IS NULL";
		List<Map<String, Object>> obsId = this.jdbcTemplate.queryForList(
				sqlObsId, programId);
		List<String> vehicleList = new ArrayList<String>();
		for (Map<String, Object> map: obsId) {
			if (vehicleList.indexOf(PDMSCommon.nvl(map.get("ID"))) >= 0) {
				continue;
			}
			vehicleList.add(PDMSCommon.nvl(map.get("ID")));		
			// ID取得
			String uuId = PDMSCommon.generateUUID();
			// 保存项目总监信息
			sql = new StringBuffer();
			sql.append(" INSERT INTO PM_USER_OBS(");
			sql.append(" ID");
			sql.append(",USER_ID");
			sql.append(",OBS_ID");
			sql.append(",PROFILE_ID");
			sql.append(",DEFAULT_FLAG");
			sql.append(",CREATE_BY");
			sql.append(",CREATE_DATE)");
			sql.append(" VALUES(");
			sql.append(" ?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",?");
			sql.append(",SYSDATE)");
			params = new ArrayList<Object>();
			params.add(uuId);
			params.add(director);
			params.add(map.get("ID"));
			params.add(PDMSConstants.SYS_PROGRAM_PROFILE_DIRECTOR);
			params.add(PDMSConstants.STATUS_Y);
			params.add(loginUser);
			this.jdbcTemplate.update(sql.toString(), params.toArray());
		}
		
		// 将项目总监更新到所有车型信息中
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" UPDATE PM_PROGRAM_VEHICLE");
		sql.append(" SET DIRECTOR = ?");
		sql.append(" WHERE PROGRAM_ID = ?");
		params.add(director);
		params.add(programId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	public ProgramBaseinfoEntity getProgramBaseinfoEntity(String programId){
		return programBaseinfoDAO.findUniqueBy("programId", programId);
	}
	
	/**
	 * 车型编码、名称Check
	 */
	public void checkVehicleInfo(String programId, String programVehicleId,
			String vehicleCode, String vehicleName) throws Exception {
		// 车型Code重复验证
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		params.add(programId);
		sql.append(" AND VEHICLE_CODE = ?");
		params.add(vehicleCode);
		if (PDMSCommon.isNotNull(programVehicleId)) {
			sql.append(" AND ID <> ?");
			params.add(programVehicleId);
		}
		int cnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
		if (cnt > 0) {
			throw new Exception("车型编码已被占用，请重新指定！");
		}
		
		// 车型名称重复验证
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(*)");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM_VEHICLE");
		sql.append(" WHERE");
		sql.append(" PROGRAM_ID = ?");
		params.add(programId);
		sql.append(" AND VEHICLE_NAME = ?");
		params.add(vehicleName);
		if (PDMSCommon.isNotNull(programVehicleId)) {
			sql.append(" AND ID <> ?");
			params.add(programVehicleId);
		}
		cnt = this.jdbcTemplate.queryForInt(sql.toString(), params.toArray());
		if (cnt > 0) {
			throw new Exception("车型名称已被占用，请重新指定！");
		}
	}
}

