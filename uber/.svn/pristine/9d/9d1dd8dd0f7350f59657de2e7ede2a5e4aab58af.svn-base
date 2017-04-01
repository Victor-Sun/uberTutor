package com.gnomon.pdms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.dao.ImsIssueListingDAO;
import com.gnomon.pdms.entity.ImsIssueListingEntity;

@Service
@Transactional
public class ImsIssueListingService {

	@Autowired
	private ImsIssueListingDAO imsIssueListingDAO;
	
	public List<ImsIssueListingEntity> getImsIssueListing(String keyId) {
		
		String hql = "FROM ImsIssueListingEntity WHERE issueId = ?";
		List<ImsIssueListingEntity> imsIssueListing =
				this.imsIssueListingDAO.find(hql, keyId);
		return imsIssueListing;
	}
	
	public void save (ImsIssueListingEntity entity){
		imsIssueListingDAO.save(entity);
	}
}
