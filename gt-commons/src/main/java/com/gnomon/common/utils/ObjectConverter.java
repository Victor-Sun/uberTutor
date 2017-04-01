/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.common.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.lang.StringUtils;

/**
 * @author frank
 *
 */
public class ObjectConverter {

	//private static ConvertUtils cu = new ConvertUtils();
	static{
		ConvertUtils.register(new Converter(){

			@Override
			public Object convert(Class cls, Object value) {
				SimpleDateFormat sdf = new SimpleDateFormat();
				return sdf.format((Date)value);
			}}, Date.class);

//		Converter dateConverter = ConvertUtils.lookup(Date.class);
		//System.out.println(dateConverter);

	}

	
	public <T> T convert(Class<T> cls,Object value){
		T returnValue = null;
		if(value == null){
			return null;
		}
		
		if(cls == String.class){

			StringConverter sc = new StringConverter();
			return returnValue = (T) sc.convert(cls, value);
		}
		return returnValue;
	}
	
	public static Long convert2Long(Object value){
		if(value== null){
			return 0L;
		}
		String str = convert2String(value);
		if(StringUtils.isEmpty(str)){
			return 0L;
		}
		
		Long r = 0L;
		try{
			r= Long.parseLong(str);
		}catch(Exception e){
			//ignore
		}
		return r;
	}
	
	public static Integer convert2Integer(Object value){
		if(value== null){
			return null;
		}
		String str = convert2String(value);
		if(StringUtils.isEmpty(str)){
			return null;
		}
		
		Integer r = null;
		try{
			r= Integer.parseInt(str);
		}catch(Exception e){
			//ignore
		}
		return r;
	}
	
	public static Double convert2Double(Object value){
		if(value == null){
			return null;
		}
		return Double.parseDouble(convert2String(value));
	}
	
	public static String convert2String(Object value,String defaultValue){
		String r = convert2String(value);
		if(r == null){
			return defaultValue;
		}else{
			return r;
		}
	}	
	public static String convert2String(Object value){
		String r = null;
		if(value == null){
			return "";
		}
		if(value.getClass() == java.lang.String.class){
			r = (String)value;
		}else if(value.getClass() == java.util.Date.class){
			r = DateUtils.formate((Date)value, "yyyy-MM-dd HH:mm:ss");
		}else if (value.getClass() == java.sql.Date.class){
			java.util.Date d = new java.util.Date(((java.sql.Date)value).getTime());
			r = DateUtils.formate((Date)d , "yyyy-MM-dd HH:mm:ss");
		}else if (value.getClass() == java.sql.Timestamp.class){
			Date d = new Date(((java.sql.Timestamp)value).getTime());
			r = DateUtils.formate((Date)value, "yyyy-MM-dd HH:mm:ss");
		}
		else if(value.getClass() == Integer.class){
			r = String.valueOf((Integer)value);
		}else if(value.getClass() == Double.class){
			r = String.valueOf((Double)value);
		}else if(value.getClass() == Long.class){
			r = String.valueOf((Long)value);
		}else if(value.getClass() == Long.class){
			r = String.valueOf((Long)value);
		}else if(value.getClass() == Boolean.class){
			r = String.valueOf((Boolean)value);
		}else if(value.getClass() == BigDecimal.class){
			r = ((BigDecimal)value).toString();
		}
		
		return r;
	}
	
	public static String convertDate2String(Object value,String formate){
		String r = null;
		if(value == null){
			return "";
		}
		if(value.getClass() == java.lang.String.class){
			r = (String)value;
		}else if(value.getClass() == java.util.Date.class){
			r = DateUtils.formate((Date)value, formate);
		}else if (value.getClass() == java.sql.Date.class){
			java.util.Date d = new java.util.Date(((java.sql.Date)value).getTime());
			r = DateUtils.formate((Date)d ,formate);
		}else if (value.getClass() == java.sql.Timestamp.class){
			Date d = new Date(((java.sql.Timestamp)value).getTime());
			r = DateUtils.formate((Date)value, formate);
		}
		
		return r;
	}
	
	public static Date convert2Date(Object value){
		Date r = null;
		if(value == null){
			return null;
		}
		
		if(value.getClass() == java.util.Date.class){
			r = (java.util.Date)value;
		}else if(value.getClass() == java.sql.Date.class){
			r = new Date(((java.sql.Date)value).getTime());
		}else if(value.getClass() == Timestamp.class){
			r = ((Timestamp)value);
		}
		
		return r;
	}
	
	public static String convertBoolean2String(Object flag){
		if(Boolean.valueOf(true).equals(flag)){
			return "Y";
		}
		if("true".equals(flag)){
			return "Y";
		}
		return "N";
	}
	
	public static boolean convert2Boolean(Object flag){
		if("Y".equals(flag)){
			return true;
		}
		if("true".equals(flag)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Object value = null;
		value="aaaa";
		
		System.out.println(ObjectConverter.convert2String(value));
		
		value = 12;
		System.out.println(ObjectConverter.convert2String(value));
		
		value = new Date();
		System.out.println(ObjectConverter.convert2String(value));
	}
}
