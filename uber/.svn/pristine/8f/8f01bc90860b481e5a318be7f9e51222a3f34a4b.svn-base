package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ObsUserVMEntity;
import com.gnomon.pdms.service.ImsOrgUserService;

@Namespace("/ims")
public class ImsOrgUserAction extends PDMSCrudActionSupport<ObsUserVMEntity>{

	private static final long serialVersionUID = 1L;

	private ObsUserVMEntity obsUserVM;

	@Override
	public ObsUserVMEntity getModel() {
		return obsUserVM;
	}
	
	@Autowired
	private ImsOrgUserService imsOrgUserService;
	
	private String keyId;
	private String programObsId;
	private String programVehicleId;

	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getProgramObsId() {
		return programObsId;
	}
	public void setProgramObsId(String programObsId) {
		this.programObsId = programObsId;
	}
	
	public String getProgramVehicleId() {
		return programVehicleId;
	}
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}
	/**
	 * 责任确认-责任工程师&&验证工程师-下拉列表
	 */
	public void getEngineers() {
		try {
		JsonResult result = new JsonResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<ObsUserVMEntity> list = this.imsOrgUserService.getEngineers(programVehicleId, programObsId);
		if(list != null){
			for (ObsUserVMEntity entity : list) {
 
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", entity.getUserNo());
				// 零件名称
				dataMap.put("name", entity.getUserName());
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
