/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package com.gnomon.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 公司常用的工具类，封装了一些常用的方法
 * @author thomas.ma
 *
 */
public class CommonUtils {
	
	/**
	 * 从AWS特定的用户名称格式中抽取UserId
	 * @TODO 临时方法，以后应该不需要使用此方法，统一使用userid
	 * @param fullStr
	 * @return
	 */
	public static String getUseridFromFullStr(String fullStr){
		String userid="";
		if(StringUtils.isNotEmpty(fullStr) && (fullStr.indexOf("<") != -1)){
			userid = fullStr.substring(0,fullStr.indexOf("<"));
		}else{
			userid = fullStr;
		}
		
		return userid;
	}
	
	public static String changeToHtml(String text) {
		text = text.replaceAll("&", "&amp;");
		text = text.replaceAll("\"", "&quot;");
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		//text = text.replaceAll(" ", "&nbsp;");
		text = text.replaceAll("\n", "<br/>");
		text = text.replaceAll("\r\n", "<br/>");
		return text;
	}
	
	public static String getUUID(){
		return Identities.uuid2();
	}
	
	public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     * 
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串2
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String aaa = "app_version_fld";
        System.out.println(underlineToCamel(aaa));
        System.out.println(underlineToCamel2(aaa));
        aaa = "appVersionFld";
        System.out.println(camelToUnderline(aaa));
    
    }
	
}
