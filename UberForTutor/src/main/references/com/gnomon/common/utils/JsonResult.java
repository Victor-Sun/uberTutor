/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gnomon.common.exception.ServiceException;


/**
 * @author peter
 * 
 * Json���ؽ������
 *
 */
public class JsonResult {
	
	
	private boolean success;
	private String msg;
	private Object data;
	private Integer total;
	
	
	/**
	 * �����������.
	 */
	@SuppressWarnings("unchecked")
	public <T extends JsonResult> T buildSuccessResult(Object resultData) {
		success = true;
		msg = "";
		data = resultData;
		return (T) this;
	}

	/**
	 * ������ѯ���Ϊlist���������
	 * @param resultData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends JsonResult> T buildSuccessResultForList(List resultData) {
		success = true;
		msg = "";
		data = resultData;
		total = resultData.size();
		return (T) this;
	}
	
	/**
	 * ������ѯ���Ϊlist���������(��ҳ)
	 * @param data2
	 * @param totalCount
	 */
	public <T extends JsonResult> T buildSuccessResultForList(List resultData, Integer totalCount) {
		success = true;
		msg = "";
		data = resultData;
		total = totalCount;
		return (T) this;		
	}	
	
	/**
	 * �����쳣���
	 * @param e
	 * @return
	 */
	public <T extends JsonResult> T buildErrorResult(String msg) {
		success = false;
		this.msg = msg;
		return (T)this;
	}
	
	/**
	 * �����쳣���
	 * @param e
	 * @return
	 */
	public <T extends JsonResult> T buildErrorResult(Throwable e) {
		success = false;
		if(e instanceof ServiceException){
			msg = e.getMessage();
			Map data1 =  new HashMap();
			data1.put("msg", msg);
			data = data1;
		}else {
			msg = "ϵͳ�쳣������ϵ����Ա";
		}
		return (T)this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
