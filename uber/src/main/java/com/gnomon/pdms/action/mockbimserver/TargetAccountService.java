package com.gnomon.pdms.action.mockbimserver;

import org.apache.struts2.convention.annotation.Namespace;
import org.springside.modules.utils.web.struts2.Struts2Utils;

@Namespace("/mockbimserver")
public class TargetAccountService extends MockBimService{
	private String systemCode;
	private String useDefaultFilters;
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public void setUseDefaultFilters(String useDefaultFilters) {
		this.useDefaultFilters = useDefaultFilters;
	}



	/**
	 * 根据目标系统代码获取账号列表
	 * 调用示例：
	 * http://168.168.7.8:8080/bim-server/rest/TargetAccountService/findBySystemCode.json?includeIsDisabled=true&includeIsDeleted=true&systemCode=CAR
	 */
	public void findXAttrBySystemCode(){
		String data = "";
		if("true".equals(success)){
			data = readFileContent("account_list_success.json");
		}else{
			data = readFileContent("account_list_error.json");

		}
		
		Struts2Utils.renderHtml(data);
		
	}
	
	/**
	 *根据目标系统ID和创建时间/更新时间获取账号列表
	 *调用示例：
	 *http://168.168.7.8:8080/bim-server/rest/TargetAccountService/findBy.json?search.systemId_eq=055CF2C39DD745009F28D1301F67D5A5search.createAt_gte=2016-07-07 00:00:00
	 */
	public void findBy(){
		String data = "";
		if("true".equals(success)){
			data = readFileContent("account_list_success.json");
		}else{
			data = readFileContent("account_list_error.json");

		}
		
		Struts2Utils.renderHtml(data);
	}
	
	/**
	 * 按账号ID获取账号明细
	 */
	public void getDetailById(){
		
	}
}
