package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.pdms.dao.PMProgramVMDAO;
import com.gnomon.pdms.dao.VPmTempProgramDAO;
import com.gnomon.pdms.entity.PMProgramVMEntity;
import com.gnomon.pdms.entity.VPmTempProgramEntity;
import com.gnomon.pdms.procedure.PkgPmTemplateDBProcedureServcie;

@Service
@Transactional
public class ProjectUploadService {

	@Autowired
	private PMProgramVMDAO pmProgramVMDAO;
	
	@Autowired
	private VPmTempProgramDAO vPmTempProgramDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PkgPmTemplateDBProcedureServcie pkgPmTemplateDBProcedureServcie;

	/**
	 * 项目模板信息取得
	 */
	public List<VPmTempProgramEntity> getProgramTempList(String programId) {
		// 项目类型取得
		List<PMProgramVMEntity> projectList = null;
		String hql = "FROM PMProgramVMEntity WHERE id = ?";
		projectList = this.pmProgramVMDAO.find(hql, programId);
		if (projectList.size() == 0) {
			return new ArrayList<VPmTempProgramEntity>();
		}
		PMProgramVMEntity project = projectList.get(0);
		// 项目模板取得
		hql = "FROM VPmTempProgramEntity WHERE programTypeId = ?";
		List<VPmTempProgramEntity> result =
				this.vPmTempProgramDAO.find(hql, project.getProgramTypeId());
        return result;
    }
	
	/**
	 * 项目模板导入
	 */
	public void importProgramFromTemplate(
			String programVehicleId, Map<String, String> model) {
		// 模板导入
		this.pkgPmTemplateDBProcedureServcie.importTemplate(programVehicleId,
				DateUtils.strToDate(model.get("sopDate")),
				model.get("programTempId"));
	}
}

