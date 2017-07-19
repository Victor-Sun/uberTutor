package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.nec.flowlites.core.FLJdbcTemplate;
import jp.co.nec.flowlites.core.FLPage;
import com.ubertutor.dao.SubjectCategoryDAO;
import com.ubertutor.dao.SubjectDAO;
import com.ubertutor.dao.UserSubjectDAO;
import com.ubertutor.entity.UserSubjectEntity;

@Service
@Transactional
public class TutorSubjectRegisterService {
	@Autowired
	private FLJdbcTemplate jdbcTemplate;
	@Autowired
	private UserSubjectDAO userSubjectDAO;
	@Autowired
	private SubjectCategoryDAO categoryDAO;
	@Autowired
	private SubjectDAO subjectDAO;
	
	/**
	 * Get all categories
	 * @return List of all categories
	 */
	public List<Map<String,Object>> getCategoryList(){
		String hql = "FROM SubjectCategoryEntity";
		return this.categoryDAO.find(hql);
	}

	/**
	 * Get all subjects according to category
	 * @param categoryId
	 * @return List of subjects according to category
	 */
	public List<Map<String,Object>> getSubjectList(Long categoryId){
		String hql = " FROM SubjectEntity where categoryId = ?";
		return this.subjectDAO.find(hql, categoryId);
	}
	
	/**
	 * Get all of a user's subjects/categories
	 * @param userId
	 * @return List of subjects according to user
	 */
	public FLPage<Map<String,Object>> getUserSubjects(Long userId, int pageNo, int pageSize){
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM V_USERS_SUBJECT_CATEGORY WHERE USER_ID = ?";
		params.add(userId);
		return this.jdbcTemplate.queryPagination(sql,pageNo, pageSize, params.toArray());
	}
	
	/**
	 * Add subject to the table user_subject
	 * @param userSubjectEntity
	 * @param userId
	 * @param subjectId
	 */
	public void save(UserSubjectEntity userSubjectEntity){
		userSubjectDAO.save(userSubjectEntity);
	}

	/**
	 * Updates subject
	 * @param userSubjectId
	 * @param description
	 */
	public void editSubject(Long userSubjectId, String description){
		List<Object> params = new ArrayList<Object>();
		String sql = " UPDATE USER_SUBJECT SET DESCRIPTION = ? WHERE ID = ?";
		params.add(description);
		params.add(userSubjectId);
		this.jdbcTemplate.update(sql,params.toArray());
	}
	
	/**
	 * Returns true if subject exists
	 * @param userId
	 * @param subjectId
	 * @return
	 */
	public boolean subjectExists(Long userId, Long subjectId){
		String hql = "FROM UserSubjectEntity WHERE userId = ? AND subjectId = ?";
		List<UserSubjectEntity> result = this.userSubjectDAO.find(hql, userId, subjectId);
        return result.size() > 0; 
	}
	
	/**
	 * Returns UserSubjectEntity
	 * @param userSubjectId
	 * @return
	 */
	public UserSubjectEntity get(Long userSubjectId){
		return userSubjectDAO.get(userSubjectId);
	}
	
	/**
	 * Deletes UserSubjectEntity
	 * @param userSubjectEntity
	 */
	public void delete(UserSubjectEntity userSubjectEntity){
		userSubjectDAO.delete(userSubjectEntity);
	}
	
	/**
	 * Deletes UserSubjectEntity
	 * @param entity
	 */
	public void delete(Long userSubjectId){
		userSubjectDAO.delete(userSubjectId);
	}
}
