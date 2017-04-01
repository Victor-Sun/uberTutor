package com.gnomon.pdms.action.ims;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsIssueMarkEntity;
import com.gnomon.pdms.service.ImsIssueMarkService;

@Namespace("/ims")
public class ImsIssueMarkAction extends PDMSCrudActionSupport<ImsIssueMarkEntity>{

	private static final long serialVersionUID = 1L;

	private ImsIssueMarkEntity imsIssueMarkEntity;
	
	@Override
	public ImsIssueMarkEntity getModel() {
		return imsIssueMarkEntity;
	}
	
	@Autowired
	private ImsIssueMarkService imsIssueMarkService;
	
	private String keyId;
	private String isConcern;
	
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getIsConcern() {
		return isConcern;
	}

	public void setIsConcern(String isConcern) {
		this.isConcern = isConcern;
	}
	
	//问题标记——关注
	public void saveImsIssueMark(){
		try {
			JsonResult result = new JsonResult();
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			List<ImsIssueMarkEntity> list = this.imsIssueMarkService.getImsIssueMark(keyId,userId);
			
			if(list.size() != 0){
				for(ImsIssueMarkEntity entity : list){
					entity.setIsConcern(isConcern);
					if("N".equals(isConcern)){
						entity.setDeleteBy(userId);
						entity.setDeleteDate(new Date());
					}else if("Y".equals(isConcern)){
						entity.setDeleteBy("");
						entity.setDeleteDate(null);
					}
					entity.setUpdateBy(userId);
					entity.setUpdateDate(new Date());
					
					imsIssueMarkService.save(entity);
				}
			}else{
				ImsIssueMarkEntity entity = new ImsIssueMarkEntity();
				String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
				entity.setId(uuid);
				entity.setIssueId(keyId);
				entity.setUserId(userId);
				entity.setIsConcern(isConcern);
				entity.setCreateBy(userId);
				entity.setCreateDate(new Date());
				entity.setUpdateBy(userId);
				entity.setUpdateDate(new Date());
				
				imsIssueMarkService.save(entity);
			}
			
			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);
			} catch (Exception ex) {
				ex.printStackTrace();
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
