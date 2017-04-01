package com.gnomon.common.webservice;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Simple password callback handler. This just checks if the password for the
 * private key is being requested, and if so sets that value.
 */
public class ClientCallback implements CallbackHandler {
	private Map<String, String> userPasswords;

	public ClientCallback(Map<String, String> userPasswords) {
		this.userPasswords = userPasswords;
	}

	public void handle(Callback[] callbacks) throws IOException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			String id = pwcb.getIdentifier();
			int usage = pwcb.getUsage();
			if (usage == WSPasswordCallback.USERNAME_TOKEN) {

				// used to retrieve password for private key
				if (userPasswords.containsKey(id)) {
					pwcb.setPassword(userPasswords.get(id));
				}

			}
		}
	}
}