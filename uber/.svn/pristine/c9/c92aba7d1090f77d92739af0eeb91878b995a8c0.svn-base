package com.gnomon.pdms.action.com;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.SysUserDepartmentService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;

@Namespace("/com")
public class SessionDataAction extends PDMSCrudActionSupport<SessionData> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;

	/**
	 * LoginUser信息取得
	 */
	public void getLoginUserInfo() {
		try {
			JsonResult result = new JsonResult();
			Map<String, Object> data = new HashMap<String, Object>();
			// 用户ID
			data.put("userId", SessionData.getLoginUserId());
			// 用户姓名
			data.put("userName", SessionData.getLoginUser().getUsername());
			// 系统用户信息取得
			Map<String, Object> userInfo =
					this.sysUserDepartmentService.getDeptUserInfo(
							SessionData.getLoginUserId());
			// 用户所在科室ID
			data.put("departmentId", userInfo.get("DEPARTMENT_ID"));
			// 用户所在科室
			data.put("departmentName", userInfo.get("DEPARTMENT_NAME"));
			// 用户所在部门ID
			data.put("topDepartmentId", userInfo.get("TOP_DEPARTMENT_ID"));
			// 用户所在部门
			data.put("topDepartmentName", userInfo.get("TOP_DEPARTMENT_NAME"));
			// 结果返回
			result.buildSuccessResult(data);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionData getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
