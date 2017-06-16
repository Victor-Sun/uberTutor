package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.UserRequestEntity;

@Repository
public class UserRequestDAO extends HibernateDao<UserRequestEntity, Long> {

}
