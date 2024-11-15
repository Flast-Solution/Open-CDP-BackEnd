package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.PriceRange;
import vn.flast.entities.SaleProduct;
import vn.flast.entities.SkuAttributed;
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
import vn.flast.searchs.ProductFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.SqlBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    @Autowired
    private SkuService skuService;

    public Product createdSeo(Product input){
        input.setImage(JsonUtils.toJson(input.getImageLists()));
        return productsRepository.save(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product createdProduct(SaleProduct input){
        if(input.getCode() == null) {
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
            // Đặt mã code vào đối tượng input
            input.setCode(code);
        }
        Product product = new Product();
        CopyProperty.CopyIgnoreNull(input, product);
        var data = productsRepository.save(product);

        /* Save Attributed */
        productAttributedRepository.deleteByProductId(data.getId());
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
        productPropertyRepository.deleteByProductId(data.getId());
        List<ProductProperty> productPropertyList = input.getListOpenInfo().stream().map(productProperty -> {
            ProductProperty property = new ProductProperty();
            CopyProperty.CopyIgnoreNull(productProperty, property);
            property.setName(productProperty.getName());
            property.setValue(productProperty.getValue());
            property.setProductId(data.getId());
            return property;
        }).toList();
        productPropertyRepository.saveAll(productPropertyList);
        saveSkuproduct(input.getSkus(), data.getId());
        return data;
    }

    public Product updated(Product input) {
        var entity = productsRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, entity);
        return productsRepository.save(entity);
    }

    public Ipage<?> fetch(ProductFilter filter){
        int LIMIT = filter.limit();
        int PAGE = filter.page();
        int OFFSET = (filter.page()) * LIMIT;
        final String totalSQL = "FROM `product` p ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("p.status", filter.status());
        sqlBuilder.addStringEquals("p.code", filter.code());
        sqlBuilder.addStringEquals("p.name", filter.name());
        sqlBuilder.addIntegerEquals("p.provider_id", filter.providerId());
        sqlBuilder.addOrderBy("ORDER BY p.id DESC");
        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);
        var nativeQuery = entityManager.createNativeQuery("SELECT p.* " + finalQuery, Product.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);
        var lists = EntityQuery.getListOfNativeQuery(nativeQuery, Product.class);
        List<SaleProduct> listSaleProduct = lists.stream().map(product -> {
            SaleProduct saleProduct = new SaleProduct();
            CopyProperty.CopyIgnoreNull(product, saleProduct);
            saleProduct.setListProperties(productAttributedRepository.findByProduct(product.getId()));
            saleProduct.setSkus(skuService.listProductSkuAndDetail(product.getId()));
            saleProduct.setListOpenInfo(productPropertyRepository.findByProductId(product.getId()));
            return saleProduct;
        }).toList();
        return  Ipage.generator(LIMIT, count, PAGE, listSaleProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = productsRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        productsRepository.delete(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSkuproduct(List<ProductSkus> input, Long productId){
        var productSkuOld = productSkusRepository.findByProductId(productId);
        Set<Long> inputSkuIds = input.stream()
                .map(ProductSkus::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Long> skusToDelete = productSkuOld.stream()
                .filter(sku -> !inputSkuIds.contains(sku.getId()))
                .map(ProductSkus::getId)
                .collect(Collectors.toList());
        if (!skusToDelete.isEmpty()) {
            productSkusRepository.updateDelProductSkus(productId,skusToDelete);
        }
        input.forEach(productSkus -> {
            ProductSkus skus = new ProductSkus();
            CopyProperty.CopyIgnoreNull(productSkus, skus);
            skus.setProductId(productId);
            ProductSkus savedSku = productSkusRepository.save(skus);
            /* Save Price Range */
            skusPriceRepository.deleteByProductId(productId);
            productSkus.getListPriceRange().forEach(priceRange -> {
                ProductSkusPrice price = new ProductSkusPrice();
                CopyProperty.CopyIgnoreNull(priceRange, price);
                price.setProductId(productId);
                price.setSkuId(savedSku.getId());
                price.setQuantityFrom(priceRange.getStart());
                price.setQuantityTo(priceRange.getEnd());
                price.setPrice(priceRange.getPrice());
                skusPriceRepository.save(price);
            });
            var skudetailold = productSkusDetailsRepository.findBySkuId(savedSku.getId());
            Set<Integer> inputSkudetailIds = productSkus.getSku().stream()
                    .map(SkuAttributed::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            List<Integer> skusDetailToDelete = skudetailold.stream()
                    .filter(sku -> !inputSkudetailIds.contains(sku.getId()))
                    .map(ProductSkusDetails::getId)
                    .collect(Collectors.toList());
            if (!skusToDelete.isEmpty()) {
                productSkusDetailsRepository.updateDelProductSkus(productId,skusDetailToDelete);
            }
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
                skusDetails.setProductId(productId);
                skusDetails.setName(attributed.getName());
                skusDetails.setValue(attributedValue.getValue());
                productSkusDetailsRepository.save(skusDetails);
            });
        });
    }

}
