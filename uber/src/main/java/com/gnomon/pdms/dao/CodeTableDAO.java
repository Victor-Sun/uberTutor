package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import com.gnomon.pdms.entity.CodeTableEntity;

@Repository
public class CodeTableDAO extends HibernateDao<CodeTableEntity, String> {

}
