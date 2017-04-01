package com.gnomon.pdms.action.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.system.entity.SysDepartmentEntity;
import com.gnomon.common.system.service.SysDepartmentService;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;

@Namespace("/sys")
public class SysDepartmentAction extends PDMSCrudActionSupport<SysDepartmentEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	


	private SysDepartmentEntity entity;

	private String id;



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public void getSysDepartmentList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> page = sysDepartmentService.getSysDepartmentList(this.getPage(),this.getLimit());
			for(Map<String, Object> map : page.getItems()){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", map.get("ID"));
				data.put("name", map.get("NAME"));
				data.put("ownerName", map.get("OWNER_NAME"));
				data.put("owner", map.get("OWNER"));
				data.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				resultList.add(data);
			}
			

			result.buildSuccessResultForList(resultList,page.getItemCount());
			Struts2Utils.renderJson(result);
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
		try {
			if(StringUtils.isEmpty(id)){
				entity.setId(UUID.randomUUID().toString());
			}
			sysDepartmentService.save(entity);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String delete() throws Exception {
		try {
			sysDepartmentService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void prepareModel() throws Exception {
		if(StringUtils.isEmpty(id)){
			entity = new SysDepartmentEntity();
			entity.setCreateDate(new Date());
			entity.setIsLeaf("Y");
		}else{
			entity = sysDepartmentService.get(id);
		}
		
	}

	@Override
	public SysDepartmentEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	



}
