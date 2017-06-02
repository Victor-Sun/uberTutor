package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.SchoolEntity;

@Repository
public class SchoolDAO extends HibernateDao<SchoolEntity, Long> {

}
