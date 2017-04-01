package com.gnomon.intergration.workflow;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 操作工作流实例接口
 * 
 * @author mopeng
 * 
 */
public interface GTWorkflowInstanceAPI {
	/**
	 * 启动一个流程实例
	 * 
	 * @param workflowDefUUID
	 * @param userId
	 * @param title
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public int createProcessInstance(String workflowDefUUID, String userId,
			String title) throws WorkflowIntergrationException;

	/**
	 * 启动一个流程实例
	 * @param workflowDefUUID
	 * @param userId
	 * @param title
	 * @param params
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public int createProcessInstance(String workflowDefUUID, String userId,
			String title,Map<String,String>params) throws WorkflowIntergrationException;

	/**
	/**
	 * 往BO子表里创建一条数据(针对标准任务从过程插入到PDC做的)
	 * 
	 * @param boTableName
	 * @param recordData
	 * @param processInstanceId
	 * @param createUser
	 * @return
	 * @throws WorkflowException
	 */
//	public int createBOData(Connection conn, String boTableName,
//			Hashtable recordData, int processInstanceId, String createUser)
//			throws WorkflowException;
	
	/**
	 * 往BO子表里创建一条数据(针对标准任务从过程插入到PDC做的)
	 * 
	 * @param boTableName
	 * @param recordData
	 * @param processInstanceId
	 * @param createUser
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public int createBOData(String boTableName,
			Hashtable recordData,
			int processInstanceId, String createUser) throws WorkflowIntergrationException;

	/**
	 * 往BO子表里创建一条数据,无需流程实例
	 * @param boFileTableName
	 * @param recordData
	 * @param userid
	 * @return
	 */
	public int createBOData(String boFileTableName, Hashtable<String, Object> recordData, String userid)throws WorkflowIntergrationException;
	
	/**
	 * 往BO表里更新一条数据
	 * 
	 * @param boTableName
	 * @param recordData
	 * @param boId
	 * @return int
	 * @throws WorkflowIntergrationException
	 */
	public int updateBOData(String boTableName, Hashtable recordData, int boId) throws WorkflowIntergrationException;
	/**
	 * 重置当前工作流到第1个节点(发起人撤销申请)
	 * 
	 * @param processInstanceId
	 * @throws WorkflowIntergrationException
	 */
	public void resetProcessApproval(int processInstanceId)
			throws WorkflowIntergrationException;
	
	/**
	 * 激活流程
	 * @param processInstanceId
	 * @param reActiveStepNo
	 * @param userId
	 * @param isDeleteHistory
	 * @param bizClazz
	 * @throws WorkflowIntergrationException
	 */
	public void reactiveProcessInstance(int processInstanceId,int reActiveStepNo,String userId,boolean isDeleteHistory, String bizClazz)	throws WorkflowIntergrationException;

	/**
	 * 关闭一个流程实例
	 * 
	 * @param closeUserId
	 * @param processInstanceId
	 * @param taskInstanceId
	 * @throws WorkflowIntergrationException
	 */
	public void closeProcessInstance(String closeUserId, int processInstanceId,
			int taskInstanceId) throws WorkflowIntergrationException;

	/**
	 * 删除一个流程实例
	 * 
	 * @param processInstanceId
	 * @throws WorkflowIntergrationException
	 */
	public void removeProcessInstance(int processInstanceId)
			throws WorkflowIntergrationException;

	/**
	 * 获取一个流程实例的进度
	 * 
	 * @param bindid
	 */
	public double getWorkfolwRateOfProgress(int bindid)throws WorkflowIntergrationException;

	/**
	 * 获取一个流程实例的所有附件
	 * 
	 * @param sid
	 * @param boName
	 * @param attachFields
	 * @param processInstanceId
	 * @return
	 */
	public List<String> getProcessInstanceAttaches(String sid, String boName,
			String[] attachFields, int processInstanceId, String filter)throws WorkflowIntergrationException;
	
	/**
	 * 上传附件到BO表中的某一个字段
	 * @param boId
	 * @param bytes
	 * @param fieldUUID
	 * @param fileName
	 */
	public void upFileByFiled(int boId,byte[] bytes, String fieldUUID, String fileName)throws WorkflowIntergrationException;
	
	/**
	 * 读取[附件]类型字段存储在文件系统的文件内容
	 * @param boId
	 * @param fieldUUID
	 * @param fileName
	 */
	public byte[] downloadFileByField(int boId, String fieldUUID, String fileName)throws WorkflowIntergrationException;
	/**
	 * 获取指定附件类型字段在某个BO表记录存储的文件下载链接(输出为html格式, 直接下载)
	 * @param curentUserId
	 * @param boId
	 * @param fieldUUID
	 */
	public String[] getFileDownloadLinkURLByFiled(String curentUserId,String currentSessionId, int boId, String fieldUUID)throws WorkflowIntergrationException;
	
	/**
	 * 赋值流程变量
	 * @param processInstanceId
	 * @param varName
	 * @param varValue
	 */
	public void assignVariable(int processInstanceId,String varName,String varValue)throws WorkflowIntergrationException;

	/**
	 * 赋值流程变量
	 * @param processInstanceId
	 * @param vars
	 */
	public void assignVariables(int processInstanceId,Hashtable<String,String> vars)throws WorkflowIntergrationException;
	
	/**
	 * 根据流程实例ID查询流程定义信息
	 * @param instanceId
	 * @return
	 */
	public Map<String,Object> getWfDefineByInstanceId(int instanceId,String tableName)throws WorkflowIntergrationException;


	/**
	 * 删除多个流程实例
	 * @param processInstanceIds
	 * @throws WorkflowIntergrationException
	 */
	public void removeProcessInstances(int[] processInstanceIds) throws WorkflowIntergrationException;
	
	/**
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public String getParticipantsActiveOfProcessInstance(int processInstanceId) throws WorkflowIntergrationException;
	
	/**
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws WorkflowIntergrationException
	 */
	public String getParticipantsHistoryOfProcessInstance(int processInstanceId) throws WorkflowIntergrationException;

}
