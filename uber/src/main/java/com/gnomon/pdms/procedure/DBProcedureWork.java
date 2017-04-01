package com.gnomon.pdms.procedure;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jdbc.Work;

/**
 * 存储过程基础类
 * @author Frank
 *
 */
public abstract class DBProcedureWork implements Work {
	protected String returnCode = "";//output 
	protected String returnMsg = "";//output 
	
	protected Map<String,Object> returnMap = new HashMap<String,Object>();
	
	public String getReturnCode() {
		return returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
}
