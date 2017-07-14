package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.SubjectCategoryEntity;

@Repository
public class SubjectCategoryDAO extends HibernateDao<SubjectCategoryEntity, Long> {

}
