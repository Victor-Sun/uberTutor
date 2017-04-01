package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMProgramStageEntity;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.service.ProjectPlanStageService;

@Namespace("/pm")
public class ProjectPlanStageAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 1L;

	private ProgramEntity programEntity;
	
	@Autowired
	private ProjectPlanStageService projectPlanStageService;
	
	@Override
	public ProgramEntity getModel() {
		return programEntity;
	}
	
	private String keyId;

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	private Date date1;
	private Date date2;
//	private int date3;
	public static int getMonthNum(Date date1,Date date2)
    {
         Calendar cal1=Calendar.getInstance();
             cal1.setTime(date1);
         Calendar cal2=Calendar.getInstance();
             cal2.setTime(date2);
            return (cal2.get(1)-cal1.get(1))*12+(cal2.get(2)-cal1.get(2));
    }

	/**
	 * 【项目管理-项目列表-计划-主计划-阶段】一览数据取得
	 */
	public void getProjectPlanStage(){
		try{	
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<PMProgramStageEntity> list =
					this.projectPlanStageService.getProjectPlanStage(keyId);
			for(PMProgramStageEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//阶段名称
				dataMap.put("stageName", entity.getName());
				//开始时间
				dataMap.put("stDate", DateUtils.change(entity.getBeginDate()));
				//结束时间
				dataMap.put("edDate", DateUtils.change(entity.getEndDate()));
				date1 = entity.getBeginDate();
				date2 = entity.getEndDate();
				int date3 = getMonthNum(date1, date2);
				//作业期间
				dataMap.put("hours", date3);
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		}catch (Exception ex) {
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
