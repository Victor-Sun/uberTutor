package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMProgramVMDAO;

@Service
@Transactional
public class DashboardPMSService {

	@Autowired
	private PMProgramVMDAO pmProgramVMDAO;

	@Autowired
	private GTJdbcTemplate jdbcTemplate;

	/*
	 * 首页-常用项目list取得
	 */
	public List<Map<String, Object>> getDashCommonProject() {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_PROGRAM");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM(?, ID) = 1");
		sql.append(" AND ID IN(");
		sql.append(" SELECT DISTINCT PROGRAM_ID");
		sql.append(" FROM");
		sql.append(" PM_PROGRAM_USUAL");
		sql.append(" WHERE");
		sql.append(" USER_ID = ?)");
		sql.append(" ORDER BY SEQ");
		paramList.add(loginUser);
		paramList.add(loginUser);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}

	/*
	 * 多项目管理状态报告list取得
	 */
	public GTPage<Map<String, Object>> getProjectReport(int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_MULTY_REPORT");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, ID) = 1");
		paramList.add(loginUser);
		return jdbcTemplate.queryPagination(sql.toString(), pageNo,pageSize,paramList.toArray());
	}
	
	/*
	 * 首页-多项目管理状态报告list取得（无分页）
	 */
	public List<Map<String, Object>> getProjectReportNoPaging() {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		// 登录用户取得
		String loginUser = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_MULTY_REPORT");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, ID) = 1");
		paramList.add(loginUser);
		return jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}

	/*
	 * 首页权限取得
	 */
	public List<Map<String, Object>> getDashboardPMS(String userId) {
		return jdbcTemplate.queryForList(
				"SELECT * FROM V_SYS_USER_WIDGET WHERE"
					+ " USER_ID = SYS_GET_USERID_BY_ID(?)"
					+ " AND MENU_ID = ? order by DISPLAY_SEQ ",
				userId, PDMSConstants.PDMS_MENU_WIDGET_100);
	}
}
