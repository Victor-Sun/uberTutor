package com.gnomon.pdms.action.com;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.dms.api.DMSDocument;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.intergration.sso.OrgUserService;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.DocumentIndexVMEntity;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.DocumentService;

@Namespace("/com")
public class DocumentAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private OrgUserService orgUserService;
	
	private File upload;
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	private String uploadFileName;
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	private String programId;
	
	private String documentId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	private String sourceType;
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	private String sourceId;
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	private String documentIdxId;
	public void setDocumentIdxId(String documentIdxId) {
		this.documentIdxId = documentIdxId;
	}
	
	private String revisionId;
	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}
	
	private String folderId;
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	private String departmentId;
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 文件信息取得
	 */
	public void getDocumentInfo() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<DocumentIndexVMEntity> list =
					this.documentService.getDocumentInfo(sourceType, sourceId);
			for (DocumentIndexVMEntity entity : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", entity.getId());
				dataMap.put("documentRevisionId", entity.getDocumentRevisionId());
				dataMap.put("documentName", entity.getDocumentName());
				dataMap.put("createByName", entity.getCreateByName());
		        dataMap.put("createDate", DateUtils.change(entity.getCreateDate()));
		        dataMap.put("documentTypeIcons", getDocumentTypeIcons(entity.getDocumentName()));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getDocumentTypeIcons(String documentName){
		String documentTypeIcons = "fa fa-file-excel-o";
		if(documentName.endsWith("doc") || documentName.endsWith("docx")){
			documentTypeIcons = "fa fa-file-word-o";
		}else if(documentName.endsWith("xls") || documentName.endsWith("xlsx")){
			documentTypeIcons = "fa fa-file-excel-o";
		}else if(documentName.endsWith("ppt") || documentName.endsWith("pptx")){
			documentTypeIcons = "fa fa-file-powerpoint-o";
		}else if(documentName.endsWith("pdf")){
			documentTypeIcons = "fa fa-file-pdf-o";
		}else if(documentName.endsWith("txt") || documentName.endsWith("docx")){
			documentTypeIcons = "fa fa-file-text-o";
		}else if(documentName.endsWith("rar") || documentName.endsWith("zip")){
			documentTypeIcons = "fa fa-file-archive-o";
		}else if(documentName.endsWith("bmp") || documentName.endsWith("gif") || documentName.endsWith("jpeg") || documentName.endsWith("png")){
			documentTypeIcons = "fa fa-file-image-o";
		}
		return documentTypeIcons;
	}
	
	/**
	 * 上传文件
	 */
	public void uploadDocument() {
		try{
			this.documentService.saveGTDocumentIndex(programId, sourceType,
					sourceId, folderId, uploadFileName, departmentId, upload,revisionId);
			this.writeSuccessResult(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	

	
	/**
	 * 删除文件
	 */
	public void deleteDocument(){
		try{
			this.documentService.deleteGTDocumentIndex(documentIdxId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 下载文件
	 */
	public void downloadDocument() {
		try {
			//DMSDocumentRevision revision =
			//		DmsAPIFactory.getDocumentAPI().getRevision(Long.parseLong(revisionId));
			// 文件信息取得
	
			DMSDocument document = DmsAPIFactory.getDocumentAPI().getRevision(Long.valueOf(revisionId)).getDocument();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-download");
			// 取得输出流
			OutputStream os = response.getOutputStream();
			// 清空输出流
			response.reset();
			//String filename = URLEncoder.encode(revision.getDocument().getName(), "UTF-8");
			String filename = URLEncoder.encode(document.getName(), "UTF-8");
			// 设定输出文件头
			response.setHeader("Content-disposition","attachment; filename="+ filename);
			DmsAPIFactory.getDocumentAPI().getRevisionContent(Long.parseLong(revisionId), os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getHistoryRevisionList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//			String documentName = "";
//			if (PDMSCommon.isNotNull(searchDocumentName)) {
//				documentName = new String(searchDocumentName.getBytes("ISO-8859-1"), "UTF-8");
//			}
			List<DMSDocumentRevision> list = documentService.getHistoryRevisionList(Long.valueOf(documentId));
			for(DMSDocumentRevision revision : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("documentRevisionId", revision.getId());
				dataMap.put("documentId", revision.getDocument().getId());
				dataMap.put("documentName", revision.getDocument().getName());
				dataMap.put("createBy", orgUserService.getUserInfo(revision.getCreateUser()).getUserName());
				dataMap.put("createDate", ObjectConverter.convertDate2String(revision.getCreateDate(),DateUtils.FORMAT_PATTEN_DATETIME));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
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

}
