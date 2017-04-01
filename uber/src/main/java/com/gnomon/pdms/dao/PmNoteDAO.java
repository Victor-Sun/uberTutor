package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.PMNoteEntity;

@Repository
public class PmNoteDAO extends HibernateDao<PMNoteEntity, Long> {

}
