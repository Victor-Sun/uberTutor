package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.ExtendedProjectEntity;

@Repository
public class ProjectTreeDao extends HibernateDao<ExtendedProjectEntity, String> {
	
}

