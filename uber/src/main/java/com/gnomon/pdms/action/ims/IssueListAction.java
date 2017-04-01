package com.gnomon.pdms.action.ims;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsLastAccessLogEntity;
import com.gnomon.pdms.service.ImsIssueMarkService;
import com.gnomon.pdms.service.IssueListService;
import com.gnomon.pdms.service.PmPostService;

@Namespace("/ims")
public class IssueListAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssueListService issueListService;
	
	@Autowired
	private PmPostService pmPostService;
	
	@Autowired
	private ImsIssueMarkService imsIssueMarkService;
	
	private String listType;
	public void setListType(String listType) {
		this.listType = listType;
	}
	
	private String searchModel;
	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}
	
	private String partStatus;
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	/**
	 * 质量问题管理-问题共通列表-一览数据取得
	 */
	public void getIssueList() {

		try{
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			String userId = SessionData.getLoginUserId();
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			
			List<String> listPart = PDMSCommon.split(partStatus, ", ");

			GTPage<Map<String, Object>> list = this.issueListService.getIssueList(
					listType, searchModel, listPart, this.getPage(), this.getLimit(),this.getFilter(),this.getSort());

			for (Map<String, Object> map : list.getItems()) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("issueId", map.get("ID"));
				//问题编号
				dataMap.put("code", map.get("CODE"));
				//问题标题
				dataMap.put("title", map.get("TITLE"));
				//问题状态
				dataMap.put("issueProcessingStatus", map.get("ISSUE_PROCESSING_STATUS"));
				
				dataMap.put("issueStatusCode", map.get("ISSUE_STATUS_CODE"));
				//问题等级
				dataMap.put("issueLevelName", map.get("ISSUE_LEVEL_NAME"));
				//所属项目
				dataMap.put("programCode", map.get("PROGRAM_CODE"));
				
				dataMap.put("programId", map.get("PROJECT_ID"));
				//责任部门
				dataMap.put("deptName", map.get("DEPT_NAME"));
				//风险等级
				dataMap.put("riskLevelCode", map.get("RISK_LEVEL_CODE"));
				//等待时间（天）
				dataMap.put("daysElapsed", map.get("DAYS_ELAPSED"));
				//表单标识
				dataMap.put("formClassId", map.get("FORM_CLASS_ID"));
				dataMap.put("navImageName", map.get("PROCESS_CODE")+"_STEP_"+map.get("CURRENT_STEP_ID"));
//				// 取得当前任务名称、负责人
//				String taskName = "";
//				String taskOwnerName = "";
//				List<Map<String, Object>> processInfo =
//						this.issueListService.getIssueProcess(PDMSCommon.nvl(map.get("ID")));
//				for (Map<String, Object> process : processInfo) {
//					//当前任务名称
//					taskName = PDMSCommon.nvl(process.get("TASK_NAME"));
//					//当前任务负责人
//					taskOwnerName += PDMSCommon.nvl(process.get("TASK_OWNER_NAME")) + ",";
//				}
//				if (PDMSCommon.isNotNull(taskOwnerName)) {
//					taskOwnerName = taskOwnerName.substring(0,  taskOwnerName.length() - 1);
//				}
				//当前任务名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				dataMap.put("taskId", map.get("TASK_ID"));
				//当前任务负责人
				dataMap.put("taskOwnerName", map.get("TASK_OWNER_NAME"));
				// 挂牌时间
				dataMap.put("listingDate", DateUtils.change((Date)map.get("LISTING_DATE")));
				// 摘牌时间
				dataMap.put("delistDate", DateUtils.change((Date)map.get("DELIST_DATE")));
//				// 挂牌状态
//				dataMap.put("issueListStatus", map.get("ISSUE_LIST_STATUS"));
				// 问题状态
				dataMap.put("issueLifecycleCode", map.get("ISSUE_LIFECYCLE_CODE"));
				// 子项目ID
				dataMap.put("subProjectId", map.get("SUB_PROJECT_ID"));
				//验证ID
				dataMap.put("vid", map.get("V_ID"));
				String isMark = imsIssueMarkService.isIssueMark(map.get("ID").toString(), userId);
				dataMap.put("isMark", isMark);
				//最近访问时间
				dataMap.put("lastAccessDate", ObjectConverter.convert2String(map.get("LAST_ACCESS_DATE")));
				dataMap.put("isNew", pmPostService.hasNewNotify(map.get("ID").toString(), "IMS-ISSUE", userId,ImsLastAccessLogEntity.OBJECT_TYPE_ISSUE));
				if(map.get("TASK_ID") != null){
					dataMap.put("isReturn", issueListService.isReturn(map.get("TASK_ID").toString()));
				}
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data,list.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void exportExcel() throws Exception {    
		try {
			// 查询条件
			Map<String, String> searchModel = new HashMap<String, String>();
			if (PDMSCommon.isNotNull(this.searchModel)) {
				searchModel = this.convertJson(this.searchModel);
			}
			
			List<String> programIdList = PDMSCommon.split(partStatus, ", ");
			HttpServletResponse response = Struts2Utils.getResponse();
			HSSFWorkbook wb = issueListService.exportExcel(listType, searchModel, programIdList);
			response.setContentType("application/vnd.ms-excel");    
			response.setHeader("Content-disposition", "attachment;filename=modelTemplate.xls");    
			OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);    
			ouputStream.flush();    
			ouputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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