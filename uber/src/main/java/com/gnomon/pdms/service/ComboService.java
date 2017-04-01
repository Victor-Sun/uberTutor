package com.gnomon.pdms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.system.entity.SysUserEntity;
import com.gnomon.pdms.dao.ImsCodeTableDAO;
import com.gnomon.pdms.dao.ImsIssueDAO;
import com.gnomon.pdms.dao.IssueSourceDAO;
import com.gnomon.pdms.dao.IssueTypeDAO;
import com.gnomon.pdms.dao.PMProgramVehicleDAO;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.StageDAO;
import com.gnomon.pdms.dao.SysUserDAO;
import com.gnomon.pdms.dao.TestTypeDAO;
import com.gnomon.pdms.dao.VImsPartDAO;
import com.gnomon.pdms.dao.VImsPartStatusDAO;
import com.gnomon.pdms.dao.VPmProcessingProgramDAO;
import com.gnomon.pdms.dao.VPmProcessingVehicleDAO;
import com.gnomon.pdms.dao.VPmRespDeptDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsCodeTableEntity;
import com.gnomon.pdms.entity.IssueSourceEntity;
import com.gnomon.pdms.entity.IssueTypeEntity;
import com.gnomon.pdms.entity.StageEntity;
import com.gnomon.pdms.entity.TestTypeEntity;
import com.gnomon.pdms.entity.VImsPartEntity;
import com.gnomon.pdms.entity.VImsPartStatusEntity;
import com.gnomon.pdms.entity.VPmProcessingProgramEntity;
import com.gnomon.pdms.entity.VPmProcessingVehicleEntity;
import com.gnomon.pdms.entity.VPmRespDeptEntity;

@Service
@Transactional
public class ComboService {

	@Autowired
	private ProgramDAO programDAO;
	
	@Autowired
	private PMProgramVehicleDAO pmProgramVehicleDAO;
	
	@Autowired
	private VPmProcessingVehicleDAO vPmProcessingVehicleDAO;
	
	
	@Autowired
	private StageDAO stageDAO;	
	
	@Autowired
	private IssueTypeDAO issueTypedao;
	
	@Autowired
	private TestTypeDAO testtypedao;
	
	@Autowired
	private ImsCodeTableDAO imsCodeTabledao;
	
	@Autowired
	private VPmRespDeptDAO vPmRespDeptDAO;
	
	@Autowired
	private VImsPartDAO vImsPartDAO;
	
	@Autowired
	private IssueSourceDAO issueSourceDAO;
	
	@Autowired
	private VImsPartStatusDAO vImsPartStatusDAO;
	
	@Autowired
	private ImsIssueDAO imsIssueDAO;
	
	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Autowired
	private VPmProcessingProgramDAO vPmProcessingProgramDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ImsCodeTableEntity> getFaultState() {
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> strategydecision = this.imsCodeTabledao.find(hql,
				"PART_STATUS");
		return strategydecision;
	}
	//验证方案
	public List<ImsCodeTableEntity> getVerification() {
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> verification = this.imsCodeTabledao.find(hql,
				"VERIFICATION");
		return verification;
	}
	//效果验证
	public List<ImsCodeTableEntity> getEffectConfirmation() {
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> effectConfirmation = this.imsCodeTabledao.find(hql,
				"EFFECT_CONFIRMATION");
		return effectConfirmation;
	}
	//问题等级
	public List<ImsCodeTableEntity> getIssueLevel() {
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> issueLevel = this.imsCodeTabledao.find(hql,
				"ISSUE_LEVEL");
		return issueLevel;
	}
	//问题确认-问题性质
	public List<ImsCodeTableEntity> getIssueNature() {
		String hql = "FROM ImsCodeTableEntity WHERE codeType = ?";
		List<ImsCodeTableEntity> strategydecision = this.imsCodeTabledao.find(hql,
				"ISSUE_NATURE");
		return strategydecision;
	}
	//所属项目
	public List<VPmProcessingProgramEntity> getProgram() {
		List<VPmProcessingProgramEntity> gettesttype = this.vPmProcessingProgramDAO.getAll();
		return gettesttype;
	}
	//子项目
	public List<VPmProcessingVehicleEntity> getProgramVehicle(String programId) {
		String hql = "FROM VPmProcessingVehicleEntity WHERE programId = ?";
		List<VPmProcessingVehicleEntity> getProgramVehicle =
				this.vPmProcessingVehicleDAO.find(hql, programId);
		return getProgramVehicle;
	}
	//样车阶段
	public List<StageEntity> getPhaseList() {
		List<StageEntity> buildPhaseList =
				this.stageDAO.getAll("seq", true);
        return buildPhaseList;
    }
	//问题类型
	public List<IssueTypeEntity> getIssueType() {
		List<IssueTypeEntity> issuetype = 
				this.issueTypedao.getAll();
		return issuetype;
	}
	//问题来源
	public List<IssueSourceEntity> getProblemSource() {
		List<IssueSourceEntity> problemSourceList =
				this.issueSourceDAO.getAll();
        return problemSourceList;
    }
	//试验类型
	public List<TestTypeEntity> getTestType(){
		List<TestTypeEntity> gettesttype = 
				this.testtypedao.getAll();
		return gettesttype;
	}
	//责任&&验证部门
	public List<VPmRespDeptEntity> getDept(String keyId, String queryFlg) {
		if ("1".equals(queryFlg)) {
			String hql = "FROM VPmRespDeptEntity WHERE programVehicleId = ?";
			List<VPmRespDeptEntity> vPmRespDept =
					this.vPmRespDeptDAO.find(hql, keyId);
			return vPmRespDept;
		} else {
			GTIssueEntity oneDate =
					this.imsIssueDAO.findUniqueBy("id", keyId);
			String hql = "FROM VPmRespDeptEntity WHERE programVehicleId = ?";
			List<VPmRespDeptEntity> vPmRespDept =
					this.vPmRespDeptDAO.find(hql, oneDate.getSubProjectId());
			return vPmRespDept;
		}
	}
	
	public List<Map<String, Object>> getRespDepartment() {
		String sql = "select * from V_SYS_DEPARTMENT_TREE where APPLY_TO in ('IMS','All')";
		return jdbcTemplate.queryForList(sql);
	}
	
	//零件代号
	public List<VImsPartEntity> getVImsPart(String id) {
		if(id == "" || id == null){
			String hql = "FROM VImsPartEntity  order by partCode asc";
			List<VImsPartEntity> vImsPart =
				this.vImsPartDAO.find(hql);
			return vImsPart;
		}else{
			String hql = "FROM VImsPartEntity WHERE partCode like ? order by partCode asc";
			List<VImsPartEntity> vImsPart =
					this.vImsPartDAO.find(hql, id);
			return vImsPart;
		}
        
	}
	
	public List<Map<String, Object>> getImsPart(String id) {
		if(id == "" || id == null){
			List<Map<String, Object>> list = jdbcTemplate.queryForList(" select * From V_IMS_PART  "); //TODO
			return list;
		}else{
			List<Map<String, Object>> list = jdbcTemplate.queryForList(" select * From V_IMS_PART where PART_CODE LIKE ?   ","'%"+id+"%'");
			return list;
		}
        
	}
	//零件名字
	public List<VImsPartEntity> getVImsPartName(String id) {
		String hql = "FROM VImsPartEntity WHERE id = ?";
		List<VImsPartEntity> getVImsPart =
				this.vImsPartDAO.find(hql, id);
		return getVImsPart;
	}
	
	//零件状态
	public List<VImsPartStatusEntity> getVImsPartStatus() {
		List<VImsPartStatusEntity> vImsPart =
				this.vImsPartStatusDAO.getAll();
        return vImsPart;
	}
	
	
	//审核批准人信息取得
	public List<SysUserEntity> getSysUserList() {
		List<SysUserEntity> result = this.sysUserDAO.getAll();
        return result;
    }
}