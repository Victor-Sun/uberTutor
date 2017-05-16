package com.gnomon.pdms.workflow;

import com.gnomon.intergration.workflow.impl.GTActivitiServiceImpl;

public class GTWorkFlowService extends GTActivitiServiceImpl{
	
//	private static final Log log = LogFactory.getLog(GTWorkFlowService.class);
//	
//	public List<Task> getTasks(String userId){
//        log.info("getTasks() : userId = " + userId);
//		//取得一个人（当前用户）未签收的任务
//		List<Task> taskCandidates=taskService.createTaskQuery().processDefinitionKey("QIMProcess").taskCandidateUser(userId).orderByTaskCreateTime().desc().list();
//		//取得一个人（当前用户）的待办任务（已签收）
//		List<Task> taskAssigness=taskService.createTaskQuery().processDefinitionKey("QIMProcess").taskAssignee(userId).orderByTaskCreateTime().desc().list();
//		List<Task> tasklist=new ArrayList<Task>();
//		tasklist.addAll(taskCandidates);
//		tasklist.addAll(taskAssigness);
//		
//        if ((tasklist != null) && (tasklist.size() > 0)){
//        	log.info("tasklist.size() = " + tasklist.size());
//        };
//
//		return tasklist;
//	}
//	
//	public void logUserTasks(Log log, String userId){
//        List<Task> tasklist = this.getTasks(userId);
//        log.info(userId + "'s task: Start.....................................................");
//        for (Task task : tasklist) { 
//			String processinsId = task.getProcessInstanceId();
//			String processdifineId = task.getProcessDefinitionId();
//			ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
//			ProcessDefinition pd =this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processdifineId).singleResult();
//			String businesskey=pi.getBusinessKey();
//        	log.info("Process Instance Id: "+ processinsId);
//        	log.info("Process Definition Id: "+ processdifineId);
//        	log.info("Process Instance: "+ pi);
//        	log.info("Process Definition: "+ pd);
//        	log.info("Business Key: "+ businesskey); 
//        	log.info("Task's Id: "+ task.getId());
//        	log.info("Task's Assignee: "+ task.getAssignee());
//        	log.info("Task's Form Key: "+ task.getFormKey());
//        	log.info("Task's Name: "+ task.getName());
//        	log.info("Task's Owner: "+ task.getOwner());
//        	log.info("Task's Parent Task Id: "+ task.getParentTaskId());
//        	log.info("Task's Task Definition Key: "+ task.getTaskDefinitionKey());
//        	log.info("Task's Category: "+ task.getCategory());
//        	log.info("Task's Create Time: "+ task.getCreateTime());
//        	log.info("Task.....................................................");
//        } 
//        log.info(userId + "'s task End.....................................................");
//	}
//	
//	public void logAllTasks(Log log){
//        List<Task> tasklist = this.taskService.createTaskQuery().list(); 
//
//        log.info("All task: Start.....................................................");
//        for (Task task : tasklist) { 
//			String processinsId = task.getProcessInstanceId();
//			String processdifineId = task.getProcessDefinitionId();
//			ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceId(processinsId).singleResult();
//			ProcessDefinition pd =this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processdifineId).singleResult();
//			String businesskey=pi.getBusinessKey();
//        	log.info("Process Instance Id: "+ processinsId);
//        	log.info("Process Definition Id: "+ processdifineId);
//        	log.info("Process Instance: "+ pi);
//        	log.info("Process Definition: "+ pd);
//        	log.info("Business Key: "+ businesskey); 
//        	log.info("Task's Id: "+ task.getId());
//        	log.info("Task's Assignee: "+ task.getAssignee());
//        	log.info("Task's Form Key: "+ task.getFormKey());
//        	log.info("Task's Name: "+ task.getName());
//        	log.info("Task's Owner: "+ task.getOwner());
//        	log.info("Task's Parent Task Id: "+ task.getParentTaskId());
//        	log.info("Task's Task Definition Key: "+ task.getTaskDefinitionKey());
//        	log.info("Task's Category: "+ task.getCategory());
//        	log.info("Task's Create Time: "+ task.getCreateTime());
//        	log.info("Task.....................................................");
//        } 
//        log.info("All task: End.....................................................");
//	}
//	
}
