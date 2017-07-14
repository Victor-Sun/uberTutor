package com.gnomon.common.web;
import javax.servlet.ServletContextEvent;    
    
import org.springframework.web.context.ContextLoaderListener;    
    
public class ApplicationListener extends ContextLoaderListener {    
    
    public void contextDestroyed(ServletContextEvent sce) {    
        // TODO Auto-generated method stub    
    
    }    
    
    public void contextInitialized(ServletContextEvent sce) {    
        String webAppRootKey = sce.getServletContext().getRealPath("/");    
        String contextPath = sce.getServletContext().getContextPath();
        System.setProperty("webRoot" , webAppRootKey);    
        System.setProperty("contextPath" , contextPath);    
    }    
    
}  