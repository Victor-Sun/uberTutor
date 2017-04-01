package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CodeTableService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 系统码表数据取得
	 */
	public List<Map<String, Object>> getSysCodeTable(String type) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"SELECT * FROM SYS_CODE_TABLE WHERE TYPE=? ORDER BY SEQ", type);
        return list;
    }
	
	/**
	 * 系统码表数据取得（质量问题管理）
	 */
	public List<Map<String, Object>> getImsCodeTable(String type) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"SELECT * FROM IMS_CODE_TABLE WHERE CODE_TYPE=? ORDER BY SEQ", type);
        return list;
    }

}
