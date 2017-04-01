package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.pdms.dao.PmNoteDAO;
import com.gnomon.pdms.entity.PMNoteEntity;

@Service
@Transactional
public class PmNoteService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PmNoteDAO pmNoteDAO;
	
	public List<Map<String, Object>> getForumList(String sourceId,String source){
		return jdbcTemplate.queryForList("select * from V_PM_FORUM_POST where SOURCE_ID = ? and SOURCE=?",sourceId,source);
	}
	
	public PMNoteEntity get(Long id){
		return pmNoteDAO.get(id);
	}
	
	public List<PMNoteEntity> getNoteList(Long folderId){
		return pmNoteDAO.findBy("folderId", folderId);
	}
	
	public List<PMNoteEntity> getNoteList(){
		return pmNoteDAO.getAll();
	}
	
	public void save(PMNoteEntity entity){
		pmNoteDAO.save(entity);
	}
	
	public void delete(Long id){
		pmNoteDAO.delete(id);
	}

}
