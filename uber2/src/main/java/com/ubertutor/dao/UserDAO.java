package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import com.gnomon.common.system.entity.UserEntity;

@Repository
public class UserDAO extends HibernateDao<UserEntity, String> {

}
