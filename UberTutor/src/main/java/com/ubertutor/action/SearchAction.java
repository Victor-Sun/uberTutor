package com.ubertutor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.SearchService;

import jp.co.nec.flowlites.core.FLPage;

@Namespace("/main")
@AllowedMethods({"displayRequests",
	"displayRequestInfo"})
public class SearchAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SearchService searchService;
	private UserRequestEntity requestEntity;
	private Long id;
	private String requestId;
	
	/**
	 * Returns id
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns requestId
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}
	
	/**
	 * Set requestId
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Sends User Requests to the front end
	 */
	public void displayRequests() throws Exception{
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.searchService.getRequests(Long.parseLong(SessionData.getLoginUserId()), this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("requestId", map.get("REQUEST_ID"));
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

	/**
	 * Sends request info to the front end
	 * @throws Exception
	 */
	public void displayRequestInfo() throws Exception{
		try {
			Long requestId = Long.parseLong(this.requestId);
			requestEntity = searchService.get(requestId);
			if(requestEntity.getStatus().equals("OPEN")){
				requestEntity.setStatus("PENDING");
				requestEntity.setPendingDate(new Date());
				searchService.save(requestEntity);
			}
			this.writeSuccessResult(searchService.getRequestInfo((requestId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserRequestEntity getModel() {
		return requestEntity;
	}

	@Override
	protected void prepareModel() throws Exception {
		requestEntity = (id != null) ? searchService.get(id) : new UserRequestEntity();
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}

	@Override
	public String save() throws Exception {
		return null;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}
}
