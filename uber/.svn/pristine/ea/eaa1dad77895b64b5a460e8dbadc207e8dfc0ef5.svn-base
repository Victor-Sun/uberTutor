package com.gnomon.pdms.action.com;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.service.ReportManager;

@Namespace("/com")
public class ReportAction extends PDMSCrudActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportManager reportManager;

	public void getReportAddress() {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("reportAddress", this.reportManager.getReportUrl());
			Object userId = SessionData.getLoginUserId();
			data.put("userId", userId);
			this.writeSuccessResult(data);
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
