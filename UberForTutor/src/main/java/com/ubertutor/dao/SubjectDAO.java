package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.SubjectEntity;

@Repository
public class SubjectDAO extends HibernateDao<SubjectEntity, Long> {

}
