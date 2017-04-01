/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

/**
 * @author frank
 *
 */
public interface SSOService {

	boolean checkSession(String userId,String sessionId) throws SSOIntergrationException;
	
	String createSession(String userId,String password,String bip,String lang) throws SSOIntergrationException;
	
	void closeSession(String sessionId);
}
