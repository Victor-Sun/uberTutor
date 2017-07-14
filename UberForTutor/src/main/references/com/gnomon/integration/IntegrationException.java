/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.integration;

/**
 * @author frank
 *
 */
public class IntegrationException extends RuntimeException {

	private static final long serialVersionUID = -531581375082719021L;

	public IntegrationException() {
		super();
	}

	public IntegrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IntegrationException(String message) {
		super(message);
	}

	public IntegrationException(Throwable cause) {
		super(cause);
	}

}
