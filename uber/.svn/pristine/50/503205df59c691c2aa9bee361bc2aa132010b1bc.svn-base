package com.gnomon.pdms.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;

@Service
@Transactional
public class ImsIssueOperationLogService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public GTPage<Map<String, Object>> getOperationLog(String issueId,
			int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_IMS_PROCESS_TASK_LOG");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ? order by create_date desc");
		// 返回值
		return jdbcTemplate.queryPagination(
				sql.toString(), pageNo, pageSize, issueId);
    }
}
