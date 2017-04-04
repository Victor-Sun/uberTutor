package com.gnomon.common.system.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.common.system.entity.SysNoticeVMEntity;

@Repository
public class SysNoticeVMDAO extends HibernateDao<SysNoticeVMEntity, String> {

}