package com.gnomon.pdms.procedure;

/**
 * 存储过程调用异常
 * 
 * @author Frank
 *
 */
public class DBProcedureExecuteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String returnCode;
	
	public DBProcedureExecuteException(String returnCode,String message) {
		super(message);
		this.returnCode = returnCode;
	}
	
	@Override
	public String toString() {
		return "ProcedureExecuteException [returnCode = " + this.returnCode
				+ ", getMessage() = " + getMessage()
				+ ", getCause() = " + getCause() + "]";
	}
}
