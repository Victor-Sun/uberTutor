package com.gnomon.intergration.workflow.impl;

import com.gnomon.intergration.workflow.GTActivitiService;

/**
 * Activiti Service Impl
 * 
 * Dongcheng Gai on 2016/07/26
 */

public class GTActivitiServiceImpl implements GTActivitiService{
//	
//	public ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//	
//	public RuntimeService runtimeService = processEngine.getRuntimeService();
//	
//	public IdentityService identityService = processEngine.getIdentityService();
//	
//	public TaskService taskService = processEngine.getTaskService();
//	
//	public RepositoryService repositoryService = processEngine.getRepositoryService();
//	
//	public ManagementService managementService = processEngine.getManagementService();
//	
//	public HistoryService historyService = processEngine.getHistoryService();
//	
//	public FormService formService = processEngine.getFormService();
//	
//	public ProcessInstance startProcess(String userId, String key, String businesskey, Map<String, Object> map) {
//		identityService.setAuthenticatedUserId(userId);
//		return runtimeService.startProcessInstanceByKey(key, businesskey, map);
//	}
//
//	public void claim(String taskId, String userId){
//		taskService.claim(taskId, userId);
//	}
//	
//	public Task getTask(String taskId){
//		return taskService.createTaskQuery().taskId(taskId).singleResult();
//	}
//	
//	public ExecutionEntity getExecution(Task task){
//		return (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).processInstanceId(task.getProcessInstanceId()).singleResult();
//	}
//	
//	public void complete(String taskId, Map<String, Object> map){
//		taskService.complete(taskId, map);
//	}
//	
//	public void setTaskVar(String taskId, String variableName, Object value){
//		taskService.setVariableLocal(taskId, variableName, value);;
//	}

}
