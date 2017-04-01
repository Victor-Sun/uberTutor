package com.gnomon.pdms.action.workorder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.DocumentIndexVMEntity;
import com.gnomon.pdms.entity.WorkOrderEntity;
import com.gnomon.pdms.procedure.PkgPmWorkOrderDBProcedureServcie;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.WorkOrderService;

@Namespace("/workorder")
public class WorkorderAction extends PDMSCrudActionSupport<WorkOrderEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private WorkOrderEntity workOrderEntity;

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private PkgPmWorkOrderDBProcedureServcie pkgPmWorkOrderDBProcedureServcie;
	
	@Autowired
	private DocumentService documentService;
	
	@Override
	public WorkOrderEntity getModel() {
		return workOrderEntity;
	}
	
	private File upload;
	
	private String uploadFileName;
	
	private String documentIndexId;
	
	private Long revisionId;
	
	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getDocumentIndexId() {
		return documentIndexId;
	}

	public void setDocumentIndexId(String documentIndexId) {
		this.documentIndexId = documentIndexId;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	private String model;
	
	private String method;

	private Long id;
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	/**
	 * 取得工作联系单草稿
	 */
	public void getWorkOrderDraftList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			String userId = SessionData.getLoginUserId();
			GTPage<Map<String, Object>> queryResult = this.workOrderService.getWorkOrderDraftList(userId,this.getPage(),this.getLimit());
			for (Map<String, Object> map : queryResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("sourceDept", map.get("SOURCE_DEPT"));
				dataMap.put("targetDept", map.get("TARGET_DEPT"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("workType", map.get("WORK_TYPE"));
				dataMap.put("workTypeName", map.get("WORK_TYPE_NAME"));
				dataMap.put("contactName", map.get("CONTACT_NAME"));
				dataMap.put("contactPhone", map.get("CONTACT_PHONE"));
				dataMap.put("workDescription", map.get("WORK_DESCRIPTION"));
				dataMap.put("isProjectType", map.get("IS_PROJECT_TYPE"));
				dataMap.put("onlineMode", map.get("ONLINE_MODE"));
				dataMap.put("woAgent", map.get("WO_AGENT"));
				dataMap.put("pgDirector", map.get("PG_DIRECTOR"));
				dataMap.put("lineMgr", map.get("LINE_MGR"));
				dataMap.put("deptDirector", map.get("DEPT_DIRECTOR"));
				dataMap.put("dueDate", ObjectConverter.convertDate2String(map.get("DUE_DATE"),DateUtils.FORMAT_PATTEN_DATE));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("createBy", map.get("CREATE_USER_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("status", map.get("STATUS_CODE"));
				dataMap.put("woType", map.get("WO_TYPE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,queryResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 取得接收工作联系单列表
	 */
	public void getInBoundWorkOrderList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> queryResult = this.workOrderService.getInBoundWorkOrderList(this.getPage(),this.getLimit());
			for (Map<String, Object> map : queryResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("sourceDept", map.get("SOURCE_DEPT"));
				dataMap.put("targetDept", map.get("TARGET_DEPT"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("workType", map.get("WORK_TYPE"));
				dataMap.put("workTypeName", map.get("WORK_TYPE_NAME"));
				dataMap.put("contactName", map.get("CONTACT_NAME"));
				dataMap.put("contactPhone", map.get("CONTACT_PHONE"));
				dataMap.put("workDescription", map.get("WORK_DESCRIPTION"));
				dataMap.put("isProjectType", map.get("IS_PROJECT_TYPE"));
				dataMap.put("onlineMode", map.get("ONLINE_MODE"));
				dataMap.put("woAgent", map.get("WO_AGENT"));
				dataMap.put("pgDirector", map.get("PG_DIRECTOR"));
				dataMap.put("lineMgr", map.get("LINE_MGR"));
				dataMap.put("deptDirector", map.get("DEPT_DIRECTOR"));
				dataMap.put("dueDate", ObjectConverter.convertDate2String(map.get("DUE_DATE"),DateUtils.FORMAT_PATTEN_DATE));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("createBy", map.get("CREATE_USER_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("status", map.get("STATUS_CODE"));
				dataMap.put("navImageName", map.get("NAV_IMAGE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,queryResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得接收工作联系单列表
	 */
	public void getOutBoundWorkOrderList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> queryResult = this.workOrderService.getOutBoundWorkOrderList(this.getPage(),this.getLimit());
			for (Map<String, Object> map : queryResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("sourceDept", map.get("SOURCE_DEPT"));
				dataMap.put("targetDept", map.get("TARGET_DEPT"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("workType", map.get("WORK_TYPE"));
				dataMap.put("workTypeName", map.get("WORK_TYPE_NAME"));
				dataMap.put("contactName", map.get("CONTACT_NAME"));
				dataMap.put("contactPhone", map.get("CONTACT_PHONE"));
				dataMap.put("workDescription", map.get("WORK_DESCRIPTION"));
				dataMap.put("isProjectType", map.get("IS_PROJECT_TYPE"));
				dataMap.put("onlineMode", map.get("ONLINE_MODE"));
				dataMap.put("woAgent", map.get("WO_AGENT"));
				dataMap.put("pgDirector", map.get("PG_DIRECTOR"));
				dataMap.put("lineMgr", map.get("LINE_MGR"));
				dataMap.put("deptDirector", map.get("DEPT_DIRECTOR"));
				dataMap.put("dueDate", ObjectConverter.convertDate2String(map.get("DUE_DATE"),DateUtils.FORMAT_PATTEN_DATE));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("createBy", map.get("CREATE_USER_NAME"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("status", map.get("STATUS_CODE"));
				dataMap.put("navImageName", map.get("NAV_IMAGE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,queryResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 部门空间-工作联系单列表
	 */
	public void getDeptWorkOrderList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> queryResult =
					this.workOrderService.getDeptWorkOrderList(
							searchModel, this.getPage(),this.getLimit());
			// 数据整理
			for (Map<String, Object> map : queryResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("WORK_ORDER_ID"));
				dataMap.put("sourceDept", map.get("SOURCE_DEPT"));
				dataMap.put("targetDept", map.get("TARGET_DEPT"));
				dataMap.put("programName", map.get("PROGRAM_NAME"));
				dataMap.put("workTypeName", map.get("WORK_TYPE_NAME"));
				dataMap.put("contactName", map.get("CONTACT_NAME"));
				dataMap.put("contactPhone", map.get("CONTACT_PHONE"));
				dataMap.put("workDescription", map.get("WORK_DESCRIPTION"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("createByName", map.get("CREATE_BY_NAME"));
				dataMap.put("createDate", DateUtils.change((Date)map.get("WORK_ORDER_CREATE_DATE")));
				dataMap.put("woTypeName", map.get("WO_TYPE_NAME"));
				dataMap.put("status", map.get("STATUS_CODE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,queryResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getWorkOrderTypeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.workOrderService.getWorkOrderTypeList();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getWorkOrderWorkTypeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.workOrderService.getWorkOrderWorkTypeList();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("name", map.get("CODE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateOutboundWorkOrder(){
		String createBy = SessionData.getLoginUserId();
		try {
			workOrderEntity.setCreateBy(createBy);
			Map<String,Object> map = pkgPmWorkOrderDBProcedureServcie.updateOutboundWorkOrder(workOrderEntity);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("workOrderId", map.get("id"));
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateInboundWorkOrder(){
		String createBy = SessionData.getLoginUserId();
		try {
			workOrderEntity.setCreateBy(createBy);
			pkgPmWorkOrderDBProcedureServcie.updateInboundWorkOrder(workOrderEntity);
			Map<String,Object> dataMap = new HashMap<String,Object>();
//			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void processOutboundStep(){
		String createBy = SessionData.getLoginUserId();
		try {
			workOrderEntity.setCreateBy(createBy);
			pkgPmWorkOrderDBProcedureServcie.processOutboundStep(workOrderEntity);
			Map<String,Object> dataMap = new HashMap<String,Object>();
//			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void processInboundStep(){
		String createBy = SessionData.getLoginUserId();
		try {
			workOrderEntity.setCreateBy(createBy);
			pkgPmWorkOrderDBProcedureServcie.processInboundStep(workOrderEntity);
			Map<String,Object> dataMap = new HashMap<String,Object>();
//			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getUserList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = workOrderService.getUserList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("USERNAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getWorkOrderNotes() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = workOrderService.getWorkOrderWorkNoteList(id);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("roleId", map.get("ROLE_ID"));
				dataMap.put("date", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("paragraph", map.get("OWNER_COMMENT"));
				dataMap.put("stepId", map.get("STEP_NAME"));
				dataMap.put("author", map.get("TASK_OWNER_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getWorkOrderForm() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object>  map = workOrderService.getWorkOrderForm(id);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("sourceDept", map.get("SOURCE_DEPT"));
			dataMap.put("targetDept", map.get("TARGET_DEPT"));
			dataMap.put("programName", map.get("PROGRAM_NAME"));
			dataMap.put("workType", map.get("WORK_TYPE"));
			dataMap.put("workTypeName", map.get("WORK_TYPE_NAME"));
			dataMap.put("contactName", map.get("CONTACT_NAME"));
			dataMap.put("contactPhone", map.get("CONTACT_PHONE"));
			dataMap.put("workDescription", map.get("WORK_DESCRIPTION"));
			dataMap.put("isProjectType", map.get("IS_PROJECT_TYPE"));
			dataMap.put("onlineMode", map.get("ONLINE_MODE"));
			dataMap.put("woAgent", map.get("WO_AGENT"));
			dataMap.put("pgDirector", map.get("PG_DIRECTOR"));
			dataMap.put("lineMgr", map.get("LINE_MGR"));
			dataMap.put("deptMgr", map.get("DEPT_MGR"));
			dataMap.put("deptMgr", map.get("DEPT_MGR"));
			dataMap.put("eng", map.get("ENG"));
			dataMap.put("deptDirector", map.get("DEPT_DIRECTOR"));
			dataMap.put("dueDate", ObjectConverter.convertDate2String(map.get("DUE_DATE"),DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("title", map.get("TITLE"));
			dataMap.put("formClassId", map.get("FORM_CLASS_ID"));
			dataMap.put("createBy", map.get("CREATE_USER_NAME"));
			dataMap.put("pm", map.get("PM"));
			dataMap.put("createDate", ObjectConverter.convertDate2String(map.get("CREATE_DATE"),DateUtils.FORMAT_PATTEN_DATE));
			dataMap.put("responseDescription", map.get("RESPONSE_DESCRIPTION"));
			dataMap.put("responseContactName", map.get("RESPONSE_CONTACT_NAME"));
			dataMap.put("responseContactPhone", map.get("RESPONSE_CONTACT_PHONE"));
//			dataMap.put("navImageName", map.get("NAV_IMAGE_NAME"));
			// 结果返回
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getWorkOrderDocumentList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			if(id != null){
				List<DocumentIndexVMEntity> list = documentService.getDocumentInfo(PDMSConstants.SOURCE_TYPE_WORK_ORDER_ATTACHMENT,id.toString());
				for(DocumentIndexVMEntity documentIndexVMEntity : list){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("id", documentIndexVMEntity.getId());
					String title = "<a href='/pdms/workorder/workorder!downloadDocument.action?revisionId="
							+ documentIndexVMEntity.getDocumentRevisionId() + "'>" +documentIndexVMEntity.getDocumentName() + "</a>";
					dataMap.put("documentName", title);
					dataMap.put("createBy", documentIndexVMEntity.getCreateBy());
					dataMap.put("createName", documentIndexVMEntity.getCreateByName());
					dataMap.put("createDate", ObjectConverter.convert2String(documentIndexVMEntity.getCreateDate()));
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data,1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void uploadDocument() {
		try{
			documentService.saveGTDocumentIndex(null, PDMSConstants.SOURCE_TYPE_WORK_ORDER_ATTACHMENT, id.toString(), null, uploadFileName, null, upload,null);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void deleteDocument() {
		try{
			documentService.deleteGTDocumentIndex(documentIndexId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void downloadDocument() {
		try {
			//DMSDocumentRevision revision =
			//		DmsAPIFactory.getDocumentAPI().getRevision(Long.parseLong(revisionId));
			// 文件信息取得
			DMSDocumentRevision revision = documentService.getDocument(revisionId);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-download");
			// 取得输出流
			OutputStream os = response.getOutputStream();
			// 清空输出流
			response.reset();
			//String filename = URLEncoder.encode(revision.getDocument().getName(), "UTF-8");
			String filename = URLEncoder.encode(revision.getDocument().getName(), "UTF-8");
			// 设定输出文件头
			response.setHeader("Content-disposition","attachment; filename="+ filename);
			DmsAPIFactory.getDocumentAPI().getRevisionContent(revisionId, os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void importExcel() {
		try{
			workOrderService.importWorkOrder(upload);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void downloadTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/工作联系单.xls");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			String filename = java.net.URLEncoder.encode("工作联系单.xls", "UTF-8");
			response.setHeader("Content-disposition","attachment; filename="+filename);// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			try {
				int l = -1;
				byte[] tmp = new byte[1024];
				while ((l = is.read(tmp)) != -1) {
					os.write(tmp, 0, l);
					// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
				}
				os.flush();
				os.close();
			} finally {
				// 关闭低层流。
				is.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if(StringUtils.isNotEmpty(workOrderEntity.getReturnComment())){
			String commont = workOrderEntity.getOwnerCommont()+":"+workOrderEntity.getReturnComment();
			workOrderEntity.setOwnerCommont(commont);
		}
		Method m = this.getClass().getDeclaredMethod(method);
		m.invoke(this);
		return null;
	}

	@Override
	public String delete() throws Exception {
		String operator = SessionData.getLoginUserId();
		pkgPmWorkOrderDBProcedureServcie.deleteWorkOrder(id, operator , "");
		this.writeSuccessResult(null);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		workOrderEntity = new WorkOrderEntity();
	}
}
