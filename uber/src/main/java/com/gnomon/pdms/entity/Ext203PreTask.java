package com.gnomon.pdms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PM_EXT203_PRE_TASK")
public class Ext203PreTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="PM_EXT203_PRE_TASK_SEQ")     
	@SequenceGenerator(name="PM_EXT203_PRE_TASK_SEQ", sequenceName="PM_EXT203_PRE_TASK_SEQ")
	private Long id;
	
	//
	@Column(name="TASK_ID") 
	private Long taskId;	
	
	@Column(name="PRE_TASK_ID") 
	private Long preTaskId;	
	
	@Column(name="LAG_PERIOD") 
	private Long lagPeriod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getPreTaskId() {
		return preTaskId;
	}

	public void setPreTaskId(Long preTaskId) {
		this.preTaskId = preTaskId;
	}

	public Long getLagPeriod() {
		return lagPeriod;
	}

	public void setLagPeriod(Long lagPeriod) {
		this.lagPeriod = lagPeriod;
	}	
	
	

	
	
}
