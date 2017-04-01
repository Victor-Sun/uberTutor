/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

import com.gnomon.intergration.IntergrationException;

/**
 * @author frank
 *
 */
public class SSOIntergrationException extends IntergrationException {

	private static final long serialVersionUID = 3800284208597598600L;

	public SSOIntergrationException() {
		super();
	}

	public SSOIntergrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSOIntergrationException(String message) {
		super(message);
	}

	public SSOIntergrationException(Throwable cause) {
		super(cause);
	}
	

}
