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
public class GTDmsResourceObs extends StringIdEntity {
	
	public static final String RESOURCE_TYPE_DOCUMENT = "D";
	public static final String RESOURCE_TYPE_FOLDER = "F";
	
	@Column(name = "RESOURCE_TYPE")
	private String resourceType;
	@Column(name = "RESOURCE_ID")
	private Long resourceId;
	@Column(name = "OBS_ID")
	private Long obsId;
	@Column(name = "INCLUDE_CHILD_OBS")
	private boolean includeChildOBS;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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
