package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.pdms.entity.TaskEntity;

@Repository
public class TaskDAO extends HibernateDao<TaskEntity, String> {

}
