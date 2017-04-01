package com.gnomon.common.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.dao.GTUserDao;
import com.gnomon.common.system.dao.SysDepartmentDAO;
import com.gnomon.common.system.dao.SysUserVMDAO;
import com.gnomon.common.system.entity.SysDepartmentEntity;
import com.gnomon.common.system.entity.SysUserEntity;

@Service
@Transactional
public class SysUserDepartmentService {
	
	@Autowired
	private SysUserVMDAO sysUserVMDAO;
	
	@Autowired
	private SysDepartmentDAO sysDepartmentDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GTUserDao gtUserDao;
	
	/*
	 * 系统用户信息取得
	 */
	public List<Map<String, Object>> getSysUserList(
			String departmentId, boolean includeSubDept,
			String searchUserName, String deptType) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		if ("group".equals(deptType)) {
			sql.append(" SELECT T2.*");
			sql.append(" FROM");
			sql.append(" SYS_MY_WORKGROUP_MEMBER T1 INNER JOIN V_SYS_USER T2");
			sql.append(" ON T1.USER_ID = T2.ID");
			sql.append(" WHERE");
			sql.append(" T1.WORKGROUP_ID = ?");
			params.add(departmentId);
			if (searchUserName != null && searchUserName.length() > 0) {
				sql.append(" AND (T2.USERNAME LIKE ? OR T2.USERID LIKE ? ) ");
				params.add("%" + searchUserName + "%");
				params.add("%" + searchUserName + "%");
			}
		} else {
			// 查询用户列表
			if (! includeSubDept) {
				// 不包含下级部门人员
				sql.append(" SELECT *");
				sql.append(" FROM");
				sql.append(" V_SYS_USER");
				sql.append(" WHERE");
				sql.append(" DEPARTMENT_ID = ?");
				params.add(departmentId);
				if (searchUserName != null && searchUserName.length() > 0) {
					sql.append(" AND (USERNAME LIKE ? or USERID LIKE ? ) ");
					params.add("%" + searchUserName + "%");
					params.add("%" + searchUserName + "%");
				}
			} else {
				// 包含下级部门
				sql.append(" SELECT T2.*");
				sql.append(",T1.DEPARTMENT_NAME");
				sql.append(",T4.TITLE PROFILE_NAME");
				sql.append(",T5.NAME AS HR_POSITION");
				sql.append(" FROM");
				sql.append(" (SELECT ID, NAME AS DEPARTMENT_NAME FROM SYS_DEPARTMENT START WITH ID = ? CONNECT BY PRIOR ID = PARENT_ID) T1");
				sql.append(",SYS_USER T2");
				sql.append(",SYS_USER_ROLE T3");
				sql.append(",SYS_ROLE T4");
				sql.append(",SYS_POSITION T5");
				sql.append(" WHERE");
				sql.append(" T1.ID = T2.DEPARTMENT_ID");
				sql.append(" AND T2.USERID = T3.USER_ID");
				sql.append(" AND T3.ROLE_ID = T4.ROLE_ID");
				sql.append(" AND T2.USERID = T5.USER_ID(+)");
				params.add(departmentId);
				if (searchUserName != null && searchUserName.length() > 0) {
					sql.append(" AND (T2.USERNAME LIKE ? or T2.USERID LIKE ? )  ");
					params.add("%" + searchUserName + "%");
					params.add("%" + searchUserName + "%");
				}
			}
		}
		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
	
	/**
	 * 系统用户信息取得（全部）
	 */
	public List<Map<String, Object>> getSysUserList(String userName) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_USER");
		sql.append(" WHERE");
		sql.append(" 1 = 1");
		if (userName != null && userName.length() > 0) {
			sql.append(" AND UPPER(USERNAME) LIKE UPPER(?)");
			params.add("%" + userName + "%");
		}
		sql.append(" ORDER BY EMPLOYEE_NO");
		
		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
	
	/*
	 * 系统用户信息取得(专业经理)
	 */
	public List<Map<String, Object>> getUserList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_USER");
		sql.append(" ORDER BY EMPLOYEE_NO");

		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString());
    }
	
	/*
	 * 系统组织信息取得
	 */
	public List<SysDepartmentEntity> getSysDepartmentList() {
		List<SysDepartmentEntity> result = this.sysDepartmentDAO.getAll();
        return result;
    }

	public void resetPassword(String userId){
		SysUserEntity sysUserEntity = gtUserDao.get(userId);
		sysUserEntity.setPassword("oqGiG3w2C/s4l945xI++My4Wpv2cCyLi");
		gtUserDao.save(sysUserEntity);
	}
	
	/**
	 * 取得系统部门
	 */
	public List<Map<String, Object>> getDeptList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_DEPARTMENT_TREE");
		sql.append(" ORDER BY S_LEVEL");

		// 返回结果
		return this.jdbcTemplate.queryForList(sql.toString());
    }

	/**
	 * 取得系统用户
	 */
	public Map<String, Object> getDeptUserInfo(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_DEPT_USER");
		sql.append(" WHERE");
		sql.append(" ID = ?");
		// 返回结果
		List<Map<String, Object>> userList =
				this.jdbcTemplate.queryForList(sql.toString(), userId);
		if (userList.size() > 0) {
			return userList.get(0);
		} else {
			return new HashMap<String, Object>();
		}
    }
	
	/**
	 * 取得指定部门下的所有组织
	 */
	public List<Map<String, Object>> getDeptListByRootDeptId(String rootDeptId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		sql.append(" SELECT");
		sql.append(" T1.ID");
		sql.append(",T1.NAME");
		sql.append(",T1.S_LEVEL");
		sql.append(" FROM");
		sql.append(" V_SYS_DEPARTMENT_TREE T1");
		sql.append(" WHERE");
		sql.append(" T1.TOP_DEPARTMENT_ID = ?");
//		sql.append(" START WITH T1.PARENT_ID IS NULL CONNECT BY PRIOR T1.ID = T1.PARENT_ID");
//		sql.append(" UNION");
//		sql.append(" SELECT");
//		sql.append(" T1.ID");
//		sql.append(",T1.NAME");
//		sql.append(",T1.S_LEVEL");
//		sql.append(" FROM");
//		sql.append(" V_SYS_DEPARTMENT_TREE T1");
//		sql.append(" WHERE");
//		sql.append(" T1.ID = ?");
		sql.append(" ORDER BY S_LEVEL");
//		paramList.add(rootDeptId);
		paramList.add(rootDeptId);
		return this.jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}
}
