package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.ImsLastAccessLogEntity;

@Repository
public class ImsLastAccessLogDAO extends HibernateDao<ImsLastAccessLogEntity, String> {

}
