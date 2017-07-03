package com.ubertutor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import jp.co.nec.flowlites.core.FLPage;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.PDMSCrudActionSupport;
import com.ubertutor.entity.FeedbackEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.SearchService;

@Namespace("/main")
public class SearchAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SearchService searchService;
	private UserRequestEntity entity;
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void displayRequests() throws Exception{
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.searchService.getRequests(this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("requestID", map.get("REQUEST_ID"));
				dataMap.put("createDate", map.get("CREATE_DATE"));
				dataMap.put("studentId", map.get("STUDENT_ID"));
				dataMap.put("studentName", map.get("STUDENT_NAME"));
				dataMap.put("tutorId", map.get("TUTOR_ID"));
				dataMap.put("tutorName", map.get("TUTOR_NAME"));
				dataMap.put("category", map.get("CATEGORY"));
				dataMap.put("subject", map.get("SUBJECT"));
				dataMap.put("status", map.get("STATUS"));
				dataMap.put("subjectDescription", map.get("SUBJECT_DESCRIPTION"));
				dataMap.put("requestTitle", map.get("REQUEST_TITLE"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayRequestInfo() throws Exception{
		try {
			Long id = Long.parseLong(Struts2Utils.getRequest().getParameter("requestId"));
			entity = searchService.get(id);
			if(entity.getStatus().equals("OPEN")){
				entity.setStatus("PENDING");
				entity.setPendingDate(new Date());
				searchService.save(entity);
			}
			this.writeSuccessResult(searchService.getRequestInfo((id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserRequestEntity getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity = (id != null) ? searchService.get(id) : new UserRequestEntity();
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
}
