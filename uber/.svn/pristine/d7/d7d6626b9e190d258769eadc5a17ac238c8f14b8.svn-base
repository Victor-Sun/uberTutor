package com.gnomon.pdms.action.dws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.dms.api.DMSFolder;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.DeptDocumentService;
import com.gnomon.pdms.service.DocumentService;

@Namespace("/dws")
public class DeptDocumentAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DeptDocumentService deptDocumentService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	private String node;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String searchDocumentName;
	public void setSearchDocumentName(String searchDocumentName) {
		this.searchDocumentName = searchDocumentName;
	}
	
	private String folderId;
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	private String departmentId;
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	private String parentId;
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	private String folderName;
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	private String userId;
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	private String permissionCode;
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	
	private boolean permission;
	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 部门文档Tree生成
	 */
	public void getDocumentTree() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;
			String loginUserId = SessionData.getLoginUserId();
			
			// 根文件夹权限查询
			boolean createFolderPrivilege = this.privilegeService.hasSystemPrivilege(
					SessionData.getUserId(), "800302");

			// Tree生成
			if (PDMSCommon.isNotNull(node) && node.equals("root")) {
				// 部门信息查询
				List<Map<String, Object>> deptList = this.deptDocumentService.getDepartmentInfo();
				for (Map<String, Object> dept : deptList) {
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", this.documentService.getDepartmentFolderId(
							PDMSCommon.nvl(dept.get("ID"))));
					dataMap.put("text", dept.get("NAME"));
					dataMap.put("iconCls", "x-fa fa-folder");
					dataMap.put("viewType", "deptDocumentList");
					dataMap.put("createFolderPrivilege", createFolderPrivilege);
					dataMap.put("departmentId", dept.get("ID"));
					dataMap.put("rootFolder", true);
					dataMap.put("expanded", false);
					data.add(dataMap);
				}
			} else {
				List<DMSFolder> folderList =
						DmsAPIFactory.getFolderAPI().getChildFolderList(new Long(node));
				for (DMSFolder folder : folderList) {
					// 如果没有对于文件夹的读取权限，则不需显示
					boolean manageFolderPrivilege =
							this.privilegeService.hasFolderPrivilege(loginUserId,
									folder.getId(), PDMSConstants.FOLDER_PERMISSION_OWNER);
					boolean readFolderPrivilege =
							this.privilegeService.hasFolderPrivilege(loginUserId,
									folder.getId(), PDMSConstants.FOLDER_PERMISSION_READ);
					boolean uploadFolderPrivilege =
							this.privilegeService.hasFolderPrivilege(loginUserId,
									folder.getId(), PDMSConstants.FOLDER_PERMISSION_UPLOAD);
					boolean deleteFolderPrivilege =
							this.privilegeService.hasFolderPrivilege(loginUserId,
									folder.getId(), PDMSConstants.FOLDER_PERMISSION_DELETE);
					dataMap = new HashMap<String,Object>();
					dataMap.put("id", folder.getId());
					dataMap.put("text", folder.getName());
					dataMap.put("iconCls", "x-fa fa-folder-open");
					dataMap.put("viewType", "deptDocumentList");
					dataMap.put("createFolderPrivilege", createFolderPrivilege);
					dataMap.put("manageFolderPrivilege", manageFolderPrivilege);
					dataMap.put("readFolderPrivilege", readFolderPrivilege);
					dataMap.put("uploadFolderPrivilege", uploadFolderPrivilege);
					dataMap.put("deleteFolderPrivilege", deleteFolderPrivilege);
					dataMap.put("departmentId", departmentId);
					dataMap.put("expanded", true);
					data.add(dataMap);
				}
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 部门文档-文档列表取得
	 */
	public void getDocumentList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.deptDocumentService.getDeptDocumentList(departmentId, folderId,
							searchDocumentName, this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("DOCUMENT_INDEX_ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("folderName", this.documentService.getFolderById(
						Long.parseLong(PDMSCommon.nvl(map.get("SOURCE_ID")))).getName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 部门文档-新建文件夹
	 */
	public void createFolder() {
		try {
			this.deptDocumentService.createFolder(new Long(parentId), folderName);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 部门文档-删除文件夹
	 */
	public void deleteFolder() {
		try {
			this.deptDocumentService.deleteFolder(new Long(folderId));
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 修改文件夹-增加权限人员
	 */
	public void addFolderAuthUser() {
		try {
			// JSON解析
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			String result = this.deptDocumentService.addFolderAuthUser(
					new Long(folderId), modelList);
			if (PDMSCommon.isNotNull(result)) {
				this.writeSuccessResult("下列人员已在权限列表中、不需要添加<BR>" + result);
			} else {
				this.writeSuccessResult(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 修改文件夹-修改用户权限
	 */
	public void updateFolderAuth() {
		try {
			this.deptDocumentService.updateFolderAuth(new Long(folderId),
					userId, permissionCode, permission);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 修改文件夹-修改名称
	 */
	public void updateFolderInfo() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.deptDocumentService.updateFolderInfo(new Long(folderId), model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 修改文件夹-确定
	 */
	public void updateFolderConfirm() {
		try {
			this.deptDocumentService.updateFolderConfirm(new Long(folderId));
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 文件夹权限列表取得
	 */
	public void getFolderPrivilege() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;

			// 文件夹属性
			DMSFolder folder = this.documentService.getFolderById(new Long(folderId));
			// 文件夹权限列表
			List<Map<String, Object>> profileList =
					this.deptDocumentService.getFolderPrivilegeUser(folderId);
			for(Map<String, Object> map : profileList) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("userId", map.get("ROLEID"));
				dataMap.put("userName", map.get("USERNAME"));
				boolean ownerPrivilege = false;
				boolean readPrivilege = false;
				boolean uploadPrivilege = false;
				boolean deletePrivilege = false;
				// 项目角色权限取得
				List<Map<String, Object>> privilegeList =
						this.deptDocumentService.getProfilePrivilege(
								PDMSCommon.nvl(map.get("ROLEID")), folderId);
				for(Map<String, Object> privilege : privilegeList) {
					if (PDMSConstants.FOLDER_PERMISSION_OWNER.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						ownerPrivilege = true;
					} else if (PDMSConstants.FOLDER_PERMISSION_READ.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						readPrivilege = true;
					} else if (PDMSConstants.FOLDER_PERMISSION_UPLOAD.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						uploadPrivilege = true;
					} else if (PDMSConstants.FOLDER_PERMISSION_DELETE.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						deletePrivilege = true;
					}
				}				
				dataMap.put("ownerPrivilege", ownerPrivilege);
				dataMap.put("readPrivilege", readPrivilege);
				dataMap.put("uploadPrivilege", uploadPrivilege);
				dataMap.put("deletePrivilege", deletePrivilege);
				if (map.get("ROLEID").equals(folder.getCreateUser())) {
					dataMap.put("createByFlag", true);
				} else {
					dataMap.put("createByFlag", false);
				}
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
