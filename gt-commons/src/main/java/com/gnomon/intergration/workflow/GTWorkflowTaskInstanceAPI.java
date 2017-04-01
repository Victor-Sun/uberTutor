package com.gnomon.intergration.workflow;

import java.util.List;
import java.util.Map;

/**
 * 操作工作流任务实例接口
 * 
 * @author mopeng
 * 
 */
public interface GTWorkflowTaskInstanceAPI {
	/**
	 * 创建流程任务实例，任务实例数量与participant 有关
	 * 
	 * @param ownerId
	 * @param processInstanceId
	 * @param synType
	 * @param priorityType
	 * @param activityNo
	 * @param participantId
	 * @param title
	 * @param isShortMessage
	 * @param localDepartmentId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public int[] createProcessTaskInstance(String ownerId, int processInstanceId, int synType, int priorityType,
			int activityNo, String participantId, String title, boolean isShortMessage, int localDepartmentId)
			throws WorkflowIntergrationException;
	
	
	public void removeProcessTaskInstance(int processTaskInstanceId)throws Exception;

	/**
	 * 根据流程实例ID获取任务ID
	 * 
	 * @param bind_id
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public int getTaskid(int bind_id) throws WorkflowIntergrationException;

	/**
	 * 根据流程实例ID获取当前任务信息
	 * 
	 * @param bindId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public List<Map<String, Object>> getCurrTask(int bindId) throws WorkflowIntergrationException;

	/**
	 * 根据流程实例ID获取当前节点信息
	 * 
	 * @param bindId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public Map<String, Object> getCurrNode(int bindId) throws WorkflowIntergrationException;

	/**
	 * 查询流程某节点任务信息
	 * 
	 * @param wfUUID
	 * @param wfNodeUUID
	 * @param bindId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public List<Map<String, Object>> getTask(String wfUUID, String wfNodeUUID, int bindId) throws WorkflowIntergrationException;

}
