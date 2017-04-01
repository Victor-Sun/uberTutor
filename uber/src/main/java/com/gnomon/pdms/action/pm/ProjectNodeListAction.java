package com.gnomon.pdms.action.pm;

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
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.ProjectNodeListService;

@Namespace("/pm")
public class ProjectNodeListAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private ProjectNodeListService projectNodeListService;

	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}

	/**
	 * 节点管理清单信息取得
	 */
	public void getProjecNodeList() {
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			// 查询
			GTPage<Map<String, Object>> list =
					this.projectNodeListService.getProjectNodeList(programVehicleId,
							searchModel, this.getPage(), this.getLimit());
			for(Map<String, Object> map : list.getItems()){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", map.get("ID"));
				//车型
				dataMap.put("vehicleName", map.get("VEHICLE_NAME"));
				//对应阀门
				dataMap.put("gateCode", map.get("GATE_CODE"));
				//节点名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				//专业领域
				dataMap.put("functionObsName", map.get("FUNCTION_OBS_NAME"));
				//责任组
				dataMap.put("obsName", map.get("OBS_NAME"));
				//责任人
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				//计划完成时间
				dataMap.put("plannedFinishiDate",
						DateUtils.change((Date)map.get("PLANNED_FINISH_DATE")));
				//实际完成时间
				dataMap.put("actualFinishDate",
						DateUtils.change((Date)map.get("ACTUAL_FINISH_DATE")));
				// 进展状态
				dataMap.put("taskProgressStatusCode", map.get("TASK_PROGRESS_STATUS_CODE"));
				// 任务状态名称
				dataMap.put("taskStatusName", map.get("TASK_STATUS_NAME"));
				// 任务状态
				dataMap.put("taskStatusCode", map.get("TASK_STATUS_CODE"));
				// 节点对应交付物
				dataMap.put("deliverable", map.get("TASK_DELIVERABLE_NAME"));
				// 最新更新时间
				dataMap.put("lastReportDate",
						DateUtils.change((Date)map.get("LAST_REPORT_DATE")));
				dataMap.put("progressStatus", map.get("PROGRESS_STATUS"));
				// 计划类别
				if ("1".equals(PDMSCommon.nvl(map.get("PLAN_LEVEL")))) {
					dataMap.put("planLevelName", "主计划");
				} else {
					dataMap.put("planLevelName", "二级计划");
				}
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, list.getItemCount());
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

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}