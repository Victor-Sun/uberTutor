package com.ubertutor.action;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.ubertutor.service.TutorProfileService;
import com.ubertutor.service.TutorSubjectRegisterService;

@Namespace("/main")
public class ProfileAction extends PDMSCrudActionSupport<UserEntity>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfileService profileService;
	@Autowired
	private TutorProfileService tutorProfileService;
	@Autowired
	private TutorSubjectRegisterService tutorSubjectRegisterService;
	private SchoolEntity schoolEntity;
	private UserEntity user = SessionData.getLoginUser();
	private UserEntity userEntity = tutorProfileService.getUser(Long.parseLong(Struts2Utils.getRequest().getParameter("tutorId")));
	
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
	 * Sends Json to display the tutor's info
	 * @throws Exception
	 */
	public void displayProfileInfo() throws Exception{
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			if(userEntity.getIsTutor().equals("Y")){
				result.put("tutorName", tutorProfileService.getUser(userEntity.getId()).getId());
				int ratingAvg = tutorProfileService.getRatingTotal(userEntity.getId()) / tutorProfileService.getRatingCount(userEntity.getId());
				result.put("tutorRating", ratingAvg);
				result.put("tutorCompletedRequests", tutorProfileService.getTotalCompletedRequests(userEntity.getId())); 
				result.put("tutorBio", userEntity.getBio());
			} else {
				
			}

			this.writeSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}
	
	/**
	 * Sends Json to display a tutor's subjects
	 * @throws Exception
	 */
	public void displayTutorSubjects() throws Exception{
		try{
			this.writeSuccessResult(tutorSubjectRegisterService.getUserSubjects(userEntity.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e);
		}
	}
	
	/**
	 * Sends Json to display a tutor's feedbacks
	 * @throws Exception
	 */
	public void displayTutorFeedbacks() throws Exception{
		try {
			this.writeSuccessResult(tutorProfileService.getFeedback(userEntity.getId()));
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
			String fullname, email, mobile, bio, school, schoolid, msg, mobileNo = "";
			fullname = Struts2Utils.getRequest().getParameter("FULLNAME");
			email = Struts2Utils.getRequest().getParameter("EMAIL");
			if(!profileService.isValidEmailAddress(email)){
				msg = "Invalid email, please check that your email is written correctly, and try again.";
				throw new Exception(msg);
			}
			mobile = Struts2Utils.getRequest().getParameter("MOBILE");
			String[] temp = mobile.split("[-.()]");
			for(int i = 0; i < temp.length; i++){
				mobileNo += temp[i];
			}
			if(!mobileNo.matches("\\d{10}")){
				msg = "Invalid phone number, please enter 10 digits.";
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
	 * Function to register a user as a tutor
	 */
	public void registerAsTutor(){
		UserEntity user = SessionData.getLoginUser();
		String s = Struts2Utils.getParameter("IS_TUTOR");
		profileService.registerAsTutor(user.getId(), s);
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
