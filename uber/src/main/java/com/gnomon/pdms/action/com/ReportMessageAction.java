package com.gnomon.pdms.action.com;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.ReportManager;

@Namespace("/com")
public class ReportMessageAction extends PDMSCrudActionSupport<Object> {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportManager reportManager;
	
	public void getReportMessage() {
		try {
			JsonResult result = new JsonResult();
			String version = this.reportManager.getReportMessage();
			String contact = this.reportManager.getContact();
			String copyright = this.reportManager.getCopyright();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("version", version);
			data.put("contact", contact);
			data.put("copyright", copyright);
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
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
