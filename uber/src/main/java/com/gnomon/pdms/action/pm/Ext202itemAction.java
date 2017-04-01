package com.gnomon.pdms.action.pm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.Ext103ListEntity;
import com.gnomon.pdms.entity.ExtendedProjectColumnDefEntity;
import com.gnomon.pdms.entity.ExtendedProjectVMEntity;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.Ext202ItemService;
import com.gnomon.pdms.service.ProjectExtensionPlanService;

@Namespace("/pm")
public class Ext202itemAction extends PDMSCrudActionSupport<Ext103ListEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private ProjectExtensionPlanService projectExtensionPlanService;
	
	@Autowired
	private Ext202ItemService ext202ItemService;
	
	@Autowired
	private DocumentService documentService;

	private String extProjectId;
	
	private Long stageId;
	
	private String obsId;
	
	private File upload;//导入文件
	
	private Long taskId;
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long revisionId;
	
	private String isCompleted;
	private String remark;
	
	public File getUpload() {
		return upload;
	}

	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	private String uploadFileName;

	public void setExtProjectId(String extProjectId) {
		this.extProjectId = extProjectId;
	}
	
	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public String getExtProjectId() {
		return extProjectId;
	}

	/**
	 * 专项数据取得（清单类）
	 */
	public void getProjectExtensionPlanList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Ext103ListEntity> list = this.projectExtensionPlanService
					.getProjectExtensionPlanList(extProjectId);
			for(Ext103ListEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("c01", entity.getC01());
				dataMap.put("c02", entity.getC02());
				dataMap.put("c03", entity.getC03());
				dataMap.put("c04", entity.getC04());
				dataMap.put("c05", entity.getC05());
				dataMap.put("c06", entity.getC06());
				dataMap.put("c07", entity.getC07());
				dataMap.put("c08", entity.getC08());
				dataMap.put("c09", entity.getC09());
				dataMap.put("c10", entity.getC10());
				dataMap.put("c11", entity.getC11());
				dataMap.put("c12", entity.getC12());
				dataMap.put("c13", entity.getC13());
				dataMap.put("c14", entity.getC14());
				dataMap.put("c15", entity.getC15());
				dataMap.put("c16", entity.getC16());
				dataMap.put("c17", entity.getC17());
				dataMap.put("c18", entity.getC18());
				dataMap.put("c19", entity.getC19());
				dataMap.put("c20", entity.getC20());
				dataMap.put("c21", entity.getC21());
				dataMap.put("c22", entity.getC22());
				dataMap.put("c23", entity.getC23());
				dataMap.put("c24", entity.getC24());
				dataMap.put("c25", entity.getC25());
				dataMap.put("c26", entity.getC26());
				dataMap.put("c27", entity.getC27());
				dataMap.put("c28", entity.getC28());
				dataMap.put("c29", entity.getC29());
				dataMap.put("c30", entity.getC30());
				dataMap.put("c31", entity.getC31());
				dataMap.put("c32", entity.getC32());
				dataMap.put("c33", entity.getC33());
				dataMap.put("c34", entity.getC34());
				dataMap.put("c35", entity.getC35());
				dataMap.put("c36", entity.getC36());
				dataMap.put("c37", entity.getC37());
				dataMap.put("c38", entity.getC38());
				dataMap.put("c39", entity.getC39());
				dataMap.put("c40", entity.getC40());
				dataMap.put("c41", entity.getC41());
				dataMap.put("c42", entity.getC42());
				dataMap.put("c43", entity.getC43());
				dataMap.put("c44", entity.getC44());
				dataMap.put("c45", entity.getC45());
				dataMap.put("c46", entity.getC46());
				dataMap.put("c47", entity.getC47());
				dataMap.put("c48", entity.getC48());
				dataMap.put("c49", entity.getC49());
				dataMap.put("c50", entity.getC50());
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 专项基本信息取得
	 */
	public void getExtensionPlanInfo() {
		try {
			JsonResult result = new JsonResult();
			ExtendedProjectVMEntity entity = this.projectExtensionPlanService.
					getProjectExtensionPlanInfo(extProjectId);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", entity.getId());
			data.put("name", entity.getName());
			data.put("code", entity.getCode());
			data.put("extProjectTypeCode", entity.getExtProjectTypeCode());
			data.put("extProjectTypeName", entity.getExtProjectTypeName());
			data.put("ownerName", entity.getOwnerName());
			data.put("taskName", entity.getTaskName());
			data.put("memo", entity.getMemo());
			data.put("projectProgressStatus", entity.getProjectProgressStatus());
			data.put("projectProgressStatusName", entity.getProjectProgressStatusName());

			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 列属性定义信息取得（清单类）
	 */
	public void getExtensionPlanColumnDefList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<ExtendedProjectColumnDefEntity> list = this.projectExtensionPlanService
					.getColumnDefList(extProjectId);
			for(ExtendedProjectColumnDefEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("columnIdx", entity.getColumnIdx());
				dataMap.put("columnTitle", entity.getColumnTitle());
				dataMap.put("columnTypeCode", entity.getColumnTypeCode());
				dataMap.put("columnWidth", entity.getColumnWidth());
				dataMap.put("isDisplayOnList",
						"Y".equals(entity.getIsDisplayOnList()));
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
	public Ext103ListEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getExtPartList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectExtensionPlanService.getExtPartList(Long.valueOf(extProjectId));
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ITEM_ID"));
			dataMap.put("stageId", map.get("STAGE_ID"));
			dataMap.put("itemNo", map.get("ITEM_NO"));
			dataMap.put("itemName", map.get("ITEM_NAME"));
			dataMap.put("progress", map.get("PROGRESS"));
			dataMap.put("userName", map.get("USERNAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getExtPartProgressList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectExtensionPlanService.getExtPartProgressList(Long.valueOf(extProjectId));
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
//			 fields: ['id','department','stageId','planStartDate','planEndDate','ratio','total','completed','progress'],
			dataMap.put("id", map.get("ITEM_ID"));
			dataMap.put("stageId", map.get("STAGE_ID"));
			dataMap.put("department", map.get("OBS_NAME"));
			dataMap.put("ratio", map.get("PROGRESS"));
			dataMap.put("total", map.get("TOTAL_COUNT"));
			dataMap.put("completed", map.get("COMPLETED"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getExtStagePartList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectExtensionPlanService.getExtStagePartList(Long.valueOf(stageId),obsId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("STAGE_ITEM_ID"));
			dataMap.put("stageId", map.get("STAGE_ID"));
			dataMap.put("itemNo", map.get("ITEM_NO"));
			dataMap.put("itemName", map.get("ITEM_NAME"));
			dataMap.put("progress", map.get("PROGRESS"));
			dataMap.put("userName", map.get("USERNAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getExt202List(){
		try {
			Map<String,Object> resultMap = new HashMap<String,Object>();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> columns = new ArrayList<Map<String,Object>>();
			List<String> fields = new ArrayList<String>();
			List<Map<String, Object>> list = ext202ItemService.getExt202ModelColumns(Long.valueOf(extProjectId));
			for(Map<String, Object> map:list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("text", map.get("DISPLAY_NAME"));
				dataMap.put("dataIndex", map.get("COLUMN_NAME"));
				columns.add(dataMap);
			}
			for(Map<String, Object> map:list){
				fields.add(map.get("COLUMN_NAME").toString());
			}
//		ObjectConverter.convert2String(value)
			List<Map<String, Object>> dataList = ext202ItemService.getExt202TableData(Long.valueOf(extProjectId));
			for(Map<String, Object> map:dataList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				Set keySet = map.keySet();//返回键的集合 
				Iterator it = keySet.iterator(); 
				while(it.hasNext()){ 
				   Object key = it.next(); 
//			   System.out.println(key+" : "+map.get(key)); //根据键来取对应的值 
				   dataMap.put(key.toString(), ObjectConverter.convert2String(map.get(key)));
			    } 
//			dataMap.put(map., value)
				data.add(dataMap);
			}
			resultMap.put("data", data);
			resultMap.put("fields", fields);
			resultMap.put("columns", columns);
			resultMap.put("success", true);
			Struts2Utils.renderJson(resultMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getExt202FormFields(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = ext202ItemService.getExt202ModelColumns(Long.valueOf(extProjectId));
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("displayName", map.get("DISPLAY_NAME"));
			dataMap.put("fieldName", map.get("COLUMN_NAME"));
			dataMap.put("fieldType", getFieldType(map.get("DATA_TYPE")));
			fields.add(dataMap);
		}
		resultMap.put("fields", fields);
		resultMap.put("success", true);
		Struts2Utils.renderJson(resultMap);
	}
	
	public String getFieldType(Object obj){
		if(obj.toString().startsWith("VARCHAR")){
			return "textfield";
		}
		if(obj.toString().startsWith("NUMBER")){
			return "numberfield";
		}
		if(obj.toString().startsWith("DATE")){
			return "datefield";
		}
		return "";
	}
	
	public void importFromExcel() {
		try{
			ext202ItemService.importFromExcel(Long.valueOf(extProjectId),uploadFileName, new FileInputStream(upload));
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void releaseExt201StageItem() {
		try{
			projectExtensionPlanService.releaseExt201StageItem(Long.valueOf(extProjectId), stageId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void startExt201Task() {
		try{
			projectExtensionPlanService.startExt201Task(taskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void finishedExt201Task() {
		try{
			projectExtensionPlanService.finishedExt201Task(taskId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getExt202ModelList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext202ItemService.getExt202ModelList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("description", map.get("DESCRIPTION"));
			
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getChecklistDocuments(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = projectExtensionPlanService.getChecklistDocuments(Long.valueOf(taskId));
			for(Map<String, Object> map:list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				String revisionId = (String)map.get("DOCUMENT_REVISION_ID");
				DMSDocumentRevision revison = documentService.getDocument(Long .valueOf(revisionId));
				dataMap.put("id", revison.getId());
				String title = "<a href='/pdms/pm/project-extension-plan!downloadDocument.action?revisionId="
						+ revison.getId() + "'>" +revison.getDocument().getName() + "</a>";
				dataMap.put("title", title);
				dataMap.put("createBy", projectExtensionPlanService.getUserName(revison.getCreateUser()));
				if(revison.getCreateDate() != null){
					dataMap.put("createDate", DateUtils.formate(revison.getCreateDate()));
				}
				
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	public void getExt201TaskForm(){
		Map<String, Object> map = projectExtensionPlanService.getExt201TaskForm(taskId);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("programName", map.get("PROGRAM_NAME"));
		dataMap.put("applicationName", map.get("APPLICATION_NAME"));
		dataMap.put("itemName", map.get("ITEM_NAME"));
		dataMap.put("obsName", map.get("OBS_NAME"));
		dataMap.put("userName", map.get("USERNAME"));
		this.writeSuccessResult(dataMap);
	}
	
	public void updateExt201TaskStatus() {
		try{
			String CompleteFlag = "N";
			if("true".equals(isCompleted)){
				CompleteFlag = "Y";
			}
			projectExtensionPlanService.updateExt201TaskStatus(taskId, CompleteFlag, remark);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void uploadChecklistDocument() {
		try{
			projectExtensionPlanService.uploadChecklistDocument(id,taskId, uploadFileName, upload);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getChecklistNotes(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectExtensionPlanService.getExt201TaskCheckListNotes(taskId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("stageItemId", map.get("STAGE_ITEM_ID"));
			dataMap.put("stageId", map.get("STAGE_ID"));
			dataMap.put("itemId", map.get("ITEM_ID"));
			dataMap.put("title", map.get("TITLE"));
			dataMap.put("description", map.get("DESCRIPTION"));
			dataMap.put("seqNo", map.get("SEQ_NO"));
			dataMap.put("isComplete", map.get("IS_COMPLETE"));
			if(map.get("PLANNED_FINISH_DATE") != null){
				dataMap.put("plannedFinishDate", DateUtils.formate((Date)map.get("PLANNED_FINISH_DATE")));
			}
			if(map.get("ACTUAL_FINISH_DATE") != null){
				dataMap.put("actualFinishDate", DateUtils.formate((Date)map.get("ACTUAL_FINISH_DATE")));
			}
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getChecklistAllDocuments(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = projectExtensionPlanService.getChecklistAllDocuments(Long.valueOf(taskId));
			for(Map<String, Object> map:list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				String revisionId = (String)map.get("DOCUMENT_REVISION_ID");
				DMSDocumentRevision revison = documentService.getDocument(Long .valueOf(revisionId));
				dataMap.put("id", revison.getId());
				String title = "<a href='/pdms/pm/project-extension-plan!downloadDocument.action?revisionId="
						+ revison.getId() + "'>" +revison.getDocument().getName() + "</a>";
				dataMap.put("title", title);
				dataMap.put("createBy", projectExtensionPlanService.getUserName(revison.getCreateUser()));
				if(revison.getCreateDate() != null){
					dataMap.put("createDate", DateUtils.formate(revison.getCreateDate()));
				}
				
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		}catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
	
	public void downloadExtTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/partTemplate.xls");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			// filename = "attachment; filename=" + filename + ".xls";
			response.setHeader("Content-disposition","attachment; filename=partTemplate.xls");// 设定输出文件头
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
	
	public void exportExcel() throws Exception {    
		try {
			HttpServletResponse response = Struts2Utils.getResponse();
			HSSFWorkbook wb = ext202ItemService.export(Long.valueOf(extProjectId));
			response.setContentType("application/vnd.ms-excel");    
			response.setHeader("Content-disposition", "attachment;filename=modelTemplate.xls");    
			OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);    
			ouputStream.flush();    
			ouputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
   }    
}
