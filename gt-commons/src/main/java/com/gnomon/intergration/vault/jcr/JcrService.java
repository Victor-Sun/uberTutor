package com.gnomon.intergration.vault.jcr;

import javax.jcr.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JcrService {
	public JcrService(){}
	public JcrService(JcrSessionTemplate sessionTemplate){
		this.sessionTemplate = sessionTemplate;
	}
	@Autowired
	protected JcrSessionTemplate sessionTemplate ;


	public String test(String workspaceName){
		return sessionTemplate.execute(workspaceName,new JcrSessionCallback<String>() {

			@Override
			public String doInSession(WrapedSession session) throws Exception {
				 Node root = session.getRootNode();
			 // Store content
	            Node hello = root.addNode("hello");
	            Node world = hello.addNode("world");
	            world.setProperty("message", "Hello, World!");
	            session.save();

	            // Retrieve content
	            Node node = root.getNode("hello/world");
	            System.out.println(node.getPath());
	            System.out.println(node.getProperty("message").getString());

		        session.save();
		       
		       // Remove content
	            root.getNode("hello").remove();
	            session.save();
		       return  null;
			}

		});
	}
}
