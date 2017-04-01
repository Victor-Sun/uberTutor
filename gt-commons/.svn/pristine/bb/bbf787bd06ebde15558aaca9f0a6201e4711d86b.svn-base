/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.common.webservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @author frank
 * 
 */
public class CXFClient {

	static Map<String, String> userPaswords = new HashMap<String,String>();

	static{
		userPaswords.put("PDMS", "123456");
	}
	private String serverContentPath;

	private JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

	public CXFClient(String serverAddress, int port, String contextPath,String username,String password) {
		this("http://" + serverAddress + ":" + port + "/" + contextPath,username,password);
	}

	public CXFClient(String serverContentPath,String username,String password) {
		this.serverContentPath = serverContentPath;
		factoryBean.getInInterceptors().add(new LoggingInInterceptor(){
			
		});
		factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
//		factoryBean.getOutInterceptors().add(new org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor());
//		
//		
//		Map<String, Object> props = new HashMap<String,Object>();
//		props.put("action", "UsernameToken");
//		props.put("passwordType", "PasswordText");
//		props.put("user", username);
//		ClientCallback clientCallback = new ClientCallback(userPaswords);
//		props.put("passwordCallbackRef", clientCallback);
//		org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor wss4jOutInterceptor = new org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor(props);
//		factoryBean.getOutInterceptors().add(wss4jOutInterceptor);
//		factoryBean.setUsername(username);
//		factoryBean.setPassword(password);

	}

	public <I> I getService(Class<I> interfaceClass, String serviceAddress) {
		factoryBean.setServiceClass(interfaceClass);
		factoryBean.setAddress(serverContentPath + "/ws/"+ serviceAddress);
		I service = (I) factoryBean.create();
		return service;
	}
}
