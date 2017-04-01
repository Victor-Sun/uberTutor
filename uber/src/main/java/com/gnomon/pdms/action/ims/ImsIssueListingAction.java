package com.gnomon.pdms.action.ims;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsIssueListingEntity;
import com.gnomon.pdms.service.ImsIssueListingService;

@Namespace("/ims")
public class ImsIssueListingAction extends PDMSCrudActionSupport<ImsIssueListingEntity> {

	private static final long serialVersionUID = 1L;
	private ImsIssueListingEntity imsIssueListingEntity;
	
	@Override
	public ImsIssueListingEntity getModel() {
		return imsIssueListingEntity;
	}
	@Autowired
	private ImsIssueListingService imsIssueListingService;
	
	private String keyId;
	
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	//点击挂牌
	public void Listing(){
		try {
			JsonResult result = new JsonResult();
			List<ImsIssueListingEntity> list = this.imsIssueListingService.getImsIssueListing(keyId);
			
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			if(list.size() == 0){
				ImsIssueListingEntity entity = new ImsIssueListingEntity();
				String uuid = com.gnomon.pdms.common.PDMSCommon.generateUUID();
				entity.setId(uuid);
				entity.setIssueId(keyId);
				entity.setListingBy(userId);
				entity.setListingDate(new Date());
				entity.setCreateBy(userId);
				entity.setCreateDate(new Date());
				entity.setUpdateBy(userId);
				entity.setUpdateDate(new Date());	
				imsIssueListingService.save(entity);
			}else{
				for(ImsIssueListingEntity entity : list){
					entity.setListingBy(userId);
					entity.setListingDate(new Date());
					entity.setUpdateBy(userId);
					entity.setUpdateDate(new Date());	
					imsIssueListingService.save(entity);
				}
			}

			Struts2Utils.renderJson(result);
			this.writeSuccessResult(null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	//点击取消挂牌
	public void CancelListing(){
		try {
			JsonResult result = new JsonResult();
			List<ImsIssueListingEntity> list = this.imsIssueListingService.getImsIssueListing(keyId);
			
			// 登录用户ID取得
			String userId = SessionData.getLoginUserId();
			if(list.size() != 0){
				for(ImsIssueListingEntity entity : list){	
					entity.setDeleteBy(userId);
					entity.setDeleteDate(new Date());		
					imsIssueListingService.save(entity);
				}
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
