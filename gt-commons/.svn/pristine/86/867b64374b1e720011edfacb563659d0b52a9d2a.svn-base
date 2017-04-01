package com.gnomon.intergration.vault.jcr;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.RepositoryFactory;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.RepositoryFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class JackrabbitTest {

	public static final String REPOSITORY_HOME = "D:/ContentRepository";
	public static final String REPOSITORY_CONF = "D:/ContentRepository/jackrabbit-repo.xml";

	private static JcrSessionTemplate sessionTemplate;
	public static JcrService jcrService;

	protected static JcrSessionTemplate getSessionTemplate() throws RepositoryException {
		if (sessionTemplate == null) {
			sessionTemplate = new JcrSessionTemplate(getRepository());
		}
		return sessionTemplate;
	}

	public static Repository getRepository() throws RepositoryException {
		Map<String, String> parameters = new HashMap<String,String>();
		parameters.put(RepositoryFactoryImpl.REPOSITORY_CONF, REPOSITORY_CONF);
		parameters.put(RepositoryFactoryImpl.REPOSITORY_HOME, REPOSITORY_HOME);
		return JcrUtils.getRepository(parameters);
	}

	@BeforeClass
	public static void init() throws Exception {
		
		jcrService = new JcrService(getSessionTemplate());
	}

	public RepositoryFactory getRepositoryFactory() {
		RepositoryFactory repositoryFactory = new RepositoryFactoryImpl();
		return repositoryFactory;
	}

	@Test
	public void test() {
		jcrService.test("test");
	}
}
