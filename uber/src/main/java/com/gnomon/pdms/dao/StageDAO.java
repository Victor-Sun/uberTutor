package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.StageEntity;
@Repository
public class StageDAO extends HibernateDao<StageEntity, String>  {

}
