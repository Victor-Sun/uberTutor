package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.dao.MergeInfoDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.MergeInfoEntity;

@Service
@Transactional
public class MainInfoService {
	
	@Autowired
	private MergeInfoDAO mergeInfoDAO;
	
	@Autowired
	private GTIssueDAO gtIssueDAO;

	/**
	 * 通过被合并问题ID查找唯一主问题
	 */
	public List<MergeInfoEntity> getMainId(String mergeId) {

		String hql = "FROM MergeInfoEntity WHERE issueId = ?";
		List<MergeInfoEntity> getMainId =
				this.mergeInfoDAO.find(hql, mergeId);
		if(getMainId != null){
			return getMainId;
		}else{
			return null;
		}
    }
	/**
	 * 被合并问题一览数据取得
	 */
	public List<MergeInfoEntity> getMergeInfo(String mainId) {
		String hql = "FROM MergeInfoEntity WHERE primaryIssueId = ?";
		List<MergeInfoEntity> mergeInfo =
				this.mergeInfoDAO.find(hql,mainId);
		if(mergeInfo != null){
			return mergeInfo;
		}else{
			return null;
		}
    }

	//主问题查询
	public GTIssueEntity getMainInfo(String mainId) {

		GTIssueEntity mainInfo =
				this.gtIssueDAO.findUniqueBy("id", mainId);
		
        return mainInfo;
    }
}
