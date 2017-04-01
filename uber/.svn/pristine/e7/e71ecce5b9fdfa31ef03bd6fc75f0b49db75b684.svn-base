package com.gnomon.pdms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ImsCodeTableDAO;
import com.gnomon.pdms.entity.ImsCodeTableEntity;
@Service
@Transactional
public class CodeInfoService {
	
	@Autowired
	private ImsCodeTableDAO imsCodeTableDAO;
	
	public Map<String, String> getCode(String codeType){
		Map<String, String> codeMap = new HashMap<String, String>();
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> codeList =
				this.imsCodeTableDAO.find(hql, codeType);
		for (ImsCodeTableEntity imsCodeTableEntity : codeList) {
			codeMap.put(imsCodeTableEntity.getCode(), imsCodeTableEntity.getName());
		}
		return codeMap;
	}
	
	public String getName(String codeType, String code){
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ? and code = ?";
		List<ImsCodeTableEntity> codeTableList = this.imsCodeTableDAO.find(hql, codeType, code);
		String name = "";
		if (codeTableList != null && codeTableList.size() > 0){
			name = ((ImsCodeTableEntity) codeTableList.get(0)).getName();
		}
		return name;
	}
}
