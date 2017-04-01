package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.ImsIssueOperationLogService;

@Namespace("/ims")
public class ImsIssueOperationLogAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ImsIssueOperationLogService imsIssueOperationLogService;
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	/**
	 * 质量问题管理-操作日志-一览数据取得
	 */
	public void getOperationLog() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageInfo =
					this.imsIssueOperationLogService.getOperationLog(
							issueId, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageInfo.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// 审批日期
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				// 审批人
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 节点名称
				dataMap.put("stepName", map.get("STEP_NAME"));
				// 操作
				dataMap.put("actionName", map.get("ACTION_NAME"));
				// 意见
				dataMap.put("taskComment", map.get("TASK_COMMENT"));
				if("PROCESS_TASK_ACTION_RETURN".equals(map.get("ACTION"))){
					dataMap.put("roleId", "return");
				}else{
					dataMap.put("roleId", "forum");
				}
				
				dataMap.put("action", map.get("ACTION"));
				dataMap.put("actionName", map.get("ACTION_NAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageInfo.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
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
