package com.gnomon.pdms.action.mockbimserver;

import org.apache.struts2.convention.annotation.Namespace;
import org.springside.modules.utils.web.struts2.Struts2Utils;

@Namespace("/mockbimserver")
public class TargetOrganizationService extends MockBimService{
	
	private String systemCode;
	private String useDefaultFilters;
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public void setUseDefaultFilters(String useDefaultFilters) {
		this.useDefaultFilters = useDefaultFilters;
	}
	/**
	 * 根据目标系统代码获取组织机构列表
	 * 
	 * 调用示例：
	 * http://168.168.7.8:8080/bim-server/rest/TargetOrganizationService/findBySystemCode.json?systemCode=CAR&useDefaultFilters=true
	 */
	public void findBySystemCode(){
		String data = "";
		if("true".equals(success)){
			data = readFileContent("org_list_success.json");
		}else{
			data = readFileContent("org_list_error.json");

		}
		
		Struts2Utils.renderHtml(data);
	}
}
