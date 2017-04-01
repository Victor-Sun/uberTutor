package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;

@Service
@Transactional
public class SysCodeSequenceService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String GenerateTaskCode() {
		StringBuffer sql = null;
		List<Object> params = null;
		
		Map<String, Object> codeInfo =
				this.getSysCodeSequence(PDMSConstants.SYS_CODE_TASK);

		if (codeInfo == null) {
			// 如果查询日期内没有记录则更新日期以及顺序号
			sql = new StringBuffer();
			params = new ArrayList<Object>();
			sql.append(" UPDATE");
			sql.append(" SYS_CODE_SEQUENCE");
			sql.append(" SET");
			sql.append(" SEQ = ?");
			sql.append(",SEQ_DATE = SYSDATE");
			sql.append(" WHERE");
			sql.append(" CODE_TYPE = ?");
			params.add(1);
			params.add(PDMSConstants.SYS_CODE_TASK);
			this.jdbcTemplate.update(sql.toString(), params.toArray());
			// 再次查询
			codeInfo = this.getSysCodeSequence(PDMSConstants.SYS_CODE_TASK);
		} else {
			// 如果查询日期内存在记录则更新顺序号
			sql = new StringBuffer();
			params = new ArrayList<Object>();
			sql.append(" UPDATE");
			sql.append(" SYS_CODE_SEQUENCE");
			sql.append(" SET");
			sql.append(" SEQ = SEQ + 1");
			sql.append(" WHERE");
			sql.append(" CODE_TYPE = ?");
			sql.append(" AND TO_CHAR(SEQ_DATE, 'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')");
			params.add(PDMSConstants.SYS_CODE_TASK);
			this.jdbcTemplate.update(sql.toString(), params.toArray());
		}
		
		if (codeInfo == null) {
			return null;
		}
		
		String strPad = PDMSCommon.padLeft(PDMSCommon.nvl(codeInfo.get("SEQ")),
				Integer.parseInt(PDMSCommon.nvl(codeInfo.get("SEQ_LEN"), "0")), '0');
		// 结果返回
		return codeInfo.get("CODE_PREFIX") + "_" + codeInfo.get("SEQ_DATE") + "_" + strPad;
    }
	
	/**
	 * 系统生成Code信息查询
	 */
	private Map<String, Object> getSysCodeSequence(String type) {
		Map<String, Object> result = null;
		StringBuffer sql = null;
		List<Object> params = null;
		
		sql = new StringBuffer();
		params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" CODE_PREFIX");
		sql.append(",SEQ");
		sql.append(",SEQ_LEN");
		sql.append(",TO_CHAR(SEQ_DATE, 'YYYYMMDD') AS SEQ_DATE");
		sql.append(" FROM");
		sql.append(" SYS_CODE_SEQUENCE");
		sql.append(" WHERE");
		sql.append(" CODE_TYPE = ?");
		sql.append(" AND TO_CHAR(SEQ_DATE, 'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')");
		params.add(type);
		List<Map<String, Object>> codeList =
				this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
		if (codeList.size() > 0) {
			result = codeList.get(0);
		}
		return result;
	}

}
