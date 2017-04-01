package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.IssueSourceEntity;
import com.gnomon.pdms.service.IssueSourceService;

@Namespace("/issuesource")
public class IssueSourceAction extends PDMSCrudActionSupport<IssueSourceEntity> {

	private static final long serialVersionUID = 1L;
	private IssueSourceEntity issueSourceEntity;
	
	@Override
	public IssueSourceEntity getModel() {
		return issueSourceEntity;
	}
	@Autowired
	private IssueSourceService issueSourceService;
	
	public Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		issueSourceService.save(issueSourceEntity);
		this.writeSuccessResult(null);
		return null;
	}

	@Override
	public String delete() throws Exception {
		try {
			issueSourceService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			issueSourceEntity = new IssueSourceEntity();
		}else{
			issueSourceEntity = issueSourceService.get(id);
		}
		
	}
	
	public void getIssueSource() {

		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = issueSourceService.getIssueSourceList();
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("code", map.get("CODE"));
				dataMap.put("name", map.get("NAME"));
				
				data.add(dataMap);
			}
			
			// 结果返回
			result.buildSuccessResultForList(data, list.size());
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
