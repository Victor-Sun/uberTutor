package com.gnomon.pdms.action.mw;

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
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.DoneListService;

@Namespace("/mw")
public class DoneListAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private DoneListService doneListService;
	
	private String searchVal;
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	private String searchItem;
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	/**
	 * 已办任务列表取得
	 */
	public void getDoneListPM() {
		try {
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			int bef = 0;
			if (PDMSCommon.isNotNull(searchVal) &&
					PDMSCommon.isNotNull(searchItem)) {
				bef = Integer.parseInt(searchVal);
				if ("1".equals(searchItem)) {
					bef = bef * 7;
				} else if ("2".equals(searchItem)) {
					bef = bef * 30;
				} else if ("3".equals(searchItem)) {
					bef = bef * 90;
				} else if ("4".equals(searchItem)) {
					bef = bef * 365;
				}
			}

			GTPage<Map<String, Object>> pageResut = this.doneListService.getDoneList(
					userId, bef, this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResut.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("taskId", map.get("TASK_ID"));
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				dataMap.put("attCnt", map.get("ATT_CNT"));
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("taskTypeName", PDMSCommon.getTaskTypeName(
						PDMSCommon.nvl(map.get("PROCESS_STEP_ID")),
						PDMSCommon.nvl(map.get("PROCESS_CODE"))));
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				dataMap.put("programId", map.get("PROGRAM_ID"));
				dataMap.put("taskPriorityName", map.get("TASK_PRIORITY_NAME"));
				dataMap.put("taskStatusCode", map.get("TASK_STATUS_CODE"));
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				dataMap.put("delayDays", map.get("DELAY_DAYS"));
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				dataMap.put("publishByName", map.get("PUBLISH_BY_NAME"));
				dataMap.put("lastReportDate", DateUtils.change((Date)map.get("LAST_REPORT_DATE")));
				dataMap.put("plannedFinishDate", DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				dataMap.put("processStepId", map.get("PROCESS_STEP_ID"));
				dataMap.put("currentStepId", map.get("CURRENT_STEP_ID"));
				dataMap.put("processTaskOwner", map.get("PROCESS_TASK_OWNER"));
				dataMap.put("processId", map.get("PROCESS_ID"));
				dataMap.put("processCode", map.get("PROCESS_CODE"));
				dataMap.put("completeFlag", map.get("COMPLETE_FLAG"));
				dataMap.put("actualFinishDate", DateUtils.change((Date)map.get("ACTUAL_FINISH_DATE")));
				dataMap.put("deliverableId", map.get("DELIVERABLE_ID"));
				dataMap.put("deliverableStatus", map.get("DELIVERABLE_STATUS"));
				dataMap.put("processTaskProgressStatus", map.get("PROCESS_TASK_PROGRESS_STATUS"));
				// 当前任务负责人
				dataMap.put("currentTaskOwner", this.doneListService.getCurrentTaskOwner(
								PDMSCommon.nvl(map.get("TASK_ID")),
								PDMSCommon.nvl(map.get("PROCESS_CODE"))));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, pageResut.getItemCount());
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

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
