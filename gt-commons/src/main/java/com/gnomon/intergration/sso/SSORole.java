/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.intergration.sso;

import java.io.Serializable;

/**
 * @author frank
 *
 */
public class SSORole implements Serializable{

	private static final long serialVersionUID = 4851878536450318026L;
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
