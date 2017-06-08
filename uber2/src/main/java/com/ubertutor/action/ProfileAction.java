package com.ubertutor.action;

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
			bio = Struts2Utils.getRequest().getParameter("BIO");
			school = Struts2Utils.getRequest().getParameter("NAME");
			schoolEntity = profileService.getSchoolByName(school);
			schoolid = schoolEntity.getId().toString();
			String[] temp = mobile.split("[-.()]");
			for(int i = 0; i < temp.length; i++){
				mobileNo += temp[i];
			}
			if(!mobileNo.matches("\\d{10}")){
				msg = "Invalid phone number";
				throw new Exception(msg);
			}
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
		profileService.registerAsTutor(user.getId());
	}

	/**
	 * Get user's isTutor status
	 */
	public void tutorStatus(){
		JsonResult result = new JsonResult();
		result = (user.getIsTutor().equals("Y"))?result.buildSuccessResult("true"):result.buildSuccessResult("false");
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
