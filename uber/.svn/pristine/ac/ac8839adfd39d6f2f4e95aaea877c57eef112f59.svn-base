package com.gnomon.pdms.action.ims;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.MyDraftService;

@Namespace("/ims")
public class MyDraftAction extends PDMSCrudActionSupport<GTIssueEntity> {

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
	private MyDraftService myDraftService;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssue;
	}

	private String query;
	private String searchChoice;

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
	
	private String keyId;
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	private String draftIdxId;
	public void setDraftIdxId(String draftIdxId) {
		this.draftIdxId = draftIdxId;
	}

	/**
	 * 质量问题管理-我的草稿-表单字段值取得
	 */
	public void getMyDraft(){
		try{
			JsonResult result = new JsonResult();
			GTIssueEntity entity =
					this.myDraftService.getMyDraft(keyId);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//问题标题
			dataMap.put("ProTitle", entity.getTitle());
			//问题提出人
//			dataMap.put("QestionOut", entity.getSubmitUserName());
			//提出时间
			SimpleDateFormat Date1 = new SimpleDateFormat("yyyy/MM/dd");
			dataMap.put("ProposedTime", Date1.format(System.currentTimeMillis()));
			//问题来源
			dataMap.put("ProblemSource", entity.getIssueSourceId());
			//所属项目
			dataMap.put("BeProject", entity.getProjectId());
			//子项目
			dataMap.put("SubProject", entity.getSubProjectId());
			//样车阶段
			dataMap.put("BuildPhase", entity.getStageId());
			//发生时间
			if(entity.getOccurrenceDate() != null){
			dataMap.put("OccurrenceTime", DateUtils.formate(entity.getOccurrenceDate()));
			}else{
				dataMap.put("OccurrenceTime","");
			}
			//发生场地
			dataMap.put("OccurrenceSite", entity.getOccurrenceSite());
			//实验类型
			dataMap.put("ExperimentType", entity.getTestTypeId());
			//样车编号
			dataMap.put("FaultNumber", entity.getSampleNumber());
			//故障零件里程
			dataMap.put("PartMileage", entity.getTroublePartMileage());
			//实验进展
			dataMap.put("ProgressExper", entity.getTestProgress());
			//问题描述
			dataMap.put("QeDescription", entity.getDescription());
			//处置措施
			dataMap.put("DisposalMeasures", entity.getDisposalMeasures());
			//初步分析原因
			dataMap.put("PreliminaryCause", entity.getFirstCauseAnalysis());
			//问题等级
			dataMap.put("risk", entity.getRiskLevelCode());
			//备注
			dataMap.put("memo", entity.getMemo());
			
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		}catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	/**
	 * 删除我的草稿
	 */
	public void delMyDraft(){
		try{
			this.myDraftService.deleteDraftIndex(draftIdxId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
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
