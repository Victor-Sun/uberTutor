package com.gnomon.pdms.procedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract class AbstractDBProcedureService {

	private static final String RETURN_ERROR = "E";

	@Autowired
	private SessionFactory sessionFactory;

	public Map<String, Object> executeProcess(DBProcedureWork work) {
		try {
			sessionFactory.getCurrentSession().doWork(work);

			if (StringUtils.isNotEmpty(work.getReturnCode())
					&& RETURN_ERROR.equals(work.getReturnCode())) {
				throw new DBProcedureExecuteException(work.getReturnCode(),
						work.getReturnMsg());
			}
			return work.returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	protected ARRAY getArray(Connection connection,
			List<Integer> partNumberList, String arrayType) throws SQLException {
		ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor(
				arrayType, connection);
		// 把list中的元素转换成自定义的类型
		ARRAY array = new ARRAY(descriptor, connection,
				partNumberList.toArray());
		return array;
	}
	
	protected ARRAY getStringArray(Connection connection,
			List<String> partStringList, String arrayType) throws SQLException {
		ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor(
				arrayType, connection);
		// 把list中的元素转换成自定义的类型
		ARRAY array = new ARRAY(descriptor, connection,
				partStringList.toArray());
		return array;
	}
}
