package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PretaskEntity;

@Repository
public class PretaskDAO extends HibernateDao<PretaskEntity, String> {
	

}
