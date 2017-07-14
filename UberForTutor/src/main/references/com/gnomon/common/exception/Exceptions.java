package com.gnomon.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * �����쳣�Ĺ�����
 * 
 * @author calvin
 */
public class Exceptions {

	/**
	 * ��CheckedExceptionת��ΪUncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * ��ErrorStackת��ΪString.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

}
