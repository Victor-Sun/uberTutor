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
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.service.QueryService;

@Namespace("/ims")
public class QueryAction extends PDMSCrudActionSupport<GTIssueEntity>{

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
	private QueryService queryService;

	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String model;
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public void setKeyId(String keyId) {
	}
	/**
	 * 问题查询
	 */
	public void getImsIssue(){

		try{
			JsonResult result = new JsonResult();
			// JSON解析
			Map<String, String> model = null ;
			if (this.model != null){
				// JSON解析
				model = this.convertJson(this.model);
			}
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Page<VImsIssueEntity> list =
					this.queryService.getQueryServiceList(model, start, limit);
			for(VImsIssueEntity entity : list.getResult()){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//标题
				dataMap.put("qeTitle", entity.getTitle());
//				//问题状态
//				dataMap.put("qeStatus", entity.getIssueStatus());
//				//问题等级
//				dataMap.put("qeLevel", entity.getIssueLevel());
				//所属项目
//				dataMap.put("beProject", entity.getProjectName());
				//表单标识
				dataMap.put("formKey", entity.getFormKey());
				//风险等级
				dataMap.put("riskLevelCode", entity.getRiskLevelCode());
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
