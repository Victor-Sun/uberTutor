package com.gnomon.pdms.action.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.entity.SysProgramProfileVMEntity;
import com.gnomon.common.system.service.SysProgramProfileService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.CodeTableEntity;

@Namespace("/com")
public class SysProgramProfileAction extends PDMSCrudActionSupport<CodeTableEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysProgramProfileService sysProgramProfileService;
	
	/**
	 * 项目角色信息取得
	 */
	public void getProgramProfileList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<SysProgramProfileVMEntity> list =
					this.sysProgramProfileService.getSysProfileList();
			for (SysProgramProfileVMEntity entity : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", entity.getId());
				dataMap.put("profileName", entity.getProfileName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProgramProfileLevelOneList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<SysProgramProfileVMEntity> list =
					this.sysProgramProfileService.getSysProfileLevelOneList();
			for (SysProgramProfileVMEntity entity : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", entity.getId());
				dataMap.put("profileName", entity.getProfileName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getProgramProfileLevelTwoList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<SysProgramProfileVMEntity> list =
					this.sysProgramProfileService.getSysProfileLevelTwoList();
			for (SysProgramProfileVMEntity entity : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", entity.getId());
				dataMap.put("profileName", entity.getProfileName());
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
	public CodeTableEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
