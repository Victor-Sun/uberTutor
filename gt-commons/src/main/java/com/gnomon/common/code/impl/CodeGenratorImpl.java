package com.gnomon.common.code.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.gnomon.common.code.CodeGenerator;

@Service
public class CodeGenratorImpl implements CodeGenerator {

	@Override
	public String generate(String expression, Map<String, Object> context) {
		List<Variable> variables = new ArrayList<Variable>();
		for(String key : context.keySet()){
			variables.add(Variable.createVariable(key, context.get(key)));
		}
		//执行表达式
		String result = (String) ExpressionEvaluator.evaluate(expression,
		variables);
		return result;
	}

}
