package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.ShippingHistory;
import vn.flast.models.ShippingStatus;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ShippingStatusRepository;
import vn.flast.utils.EntityQuery;
import java.util.List;

@Service
public class ShippingService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ShippingStatusRepository shippingStatusRepository;

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, ShippingHistory.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    public ShippingStatus createStatus(ShippingStatus input){
        if(shippingStatusRepository.existsByName(input.getName())){
            return null;
        }
        return shippingStatusRepository.save(input);
    }

    public List<ShippingStatus> fetchStatus(){
        return shippingStatusRepository.findAll();
    }
}
