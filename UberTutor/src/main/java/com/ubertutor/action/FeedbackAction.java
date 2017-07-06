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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		feedbackEntity = (id != null) ? feedbackService.get(id) : new FeedbackEntity();
	}
}
