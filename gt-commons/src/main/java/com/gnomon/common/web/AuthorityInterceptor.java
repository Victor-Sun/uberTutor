package com.gnomon.common.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.sso.OrgUserService;
import com.gnomon.intergration.sso.SSORole;
import com.gnomon.intergration.sso.SSOService;
import com.gnomon.intergration.sso.SSOUserInfo;
import com.gnomon.intergration.sso.UserContext;
import com.gnomontech.pdms.redis.OnlineUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final String SESSION_USER_CONTEXT = "USER_CONTEXT";


	/**
	 * 
	 */
	// private static final String USER_AUTHORITY_MAP = "userAuthorityMap";

	Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

	private static final long serialVersionUID = 1L;

	protected static final String ACCESS_FORBID = "accessForbid";
	
	private String requestURL;
	private HttpServletRequest request;
	

	// private String businessPort
	// =System.getProperty("businessPort",AWSConfig.getConfigValue(AWSConfig.BUSINESS_PORT));

	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	@SuppressWarnings("serial")
	public final Map<String, Locale> localeMap = new HashMap<String, Locale>() {
		{
			put("cn", Locale.SIMPLIFIED_CHINESE);
			put("big5", Locale.TRADITIONAL_CHINESE);
			put("en", Locale.US);
		}
	};

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private SSOService ssoService;

//	@Autowired
//	private ResourceService resourceService;

//	/**
//	 * 当前用户是否是管理员
//	 * 
//	 * @return
//	 */
//	private Boolean isAdmin() {
//		return (Boolean) ServletActionContext.getRequest().getSession().getAttribute(UserContext.KEY_IS_ADMIN);
//	}

	/**
	 * 当前用户的部门ID
	 * 
	 * @return
	 */
	public String getUserDepartmentId() {
		return (String) ServletActionContext.getRequest().getSession().getAttribute(UserContext.KEY_DEPARTMENT_ID);

	}

	/**
	 * 当前用户的角色IDs
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUserRoleIds() {
		return (List<String>) ServletActionContext.getRequest().getSession().getAttribute(UserContext.KEY_ROLE_IDS);
	}
	
	public SSOUserInfo getUserInfo(){
		return (SSOUserInfo) ServletActionContext.getRequest().getSession().getAttribute(UserContext.KEY_USER_INFO);
	}

	// 重新登录
	private String toLogin() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (!isAjaxRequest(request)) {
			String path = request.getContextPath();  
	        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
	        response.sendRedirect(basePath+"login_aws.jsp");  
			// 没有登录，转向到登录页面
			return null;
		}else{
			response.setHeader("__timeout", "true");
			response.getWriter().write("{success:false,msg:'没有登录或操作超时，请重新登录!'}");
		}
		cleanSession();//add by frank
		return null;
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		this.request = request;
		
		// Set RequestURL to session, skip login url
		String requestURL = request.getRequestURI();
		this.requestURL = requestURL;
		// 登录请求不做验证
		if (requestURL.equals("/pdms/main/login!login.action")) {
			return actionInvocation.invoke();
		}
		if(OnlineUtils.isUseRedis()){
			String userId = SessionData.getLoginUserId();
			boolean isOnline = OnlineUtils.isOnline(userId,request.getSession().getId());
			if(isOnline){
				return actionInvocation.invoke();
			}else{
				this.relogin(request);
			}
		}
		return actionInvocation.invoke();

	}
	
	/**
	 * 不进行身份认证的URL
	 * @return
	 */
	protected String[] getIgnoreUrls() {
		return new String[]{
				"/6dPPM_Web/common/document/dms!downloadLastVersion.action",
				"/ts_dms/dms/document!downloadLastVersion.action",
				"/ts_dms/dms/document!downloadLastVersionForPdf.action"};
	}

	/**
	 * 重新登录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String relogin(HttpServletRequest request) throws Exception{
		if (isAjaxRequest(request)) {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("__timeout", "true");
			response.getWriter().write("{success:false,msg:'身份认证失败，请重新登录!'}");
			return null;
		} else {
			return Action.LOGIN;
		}
	}
	
	protected String afterLogin() throws Exception{
		return null;
	}
	

	/**
	 * 从Session获取已认证用户的SID
	 * 
	 * @param session
	 * @return
	 */
	private String getSIDFromSession(HttpSession session) {
		if(UserContext.getUserInfo()  == null){
			return null;
		}
		String sid = (String) session.getAttribute("aws-sid");
//		if(sid != null && UserContext.getContext() != null){
//			if(sid.equals(UserContext.getSessionId())){
//				saveLoginStatusToSession(sid);
//			}
//		}
		return sid;
	}

	public boolean isAjaxRequest(HttpServletRequest request) throws IOException {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	private void cleanSession() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			session.removeAttribute(name);
		}
//		session.removeAttribute("aws-uname");
//		session.removeAttribute("aws-uid");
//		session.removeAttribute("aws-sid");
//		session.removeAttribute("WW_TRANS_I18N_LOCALE");

//		UserContext.clear();

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
	public String getUsername(String userid) {
		// 取用户名
		SSOUserInfo userInfo = orgUserService.getUserInfo(userid);

		String username = userInfo.getUserName();
		return username;
	}

	private boolean checkSession(String userId, String sessionId) {

		logger.debug("验证开始");
//		boolean isOk = ssoService.checkSession(userId, sessionId);
		boolean isOk = AwsApi.getInstance().getSession().checkSession(userId, sessionId);
		logger.debug("验证结束" + isOk);
		return isOk;
	}
	

	private void saveLoginStatusToSession(String sessionId) {

		// 获取UID
		String userId = getUIDBySID(sessionId);

		String userName = this.getUsername(userId);

		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("aws-uname", userName);

		session.setAttribute("aws-uid", userId);
		session.setAttribute("aws-sid", sessionId);

		session.setAttribute("WW_TRANS_I18N_LOCALE", getLocaleBySID(sessionId));
//		ActionContext.getContext().setLocale(Locale.US);
		ActionContext.getContext().setLocale(getLocaleBySID(sessionId));   
//		UserContext context = UserContext.getContext();
		
		SSOUserInfo userInfo = orgUserService.getUserInfo(userId);
		
		session.setAttribute(UserContext.KEY_USER_INFO, userInfo);
		
		session.setAttribute(UserContext.KEY_SESSION_ID, sessionId);
		
		String userDepartmentId = userInfo.getDepartmentId();
		List<SSORole> userRoleList = orgUserService.getUserAllRoleList(userId);
		List<String> userRoleIds = new ArrayList<String>();
		for (SSORole role : userRoleList) {
			userRoleIds.add(role.getId());
		}

//		context.setAttribute(IS_ADMIN, isAdmin);
//		context.setAttribute(DEPARTMENT_ID, userDepartmentId);
//		context.setAttribute(ROLE_IDS, userRoleIds);

		session.setAttribute(UserContext.KEY_IS_ADMIN, userInfo.isAdmin());
		session.setAttribute(UserContext.KEY_DEPARTMENT_ID, userDepartmentId);
		session.setAttribute(UserContext.KEY_ROLE_IDS, userRoleIds);
		session.setAttribute(UserContext.KEY_ROLES, userRoleList);
	}

	/**
	 * 根据SessionId获取语言环境
	 * 
	 * @param sessionId
	 * @return
	 */
	private Locale getLocaleBySID(String SSO_SID) {
		String localeStr = "";
		if (SSO_SID.indexOf("{") > 0 && SSO_SID.indexOf("}") > 0) {
			localeStr = SSO_SID.substring(SSO_SID.indexOf("{") + 1, SSO_SID.indexOf("}"));
		}
		if (localeMap.get(localeStr) != null) {
			return localeMap.get(localeStr);
		} else {
			return localeMap.get("cn");
		}
	}
}