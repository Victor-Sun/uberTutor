package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.gnomon.common.system.entity.SysUserEntity;

@Repository
public class SysUserDAO extends HibernateDao<SysUserEntity, String> {

}
