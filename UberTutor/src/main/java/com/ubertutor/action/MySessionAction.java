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
import com.ubertutor.entity.UserEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.MySessionService;

import jp.co.nec.flowlites.core.FLPage;

@Namespace("/main")
@AllowedMethods({"displayAllSessions",
	"displayUserRequests",
	"displayTutorSessions",
	"displayRequestInfo",
	"updateRequestToInProcess",
	"updateRequestToInProcess",
	"updateRequestToCanceled"})
public class MySessionAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MySessionService sessionService;
	private UserEntity userEntity = SessionData.getLoginUser();
	private UserRequestEntity requestEntity;
	private Long requestId;

	/**
	 * @return the requestId
	 */
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	/**
	 * Displays all requests
	 */
	public void displayAllSessions(){
		this.writeSuccessResult(sessionService.getRequests());
	}

	/**
	 * Displays all of the User's requests/sessions
	 */
	public void displayUserRequests(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.sessionService.getUserRequests(userEntity.getId(), this.getPage(), this.getLimit());
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Displays all of a Tutor's sessions
	 */
	public void displayTutorSessions(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.sessionService.getTutorRequests(userEntity.getId(), this.getPage(), this.getLimit());
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Displays the info for a request
	 * @throws Exception
	 */
	public void displayRequestInfo() throws Exception{
		try{
			String msg;
			if(requestId.equals(null)){
				msg = "An error has occured!";
				throw new Exception(msg);
			}
			requestEntity = sessionService.get(requestId);
			this.writeSuccessResult(sessionService.getRequestInfo((requestId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates a request to In Process
	 */
	public void updateRequestToInProcess() throws Exception{
		try{
			requestEntity = sessionService.get(requestId);
			if(requestEntity.getStatus() != "OPEN"){
				String msg = "Request has already been accepted! Try accepting a different request!";
				throw new Exception(msg);
			}
			requestEntity.setStatus("IN PROCESS");
			requestEntity.setProcessDate(new Date());
			sessionService.save(requestEntity);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Updates a request to Closed
	 */
	public void updateRequestToClosed(){
		requestEntity = sessionService.get(requestId);
		requestEntity.setStatus("CLOSED");
		requestEntity.setCloseDate(new Date());
		sessionService.save(requestEntity);
	}	

	/**
	 * Updates a request to Canceled
	 */
	public void updateRequestToCanceled(){
		requestEntity = sessionService.get(requestId);
		requestEntity.setStatus("CANCELED");
		requestEntity.setCancelDate(new Date());
		sessionService.save(requestEntity);
	}	

	@Override
	public UserRequestEntity getModel() {
		return requestEntity;
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

	@Override
	protected void prepareModel() throws Exception {
	}
}
