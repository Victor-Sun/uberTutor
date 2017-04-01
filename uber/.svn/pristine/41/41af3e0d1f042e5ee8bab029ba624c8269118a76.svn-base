package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsIssueChangeFieldEntity;
import com.gnomon.pdms.service.ImsIssueChangeFieldService;

@Namespace("/ims")
public class ImsIssueChangeFieldAction extends PDMSCrudActionSupport<ImsIssueChangeFieldEntity> {

	private static final long serialVersionUID = 1L;

	private ImsIssueChangeFieldEntity imsIssueChangeFieldEntity;
	
	@Autowired
	private ImsIssueChangeFieldService imsIssueChangeFieldService;
	
	@Override
	public ImsIssueChangeFieldEntity getModel() {
		return imsIssueChangeFieldEntity;
	}
	
	private String issueId;
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	/**
	 * 质量问题管理-变更日志-一览数据取得
	 */
	public void getChangeField(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = imsIssueChangeFieldService.getChangeFieldList(issueId);
		for(Map<String, Object> map : list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//ID
			dataMap.put("id", map.get("ID"));
			//变更时间
			dataMap.put("processingTime",  ObjectConverter.convert2String(map.get("CHANGE_DATE")));
			
			//变更人
			dataMap.put("processingPeople", map.get("CHANGE_USERNAME"));
			//变更前
			dataMap.put("actionOne", map.get("FIELD_ORIGINAL_VALUE"));
			//变更为
			dataMap.put("actionTwo", map.get("FIELD_NAME"));
			//变更后
			dataMap.put("actionThree", map.get("FIELD_NEW_VALUE"));
			data.add(dataMap);
		}	
		// 结果返回
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
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
