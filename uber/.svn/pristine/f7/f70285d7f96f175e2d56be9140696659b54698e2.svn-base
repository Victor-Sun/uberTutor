package com.gnomon.pdms.service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.GTRoleDao;
import com.gnomon.pdms.entity.GTRole;

@Service
@Transactional
public class GTRoleManager {

	@Autowired
	private GTRoleDao gtRoleDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<GTRole> getRoleList(){
		List<GTRole> RoleList = gtRoleDao.getAll();
        return RoleList;
    }
	
	public List<GTRole> getRoleList(Long sourceId){
		
		List<GTRole> checkpointList = gtRoleDao.findBy("issueSourceId", sourceId);
        return checkpointList;
    }
	
	
	public void save (GTRole entity){
		gtRoleDao.save(entity);
	}
	
	public GTRole get(Long id){
		return gtRoleDao.get(id);
	}
	
	public void delete(Long id){
		gtRoleDao.delete(id);;
	}
	
	public List<Map<String, Object>> getRoleUserList(String roleId){
		return jdbcTemplate.queryForList("select T1.*,T2.*, T4.NAME,T4.ID as DepartmentId from SYS_USER T1,SYS_ROLE T2,SYS_USER_ROLE T3, SYS_DEPARTMENT T4 where T4.ID = T3.DEPARTMENT_ID AND T1.USERID = T3.USER_ID AND T2.ROLE_ID = T3.ROLE_ID AND T2.ROLE_ID=?",roleId);
	}
	
	public List<Map<String, Object>> getUserList(){
		return jdbcTemplate.queryForList("select * from SYS_USER ");
	}
	//部门
	public List<Map<String, Object>> getDepartmentList(){
		return jdbcTemplate.queryForList("select * from SYS_DEPARTMENT");
	}
	
	public List<Map<String, Object>> getRoleMenuList(String parentId,String roleId){
		return jdbcTemplate.queryForList("SELECT mw.item_id2 item_id, mw.item_name, mw.item_parent_id2 item_parent_id, mw.item_type, mw.icon_cls,mw.display_seq, mw.user_type,mw.is_leaf, decode(pkg_pdms_common.get_role_menu_widget_check(?, mw.item_id,mw.item_type),'Y','true','false') is_check  FROM v_sys_menu_widget mw WHERE  mw.item_parent_id2=?  order by DISPLAY_SEQ ",roleId,parentId);
	}
	
	public void saveRoleMenu (String roleId,String idStr) throws Exception{
		this.deleteRoleMenu(roleId);
		if(StringUtils.isNotEmpty(idStr)){
			String[] idArray = idStr.split(",");
			for(int i=0;i<idArray.length;i++){
				String id = idArray[i];
				if("root".equals(id)){
					continue;
				}
				String itemId = id.substring(0, id.indexOf("_"));
				String type = id.substring(id.indexOf("_")+1);
				this.createRoleMenu(roleId, itemId , type);
			}
		}
		
	}
	
	public void deleteRoleMenu(String roleId) throws Exception{
		Session session = gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_deal_menu_widget(?,?,?,?,?,?)}");

		statement.setString(1, "DELETE");
		statement.setString(2, roleId);
		statement.setLong(3, 0l);
		statement.setString(4, "");
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.registerOutParameter(6, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(5);
		String returnMsg = statement.getString(6);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void createRoleMenu(String roleId,String id,String type) throws Exception{
		Session session = gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_deal_menu_widget(?,?,?,?,?,?)}");

		statement.setString(1, "CREATE");
		statement.setString(2, roleId);
		statement.setLong(3, Long.valueOf(id));
		statement.setString(4, type);
		statement.registerOutParameter(5, Types.VARCHAR);
		statement.registerOutParameter(6, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(5);
		String returnMsg = statement.getString(6);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void createRole(String roleId,String internalUser,String roleName,String roleNameEn,String isSysRole) throws Exception{
		Session session = gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_deal(?,?,?,?,?,?,?,?)}");

		statement.setString(1, "CREATE");
		statement.setString(2, roleId);
		statement.setString(3, internalUser);
		statement.setString(4, roleName);
		statement.setString(5, roleNameEn);
		statement.setString(6, isSysRole);
		statement.registerOutParameter(7, Types.VARCHAR);
		statement.registerOutParameter(8, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(7);
		String returnMsg = statement.getString(8);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void updateRole(String roleId,String internalUser,String roleName,String roleNameEn,String isSysRole) throws Exception{
		Session session = gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_deal(?,?,?,?,?,?,?,?)}");

		statement.setString(1, "UPDATE");
		statement.setString(2, roleId);
		statement.setString(3, internalUser);
		statement.setString(4, roleName);
		statement.setString(5, roleNameEn);
		statement.setString(6, isSysRole);
		statement.registerOutParameter(7, Types.VARCHAR);
		statement.registerOutParameter(8, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(7);
		String returnMsg = statement.getString(8);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public void deleteRole(String roleId) throws Exception{
		Session session = gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{call pkg_pdms_common.role_deal(?,?,?,?,?,?,?,?)}");

		statement.setString(1, "DELETE");
		statement.setString(2, roleId);
		statement.setString(3, "");
		statement.setString(4, "");
		statement.setString(5, "");
		statement.setString(6, "");
		statement.registerOutParameter(7, Types.VARCHAR);
		statement.registerOutParameter(8, Types.VARCHAR);
		statement.executeUpdate();
		String returnCode = statement.getString(7);
		String returnMsg = statement.getString(8);
		if(!PDMSConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			throw new RuntimeException(returnMsg);
		}
	}
	
	public String deleteRolePre(String roleId) throws Exception{
		Session session =gtRoleDao.getSession();  
		CallableStatement statement = session.connection().prepareCall("{?=call pkg_pdms_common.role_delete_pre(?)}");
		
		statement.registerOutParameter(1, Types.VARCHAR);
		statement.setString(2, roleId);
		statement.executeUpdate();
		String result = statement.getString(1);
		
		return result;
	}
	
	public List<Map<String, Object>> getMenuWidgetList(String parentId){
		return jdbcTemplate.queryForList("select * from V_SYS_MENU_WIDGET t where t.item_parent_id2=?",parentId);
	}

	public List<Map<String, Object>> getUserWidgetList(String userId,String menuId){
		return jdbcTemplate.queryForList("select * from V_SYS_USER_WIDGET t where t.user_id=? and t.menu_id=?",userId,menuId);
	}

}

