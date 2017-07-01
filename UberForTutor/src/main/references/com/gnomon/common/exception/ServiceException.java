package com.gnomon.common.exception;

/**
 * Service�㹫�õ�Exception.
 * 
 * �̳���RuntimeException, ����Spring��������ĺ������׳�ʱ�ᴥ������ع�.
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
