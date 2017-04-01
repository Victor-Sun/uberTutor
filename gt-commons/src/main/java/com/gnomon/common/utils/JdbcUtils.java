package com.gnomon.common.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class JdbcUtils {
	public static void insert(JdbcTemplate jdbcTemplate,String tablename,Map<String,Object>kv){
		StringBuilder valueSql = new StringBuilder(" values(");
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		sql.append(tablename).append("(");
		final List<Object> values = new ArrayList<Object>();
		String[] names = kv.keySet().toArray(new String[0]);
		for(int i=0; i<names.length;i++){
			String name = names[i];
			
			//不插入空属性
			Object o = kv.get(name);
			if(o instanceof String){
				if(StringUtils.isEmpty((String)o)){
					continue;
				}
			}else if(o == null){
				continue;
			}
			
			values.add(kv.get(name));
			sql.append(name);
			valueSql.append("?");
			if(i<names.length -1){
				sql.append(",");
				valueSql.append(",");
			}else{
				sql.append(") ");
				valueSql.append(")");
			}
		}
		
		sql.append(valueSql);
		jdbcTemplate.update(sql.toString(),new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				for(int i=1;i<=values.size();i++){
					Object value = values.get(i-1);
					ps.setObject(i, value);
				}
			}
			
		});
	}
	
	public static void update(JdbcTemplate jdbcTemplate,String tablename,Long id,Map<String,Object>kv) {
		StringBuilder sql = new StringBuilder("UPDATE  ");
		sql.append(tablename).append(" SET ");
		final List<Object> values = new ArrayList<Object>();
		String[] names = kv.keySet().toArray(new String[0]);
		for(int i=0; i<names.length;i++){
			String name = names[i];
			values.add(kv.get(name));
			sql.append(name);
			sql.append("=?");
			if(i<names.length -1){
				sql.append(",");
			}else{
				sql.append("  ");
			}
		}
		sql.append(" WHERE ID="+id);
		
		jdbcTemplate.update(sql.toString(),new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				for(int i=1;i<=values.size();i++){
					Object value = values.get(i-1);
					if(value instanceof Date){
						ps.setDate(i, new java.sql.Date(((Date)value).getTime()));
					}else{
						ps.setObject(i, value);
					}
				}
			}
			
		});
	}
	
	/**
	 * 调用存储过程
	 * @param jdbcTemplate
	 * @param sql  调用的存储过程和参数。例如：{call PROCEDURE_TEST(?, ?)}
	 * @param declaredParameters
	 * @param parameters
	 * @return
	 */
	public static Map<String, Object> call(JdbcTemplate jdbcTemplate,final String sql,final List<SqlParameter> declaredParameters,final List<Object> parameters) {
		
		CallableStatementCreator csc = new CallableStatementCreator(){

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall(sql);
				for(int parameterIndex=1;parameterIndex<=declaredParameters.size();parameterIndex++){
					SqlParameter sp = declaredParameters.get(parameterIndex);
					if(sp instanceof SqlInOutParameter){
						//注册输出参数
						cs.registerOutParameter(parameterIndex, sp.getSqlType());
					}
				}
				//传入参数
				for(int parameterIndex=1;parameterIndex<=declaredParameters.size();parameterIndex++){
					Object parameter = parameters.get(parameterIndex);
					cs.setObject(parameterIndex, parameter);
				}
				return cs;
			
			} 
		};
		return jdbcTemplate.call(csc, declaredParameters);
		
	}
	
	
	public static void main(String[] args) {
		final String callProcedureSql = "{call PROCEDURE_TEST(?, ?)}";  

		 //参数定义
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		declaredParameters.add(new SqlInOutParameter("inOutName", Types.VARCHAR));
		declaredParameters.add(new SqlOutParameter("outId", Types.INTEGER));

		List<Object> parameters = new ArrayList<Object>();// 传入参数
		parameters.add("aaaaa");
		Integer id = 0;
		parameters.add(id);
		JdbcTemplate jdbcTemplate = null;

		JdbcUtils.call(jdbcTemplate, callProcedureSql, declaredParameters, parameters);
	}
	
}
