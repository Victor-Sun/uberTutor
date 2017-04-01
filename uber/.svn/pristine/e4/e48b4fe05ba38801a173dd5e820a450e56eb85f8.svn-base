package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsIssueMergeEntity;
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.service.MyTaskInfo01ComparedService;
import com.gnomon.pdms.service.MyTaskService;

@Namespace("/ims")
public class MyTaskInfo01ComparedAction extends PDMSCrudActionSupport<ImsIssueMergeEntity> {

	private static final long serialVersionUID = 1L;

	private int start;
	private int limit;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	private ImsIssueMergeEntity imsIssueMergeEntity;
	
	@Autowired
	private MyTaskInfo01ComparedService myTaskInfo01ComparedService;
	
	@Autowired
	private MyTaskService myTaskService;
	
	@Override
	public ImsIssueMergeEntity getModel() {
		return imsIssueMergeEntity;
	}

	private String query;
	private String searchChoice;

	public String getSearchChoice() {
		return searchChoice;
	}
	public void setSearchChoice(String searchChoice) {
		this.searchChoice = searchChoice;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	private String keyId;
	private String oldFormId;

	private String ProcessInstanceId;
	private String TaskId;

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public void setOldFormId(String oldFormId) {
		this.oldFormId = oldFormId;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return TaskId;
	}

	public void setTaskId(String taskId) {
		TaskId = taskId;
	}

	/**
	 * 质量问题管理-我的待办-问题发布-合并查询
	 */
	public void getImsIssue(){

		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Page<VImsIssueEntity> list =
					this.myTaskInfo01ComparedService.getCombineList(query, searchChoice, start, limit, oldFormId);
			for(VImsIssueEntity entity : list.getResult()){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//标题
				dataMap.put("qeTitle", entity.getTitle());
				//问题状态
//				dataMap.put("qeStatus", entity.getIssueStatus());
//				//问题等级
//				dataMap.put("qeLevel", entity.getIssueLevel());
				//所属项目
//				dataMap.put("beProject", entity.getProjectName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, Integer.valueOf(
					String.valueOf(list.getTotalCount())));
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-我的待办-问题发布-合并结果保存
	 */
	public void saveCombined(){

		try{
			JsonResult result = new JsonResult();
			GTIssueEntity entityG = 
					this.myTaskInfo01ComparedService.getNewQestion(oldFormId);

			ImsIssueMergeEntity entity = null;
			entity = new ImsIssueMergeEntity();

			String userId = "";
			// 登录用户ID取得
			userId = SessionData.getLoginUserId();

			String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
			entity.setId(uuid);                                                 //ID
			entity.setPrimaryIssueId(keyId);                                    //主问题ID
			entity.setIssueId(entityG.getId());                                 //被合并的问题ID
			entity.setCode(entityG.getCode());                                  //被合并的问题编号
			entity.setTitle(entityG.getTitle());                                //被合并的问题标题
//			entity.setSubmitUserName(entityG.getSubmitUserName());              //被合并的问题提出人
			entity.setOpenDate(entityG.getOpenDate());                          //被合并的问题提出时间
			entity.setCreateBy(userId);
			entity.setCreateDate(new Date());
			entity.setUpdateBy(userId);
			entity.setUpdateDate(new Date());

			entityG.setIssueStatusCode("ISSUE_STATUS_70");                      //问题状态
			entityG.setIssueLifecycleCode("ISSUE_LIFECYCLE_INEFFECTIVE");
			entityG.setUpdateBy(userId);
			entityG.setUpdateDate(new Date());
			
//			myTaskInfo01ComparedService.mergeIssue(ProcessInstanceId, TaskId);
			
			myTaskInfo01ComparedService.save(entity);
			myTaskInfo01ComparedService.save(entityG);

			//保存变更记录
//			this.pkgImsIssueDBProcedureServcie.saveIssueChangelog(keyId, "ISSUE_STATUS_CODE", "ISSUE_STATUS_60", "ISSUE_STATUS_70", userId);
			//我参与的问题list
//			List<VImsParticipationEntity> list = 
//					this.myTaskService.getMyParticipation(keyId, userId);
//			
//			if(list.size() != 0){
//				myTaskService.updateMyParticipation(keyId);
//			} else {
//				myTaskService.insertMyParticipation(keyId);
//			}
			
			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);

		}catch (Exception ex) {
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
	
}
