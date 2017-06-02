package com.ubertutor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;

@Namespace("/main")
public class ProfileAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfileService profileService;
	
	public void display() throws Exception{
		try{
			UserEntity user = SessionData.getLoginUser();
			JsonResult result = new JsonResult();
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			Map<String, String> profileResult = new HashMap<String,String>();
			List<Map<String, String>> list = new ArrayList();
			
			profileResult.put("fullname",user.getFullname());
			profileResult.put("email",user.getEmail());
			profileResult.put("mobile",user.getMobile());
			profileResult.put("bio",user.getBio());
			profileResult.put("school",profileService.getSchool((long)2).getName());
			list.add(profileResult);
			for (Map<String, String> map : list) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("userId", map.get("USER_ID"));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			data.add(profileResult);
			System.out.println(profileResult.toString());
			System.out.println(data);
//			Struts2Utils.renderJson(profileResult);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
