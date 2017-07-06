package com.ubertutor.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.PDMSCrudActionSupport;
import com.ubertutor.entity.FeedbackEntity;
import com.ubertutor.service.FeedbackService;

@Namespace("/main")
@AllowedMethods("save")
public class FeedbackAction extends PDMSCrudActionSupport<FeedbackEntity> {
	private static final long serialVersionUID = 1L;
	private FeedbackEntity feedbackEntity;
	@Autowired
	private FeedbackService feedbackService;
	private Long id;
	private String rating, requestId, feedback;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String save() throws Exception{
		try{
			feedbackEntity.setCreateDate(new Date());
			feedbackService.save(feedbackEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void displayFeedbackInfo() throws Exception{
		try{
			this.writeSuccessResult(feedbackService.getFeedbackInfo(Long.parseLong(requestId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FeedbackEntity getModel() {
		return feedbackEntity;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		feedbackEntity = (id != null) ? feedbackService.getFeedback(id) : new FeedbackEntity();
	}
}
