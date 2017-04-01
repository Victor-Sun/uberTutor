package com.gnomon.pdms.action.mockbimserver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public abstract class MockBimService extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String success = "true";
	private String systemCode;

	
	public String getSuccess() {
		return success;
	}


	public void setSuccess(String success) {
		this.success = success;
	}


	protected String readFileContent(String resourceName){
		String content = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream is = MockBimService.class.getResourceAsStream(resourceName);
			if(is != null){
				while(is.available()>0){
					baos.write(is.read());
				}
				is.close();
			}
			
			content = new String(baos.toByteArray(),"utf-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

}
