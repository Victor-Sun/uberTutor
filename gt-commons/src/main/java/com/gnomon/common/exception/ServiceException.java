package com.gnomon.common.exception;

/**
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 * @author calvin
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = 3583566093089790852L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String messageCode, String message) {
		super(messageCode, message);
		setMessageCode(messageCode);
	}

	public ServiceException(String messageCode, String message, Throwable cause) {
		super(messageCode, message, cause);
		setMessageCode(messageCode);
	}
}
