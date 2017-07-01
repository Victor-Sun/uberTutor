/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringContextHolder.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package org.springside.modules.utils.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 浠ラ潤鎬佸彉閲忎繚瀛楽pring ApplicationContext, 鍙湪浠讳綍浠ｇ爜浠讳綍鍦版柟浠讳綍鏃跺�欎腑鍙栧嚭ApplicaitonContext.
 * 
 * @author calvin
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	/**
	 * 瀹炵幇ApplicationContextAware鎺ュ彛, 娉ㄥ叆Context鍒伴潤鎬佸彉閲忎腑.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("娉ㄥ叆ApplicationContext鍒癝pringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder涓殑ApplicationContext琚鐩�, 鍘熸湁ApplicationContext涓�:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; //NOSONAR
	}

	/**
	 * 瀹炵幇DisposableBean鎺ュ彛,鍦–ontext鍏抽棴鏃舵竻鐞嗛潤鎬佸彉閲�.
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	/**
	 * 鍙栧緱瀛樺偍鍦ㄩ潤鎬佸彉閲忎腑鐨凙pplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 浠庨潤鎬佸彉閲廰pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 浠庨潤鎬佸彉閲廰pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 娓呴櫎SpringContextHolder涓殑ApplicationContext涓篘ull.
	 */
	public static void clear() {
		logger.debug("娓呴櫎SpringContextHolder涓殑ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 妫�鏌pplicationContext涓嶄负绌�.
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext鏈敞鍏�,璇峰湪applicationContext.xml涓畾涔塖pringContextHolder");
		}
	}
}
