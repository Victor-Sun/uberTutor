package com.gnomon.common.system.service;

import java.util.List;
import java.util.Map;

import com.gnomon.common.exception.ServiceException;
import com.gnomon.common.system.entity.GTProgramProfile;

public interface GTProgramProfileService {
	
	public List<GTProgramProfile> getProfileList() throws ServiceException;
	
	public List<Map<String,Object>> getPrivilegeList(String profileId) throws ServiceException;
	
	public List<Map<String,Object>> getPrivilegeList() throws ServiceException;
	
	public void setProfile(String id,String type) throws ServiceException;
	
	public void initialProgramProfileState() throws ServiceException;
	
	public void updateProfilePrivilege(String id,String type) throws ServiceException;
	
	public void insertProfilePrivilege(String profileId,String privilageCode,String allowFlag) throws ServiceException;
	
	public void saveProfileAllPrivilege(String profileId,String allowFlag) throws ServiceException;
	
	public void deleteProfile(String id) throws ServiceException;
	
	public void deleteProgramProfilePrivilege(String profileId) throws ServiceException;
	
	public void saveProfile(GTProgramProfile programProfile) throws ServiceException;
	

}
