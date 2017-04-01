package com.gnomon.pdms.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.dao.ImsIssueMergeDAO;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.VImsIssueDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ImsIssueMergeEntity;
import com.gnomon.pdms.entity.VImsIssueEntity;

@Service
@Transactional
public class MyTaskInfo01ComparedService {
	
	//private GTWorkFlowService workflowService = new GTWorkFlowService();

	@Autowired
	private ImsIssueMergeDAO imsIssueMergeDAO;

	@Autowired
	private GTIssueDAO gtIssueDAO;
	
	@Autowired
	private VImsIssueDAO vImsIssueDAO;

	@Autowired
	private ProgramDAO programDAO;

	public void save (ImsIssueMergeEntity entity){
		imsIssueMergeDAO.save(entity);
	}
	public void save (GTIssueEntity entity){
		gtIssueDAO.save(entity);
	}
	//其它
	public GTIssueEntity getNewQestion(String keyId) {

		GTIssueEntity newQestion =
				this.gtIssueDAO.findUniqueBy("id", keyId);
		
        return newQestion;
    }
	//合并模糊查询
	public Page<VImsIssueEntity> getCombineList(String query, String searchChoice, int start, int end, String oldFormId) throws UnsupportedEncodingException {
		Page<VImsIssueEntity> combineList = null ;
		Page<VImsIssueEntity> page = new Page<VImsIssueEntity>(end);
		page.setPageNo(start);

		List<String> params = new ArrayList<String>();
		String hql = "from VImsIssueEntity where id != ?";
		params.add(oldFormId);

		if (PDMSCommon.isNotNull(query)) {
			String strQuery = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			hql += " and " + searchChoice + " like ?";
			params.add("%" + strQuery + "%");
		}
//		if (PDMSCommon.isNotNull(proTitle)) {
//			hql += " AND title like ?";
//			params.add("%" + proTitle + "%");
//		}
//		if (PDMSCommon.isNotNull(qestionId)) {
//			hql += " AND code like ?";
//			params.add("%" + qestionId + "%");
//		}
//		if (PDMSCommon.isNotNull(qeDescription)) {
//			hql += " AND description like ?";
//			params.add("%" + qeDescription + "%");
//		}
		
		hql += " and issueStatusCode in (?,?,?,?,?)";
		params.add("ISSUE_STATUS_61");
		params.add("ISSUE_STATUS_62");
		params.add("ISSUE_STATUS_63");
		params.add("ISSUE_STATUS_64");
		params.add("ISSUE_STATUS_65");
		combineList =
				this.vImsIssueDAO.findPage(page, hql, params.toArray());
		return combineList;
	}
	
//	public void mergeIssue(String processInstanceId, String taskId) {
//		log.info("问题发布.合并 start....................................................");
//		log.info("processInstanceId = " + processInstanceId);
//		log.info("taskId = " + taskId);
//				
//		Task task = workflowService.getTask(taskId);
//		log.info("Task's Name: "+ task.getName());
//    	Map<String, Object> variableMap = new HashMap<String, Object>();
//    	workflowService.taskService.setVariable(taskId, "publishFlag", 3);  
//        workflowService.complete(task.getId(), variableMap);            
//        log.info("问题发布.合并 end......................................................");
//	}
}
