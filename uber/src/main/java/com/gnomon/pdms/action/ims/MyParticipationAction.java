package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;
import com.gnomon.pdms.service.MyParticipationService;

@Namespace("/ims")
public class MyParticipationAction extends PDMSCrudActionSupport<GTIssueEntity> {

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

	private GTIssueEntity gtIssue;
	
	@Autowired
	private MyParticipationService myparticipationService;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String query;
	private String searchChoice;
	private String notifyFlg;

	public void setNotifyFlg(String notifyFlg) {
		this.notifyFlg = notifyFlg;
	}
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
	
	public void setKeyId(String keyId) {
	}
	/**
	 * 质量问题管理-我参与的问题-一览数据取得
	 */
	public void getImsIssue(){

		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Page<VImsParticipationEntity> list =
					this.myparticipationService.getMyParticipationList(query, searchChoice, start, limit, notifyFlg);
			for(VImsParticipationEntity entity : list.getResult()){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//标题
				dataMap.put("qeTitle", entity.getTitle());
				//问题状态
				dataMap.put("qeStatus", entity.getIssueStatus());
				//问题等级
				dataMap.put("qeLevel", entity.getIssueLevel());
				//所属项目
				dataMap.put("beProject", entity.getProjectName());
				//表单标识
				dataMap.put("formKey", entity.getFormKey());
				//问题等级
				dataMap.put("risk", entity.getRiskLevelCode());
				//关注
				dataMap.put("mark", entity.getIsMark());
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
