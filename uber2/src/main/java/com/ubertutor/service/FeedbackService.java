package com.ubertutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.FeedbackDAO;
import com.ubertutor.entity.FeedbackEntity;

@Service
@Transactional
public class FeedbackService {
	@Autowired
	private FeedbackDAO feedbackDAO;

	public void save(FeedbackEntity entity){
		this.feedbackDAO.save(entity);
	}
	
	public FeedbackEntity get(Long id){
		return this.feedbackDAO.get(id);
	}
}
