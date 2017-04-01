package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.ImsIssueDAO;
import com.gnomon.pdms.entity.GTIssueEntity;

@Service
@Transactional
public class ImsIssueService {

	@Autowired
	private ImsIssueDAO imsIssueDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public GTIssueEntity get (String id){
		return imsIssueDAO.get(id);
	}
	
	public Map<String, Object> getImsIssue(String id, boolean historyFlag){
		String sql = "";
		if (historyFlag) {
			sql = "select * from V_IMS_HST_ISSUE where ID = ?";
		} else {
			sql = "select * from V_IMS_ISSUE where ID = ?";
		}
		return jdbcTemplate.queryForMap(sql, id);
	}
	
	public Map<String, Object> getMyLastIssue(String userId){
		String sql = "select * from V_IMS_ISSUE where CREATE_BY = ? order by  CREATE_DATE desc";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public GTPage<Map<String, Object>> getMergeIssueList(String programId,int pageNo, int pageSize,String fromDate,String toDate,String userName,String title,String code){
		String sql = "SELECT * FROM V_IMS_PRIMARY_ISSUE WHERE PROGRAM_ID = ? ";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(programId);
		if(StringUtils.isNotEmpty(fromDate)){
			sql += " AND CREATE_DATE >= ? ";
			paramList.add(DateUtils.strToDate(fromDate));
		}
		if(StringUtils.isNotEmpty(toDate)){
			sql += " AND CREATE_DATE <= ? ";
			paramList.add(DateUtils.strToDate(toDate));
		}
		if(StringUtils.isNotEmpty(userName)){
			sql += " AND SUBMIT_USER_NAME LIKE ? ";
			paramList.add("%"+userName+"%");
		}
		if(StringUtils.isNotEmpty(title)){
			sql += " AND TITLE like ? ";
			paramList.add("%"+title+"%");
		}
		if(StringUtils.isNotEmpty(code)){
			sql += " AND CODE like ? ";
			paramList.add("%"+code+"%");
		}
		return jdbcTemplate.queryPagination(sql, pageNo, pageSize,paramList.toArray());
	}
	
	public List<Map<String, Object>> getMergedIssueList(String issueId,String fromDate,String toDate,String userName,String title,String code){
		String sql = "SELECT * FROM V_IMS_ISSUE_MERGE WHERE PRIMARY_ISSUE_ID = ? ";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(issueId);
		if(StringUtils.isNotEmpty(fromDate)){
			sql += " AND CREATE_DATE >= ? ";
			paramList.add(DateUtils.strToDate(fromDate));
		}
		if(StringUtils.isNotEmpty(toDate)){
			sql += " AND CREATE_DATE <= ? ";
			paramList.add(DateUtils.strToDate(toDate));
		}
		if(StringUtils.isNotEmpty(userName)){
			sql += " AND SUBMIT_USER_NAME LIKE ? ";
			paramList.add("%"+userName+"%");
		}
		if(StringUtils.isNotEmpty(title)){
			sql += " AND TITLE like ? ";
			paramList.add("%"+title+"%");
		}
		if(StringUtils.isNotEmpty(code)){
			sql += " AND CODE like ? ";
			paramList.add("%"+code+"%");
		}
		return jdbcTemplate.queryForList(sql,paramList.toArray());
	}
	
	public List<Map<String, Object>> getMergedMainIssueList(String issueId,String fromDate,String toDate,String userName,String title,String code){
		String sql = "SELECT * FROM V_IMS_MAIN_ISSUE_MERGE WHERE ISSUE_ID = ? ";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(issueId);
		if(StringUtils.isNotEmpty(fromDate)){
			sql += " AND CREATE_DATE >= ? ";
			paramList.add(DateUtils.strToDate(fromDate));
		}
		if(StringUtils.isNotEmpty(toDate)){
			sql += " AND CREATE_DATE <= ? ";
			paramList.add(DateUtils.strToDate(toDate));
		}
		if(StringUtils.isNotEmpty(userName)){
			sql += " AND SUBMIT_USER_NAME LIKE ? ";
			paramList.add("%"+userName+"%");
		}
		if(StringUtils.isNotEmpty(title)){
			sql += " AND TITLE like ? ";
			paramList.add("%"+title+"%");
		}
		if(StringUtils.isNotEmpty(code)){
			sql += " AND CODE like ? ";
			paramList.add("%"+code+"%");
		}
		return jdbcTemplate.queryForList(sql,paramList.toArray());
	}
	
	public List<Map<String, Object>> getPartList(String code){
		return jdbcTemplate.queryForList(" select * from V_IMS_PART where PART_CODE like ?",""+code+"%");
	}
	
	public List<Map<String, Object>> getTodoListIMS(String userId) {
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
		sql.append("    AND T4.PARENT_ID = ?		order by DISPLAY_SEQ ");
		return jdbcTemplate.queryForList(sql.toString(),
				userId, PDMSConstants.PDMS_MENU_WIDGET_601);
	}
}