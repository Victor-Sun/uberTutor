package com.gnomon.intergration.im;

import java.util.Map;

public interface IMService {
	/**
	 * 发送邮件
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @return
	 */
	int sendMail(String from,String to,String subject,String content);
	
	/**
	 * 发送短信
	 * @param senderUID
	 * @param mobileNo
	 * @param content
	 * @return
	 */
	Map sendShortMessage(String senderUID, String mobileNo, String content);
	
}
