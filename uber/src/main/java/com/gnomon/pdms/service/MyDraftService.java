package com.gnomon.pdms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.GTIssueDAO;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.entity.GTIssueEntity;
import com.gnomon.pdms.entity.ProgramEntity;

@Service
@Transactional
public class MyDraftService {

	@Autowired
	private GTIssueDAO gtIssueDAO;

	@Autowired
	private ProgramDAO programDAO;
	
	public GTIssueEntity getMyDraft(String keyId) {
		GTIssueEntity myDraft =
				this.gtIssueDAO.findUniqueBy("id", keyId);
        return myDraft;
    }

	//所属项目
	public ProgramEntity getProgram(String keyId) {

		ProgramEntity getProgram =
				this.programDAO.findUniqueBy("id", keyId);
		
        return getProgram;
    }
	/**
	 * 我的草稿删除
	 */
	public void deleteDraftIndex(String id) {
		
		gtIssueDAO.delete(id);
	}
}
