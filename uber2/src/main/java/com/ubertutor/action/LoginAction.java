package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.SysUserDepartmentService;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomontech.pdms.redis.OnlineUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.LoginService;

@Namespace("/main")
public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;
	
	private String username, password;
	
	/**
	 * 
	 * @return Username as String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 
	 * @return password as String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Login function
	 * @throws Exception
	 */
	public void login() throws Exception {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg; 
			if (!loginService.verifyUserId(username)) {
				msg = "User does not exist";
				throw new Exception(msg);
			}
			if (!loginService.verifyUserPassword(username, EncryptUtil.encrypt(password))) {
				msg = "Username and password combination is incorrect! Try again!";
				throw new Exception(msg);
			}
			UserEntity entity = this.loginService.getUser(username);
			if("Y".equals(entity.getIsDisabled())){
				msg = "User has been disabled!";
				throw new Exception(msg);
			}
			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_USER, entity);
			if(OnlineUtils.isUseRedis()){
				if(OnlineUtils.isOnline(username)){
					OnlineUtils.logout(username);
				}
				OnlineUtils.login(Struts2Utils.getSession().getId(), entity);
			}
			resultMap.put("userName", entity.getUsername());
			writeSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * Logout function
	 */
	public void logout() {
		try {
			Struts2Utils.getRequest().getSession().invalidate();
			this.writeSuccessResult(null);
		} catch (Exception e) {
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