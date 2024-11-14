package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.SaleProduct;
import vn.flast.models.Attributed;
import vn.flast.models.AttributedValue;
import vn.flast.models.Product;
import vn.flast.models.ProductAttributed;
import vn.flast.models.ProductProperty;
import vn.flast.models.ProductSkus;
import vn.flast.models.ProductSkusDetails;
import vn.flast.models.ProductSkusPrice;
import vn.flast.models.Provider;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.AttributedRepository;
import vn.flast.repositories.AttributedValueRepository;
import vn.flast.repositories.ProductAttributedRepository;
import vn.flast.repositories.ProductPropertyRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.repositories.ProductSkusDetailsRepository;
import vn.flast.repositories.ProductSkusPriceRepository;
import vn.flast.repositories.ProductSkusRepository;
import vn.flast.repositories.ProviderRepository;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;
import java.util.List;

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

    @Autowired
    private ProductSkusRepository productSkusRepository;

    @Autowired
    private ProductSkusPriceRepository skusPriceRepository;

    @Autowired
    private ProductSkusDetailsRepository productSkusDetailsRepository;

    @Autowired
    private AttributedRepository attributedRepository;

    @Autowired
    private AttributedValueRepository attributedValueRepository;

    public Product createdSeo(Product input){
        input.setImage(JsonUtils.toJson(input.getImageLists()));
        return productsRepository.save(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product createdProduct(SaleProduct input){
        Provider provider = providerRepository.findById(input.getProviderId()).orElseThrow(
            () -> new RuntimeException("Nhà cung cấp chưa tồn tại")
        );
        String twoLetterService = provider.getName().substring(0, 2);
        String name = Common.deAccent(input.getName());
        String twoLetterName = name.length() >= 2 ? name.substring(0, 2) : name;
        String code;
        do {
            String random = Common.getAlphaNumericString(4, false);
            code = (twoLetterService + twoLetterName + random).toUpperCase();
        } while (productsRepository.findByCode(code) != null);
        input.setCode(code);

        Product product = new Product();
        CopyProperty.CopyIgnoreNull(input, product);
        var data = productsRepository.save(product);

        /* Save Attributed */
        List<ProductAttributed> productAttributedList = input.getListProperties().stream().flatMap(
            property -> property.getPropertyValueId().stream().map(
            propertyValueId -> {
                Attributed attributed = attributedRepository.findById(property.getAttributedId()).orElseThrow(
                    () -> new RuntimeException("không tồn tại bản ghi")
                );
                AttributedValue attributedValue = attributedValueRepository.findById(propertyValueId).orElseThrow(
                    () -> new RuntimeException("không tồn tại bản ghi")
                );
                ProductAttributed productAttributed = new ProductAttributed();
                productAttributed.setProductId(data.getId());
                productAttributed.setAttributedId(property.getAttributedId());
                productAttributed.setAttributedValueId(propertyValueId);
                productAttributed.setName(attributed.getName());
                productAttributed.setValue(attributedValue.getValue());
                return productAttributed;
        })).toList();
        productAttributedRepository.saveAll(productAttributedList);

        /* Save Property */
        List<ProductProperty> productPropertyList = input.getListOpenInfo().stream().map(productProperty -> {
            ProductProperty property = new ProductProperty();
            property.setName(productProperty.getName());
            property.setValue(productProperty.getValue());
            property.setProductId(data.getId());
            return property;
        }).toList();
        productPropertyRepository.saveAll(productPropertyList);

        /* Save SKU */
        input.getSkus().forEach(productSkus -> {
            ProductSkus skus = new ProductSkus();
            skus.setProductId(data.getId());
            ProductSkus savedSku = productSkusRepository.save(skus);
            /* Save Price Range */
            productSkus.getListPriceRange().forEach(priceRange -> {
                ProductSkusPrice price = new ProductSkusPrice();
                CopyProperty.CopyIgnoreNull(priceRange, price);
                price.setProductId(data.getId());
                price.setSkuId(savedSku.getId());
                price.setQuantityFrom(priceRange.getStart());
                price.setQuantityTo(priceRange.getEnd());
                price.setPrice(priceRange.getPrice());
                skusPriceRepository.save(price);
            });
            productSkus.getSku().forEach(sku -> {
                ProductSkusDetails skusDetails = new ProductSkusDetails();
                Attributed attributed = attributedRepository.findById(sku.getAttributedId()).orElseThrow(
                    () -> new RuntimeException("không tồn tại bản ghi")
                );
                AttributedValue attributedValue = attributedValueRepository.findById(sku.getAttributedValueId()).orElseThrow(
                    () -> new RuntimeException("không tồn tại bản ghi")
                );
                CopyProperty.CopyIgnoreNull(sku, skusDetails);
                skusDetails.setSkuId(savedSku.getId());
                skusDetails.setProductId(data.getId());
                skusDetails.setName(attributed.getName());
                skusDetails.setValue(attributedValue.getValue());
                productSkusDetailsRepository.save(skusDetails);
            });
        });
        return data;
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
