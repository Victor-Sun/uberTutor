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
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.MergeInfoEntity;
import com.gnomon.pdms.service.MainInfoService;

@Namespace("/ims")
public class MainInfoAction extends PDMSCrudActionSupport<GTIssueEntity>{

	private static final long serialVersionUID = 1L;

	private GTIssueEntity gtIssueEntity;
	
	@Override
	public GTIssueEntity getModel() {
		return gtIssueEntity;
	}

	@Autowired
	private MainInfoService mainInfoService;
	
	private String mergeId;

	public String getMergeId() {
		return mergeId;
	}

	public void setMergeId(String mergeId) {
		this.mergeId = mergeId;
	}

	/**
	 * 通过被合并问题ID查找唯一主问题
	 */
	public void getMainInfo(){
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			
			if(mergeId != null){
				List<MergeInfoEntity> list =
					this.mainInfoService.getMainId(mergeId);
				if(list != null){
					for(int i=0;i<list.size();i++){
					GTIssueEntity entity = 
							this.mainInfoService.getMainInfo(((MergeInfoEntity) list.get(0)).getPrimaryIssueId());
					
					if(entity != null){

						Map<String, Object> dataMap = new HashMap<String, Object>();

						//ID
						dataMap.put("id", entity.getId());
						//标题
						dataMap.put("mainQeTitle", entity.getTitle());
						//提出人
//						dataMap.put("mainQePeople", entity.getSubmitUserName());
						//提出时间
						if (entity.getOccurrenceDate() != null) {
							dataMap.put("mainQeTime",
									DateUtils.formate(entity.getOpenDate()));
						}else{
							dataMap.put("mainQeTime", "");
						}
						//编号
						dataMap.put("mainQeNum", entity.getCode());
			
						data.add(dataMap);
						// 结果返回
						result.buildSuccessResultForList(data, 1);
						Struts2Utils.renderJson(result);
						}
					}
				}
			}
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
