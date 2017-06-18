package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainPageService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> getTutorCurrentRequests(Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM USER_REQUEST WHERE STATUS = ? AND TUTOR_ID = ?");
		params.add(id);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	
}
