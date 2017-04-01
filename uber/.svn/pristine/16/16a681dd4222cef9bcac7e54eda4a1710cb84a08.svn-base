package com.gnomon.pdms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ImsIssueMarkDAO;
import com.gnomon.pdms.entity.ImsIssueMarkEntity;

@Service
@Transactional
public class ImsIssueMarkService {

	@Autowired
	private ImsIssueMarkDAO imsIssueMarkDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<ImsIssueMarkEntity> getImsIssueMark(String keyId,String userId) {

		String hql = "FROM ImsIssueMarkEntity WHERE issueId = ? and userId = ?";
		List<ImsIssueMarkEntity> verificationScheme =
				this.imsIssueMarkDAO.find(hql, keyId, userId);
		return verificationScheme;
	}
	
	public void save (ImsIssueMarkEntity entity){
		imsIssueMarkDAO.save(entity);
	}
	
	public String isIssueMark(String issueId,String userId){
		int count = jdbcTemplate.queryForInt(" select count(*) from IMS_ISSUE_MARK where  ISSUE_ID=? and USER_ID=? and IS_CONCERN = 'Y' ",issueId,userId);
		if(count > 0){
			return "Y";
		}else{
			return "N";
		}
	}
	
}
