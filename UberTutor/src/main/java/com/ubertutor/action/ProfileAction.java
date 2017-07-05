package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.SchoolEntity;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.ProfileService;

@Namespace("/main")
@AllowedMethods({"display","update","displaySchool","tutorStatus"})
public class ProfileAction extends PDMSCrudActionSupport<UserEntity>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private ProfileService profileService;
	private SchoolEntity schoolEntity;
	private UserEntity entity = SessionData.getLoginUser();
	private String fullname, email, mobile, bio, schoolName, isTutor;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getIsTutor() {
		return isTutor;
	}

	public void setIsTutor(String isTutor) {
		this.isTutor = isTutor;
	}

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
	 * @return 
	 * @throws Exception
	 */
	@Override
	public String save() throws Exception{
		try{
			String schoolId, msg, mobileNo = "";
			isTutor = Struts2Utils.getRequest().getParameter("isTutor");
			if(!profileService.isValidEmailAddress(email)){
				msg = "Invalid email, please check that your email is written correctly, and try again.";
				throw new Exception(msg);
			}
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
			if(schoolName== null || schoolName == ""){
				msg = "School cannot be empty";
				throw new Exception(msg);
			}
			schoolEntity = profileService.getSchoolByName(schoolName);
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
		return null;
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
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isTutor", entity.getIsTutor());
		this.writeSuccessResult(result);
	}

	public UserEntity getModel() {
		return null;
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
}
