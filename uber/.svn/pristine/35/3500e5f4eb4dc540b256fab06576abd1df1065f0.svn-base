package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsCodeTableEntity;
import com.gnomon.pdms.entity.IssueSourceEntity;
import com.gnomon.pdms.entity.IssueTypeEntity;
import com.gnomon.pdms.entity.StageEntity;
import com.gnomon.pdms.entity.TestTypeEntity;
import com.gnomon.pdms.entity.VImsPartEntity;
import com.gnomon.pdms.entity.VImsPartStatusEntity;
import com.gnomon.pdms.entity.VPmProcessingProgramEntity;
import com.gnomon.pdms.entity.VPmProcessingVehicleEntity;
import com.gnomon.pdms.entity.VPmRespDeptEntity;
import com.gnomon.pdms.service.ComboService;

@Namespace("/ims")
public class ComboAction extends PDMSCrudActionSupport<ImsCodeTableEntity>{

	private static final long serialVersionUID = 1L;
	
	private ImsCodeTableEntity imsCodeTableEntity;
	
	@Override
	public ImsCodeTableEntity getModel() {
		return imsCodeTableEntity;
	}
	
	@Autowired
	private ComboService comboService;
	
	private String programId;
	private String programVehicleId;
	private String id;
	private String keyId;
	private String queryFlg;

	public void setQueryFlg(String queryFlg) {
		this.queryFlg = queryFlg;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public void setTaskId(String taskId) {
	}

	/**
	 * 质量问题管理-问题提交-项目-下拉列表
	 */
	public void getProgram() {
		try {
		JsonResult result = new JsonResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<VPmProcessingProgramEntity> list = this.comboService.getProgram();
		
		for (VPmProcessingProgramEntity entity : list) {

			Map<String, Object> dataMap = new HashMap<String, Object>();
			// ID
			dataMap.put("id", entity.getId());
			// 阶段
			dataMap.put("name", entity.getCode());
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
	 * 质量问题管理-问题提交-子项目-下拉列表
	 */
	public void getProgramVehicle() {
		try {
		JsonResult result = new JsonResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<VPmProcessingVehicleEntity> list = this.comboService.getProgramVehicle(programId);

		for (VPmProcessingVehicleEntity entity : list) {
 
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//ID
			dataMap.put("id", entity.getId());
			//名称
			dataMap.put("name", entity.getVehicleName());
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
	 * 质量问题管理-问题提交-样车阶段-下拉列表
	 */
	public void getBuildPhase(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<StageEntity> list =
					this.comboService.getPhaseList();
			for(StageEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//名称
				dataMap.put("name", entity.getName());
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
	 * 对策决定-问题类型-下拉列表
	 */
	public void getIssueType(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<IssueTypeEntity> list = this.comboService.getIssueType();
			
			for (IssueTypeEntity entity : list) {

				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", entity.getId());
				//名称
				dataMap.put("name", entity.getName());
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
	 * 质量问题管理-提交问题-问题来源-下拉列表
	 */
	public void getSourceList(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<IssueSourceEntity> list =
					this.comboService.getProblemSource();
			for(IssueSourceEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//ID
				dataMap.put("id", entity.getId());
				//名称
				dataMap.put("name", entity.getName());
				dataMap.put("code", entity.getCode());
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
	 * 质量问题管理-问题提交-试验类型-下拉列表
	 */

	public void getTestType() {
		try {
		JsonResult result = new JsonResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<TestTypeEntity> list = this.comboService.getTestType();
	
		for (TestTypeEntity entity : list) {

			Map<String, Object> dataMap = new HashMap<String, Object>();
			// ID
			dataMap.put("id", entity.getId());
			//名称
			dataMap.put("name", entity.getName());
			dataMap.put("code", entity.getCode());
			dataMap.put("createBy", entity.getCreateBy());
			dataMap.put("createDate", ObjectConverter.convert2String(entity.getCreateDate()));
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
	 * 对策决定-验证方案-下拉列表
	 */
	public void getVerification(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ImsCodeTableEntity> list = this.comboService.getVerification();
			if(list != null){
				for (ImsCodeTableEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					// ID
					dataMap.put("id", entity.getCode());
					//名称
					dataMap.put("name", entity.getName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	/**
	 * 效果确认-下拉列表
	 */
	public void getEffectConfirmation(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ImsCodeTableEntity> list = this.comboService.getEffectConfirmation();
			if(list != null){
				for (ImsCodeTableEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", entity.getCode());
					//名称
					dataMap.put("name", entity.getName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	/**
	 * 问题等级-下拉列表
	 */
	public void getIssueLevel(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ImsCodeTableEntity> list = this.comboService.getIssueLevel();
			if(list != null){
				for (ImsCodeTableEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", entity.getCode());
					//名称
					dataMap.put("name", entity.getName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	/**
	 * 责任部门&&验证部门-下拉列表
	 */
	public void getDepartment(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VPmRespDeptEntity> list = this.comboService.getDept(keyId, queryFlg);
			if(list != null){
				for (VPmRespDeptEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					// ID
					dataMap.put("id", entity.getId());
					// OBS_NAME
					dataMap.put("name", entity.getObsName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	public void getRespDepartment(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.comboService.getRespDepartment();
			if(list != null){
				for (Map<String, Object> map : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					// ID
					dataMap.put("id", map.get("ID"));
					dataMap.put("name", map.get("NAME"));
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	/**
	 * 问题确认-问题性质-下拉列表
	 */
    public void getIssueNature(){
			
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ImsCodeTableEntity> list = this.comboService.getIssueNature();
			if(list != null){
				for (ImsCodeTableEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", entity.getCode());
					//名称
					dataMap.put("name", entity.getName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	/**
	 * 对策决定-故障零件代号-下拉列表
	 */
	public void getVImsPart(){
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = this.comboService.getImsPart(id);
			if(list != null){
				for (Map<String, Object> map : list) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", map.get("ID"));
					//名称
					dataMap.put("partCode", map.get("PART_CODE"));
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	/**
	 * 对策决定-故障零件名字-下拉列表
	 */
	public void getVImsPartName() {
		try {
		JsonResult result = new JsonResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<VImsPartEntity> list = this.comboService.getVImsPartName(id);
		if(list != null){
			for (VImsPartEntity entity : list) {
 
				Map<String, Object> dataMap = new HashMap<String, Object>();
				// 零件名称
				dataMap.put("partName", entity.getPartName());
				data.add(dataMap);
			}
		}
		// 结果返回
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 对策决定-故障零件状态-下拉列表
	 */
	public void getVImsPartStatus() {
		
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VImsPartStatusEntity> list = this.comboService.getVImsPartStatus();
			if(list != null){
				for (VImsPartStatusEntity entity : list) {

					Map<String, Object> dataMap = new HashMap<String, Object>();
					//ID
					dataMap.put("id", entity.getId());
					//名称
					dataMap.put("name", entity.getName());
					data.add(dataMap);
				}
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
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
}
