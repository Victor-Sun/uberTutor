package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.UserEntity;

@Repository
public class UserDAO extends HibernateDao<UserEntity, String> {

}
