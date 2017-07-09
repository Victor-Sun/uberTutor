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
	"displayUserSessions",
	"displayTutorSessions",
	"displaySessionInfo",
	"updateSessionToInProcess",
	"updateSessionToInProcess",
	"updateSessionToCanceled"})
public class MySessionAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MySessionService sessionService;
	private UserEntity userEntity = SessionData.getLoginUser();
	private UserRequestEntity requestEntity;
	private String requestId;

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
	 * Sends all requests to front end
	 */
	public void displayAllSessions(){
		this.writeSuccessResult(sessionService.getSessions());
	}

	/**
	 * Sends all of a user's requests to the front end
	 */
	public void displayUserSessions(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.sessionService.getUserSessions(userEntity.getId(), this.getPage(), this.getLimit());
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
	 * Sends all of a tutor's requests to the front end
	 */
	public void displayTutorSessions(){
		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			FLPage<Map<String,Object>> pageResult = this.sessionService.getTutorSessions(userEntity.getId(), this.getPage(), this.getLimit());
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
	 * Sends a requests info to the front end
	 * @throws Exception
	 */
	public void displaySessionInfo() throws Exception{
		try{
			String msg;
			if(requestId.equals(null)){
				msg = "An error has occured!";
				throw new Exception(msg);
			}
			Long id = Long.parseLong(requestId);
			requestEntity = sessionService.get(id);
			this.writeSuccessResult(sessionService.getSessionInfo((id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates a session to In Process
	 */
	public void updateSessionToInProcess(){
		Long id = Long.parseLong(requestId);
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("IN PROCESS");
		requestEntity.setProcessDate(new Date());
		sessionService.save(requestEntity);
	}
	
	/**
	 * Updates a session to Closed
	 */
	public void updateSessionToClosed(){
		Long id = Long.parseLong(requestId);
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("CLOSED");
		requestEntity.setCloseDate(new Date());
		sessionService.save(requestEntity);
	}	

	/**
	 * Updates a session to Canceled
	 */
	public void updateSessionToCanceled(){
		Long id = Long.parseLong(requestId);
		requestEntity = sessionService.get(id);
		requestEntity.setStatus("CANCELED");
		requestEntity.setCancelDate(new Date());
		sessionService.save(requestEntity);
	}	

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
