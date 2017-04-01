package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMFunctionTaskEntity;

@Repository
public class PMFunctionTaskDAO extends HibernateDao<PMFunctionTaskEntity, String> {

}
