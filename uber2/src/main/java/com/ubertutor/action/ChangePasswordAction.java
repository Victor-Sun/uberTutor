package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/main")
public class ChangePasswordAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	//TODO Check user current password(Based on session? How to get username?)
	//Then change

	public void changePassword() throws Exception{
		try{
		String user = SessionData.getUserId();
		System.out.println(user);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	private void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			Struts2Utils.renderHtml(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
