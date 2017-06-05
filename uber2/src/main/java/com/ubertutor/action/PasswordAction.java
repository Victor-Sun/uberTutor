package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.service.ChangePasswordService;
import com.ubertutor.service.LoginService;

@Namespace("/main")
public class PasswordAction extends ActionSupport{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private LoginService loginService;
    @Autowired
    private ChangePasswordService passwordService;
    
    public void updatePassword(){
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg, p1 = "", p2 = "", currentPassword = "";
			currentPassword = Struts2Utils.getRequest().getParameter("currentpassword");
			p1 = Struts2Utils.getRequest().getParameter("newpassword");
			p2 = Struts2Utils.getRequest().getParameter("newpassword2");
			String username = SessionData.getUserId();
			String userId = SessionData.getLoginUserId();
			if(!loginService.verifyUserPassword(username, EncryptUtil.encrypt(currentPassword))){
				msg = "Password is incorrect! Please confirm your password and try again.";
				throw new Exception(msg);
			}
			if(!p1.equals(p2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
			passwordService.updatePassword(userId, p1);
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
