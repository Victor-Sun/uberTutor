package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;
import com.gnomon.common.page.GTPage;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.SysFolderAuthDAO;
import com.gnomon.pdms.dao.SysFolderAuthPermissionDAO;
import com.gnomon.pdms.entity.SysFolderAuthEntity;
import com.gnomon.pdms.entity.SysFolderAuthPermissionEntity;

@Service
@Transactional
public class DeptDocumentService {

	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SysFolderAuthDAO sysFolderAuthDAO;
	
	@Autowired
	private SysFolderAuthPermissionDAO sysFolderAuthPermissionDAO;
	
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 登录者所在部门查询
	 */
	public List<Map<String, Object>> getDepartmentInfo() {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		// 登录者用户ID
		String loginUserId = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_TOP_DEPARTMENT");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.HAS_DEPT_PERMISSION(ID, ?) = 1");
		params.add(loginUserId);
		// 返回查询结果
		return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}

	/**
	 * 部门文档-文档列表取得
	 */
	public GTPage<Map<String, Object>> getDeptDocumentList(String departmentId,
			String sourceId, String documentName, int pageNo, int pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String loginUserId = SessionData.getLoginUserId();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_DOCUMENT_DEPARTMENT");
		sql.append(" WHERE");
		sql.append(" PKG_PERMISSION.HAS_FOLDER_PRIV(?, SOURCE_ID, ?) = 1");
		paramList.add(loginUserId);
		paramList.add(PDMSConstants.FOLDER_PERMISSION_READ);
		if (PDMSCommon.isNotNull(departmentId)) {
			sql.append(" AND DEPARTMENT_ID = ?");
			paramList.add(departmentId);
		}
		if (PDMSCommon.isNotNull(sourceId)) {
			sql.append(" AND SOURCE_ID = ?");
			paramList.add(sourceId);
		}
		if (PDMSCommon.isNotNull(documentName)) {
			sql.append(" AND UPPER(DOCUMENT_NAME) LIKE UPPER(?)");
			paramList.add("%" + documentName + "%");
		}
		sql.append(" ORDER BY");
		sql.append(" SOURCE_ID");
		sql.append(",CREATE_DATE");
		return jdbcTemplate.queryPagination(
				sql.toString(), pageNo, pageSize, paramList.toArray());
    }
	
	/**
	 * 部门文档-新建文件夹
	 */
	public void createFolder(Long parentId, String folderName) {
		String loginUserId = SessionData.getLoginUserId();
		
		// 新建文件夹
		Long folderId = this.documentService.createFolder(parentId, folderName);
		// 登录创建者权限
		this.addFolderAuth(loginUserId, folderId, true, true, true, true);
	}
	
	/**
	 * 部门文档-删除文件夹
	 */
	public void deleteFolder(Long folderId) throws Exception {
		StringBuffer sql = null;
		this.documentService.deleteFolder(folderId);
		// 删除权限信息
		sql = new StringBuffer();
		sql.append(" DELETE FROM SYS_FOLDER_AUTH_PERMISSION");
		sql.append(" WHERE FOLDER_AUTH_ID IN (");
		sql.append(" SELECT ID");
		sql.append(" FROM");
		sql.append(" SYS_FOLDER_AUTH");
		sql.append(" WHERE");
		sql.append(" FOLDER_ID = ?)");
		this.jdbcTemplate.update(sql.toString(), folderId);
		
		sql = new StringBuffer();
		sql.append(" DELETE FROM SYS_FOLDER_AUTH");
		sql.append(" WHERE");
		sql.append(" FOLDER_ID = ?");
		this.jdbcTemplate.update(sql.toString(), folderId);
	}
	
	/**
	 * 部门文档-修改文件夹-增加权限人员
	 */
	public String addFolderAuthUser(Long folderId, List<Map<String, String>> modelList) {
		// 重复用户验证
		StringBuffer result = new StringBuffer();
		List<Map<String, String>> newUserList = new ArrayList<Map<String, String>>();
		for (Map<String, String> model : modelList) {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT DISTINCT");
			sql.append(" ROLEID");
			sql.append(",USERNAME");
			sql.append(" FROM");
			sql.append(" V_SYS_FOLDER_AUTH");
			sql.append(" WHERE");
			sql.append(" FOLDER_ID = ?");
			sql.append(" AND ROLEID = ?");
			List<Map<String, Object>> userList = this.jdbcTemplate.queryForList(
					sql.toString(), folderId, model.get("id"));
			if (userList == null || userList.size() == 0) {
				newUserList.add(model);
			} else {
				for (Map<String, Object> user : userList) {
					if (result.length() > 0) {
						result.append(",");
					}
					result.append(user.get("USERNAME"));
				}
			}
		}
		// 权限用户登录
		for (Map<String, String> model : newUserList) {
			this.addFolderAuth(model.get("id"), folderId, false, true, false, false);
		}
		// 结果返回
		return result.toString();
	}
	
	/**
	 * 部门文档-修改文件夹-修改用户权限
	 */
	public void updateFolderAuth(Long folderId, String userId,
			String permissionCode, boolean permission) {
		String loginUserId = SessionData.getLoginUserId();
		
		// 已有数据查询
		List<SysFolderAuthEntity> currAuth = this.sysFolderAuthDAO.find(
			new Criterion[]{
					Restrictions.eq("roleid", userId),
					Restrictions.eq("folderId", folderId)
		});
		for (SysFolderAuthEntity entity : currAuth) {
			String folderAuthId = entity.getId();
			// 删除已有权限Code
			List<SysFolderAuthPermissionEntity> currAuthPermission =
				this.sysFolderAuthPermissionDAO.find(
					new Criterion[]{
							Restrictions.eq("folderAuthId", folderAuthId),
							Restrictions.eq("folderPermissionCode", permissionCode)
				});
			for (SysFolderAuthPermissionEntity sp : currAuthPermission) {
				this.sysFolderAuthPermissionDAO.delete(sp.getId());
			}
			// 增加对象权限Code
			if (permission) {
				SysFolderAuthPermissionEntity sfapEntity = new SysFolderAuthPermissionEntity();
				sfapEntity.setId(PDMSCommon.generateUUID());
				sfapEntity.setFolderAuthId(folderAuthId);
				sfapEntity.setFolderPermissionCode(permissionCode);
				sfapEntity.setCreateBy(loginUserId);
				sfapEntity.setCreateDate(new Date());
				this.sysFolderAuthPermissionDAO.save(sfapEntity);
			}
		}
	}
	
	/**
	 * 部门文档-文件夹权限用户信息取得
	 */
	public List<Map<String, Object>> getFolderPrivilegeUser(String folderId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_FOLDER_AUTH");
		sql.append(" WHERE");
		sql.append(" FOLDER_ID = ?");
		return this.jdbcTemplate.queryForList(sql.toString(), folderId);
	}
	
	/**
	 * 部门文档-项目角色文件夹权限取得
	 */
	public List<Map<String, Object>> getProfilePrivilege(String userId, String folderId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_SYS_FOLDER_AUTH_PERMISSION");
		sql.append(" WHERE");
		sql.append(" ROLEID = ?");
		sql.append(" AND FOLDER_ID = ?");
		return this.jdbcTemplate.queryForList(sql.toString(), userId, folderId);
	}
	
	/**
	 * 部门文档-修改文件夹-修改文件夹名称
	 */
	public void updateFolderInfo(Long folderId, Map<String, String> model) {
		// 修改文件夹
		this.documentService.updateFolder(folderId, model.get("folderName"));
	}
	
	/**
	 * 部门文档-修改文件夹-确定
	 */
	public void updateFolderConfirm(Long folderId) {
		// 删除多余的权限记录
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM SYS_FOLDER_AUTH T1");
		sql.append(" WHERE NOT EXISTS(");
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" SYS_FOLDER_AUTH_PERMISSION T2");
		sql.append(" WHERE T2.FOLDER_AUTH_ID = T1.ID)");
		sql.append(" AND T1.FOLDER_ID = ?");
		this.jdbcTemplate.update(sql.toString(), folderId);
	}
	
	/**
	 * 部门文档-新建文件夹的用户权限
	 */
	private void addFolderAuth(String userId, Long folderId, boolean ownerPermission,
			boolean readPermission, boolean uploadPermission, boolean deletePermission) {
		SysFolderAuthEntity sfaEntity = null;
		SysFolderAuthPermissionEntity sfapEntity = null;
		String loginUserId = SessionData.getLoginUserId();
		
		// 登录创建者权限
		String folderAuthId = PDMSCommon.generateUUID();
		sfaEntity = new SysFolderAuthEntity();
		sfaEntity.setId(folderAuthId);
		sfaEntity.setRoleid(userId);
		sfaEntity.setFolderId(folderId);
		sfaEntity.setCreateBy(loginUserId);
		sfaEntity.setCreateDate(new Date());
		this.sysFolderAuthDAO.save(sfaEntity);
		// 角色权限Code
		if (ownerPermission) {
			sfapEntity = new SysFolderAuthPermissionEntity();
			sfapEntity.setId(PDMSCommon.generateUUID());
			sfapEntity.setFolderAuthId(folderAuthId);
			sfapEntity.setFolderPermissionCode(PDMSConstants.FOLDER_PERMISSION_OWNER);
			sfapEntity.setCreateBy(loginUserId);
			sfapEntity.setCreateDate(new Date());
			this.sysFolderAuthPermissionDAO.save(sfapEntity);
		}
		if (readPermission) {
			sfapEntity = new SysFolderAuthPermissionEntity();
			sfapEntity.setId(PDMSCommon.generateUUID());
			sfapEntity.setFolderAuthId(folderAuthId);
			sfapEntity.setFolderPermissionCode(PDMSConstants.FOLDER_PERMISSION_READ);
			sfapEntity.setCreateBy(loginUserId);
			sfapEntity.setCreateDate(new Date());
			this.sysFolderAuthPermissionDAO.save(sfapEntity);
		}
		if (uploadPermission) {
			sfapEntity = new SysFolderAuthPermissionEntity();
			sfapEntity.setId(PDMSCommon.generateUUID());
			sfapEntity.setFolderAuthId(folderAuthId);
			sfapEntity.setFolderPermissionCode(PDMSConstants.FOLDER_PERMISSION_UPLOAD);
			sfapEntity.setCreateBy(loginUserId);
			sfapEntity.setCreateDate(new Date());
			this.sysFolderAuthPermissionDAO.save(sfapEntity);
		}
		if (deletePermission) {
			sfapEntity = new SysFolderAuthPermissionEntity();
			sfapEntity.setId(PDMSCommon.generateUUID());
			sfapEntity.setFolderAuthId(folderAuthId);
			sfapEntity.setFolderPermissionCode(PDMSConstants.FOLDER_PERMISSION_DELETE);
			sfapEntity.setCreateBy(loginUserId);
			sfapEntity.setCreateDate(new Date());
			this.sysFolderAuthPermissionDAO.save(sfapEntity);
		}
	}
}

