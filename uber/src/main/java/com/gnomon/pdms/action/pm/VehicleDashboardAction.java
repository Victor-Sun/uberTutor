package com.gnomon.pdms.action.pm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.VehicleDashBoardService;
@Namespace("/pm")
public class VehicleDashboardAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private VehicleDashBoardService vehicleDashBoardService;
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	/**
	 * 车型看板-开放问题数据取得
	 */
	public void getOpenIssueReport() {
		try{
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			
			Map<String, Object> map =
					this.vehicleDashBoardService.getOpenIssueReport(programVehicleId);
			BigDecimal total = new BigDecimal(PDMSCommon.nvl(map.get("TOTAL"), "0"));
			BigDecimal closed = new BigDecimal(PDMSCommon.nvl(map.get("CLOSED"), "0"));
			BigDecimal inProcess = new BigDecimal(PDMSCommon.nvl(map.get("IN_PROCESS"), "0"));
			// 总数量
			data.put("total", total);
			// 已关闭
			data.put("closed", closed);
			// 进行中
			data.put("inProcess", inProcess);
			// 已关闭比率
			if (total.intValue() != 0 && closed.intValue() != 0) {
				data.put("closedRate", closed.divide(total, BigDecimal.ROUND_CEILING, 2));
			} else {
				data.put("closedRate", 0);
			}
			// 进行中比率
			if (total.intValue() != 0 && inProcess.intValue() != 0) {
				data.put("inProcessRate", inProcess.divide(total, BigDecimal.ROUND_CEILING, 2));
			} else {
				data.put("inProcessRate", 0);
			}
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 车型看板-交付物看板数据取得
	 */
	public void getDeliverableStatusReport() {
		try{
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			
			Map<String, Object> map =
					this.vehicleDashBoardService.getDeliverableStatusReport(programVehicleId);
			BigDecimal totalCnt = new BigDecimal(PDMSCommon.nvl(map.get("TOTAL_COUNT"), "0"));
			BigDecimal submitedCnt = new BigDecimal(PDMSCommon.nvl(map.get("SUBMITED_COUNT"), "0"));
			BigDecimal unsubmitCnt = new BigDecimal(PDMSCommon.nvl(map.get("UNSUBMIT_COUNT"), "0"));
			// 总数量
			data.put("totalCnt", totalCnt);
			// 已提交
			data.put("submitedCnt", submitedCnt);
			// 未提交
			data.put("unsubmitCnt", unsubmitCnt);
			// 已关闭比率
			if (totalCnt.intValue() != 0 && submitedCnt.intValue() != 0) {
				data.put("submitedRate", submitedCnt.divide(totalCnt, BigDecimal.ROUND_CEILING, 2));
			} else {
				data.put("submitedRate", 0);
			}
			// 进行中比率
			if (totalCnt.intValue() != 0 && unsubmitCnt.intValue() != 0) {
				data.put("unsubmitRate", unsubmitCnt.divide(totalCnt, BigDecimal.ROUND_CEILING, 2));
			} else {
				data.put("unsubmitRate", 0);
			}
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 车型看板-交付物看板数据取得
	 */
	public void getDeliverableStatusData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehicleDashBoardService.getDeliverableStatusData(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 当前节点
				dataMap.put("gateCode", map.get("GATE_CODE"));
				// 总数量
				dataMap.put("total", map.get("TOTAL"));
				// 未启动
				dataMap.put("notStart", map.get("NOT_START"));
				// 无风险
				dataMap.put("green", map.get("GREEN"));
				// 小风险
				dataMap.put("greenYellow", map.get("GREEN_YELLOW"));
				// 中风险
				dataMap.put("yellow", map.get("YELLOW"));
				// 大风险
				dataMap.put("red", map.get("RED"));
				// 无法判断
				dataMap.put("blue", map.get("BLUE"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 车型看板-节点进度趋势图数据取得
	 */
	public void getTaskProgressData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehicleDashBoardService.getTaskProgressData(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 计划完成率
				dataMap.put("nodePlanCompleteRate", new BigDecimal(PDMSCommon.nvl(
						map.get("NODE_PLAN_COMPLETE_RATE"), "0")).multiply(new BigDecimal("100")));
				// 实际完成率
				dataMap.put("nodeActualCompleteRate", new BigDecimal(PDMSCommon.nvl(
						map.get("NODE_ACTURAL_COMPLETE_RATE"), "0")).multiply(new BigDecimal("100")));
				// 日期
				dataMap.put("dateTo", DateUtils.change((Date)map.get("DATE_TO")));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 车型看板-节点分布图数据取得
	 */
	public void getNodeTrendData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehicleDashBoardService.getNodeTrendData(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 节点数
				dataMap.put("nodeCount", map.get("NODE_COUNT"));
				// 日期
				dataMap.put("dateTo", DateUtils.change((Date)map.get("DATE_TO")));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 车型看板-质量问题趋势图数据取得
	 */
	public void getOpenIssueTrendData() {
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list =
					this.vehicleDashBoardService.getOpenIssueTrendData(programVehicleId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 日期
				dataMap.put("dateTo", DateUtils.change((Date)map.get("DATE_TO")));
				// 实际遗留预关闭数
				dataMap.put("pendingCount", map.get("PENDING_COUNT"));
				// 实际遗留分析中预关闭数
				dataMap.put("openCount", map.get("OPEN_COUNT"));
				// 计划遗留问题数
				dataMap.put("plannedPendingCount", map.get("PLANNED_PENDING_COUNT"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
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
