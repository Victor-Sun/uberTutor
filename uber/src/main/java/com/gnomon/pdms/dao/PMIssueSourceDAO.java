package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMIssueSourceEntity;

@Repository
public class PMIssueSourceDAO extends HibernateDao<PMIssueSourceEntity, Long>  {

}
