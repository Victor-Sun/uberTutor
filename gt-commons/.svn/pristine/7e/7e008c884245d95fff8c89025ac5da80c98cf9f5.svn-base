/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.sso.OrgUserService;
import com.gnomon.intergration.sso.SSODepartment;
import com.gnomon.intergration.sso.SSOIntergrationException;
import com.gnomon.intergration.sso.SSORole;
import com.gnomon.intergration.sso.SSOUserInfo;
import com.gnomon.servicewapper.aws.api.WappedAWSOrgAPI;

/**
 * @author frank
 *
 */
@Service
public class AWSOrgUserServiceImpl implements OrgUserService {
	
	public static final String KEY_USERINFO_ID = "id";
	public static final String KEY_USERINFO_USERID = "userId";
	public static final String KEY_USERINFO_USERNAME = "userName";
	public static final String KEY_USERINFO_USERNO = "userNo";
	public static final String KEY_USERINFO_TEL = "tel";
	public static final String KEY_USERINFO_MOBILE = "mobile";
	public static final String KEY_USERINFO_EMAIL = "email";
	public static final String KEY_USERINFO_COMPANYNAME = "companyName";
	public static final String KEY_USERINFO_DEPARTMENTNAME = "departmentName";
	public static final String KEY_USERINFO_DEPARTMENTFULLNAME = "departmentFullName";
	public static final String KEY_USERINFO_USERMAP = "userMap";
	public static final String KEY_USERINFO_EXTEND1 = "extend1";
	public static final String KEY_USERINFO_EXTEND2 = "extend2";
	public static final String KEY_USERINFO_EXTEND3 = "extend3";
	public static final String KEY_USERINFO_EXTEND4 = "extend4";
	public static final String KEY_USERINFO_EXTEND5 = "extend5";
	
	public static final String KEY_DEPARTMENTINFO_ID = "id";
	public static final String KEY_DEPARTMENTINFO_NAME = "departmentName";
	public static final String KEY_DEPARTMENTINFO_NO = "departmentNo";
	public static final String KEY_DEPARTMENTINFO_FULLNAME = "departmentFullName";
	public static final String KEY_DEPARTMENTINFO_FULLID = "departmentFullId";
	public static final String KEY_DEPARTMENTINFO_PARENTDEPARTMENTID = "parentDepartmentId";
	
	public static final String KEY_ROLEINFO_ID = "id";
	public static final String KEY_ROLEINFO_NAME = "roleName";
	public static final String KEY_ROLEINFO_GROUPNAME = "groupName";
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public Map<String, String> getUserInfoMap(String userId) {
		Map<String, String> userInfoMap = new HashMap<String, String>();
//		String userInfo = "";
		try {
			Map<String,Object> m = null;
//			m = jdbcTemplate.queryForMap("SELECT * FROM ORGUSER WHERE USERID=?",userId);
//			userInfoMap.put(KEY_USERINFO_ID, ObjectConverter.convert2String(m.get("ID")));
//			userInfoMap.put(KEY_USERINFO_USERID, ObjectConverter.convert2String(m.get("USERID")));
//			userInfoMap.put(KEY_USERINFO_USERNAME, ObjectConverter.convert2String(m.get("USERNAME")));
//			userInfoMap.put(KEY_USERINFO_USERNO, ObjectConverter.convert2String(m.get("USERNO")));
//			userInfoMap.put(KEY_USERINFO_TEL, ObjectConverter.convert2String(m.get("OFFICETEL")));
//			userInfoMap.put(KEY_USERINFO_MOBILE, ObjectConverter.convert2String(m.get("MOBILE")));
//			userInfoMap.put(KEY_USERINFO_EMAIL, ObjectConverter.convert2String(m.get("EMAIL")));
//			userInfoMap.put(KEY_DEPARTMENTINFO_ID, ObjectConverter.convert2String(m.get("DEPARTMENTID")));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

//		JSONObject userInfoJO = JSONObject.fromObject(userInfo);
//
//		userInfoMap.put(KEY_USERINFO_ID, userInfoJO.getString(KEY_USERINFO_ID));
//		userInfoMap.put(KEY_USERINFO_USERID, userInfoJO.getString(KEY_USERINFO_USERID));
//		userInfoMap.put(KEY_USERINFO_USERNAME, userInfoJO.getString(KEY_USERINFO_USERNAME));
//		userInfoMap.put(KEY_USERINFO_USERNO, userInfoJO.getString(KEY_USERINFO_USERNO));
//		userInfoMap.put(KEY_USERINFO_TEL, userInfoJO.getString(KEY_USERINFO_TEL));
//		userInfoMap.put(KEY_USERINFO_MOBILE, userInfoJO.getString(KEY_USERINFO_MOBILE));
//		userInfoMap.put(KEY_USERINFO_EMAIL, userInfoJO.getString(KEY_USERINFO_EMAIL));
//		userInfoMap.put(KEY_USERINFO_EXTEND1, userInfoJO.getString(KEY_USERINFO_EXTEND1));
//		userInfoMap.put(KEY_USERINFO_EXTEND2, userInfoJO.getString(KEY_USERINFO_EXTEND2));
//		userInfoMap.put(KEY_USERINFO_EXTEND3, userInfoJO.getString(KEY_USERINFO_EXTEND3));
//		userInfoMap.put(KEY_USERINFO_EXTEND4, userInfoJO.getString(KEY_USERINFO_EXTEND4));
//		userInfoMap.put(KEY_USERINFO_EXTEND5, userInfoJO.getString(KEY_USERINFO_EXTEND5));
		return userInfoMap;
	}
	
	public Map<String, String> getUserDepartmentInfo(String userId) {
		Map<String, String> departmentInfoMap = new HashMap<String, String>();
		String departmentInfo = "";
		try {
			departmentInfo = AwsApi.getInstance().getOrg().getUserDepartmentInfo(userId);
			

		} catch (Exception e) {
			throw new RuntimeException(e);
//			reloadUserCache();
//			try {
//				departmentInfo = awsAPI.getORGAPI().getUserDepartmentInfo(userId);
//			} catch (AWSSDKException e1) {
//				log.error(e1);
//				e1.printStackTrace(System.err);
//				throw new RuntimeException(e.getMessage(), e);
//
//			}
		}
		JSONObject departmentInfoJO = JSONObject.fromObject(departmentInfo);
		
		departmentInfoMap.put(KEY_DEPARTMENTINFO_ID, departmentInfoJO.getString(KEY_DEPARTMENTINFO_ID));
		departmentInfoMap.put(KEY_DEPARTMENTINFO_NAME, departmentInfoJO.getString(KEY_DEPARTMENTINFO_NAME));
		departmentInfoMap.put(KEY_DEPARTMENTINFO_NO, departmentInfoJO.getString(KEY_DEPARTMENTINFO_NO));
		departmentInfoMap.put(KEY_DEPARTMENTINFO_FULLNAME, departmentInfoJO.getString(KEY_DEPARTMENTINFO_FULLNAME));
		departmentInfoMap.put(KEY_DEPARTMENTINFO_FULLID, departmentInfoJO.getString(KEY_DEPARTMENTINFO_FULLID));
		departmentInfoMap.put(KEY_DEPARTMENTINFO_PARENTDEPARTMENTID,
		departmentInfoJO.getString(KEY_DEPARTMENTINFO_PARENTDEPARTMENTID));
		return departmentInfoMap;
	}
	
	public Map<String, String> getUserRoleInfo(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		String userRoleInfo = "";
		try {
			userRoleInfo = AwsApi.getInstance().getOrg().getUserRoleInfo(userId);
		} catch (Exception e) {
			throw new RuntimeException(e) ;
//			reloadUserCache();
//			reloadRole();
//			try {
//				userRoleInfo = getORGAPI().getUserInfo(userId);
//			} catch (AWSSDKException e1) {
//				log.error(e1);
//				e1.printStackTrace(System.err);
//				throw new RuntimeException(e.getMessage(), e);
//
//			}
		}
		if(userRoleInfo== null || userRoleInfo.trim().equals("") ){
			return map;
		}
		JSONObject jo = JSONObject.fromObject(userRoleInfo);
		map.put(KEY_ROLEINFO_ID, jo.getString(KEY_ROLEINFO_ID));
		map.put(KEY_ROLEINFO_NAME, jo.getString(KEY_ROLEINFO_NAME));
		map.put(KEY_ROLEINFO_GROUPNAME, jo.getString(KEY_ROLEINFO_GROUPNAME));
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.UserService#getUserInfo(java.lang.String)
	 */
	@Override
	public SSOUserInfo getUserInfo(String userid) throws SSOIntergrationException {
		if(org.apache.commons.lang.StringUtils.isEmpty(userid)){
			return null;
		}
		Map<String, String> map = getUserInfoMap(userid);
		SSOUserInfo userInfo = new SSOUserInfo();
		userInfo.setId(map.get(WappedAWSOrgAPI.KEY_USERINFO_ID));
		userInfo.setUserId(map.get(WappedAWSOrgAPI.KEY_USERINFO_USERID));
		userInfo.setUserName(map.get(WappedAWSOrgAPI.KEY_USERINFO_USERNAME));
		userInfo.setEmail(map.get(WappedAWSOrgAPI.KEY_USERINFO_EMAIL));
		
		boolean isAdmin = false;
		List<SSORole> userAllRoleList = getUserAllRoleList(userid);
		userInfo.setUserRoles(userAllRoleList);
		for(SSORole role : userAllRoleList){
			if(role.getName().equals("系统管理员")){
				isAdmin = true;
			}
		}
		
		userInfo.setAdmin(isAdmin);
		map = getUserDepartmentInfo(userid);
		userInfo.setDepartmentId(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_ID));
		userInfo.setDepartmentName(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_NAME));
		

		return userInfo;
	}

	@Override
	public String getUseridByName(String username) throws SSOIntergrationException {
		if(StringUtils.isEmpty(username)){
			return "";
		}
		String userId = "";
		String sql="SELECT USERID FROM ORGUSER WHERE USERNAME = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,username.trim());
		for (Map<String, Object> map : list) {
			userId = ObjectConverter.convert2String(map.get("USERID"));
			if(userId ==  null){
				userId = "";
			}
			break;
		}
		return userId;
	}
	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getUserRoleList(java.lang.String)
	 */
	@Override
	public List<SSORole> getUserRoleList(String userId) throws SSOIntergrationException {
		 Map<String, String> userRoleInfo = getUserRoleInfo(userId);
		 SSORole role = new SSORole();
		 role.setId((String) ConvertUtils.convert(userRoleInfo.get(WappedAWSOrgAPI.KEY_ROLEINFO_ID),String.class));
		 role.setName((String) ConvertUtils.convert(userRoleInfo.get(WappedAWSOrgAPI.KEY_ROLEINFO_NAME),String.class));
		 
		 List<SSORole> list = new ArrayList<SSORole>();
		 list.add(role);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getUserListByDepartmentId(java.lang.String)
	 */
	@Override
	public List<SSOUserInfo> getUserListByDepartmentId(String departmentId) throws SSOIntergrationException {
		if(StringUtils.isBlank(departmentId)){
			departmentId = "0";
		}
		List<SSOUserInfo> returnList = new ArrayList<SSOUserInfo>();
//		List<Map<String, String>> userList = orgAPI.getUserListByDepartmentId(Integer.parseInt(departmentId));
		String sql="SELECT * FROM ORGUSER WHERE DEPARTMENTID = ?";
		List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql,departmentId);
		for (Map<String, Object> map : userList) {
			SSOUserInfo userInfo = new SSOUserInfo();
			String id = (String) ConvertUtils.convert(map.get("ID"),String.class);
			userInfo.setId(id);
			String userId = (String) ConvertUtils.convert(map.get("USERID"),String.class);
			userInfo.setUserId(userId);
			String userName = (String) ConvertUtils.convert(map.get("USERNAME"),String.class);
			userInfo.setUserName(userName);
			
			String email = (String) ConvertUtils.convert(map.get("EMAIL"),String.class);
			userInfo.setEmail(email);
			
//			Map<String, String> userDepartmentInfo = orgAPI.getUserDepartmentInfo(id);
			String sql1="select * from ORGDEPARTMENT where ID =?";
			Map<String, Object> subDepartment = jdbcTemplate.queryForMap(sql1,departmentId);
			String dId = (String) ConvertUtils.convert(subDepartment.get("ID"),String.class);
			userInfo.setDepartmentId(dId);
			String dName = (String) ConvertUtils.convert(subDepartment.get("DEPARTMENTNAME"),String.class);
			userInfo.setDepartmentName(dName);
			
			returnList.add(userInfo);
		}
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getSubDepartmentList(java.lang.String)
	 */
	@Override
	public List<SSODepartment> getSubDepartmentList(String departmentId) throws SSOIntergrationException {
		
		if(StringUtils.isEmpty(departmentId)){
			departmentId = "0";
		}

		List<SSODepartment> subDepList = new ArrayList<SSODepartment> ();
//		List<Map<String, String>> subDepartmentList = orgAPI.getSubDepartmentList(Integer.parseInt(departmentId));
		String sql="select * from ORGDEPARTMENT where PARENTDEPARTMENTID =?";
		List<Map<String, Object>> subDepartmentList = jdbcTemplate.queryForList(sql,departmentId);
		for (Map<String, Object> map : subDepartmentList) {
			SSODepartment dep = new SSODepartment();
			String id = (String) ConvertUtils.convert(map.get("ID"),String.class);
			dep.setId(id);
			String name = (String) ConvertUtils.convert(map.get("DEPARTMENTNAME"),String.class);
			dep.setName(name);

			String parentId = (String) ConvertUtils.convert(map.get("PARENTDEPARTMENTID"),String.class);
			dep.setParentId(parentId);
			subDepList.add(dep);
			
		}
		return subDepList;
	}
	
	public List<Map<String, String>> getSubDepartmentList(long departmentId) {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

		try {
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList("SELECT  * FROM ORGDEPARTMENT WHERE PARENTDEPARTMENTID=" + departmentId);
			for (Map<String, Object> map : list) {
				Map<String, String> depMap = new HashMap<String, String>();
				depMap.put(KEY_DEPARTMENTINFO_ID, (String) ConvertUtils.convert(map.get("ID"), String.class));
				depMap.put(KEY_DEPARTMENTINFO_NAME,
						(String) ConvertUtils.convert(map.get("DEPARTMENTNAME"), String.class));
				depMap.put(KEY_DEPARTMENTINFO_PARENTDEPARTMENTID,
						(String) ConvertUtils.convert(map.get("PARENTDEPARTMENTID"), String.class));
				returnList.add(depMap);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
		return returnList;
	}
	@Override
	public List<SSODepartment> getAllSubDepartmentList(String departmentId) throws SSOIntergrationException {
		if(StringUtils.isEmpty(departmentId)){
			departmentId = "0";
		}
		List<SSODepartment> subDepList = new ArrayList<SSODepartment> ();
		List<Map<String, String>> subDepartmentList = getSubDepartmentList(Integer.parseInt(departmentId));
		for (Map<String, String> map : subDepartmentList) {
			SSODepartment dep = new SSODepartment();
			String id = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_ID),String.class);
			dep.setId(id);
			String name = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_NAME),String.class);
			dep.setName(name);

			String parentId = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_PARENTDEPARTMENTID),String.class);
			dep.setParentId(parentId);
			subDepList.add(dep);
			//递归查询所有子部门
			subDepList.addAll(getAllSubDepartmentList(id));
			
		}
		return subDepList;
	}
	


	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getRoleList()
	 */
	@Override
	public List<SSORole> getRoleList() throws SSOIntergrationException {
		List<SSORole> roleList = new ArrayList<SSORole> ();
		
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

		try {
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList("SELECT  * FROM ORGROLE");
			for (Map<String, Object> map : list) {
				Map<String, String> m = new HashMap<String, String>();
				m.put(KEY_ROLEINFO_ID, (String) ConvertUtils.convert(map.get("ID"), String.class));
				m.put(KEY_ROLEINFO_NAME, (String) ConvertUtils.convert(map.get("ROLENAME"), String.class));
				m.put(KEY_ROLEINFO_GROUPNAME, (String) ConvertUtils.convert(map.get("GROUPNAME"), String.class));
				returnList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
		
		List<Map<String, String>> list = returnList;
		
		for (Map<String, String> map : list) {
			String id = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_ROLEINFO_ID),String.class);
			String name = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_ROLEINFO_NAME),String.class);
//			String groupName = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_ROLEINFO_GROUPNAME),String.class);
			SSORole role = new SSORole();
			role.setId(id);
			role.setName(name);
			roleList.add(role);
			
		}
		
		return roleList;
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getDepartment(java.lang.String)
	 */
	@Override
	public SSODepartment getDepartment(String departmentId) throws SSOIntergrationException {
		
		Map<String, String> map = new HashMap<String, String>();
		try {
			String info = "";
			try{
				Map<String, Object> m = jdbcTemplate.queryForMap("SELECT * FROM ORGDEPARTMENT WHERE ID=?",departmentId);
				map.put(KEY_DEPARTMENTINFO_ID, ObjectConverter.convert2String(m.get("ID")));
				map.put(KEY_DEPARTMENTINFO_NAME, ObjectConverter.convert2String(m.get("DEPARTMENTNAME")));
				map.put(KEY_DEPARTMENTINFO_NO, ObjectConverter.convert2String(m.get("DEPARTMENTNO")));
				
//				info = AwsApi.getInstance().getOrg().getDepartmentInfo(Integer.parseInt(departmentId));
			}catch(Exception e){
				throw e;
//				reloadDepartment();
//				try{
//					info = getORGAPI().getDepartmentInfo(Integer.parseInt(departmentId));
//				}catch(Exception e1){
//					throw e1;
//				}
			}
//			JSONObject jo = JSONObject.fromObject(info);
//			map.put(KEY_DEPARTMENTINFO_ID, jo.getString(KEY_DEPARTMENTINFO_ID));
//			map.put(KEY_DEPARTMENTINFO_NAME, jo.getString(KEY_DEPARTMENTINFO_NAME));
//			map.put(KEY_DEPARTMENTINFO_NO, jo.getString(KEY_DEPARTMENTINFO_NO));
		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
		
		
		String id = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_ID),String.class);
		String name = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_NAME),String.class);
		String parentId = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_PARENTDEPARTMENTID),String.class);

		SSODepartment department = new SSODepartment();
		department.setId(id);
		department.setName(name);
		department.setParentId(parentId);
		
		return department;
	}
	
	
	
	public Map<String, String> getRoleInfo(String roleId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String roleInfo = "";
			try{
				roleInfo = AwsApi.getInstance().getOrg().getRoleInfo(Integer.parseInt(roleId));
			}catch(Exception e){
////				reloadRole();
//				try{
//					roleInfo = getORGAPI().getRoleInfo(Integer.parseInt(roleId));
//				}catch(Exception e1){
//					throw e1;
//				}
				throw e;
			}
			JSONObject jo = JSONObject.fromObject(roleInfo);
			map.put(KEY_ROLEINFO_ID, jo.getString(KEY_ROLEINFO_ID));
			map.put(KEY_ROLEINFO_NAME, jo.getString(KEY_ROLEINFO_NAME));
			map.put(KEY_ROLEINFO_GROUPNAME, jo.getString(KEY_ROLEINFO_GROUPNAME));
		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getRole(java.lang.String)
	 */
	@Override
	public SSORole getRole(String roleId) throws SSOIntergrationException {
		Map<String, String> map = getRoleInfo(roleId);
		String id = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_ROLEINFO_ID),String.class);
		String name = (String) ConvertUtils.convert(map.get(WappedAWSOrgAPI.KEY_ROLEINFO_NAME),String.class);

		SSORole role = new SSORole();
		role.setId(id);
		role.setName(name);
		
		return role;
	}
	
	

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.OrgUserService#getUserDepartmen(java.lang.String)
	 */
	@Override
	public SSODepartment getUserDepartmen(String userId) throws SSOIntergrationException {
		Map<String, String> userDepartmentInfo = getUserDepartmentInfo(userId);
		SSODepartment d = new SSODepartment();
		d.setId(userDepartmentInfo.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_ID));
		d.setName(userDepartmentInfo.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_NAME));
		d.setParentId(userDepartmentInfo.get(WappedAWSOrgAPI.KEY_DEPARTMENTINFO_PARENTDEPARTMENTID));
		return d;
	}

	@Override
	public Map<SSODepartment, List<SSORole>> getUserDepartmentAndRole(String userId) throws SSOIntergrationException {
		/*
		 * 使用直接查询AWS数据库的方式实现，要求PDMS与AWS公用同一个数据库
		 */
		Map<SSODepartment, List<SSORole>> userDepartmenAndRole = new HashMap<SSODepartment, List<SSORole>>();
		//添加用户所在部门和角色
		SSODepartment userDepartmen = getUserDepartmen(userId);
		List<SSORole> userRoles = getUserRoleList(userId);
		userDepartmenAndRole.put(userDepartmen, userRoles);
		//添加兼任部门和角色
		String sql =
					"SELECT                                              	"+
					"	d.ID AS DEPT_ID,                                    "+
					"	d.DEPARTMENTNAME AS DEPT_NAME,                      "+
					"	d.PARENTDEPARTMENTID AS PARENT_DEPT_ID,             "+
					"	r.ID AS ROLE_ID,                                    "+
					"	r.ROLENAME AS ROLE_NAME                             "+
					"FROM ORGUSER u                                         "+
					"	INNER JOIN ORGUSERMAP o ON u.ID = o.MAPID           "+
					"	INNER JOIN ORGROLE r ON o.ROLEID = r.ID             "+
					"	INNER JOIN ORGDEPARTMENT d ON d.ID=o.DEPARTMENTID   "+
					"WHERE U.USERID=?";				                
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,userId);
		for(Map<String, Object> m : list){
			String deptId = ObjectConverter.convert2String(m.get("DEPT_ID"));
			String deptName = ObjectConverter.convert2String(m.get("DEPT_NAME"));
			String parentDeptId = ObjectConverter.convert2String(m.get("PARENT_DEPT_ID"));
			SSODepartment dept = new SSODepartment();
			dept.setId(deptId);
			dept.setName(deptName);
			dept.setParentId(parentDeptId);
			
			String roleId = ObjectConverter.convert2String(m.get("ROLE_ID"));
			String roleName = ObjectConverter.convert2String(m.get("ROLE_NAME"));
			SSORole role = new SSORole();
			role.setId(roleId);
			role.setName(roleName);
			
			if(!userDepartmenAndRole.containsKey(dept)){
				userDepartmenAndRole.put(dept, new ArrayList<SSORole>());
			}
			userDepartmenAndRole.get(dept).add(role);//add role
			
		}
		return userDepartmenAndRole;
	}
	@Override
	public List<SSODepartment> getUserDepartmentList(String userId) throws SSOIntergrationException {
		Map<SSODepartment, List<SSORole>> userDepartmentAndRole = getUserDepartmentAndRole(userId);
		Set<SSODepartment> keySet = userDepartmentAndRole.keySet();
		return new ArrayList<SSODepartment>(keySet);
	}
	
	@Override
	public List<SSORole> getUserAllRoleList(String userId) throws SSOIntergrationException {
		Map<SSODepartment, List<SSORole>> userDepartmentAndRole = getUserDepartmentAndRole(userId);
		Set<SSORole> roles = new HashSet<SSORole>();
		for(SSODepartment dept : userDepartmentAndRole.keySet()){
			List<SSORole> list = userDepartmentAndRole.get(dept);
			roles.addAll(list);
		}
		return new ArrayList<SSORole>(roles);
	}
	
}
