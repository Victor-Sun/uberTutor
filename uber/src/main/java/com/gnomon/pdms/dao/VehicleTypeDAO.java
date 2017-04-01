package com.gnomon.pdms.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import com.gnomon.pdms.entity.VehicleTypeEntity;

@Repository
public class VehicleTypeDAO extends HibernateDao<VehicleTypeEntity, String> {

}
