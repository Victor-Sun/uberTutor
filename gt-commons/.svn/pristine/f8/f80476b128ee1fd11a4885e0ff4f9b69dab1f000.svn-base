package com.gnomon.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 静态获取BeanFactory的工具类，除非特殊情况，尽量避免使用
 * @author frank
 *
 */
@Service("serviceLocator")
@Lazy(false)
public class SpringServiceUtils implements BeanFactoryAware {  
    private static BeanFactory beanFactory = null;  
   
    private static SpringServiceUtils servlocator = null;  
   
    public void setBeanFactory(BeanFactory factory) throws BeansException {  
        beanFactory = factory;  
    }  
   
    public BeanFactory getBeanFactory() {  
        return beanFactory;  
    }  
   
    /** 
    * 创建读取Bean服务类实例
    */  
    public static SpringServiceUtils getInstance() {  
        if (servlocator == null){  
              servlocator = (SpringServiceUtils) beanFactory.getBean("serviceLocator");  
        }  
        return servlocator;  
    }  
   
    /** 
    * 根据提供的bean名称得到相应的服务类      
    * @param servName bean名称      
    */  
    public static Object getService(String servName) {  
     return getInstance().getBeanFactory().getBean(servName);  
    }  
    
    /**
     * 根据类获取指定的服务类实例
     * @param clazz
     * @return
     */
    public static <T> T getService(Class<T> clazz) {  
    	return getInstance().getBeanFactory().getBean(clazz);  
    }  
   
    /** 
    * 根据提供的bean名称得到对应于指定类型的服务类 
    * @param servName bean名称 
    * @param clazz 返回的bean类型,若类型不匹配,将抛出异常 
    */  
    public static <T> T getService(String servName, Class<T> clazz) {  
        return getInstance().getBeanFactory().getBean(servName, clazz);  
    }  
     
      
     /** 
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true  
     * @param name 
     * @return boolean 
     */  
     public static boolean containsBean(String name) {  
       return getInstance().getBeanFactory().containsBean(name);  
     }  
      
     /** 
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）    
     * @param name 
     * @return boolean 
     * @throws NoSuchBeanDefinitionException 
     */  
     public static boolean isSingleton(String name)  {  
       return getInstance().getBeanFactory().isSingleton(name);  
     }  
      
     /** 
     * @param name 
     * @return Class 注册对象的类型 
     * @throws NoSuchBeanDefinitionException 
     */  
     public static Class<?> getType(String name) {  
       return getInstance().getBeanFactory().getType(name);  
     }  
      
     /** 
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名    
     * @param name 
     * @return 
     * @throws NoSuchBeanDefinitionException 
     */  
     public static String[] getAliases(String name) {  
       return getInstance().getBeanFactory().getAliases(name);  
     }  
  
}  