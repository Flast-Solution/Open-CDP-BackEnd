package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.SaleProduct;
import vn.flast.models.Product;
import vn.flast.models.ProductAttributed;
import vn.flast.models.ProductProperty;
import vn.flast.models.Provider;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductAttributedRepository;
import vn.flast.repositories.ProductPropertyRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.repositories.ProviderRepository;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;

    @Autowired
    private ProductAttributedRepository productAttributedRepository;

    public Product createdSeo(Product input){
        input.setImage(JsonUtils.toJson(input.getImageLists()));
        return productsRepository.save(input);
    }

    public Product createdSale(SaleProduct input){
        Provider provider = providerRepository.findById(input.getProviderId()).orElseThrow(
                () -> new RuntimeException("Nhà cung cấp chưa tồn tại")
        );
        String twoLetterService = provider.getName().substring(0, 2);
        String name = Common.deAccent(input.getName());
        String twoLetterName = name.substring(0, 2);
        if (StringUtils.isEmpty(input.getCode())) {
            String random = Common.getAlphaNumericString(4, false);
            String code = twoLetterService + twoLetterName + random;
            input.setCode(code.toUpperCase());
        }
        Product product = new Product();
        CopyProperty.CopyIgnoreNull(input, product);
        var data = productsRepository.save(product);
        List<ProductAttributed> productAttributedList = input.getListProperties().stream()
                .flatMap(property -> property.getPropertyValueId().stream()
                        .map(propertyValueId -> {
                            ProductAttributed productAttributed = new ProductAttributed();
                            productAttributed.setProductId(data.getId());
                            productAttributed.setAttributedId(property.getAttributedId());
                            productAttributed.setAttributedValueId(propertyValueId);
                            productAttributed.setName(property.getName());
                            productAttributed.setValue(property.getValue());
                            return productAttributed;
                        }))
                .collect(Collectors.toList());
        productAttributedRepository.saveAll(productAttributedList);
        List<ProductProperty> productPropertyList = input.getListOpenInfo().stream()
                .map(productProperty -> {
                    ProductProperty property = new ProductProperty();
                    property.setName(productProperty.getName());
                    property.setValue(productProperty.getValue());
                    property.setProductId(data.getId());
                    return property;
                })
                .collect(Collectors.toList());
        productPropertyRepository.saveAll(productPropertyList);
        return data;
    };

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
