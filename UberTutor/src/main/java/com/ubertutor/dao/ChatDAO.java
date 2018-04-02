package com.ubertutor.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.ubertutor.entity.ChatEntity;

@Repository
public class ChatDAO  extends HibernateDao<ChatEntity, Long> {

}
