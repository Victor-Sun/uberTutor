package com.gnomon.pdms.action.pm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.Ext301ItemEntity;
import com.gnomon.pdms.entity.ExtendedProjectColumnDefEntity;
import com.gnomon.pdms.entity.ExtendedProjectVMEntity;
import com.gnomon.pdms.procedure.PkgExt301DBProcedureServcie;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.Ext202ItemService;
import com.gnomon.pdms.service.Ext301Service;
import com.gnomon.pdms.service.ProjectExtensionPlanService;

@Namespace("/pm")
public class Ext301Action extends PDMSCrudActionSupport<Ext301ItemEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private ProjectExtensionPlanService projectExtensionPlanService;
	
	@Autowired
	private Ext202ItemService ext202ItemService;
	
	@Autowired
	private Ext301Service ext301Service;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private PkgExt301DBProcedureServcie pkgExt301DBProcedureServcie;

	private String extProjectId;
	
	private String programVehicleId;
	
	private String status;
	
	private String obsName;
	
	private String code;
	
	private String action;
	
	private Long stageId;
	
	private String obsId;
	
	private File upload;//导入文件
	
	private Long taskId;
	
	private String plannedFinishDate;
	
	private String actualFinishDate;
	
	private String model;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
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

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private String uploadFileName;

	public void setExtProjectId(String extProjectId) {
		this.extProjectId = extProjectId;
	}
	
	public String getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(String plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public String getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(String actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
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
	public Ext301ItemEntity getModel() {
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
	
	public void getExt301List(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> fields = new ArrayList<String>();
		resultMap.put("data", ext301Service.getExt301ItemList(programVehicleId,status));
		resultMap.put("fields", fields);
		resultMap.put("columns", getExt301GridColumns(null));
		resultMap.put("success", true);
		Struts2Utils.renderJson(resultMap);
	}
	
	public List<Map<String, Object>> getExt301GridColumns(Long parentId){
		List<Map<String,Object>> columns = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> gridColumns = ext301Service.getExt301GridTitle(parentId);
		for(Map<String, Object> map:gridColumns){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("text", map.get("TEXT"));
			if(map.get("PARENT_ID") == null && ((BigDecimal)map.get("ID")).compareTo(new BigDecimal(9)) > 0){
				dataMap.put("columns", getExt301GridColumns(((BigDecimal)map.get("ID")).longValue()));
			}else{
				dataMap.put("dataIndex", map.get("DATA_INDEX"));
				if(((BigDecimal)map.get("ID")).compareTo(new BigDecimal(10)) < 0){
					dataMap.put("locked", true);
					dataMap.put("width", 100);
				}
				if("DATE".equals(map.get("DATA_TYPE"))){
					dataMap.put("format", "Y/m/d");
				}
			}
			columns.add(dataMap);
		}
		return columns;
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
			ext301Service.importFromExcel(Long.valueOf(extProjectId),programVehicleId,uploadFileName, new FileInputStream(upload));
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void initSchedule() {
		try{
			pkgExt301DBProcedureServcie.initSchedule(programVehicleId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	public void processSchedule() {
		try{
			String userId = SessionData.getLoginUserId();
			List<Integer> itemList = new ArrayList<Integer>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				itemList.add(Integer.parseInt(jsonArray.get(i).toString()));
			}
			pkgExt301DBProcedureServcie.processSchedule(programVehicleId, userId , itemList , action, null, null);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void processExcute() {
		try{
			String userId = SessionData.getLoginUserId();
			List<Integer> itemList = new ArrayList<Integer>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				itemList.add(Integer.parseInt(jsonArray.get(i).toString()));
			}
			pkgExt301DBProcedureServcie.processSchedule(programVehicleId, userId , itemList , action, null, null);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void updateTaskComplete() {
		try{
			List<Integer> taskList = new ArrayList<Integer>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				taskList.add(Integer.parseInt(jsonArray.get(i).toString()));
			}
			pkgExt301DBProcedureServcie.updateTaskComplete(taskList, remark);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void batchProcessSchedule() {
		try{
			List<Integer> itemList = new ArrayList<Integer>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				itemList.add(Integer.parseInt(jsonArray.get(i).toString()));
			}
			String userId = SessionData.getLoginUserId();
			pkgExt301DBProcedureServcie.batchProcessSchedule(programVehicleId, code, DateUtils.strToDate(plannedFinishDate), userId, itemList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void batchCompleteSchedule() {
		try{
			List<Integer> itemList = new ArrayList<Integer>(); 
			JSONArray jsonArray = JSONArray.fromObject(model);
			for (int i = 0; i < jsonArray.size(); i++) {
				itemList.add(Integer.parseInt(jsonArray.get(i).toString()));
			}
			pkgExt301DBProcedureServcie.batchCompleteSchedule(DateUtils.strToDate(plannedFinishDate),itemList);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void saveNodeInfo() {
		try{
   	 		ext301Service.saveNodeInfo(taskId,plannedFinishDate,actualFinishDate);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getPartSchedulePool(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		String userId = SessionData.getLoginUserId();
		List<Map<String, Object>> list = ext301Service.getPartSchedulePool(programVehicleId,userId );
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("partNo", map.get("ITEM_NO"));
			dataMap.put("partName", map.get("ITEM_NAME"));
			dataMap.put("kcrp", map.get("IS_KCRP"));
			dataMap.put("obsName", map.get("OBS_NAME"));
			dataMap.put("username", map.get("USERNAME"));
			dataMap.put("progress", map.get("PROGRESS"));
			
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getPartNodeItemList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext301Service.getPartNodeItemList(id);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("taskId", map.get("TASK_ID"));
			dataMap.put("title", map.get("TITLE"));
			dataMap.put("plannedFinishDate", ObjectConverter.convert2String(map.get("PLANNED_FINISH_DATE")));
			dataMap.put("actualFinishDate", ObjectConverter.convert2String(map.get("ACTUAL_FINISH_DATE")));
			dataMap.put("ownerName", map.get("OWNER_NAME"));
			
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getPartNodeList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext301Service.getPartExcuteHeader(programVehicleId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("CODE"));
			dataMap.put("name", map.get("TITLE"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	

	
	public void downloadExportTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/EXT301_ITEMLIST.xls");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			String filename = java.net.URLEncoder.encode("零部件管控表.xls", "UTF-8");
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
	
  
	public void getVehicleList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = ext301Service.getVehicleList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getPartCheckPoolList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> fields = new ArrayList<String>();
		String userId = SessionData.getLoginUserId();
		resultMap.put("data", ext301Service.getPartCheckPoolList(programVehicleId, userId,obsName ));
		resultMap.put("fields", fields);
		resultMap.put("columns", getExt301GridColumns(null));
		resultMap.put("success", true);
		Struts2Utils.renderJson(resultMap);
	}
	
	public void getPartConfirmPoolList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> fields = new ArrayList<String>();
		String userId = SessionData.getLoginUserId();
		resultMap.put("data", ext301Service.getPartConfirmPoolList(programVehicleId, userId,obsName ));
		resultMap.put("fields", fields);
		resultMap.put("columns", getExt301GridColumns(null));
		resultMap.put("success", true);
		Struts2Utils.renderJson(resultMap);
	}
	
	public void getPartExcuteHeader(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = ext301Service.getPartExcuteHeader(programVehicleId);
			String userId = SessionData.getLoginUserId();
			String currentCode = (String) pkgExt301DBProcedureServcie.getCurrentCheckpoint(programVehicleId, userId ).get("code");
			for(Map<String, Object> map:list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("code", map.get("CODE"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("currentCode",currentCode);
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Map<String, Object>> getPartExcuteGridColumns(Long parentId,String code){
		List<Map<String,Object>> columns = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> gridColumns = ext301Service.getExt301GridTitle(parentId);
		for(Map<String, Object> map:gridColumns){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("text", map.get("TEXT"));
			if(map.get("PARENT_ID") == null && ((BigDecimal)map.get("ID")).compareTo(new BigDecimal(9)) > 0){
				if(map.get("DATA_INDEX").equals(code)){
					dataMap.put("columns", this.getPartExcuteGridColumns(((BigDecimal)map.get("ID")).longValue(),code));
				}else{
					continue;
				}
			}else{
				dataMap.put("dataIndex", map.get("DATA_INDEX"));
				if(((BigDecimal)map.get("ID")).compareTo(new BigDecimal(10)) < 0){
//					dataMap.put("locked", true);
					dataMap.put("width", 100);
				}else{
					dataMap.put("xtype", "datecolumn");
					dataMap.put("format", "Y/m/d");
					Map<String,Object> editorMap = new HashMap<String,Object>();
					editorMap.put("xtype", "datefield");
					editorMap.put("format", "Y/m/d");
					dataMap.put("editor", editorMap);
				}
			}
			columns.add(dataMap);
		}
		return columns;
	}
	
	public void getPartExcutePoolList(){
		try {
			Map<String,Object> resultMap = new HashMap<String,Object>();
			String userId = SessionData.getLoginUserId();
			resultMap.put("data", ext301Service.getPartExcutePoolList(programVehicleId, userId,code ));
			resultMap.put("success", true);
			Struts2Utils.renderJson(resultMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void importPartList() {
		try{
			if(StringUtils.isEmpty(extProjectId)){
				extProjectId = ext301Service.getPartProjectId(programVehicleId).toString();
			}
			ext301Service.uploadPartControllReport(programVehicleId, "partControll.xls");
			ext301Service.importPartList(upload, Long.valueOf(extProjectId), programVehicleId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	

}
