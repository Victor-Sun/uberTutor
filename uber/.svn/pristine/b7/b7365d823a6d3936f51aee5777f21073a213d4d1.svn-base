package com.gnomon.pdms.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.gnomon.common.utils.DateUtils;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.VImsIssueDAO;
import com.gnomon.pdms.dao.VImsIssuePartDAO;
import com.gnomon.pdms.entity.VImsIssueEntity;

@Service
@Transactional
public class QueryService {
	
	@Autowired
	private VImsIssueDAO vImsIssueDAO;
	
	@Autowired
	private VImsIssuePartDAO vImsIssuePartDAO;

	@Autowired
	private ProgramDAO programDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Page<VImsIssueEntity> getQueryServiceList(Map<String, String> model, int start, int end) throws UnsupportedEncodingException {
		Page<VImsIssueEntity> queryList = null ;
		Page<VImsIssueEntity> page = new Page<VImsIssueEntity>(end);
		page.setPageNo(start);

		List<Object> params = new ArrayList<Object>();
		String hql = "from VImsIssueEntity where issueStatusCode != 'ISSUE_STATUS_50'";
		if (model != null) {
			//问题状态
			if (PDMSCommon.isNotNull(model.get("QeStatus")) && PDMSCommon.isNotNull(model.get("QeStatusTo"))) {
				hql += " and issueStatus >= ? and issueStatus <= ?";
				params.add(model.get("QeStatus"));
				params.add(model.get("QeStatusTo"));
			} else if (PDMSCommon.isNotNull(model.get("QeStatus")) && PDMSCommon.isNull(model.get("QeStatusTo"))) {
				hql += " and issueStatus = ?";
				params.add(model.get("QeStatus"));
			}
			//提出时间
			if (PDMSCommon.isNotNull(model.get("ProposedTime")) && PDMSCommon.isNotNull(model.get("ProposedTimeTo"))) {
				Date date1 = DateUtils.strToDate(model.get("ProposedTime"));
				Date date2 = DateUtils.strToDate(model.get("ProposedTimeTo"));
				hql += " and openDate between ? and ?";
				params.add(date1);
				params.add(date2);
			} else if (PDMSCommon.isNotNull(model.get("ProposedTime")) && PDMSCommon.isNull(model.get("ProposedTimeTo"))) {
				Date date1 = DateUtils.strToDate(model.get("ProposedTime"));
				hql += " and openDate = ?";
				params.add(date1);
			}
			//所属项目
			if (PDMSCommon.isNotNull(model.get("BeProject"))) {
				hql += " and projectId = ?";
				params.add(model.get("BeProject"));
			}
			//子项目
			if (PDMSCommon.isNotNull(model.get("SubProject"))) {
				hql += " and subProjectId = ?";
				params.add(model.get("SubProject"));
			}
			//责任部门
			if (PDMSCommon.isNotNull(model.get("ResDepartment"))) {
				hql += " and deptId = ?";
				params.add(model.get("ResDepartment"));
			}
			//问题编号
			if (PDMSCommon.isNotNull(model.get("QestionId"))) {
				hql += " and code like ?";
				params.add("%" + model.get("QestionId") + "%");
			}
			//问题标题
			if (PDMSCommon.isNotNull(model.get("ProTitle"))) {
				hql += " and title like ?";
				params.add("%" + model.get("ProTitle") + "%");
			}
			//问题等级
			if (PDMSCommon.isNotNull(model.get("QeLevel"))) {
				hql += " and issueLevelCode = ?";
				params.add(model.get("QeLevel"));
			}
			//问题提出人
			if (PDMSCommon.isNotNull(model.get("QestionOut"))) {
				hql += " and submitUserName like ?";
				params.add("%" + model.get("QestionOut") + "%");
			}
			//责任工程师
			if (PDMSCommon.isNotNull(model.get("ResponEngineer"))) {
				hql += " and ownerName like ?";
				params.add("%" + model.get("ResponEngineer") + "%");
			}
			//故障零件代号与所属系统
			if (PDMSCommon.isNotNull(model.get("partNumber")) || PDMSCommon.isNotNull(model.get("BeSystem"))) {
				List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
				List<Object> paramsPart = new ArrayList<Object>();
				String sql = "select distinct ISSUE_ID from V_IMS_ISSUE_PART where 1 = 1";
				if (PDMSCommon.isNotNull(model.get("partNumber"))) {
					sql += " and TROUBLE_PART_NUMBER like ? or PART_NUMBER = ?";
					paramsPart.add("%" + model.get("partNumber") + "%");
					paramsPart.add(model.get("partNumber"));
				}
				if (PDMSCommon.isNotNull(model.get("BeSystem"))) {
					sql += " and (SYSTEM_NO like ? or SYSTEM_NAME like ?)";
					paramsPart.add("%" + model.get("BeSystem") + "%");
					paramsPart.add("%" + model.get("BeSystem") + "%");
				}
				data = jdbcTemplate.queryForList(sql, paramsPart.toArray());

				hql += " and id in (";
				if (data.size() != 0) {
					int i = 0;
					for(Map<String,Object> map : data){
						hql += "?";
						if (i < data.size()-1) {
							hql += ", ";
						}
						i++;
						//ID
						params.add(map.get("ISSUE_ID"));
					}
				} else {
					hql += "?";
					params.add(null);
				}
				hql += " )";
			}
			//所属系统
//			if (PDMSCommon.isNotNull(model.get("BeSystem"))) {
//				hql += " and ownerName like ?";
//				params.add("%" + model.get("BeSystem") + "%");
//			}
			//问题来源
			if (PDMSCommon.isNotNull(model.get("ProblemSource"))) {
				hql += " and issueSourceId = ?";
				params.add(model.get("ProblemSource"));
			}
			//试验类型
			if (PDMSCommon.isNotNull(model.get("ExperimentType"))) {
				hql += " and testTypeId = ?";
				params.add(model.get("ExperimentType"));
			}
			//发生场地
			if (PDMSCommon.isNotNull(model.get("OccurrenceSite"))) {
				hql += " and occurrenceSite like ?";
				params.add("%" + model.get("OccurrenceSite") + "%");
			}
			//样车阶段
			if (PDMSCommon.isNotNull(model.get("BuildPhase"))) {
				hql += " and stageId = ?";
				params.add(model.get("BuildPhase"));
			}
		}
		hql += " order by rownum";
		queryList =
				this.vImsIssueDAO.findPage(page, hql, params.toArray());

        return queryList;
    }

}