package com.gnomon.intergration.im.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.actionsoft.awf.services.WSDKException;
import com.gnomon.intergration.aws.AwsApi;
import com.gnomon.intergration.im.IMService;

@Service
public class IMServiceImpl implements IMService {

	@Override
	public int sendMail(String from, String to, String subject, String content) {
		
		try {
			return AwsApi.getInstance().getIM().sendMail(from, to, subject, content);
		} catch (WSDKException e) {
			throw new RuntimeException("邮件发送失败",e);
		}
	}

	@Override
	public Map sendShortMessage(String senderUID, String mobileNo, String content){
		try {
			return AwsApi.getInstance().getIM().sendShortMessage(senderUID, mobileNo, content);
		} catch (WSDKException e) {
			throw new RuntimeException("短信发送失败",e);
		}
	}
	

}
