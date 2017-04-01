package com.gnomon.pdms.action.ims;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.ImsReportService;

@Namespace("/ims")
public class ImsReportAction extends PDMSCrudActionSupport<GTIssueEntity> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ImsReportService imsReportService;
	
	private String partStatus;
	
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private boolean judgePreviledge;
	public void setJudgePreviledge(boolean judgePreviledge) {
		this.judgePreviledge = judgePreviledge;
	}
	
	/**
	 * 质量问题管理-仪表盘-总体情况
	 */
	public void getGeneral() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();

			List<String> listPart = PDMSCommon.split(partStatus, ", ");
			Map<String, Object> map = 
					this.imsReportService.getIssueTotalStatus(listPart);

			// 问题总数
			data.put("total", "Total");
			// 关闭数
			data.put("data1", map.get(ImsReportService.KEY_CLOSE_COUNT));
			// 预关闭数
			data.put("data2", map.get(ImsReportService.KEY_PRECLOSE_COUNT));
			// 继续跟进数
			data.put("data3", map.get(ImsReportService.KEY_CONTINUE_COUNT));
			//
			data.put("other", "4");

			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-仪表盘-问题状态统计
	 */
	public void getStatusStatistics() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<String> listPart = PDMSCommon.split(partStatus, ", ");
			List<Map<String,Object>> list =
					this.imsReportService.getIssueStatus(
							listPart, vehicleId, judgePreviledge);
			BigDecimal count = BigDecimal.ZERO;

			for (Map<String,Object> map : list) {
				count = count.add(new BigDecimal(String.valueOf(
						map.get(ImsReportService.KEY_ISSUE_COUNT))));
			}

			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				float rate = 0;
				if (count.intValue() != 0) {
					rate = new BigDecimal(String.valueOf(
						map.get(ImsReportService.KEY_ISSUE_COUNT))).
						multiply(new BigDecimal(100)).
						divide(count, 1, BigDecimal.ROUND_CEILING).floatValue();
				}
				// 问题状态
				dataMap.put("os", map.get(ImsReportService.KEY_ISSUE_STATUS_NAME));
				// 状态统计
				dataMap.put("data1", rate);	
				data.add(dataMap);
			}

			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 质量问题管理-仪表盘-问题状态部门分类统计
	 */
	public void getStatusOfObs() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<String> listPart = PDMSCommon.split(partStatus, ", ");
			List<Map<String,Object>> list =
					this.imsReportService.getIssueStatusOfObs(
							listPart, vehicleId, judgePreviledge);

			for (Map<String,Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();

				// 部门
				dataMap.put("dept", map.get("DEPT_NAME"));
				// 状态1统计
				dataMap.put("data1", map.get(ImsReportService.KEY_ISSUE_COUNT_61));
				// 状态2统计
				dataMap.put("data2", map.get(ImsReportService.KEY_ISSUE_COUNT_62));
				// 状态3统计
				dataMap.put("data3", map.get(ImsReportService.KEY_ISSUE_COUNT_63));
				// 状态4统计
				dataMap.put("data4", map.get(ImsReportService.KEY_ISSUE_COUNT_64));
				// 状态5统计
				dataMap.put("data5", map.get(ImsReportService.KEY_ISSUE_COUNT_65));
//				// 状态6统计
//				dataMap.put("data6", map.get(ImsReportService.KEY_ISSUE_COUNT_66));
				data.add(dataMap);
			}

			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//	public float getRate(BigDecimal count, String issueCount) {
//		float rate = 0;
//		if (count.intValue() != 0) {
//			rate = new BigDecimal(issueCount).
//					multiply(new BigDecimal(100)).
//					divide(count, 1, BigDecimal.ROUND_CEILING).floatValue();
//		}
//		return rate;
//	}

	@Override
	public GTIssueEntity getModel() {
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