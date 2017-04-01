package com.gnomon.pdms.common;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gnomon.common.utils.CommonUtils;
import com.gnomon.pdms.constant.PDMSConstants;
  
/** 
 * 共通函数类
 */  
public class PDMSCommon {
	
    /** 
     * 生成UUID
     */  
    public static String generateUUID() {
    	return CommonUtils.getUUID();
    }
    
    /** 
     * 空串判断（true：空、false：非空）
     */
    public static boolean isNull(String value) {
    	if (value == null || "".equals(value)) {
    		return true;
    	}
    	return false;
    }
    
    /** 
     * 空串判断（true：非空、false：空）
     */
    public static boolean isNotNull(String value) {    	
    	return ! isNull(value);
    }
    
    /** 
     * 字符串变换
     */
    public static String nvl(Object obj, String strConv) {
        if (obj == null || "".equals(obj)) {
            return strConv;
        } else {
            return obj.toString();
        }
    }
    
    /** 
     * 字符串变换
     */
    public static String nvl(Object obj) {
        return nvl(obj, "");
    }
    
    /**
     * 字符串分割返回List
     */
    public static List<String> split(String buf, String split) {
        List<String> result = new ArrayList<String>();
		if (isNull(buf)) {
			return result;
		}
		String sub_buf = "";
		int indx_from = 0;
		int indx_to = buf.indexOf(split);

		if (indx_to < 0) {
			result.add(buf);
			return result;
		}
		while (true) {
			if (indx_to < 0) {
				sub_buf = buf.substring(indx_from);
				result.add(sub_buf);
				break;
			}
			if (indx_from > indx_to) {
				break;
			}
			sub_buf = buf.substring(indx_from, indx_to);
			result.add(sub_buf);
			indx_from = indx_to + split.length();
			indx_to = buf.indexOf(split, indx_from);
		}
        return result;
    }
    
    /**
     * 取得任务类型名称取得
     */
    public static String getTaskTypeName(String processStepId, String processCode) {
    	String taskTypeName = "节点任务";
    	if (PDMSConstants.PROCESS_CODE_PROGRESS_STATUS.equals(processCode)) {
    		taskTypeName = "风险状态变更流程";
    	} else if (! "1".equals(processStepId) &&
    			PDMSConstants.PROCESS_CODE_DELIVERABLE.equals(processCode)) {
    		taskTypeName = "任务完成审批流程";
    	}
    	return taskTypeName;
    }
    
    /**
     * 字符串左侧填补空位
     */
    public static String padLeft(String string, int length, char padChar) {
        if (string == null) {
            return null;
        }
        int n = length - string.length();
        if (n <= 0) {
            return string;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++){
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }
    
    /**
     * String和int转换
     */
    public static int toInt(String str) {
        if (isNotNull(str)) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }
    
    /**
     * String和Long转换
     */
    public static Long toLong(String num) {
        if (isNotNull(num)) {
            Long lng = new Long(num);
            return lng;
        } else {
            return null;
        }
    }
    
    /**
     * java.util.date和java.sql.date转换
     */
    public static java.sql.Date toSqlDate(Date date) {
    	if (date == null) {
    		return null;
    	}
        return new java.sql.Date(date.getTime());
    }
}  