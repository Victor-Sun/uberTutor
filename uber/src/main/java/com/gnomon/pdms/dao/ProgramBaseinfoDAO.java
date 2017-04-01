package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.ProgramBaseinfoEntity;

@Repository
public class ProgramBaseinfoDAO extends
						HibernateDao<ProgramBaseinfoEntity, String> {
}
