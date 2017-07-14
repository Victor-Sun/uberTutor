package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.FeedbackEntity;

@Repository
public class FeedbackDAO extends HibernateDao<FeedbackEntity, Long> {

}
