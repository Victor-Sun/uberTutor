/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author frank
 *
 */
@Service
@Transactional
public class SequenceTools {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public SequenceTools(){
	}
	
	public synchronized long getSequence(String module,String type){
		long sequence = 0;
		String tableName = "GT_COMMON_SEQUENCE";
		try {
			
			//如果不存在插入一条新纪录
			int c =getJdbcTemplate().queryForInt("select count(SERIAL_NUMBER) from " + tableName + " where MODULE= ? and TYPE=?",
					new Object[] { module,type });
			if (c == 0) {
				getJdbcTemplate().update("insert into " + tableName + " (MODULE,TYPE,SERIAL_NUMBER) values(?,?,?)",
						new Object[] { module,type, 0 });
			}
			
			
			// 序号加一
			getJdbcTemplate()
			.update(
					"update "
							+ tableName
							+ " set SERIAL_NUMBER=(select max(SERIAL_NUMBER) from "+tableName+" where MODULE= ? and TYPE=?) + 1 where MODULE= ? and TYPE=?",
							new Object[] { module, type,module, type });
			
			// 获取最新的序号
			sequence = getJdbcTemplate().queryForLong(
					"select SERIAL_NUMBER from " + tableName + " where MODULE= ? and TYPE=?",
					new Object[] { module, type });
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return sequence;
	}
	
	public synchronized long querySequence(String module,String type){
		long sequence = 0;
		String tableName = "GT_COMMON_SEQUENCE";
		try {
			
			//如果不存在插入一条新纪录
			int c =getJdbcTemplate().queryForInt("select count(SERIAL_NUMBER) from " + tableName + " where MODULE= ? and TYPE=?",
					new Object[] { module,type });
			if (c == 0) {
				getJdbcTemplate().update("insert into " + tableName + " (MODULE,TYPE,SERIAL_NUMBER) values(?,?,?)",
						new Object[] { module,type, 0 });
			}
			
			// 获取最新的序号
			sequence = getJdbcTemplate().queryForLong(
					"select SERIAL_NUMBER from " + tableName + " where MODULE= ? and TYPE=?",
					new Object[] { module, type });
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return sequence;
	}
	
	public synchronized long updateSequence(String module,String type,Long value){
		long sequence = 0;
		String tableName = "GT_COMMON_SEQUENCE";
		try {
			
			long maxValue = querySequence(module,type);
			if(value > maxValue){
				// 序号加一
				getJdbcTemplate()
				.update(
						"update "
								+ tableName
								+ " set SERIAL_NUMBER='"+value+"' where MODULE= ? and TYPE=?",
								new Object[] { module, type});
				
				// 获取最新的序号
				sequence = getJdbcTemplate().queryForLong(
						"select SERIAL_NUMBER from " + tableName + " where MODULE= ? and TYPE=?",
						new Object[] { module, type });
			}else{
				throw new RuntimeException();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException();
		}
		return sequence;
	}
	
	public synchronized String getSequence(String module,String type,int size){
		long s = getSequence(module,type);
		return StringUtils.leftPad(String.valueOf(s),size,"0");
	}
	
}
