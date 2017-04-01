package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMObsEntity;

@Repository
public class PMObsDAO extends HibernateDao<PMObsEntity, String> {

}
