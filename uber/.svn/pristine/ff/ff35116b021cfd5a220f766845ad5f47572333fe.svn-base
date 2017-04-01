package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;

@Service
@Transactional
public class ImsDashboardService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;

	/*
	 * 首页权限取得
	 */
	public List<Map<String, Object>> getDashboardIMS(String userId) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT *															");
		sql.append("   FROM SYS_WIDGET              T1,   ");
		sql.append("        V_SYS_WIDGET_PERMISSION T2,   ");
		sql.append("        SYS_USER_ROLE           T3,  ");
		sql.append("        SYS_PAGE_WIDGET         T4		");
		sql.append("  WHERE T1.ID = T2.ITEM_ID					");
		sql.append("    AND T2.ROLE_ID = T3.ROLE_ID			");
		sql.append("    AND T3.USER_ID = SYS_GET_USERID_BY_ID(?)			");
		sql.append("    AND T4.WIDGET_ID = T1.ID			");
		sql.append("    AND T4.PARENT_ID = ?		");
		return jdbcTemplate.queryForList(sql.toString(),
				userId, PDMSConstants.PDMS_MENU_WIDGET_800601);
	}
	
	public void addMembers(String id,List<Map<String, String>> memberIdlist){
		for(Map<String, String> map:memberIdlist){
			pkgImsDBProcedureServcie.addMember(id, map.get("id"));
		}
	}
	
	public List<Map<String, Object>> getIssueMemberList(String id){
		return jdbcTemplate.queryForList("select * from V_IMS_ISSUE_MEMBER where ISSUE_ID = ?",id);
	}
}
