package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMPreTaskEntity;

@Repository
public class PMPreTaskDAO extends HibernateDao<PMPreTaskEntity, String> {

}
