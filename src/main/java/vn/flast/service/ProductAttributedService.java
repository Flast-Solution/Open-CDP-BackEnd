package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.ProductAttributed;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductAttributedRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class ProductAttributedService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductAttributedRepository productAttributedRepository;


    public ProductAttributed created(ProductAttributed input){
        return productAttributedRepository.save(input);
    }

    public ProductAttributed updated(ProductAttributed input) {
        var productAttributed = productAttributedRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, productAttributed);
        return productAttributedRepository.save(productAttributed);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, ProductAttributed.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = productAttributedRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        productAttributedRepository.delete(data);
    }
}
