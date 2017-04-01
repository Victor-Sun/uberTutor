package com.gnomon.intergration.workflow.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.workflow.GTWorkflowTaskInstanceAPI;
import com.gnomon.intergration.workflow.WorkflowIntergrationException;
import com.gnomon.servicewapper.aws.api.WappedAWSWorkflowTaskInstanceAPI;

@Repository
public class AWSWorkflowTaskInstanceAPIImpl implements GTWorkflowTaskInstanceAPI {
	private Log log = LogFactory.getLog(AWSWorkflowTaskInstanceAPIImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	private com.actionsoft.sdk.services.JWS.WorkflowTask getAWSWorkflowTaskAPI(){
		return AwsApi.getInstance().getWorkflowTask();
	}
	
	@Override
	public int[] createProcessTaskInstance(String ownerId,
			int processInstanceId, int synType, int priorityType,
			int activityNo, String participantId, String title,
			boolean isShortMessage, int localDepartmentId)
			throws WorkflowIntergrationException {
		
		try {
			return getAWSWorkflowTaskAPI().createProcessTaskInstance(ownerId, processInstanceId, synType, priorityType, activityNo, participantId, title, isShortMessage, localDepartmentId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
		
	}

	@Override
	public int getTaskid(int bind_id) {
		try {
			return 0;//TODO FAKE getAWSWorkflowTaskAPI().getTaskid(bind_id);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getCurrTask(int bindId) throws WorkflowIntergrationException {
		try {
			return null;//TODO FAKE getAWSWorkflowTaskAPI().getCurrTask(bindId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	@Override
	public List<Map<String, Object>> getTask(String wfUUID,String wfNodeUUID,int bindId) throws WorkflowIntergrationException {
		try {
			return null;//TODO FAKE getAWSWorkflowTaskAPI().getTask(wfUUID, wfNodeUUID, bindId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	
	@Override
	public Map<String, Object> getCurrNode(int bindId) throws WorkflowIntergrationException {
		try {
			return null;//TODO FAKE getAWSWorkflowTaskAPI().getCurrNode(bindId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}

	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowTaskInstanceAPI#removeProcessTaskInstance(int)
	 */
	@Override
	public void removeProcessTaskInstance(int processTaskInstanceId) throws Exception {
		try {
			getAWSWorkflowTaskAPI().removeProcessTaskInstance(processTaskInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
		
	}

	
}
