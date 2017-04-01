package com.gnomon.pdms.action.ims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.MergeInfoEntity;
import com.gnomon.pdms.service.MainInfoService;

@Namespace("/ims")
public class MergeInfoAction extends PDMSCrudActionSupport<MergeInfoEntity>{

	private static final long serialVersionUID = 1L;
	
	private MergeInfoEntity mergeInfoEntity;

	@Autowired
	private MainInfoService mainInfoService;
	
	private String mergeId;

	public String getMergeId() {
		return mergeId;
	}

	public void setMergeId(String mergeId) {
		this.mergeId = mergeId;
	}

	@Override
	public MergeInfoEntity getModel() {
		return mergeInfoEntity;
	}

	/**
	 * 质量问题管理-合并信息-一览数据取得
	 */
	public void getMergeInfo(){
		JsonResult result = new JsonResult();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		
		if(mergeId != null){
			List<MergeInfoEntity> mainList =
					this.mainInfoService.getMainId(mergeId);
			for(int i=0;i<mainList.size();i++){
				List<MergeInfoEntity> mergeList = 
					this.mainInfoService.getMergeInfo(((MergeInfoEntity) mainList.get(0)).getPrimaryIssueId());
			
				for(MergeInfoEntity entity : mergeList){
					Map<String,Object> dataMap = new HashMap<String,Object>();
					//ID
					dataMap.put("id", entity.getId());
					//问题编号
					dataMap.put("qeNum", entity.getCode());
					//问题标题
					dataMap.put("qeTitle", entity.getTitle());
					//问题提出人
					dataMap.put("qePeople", entity.getSubmitUserName());
					//问题提出时间
					if(entity.getOpenDate() != null) {
						dataMap.put("qeTime", DateUtils.formate(entity.getOpenDate()));
					}else{
						dataMap.put("qeTime","");
					}
				
					data.add(dataMap);
				}	
				// 结果返回
				result.buildSuccessResultForList(data, 1);
				Struts2Utils.renderJson(result);
			}
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
