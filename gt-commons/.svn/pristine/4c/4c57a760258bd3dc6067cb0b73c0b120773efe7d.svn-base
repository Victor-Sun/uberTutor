package com.gnomon.intergration.aws;


import com.actionsoft.sdk.services.JWS;
import com.actionsoft.sdk.services.JWS.BO;
import com.actionsoft.sdk.services.JWS.IM;
import com.actionsoft.sdk.services.JWS.ORG;
import com.actionsoft.sdk.services.JWS.Security;
import com.actionsoft.sdk.services.JWS.Session;
import com.actionsoft.sdk.services.JWS.TaskWorklist;
import com.actionsoft.sdk.services.JWS.Workflow;
import com.actionsoft.sdk.services.JWS.WorkflowTask;

public class AwsApi {
	
	public static final String AWS_SERVER_URL= System.getProperty("awsServerUrl", "127.0.0.1:83");
	private String awsServerPortalUrl=AWS_SERVER_URL+"/portal";

	private static AwsApi instance;
	private String wsdBaselLocation="http://"+AWS_SERVER_URL+"/services";
	private String securityProfileUuid = System.getProperty("securityProfileUuid","1D482544CFEC4568B39BEDCB68526441");
	private String imProfileUuid = System.getProperty("imProfileUuid","fe69b402acc61fa152dadb2d8c3d5e70");
	private String processProfileUuid = System.getProperty("processProfileUuid","ff8f8038f3c86712276d2d81cf7b8706");
	private String worklistProfileUuid = System.getProperty("worklistProfileUuid","ff8f8038f3c88ee94f29b924e9e71c56");
	private String orgProfileUuid = System.getProperty("orgProfileUuid", "ff8f8038f3f79fbd7d61cb403d23396f");
	private String boProfileUuid = System.getProperty("boProfileUuid","ff8f8038f3cd78591d91aa84096eca25");
	
	private static JWS.Session session;
	private static JWS.ORG org;
	private static JWS.BO bo;
	private static JWS.Workflow workflow;
	private static JWS.WorkflowTask workflowTask;
	private static JWS.IM im;
	private static JWS.TaskWorklist taskWorklist;
	private static JWS.Security security;

	private AwsApi() {
	}
	
	public static AwsApi getInstance(){
		if(instance == null){
			instance = new AwsApi();
		}
		return instance;
	}
	public Session getSession(){
		if(session == null){
			session =  JWS.Session.create(wsdBaselLocation+"/SessionService", securityProfileUuid);
		}
		return session;
	}
	public Security getSecurity(){
		if(security == null){
			security =  JWS.Security.create(wsdBaselLocation+"/SecurityService", securityProfileUuid);
		}
		return security;
	}
	public ORG getOrg(){
		if(org == null){
			org =  JWS.ORG.create(wsdBaselLocation+"/ORGService", orgProfileUuid);
		}
		return org;
	}
	
	public BO getBO(){
		if(bo == null){
			bo =  JWS.BO.create(wsdBaselLocation+"/BOService", boProfileUuid);
		}
		return bo;
	}

	public Workflow getWorkflow(){
		if(workflow == null){
			workflow =  JWS.Workflow.create(wsdBaselLocation+"/WorkflowService", processProfileUuid);
		}
		return workflow;
	}
	
	public WorkflowTask getWorkflowTask(){
		if(workflowTask == null){
			workflowTask =  JWS.WorkflowTask.create(wsdBaselLocation+"/WorkflowTaskService", processProfileUuid);
		}
		return workflowTask;
	}
	
	public TaskWorklist getTaskWorklist(){
		if(taskWorklist == null){
			taskWorklist =  JWS.TaskWorklist.create(wsdBaselLocation+"/TaskWorklist", worklistProfileUuid);
		}
		return taskWorklist;
	}
	
	public IM getIM(){
		if(im == null){
			im =  JWS.IM.create(wsdBaselLocation+"/IMService", imProfileUuid);
		}
		return im;
	}
	
	public String getAwsServerPortalUrl() {
		return awsServerPortalUrl;
	}
	public static void main(String[] args) {
		Session session2 = AwsApi.getInstance().getSession();
		System.out.println(session2);
	}
}
