package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.Provider;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProviderRepository;
import vn.flast.searchs.ProviderFilter;
import vn.flast.utils.EntityQuery;

@Service
public class ProviderService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProviderRepository providerRepository;

    public Provider save(Provider input){
        return providerRepository.save(input);
    }

    public Ipage<?> fetch(ProviderFilter filter){

        var et = EntityQuery.create(entityManager, Provider.class);
        et.like("name", filter.name())
            .stringEqualsTo("mobile", filter.mobile());
        var lists = et.list();
        return  Ipage.generator(10, et.count(), 1, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = providerRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        providerRepository.delete(data);
    }
}
