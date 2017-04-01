package com.gnomon.common.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="tasks")
public class Task {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private int id;

	@Column(name="Name", nullable=false)
	private String name;
	
	@Column(name="StartDate", nullable=false)
	private String startDate;
	
	@Column(name="EndDate", nullable=false)
	private String endDate;
	
	@Column(name="parentId", nullable=true)
	private Integer parentId;
	
	@Column(name="PhantomId", nullable=true)
	private String phantomId;
	
	@Column(name="PhantomParentId", nullable=true)
	private String phantomParentId;
	
	@Column(name="leaf", nullable=false)
	private Boolean leaf;
	
	@Column(name="Duration", nullable=false)
	private String duration;
	
	@Column(name="DurationUnit", nullable=false)
	private String durationUnit;
	
	@Column(name="PercentDone", nullable=false)
	private String percentDone;
	
	@Column(name="Resizable", nullable=false)
	private Boolean resizable;
	
	@Column(name="Draggable", nullable=false)
	private Boolean draggable;
	
	@Column(name="Cls", nullable=false)
	private String cls;
	
	@JsonProperty("Id")
	public int getId() {
		return id;
	}
	
	@JsonProperty("Id")
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("PhantomId")
	public String getPhantomId() {
		return phantomId == null ? "" : phantomId;
	}
	
	@JsonProperty("PhantomId")
	public void setPhantomId(String id) {
		this.phantomId = id;
	}
	
	@JsonProperty("PhantomParentId")
	public String getPhantomParentId() {
		return phantomParentId == null ? "" : phantomParentId;
	}
	
	@JsonProperty("PhantomParentId")
	public void setPhantomParentId(String id) {
		this.phantomParentId = id;
	}
	
	@JsonProperty("Name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("StartDate")
	public String getStartDate() {
		return startDate;
	}
	
	@JsonProperty("StartDate")
	public void setStartDate(String start) {
		this.startDate = start;
	}
	
	@JsonProperty("EndDate")
	public String getEndDate() {
		return endDate;
	}
	
	@JsonProperty("EndDate")
	public void setEndDate(String end) {
		this.endDate = end;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public void setParentId(Integer id) {
		this.parentId = id;
	}
	
	@JsonProperty("PercentDone")
	public String getPercentDone() {
		return percentDone;
	}
	
	@JsonProperty("PercentDone")
	public void setPercentDone(String done) {
		this.percentDone = done;
	}
	
	@JsonProperty("Duration")
	public String getDuration() {
		return duration;
	}
	
	@JsonProperty("Duration")
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@JsonProperty("DurationUnit")
	public String getDurationUnit() {
		return durationUnit;
	}
	
	@JsonProperty("DurationUnit")
	public void setDurationUnit(String unit) {
		this.durationUnit = unit;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@JsonProperty("Resizable")
	public Boolean getResizable() {
		return resizable != null && resizable;
	}
	
	@JsonProperty("Resizable")
	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}
	
	@JsonProperty("Draggable")
	public Boolean getDraggable() {
		return draggable != null && draggable;
	}
	
	@JsonProperty("Draggable")
	public void setDraggable(Boolean draggable) {
		this.draggable = draggable;
	}
	
	@JsonProperty("Cls")
	public String getCls() {
		return cls == null ? "" : cls;
	}
	
	@JsonProperty("Cls")
	public void setCls(String cls) {
		this.cls = cls;
	}
}
