package com.gnomon.pdms.action.ims;

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
import com.gnomon.pdms.service.HistoryFormService;

@Namespace("/ims")
public class HistoryFormAction extends PDMSCrudActionSupport<Object> {
	
	private static final long serialVersionUID = 1L;

	@Autowired
    private HistoryFormService historyFormService;
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	/**
	 * 质量问题管理-问题提交-历史表单-一览数据取得
	 */
	public void getHistoryForm() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			GTPage<Map<String, Object>> pageInfo =
					this.historyFormService.getHistoryForm(
							issueId, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageInfo.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// ID
				dataMap.put("id", map.get("ID"));
				// ISSUE_ID
				dataMap.put("issueId", map.get("ISSUE_ID"));
				// 问题编号
				dataMap.put("code", map.get("CODE"));
				// 问题标题
				dataMap.put("title", map.get("TITLE"));
				// 问题状态
				dataMap.put("issueStatus", map.get("ISSUE_PROCESSING_STATUS"));
				// 问题等级
				dataMap.put("issueLevel", map.get("ISSUE_LEVEL_NAME"));
				// 所属项目
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				// 版本号
				dataMap.put("revision", map.get("REVISION"));
				// 版本创建日期
				dataMap.put("hstCreateDate", DateUtils.change((Date)map.get("HST_CREATE_DATE")));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageInfo.getItemCount());
			Struts2Utils.renderJson(result);
		}  catch (Exception ex) {
			ex.printStackTrace();
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
