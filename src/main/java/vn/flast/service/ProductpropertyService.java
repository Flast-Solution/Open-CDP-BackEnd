package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.ProductProperty;
import vn.flast.models.ProductService;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductPropertyRepository;
import vn.flast.repositories.ProductServiceRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class ProductpropertyService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;


    public ProductProperty created(ProductProperty input){
        return productPropertyRepository.save(input);
    }

    public ProductProperty updated(ProductProperty input) {
        var productProperty = productPropertyRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, productProperty);
        return productPropertyRepository.save(productProperty);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, ProductProperty.class);
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
