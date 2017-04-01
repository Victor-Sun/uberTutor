package com.gnomon.pdms.action.sys;

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
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMDeptIssueSourceEntity;
import com.gnomon.pdms.service.PmDeptIssueService;

@Namespace("/sys")
public class PmDeptIssueSourceAction extends PDMSCrudActionSupport<PMDeptIssueSourceEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PmDeptIssueService pmDeptIssueService;

	private PMDeptIssueSourceEntity entity;
	
	public Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 取得问题类型列表
	 */
	public void getDeptIssueSourceList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			GTPage<Map<String, Object>> pageResult =
					this.pmDeptIssueService.getDeptIssueSourceList(this.getPage(), this.getLimit());
			for(Map<String, Object> map : pageResult.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("description", map.get("DESCRIPTION"));
				dataMap.put("seq", map.get("SEQ"));
				dataMap.put("createDate", DateUtils.formate((Date)map.get("CREATE_DATE"),
						"yyyy/MM/dd HH:mm:ss"));
				data.add(dataMap);
			}
//			 结果返回
			result.buildSuccessResultForList(data,pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public PMDeptIssueSourceEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
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
		pmDeptIssueService.save(entity);
		this.writeSuccessResult(null);
		return null;
	}

	@Override
	public String delete() throws Exception {
		try {
			pmDeptIssueService.delete(id);
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
			entity = new PMDeptIssueSourceEntity();
			entity.setCreateDate(new Date());
			entity.setIsActive("Y");
//			entity.setType("P");
		}else{
			entity = pmDeptIssueService.get(id);
		}
	}
	
}
