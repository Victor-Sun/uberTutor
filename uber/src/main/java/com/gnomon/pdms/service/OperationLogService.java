package com.gnomon.pdms.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.base.StringIdEntity;
import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;

@Service
@Transactional
public class OperationLogService extends StringIdEntity {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	/**
	 * 用户登录日志信息取得
	 */
	public GTPage<Map<String, Object>> getLoginLog(int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_LOGIN_LOG");
		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize);
    }

}
