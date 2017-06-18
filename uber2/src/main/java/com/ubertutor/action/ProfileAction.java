package com.ubertutor.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.ubertutor.entity.SchoolEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;
import com.ubertutor.service.SessionProfileService;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
public class ProfileAction extends PDMSCrudActionSupport<UserEntity>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfileService profileService;
	@Autowired
	private SessionProfileService tutorProfileService;
	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	private SchoolEntity schoolEntity;
	private UserEntity user = SessionData.getLoginUser();

	/**
	 * Sends Json to front end to display a user's profile
	 * Depends on whether the user has a school
	 * @throws Exception
	 */
	public void display() throws Exception{
		try{
			if(profileService.hasSchool(user.getId()).isEmpty()){
				this.writeSuccessResult(profileService.getUserInfo(user.getId()));
			}else{
				this.writeSuccessResult(profileService.getAllUserInfo(user.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Updates a user's profile
	 * @throws Exception
	 */
	public void update() throws Exception{
		try{
			UserEntity user = SessionData.getLoginUser();
			String fullname, email, mobile, bio, school, schoolid, isTutor, msg, mobileNo = "";
			fullname = Struts2Utils.getRequest().getParameter("fullname");
			email = Struts2Utils.getRequest().getParameter("email");
			isTutor = Struts2Utils.getRequest().getParameter("isTutor");
			if(!profileService.isValidEmailAddress(email)){
				msg = "Invalid email, please check that your email is written correctly, and try again.";
				throw new Exception(msg);
			}
			mobile = Struts2Utils.getRequest().getParameter("mobile");
			String[] temp = mobile.split("[-.()]");
			for(int i = 0; i < temp.length; i++){
				mobileNo += temp[i];
			}
			if(!mobileNo.matches("\\d{10}")){
				msg = "Invalid phone number, please enter 10 digits.";
				throw new Exception(msg);
			}
			school = Struts2Utils.getRequest().getParameter("name");
			if(school== null || school == ""){
				msg = "School cannot be empty";
				throw new Exception(msg);
			}
			schoolEntity = profileService.getSchoolByName(school);
			schoolid = schoolEntity.getId().toString();
			bio = Struts2Utils.getRequest().getParameter("bio");
			profileService.updateProfile(user.getId().toString(), fullname, email, mobileNo, bio, schoolid, isTutor);
		}catch(Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Separate function to display school
	 */
	public void displaySchool(){
		try{
			this.writeSuccessResult(profileService.getSchoolList());
		}catch(Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Get user's isTutor status
	 */
	public void tutorStatus(){
		UserEntity user = SessionData.getLoginUser();
		user = SessionData.getLoginUser();
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
