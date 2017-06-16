package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.UserRequestEntity;

@Namespace("/main")
public class RequestAction extends PDMSCrudActionSupport<UserRequestEntity> {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public UserRequestEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		System.out.println("Success");
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
