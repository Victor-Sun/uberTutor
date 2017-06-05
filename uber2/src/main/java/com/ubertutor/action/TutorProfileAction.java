package com.ubertutor.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.ubertutor.dao.UserDAO;
import com.ubertutor.service.TutorProfileService;

@Namespace("/main")
public class TutorProfileAction extends ActionSupport{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private TutorProfileService tutorProfileService;
    @Autowired
    private UserDAO userDAO;
    
}
