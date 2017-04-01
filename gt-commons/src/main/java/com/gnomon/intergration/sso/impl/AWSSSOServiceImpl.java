/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso.impl;

import org.springframework.stereotype.Service;

import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.sso.SSOIntergrationException;
import com.gnomon.intergration.sso.SSOService;

/**
 * @author frank
 *
 */
@Service
public class AWSSSOServiceImpl implements SSOService{

	
	/* (non-Javadoc)
	 * @see com.gnomon.intergration.sso.SSOService#checkSession(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkSession(String userId, String sessionId) {
		return AwsApi.getInstance().getSession().checkSession(userId, sessionId);
	}
	
	@Override
	public String createSession(String userId, String password,String bip,String lang) throws SSOIntergrationException {
		String sid = null;
		
		try {
			sid = AwsApi.getInstance().getSession().createSession(userId, password,bip,lang);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SSOIntergrationException(e);
			//ignore
//			throw new SSOIntergrationException(e);
		}
		
		return sid;
	}
	
	@Override
	public void closeSession(String sessionId) {
		AwsApi.getInstance().getSession().close(sessionId);
		
	}
}
