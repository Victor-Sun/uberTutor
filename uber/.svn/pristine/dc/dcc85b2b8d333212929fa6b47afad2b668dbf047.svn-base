package com.gnomon.pdms.action.com;

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
import com.gnomon.pdms.entity.CodeTableEntity;
import com.gnomon.pdms.service.CodeTableService;

@Namespace("/com")
public class SysCodeAction extends PDMSCrudActionSupport<CodeTableEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CodeTableService codeTableService;
	
	private String type;
	public void setType(String type) {
		this.type = type;
	}
	
	private String exceptCode;
	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}

	/**
	 * 系统码表数据取得
	 */
	public void getSysCodeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			// Code List取得
			List<String> exceptCodeList = PDMSCommon.split(exceptCode, ",");
			List<Map<String, Object>> list =
					this.codeTableService.getSysCodeTable(type);
			for (Map<String, Object> map : list) {
				if (exceptCodeList.indexOf(map.get("CODE")) >= 0) {
					continue;
				}
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("code", map.get("CODE"));
				dataMap.put("name", map.get("NAME"));
				dataMap.put("description", map.get("DESCRIPTION"));
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
	 * 系统码表数据取得（质量问题管理）
	 */
	public void getImsCodeList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			// Code List取得
			List<String> exceptCodeList = PDMSCommon.split(exceptCode, ",");
			List<Map<String, Object>> list =
					this.codeTableService.getImsCodeTable(type);
			for (Map<String, Object> map : list) {
				if (exceptCodeList.indexOf(map.get("CODE")) >= 0) {
					continue;
				}
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("code", map.get("CODE"));
				dataMap.put("name", map.get("NAME"));
				dataMap.put("description", map.get("DESCRIPTION"));
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
