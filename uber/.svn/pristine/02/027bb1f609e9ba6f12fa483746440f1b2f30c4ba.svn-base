package com.gnomon.pdms.action.ims;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.service.ListingAreaService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/ims")
public class ListingAreaAction extends PDMSCrudActionSupport<GTIssueEntity> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ListingAreaService listingAreaService;
	
	@Autowired
	private SysNoticeService sysNoticeService;

	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	private String issueId;
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	/**
	 * 挂牌
	 */
	public void listIssue() {
		try {
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
			this.listingAreaService.listIssue(issueId, model);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}
	
	/**
	 * 摘牌
	 */
	public void delListing() {
		try{
			this.listingAreaService.delListing(issueId);
			// 给挂牌人发送通知
			this.sysNoticeService.delListingIssueNotice(issueId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 取消挂牌
	 */
	public void undoListing() {
		try{
			this.listingAreaService.undoListIssue(issueId);
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

	@Override
	public GTIssueEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
