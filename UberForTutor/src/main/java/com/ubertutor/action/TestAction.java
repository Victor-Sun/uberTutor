package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/main")
public class TestAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	public void test(){
		System.out.println(SUCCESS);
	}
}
