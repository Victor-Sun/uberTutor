package com.gnomon.common.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gnomon.common.base.StringIdEntity;

@Entity
@Access(AccessType.FIELD)
@Table(name = "GT_DMS_APP_OBS")
public class GTDmsAppObs extends StringIdEntity {

	@Column(name = "APP_ID")
	private Long appId;
	@Column(name = "OBS_ID")
	private Long obsId;
	@Column(name = "INCLUDE_CHILD_OBS")
	private boolean includeChildOBS;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getObsId() {
		return obsId;
	}

	public void setObsId(Long obsId) {
		this.obsId = obsId;
	}

	public boolean isIncludeChildOBS() {
		return includeChildOBS;
	}

	public void setIncludeChildOBS(boolean includeChildOBS) {
		this.includeChildOBS = includeChildOBS;
	}

}
