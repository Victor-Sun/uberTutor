package com.gnomon.common.code.impl;

import com.gnomon.common.utils.SequenceTools;
import com.gnomon.common.utils.SpringServiceUtils;

public class CodeFunctions {
	
	private SequenceTools getSequenceTools(){
		return SpringServiceUtils.getService(SequenceTools.class);
	}
	
	/**
	 * 序号函数
	 * @param module
	 * @param type
	 * @param width
	 * @return
	 */
	public String seq(String module,String type,int width){
//		return "001";
		return getSequenceTools().getSequence(module, type,width);
	}
	
	
}
