package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.dms.api.DMSFolder;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.PMNoteEntity;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.PmNoteService;
import com.gnomon.pdms.service.ProjectDocumentService;

@Namespace("/pm")
public class ProjectNoteAction extends PDMSCrudActionSupport<PMNoteEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjectDocumentService projectDocumentService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	private PmNoteService pmNoteService;

	private String programId;
	
	private Long id;
	
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String searchDocumentName;
	
	private String noteContent;
	
	private PMNoteEntity entity;
	
	public String getNoteContent() {
		return noteContent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public void setSearchDocumentName(String searchDocumentName) {
		this.searchDocumentName = searchDocumentName;
	}
	
	private String searchVehicleCode;
	public void setSearchVehicleCode(String searchVehicleCode) {
		this.searchVehicleCode = searchVehicleCode;
	}
	
	private String searchGateCode;
	public void setSearchGateCode(String searchGateCode) {
		this.searchGateCode = searchGateCode;
	}
	
	private String searchDeliverableName;
	public void setSearchDeliverableName(String searchDeliverableName) {
		this.searchDeliverableName = searchDeliverableName;
	}
	
	private String searchTaskName;
	public void setSearchTaskName(String searchTaskName) {
		this.searchTaskName = searchTaskName;
	}

	private String node;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String sourceType;
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String parentId;
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	private String folderId;
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	private String sourceId;
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
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

	/**
	 * 项目文档Tree生成
	 */
	public void getFolderTree() {
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = null;
		String loginUserId = SessionData.getLoginUserId();
		
		// 新建文件夹权限查询
		boolean createFolderPrivilege = this.privilegeService.hasProgramPrivilege(
				loginUserId, programId, "P0701");
		
		if (PDMSCommon.isNotNull(node) && node.equals("root")) {
			// 用户文件夹
			dataMap = new HashMap<String,Object>();
			dataMap.put("id", this.documentService.getProjectNoteFolderId(programId));
			dataMap.put("text", "项目笔记文件夹");
			dataMap.put("sourceType", PDMSConstants.SOURCE_TYPE_CUSTOMIZATION);
			dataMap.put("customizeRoot", true);
			dataMap.put("iconCls", "x-fa fa-folder");
			dataMap.put("viewType", "customizeDocument");
			dataMap.put("showAll", true);
			dataMap.put("createFolderPrivilege", createFolderPrivilege);
			dataMap.put("expanded", false);
			data.add(dataMap);
		} else if (PDMSConstants.SOURCE_TYPE_CUSTOMIZATION.equals(sourceType)) {
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
				boolean writeFolderPrivilege =
						this.privilegeService.hasFolderPrivilege(loginUserId,
								folder.getId(), PDMSConstants.FOLDER_PERMISSION_WRITE);
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", folder.getId());
				dataMap.put("text", folder.getName());
				dataMap.put("sourceType", PDMSConstants.SOURCE_TYPE_CUSTOMIZATION);
				dataMap.put("iconCls", "x-fa fa-folder-open");
				dataMap.put("viewType", "customizeDocument");
				dataMap.put("createFolderPrivilege", createFolderPrivilege);
				dataMap.put("manageFolderPrivilege", manageFolderPrivilege);
				dataMap.put("readFolderPrivilege", readFolderPrivilege);
				dataMap.put("writeFolderPrivilege", writeFolderPrivilege);
				dataMap.put("expanded", true);
				data.add(dataMap);
			}
		} else {
			// 项目经理、总监可查看所有
			if (this.privilegeService.isProgramLeader(loginUserId, programId)) {
				// 项目交付物
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目交付物");
				dataMap.put("iconCls", "x-fa fa-file-text");
				dataMap.put("viewType", "deliverableDocument");
				dataMap.put("leaf", true);
				data.add(dataMap);
				// 项目备案
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目备案");
				dataMap.put("sourceType", PDMSConstants.SOURCE_TYPE_PM_RECORD);
				dataMap.put("iconCls", "x-fa fa-file-archive-o");
				dataMap.put("viewType", "projectDocumentList");
				dataMap.put("leaf", true);
				data.add(dataMap);
				// 项目开发范围
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目开发范围");
				dataMap.put("sourceType", PDMSConstants.SOURCE_TYPE_PM_SCOPE);
				dataMap.put("iconCls", "x-fa fa-arrows-h");
				dataMap.put("viewType", "projectDocumentList");
				dataMap.put("leaf", true);
				data.add(dataMap);
				// 项目附件
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目附件");
				dataMap.put("sourceType", PDMSConstants.SOURCE_TYPE_PM_ATTACHMENT);
				dataMap.put("iconCls", "x-fa fa-envelope");
				dataMap.put("viewType", "projectDocumentList");
				dataMap.put("leaf", true);
				data.add(dataMap);
				// 项目过程文档
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目过程文档");
				dataMap.put("iconCls", "x-fa fa-copy");
				dataMap.put("viewType", "taskDocument");
				dataMap.put("leaf", true);
				data.add(dataMap);
			}
			// 项目成员可查看纪要
			if (this.privilegeService.isProgramMember(loginUserId, programId)) {
				// 项目纪要
				dataMap = new HashMap<String,Object>();
				dataMap.put("id", PDMSCommon.generateUUID());
				dataMap.put("text", "项目会议纪要");
				dataMap.put("iconCls", "x-fa fa-sticky-note-o");
				dataMap.put("viewType", "meetingDocument");
				dataMap.put("leaf", true);
				data.add(dataMap);
			}
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}

	/**
	 * 项目文档列表取得
	 */
	public void getProjectDocumentList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.projectDocumentService.getProjectDocumentList(
							programId, searchDocumentName, sourceType,
							this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("sourceType", map.get("SOURCE_TYPE"));
				dataMap.put("sourceId", map.get("SOURCE_ID"));
				dataMap.put("sourceName", map.get("SOURCE_NAME"));
				dataMap.put("documentId", map.get("DOCUMENT_ID"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
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
	 * 项目文档-交付物文档列表取得
	 */
	public void getDeliverableDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String vehicleCode = "";
//			if (PDMSCommon.isNotNull(searchVehicleCode)) {
//				vehicleCode = new String(searchVehicleCode.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String gateCode = "";
//			if (PDMSCommon.isNotNull(searchGateCode)) {
//				gateCode = new String(searchGateCode.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String deliverableName = "";
//			if (PDMSCommon.isNotNull(searchDeliverableName)) {
//				deliverableName = new String(searchDeliverableName.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.projectDocumentService.getDeliverableDocumentList(
							programId, searchVehicleCode, searchGateCode, searchDeliverableName,
							searchDocumentName, this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("DOCUMENT_INDEX_ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("gateCode", map.get("GATE_CODE"));
				dataMap.put("deliverableName", map.get("DELIVERABLE_NAME"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("groupHeader", "车型编码：" + map.get("VEHICLE_CODE") + " | "
										 + "所属阀门：" + map.get("GATE_CODE") + " | "
										 + "交付物名称：" + map.get("DELIVERABLE_NAME"));
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
	 * 项目文档-项目过程文档列表取得
	 */
	public void getTaskDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String vehicleCode = "";
//			if (PDMSCommon.isNotNull(searchVehicleCode)) {
//				vehicleCode = new String(searchVehicleCode.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String taskName = "";
//			if (PDMSCommon.isNotNull(searchTaskName)) {
//				taskName = new String(searchTaskName.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.projectDocumentService.getTaskDocumentList(
							programId, searchVehicleCode, searchTaskName, searchDocumentName,
							this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("DOCUMENT_INDEX_ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("sourceTypeName", map.get("SOURCE_TYPE_NAME"));
				dataMap.put("groupHeader", "车型编码：" + map.get("VEHICLE_CODE") + " | "
										 + "任务名称：" + map.get("TASK_NAME"));
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
	 * 项目文档-项目会议纪要文档列表取得
	 */
	public void getMeetingDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String vehicleCode = "";
//			if (PDMSCommon.isNotNull(searchVehicleCode)) {
//				vehicleCode = new String(searchVehicleCode.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.projectDocumentService.getMeetingDocumentList(
							programId, searchVehicleCode, searchDocumentName,
							this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("DOCUMENT_INDEX_ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("sourceTypeName", map.get("SOURCE_TYPE_NAME"));
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
	 * 项目文档-用户自定义文档列表取得
	 */
	public void getCustomizeDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			GTPage<Map<String, Object>> pageResult =
					this.projectDocumentService.getCustomizeDocumentList(
							programId, sourceId, searchDocumentName,
							this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("docmentIndexId", map.get("DOCUMENT_INDEX_ID"));
				dataMap.put("documentRevisionId", map.get("DOCUMENT_REVISION_ID"));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("documentName", map.get("DOCUMENT_NAME"));
				dataMap.put("createBy", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				if (PDMSCommon.isNull(sourceId)) {
					dataMap.put("folderName", this.documentService.getFolderById(
							Long.parseLong(PDMSCommon.nvl(map.get("SOURCE_ID")))).getName());
				}
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
	 * 新建文件夹
	 */
	public void createFolder() {
		try {
			this.projectDocumentService.createFolder(new Long(parentId), folderName);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 删除文件夹
	 */
	public void deleteFolder() {
		try {
			this.projectDocumentService.deleteFolder(new Long(folderId));
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
			String result = this.projectDocumentService.addFolderAuthUser(
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
			this.projectDocumentService.updateFolderAuth(new Long(folderId),
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
			this.projectDocumentService.updateFolderInfo(new Long(folderId), model);
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
			this.projectDocumentService.updateFolderConfirm(new Long(folderId));
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
					this.projectDocumentService.getFolderPrivilegeUser(folderId);
			for(Map<String, Object> map : profileList) {
				dataMap = new HashMap<String,Object>();
				dataMap.put("userId", map.get("ROLEID"));
				dataMap.put("userName", map.get("USERNAME"));
				boolean ownerPrivilege = false;
				boolean readPrivilege = false;
				boolean writePrivilege = false;
				// 项目角色权限取得
				List<Map<String, Object>> privilegeList =
						this.projectDocumentService.getProfilePrivilege(
								PDMSCommon.nvl(map.get("ROLEID")), folderId);
				for(Map<String, Object> privilege : privilegeList) {
					if (PDMSConstants.FOLDER_PERMISSION_OWNER.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						ownerPrivilege = true;
					} else if (PDMSConstants.FOLDER_PERMISSION_READ.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						readPrivilege = true;
					} else if (PDMSConstants.FOLDER_PERMISSION_WRITE.equals(
							privilege.get("FOLDER_PERMISSION_CODE"))) {
						writePrivilege = true;
					}
				}				
				dataMap.put("ownerPrivilege", ownerPrivilege);
				dataMap.put("readPrivilege", readPrivilege);
				dataMap.put("writePrivilege", writePrivilege);
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
//		System.out.println(programId+noteContent);
		try {
			pmNoteService.save(entity);
			Struts2Utils.renderHtml(entity.getId().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void getNote(){
		PMNoteEntity note = pmNoteService.get(id);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("title", note.getNoteTitle());
		dataMap.put("noteContent", note.getNoteContent());
		Struts2Utils.renderJson(dataMap);
		
	}
	
	public void getNoteList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<PMNoteEntity> list = pmNoteService.getNoteList(Long.valueOf(folderId));
			for(PMNoteEntity note : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", note.getId());
				dataMap.put("title", note.getNoteTitle());
				dataMap.put("noteContent", note.getNoteContent());
				dataMap.put("createDate", DateUtils.formate(note.getCreateDate(), DateUtils.FORMAT_PATTEN_DATETIME));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,list.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String delete() throws Exception {
		pmNoteService.delete(id);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new PMNoteEntity();
			String createBy = SessionData.getLoginUserId();
			entity.setCreateBy(createBy);
			entity.setCreateDate(new Date());
		}else{
			entity = pmNoteService.get(id);
		}
	}

	@Override
	public PMNoteEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
	}

}
