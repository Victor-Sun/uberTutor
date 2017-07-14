/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.integration.sso;

import com.gnomon.integration.IntegrationException;

/**
 * @author frank
 *
 */
public class SSOIntegrationException extends IntegrationException {

	private static final long serialVersionUID = 3800284208597598600L;

	public SSOIntegrationException() {
		super();
	}

	public SSOIntegrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSOIntegrationException(String message) {
		super(message);
	}

	public SSOIntegrationException(Throwable cause) {
		super(cause);
	}
	

}
