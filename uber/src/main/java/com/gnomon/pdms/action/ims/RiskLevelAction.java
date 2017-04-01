package com.gnomon.pdms.action.ims;

import java.util.Date;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.RiskLevelService;

@Namespace("/ims")
public class RiskLevelAction extends PDMSCrudActionSupport<GTIssueEntity> {

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

	private GTIssueEntity gTIssueEntity;
	@Override
	public GTIssueEntity getModel() {
		return gTIssueEntity;
	}
	@Autowired
	private RiskLevelService riskLevelService;
	
	private String keyId;
	private String riskLevelCode;
	
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getRiskLevelCode() {
		return riskLevelCode;
	}

	public void setRiskLevelCode(String riskLevelCode) {
		this.riskLevelCode = riskLevelCode;
	}
//	
//	private String partStatus;
//	
//	public void setPartStatus(String partStatus) {
//		this.partStatus = partStatus;
//	}

	//保存问题等级
	public void saveRiskLevel(){
		try {
			JsonResult result = new JsonResult();
			
			GTIssueEntity entity = this.riskLevelService.getRiskLevel(keyId);
			
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();

			if ("RISK_LEVEL_4".equals(riskLevelCode)) {
				entity.setRiskLevelCode("");
			} else {
				entity.setRiskLevelCode(riskLevelCode);
			}
			entity.setUpdateBy(userId);
			entity.setUpdateDate(new Date());

			riskLevelService.save(entity);	
			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
//	/**
//	 * 主页-热点问题-一览数据取得
//	 */
//	public void getRiskList(){
//		JsonResult result = new JsonResult();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		List<String> listPart = PDMSCommon.split(partStatus, ", ");
//		GTPage<Map<String, Object>> pageResult = this.riskLevelService.getRiskList(
//				listPart, this.getPage(), this.getLimit());
//		for(Map<String, Object> map : pageResult.getItems()){
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			//ID
//			dataMap.put("id", map.get("ID"));
//			//标题
//			dataMap.put("qeTitle", map.get("TITLE"));
//			//所属项目
//			dataMap.put("beProject", map.get("PROJECT_NAME"));
//			//问题状态
//			dataMap.put("qeStatus", map.get("ISSUE_STATUS"));
//			//问题等级
//			dataMap.put("qeLevel", map.get("ISSUE_LEVEL"));
//			//责任部门
//			dataMap.put("resDepartment", map.get("DEPT_NAME"));
//			//发布时间
//			dataMap.put("pubdate", DateUtils.change((Date)map.get("PUBLISH_DATE")));
//			//问题等级
//			dataMap.put("risk", map.get("RISK_LEVEL_CODE"));
//			data.add(dataMap);
//		}
//		// 结果返回
//		result.buildSuccessResultForList(data, pageResult.getItemCount());
//		Struts2Utils.renderJson(result);
//	}
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
