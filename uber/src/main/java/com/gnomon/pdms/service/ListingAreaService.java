package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.procedure.PkgImsDBProcedureServcie;

@Service
@Transactional
public class ListingAreaService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgImsDBProcedureServcie pkgImsDBProcedureServcie;
	
	@Autowired
	private GTIssueDAO gtIssueDAO;
	
	/**
	 * 挂牌区单条数据取得
	 */
	public List<Map<String, Object>> getListFormKey (String keyId) {

		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" IMS_ISSUE_LISTING");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		paramList.add(keyId);
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
		return result;
    }
	
	/**
	 * 挂牌
	 */
	public void listIssue(String issueId, Map<String, String> model) {
		String loginUser = SessionData.getLoginUserId();
		
		// 更新责任部门、问题等级
//		GTIssueEntity issue = this.gtIssueDAO.get(issueId);
//		issue.setDeptId(model.get("deptId"));
//		issue.setIssueLevelCode(model.get("issueLevelCode"));
//		this.gtIssueDAO.save(issue);
		
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" UPDATE IMS_ISSUE");
		sql.append(" SET");
		sql.append(" DEPT_ID = ?");
		sql.append(",ISSUE_LEVEL_CODE = ?");
		sql.append(",UPDATE_BY = ?");
		sql.append(",UPDATE_DATE = SYSDATE");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		paramList.add(model.get("deptId"));
		paramList.add(model.get("issueLevelCode"));
		paramList.add(loginUser);
		paramList.add(issueId);
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		// 挂牌
		this.pkgImsDBProcedureServcie.listIssue(issueId,
				loginUser, model.get("reason"));
	}
	
	/**
	 * 摘牌
	 */
	public void delListing (String issueId) {
		String loginUser = SessionData.getLoginUserId();
		// 摘牌
		this.pkgImsDBProcedureServcie.delistIssue(issueId, loginUser);
	}
	
	/**
	 * 取消挂牌
	 */
	public void undoListIssue (String issueId) {
		String loginUser = SessionData.getLoginUserId();
		// 取消挂牌
		this.pkgImsDBProcedureServcie.undoListIssue(issueId, loginUser);
	}

}