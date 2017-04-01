package com.gnomon.intergration.vault.jcr;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.nodetype.NodeDefinitionTemplate;
import javax.jcr.nodetype.NodeTypeDefinition;
import javax.jcr.nodetype.NodeTypeManager;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.commons.JndiRepositoryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class JcrSessionTemplate {

	public static final String REPOSITORY_HOME = "/ContentRepository";
	public static final String REPOSITORY_CONF = "/jackrabbit-repo.xml";
	public static final String REPOSITORY_JNDI_ENV = "java:comp/env/";
	public static final String REPOSITORY_JNDI_NAME = "jcr/repository";

	private static final String DEFAULT_USERNAME = "admin";
	private static final String DEFAULT_PASSWORD = "admin";
	
	//fields
	private String username = DEFAULT_USERNAME;
	private String password = DEFAULT_PASSWORD;

	private static Repository repository;
	
	@Value("#{application['jcr.repository.jndiName']}")
	private String repossitoryJndiName=REPOSITORY_JNDI_NAME;

	//Constructor
	public JcrSessionTemplate(){}
	public JcrSessionTemplate(Repository repository){
		JcrSessionTemplate.repository = repository;
	}
	public JcrSessionTemplate(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	//Methods

	protected Repository getRepository() throws Exception {
		if (repository == null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(JndiRepositoryFactory.JNDI_NAME, REPOSITORY_JNDI_ENV+repossitoryJndiName);
			repository = JcrUtils.getRepository(parameters);
			
		}
		return repository;
	}
	
	public <T> T execute(String workspaceName,JcrSessionCallback<T> callback) {
		Assert.notNull(callback, "Callback object must not be null");
		Session session = null;
		try {

			session = getRepository().login(new SimpleCredentials(username, password.toCharArray()),workspaceName);

//			NodeTypeManager manager = (NodeTypeManager) session.getWorkspace().getNodeTypeManager();
//			NodeDefinitionTemplate template = manager.createNodeDefinitionTemplate();
//			NodeTypeDefinition ntd = new NodeTypeDefinition();
//			manager.registerNodeType(ntd, allowUpdate);

			synchronized (session) {
				return (T) callback.doInSession(new WrapedSession(session));
			}
		} catch (Exception e) {
			throw new RuntimeException("Callback execute exception", e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
	
}
