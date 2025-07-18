package vn.flast.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vn.flast.models.Data;
import vn.flast.utils.EntityQuery;

import java.util.List;

@Repository("dataDao")
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
