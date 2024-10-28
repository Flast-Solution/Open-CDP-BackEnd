package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.ProductProperty;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductPropertyRepository;
import vn.flast.searchs.AttributedFilter;
import vn.flast.utils.EntityQuery;

@Service
public class ProductPropertyService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;

    public ProductProperty created(ProductProperty input){
        return productPropertyRepository.save(input);
    }

    public Ipage<?> fetch(AttributedFilter filter){
        int LIMIT = 20;
        int currentPage = filter.page();
        var et = EntityQuery.create(entityManager, ProductProperty.class);
        et.like("name", filter.name());
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = productPropertyRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        productPropertyRepository.delete(data);
    }
}
