package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.IssueTypeEntity;

@Repository
public class IssueTypeDAO extends HibernateDao<IssueTypeEntity, String>{

}
         