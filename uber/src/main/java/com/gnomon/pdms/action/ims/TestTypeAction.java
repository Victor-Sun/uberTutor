package com.gnomon.pdms.action.ims;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.TestTypeEntity;
import com.gnomon.pdms.service.TestTypeService;

@Namespace("/testtype")
public class TestTypeAction extends PDMSCrudActionSupport<TestTypeEntity> {

	private static final long serialVersionUID = 1L;
	private TestTypeEntity testTypeEntity;
	
	@Override
	public TestTypeEntity getModel() {
		return testTypeEntity;
	}
	@Autowired
	private TestTypeService testTypeService;
	
	public String id;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		testTypeService.save(testTypeEntity);
		this.writeSuccessResult(null);
		return null;
	}

	@Override
	public String delete() throws Exception {
		try {
			testTypeService.delete(id);
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
			testTypeEntity = new TestTypeEntity();
		}else{
			testTypeEntity = testTypeService.getTestType(id);
		}
		
	}
	
//	public void getPartList() {
//
//		try{
//			JsonResult result = new JsonResult();
//			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//			GTPage<Map<String, Object>> pageResult = this.imsPartService.getImsPartList(this.getPage(), this.getLimit());
//			for (Map<String, Object> map : pageResult.getItems()) {
//				Map<String, Object> dataMap = new HashMap<String, Object>();
//				dataMap.put("code", map.get("CODE"));
//				dataMap.put("name", map.get("NAME"));
//				dataMap.put("createBy", map.get("CREATE_BY"));
//				dataMap.put("createDate", map.get("CREATE_DATE"));
//				dataMap.put("updateBy", map.get("UPDATE_BY"));
//				dataMap.put("updateDate", map.get("UPDATE_DATE"));
//				dataMap.put("deleteBy", map.get("DELETE_BY"));
//				dataMap.put("deleteDate", map.get("DELETE_DATE"));
//				dataMap.put("groupNo", map.get("GROUP_NO"));
//				dataMap.put("systemNo", map.get("SYSTEM_NO"));
//				dataMap.put("systemName", map.get("SYSTEM_NAME"));
//				dataMap.put("groupName", map.get("GROUP_NAME"));
//				
//				data.add(dataMap);
//			}
//			
//			// 结果返回
//			result.buildSuccessResultForList(data, pageResult.getItemCount());
//			Struts2Utils.renderJson(result);
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	
}
