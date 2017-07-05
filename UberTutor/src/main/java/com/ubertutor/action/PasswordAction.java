package com.ubertutor.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.PDMSCrudActionSupport;
import com.gnomon.common.utils.EncryptUtil;
import com.gnomon.common.web.SessionData;
import com.ubertutor.entity.UserEntity;
import com.ubertutor.service.LoginService;
import com.ubertutor.service.PasswordService;

@Namespace("/main")
@AllowedMethods("save")
public class PasswordAction extends PDMSCrudActionSupport<UserEntity>{
    private static final long serialVersionUID = 1L;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordService passwordService;
    private UserEntity entity = SessionData.getLoginUser();
    private String currentPassword, newPassword, newPassword2;
    
    public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

	/**
     * Update Password function
     */
	@Override
    public String save(){
		try{
			String username = SessionData.getUserId();
			String msg;
			Long userId = Long.parseLong(SessionData.getLoginUserId());
			if(!loginService.verifyUserPassword(username, EncryptUtil.encrypt(currentPassword))){
				msg = "Password is incorrect! Please confirm your password and try again.";
				throw new Exception(msg);
			}
			if(!newPassword.equals(newPassword2)){
				msg = "Passwords do not match, please check your passwords then submit again!";
				throw new Exception(msg);
			}
			if(newPassword.equals(currentPassword)){
				msg = "Your new password cannot be the same as your current password! Enter a different password and try again!";
				throw new Exception(msg);
			}
			entity.setPassword(EncryptUtil.encrypt(newPassword));
			entity.setUpdateBy(userId.toString());
			entity.setUpdateDate(new Date());
			passwordService.updatePassword(entity);
			this.writeSuccessResult(SUCCESS);
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
		return null;
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
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
	}
}
