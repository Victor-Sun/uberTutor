package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.GTMenuDao;
import com.gnomon.pdms.entity.GTMenu;

@Service
@Transactional
public class GTMenuManager {

	@Autowired
	private GTMenuDao gtMenuDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<GTMenu> getMenuList(){
		List<GTMenu> MenuList = gtMenuDao.getAll();
        return MenuList;
    }
	
	public List<GTMenu> getMenuList(Long parentId){
		
		List<GTMenu> checkpointList = gtMenuDao.findBy("parentId", parentId);
        return checkpointList;
    }
	
	
	public void save (GTMenu entity){
		gtMenuDao.save(entity);
	}
	
	public GTMenu get(Long id){
		return gtMenuDao.get(id);
	}
	
	public void delete(Long id){
		gtMenuDao.delete(id);;
	}
	
	public List<Map<String, Object>> getMenuList(String userId, String parentId, String menuType){
		return jdbcTemplate.queryForList("select * from V_SYS_ROLE_MENU_WIDGET where USERID=? AND PARENT_ID=? AND MENU_TYPE= ? ORDER BY DISPLAY_SEQ",userId,parentId, menuType);
	}
}

