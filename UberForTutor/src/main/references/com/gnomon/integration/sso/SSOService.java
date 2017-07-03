/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.integration.sso;

/**
 * @author frank
 *
 */
public interface SSOService {

	boolean checkSession(String userId,String sessionId) throws SSOIntegrationException;
	
	String createSession(String userId,String password,String bip,String lang) throws SSOIntegrationException;
	
	void closeSession(String sessionId);
}
