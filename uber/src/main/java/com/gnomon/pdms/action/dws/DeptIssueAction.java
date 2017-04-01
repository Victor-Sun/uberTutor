package com.gnomon.pdms.action.dws;

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
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.service.DeptIssueService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/dws")
public class DeptIssueAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DeptIssueService deptIssueService;
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	private String action;
	public void setAction(String action) {
		this.action = action;
	}

	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String query;
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * 部门OpenIssue列表取得
	 */
	public void getDeptIssueList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.deptIssueService.getDeptIssueList(
							searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				data.add(this.getDataMap(map));
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 部门OpenIssue信息取得
	 */
	public void getDeptIssue() {
		try {
			JsonResult result = new JsonResult();
			// 查询
			Map<String, Object> issueMap = this.deptIssueService.getDeptIssue(issueId);
			Map<String,Object> data = this.getDataMap(issueMap);
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 草稿列表取得
	 */
	public void getDraftDeptIssueList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.deptIssueService.getDraftDeptIssueList(
							searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				data.add(this.getDataMap(map));
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 提交/保存问题
	 */
	public void saveDeptIssue() {
		try {
			Map<String, String> model = this.convertJson(this.model);
			Long issueId = this.deptIssueService.saveDeptIssue(action, model);
			// 通知
			this.sysNoticeService.openIssueNotify(
					PDMSConstants.NOTICE_TYPE_DEPT_ISSUE, issueId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存草稿
	 */
	public void saveDraftDeptIssue() {
		try {
			Map<String, String> model = this.convertJson(this.model);
			Long issueId = this.deptIssueService.saveDraftDeptIssue(model);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("issueId", issueId);
			// 返回UUID
			Map<String, Object> issueMap = this.deptIssueService.getDeptIssue(PDMSCommon.nvl(issueId));
			dataMap.put("issueUuid", issueMap.get("ISSUE_UUID"));
			this.writeSuccessResult(dataMap);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 归档问题查询
	 */
	public void getDeptIssueKB() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getDeptIssueKBList(query);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("issueTitle", map.get("TITLE"));
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				dataMap.put("issueResolution", map.get("ISSUE_RESOLUTION"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("updateDate", DateUtils.formate((Date)map.get("UPDATE_DATE")));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得问题来源
	 */
	public void getDeptIssueSourceList(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getDeptIssueSourceList();
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 问题来源
				dataMap.put("title", map.get("TITLE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得进度列表
	 */
	public void getDeptIssueNotes() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getDeptIssueNotesList(issueId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// TODO：暂时工程师图片
				dataMap.put("roleId", "PM-ENG");
				if(map.get("CREATE_DATE") != null){
					dataMap.put("date", DateUtils.formate((Date)map.get("CREATE_DATE")));
				}
				dataMap.put("paragraph", map.get("DESCRIPTION"));
				dataMap.put("title", map.get("DESCRIPTION"));
				dataMap.put("author", map.get("USERNAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 更新进度
	 */
	public void reportDeptIssueProgress() {
		try {
			Map<String, String> model = this.convertJson(this.model);
			this.deptIssueService.reportDeptIssueProgress(issueId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得责任人列表
	 */
	public void getRespUserList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getRespUserList(issueId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 用户姓名
				dataMap.put("name", map.get("USERNAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得提出人科室列表
	 */
	public void getSubDeptList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getSubDeptList(issueId);
			for (Map<String, Object> map : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// 用户姓名
				dataMap.put("name", map.get("NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 讨论组成员列表取得
	 */
	public void getDeptIssueMemberList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询
			List<Map<String, Object>> list =
					this.deptIssueService.getDeptIssueMemberList(issueId);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("createDate", DateUtils.change((Date)map.get("CREATE_DATE")));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 增加讨论组成员
	 */
	public void addDeptIssueMember() {
		try {
			List<Map<String, String>> modelList = this.convertJson2List(this.model);
			this.deptIssueService.addDeptIssueMember(issueId, modelList);
			// 通知
			this.sysNoticeService.cooperationNotify(PDMSConstants.NOTICE_TYPE_DEPT_ISSUE_CT,
					issueId, modelList);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 归档问题列表取得
	 */
	public void getArchiveDeptIssueList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.deptIssueService.getArchiveDeptIssueList(
							searchModel, this.getPage(), this.getLimit());
			for (Map<String, Object> map : list.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", map.get("KB_ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("issueUuid", map.get("ISSUE_UUID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("issueDescription", map.get("ISSUE_DESCRIPTION"));
				dataMap.put("issueCause", map.get("ISSUE_CAUSE"));
				dataMap.put("issueResolution", map.get("ISSUE_RESOLUTION"));
				dataMap.put("userName", map.get("USER_NAME"));
				dataMap.put("updateDate", DateUtils.change((Date)map.get("UPDATE_DATE")));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 归档
	 */
	public void saveArchiveDeptIssue() {
		try {
			Map<String, String> model = this.convertJson(this.model);
			this.deptIssueService.saveArchiveDeptIssue(issueId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否是问题成员判定
	 */
	public void isDeptIssueMember() {
		JsonResult result = new JsonResult();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String userId = SessionData.getLoginUserId();
		try {
			boolean isMember = this.deptIssueService.isDeptIssueMember(issueId, userId);
			dataMap.put("isMember", isMember);
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);
		} catch(Exception e) {
			result.buildErrorResult(e);
		}
	}
	
	/**
	 * 删除草稿
	 */
	public void deleteDraftDeptIssue() {
		try {
			this.deptIssueService.deleteDraftDeptIssue(issueId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据保存
	 */
	private Map<String, Object> getDataMap(Map<String,Object> issueMap) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		// ID
		dataMap.put("id", issueMap.get("ISSUE_ID"));
		// UUID
		dataMap.put("uuid", issueMap.get("ISSUE_UUID"));
		// 问题标题
		dataMap.put("issueTitle", issueMap.get("ISSUE_TITLE"));
		// 进度状态
		dataMap.put("progressStatus", issueMap.get("PROGRESS_STATUS"));
		// 提出日期
		dataMap.put("raiseDate", DateUtils.change((Date)issueMap.get("RAISE_DATE")));
		// 偏离计划完成天数
		dataMap.put("daysDelayed", issueMap.get("DAYS_DELAYED"));
		// 部门ID
		dataMap.put("deptId", issueMap.get("DEPT_ID"));
		// 部门
		dataMap.put("deptName", issueMap.get("DEPT_NAME"));
		// 科室ID
		dataMap.put("subDeptId", issueMap.get("SUB_DEPT_ID"));
		// 科室
		dataMap.put("subDeptName", issueMap.get("SUB_DEPT_NAME"));
		// 问题来源ID
		dataMap.put("issueSourceId", issueMap.get("ISSUE_SOURCE_ID"));
		// 问题来源
		dataMap.put("issueSourceTitle", issueMap.get("TITLE"));
		// 提出人ID
		dataMap.put("raiseBy", issueMap.get("RAISE_BY"));
		// 提出人
		dataMap.put("raiseByName", issueMap.get("RAISE_BY_NAME"));
		// 责任科室ID
		dataMap.put("respDeptId", issueMap.get("RESP_DEPT_ID"));
		// 责任科室
		dataMap.put("respDeptName", issueMap.get("RESP_DEPT_NAME"));
		// 责任人ID
		dataMap.put("respUserId", issueMap.get("RESP_USER_ID"));
		// 责任人
		dataMap.put("respUserName", issueMap.get("RESP_USER_NAME"));
		// 当前任务负责人
		dataMap.put("taskOwner", issueMap.get("TASK_OWNER"));
		// 完成状态
		dataMap.put("statusCode", issueMap.get("STATUS_CODE"));
		// 完成状态名称
		dataMap.put("statusCodeName", issueMap.get("STATUS_CODE_NAME"));
		// 计划完成日期
		dataMap.put("dueDate", DateUtils.change((Date)issueMap.get("DUE_DATE")));
		// 实际完成日期
		dataMap.put("actualFinishaDate", DateUtils.change((Date)issueMap.get("ACTUAL_FINISH_DATE")));
		// 问题描述
		dataMap.put("issueDescription", issueMap.get("ISSUE_DESCRIPTION"));
		// 备注
		dataMap.put("issueCause", issueMap.get("ISSUE_CAUSE"));
		// 创建人ID
		dataMap.put("createBy", issueMap.get("CREATE_BY"));
		// 创建人
		dataMap.put("createByName", issueMap.get("CREATE_BY_NAME"));
		// 优先级
		dataMap.put("issuePriorityCode", issueMap.get("ISSUE_PRIORITY_CODE"));
		// 问题来源说明
		dataMap.put("issueSourceDescription", issueMap.get("ISSUE_SOURCE_DESCRIPTION"));
		return dataMap;
	}
	
	/**
	 * 审批记录取得
	 */
	public void getDeptIssueProcessRecord() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.deptIssueService.getProcessRecord(issueId);
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

	@Override
	public Object getModel() {
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
