package com.ubertutor.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.UserRequestDAO;
import com.ubertutor.dao.UserSubjectDAO;
import com.ubertutor.entity.SubjectCategoryEntity;
import com.ubertutor.entity.SubjectEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class MakeRequestService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserRequestDAO userRequestDao;

	@Autowired
	private UserSubjectDAO userSubjectDao;
	
	//public void makeRequest(UserRequestEntity entity){
	//	userRequestDao.save(entity);
	//}
	
	public UserRequestEntity get(Long id){
		return userRequestDao.get(id);
	}
	
	public void delete(UserRequestEntity entity){
		userRequestDao.delete(entity);
	}
	
	public void delete(Long id){
		userRequestDao.delete(id);
	}
	
	//Zelin: list subjects based on category user select
	public List<Map<String,Object>> getSubjects(String categoryId){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID, TITLE FROM SUBJECT WHERE CATEGORY_ID = ?");
		params.add(categoryId);
		return this.jdbcTemplate.queryForList(sql.toString(),params.toArray());
	}
	
	/**Zelin: save all request to database
	 * @param entity
	 * @param userId
	 * @param subjectId
	 */
	
	public void makeRequest(UserRequestEntity requestEntity, Long userId, String description, Long subjectId, String title){
		Date date = new Date();
	    requestEntity.setPendingDate(date);
	    requestEntity.setCancelDate(null);
	    requestEntity.setCloseDate(null);
	    requestEntity.setDescription(description);
	    requestEntity.setOpenDate(null);
	    requestEntity.setProcessDate(null);
	    requestEntity.setStatus("Pending");
	    requestEntity.setSubjectId(subjectId);
	    requestEntity.setTitle(title);
	    requestEntity.setTutorId(null);
	    requestEntity.setUpdateDate(null);
	    requestEntity.setUserId(userId);
		
	    userRequestDao.save(requestEntity);
	}
	
}
