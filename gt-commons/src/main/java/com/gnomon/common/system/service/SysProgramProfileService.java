package com.gnomon.common.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.dao.SysProgramProfileVMDAO;
import com.gnomon.common.system.entity.SysProgramProfileVMEntity;

@Service
@Transactional
public class SysProgramProfileService {
	
	public static final String SYS_PROGRAM_PROFILE_DIRECTOR = "7692EC0BD4A344DDB763955858E8174A"; // 项目总监
	public static final String SYS_PROGRAM_PROFILE_PM = "AF361385A7F144A696CC5C98F7D1419F"; // 项目经理
	public static final String SYS_PROGRAM_PROFILE_FM = "FFD578E366BA46AEBA0DA21AA9CD6DAA"; // 专业经理
	public static final String SYS_PROGRAM_PROFILE_QM = "E57DB49C98DD41E9ADD79E0AD77B89AD"; // 质量经理
	
	@Autowired
	private SysProgramProfileVMDAO sysProgramProfileVMDAO;
	
	/**
	 * 项目角色信息取得
	 */
	public List<SysProgramProfileVMEntity> getSysProfileList() {
		List<Object> params = new ArrayList<Object>();
		String hql = "FROM SysProgramProfileVMEntity WHERE id not in(?, ?, ?, ?)";
		params.add(SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(SYS_PROGRAM_PROFILE_PM);
		params.add(SYS_PROGRAM_PROFILE_FM);
		params.add(SYS_PROGRAM_PROFILE_QM);
        return this.sysProgramProfileVMDAO.find(hql, params.toArray());
    }
	
	/**
	 * 一级计划项目角色信息取得
	 */
	public List<SysProgramProfileVMEntity> getSysProfileLevelOneList() {
		List<Object> params = new ArrayList<Object>();
		String hql = "FROM SysProgramProfileVMEntity WHERE id not in(?, ?, ?, ?) and planLevel = 1";
		params.add(SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(SYS_PROGRAM_PROFILE_PM);
		params.add(SYS_PROGRAM_PROFILE_FM);
		params.add(SYS_PROGRAM_PROFILE_QM);
        return this.sysProgramProfileVMDAO.find(hql, params.toArray());
    }
	
	/**
	 * 二级计划项目角色信息取得
	 */
	public List<SysProgramProfileVMEntity> getSysProfileLevelTwoList() {
		List<Object> params = new ArrayList<Object>();
		String hql = "FROM SysProgramProfileVMEntity WHERE id not in(?, ?, ?, ?) and planLevel = 2 ";
		params.add(SYS_PROGRAM_PROFILE_DIRECTOR);
		params.add(SYS_PROGRAM_PROFILE_PM);
		params.add(SYS_PROGRAM_PROFILE_FM);
		params.add(SYS_PROGRAM_PROFILE_QM);
        return this.sysProgramProfileVMDAO.find(hql, params.toArray());
    }

}
