package com.gnomon.pdms.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;

@Service
@Transactional
public class HistoryFormService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/**
	 * 历史表单列表取得
	 */
	public GTPage<Map<String, Object>> getHistoryForm(String issueId,
			int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_IMS_HST_ISSUE");
		sql.append(" WHERE");
		sql.append(" ISSUE_ID = ?");
		// 返回值
		return jdbcTemplate.queryPagination(
				sql.toString(), pageNo, pageSize, issueId);
    }
}
