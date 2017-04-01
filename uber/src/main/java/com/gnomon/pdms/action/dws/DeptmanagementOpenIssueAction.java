package com.gnomon.pdms.action.dws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.IssueProgressEntity;
import com.gnomon.pdms.entity.VIssueSourceEntity;
import com.gnomon.pdms.service.DeptmanagementOpenIssueService;

@Namespace("/dws")
public class DeptmanagementOpenIssueAction extends PDMSCrudActionSupport<VIssueSourceEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private VIssueSourceEntity vissueSourceEntity;

	@Autowired
	private DeptmanagementOpenIssueService deptmanagementOpenIssueService;
	
	@Override
	public VIssueSourceEntity getModel() {
		return vissueSourceEntity;
	}
	
	private String issueId;
	
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	/**
	 * 【项目管理-项目列表】一OpenIssue览数据取得
	 */
	public void getDeptmanagementOpenIssue() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VIssueSourceEntity> list =
					this.deptmanagementOpenIssueService.getDeptmanagementOpenIssueService();
			for(VIssueSourceEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// ID
				dataMap.put("id", entity.getId());
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
				// 工作进展描述
				dataMap.put("progressDescription", entity.getProgressDescription());
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
	 * 【项目管理-项目列表】一OpenIssue览详细form数据取得
	 */
	public void getDeptmanagementOpenIssueForm() {
		try {
			JsonResult result = new JsonResult();
			VIssueSourceEntity  entity =
					this.deptmanagementOpenIssueService.getDeptmanagementOpenIssueFormService(issueId);
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
	public void getDeptmanagementOpenIssueGrid() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<IssueProgressEntity> list =
					this.deptmanagementOpenIssueService.getDeptmanagementOpenIssueGridService(issueId);
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
}
