package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.WorkOrderEntity;

@Repository
public class WorkOrderDAO extends HibernateDao<WorkOrderEntity, Long> {

}
