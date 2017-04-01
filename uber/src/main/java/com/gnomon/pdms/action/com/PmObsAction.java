package com.gnomon.pdms.action.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.entity.SysUserVMEntity;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMObsTreeVMEntity;
import com.gnomon.pdms.service.PmObsService;

@Namespace("/com")
public class PmObsAction extends PDMSCrudActionSupport<SysUserVMEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PmObsService pmObsService;
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String vehicleId;
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	private String rootObsId;
	public void setRootObsId(String rootObsId) {
		this.rootObsId = rootObsId;
	}

	private String node;
	public void setNode(String node) {
		this.node = node;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	/**
	 * 项目组织信息取得
	 */
	public void getPmObsTree() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<PMObsTreeVMEntity> list =
					this.pmObsService.getPmObsTree(programId, vehicleId);
			
			if ("root".equals(node)) {
				for (PMObsTreeVMEntity entity : list) {
					if (PDMSCommon.isNotNull(rootObsId)) {
						if (rootObsId.equals(entity.getId())) {
							Map<String, Object> dataMap = new HashMap<String, Object>();
							dataMap.put("id", entity.getId());
							dataMap.put("text", entity.getObsName());
							dataMap.put("iconCls", "x-fa fa-group");
							dataMap.put("expanded", false);
							dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
							data.add(dataMap);
							break;
						}
					} else {
						if (PDMSCommon.isNull(entity.getParentId())) {
							Map<String, Object> dataMap = new HashMap<String, Object>();
							dataMap.put("id", entity.getId());
							dataMap.put("text", entity.getObsName());
							dataMap.put("iconCls", "x-fa fa-group");
							dataMap.put("expanded", false);
							dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
							data.add(dataMap);
						}
					}
				}
			} else {
				for (PMObsTreeVMEntity entity : list) {
					if (node.equals(entity.getParentId())) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", entity.getId());
						dataMap.put("text", entity.getObsName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", "Y".equals(entity.getIsLeaf()));
						data.add(dataMap);
					}
				}
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 专业领域及其下级组织一览取得
	 */
	public void getRespDeptChildList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> list =
					this.pmObsService.getRespDeptChildList(programVehicleId, rootObsId);
			for (Map<String, Object> map : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("obsCode", map.get("OBS_CODE"));
				dataMap.put("obsName", map.get("OBS_NAME"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUserVMEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
