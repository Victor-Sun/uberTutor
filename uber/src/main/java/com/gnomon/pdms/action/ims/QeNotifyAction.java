package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.VImsIssueEntity;
import com.gnomon.pdms.entity.VImsIssueNotificationEntity;
import com.gnomon.pdms.service.MyTaskService;
import com.gnomon.pdms.service.QeNotifyService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/ims")
public class QeNotifyAction extends PDMSCrudActionSupport<VImsIssueNotificationEntity>{

	private static final long serialVersionUID = 1L;
	
	private VImsIssueNotificationEntity vImsIssueNotificationEntity;
	
	@Autowired
	private QeNotifyService qeNotifyService;

	@Override
	public VImsIssueNotificationEntity getModel() {
		return vImsIssueNotificationEntity;
	}

	private String keyId;
	private String editModel;
	
	public void setEditModel(String editModel) {
		this.editModel = editModel;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	/**
	 * 知会查询
	 */
	public void getNotifyList(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<VImsIssueNotificationEntity> list = this.qeNotifyService.getNotifyList(keyId);

		for(VImsIssueNotificationEntity entity : list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//ID
			dataMap.put("CurrentId", entity.getMemberUserid());
			//NAME
			dataMap.put("CurrentName", entity.getToUserName());
			data.add(dataMap);
		}
		// 结果返回
		result.buildSuccessResultForList(data, 1);
		Struts2Utils.renderJson(result);
	}
	/**
	 * 知会存储
	 */
	public void saveNotifyList(){
		try{
			List<Map<String, String>> modelList = this.convertJson2List(this.editModel);
			this.qeNotifyService.saveNotify(keyId, modelList);
			// 结果返回
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
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
