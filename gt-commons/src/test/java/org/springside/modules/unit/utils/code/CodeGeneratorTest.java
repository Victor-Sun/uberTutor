package org.springside.modules.unit.utils.code;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.gnomon.common.code.CodeGenerator;
import com.gnomon.common.code.impl.CodeGenratorImpl;

public class CodeGeneratorTest {
	/**
	 	
		 位置”1” -文件类型/类别		documentType.code
		 位置”2 ”-责任部门或来源		deptOrSrc
		 位置”3”- TS16949标准章节号	chapterNumber
		 位置”4”-同类文件序号			3位序列号，序列名称为文件类型代码
		 位置”5”-业务代码				businessCode
		 位置”6”-该文件应用范围		applyScope
	 */
//	private String expressionStr = "documentType\"-\"deptOrSrc\"-\"chapterNumber$SEQ(\"DMS\",documentType,3)\"-\"businessCode\"-\"applyScope";
	private String expressionStr = "documentType+\"-\"+deptOrSrc+\"-\"+chapterNumber+$SEQ(\"DMS\",documentType,3)+\"-\"+businessCode+\"-\"+applyScope";
	private String expextGenerateCode = "AM-BB-1001-PB-MR";
	
	CodeGenerator codeGenerator = new CodeGenratorImpl();
	
	static Map<String, Object> context = new HashMap<String,Object>();
	static{
		context.put("documentType", "AM");
		context.put("deptOrSrc", "BB");
		context.put("chapterNumber", "1");
		context.put("businessCode", "PB");
		context.put("applyScope", "MR");
		
	}
	@Test
	public void test(){
		/**
		 * 支持的公式：
		 * $SEQ(<模块名称>,<序列名称>,<位数>)	获得序列的值
		 */
		String code = codeGenerator.generate(expressionStr, context);
		Assert.assertEquals(expextGenerateCode, code);
		
	}

}
