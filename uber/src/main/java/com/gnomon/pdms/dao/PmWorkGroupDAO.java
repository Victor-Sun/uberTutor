package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMWorkGroupEntity;

@Repository
public class PmWorkGroupDAO extends HibernateDao<PMWorkGroupEntity, Long> {

}
