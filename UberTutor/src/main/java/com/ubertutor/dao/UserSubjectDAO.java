package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.UserSubjectEntity;

@Repository
public class UserSubjectDAO extends HibernateDao<UserSubjectEntity, Long> {

}