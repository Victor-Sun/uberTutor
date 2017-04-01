package com.gnomon.common.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Access(AccessType.FIELD)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="GT_PROGRAM_PROFILE")
public class GTProgramProfile extends StringIdEntity{
	
	@Column(name="PROFILE_NAME")
	private String profileName;
	@Column(name="DEFAULT_FLAG")
	private String defaultFlag;
	@Column(name="SUPERUSER_FLAG")
	private String superuserFlag;

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getSuperuserFlag() {
		return superuserFlag;
	}

	public void setSuperuserFlag(String superuserFlag) {
		this.superuserFlag = superuserFlag;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	
	
}
