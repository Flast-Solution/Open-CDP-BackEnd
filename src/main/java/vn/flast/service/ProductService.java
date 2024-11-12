package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.Product;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class ProductService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductRepository productsRepository;

    public Product created(Product input){
        return productsRepository.save(input);
    }

    public Product updated(Product input) {
        var entity = productsRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, entity);
        return productsRepository.save(entity);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, Product.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = productsRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        productsRepository.delete(data);
    }
}
