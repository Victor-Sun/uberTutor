package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.Ext203PreTaskDAO;
import com.gnomon.pdms.entity.Ext203PreTask;

@Service
@Transactional
public class Ext203PreTaskService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Ext203PreTaskDAO ext203PreTaskDAO;
	
	public List<Map<String, Object>> getExt203DependenceList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_PRE_TASK ");
        return list;
    }
	
	public List<Map<String, Object>> getExt203AssignmentList(Long projectId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=?",projectId);
        return list;
    }
	
	public List<Map<String, Object>> getExt203ResourcesList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM SYS_USER ");
        return list;
    }
	
	public List<Map<String, Object>> getExt203TaskList(Long projectId,Long parentId) {
		List<Map<String, Object>> list = null;
		if(parentId == null){
			list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=? and PARENT_ID is null",projectId);
		}else{
			list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=? and PARENT_ID=?",projectId,parentId);
		}
		
        return list;
    }

	public void addTaskList(List<Ext203PreTask> list){
		for(Ext203PreTask Ext203PreTask:list){
			ext203PreTaskDAO.save(Ext203PreTask);
		}
	}
	
	public void save(Ext203PreTask entity){
		ext203PreTaskDAO.save(entity);
	}
	
	public void updateTaskList(List<Ext203PreTask> list){
		for(Ext203PreTask Ext203PreTask:list){
			ext203PreTaskDAO.save(Ext203PreTask);
		}
	}
	
	public Ext203PreTask get(Long id){
		return ext203PreTaskDAO.get(id);
	}

}
