package com.gnomon.intergration.vault.jcr;


public interface JcrSessionCallback<T> {

	T doInSession(WrapedSession session) throws Exception;
}
