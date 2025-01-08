package vn.flast.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.flast.dao.DataDao;
import vn.flast.models.Data;
import vn.flast.utils.EntityQuery;

import java.util.List;

public class DataDaoImpl implements DataDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Data> lastInteracted(String phone) {
        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);
        return et.stringEqualsTo("customerMobile", phone)
                .setMaxResults(10)
                .addDescendingOrderBy("id").list();
    }
}
