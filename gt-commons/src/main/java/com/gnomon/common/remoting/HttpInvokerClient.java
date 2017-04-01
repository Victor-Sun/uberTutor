package com.gnomon.common.remoting;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class HttpInvokerClient {
	private String serverContentPath;
	
	public HttpInvokerClient(String serverContentPath) {
		super();
		this.serverContentPath = serverContentPath;
	}

	HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
	
	public <I> I getService(Class<I> interfaceClass, String serviceName) {
		factoryBean.setServiceUrl(serverContentPath+"/"+serviceName);
		factoryBean.setServiceInterface(interfaceClass);
		@SuppressWarnings("unchecked")
		I service = (I) factoryBean.getObject();
		return service;
	}
}
