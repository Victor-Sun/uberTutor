package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.SchoolEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;

@Namespace("/main")
public class ProfileAction extends PDMSCrudActionSupport<UserEntity>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfileService profileService;
	private SchoolEntity schoolEntity;
	private UserEntity user = SessionData.getLoginUser();

	/**
	 * Sends Json to front end to display a user's profile
	 * @throws Exception
	 */
	public void display() throws Exception{
		try{
			this.writeSuccessResult(profileService.getUserInfo(user.getId()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Updates a user's profile
	 * @throws Exception
	 */
	public void update() throws Exception{
		try{
			String fullname, email, mobile, bio, school, schoolid, msg, mobileNo = "";
			fullname = Struts2Utils.getRequest().getParameter("FULLNAME");
			email = Struts2Utils.getRequest().getParameter("EMAIL");
			mobile = Struts2Utils.getRequest().getParameter("MOBILE");
			String[] temp = mobile.split("[-.()]");
			for(int i = 0; i < temp.length; i++){
				mobileNo += temp[i];
			}
			if(!mobileNo.matches("\\d{10}")){
				msg = "Invalid phone number, please enter 10 digits";
				throw new Exception(msg);
			}
			school = Struts2Utils.getRequest().getParameter("NAME");
			if(school== null || school == ""){
				msg = "School cannot be empty";
				throw new Exception(msg);
			}
			schoolEntity = profileService.getSchoolByName(school);
			schoolid = schoolEntity.getId().toString();
			bio = Struts2Utils.getRequest().getParameter("BIO");
			profileService.updateProfile(user.getId().toString(), fullname, email, mobileNo, bio, schoolid);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Separate function to display school
	 */
	public void displaySchool(){
		JsonResult result = new JsonResult();
		try{
			result.buildSuccessResult(profileService.getSchoolList());
			Struts2Utils.renderJson(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function to register a user as a tutor
	 */
	public void registerAsTutor(){
		String s = Struts2Utils.getParameter("IS_TUTOR");
		profileService.registerAsTutor(user.getId(), s);
	}

	/**
	 * Get user's isTutor status
	 */
	public void tutorStatus(){
//		JsonResult result = new JsonResult();
		user = SessionData.getLoginUser();
//		result.buildSuccessResult(user.getIsTutor());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isTutor", user.getIsTutor());
		this.writeSuccessResult(result);
	}

	@Override
	public UserEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}
}
