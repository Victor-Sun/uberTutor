package com.gnomon.pdms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportManager {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String reportUrl;
	private String reportMessage;
	private String contact;
	private String copyright;

	@Value("#{version['pdms.contact']}")
	public void Contact(String contact) {
		this.contact = contact;
	}
	public String getContact() {
		return contact;
	}

	@Value("#{version['pdms.copyright']}")
	public void Copyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCopyright() {
		return copyright;
	}

	@Value("#{version['pdms.version']}")
	public void ReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}
	
	public String getReportMessage() {
		return reportMessage;
	}

	@Value("#{application['reportserver.url']}")
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	
	public String getReportUrl() {
		return reportUrl;
	}
}

