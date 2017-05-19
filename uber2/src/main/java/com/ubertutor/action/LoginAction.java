package com.ubertutor.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.entity.UserEntity;
import com.gnomon.common.system.service.SysUserDepartmentService;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.EncryptUtil;
import com.gnomontech.pdms.redis.OnlineUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.service.LoginService;

@Namespace("/main")
public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;
	
	private String username, password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
		
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * Login处理
	 */
	public void login() throws Exception {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String msg; 
					
			// 用户ID存在验证
			if (!loginService.verifyUserId(username)) {
				msg = "用户名不存在！";
				throw new Exception(msg);
			}
			// 用户密码验证
			if (!loginService.verifyUserPassword(username, EncryptUtil.encrypt(password))) {
				msg = "用户密码错误！";
				throw new Exception(msg);
			}
			
			// 用户信息取得
			UserEntity loginUser = this.loginService.getUser(username);
			
			//禁用的用户不允许登录
			//TODO Change code to make it better
			if("Y".equals(loginUser.getIsDisabled())){
				msg = "用户已被禁用，不允许登录！";
				throw new Exception(msg);
			}
			
			// Session-用户信息
			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_USER, loginUser);
			if(OnlineUtils.isUseRedis()){
				if(OnlineUtils.isOnline(username)){
					OnlineUtils.logout(username);
				}
				OnlineUtils.login(Struts2Utils.getSession().getId(), loginUser);
			}
			
			// Session-用户部门信息
			// 系统用户信息取得
			//TODO Figure out how to incorporate this code into uber2, is this code needed?
			
//			Map<String, Object> deptUserInfo = this.sysUserDepartmentService.getDeptUserInfo(loginUser.getId());
//			Struts2Utils.getSession().setAttribute(SessionData.KEY_LOGIN_DEPT, deptUserInfo);
//			
//			loginService.saveLoginLog(loginUser.getId());
			
			resultMap.put("userName", loginUser.getUsername());
			//TODO Ask why don't use "this" here
			writeSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/*
	 * Logout处理
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
	
	public void downloadHelpDocument() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/PMS用户操作手册.docx");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			// filename = "attachment; filename=" + filename + ".xls";
			response.setHeader("Content-disposition","attachment; filename="+java.net.URLEncoder.encode("PMS用户操作手册.docx", "UTF-8"));// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			try {
				int l = -1;
				byte[] tmp = new byte[1024];
				while ((l = is.read(tmp)) != -1) {
					os.write(tmp, 0, l);
					// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
				}
				os.flush();
				os.close();
			} finally {
				// 关闭低层流。
				is.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}