package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.service.ChangePasswordService;
import com.ubertutor.service.LoginService;

public class ChangePasswordAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	@Autowired
	private LoginService loginService; 
	@Autowired
	private ChangePasswordService passwordService;
	
	public void changePassword() throws Exception{
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg, p1 = "", p2 = "", currentPassword = "";
			currentPassword = Struts2Utils.getRequest().getParameter("currentpassword");
			p1 = Struts2Utils.getRequest().getParameter("newpassword");
			p2 = Struts2Utils.getRequest().getParameter("newpassword2");
			String user = SessionData.getUserId();
			String userid = SessionData.getLoginUserId();

			if(!loginService.verifyUserPassword(user, EncryptUtil.encrypt(currentPassword))){
				msg = "Password is incorrect! Please confirm your password and try again.";
				throw new Exception(msg);
			}
			if(!p1.equals(p2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
			System.out.println(user + " " + userid);
			//TODO Think of a better way to update the password
//			entity.setPassword(EncryptUtil.encrypt(entity.getPassword()));
//			signupService.registerAccount(entity);
			passwordService.updatePassword(userid, p1);
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	public void writeSuccessResult(Object data) {
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
	
	public void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
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
