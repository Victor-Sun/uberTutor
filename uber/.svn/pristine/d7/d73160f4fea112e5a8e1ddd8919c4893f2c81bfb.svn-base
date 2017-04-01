package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.dao.ExtendedProjectDAO;
import com.gnomon.pdms.entity.ExtendedProjectEntity;

@Service
@Transactional
public class ProjectTreeService {

	@Autowired
	private ExtendedProjectDAO extendedProjectDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PrivilegeService privilegeService;

	public List<ExtendedProjectEntity> getExtendedProjectList(String programId) {
		List<ExtendedProjectEntity> result =
				this.extendedProjectDAO.findBy("pdmsProgramUuid", programId);
        return result;
    }
	
	public List<Map<String, Object>> getExtProjectList(String programId){
		return jdbcTemplate.queryForList("select T1.*,T2.View_Type from Pm_Ext_Project T1,PM_EXT_PROCESS T2 where T1.EXT_PROCESS_CODE = T2.CODE and T1.Pdms_Program_Uuid=?",programId);
	}
	
	/**
	 * Tree节点权限取得
	 */
	public boolean getProjectTreePrivilege(String programId, String privilegeCode) {
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		// 如果具有查看所有项目权限则返回True
		if (this.privilegeService.canViewAllProgram(loginUser)) {
			return true;
		}
		// 返回指定权限
		return this.privilegeService.hasProgramPrivilege(
				loginUser, programId, privilegeCode);
	}
}