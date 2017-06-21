package com.ubertutor.action;

import java.util.Date;
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
	private UserEntity entity = SessionData.getLoginUser();

	/**
	 * Sends Json to front end to display a user's profile
	 * Depends on whether the user has a school
	 * @throws Exception
	 */
	public void display() throws Exception{
		try{
			if(profileService.hasSchool(entity.getId()).isEmpty()){
				this.writeSuccessResult(profileService.getUserInfo(entity.getId()));
			}else{
				this.writeSuccessResult(profileService.getAllUserInfo(entity.getId()));
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
			String fullname, email, mobile, bio, school, schoolId, isTutor, msg, mobileNo = "";
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
			if(fullname.isEmpty()){
				msg = "Fullname cannot be empty, please fill out your fullname and try again.";
				throw new Exception(msg);
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
			schoolId = schoolEntity.getId().toString();
			bio = Struts2Utils.getRequest().getParameter("bio");
			entity.setFullname(fullname);
			entity.setEmail(email);
			entity.setMobile(mobileNo);
			entity.setBio(bio);
			entity.setSchoolId(schoolId);
			entity.setIsTutor(isTutor);
			entity.setUpdateBy(entity.getId().toString());
			entity.setUpdateDate(new Date());
			profileService.updateProfile(entity);
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
		entity = SessionData.getLoginUser();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isTutor", entity.getIsTutor());
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
