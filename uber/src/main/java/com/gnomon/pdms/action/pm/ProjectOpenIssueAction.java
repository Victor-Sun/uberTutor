package com.gnomon.pdms.action.pm;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.DocumentIndexVMEntity;
import com.gnomon.pdms.entity.ImsLastAccessLogEntity;
import com.gnomon.pdms.entity.IssueProgressEntity;
import com.gnomon.pdms.entity.VIssueSourceEntity;
import com.gnomon.pdms.procedure.PkgPmPostDBProcedureServcie;
import com.gnomon.pdms.service.DocumentService;
import com.gnomon.pdms.service.LastAccessLogService;
import com.gnomon.pdms.service.ObsUserService;
import com.gnomon.pdms.service.PmPostService;
import com.gnomon.pdms.service.ProjectOpenIssueService;
import com.gnomon.pdms.service.ProjectOrganizationService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/pm")
public class ProjectOpenIssueAction extends PDMSCrudActionSupport<VIssueSourceEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private VIssueSourceEntity vissueSourceEntity;

	@Autowired
	private ProjectOpenIssueService projectOpenIssueService;
	
	@Autowired
	private LastAccessLogService lastAccessLogService;
	
	@Autowired
	private PmPostService pmPostService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private PkgPmPostDBProcedureServcie pkgPmPostDBProcedureServcie;
	
	@Autowired
	private ProjectOrganizationService projectOrganizationService;
	
	@Autowired
	private ObsUserService obsUserService;
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	@Override
	public VIssueSourceEntity getModel() {
		return vissueSourceEntity;
	}

	private Long id;
	
	private String documentIndexId;
	private String programId;
	
	private String programVehicleId;
	
	private String obsId;
	
	private String respUserId;
	
	private String issueSourceId;
	
	private String issueTypeId;
	
	private String dueDate;
	
	private String issueDescription;
	
	private String issueCause;
	
	private String issueResolution;
	
	private String action;
	
	private String title;
	private String content;
	
	private Long parentPostId;
	
	private String query;
	
	private String fromDate;
	
	private String toDate;
	
	private String userName;
	
	private String programName;
	
	private File upload;
	
	private String uploadFileName;
	
	private String modelData;
	
	public String getQuery() {
		return query;
	}

	public String getModelData() {
		return modelData;
	}

	public Long getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
	}

	public void setModelData(String modelData) {
		this.modelData = modelData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDocumentIndexId() {
		return documentIndexId;
	}

	public void setDocumentIndexId(String documentIndexId) {
		this.documentIndexId = documentIndexId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String remark;
	
	private String progressStatus;

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getIssueResolution() {
		return issueResolution;
	}

	public void setIssueResolution(String issueResolution) {
		this.issueResolution = issueResolution;
	}

	public String getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}

	public String getTitle() {
		return title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getIssueCause() {
		return issueCause;
	}

	public void setIssueCause(String issueCause) {
		this.issueCause = issueCause;
	}

	public String getRespUserId() {
		return respUserId;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setRespUserId(String respUserId) {
		this.respUserId = respUserId;
	}

	public String getIssueSourceId() {
		return issueSourceId;
	}

	public void setIssueSourceId(String issueSourceId) {
		this.issueSourceId = issueSourceId;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public String getProgramId() {
		return programId;
	}

	private Long issueId;
	
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	private String raiseBy;
	public void setRaiseBy(String raiseBy) {
		this.raiseBy = raiseBy;
	}
	
	private String raiseDate;
	public void setRaiseDate(String raiseDate) {
		this.raiseDate = raiseDate;
	}

	private String createBy;
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	private String issuePriorityCode;
	private String issueSourceDescription;
	public void setIssuePriorityCode(String issuePriorityCode) {
		this.issuePriorityCode = issuePriorityCode;
	}

	public String getIssueSourceDescription() {
		return issueSourceDescription;
	}

	public void setIssueSourceDescription(String issueSourceDescription) {
		this.issueSourceDescription = issueSourceDescription;
	}

	/**
	 * 【项目管理-项目列表】一OpenIssue览数据取得
	 */
	public void getProjectOpenIssue() {
		try {
			String loginUser = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =
					this.projectOpenIssueService.getOpenIssueListByProgramId(
							programId,this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = setIssueMap(map);
				// OpenIssue知会成员判断
				boolean isIssueMember = false;
				List<Map<String, Object>>  memberList =
						projectOpenIssueService.getIssueMemberList(
								new Long(PDMSCommon.nvl(map.get("ISSUE_ID"), "0")));
				for(Map<String, Object> member : memberList){
					if (loginUser.equals(member.get("USER_ID"))) {
						isIssueMember = true;
						break;
					}
				}
				dataMap.put("isIssueMember", isIssueMember);
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getOpenIssue() {
		try {
			String loginUser = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.projectOpenIssueService.getOpenIssueList(
							searchModel, this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = setIssueMap(map);
//				// OpenIssue知会成员判断
//				boolean isIssueMember = false;
//				List<Map<String, Object>>  memberList =
//						projectOpenIssueService.getIssueMemberList(
//								new Long(PDMSCommon.nvl(map.get("ISSUE_ID"), "0")));
//				for(Map<String, Object> member : memberList){
//					if (loginUser.equals(member.get("USER_ID"))) {
//						isIssueMember = true;
//						break;
//					}
//				}
//				dataMap.put("isIssueMember", isIssueMember);
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getLimitCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 部门空间-项目OpenIssue列表取得
	 */
	public void getDeptOpenIssue() {
		try {
			String loginUser = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> pageResult =
					this.projectOpenIssueService.getDeptOpenIssueList(
							searchModel, this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			// 数据整理
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = setIssueMap(map);
				// OpenIssue知会成员判断
				boolean isIssueMember = false;
				List<Map<String, Object>>  memberList =
						projectOpenIssueService.getIssueMemberList(
								new Long(PDMSCommon.nvl(map.get("ISSUE_ID"), "0")));
				for(Map<String, Object> member : memberList){
					if (loginUser.equals(member.get("USER_ID"))) {
						isIssueMember = true;
						break;
					}
				}
				dataMap.put("isIssueMember", isIssueMember);
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getLimitCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getDraftOpenIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =	this.projectOpenIssueService.getDraftOpenIssueList(this.getPage(),this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = setIssueMap(map);
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getLimitCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getArchiveOpenIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =	this.projectOpenIssueService.getArchiveOpenIssueList(fromDate,toDate,userName,title,programName,this.getPage(),this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", map.get("KB_ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("issueUuid", map.get("ISSUE_UUID"));
				dataMap.put("status", map.get("STATUS_CODE"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				dataMap.put("issueResolution", map.get("ISSUE_RESOLUTION"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("updateDate", ObjectConverter.convert2String(map.get("UPDATE_DATE")));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Map<String, Object> setIssueMap(Map<String, Object> map) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// ID
		dataMap.put("id", map.get("ISSUE_ID"));
		dataMap.put("issueUuid", map.get("ISSUE_UUID"));
		// 标题
		dataMap.put("issueTitle", map.get("ISSUE_TITLE"));
		// 问题来源
		dataMap.put("issueSourceTitle", map.get("TITLE"));
		// 提出人
		dataMap.put("raiseByName", map.get("RAISE_BY_NAME"));
		// 提出日期
		if(map.get("RAISE_DATE") != null){
			dataMap.put("raiseDate", DateUtils.formate((Date)map.get("RAISE_DATE")));
		}
		// 任务
		dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
		// 原因
		dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
		// 责任组
		dataMap.put("respObsName", map.get("RESP_OBS_NAME"));
		// 责任人
		dataMap.put("respUserName", map.get("RESP_USER_NAME"));
		// 完成状态
		dataMap.put("statusCode", map.get("STATUS_CODE"));
		// 完成状态名称
		dataMap.put("statusCodeName", map.get("STATUS_CODE_NAME"));
		// 进度状态
		dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
		// 计划完成日期
		if(map.get("DUE_DATE") != null){
			dataMap.put("dueDate", DateUtils.formate((Date)map.get("DUE_DATE")));
		}
		
		// 实际完成日期
		if(map.get("ACTUAL_FINISH_DATE") != null){
			dataMap.put("actualFinishDate", DateUtils.formate((Date)map.get("ACTUAL_FINISH_DATE")));
		}
		// 偏离计划完成日期
//		if(map.get("DUE_DATE") != null && map.get("ACTUAL_FINISH_DATE") != null){
//			Date dueDate = (Date)map.get("DUE_DATE");
//			Date finishDate = (Date)map.get("ACTUAL_FINISH_DATE");
//			Integer departedDays = DateUtils.daysBetween(dueDate, finishDate);
//			dataMap.put("departedDays", departedDays);
//		}
		dataMap.put("daysDelayed", map.get("DAYS_DELAYED"));
		//车型状态
		dataMap.put("vehicleStatusCode", map.get("VEHICLE_STATUS_CODE"));
		
		//当前任务负责人
		dataMap.put("taskOwner", map.get("TASK_OWNER"));
		// 优先级
		dataMap.put("issuePriorityName", map.get("ISSUE_PRIORITY_NAME"));
		// 所属项目
		dataMap.put("programCode", map.get("PROGRAM_CODE"));
		// 所属车型
		dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
		dataMap.put("issueSourceDescription", map.get("ISSUE_SOURCE_DESCRIPTION"));
		String userId = SessionData.getLoginUserId();
		dataMap.put("isNew", pmPostService.hasNewNotify(map.get("ISSUE_UUID").toString(), "PM-ISSUE", userId,ImsLastAccessLogEntity.OBJECT_TYPE_OPEN_ISSUE ));
		return dataMap;
	}
	
	public void getProjectVehicleOpenIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult =	this.projectOpenIssueService.getOpenIssueList(programVehicleId,this.getPage(),this.getLimit(),this.getFilter(),this.getSort());
			for(Map<String, Object> map : pageResult.getItems()){
				Map<String, Object> dataMap = setIssueMap(map);
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 【项目管理-项目列表】一OpenIssue览详细form数据取得
	 */
	public void getProjectOpenIssueForm() {
		try {
			JsonResult result = new JsonResult();
			VIssueSourceEntity  entity =
					this.projectOpenIssueService.getProjectOpenIssueFormService(issueId);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 问题来源
				dataMap.put("issueSource", entity.getIssueSourceName());
				// 提出人
				dataMap.put("proposedby", entity.getProposedBy());
				// 提出日期
				dataMap.put("proposedDate", DateUtils.change(entity.getProposedDate()));
				// 任务
				dataMap.put("task", entity.getIssueDescription());
				// 原因
				dataMap.put("reason", entity.getReason());
				// 责任组
				dataMap.put("respDept", entity.getRespObsId());
				// 责任人
				dataMap.put("respPerson", entity.getRespUserId());
				// 完成状态
				dataMap.put("status", entity.getIssueCompleteStatusCode());
				// 计划完成日期
				dataMap.put("dueDate", DateUtils.change(entity.getDueDate()));
				// 实际完成日期
				dataMap.put("finishDate", DateUtils.change(entity.getActualFinishDate()));
				// 偏离计划完成日期
				dataMap.put("departedDays", entity.getDepartedDays());
				lastAccessLogService.updateAccessLog(issueId.toString(),ImsLastAccessLogEntity.OBJECT_TYPE_OPEN_ISSUE);

				// 结果返回
				result.buildSuccessResult(dataMap);
				Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 【项目管理-项目列表】一OpenIssue览详细grid数据取得
	 */
	public void getProjectOpenIssueGrid() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<IssueProgressEntity> list =
					this.projectOpenIssueService.getProjectOpenIssueGridService(issueId);
			for(IssueProgressEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// 工作进展描述
				dataMap.put("progressDesc", entity.getDescription());
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
	
	public void getIssueSourceList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectOpenIssueService.getIssueSourceList();
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("TITLE"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getVehicleList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = projectOpenIssueService.getVehicleList(programId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("VEHICLE_NAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getVehicleList4RaiseIssue(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list =
				this.projectOpenIssueService.getVehicleList4RaiseIssue(programId);
		for(Map<String, Object> map:list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("VEHICLE_NAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getProgramList4RaiseIssue(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list =
				this.projectOpenIssueService.getVehicleList4RaiseIssue(null);
		List<String> programList = new ArrayList<String>();
		for(Map<String, Object> map:list){
			String programId = PDMSCommon.nvl(map.get("PROGRAM_ID"));
			if (programList.indexOf(programId) >= 0) {
				continue;
			}
			Map<String,Object> dataMap = new HashMap<String,Object>();
			// 项目ID
			dataMap.put("id", programId);
			// 项目代号
			dataMap.put("code", map.get("PROGRAM_CODE"));
			// 项目名称
			dataMap.put("programName", map.get("PROGRAM_NAME"));
			programList.add(programId);
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getResponsibleObsList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list =
				this.projectOrganizationService.getObsPlanList(programVehicleId);
		for (Map<String, Object> map : list) {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("ID"));
			dataMap.put("name", map.get("OBS_NAME"));
			data.add(dataMap);
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void getResponsibleUser(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = this.obsUserService.getObsUserList(
				null, obsId, true, null);
		List<String> userList = new ArrayList<String>();
		for(Map<String, Object> map:list){
			if (userList.indexOf(PDMSCommon.nvl(map.get("USER_ID"))) >= 0) {
				continue;
			}
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", map.get("USER_ID"));
			dataMap.put("name", map.get("USER_NAME"));
			data.add(dataMap);
			userList.add(PDMSCommon.nvl(map.get("USER_ID")));
		}
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	
	public void saveIssue(){
		//String loginUser = SessionData.getLoginUserId();
		try {
			String returnComments = "";
			if("RETURN".equals(action)){
				returnComments = remark;
			}
			Long issueId = projectOpenIssueService.saveIssue(id, createBy, programVehicleId,
					issueSourceId, issueTypeId, title, issueDescription, issueCause, dueDate, raiseBy,
					raiseDate, obsId, respUserId, issuePriorityCode,issueSourceDescription, action, remark, returnComments);
			// 通知
			this.sysNoticeService.openIssueNotify(
					PDMSConstants.NOTICE_TYPE_PM_ISSUE, issueId);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void saveArchiveIssue(){
		String raiseBy = SessionData.getLoginUserId();
		try {
			projectOpenIssueService.saveArchiveIssue(id,title, issueDescription, issueCause, issueResolution, raiseBy);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getIssueStatistic(){
		try {
			Map<String, Object> map = projectOpenIssueService.getIssueStatisticMap(programId);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("pending", map.get("PENDING"));
			dataMap.put("closed", map.get("CLOSED"));
			dataMap.put("total", map.get("TOTAL"));
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getIssueStatisticByVehicle(){
		try {
			Map<String, Object> map = projectOpenIssueService.getIssueStatisticMapByVehicle(programVehicleId);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("pending", map.get("PENDING"));
			dataMap.put("closed", map.get("CLOSED"));
			dataMap.put("total", map.get("TOTAL"));
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void reportIssueProgress(){
		String operatorId = SessionData.getLoginUserId();
		try {
			Long issueId = projectOpenIssueService.reportIssueProgress(id,operatorId, remark);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteOpenIssue(){
		String operatorId = SessionData.getLoginUserId();
		try {
			Long issueId = projectOpenIssueService.deleteOpenIssue(id,operatorId, issueDescription);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("issueId", issueId);
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getProjectOpenIssueLine() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =	this.projectOpenIssueService.getProjectOpenIssueLine(programId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("month", map.get("TRAN_MONTH"));
				dataMap.put("data1", map.get("RAISED"));
				dataMap.put("data2", map.get("CLOSED"));
				if(map.get("RAISED") != null && map.get("CLOSED") != null){
					int open = ((BigDecimal)map.get("RAISED")).intValue() - ((BigDecimal)map.get("CLOSED")).intValue();
					dataMap.put("data3", open);
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
	
	public void getProjectOpenIssueBar() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =	this.projectOpenIssueService.getProjectOpenIssueBar(programId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("dept", map.get("FUNCTION_NAME"));
				dataMap.put("data1", map.get("R"));
				dataMap.put("data2", map.get("Y"));
				dataMap.put("data3", map.get("W"));
				dataMap.put("data4", map.get("G"));
				dataMap.put("other", map.get("TOTAL"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProjectVehicleOpenIssueLine() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =	this.projectOpenIssueService.getProjectVehicleOpenIssueLine(programVehicleId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("month", map.get("TRAN_MONTH"));
				dataMap.put("data1", map.get("RAISED"));
				dataMap.put("data2", map.get("CLOSED"));
				if(map.get("RAISED") != null && map.get("CLOSED") != null){
					int open = ((BigDecimal)map.get("RAISED")).intValue() - ((BigDecimal)map.get("CLOSED")).intValue();
					dataMap.put("data3", open);
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
	
	public void getProjectOpenIssuePie() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<String> color = new ArrayList<String>();
			List<Map<String, Object>> list =	this.projectOpenIssueService.getProjectOpenIssuePie(programId);
			
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for(Map<String, Object> map : list){
				if(map.get("PROGRESS_STATUS").equals("RED")){
					tempMap.put("R", map.get("PROGRESS_STATUS_PERCENT"));
				}
				if(map.get("PROGRESS_STATUS").equals("YELLOW")){
					tempMap.put("Y", map.get("PROGRESS_STATUS_PERCENT"));
				}
				if(map.get("PROGRESS_STATUS").equals("GRAY")){
					tempMap.put("W", map.get("PROGRESS_STATUS_PERCENT"));
				}
				if(map.get("PROGRESS_STATUS").equals("GREEN")){
					tempMap.put("G", map.get("PROGRESS_STATUS_PERCENT"));
				}
			}
			if(PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("R"), "0")) != 0){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("status", "大风险");
				dataMap.put("data1", PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("R"), "0")));
				data.add(dataMap);
				color.add("red");
			}
			if(PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("Y"), "0")) != 0){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("status", "小风险");
				dataMap.put("data1", PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("Y"), "0")));
				data.add(dataMap);
				color.add("yellow");
			}
			if(PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("W"), "0")) != 0){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("status", "无风险");
				dataMap.put("data1", PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("W"), "0")));
				data.add(dataMap);
				color.add("gray");
			}
			if(PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("G"), "0")) != 0){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("status", "已完成");
				dataMap.put("data1", PDMSCommon.toInt(PDMSCommon.nvl(tempMap.get("G"), "0")));
				data.add(dataMap);
				color.add("green");
			}
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("data", data);
			returnMap.put("color", color);
			returnMap.put("success", true);
			// 结果返回
//			result.buildSuccessResultForList(returnMap, 1);
			Struts2Utils.renderJson(returnMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getIssueForm() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object>  map = projectOpenIssueService.getOpenIssueForm(Long.valueOf(issueId));
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", map.get("ISSUE_ID"));
			dataMap.put("issueSourceId", map.get("ISSUE_SOURCE_ID"));
			dataMap.put("respUserId", map.get("RESP_USER_ID"));
			dataMap.put("respUserName", map.get("RESP_USER_NAME"));
			dataMap.put("obsId", map.get("RESP_OBS_ID"));
			dataMap.put("obsName", map.get("OBS_NAME"));
			dataMap.put("programId", map.get("PROGRAM_ID"));
			dataMap.put("programCode", map.get("PROGRAM_CODE"));
			dataMap.put("programVehicleId", map.get("PROGRAM_VEHICLE_ID"));
			dataMap.put("vehicleCode", map.get("VEHICLE_CODE"));
			dataMap.put("title", map.get("ISSUE_TITLE"));
			if(map.get("DUE_DATE") != null){
				dataMap.put("dueDate", DateUtils.formate((Date)map.get("DUE_DATE")));
			}
			
			dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
			dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
			dataMap.put("status", map.get("STATUS_CODE"));
			dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
			dataMap.put("raiseByName", map.get("RAISE_BY_NAME"));
			// 实际完成日期
			dataMap.put("actualFinishDate", DateUtils.formate((Date)map.get("ACTUAL_FINISH_DATE")));
			// 偏离计划完成日期
			if (map.get("DUE_DATE") != null && map.get("ACTUAL_FINISH_DATE") != null) {
				Date dueDate = (Date)map.get("DUE_DATE");
				Date finishDate = (Date)map.get("ACTUAL_FINISH_DATE");
				Integer departedDays = DateUtils.daysBetween(dueDate, finishDate);
				dataMap.put("departedDays", departedDays);
			}
			// 提出人
			dataMap.put("raiseBy", map.get("RAISE_BY"));
			dataMap.put("raiseByName", map.get("RAISE_BY_NAME"));
			// 提出日期
			if (map.get("RAISE_DATE") != null) {
				dataMap.put("raiseDate", DateUtils.formate((Date)map.get("RAISE_DATE")));
			}
			// 录入人
			dataMap.put("createBy", map.get("CREATE_BY"));
			dataMap.put("createByName", map.get("CREATE_BY_NAME"));
			// 优先级
			dataMap.put("issuePriorityCode", map.get("ISSUE_PRIORITY_CODE"));
			dataMap.put("issueSourceDescription", map.get("ISSUE_SOURCE_DESCRIPTION"));
			// 问题类型
			dataMap.put("issueTypeId", map.get("ISSUE_TYPE_ID"));
			// 访问次数更新
			this.lastAccessLogService.updateAccessLog(map.get("ISSUE_UUID").toString(),ImsLastAccessLogEntity.OBJECT_TYPE_OPEN_ISSUE);
			// 结果返回
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProjectOpenIssueNotes() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =	this.projectOpenIssueService.getProjectOpenIssueNotes(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
//				dataMap.put("id", map.get("ISSUE_ID"));
				if(map.get("CREATE_DATE") != null){
					dataMap.put("date", DateUtils.formate((Date)map.get("CREATE_DATE")));
				}
				dataMap.put("paragraph", map.get("DESCRIPTION"));
				dataMap.put("title", map.get("DESCRIPTION"));
				dataMap.put("author", map.get("USERNAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getIssueKB() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  list = projectOpenIssueService.getIssueKB(query);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				dataMap.put("issueResolution", map.get("ISSUE_RESOLUTION"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("updateDate", DateUtils.formate((Date)map.get("UPDATE_DATE")));
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getRaiseIssueDocument() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			if(issueId != null){
				List<DocumentIndexVMEntity> list = documentService.getDocumentInfo(PDMSConstants.SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT,issueId.toString());
				for(DocumentIndexVMEntity documentIndexVMEntity : list){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("id", documentIndexVMEntity.getId());
					dataMap.put("documentRevisionId", documentIndexVMEntity.getDocumentRevisionId());
					dataMap.put("documentName", documentIndexVMEntity.getDocumentName());
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
			Map<String, Object> map = projectOpenIssueService.getOpenIssueForm(issueId);
			documentService.saveGTDocumentIndex(map.get("PROGRAM_ID").toString(), PDMSConstants.SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT, issueId.toString(), null, uploadFileName, null, upload,null);
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
	
	public void getIssueMemberList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  list = projectOpenIssueService.getIssueMemberList(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addMember() {
		try{
			List<Map<String, String>> memberIdlist = this.convertJson2List(this.modelData);
			projectOpenIssueService.addMembers(issueId, memberIdlist);
			// 通知
			this.sysNoticeService.cooperationNotify(PDMSConstants.NOTICE_TYPE_PM_ISSUE_CT,
					PDMSCommon.nvl(issueId), memberIdlist);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void getForumList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  list = projectOpenIssueService.getForumList(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("content", map.get("CONTENT"));	
				dataMap.put("parentId", map.get("PARENT_ID"));
				dataMap.put("postLevel", map.get("POST_LEVEL"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void newPost() {
		try{
			String userId = SessionData.getLoginUserId();
			pkgPmPostDBProcedureServcie.newPost(userId , PkgPmPostDBProcedureServcie.POST_SOURCE_TYPE_PM_ISSUE, getIssueUUID(issueId), content, content);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	private String getIssueUUID(Long issueId) {
		Map<String, Object> map = projectOpenIssueService.getOpenIssue(issueId);
		return map.get("UUID").toString();
	}
	
	/**
	 * 问题类型查询
	 */
	public void getIssueTypeList(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = this.projectOpenIssueService.getIssueTypeList();
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("name", map.get("TITLE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 审批记录取得
	 */
	public void getProcessRecord() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.projectOpenIssueService.getProcessRecord(issueId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				String completeDate = DateUtils.formate((Date)map.get("COMPLETE_DATE"),
						DateUtils.FORMAT_PATTEN_DATETIME);
				dataMap.put("id", map.get("ID"));
				dataMap.put("author", map.get("TASK_OWNER_NAME"));
				dataMap.put("date", completeDate.substring(0, 10));
				dataMap.put("time", completeDate.substring(11));
				// 提交者动作
				dataMap.put("stepName", map.get("STEP_NAME"));
				dataMap.put("paragraph", map.get("ACTION_NAME"));
				dataMap.put("stepId", map.get("STEP_ID"));
				dataMap.put("roleId", map.get("ROLE_ID"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
}