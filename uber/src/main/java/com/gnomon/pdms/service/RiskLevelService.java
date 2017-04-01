package com.gnomon.pdms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.entity.GTIssueEntity;

@Service
@Transactional
public class RiskLevelService {
	
	@Autowired
	private GTIssueDAO gTIssueDAO;
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public GTIssueEntity getRiskLevel(String KeyId) {
		GTIssueEntity getRiskLevel = this.gTIssueDAO.findUniqueBy("id", KeyId);
		return getRiskLevel;
	}
	
//	public GTPage<Map<String, Object>> getRiskList(List<String> programIdList,
//			int pageNo, int pageSize) {
//		
//		StringBuffer sql = new StringBuffer();
//		List<Object> paramList = new ArrayList<Object>();
//		
//		sql.append(" SELECT *");
//		sql.append(" FROM");
//		sql.append(" V_IMS_ISSUE_RISK");
//		sql.append(" WHERE");
//		sql.append(" 1 = 1");
//		
//		if(programIdList != null && programIdList.size() > 0){
//			sql.append(" AND PROJECT_ID IN(");
//			for (int i = 0; i < programIdList.size(); i++) {
//				String programId  = programIdList.get(i);
//				sql.append(" ?");
//				paramList.add(programId);
//				if(i < programIdList.size() - 1){
//					sql.append(",");
//				}else{
//					sql.append(")");
//				}
//			}
//		}
//		return jdbcTemplate.queryPagination(sql.toString(), pageNo, pageSize,
//				paramList.toArray());
//	}
		
	public void save (GTIssueEntity entity){
		gTIssueDAO.save(entity);
	}
}
