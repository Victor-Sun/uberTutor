package com.gnomon.intergration.sso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gnomon.intergration.sso.SSOService;
import com.gnomon.intergration.sso.UserContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class GTAuthorizationUCMInterceptor extends AbstractInterceptor {
	Logger logger = LoggerFactory.getLogger(GTAuthorizationUCMInterceptor.class);

	private static final long serialVersionUID = 1L;

//	private String businessPort =System.getProperty("businessPort",AWSConfig.getConfigValue(AWSConfig.BUSINESS_PORT));
	
	@SuppressWarnings("serial")
	public final Map<String, Locale> localeMap = new HashMap<String, Locale>() {
		{
			put("cn", Locale.SIMPLIFIED_CHINESE);
			put("big5", Locale.TRADITIONAL_CHINESE);
			put("en", Locale.US);
		}
	};
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	@Autowired
//	private SSOService ssoService;
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		String UID = (String) request.getSession().getAttribute("ORACLE_UID");
		if(StringUtils.isEmpty(UID)){
			UID = request.getParameter("uid");
			if(UID == null){
				UID = "test";//默认是test用户
			}
			request.getSession().setAttribute("ORACLE_UID", UID);
			SSOUserInfo userInfo = new SSOUserInfo();
			userInfo.setUserId("test");
			request.getSession().setAttribute(UserContext.KEY_USER_INFO, userInfo);
		}
		
//		UserContext.setUserContext(UID, UID, UID);
		return actionInvocation.invoke();
//		//Set RequestURL to session, skip login url
//		String requestURL = request.getRequestURI();
//		if(requestURL.indexOf("account/login!login.action") > 0){
//			return actionInvocation.invoke();
//		}else{			
//			if(!StringUtils.isEmpty(request.getQueryString())){
//				requestURL = requestURL + "?"+request.getQueryString();
//			}
//			request.getSession().setAttribute("requestURL", requestURL);
//		}
//		
//		String SSO_SID = (String) request.getSession().getAttribute("aws-sid");
//		String SSO_SID_REQ = request.getParameter("sid");
//		
//		//如果登录用户变更，清除Session中原有用户信息
//		if(SSO_SID_REQ != null && SSO_SID != null && !SSO_SID.equals(SSO_SID_REQ)){
//			cleanSession();
//			SSO_SID = null;
//			logger.debug("登录用户变更，清除Session中原有用户信息,Session:"+SSO_SID+"Req:"+SSO_SID_REQ);
//		}
//		
//		String SSO_UID = "";
//		if (SSO_SID != null) {
//			//获取UID
//			SSO_UID = SSO_SID;//getUIDBySID(SSO_SID);
//			String userName = (String) request.getSession().getAttribute("aws-uname");
//			UserContext.addUserContext(SSO_UID, userName, SSO_SID);
//			logger.debug("Session中有用户信息:"+SSO_SID);
//			return actionInvocation.invoke();
//		} else {//session过期或者没有登录
//			
//			logger.debug("session过期或者没有登录:"+SSO_SID);
//			
//			if(SSO_SID_REQ != null){
//				SSO_SID = SSO_SID_REQ;
//				
//				//获取UID
//				SSO_UID = getUIDBySID(SSO_SID);
//
//				// 验证，返回验证结果
//				boolean isOk = true;//checkSession(SSO_UID,SSO_SID);
//
//				if (isOk == true) {
//					saveLoginStatusToSession(SSO_UID,SSO_SID);
//					
//					return actionInvocation.invoke();
//
//				} else {
//					//身份认证失败，转向到登录页面
//					logger.debug("身份认证失败，转向到登录页面:"+SSO_SID);
//					return Action.LOGIN;
//
//				}
//			}else{
//				//没有登录，转向到登录页面
//				logger.debug("没有登录，转向到登录页面:"+SSO_SID);
//				return Action.LOGIN;
//			}
//		}
			
	}

	/**
	 * 
	 */
	private void cleanSession() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("aws-uname");
		session.removeAttribute("aws-uid");
		session.removeAttribute("aws-sid");
		session.removeAttribute("WW_TRANS_I18N_LOCALE");
		
	}

	/**
	 * @param sSO_SID
	 * @return
	 */
	private String getUIDBySID(String SSO_SID) {
		String SSO_UID = "";
		if (SSO_SID.indexOf("_") > 0) {
			SSO_UID = SSO_SID.substring(0, SSO_SID.indexOf("_"));
		}
		return SSO_UID;
	}

	/**
	 * 根据用户帐号获取用户名
	 * 
	 * @param userid
	 *            用户帐号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUsername(String userid) {
		// 取用户名
		String username = "";
		String sql = "select username from orguser where userid = '" + userid
				+ "'";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map map : list) {
			username = map.get("username").toString();
			break;
		}
		return username;
	}
//	
//	private boolean checkSession(String userId, String sessionId){
//		logger.debug("验证开始:"+sessionId);
//		//boolean isOk = true;//TODO  暂时不进行身份认证
//		boolean isOk = ssoService.checkSession(userId, sessionId);
//		logger.debug("验证结束:"+isOk+","+sessionId);
//		return isOk;
//	}
//	

	private void saveLoginStatusToSession(String userId,String sessionId){
		String userName = this.getUsername(userId);

		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("aws-uname", userName);

		session.setAttribute("aws-uid", userId);
		session.setAttribute("aws-sid", sessionId);

		session.setAttribute("WW_TRANS_I18N_LOCALE", getLocaleBySID(sessionId));
		
//		UserContext.setUserContext(userId, userName, sessionId);
	}

	/**
	 * 根据SessionId获取语言环境
	 * @param sessionId
	 * @return
	 */
	private Locale getLocaleBySID(String SSO_SID) {
		String localeStr = "";
		if (SSO_SID.indexOf("{") > 0 && SSO_SID.indexOf("}") > 0) {
			localeStr = SSO_SID.substring(SSO_SID.indexOf("{")+1, SSO_SID.indexOf("}"));
		}
		if(localeMap.get(localeStr) != null){
			return localeMap.get(localeStr);
		}else{
			return localeMap.get("cn");
		}
	}
}
