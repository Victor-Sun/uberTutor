package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.PDMSCrudActionSupport;
import com.ubertutor.entity.FeedbackEntity;
import com.ubertutor.entity.UserRequestEntity;
import com.ubertutor.service.FeedbackService;

@Namespace("/main")
@AllowedMethods({"save","displayFeedbackInfo"})
public class FeedbackAction extends PDMSCrudActionSupport<FeedbackEntity> {
	private static final long serialVersionUID = 1L;
	private FeedbackEntity feedbackEntity = new FeedbackEntity();
	@Autowired
	private FeedbackService feedbackService;
	private Long id;
	private String rating, requestId, feedback;

	/**
	 * Returns ID
	 * @return 
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set ID
	 * @param
	 */
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
	
	/**
	 * Saves feedback to db
	 */
	public String save() throws Exception{
		try{
			feedbackEntity.setCreateDate(new Date());
			feedbackService.save(feedbackEntity);
			UserRequestEntity requestEntity = feedbackService.getRequest(requestId);
			requestEntity.setFeedback(feedbackEntity.getId());
			feedbackService.save(requestEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Outputs feedback information from db
	 * @throws Exception
	 */
	public void displayFeedbackInfo() throws Exception{
		try{
			boolean hasFeedback = feedbackService.hasFeedback(Long.parseLong(requestId));
			Map<String, Object> result = (!hasFeedback) ? new HashMap<String, Object>() : feedbackService.getFeedbackInfo(requestId);
			result.put("hasFeedback", hasFeedback);
			this.writeSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public FeedbackEntity getModel() {
		return feedbackEntity;
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		feedbackEntity = (id != null) ? feedbackService.getFeedback(id) : new FeedbackEntity();
	}
}
