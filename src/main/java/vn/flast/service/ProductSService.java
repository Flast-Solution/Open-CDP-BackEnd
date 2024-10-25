package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.ProductService;
import vn.flast.models.Shipping;
import vn.flast.models.Stock;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductServiceRepository;
import vn.flast.repositories.ShippingRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class ProductSService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductServiceRepository productsRepository;


    public ProductService created(ProductService input){
        return productsRepository.save(input);
    }

    public ProductService updated(ProductService input) {
        var productService = productsRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, productService);
        return productsRepository.save(productService);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, ProductService.class);
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
