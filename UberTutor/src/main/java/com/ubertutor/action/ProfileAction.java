package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

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
	private UserEntity userEntity = SessionData.getLoginUser();
	private String fullname, email, mobile, bio, schoolName, isTutor;
	
	/**
	 * Returns fullname
	 * @return
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * @param fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Returns email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns mobile
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Set mobile
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Returns bio
	 * @return
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * Set bio
	 * @param bio
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * Returns schoolName
	 * @return
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * Set schoolname
	 * @param schoolName
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * Returns isTutor
	 * @return
	 */
	public String getIsTutor() {
		return isTutor;
	}

	/**
	 * Set isTutor
	 * @param isTutor
	 */
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
			if(profileService.hasSchool(userEntity.getId()).isEmpty()){
				this.writeSuccessResult(profileService.getUserInfo(userEntity.getId()));
			}else{
				this.writeSuccessResult(profileService.getAllUserInfo(userEntity.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}

	/**
	 * Save a user's profile
	 * @return 
	 * @throws Exception
	 */
	@Override
	public String save() throws Exception{
		try{
			String schoolId, msg, mobileNo = "";
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
			userEntity.setFullname(fullname);
			userEntity.setEmail(email);
			userEntity.setMobile(mobileNo);
			userEntity.setBio(bio);
			userEntity.setSchoolId(schoolId);
			userEntity.setIsTutor(isTutor);
			userEntity.setUpdateBy(userEntity.getId().toString());
			userEntity.setUpdateDate(new Date());
			profileService.updateProfile(userEntity);
		}catch(Exception e){
			e.printStackTrace();
			this.writeErrorResult(e);
		}
		return null;
	}

	/**
	 * Sends School to the front end
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
		result.put("isTutor", userEntity.getIsTutor());
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
