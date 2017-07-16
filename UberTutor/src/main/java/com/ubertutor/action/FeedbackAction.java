package com.ubertutor.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.common.PDMSCrudActionSupport;
import com.ubertutor.entity.FeedbackEntity;
import com.ubertutor.service.FeedbackService;

@Namespace("/main")
@AllowedMethods({"save","displayFeedbackInfo"})
public class FeedbackAction extends PDMSCrudActionSupport<FeedbackEntity> {
	private static final long serialVersionUID = 1L;
	private FeedbackEntity feedbackEntity = new FeedbackEntity();
	@Autowired
	private FeedbackService feedbackService;
	private Long id, userId, tutorId, requestId;
	private String rating, feedback;

	/**
	 * Returns id
	 * @return 
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * @param
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns rating
	 * @return
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Set rating
	 * @param rating
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the requestId
	 */
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	/**
	 * Returns feedback
	 * @return
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * Set feedback
	 * @param feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	/**
	 * Returns userId
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Set userId
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns getTutorId
	 * @return
	 */
	public Long getTutorId() {
		return tutorId;
	}

	/**
	 * Set getTutorId
	 * @param tutorId
	 */
	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

	/**
	 * Saves feedback to db
	 */
	public String save() throws Exception{
		try{
			feedbackEntity.setCreateDate(new Date());
			feedbackService.save(feedbackEntity);
			feedbackService.updateRequest(requestId, feedbackEntity.getId());
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
			boolean hasFeedback = feedbackService.hasFeedback(requestId);
			Map<String, Object> result = (hasFeedback) ? feedbackService.getFeedbackInfo(requestId) : new HashMap<String, Object>();
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
