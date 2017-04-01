/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gnomon.intergration.IntergrationException;
import com.gnomon.intergration.sso.SSOExtendService;
import com.gnomon.intergration.workflow.WorkflowIntergrationException;
import com.gnomon.servicewapper.aws.api.WappedAWSExtendAPI;

/**
 * @author frank
 * 
 */
@Service
public class AWSSSOExtendServiceImpl implements SSOExtendService {

	private Log log = LogFactory.getLog(AWSSSOExtendServiceImpl.class);


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gnomon.intergration.extend.GTExtendService#getAddressComponentAddressCode
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public String getAddressComponentAddressCode(String fieldName, String imgURL) throws IntergrationException {
//		try {
//			return wappedAWSExtendAPI.getComponentAddressCode(fieldName, imgURL);
//		} catch (Exception e) {
//			log.error(e);
//			throw new WorkflowIntergrationException(e);
//		}
		return "";
	}

}
