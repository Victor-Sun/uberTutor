package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.alibaba.fastjson.JSON;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.LoginService;
import com.ubertutor.service.PasswordService;

@Namespace("/main")
public class PasswordAction extends ActionSupport{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordService passwordService;
    private UserEntity entity;
    
    /**
     * Update Password function
     */
    public void updatePassword(){
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg, p1 = "", p2 = "", currentPassword = "";
			currentPassword = Struts2Utils.getRequest().getParameter("currentpassword");
			p1 = Struts2Utils.getRequest().getParameter("newpassword");
			p2 = Struts2Utils.getRequest().getParameter("newpassword2");
			String username = SessionData.getUserId();
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			if(!loginService.verifyUserPassword(username, EncryptUtil.encrypt(currentPassword))){
				msg = "Password is incorrect! Please confirm your password and try again.";
				throw new Exception(msg);
			}
			if(!p1.equals(p2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
			entity = passwordService.get(userId);
			entity.setPassword(EncryptUtil.encrypt(p1));
			entity.setUpdateBy(userId.toString());
			entity.setUpdateDate(new Date());
			passwordService.updatePassword(entity);
			this.writeSuccessResult(resultMap);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
    }
    
	protected void writeSuccessResult(Object data) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (null != data) {
			resultMap.put("data", data);
		}
//		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			// Struts2Utils.getResponse().getWriter().write(jsonObject.toString());
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e);
		}
	}

	protected void writeErrorResult(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (null != data) {
			resultMap.put("data", data);
		}
//		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		try {
			// Struts2Utils.getResponse().getWriter().write(jsonObject.toString());
			Struts2Utils.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e);
		}
	}
}
