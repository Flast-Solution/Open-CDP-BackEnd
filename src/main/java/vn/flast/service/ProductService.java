package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.SaleProduct;
import vn.flast.entities.SkuAttributed;
import vn.flast.models.Attributed;
import vn.flast.models.AttributedValue;
import vn.flast.models.Media;
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
import vn.flast.repositories.MediaRepository;
import vn.flast.repositories.ProductAttributedRepository;
import vn.flast.repositories.ProductPropertyRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.repositories.ProductSkusDetailsRepository;
import vn.flast.repositories.ProductSkusPriceRepository;
import vn.flast.repositories.ProductSkusRepository;
import vn.flast.repositories.ProviderRepository;
import vn.flast.repositories.WarehouseRepository;
import vn.flast.searchs.ProductFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
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

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Product createdSeo(Product input) {
        return productsRepository.save(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product saveProduct(SaleProduct input) {
        if(input.getCode() == null) {
            Provider provider = providerRepository.findById(input.getProviderId()).orElseThrow(
                () -> new RuntimeException("Nhà cung cấp chưa tồn tại")
            );
            String twoLetterService = provider.getName().substring(0, 2);
            String code;
            do {
                String random = Common.getAlphaNumericString(8, false);
                code = (twoLetterService + random).toUpperCase();
            } while (productsRepository.findByCode(code) != null);
            input.setCode(code);
        }
        Product product = new Product();
        CopyProperty.CopyIgnoreNull(input, product);
        var data = productsRepository.save(product);
        if(input.getSessionId() != 0) {
            updateMedia(input.getSessionId(), data.getId());
        }
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
            }
        )).toList();
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

        /* Save SKU */
        saveSkuProduct(input.getSkus(), data.getId());
        return data;
    }

    public Product updated(Product input) {
        var entity = productsRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, entity);
        return productsRepository.save(entity);
    }

    private void updateMedia(Long sessionId, Long id) {
        List<Media> mediaList = mediaService.listSesionId(sessionId);
        if (!mediaList.isEmpty()) {
            mediaList.forEach(media -> media.setObjectId(Math.toIntExact(id))); // Không cần chuyển `Long` -> `int`
            mediaRepository.saveAll(mediaList); // Batch update
        }
    }

    public SaleProduct findName(String name) {
        var entity = productsRepository.findByName(name).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        SaleProduct saleProduct = new SaleProduct();
        CopyProperty.CopyIgnoreNull(entity, saleProduct);
        saleProduct.setListProperties(productAttributedRepository.findByProduct(entity.getId()));
        saleProduct.setSkus(skuService.listProductSkuAndDetail(entity.getId()));
        saleProduct.setListOpenInfo(productPropertyRepository.findByProductId(entity.getId()));
        return saleProduct;
    }

    public SaleProduct findById(Long id) {
        var entity = productsRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        SaleProduct saleProduct = new SaleProduct();
        CopyProperty.CopyIgnoreNull(entity, saleProduct);
        saleProduct.setListProperties(productAttributedRepository.findByProduct(entity.getId()));
        saleProduct.setSkus(skuService.listProductSkuAndDetail(saleProduct.getId()));
        saleProduct.setListOpenInfo(productPropertyRepository.findByProductId(entity.getId()));
        saleProduct.setImageLists(mediaService.list(Math.toIntExact(id), "Product")
            .stream()
            .map(Media::getFileName).collect(Collectors.toList()));
        return saleProduct;
    }

    public Ipage<?> fetch(ProductFilter filter) {

        int LIMIT = filter.limit();
        int PAGE = filter.page();
        int OFFSET = (filter.page()) * LIMIT;

        final String totalSQL = "FROM `product` p ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        if(Objects.nonNull(filter.ids())) {
            sqlBuilder.addIn("p.id", filter.ids());
        }

        sqlBuilder.addIntegerEquals("p.status", filter.status());
        sqlBuilder.addIntegerEquals("p.service_id", filter.serviceId());
        sqlBuilder.addStringEquals("p.code", filter.code());
        sqlBuilder.like("p.name", filter.name());
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
            saleProduct.setWarehouses(warehouseRepository.findByProductId(product.getId()));
            saleProduct.setImageLists(new ArrayList<>());
            return saleProduct;
        }).toList();
        return Ipage.generator(LIMIT, count, PAGE, listSaleProduct);
    }

    public List<SaleProduct> findByListId(List<Long> ids) {
        var productList = productsRepository.findByListId(ids);
        List<SaleProduct> list = new ArrayList<>();
        for (Product product : productList) {
            SaleProduct saleProduct = new SaleProduct();
            CopyProperty.CopyIgnoreNull(product, saleProduct);
            saleProduct.setListProperties(productAttributedRepository.findByProduct(product.getId()));
            saleProduct.setSkus(skuService.listProductSkuAndDetail(product.getId()));
            saleProduct.setListOpenInfo(productPropertyRepository.findByProductId(product.getId()));
            saleProduct.setImageLists(mediaService.list(Math.toIntExact(product.getId()), "Product")
                .stream()
                .map(Media::getFileName).collect(Collectors.toList()));
            list.add(saleProduct);
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = productsRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        productsRepository.delete(data);
    }

    public void saveSkuProduct(List<ProductSkus> input, Long productId){
        var skuModels = productSkusRepository.findByProductId(productId);
        Set<Long> inputSkuIds = input.stream()
            .map(ProductSkus::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        List<Long> skusToDelete = skuModels.stream()
            .map(ProductSkus::getId)
            .filter(id -> !inputSkuIds.contains(id))
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
            skusPriceRepository.deleteBySkuId(savedSku.getId());
            productSkus.getListPriceRange().forEach(priceRange -> {
                ProductSkusPrice price = new ProductSkusPrice();
                CopyProperty.CopyIgnoreNull(priceRange, price);
                price.setProductId(productId);
                price.setSkuId(savedSku.getId());
                price.setPrice(priceRange.getPrice());
                skusPriceRepository.save(price);
            });
            var skuDetailOld = productSkusDetailsRepository.findBySkuId(savedSku.getId());
            Set<Integer> inputSkuDetailIds = productSkus.getSku().stream()
                .map(SkuAttributed::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            List<Integer> skusDetailToDelete = skuDetailOld.stream()
                .map(ProductSkusDetails::getId)
                .filter(id -> !inputSkuDetailIds.contains(id))
                .collect(Collectors.toList());
            if (!skusToDelete.isEmpty()) {
                productSkusDetailsRepository.updateDelProductSkus(productId, skusDetailToDelete);
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
