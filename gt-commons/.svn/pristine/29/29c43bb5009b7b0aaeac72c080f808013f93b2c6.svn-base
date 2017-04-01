package com.gnomon.intergration.workflow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.workflow.WorkflowIntergrationException;

/**
 * 知识库目录管理AWS实现
 * 
 * @author MP
 * 
 */
@Service
public class AWSWorkflowInstanceAPIImpl implements
		com.gnomon.intergration.workflow.GTWorkflowInstanceAPI {

	private Log log = LogFactory.getLog(AWSWorkflowInstanceAPIImpl.class);

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	private com.actionsoft.sdk.services.JWS.Workflow getAWSWorkflowAPI(){
		return AwsApi.getInstance().getWorkflow();
	}
	private com.actionsoft.sdk.services.JWS.BO getAWSBOAPI(){
		return AwsApi.getInstance().getBO();
	}

	public void closeProcessInstance(String closeUserId, int processInstanceId,
			int taskInstanceId) throws WorkflowIntergrationException {
		try {
			getAWSWorkflowAPI().closeProcessInstance(closeUserId, processInstanceId, taskInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	// 创建流程实例的方法的Imp
	public int createProcessInstance(String workflowDefUUID, String userId,
			String title) throws WorkflowIntergrationException {
		try {
			return getAWSWorkflowAPI().createProcessInstance(workflowDefUUID, userId, title);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowInstanceAPI#createProcessInstance(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public int createProcessInstance(String workflowDefUUID, String userId, String title, Map<String, String> params)
			throws WorkflowIntergrationException {
		try {
			return getAWSWorkflowAPI().createProcessInstance(workflowDefUUID, userId, title);//TODO 没有实现参数传递
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	// 往BO子表里创建一条数据的Imp
	public int createBOData(String boTableName,
			Hashtable recordData,
			int processInstanceId, String createUser) throws WorkflowIntergrationException {
		try {
			return getAWSBOAPI().createBOData(boTableName, recordData, processInstanceId, createUser);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	//往BO表里更新一条数据
	public int updateBOData(String boTableName, Hashtable recordData, int boId)
			throws WorkflowIntergrationException {
		try {
			return getAWSBOAPI().updateBOData(boTableName, recordData, boId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	public void resetProcessApproval(int processInstanceId)
			throws WorkflowIntergrationException {
		try {
			getAWSWorkflowAPI().resetProcessApproval(processInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}

	}
	
	public void reactiveProcessInstance(int processInstanceId,int reActiveStepNo,String userId,boolean isDeleteHistory, String bizClazz)	throws WorkflowIntergrationException {
		try {
			getAWSWorkflowAPI().reactiveProcessInstance(processInstanceId, reActiveStepNo, userId, isDeleteHistory, bizClazz);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}

	}

	public double getWorkfolwRateOfProgress(int bindid) throws WorkflowIntergrationException {

		try {
			return 0.0;//getAWSWorkflowAPI().getWorkfolwRateOfProgress(bindid);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	public void removeProcessInstance(int processInstanceId)
			throws WorkflowIntergrationException {
		try {
			getAWSWorkflowAPI().removeProcessInstance(processInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	
	}

	// 获取流程实例交付物
	public List<String> getProcessInstanceAttaches(String sid, String boName,
			String[] attachFields, int processInstanceId, String filter) throws WorkflowIntergrationException {
		try {
			return new ArrayList<String> ();//TODO FAKE getAWSWorkflowAPI().getProcessInstanceAttaches(sid, boName, attachFields, processInstanceId, filter);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	@Override
	public void upFileByFiled(int boId, byte[] bytes, String fieldUUID,String fileName) throws WorkflowIntergrationException {
		try {
			getAWSBOAPI().upFileByFiled(boId, bytes, fieldUUID, fileName);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	@Override
	public byte[] downloadFileByField(int boId, String fieldUUID,
			String fileName) throws WorkflowIntergrationException {
		try {
			return getAWSBOAPI().downloadFileByFiled(boId, fieldUUID, fileName);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	@Override
	public String[] getFileDownloadLinkURLByFiled(String curentUserId,String currentSessionId, int boId, String fieldUUID) throws WorkflowIntergrationException {
		try {

			return new String[0];//TOTO FAKE getAWSBOAPI().getFileDownloadLinkURLByFiled(curentUserId,currentSessionId, boId, fieldUUID);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	public void assignVariable(int processInstanceId,String varName,String varValue) throws WorkflowIntergrationException{
		try {
			getAWSWorkflowAPI().assignVariable(processInstanceId, varName, varValue);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	public void assignVariables(int processInstanceId,Hashtable<String,String> vars) throws WorkflowIntergrationException{
		try {
			getAWSWorkflowAPI().assignVariables(processInstanceId, vars);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	@Override
	public Map<String, Object> getWfDefineByInstanceId(int instanceId,String tableName) throws WorkflowIntergrationException {
		try {
			return new HashMap<String,Object>();//TODO FAKE getAWSWorkflowAPI().getWfDefineByInstanceId(instanceId, tableName);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowInstanceAPI#createBOData(java.lang.String, java.util.Hashtable, java.lang.String)
	 */
	@Override
	public int createBOData(String boFileTableName, Hashtable<String, Object> recordData, String userid) throws WorkflowIntergrationException {
		try {
			return getAWSBOAPI().createBOData(boFileTableName, recordData, userid);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowInstanceAPI#removeProcessInstances(int[])
	 */
	@Override
	public void removeProcessInstances(int[] processInstanceIds) throws WorkflowIntergrationException {
		try {
			getAWSWorkflowAPI().removeProcessInstances(processInstanceIds);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowInstanceAPI#getParticipantsActiveOfProcessInstance(int)
	 */
	@Override
	public String getParticipantsActiveOfProcessInstance(int processInstanceId) throws WorkflowIntergrationException {
		try {
			return "";//TODO FAKE getAWSWorkflowAPI().getParticipantsActiveOfProcessInstance(processInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.gnomon.intergration.workflow.GTWorkflowInstanceAPI#getParticipantsHistoryOfProcessInstance(int)
	 */
	@Override
	public String getParticipantsHistoryOfProcessInstance(int processInstanceId) throws WorkflowIntergrationException {
		try {
			return getAWSWorkflowAPI().getParticipantsHistoryOfProcessInstance(processInstanceId);
		} catch (Exception e) {
			log.error(e);
			throw new WorkflowIntergrationException(e);
		}
	}
	
	
}
