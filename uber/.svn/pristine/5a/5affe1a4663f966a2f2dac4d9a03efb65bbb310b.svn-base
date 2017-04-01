package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.ProgramUsualDAO;
import com.gnomon.pdms.entity.ProgramUsualEntity;
import com.gnomon.pdms.procedure.PkgSysMyWorkgroupDBProcedureServcie;

@Service
@Transactional
public class PersonalSettingsService {

	@Autowired
	private ProgramUsualDAO programUsualDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgSysMyWorkgroupDBProcedureServcie pkgSysMyWorkgroupDBProcedureServcie;

	/**
	 * 首页-个人设置-常用项目(常用项目的值取得)
	 */
	public boolean getCommonProject(String id) {
		String loginUserId = SessionData.getLoginUserId();
		String hql = "FROM ProgramUsualEntity WHERE programId= ? AND userId = ?";
		List<ProgramUsualEntity> result = 
				this.programUsualDAO.find(hql, id, loginUserId);
		if (result.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 首页-个人设置-常用项目check更新
	 */
	public void updatePersonalSettingsEditChange(List<Map<String, String>> updModel) {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		for (Map<String, String> array : updModel) {
			// 登录ID取得
			String id = PDMSCommon.generateUUID();
			if ("true".equals(array.get("commonProject"))) {
				// 插入项目信息
				sql = new StringBuffer();
				sql.append(" INSERT INTO PM_PROGRAM_USUAL (");
				sql.append(" ID");
				sql.append(",PROGRAM_ID");
				sql.append(",USER_ID");
				sql.append(",CREATE_BY");
				sql.append(",CREATE_DATE");
				sql.append(") VALUES (");
				sql.append(" ?");
				sql.append(",?");
				sql.append(",?");
				sql.append(",?");
				sql.append(",SYSDATE)");
				params = new ArrayList<Object>();
				params.add(id);
				params.add(array.get("id"));
				params.add(loginUser);
				params.add(loginUser);
				jdbcTemplate.update(sql.toString(), params.toArray());
			} else {
				// 删除记录
				sql = new StringBuffer();
				sql.append(" DELETE FROM PM_PROGRAM_USUAL");
				sql.append(" WHERE");
				sql.append(" PROGRAM_ID = ?");
				sql.append(" AND USER_ID = ?");
				params = new ArrayList<Object>();
				params.add(array.get("id"));
				params.add(loginUser);
				jdbcTemplate.update(sql.toString(), params.toArray());

			}
		}
	}
	
	/**
	 * 密码验证
	 */
	public boolean checkPassword(String userId, String password) {
		StringBuffer sql = null;
		List<Object> params = null;
		
		sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) FROM SYS_USER");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		sql.append(" AND PASSWORD = ?");
		params = new ArrayList<Object>();
		params.add(userId);
		params.add(EncryptUtil.encrypt(password));
		return this.jdbcTemplate.queryForInt(sql.toString(), params.toArray()) > 0;
	}
	
	/**
	 * 密码修正
	 */
	public void changePersonalSettingsPsw(String userId, Map<String, String> projectInfo) {
		StringBuffer sql = null;
		List<Object> params = null;
		
		// 更新项目信息
		sql = new StringBuffer();
		sql.append(" UPDATE SYS_USER SET");
		sql.append(" PASSWORD = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		params = new ArrayList<Object>();
		params.add(EncryptUtil.encrypt(projectInfo.get("newPws")));
		params.add(userId);
		params.add(userId);
		this.jdbcTemplate.update(sql.toString(), params.toArray());
	}
	
	/**
	 * 我的工作组-取得工作组列表
	 */
	public List<Map<String, Object>> getMyWorkGroupList() {
		StringBuffer sql = null;
		List<Object> params = null;
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_MY_WORKGROUP");
		sql.append(" WHERE");
		sql.append(" USER_ID = ?");
		params.add(loginUser);
		// 返回值
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 我的工作组-取得工作组成员列表
	 */
	public List<Map<String, Object>> getMyWorkGroupMemberList(Long workGroupId) {
		StringBuffer sql = null;
		List<Object> params = null;
		
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_MY_WORKGROUP_MEMBER");
		sql.append(" WHERE");
		sql.append(" WORKGROUP_ID = ?");
		params.add(workGroupId);
		// 返回值
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	/**
	 * 我的工作组-新建工作组
	 */
	public void createMyWorkGroup(Map<String, String> model) {
		// LoginUser取得
		String loginUser = SessionData.getLoginUserId();
		this.pkgSysMyWorkgroupDBProcedureServcie.createWorkgroup(
				model.get("groupName"), loginUser);
	}
	
	/**
	 * 我的工作组-修改工作组名称
	 */
	public void updateMyWorkGroup(Long workGroupId, Map<String, String> model) {
		this.pkgSysMyWorkgroupDBProcedureServcie.renameWorkgroup(
				workGroupId, model.get("groupName"));
	}
	
	/**
	 * 我的工作组-删除工作组
	 */
	public void deleteMyWorkGroup(Long workGroupId) {
		this.pkgSysMyWorkgroupDBProcedureServcie.deleteWorkgroup(workGroupId);
	}
	
	/**
	 * 我的工作组-添加工作组成员
	 */
	public String addMyWorkgroupMember(Long workGroupId,
			List<Map<String, String>> modelList) {
		StringBuffer result = new StringBuffer();
		StringBuffer sql = null;
		
		// 成员重复Check
		List<Map<String, String>> newMemberList = new ArrayList<Map<String, String>>();
		for (Map<String, String> model : modelList) {
			sql = new StringBuffer();
			sql.append(" SELECT DISTINCT");
			sql.append(" USER_ID");
			sql.append(",USERNAME");
			sql.append(" FROM");
			sql.append(" V_SYS_MY_WORKGROUP_MEMBER");
			sql.append(" WHERE");
			sql.append(" WORKGROUP_ID = ?");
			sql.append(" AND USER_ID = ?");
			List<Map<String, Object>> memberList = this.jdbcTemplate.queryForList(
					sql.toString(), workGroupId, model.get("id"));
			if (memberList == null || memberList.size() == 0) {
				newMemberList.add(model);
			} else {
				for (Map<String, Object> member : memberList) {
					if (result.length() > 0) {
						result.append(",");
					}
					result.append(member.get("USERNAME"));
				}
			}
		}
		if (newMemberList.size() == 0) {
			return result.toString();
		}
		for (Map<String, String> model : newMemberList) {
			this.pkgSysMyWorkgroupDBProcedureServcie.addWorkgroupMember(
					workGroupId, model.get("id"));
		}
		return result.toString();
	}
	
	/**
	 * 我的工作组-删除工作组成员
	 */
	public void deleteMyWorkgroupMember(Long workGroupId,
			List<Map<String, String>> modelList) {
		for (Map<String, String> model : modelList) {
			this.pkgSysMyWorkgroupDBProcedureServcie.deleteWorkgroupMember(Long.valueOf(model.get("id")));
		}
	}
}
