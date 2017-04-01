package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.PMTempTaskVMDAO;
import com.gnomon.pdms.dao.VPmTempDeliverableDAO;
import com.gnomon.pdms.dao.VPmTempGateDAO;
import com.gnomon.pdms.dao.VPmTempMainNodeDAO;
import com.gnomon.pdms.dao.VPmTempObsDAO;
import com.gnomon.pdms.dao.VPmTempProgramDAO;
import com.gnomon.pdms.entity.PMTempTaskVMEntity;
import com.gnomon.pdms.entity.VPmTempDeliverableEntity;
import com.gnomon.pdms.entity.VPmTempGateEntity;
import com.gnomon.pdms.entity.VPmTempMainNodeEntity;
import com.gnomon.pdms.entity.VPmTempObsEntity;
import com.gnomon.pdms.entity.VPmTempProgramEntity;

@Service
@Transactional
public class ProjectTempService {
	
	@Autowired
	private VPmTempObsDAO vPmTempObsDAO;
	
	@Autowired
	private VPmTempGateDAO vPmTempGateDAO;
	
	@Autowired
	private VPmTempMainNodeDAO vPmTempMainNodeDAO;
	
	@Autowired
	private VPmTempProgramDAO vPmTempProgramDAO;
	
	@Autowired
	private VPmTempMainNodeDAO vpmTempMainNodeDAO;

	@Autowired
	private VPmTempGateDAO vpmTempGateDAO;
	
	@Autowired
	private VPmTempDeliverableDAO vpmTempDeliverableDAO;

	@Autowired
	private VPmTempProgramDAO vpmTempProgramDAO;

	@Autowired
	private PMTempTaskVMDAO pmTempTaskVMDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * 模板信息取得
	 */
	public VPmTempProgramEntity getProjectTemplateForm(String id) {
		VPmTempProgramEntity result =
				this.vpmTempProgramDAO.findUniqueBy("id", id);
        return result;
	}

	/*
	 * 组织信息取得
	 */
	public VPmTempObsEntity getProjectTemplateOrgNode(String id) {

		VPmTempObsEntity result =
				this.vPmTempObsDAO.findUniqueBy("id", id);
        return result;
	}

	/*
	 * 组织交付物取得
	 */
	public List<PMTempTaskVMEntity> getTemplateOrgNodeDeliverable(String obsId) {
		String hql = "FROM PMTempTaskVMEntity WHERE obsId = ?";
		List<PMTempTaskVMEntity> result =
				this.vpmTempDeliverableDAO.find(hql, obsId);
		return result;
	}

	/*
	 * 节点信息取得
	 */
	public List<VPmTempMainNodeEntity> getProjectTemplateMainNode(String programId) {
		String hql = "FROM VPmTempMainNodeEntity WHERE programId = ?"
				+ " order by finishDaysToSop";
		List<VPmTempMainNodeEntity> result =
				this.vpmTempMainNodeDAO.find(hql, programId);
        return result;
	}

	/*
	 * 阀门信息取得
	 */
	public List<VPmTempGateEntity> getProjectTemplateGateNode(String programId) {
		String hql = "FROM VPmTempGateEntity WHERE programId = ? "
				+ "order by finishDaysToSop";
		List<VPmTempGateEntity> result =
				this.vpmTempGateDAO.find(hql, programId);
        return result;
	}

	/*
	 * 阀门交付物取得
	 */
	public List<VPmTempDeliverableEntity> getTemplateGateNodeDeliverable(String taskId) {
		String hql = "FROM VPmTempDeliverableEntity WHERE taskId = ?";
		List<VPmTempDeliverableEntity> result =
				this.vpmTempDeliverableDAO.find(hql, taskId);
		return result;
	}

	/*
	 * 专业领域信息取得
	 */
	public List<PMTempTaskVMEntity> getProjectTemplateMainDept(String programId) {
		String hql = "FROM PMTempTaskVMEntity WHERE obsId = ?"
				+ " order by finishDaysToSop";
		List<PMTempTaskVMEntity> result =
				this.vpmTempGateDAO.find(hql, programId);
        return result;
	}

	/*
	 * 专业领域交付物取得
	 */
	public List<Map<String, Object>> getTemplateMainDeptDeliverable(String taskId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append(" SELECT *");
		sql.append(" FROM");
		sql.append(" V_PM_TEMP_TASK_DELIVERABLE");
		sql.append(" WHERE");
		sql.append(" RESP_TASK_ID = ?");
		params.add(taskId);
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}

	/*
	 * 泳道信息取得
	 */
	public List<Map<String, Object>> getProjectTemplateSwimLane(String programId, Integer grade) {
		if (grade == 1) {
			String sql = "SELECT * FROM V_PM_TEMP_FUNCTION WHERE PROGRAM_ID = ?"
					+ " and PARENT_OBS_PARENT_ID IS NULL order by SEQ_NO";
			List<Map<String, Object>> result =
					jdbcTemplate.queryForList(sql, programId);
	        return result;
		} else {
			String sql = "SELECT * FROM V_PM_TEMP_FUNCTION WHERE"
					+ " PARENT_OBS_ID = ? order by SEQ_NO";
			List<Map<String, Object>> result =
					jdbcTemplate.queryForList(sql, programId);
	        return result;
		}
	}

	/*
	 * 泳道交付物取得
	 */
	public List<Map<String, Object>> getTemplateSwimLaneDeliverable(String taskId) {
		String sql = "SELECT * FROM V_PM_TEMP_FUNCTION_TASK WHERE"
				+ " FUNCTION_ID = ? order by FINISH_DAYS_TO_SOP";
		List<Map<String, Object>> result =
				jdbcTemplate.queryForList(sql, taskId);
		return result;
	}

	public List<VPmTempObsEntity> getObsTypeName(){
		List<VPmTempObsEntity> result =
				this.vPmTempObsDAO.getAll();
        return result;
    }
	
	public List<VPmTempProgramEntity> getAllTempProgram(){
		return vPmTempProgramDAO.getAll();
	}
	
	public List<VPmTempProgramEntity> getTempProgramListByLevel(Integer planLevel){
		return vPmTempProgramDAO.find("from VPmTempProgramEntity where planLevel=?",planLevel);
	}
	
	public List<VPmTempProgramEntity> getTempProgramListByProgramTypeId(String programTypeId){
		return vPmTempProgramDAO.find("from VPmTempProgramEntity where programTypeId=?",programTypeId);
	}

	public List<VPmTempObsEntity> getTempProgramObsList(String tempProgramId,String parentId) {
		if(StringUtils.isEmpty(parentId)){
			return vPmTempObsDAO.find("from VPmTempObsEntity where programId=? AND parentId is null", tempProgramId);
		}else{
			return vPmTempObsDAO.find("from VPmTempObsEntity where programId=? AND parentId=?", tempProgramId,parentId);
		}
	}
	
	/**
	 * 主计划泳道（专业领域,不包括关键路径）
	 * @param tempProgramId
	 * @return
	 */
	public List<VPmTempObsEntity> getDeptRespListOfMainPlan(String tempProgramId){
		return vPmTempObsDAO.find("from VPmTempObsEntity where programId=? AND obsTypeCode in (?) order by lft", tempProgramId,PDMSConstants.OBS_TYPE_RESP_DEPT);
	}
	
	/**
	 * 查询二级计划下的泳道
	 * @param tempProgramId
	 * @param deptObsId
	 * @return
	 */
	public List<VPmTempObsEntity> getSwimlaneListOfDeptPlan(String tempProgramId,String deptObsId){
		return vPmTempObsDAO.find("from VPmTempObsEntity where programId=? AND parentId=? order by lft", tempProgramId,deptObsId);
	}
	
	public List<VPmTempGateEntity> getTempGate(String tempProgramId){
		return vPmTempGateDAO.find("from VPmTempGateEntity where programId=? order by finishDaysToSop", tempProgramId);
	}
	
	public List<VPmTempMainNodeEntity> getTempMainNodeList(String tempProgramId){
		return vPmTempMainNodeDAO.find("from VPmTempMainNodeEntity where programId=? order by finishDaysToSop", tempProgramId);
	}
}
