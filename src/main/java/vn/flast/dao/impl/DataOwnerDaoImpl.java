package vn.flast.dao.impl;

import vn.flast.dao.DaoImpl;
import vn.flast.dao.DataOwnerDao;
import vn.flast.models.DataOwner;
import vn.flast.utils.EntityQuery;

public class DataOwnerDaoImpl extends DaoImpl<Long, DataOwner> implements DataOwnerDao  {


    @Override
    public DataOwner findByPhone(String customerMobile) {
        return EntityQuery.create(entityManager, DataOwner.class)
                .stringEqualsTo("customerMobile", customerMobile)
                .uniqueResult();
    }
}
