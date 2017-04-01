package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.IssueProgressDAO;
import com.gnomon.pdms.dao.VIssueSourceDAO;
import com.gnomon.pdms.entity.IssueProgressEntity;
import com.gnomon.pdms.entity.VIssueSourceEntity;

@Service
@Transactional
public class DeptmanagementOpenIssueService {

	@Autowired
	private VIssueSourceDAO vissueSourceDAO;
	@Autowired
	private IssueProgressDAO issueProgressDAO;
	/**
	 * 【部门管理-项目列表】一OpenIssueService
	 */
	public List<VIssueSourceEntity> getDeptmanagementOpenIssueService(){
		String hql = "FROM VIssueSourceEntity WHERE issueTypeCode = ?";
		List<VIssueSourceEntity> deptmanagementOpenIssue =
				this.vissueSourceDAO.find(hql, "ISSUE_TYPE_2");
        return deptmanagementOpenIssue;
    }
	
	/**
	 * 【部门管理-项目列表】一OpenIssue览详细form数据取得Service
	 */
	public VIssueSourceEntity getDeptmanagementOpenIssueFormService(String issueId){
		VIssueSourceEntity result =
				this.vissueSourceDAO.findUniqueBy("id",issueId);
        return result;
    }
	
	/**
	 * 【项目管理-项目列表】一OpenIssue览详细grid数据取得Service
	 */
	public List<IssueProgressEntity> getDeptmanagementOpenIssueGridService(String issueId){
		String hql = "FROM IssueProgressEntity WHERE issueId = ?";
		List<IssueProgressEntity> deptmanagementOpenIssueGrid =
				this.issueProgressDAO.find(hql, issueId);
        return deptmanagementOpenIssueGrid;
    }

}
