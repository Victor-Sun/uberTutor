package com.gnomon.pdms.service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.GTRoleDao;

@Service
@Transactional
public class GTUserRoleManager {

	@Autowired
	private GTRoleDao gtUserRoleDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public List<String> getUserRoleList(String userid){
		List<String> roleIdList = new ArrayList<String>();
		String sql = "SELECT ROLE_ID FROM SYS_USER_ROLE WHERE USER_ID=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,userid);
		for (int i = 0; i < list.size(); i++) {
			String roleId = ObjectConverter.convert2String(list.get(i).get("ROLE_ID"));
			roleIdList.add(roleId);
		}
		return roleIdList;
	}
	
	
	public void createRoleUser(String roleId,String userId) throws Exception{
		Session session = gtUserRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_user_deal(?,?,?,?,?)}");

		statement.setString(1, "CREATE");
		statement.setString(2, roleId);
		statement.setString(3, userId);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void deleteRoleUser(String roleId,String userId) throws Exception{
		Session session = gtUserRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_user_deal(?,?,?,?,?)}");

		statement.setString(1, "DELETE");
		statement.setString(2, roleId);
		statement.setString(3, userId);
		statement.registerOutParameter(4, Types.VARCHAR);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(4);
		String returnMsg = statement.getString(5);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
}

