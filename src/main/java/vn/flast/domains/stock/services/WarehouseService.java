package vn.flast.domains.stock.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.BaseController;
import vn.flast.entities.warehouse.SaveStock;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.models.*;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.*;
import vn.flast.searchs.WarehouseFilter;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService extends BaseController {

    @PersistenceContext
    protected EntityManager entityManager;

    private final WarehouseProductRepository wareHouseRepository;
    private final WareHouseStatusRepository wareHouseStatusRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;

    public WarehouseProduct created(SaveStock saveStock) {
        var input = saveStock.model();
        input.setUserName(getUserSso());
        input.setSkuInfo(JsonUtils.toJson(saveStock.mSkuDetails()));

        WareHouseStock stock = warehouseStockRepository.findById(input.getStockId()).orElseThrow(
            () -> new RuntimeException("Kho không tồn tại !")
        );
        input.setStockName(stock.getName());
        return wareHouseRepository.save(input);
    }

    public WarehouseProduct updated(SaveStock saveStock) {
        var input = saveStock.model();
        var warehouse = wareHouseRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouse);

        WareHouseStock stock = warehouseStockRepository.findById(input.getStockId()).orElseThrow(
            () -> new RuntimeException("Kho không tồn tại !")
        );
        warehouse.setStockName(stock.getName());
        warehouse.setSkuInfo(JsonUtils.toJson(saveStock.mSkuDetails()));
        return wareHouseRepository.save(warehouse);
    }

    public WarehouseProduct updated(WarehouseProduct model) {
        return wareHouseRepository.save(model);
    }

    public Ipage<?> fetch(WarehouseFilter filter) {
        int LIMIT = filter.limit();
        int currentPage = filter.page();

        var et = EntityQuery.create(entityManager, WarehouseProduct.class);
        et.addDescendingOrderBy("id")
            .integerEqualsTo("productId", filter.productId())
            .integerEqualsTo("skuId", filter.skuId())
            .integerEqualsTo("providerId", filter.providerId())
            .integerEqualsTo("stockId", filter.stockId())
            .setMaxResults(LIMIT)
            .setFirstResult(LIMIT * currentPage);
        var lists = et.list();

        List<Long> pIds = lists.stream().map(WarehouseProduct::getProductId).toList();
        List<Product> products = productRepository.findByListId(pIds);
        Map<Long, Product> mProducts = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        List<Long> providerIds = lists.stream().map(WarehouseProduct::getProviderId).toList();
        List<Provider> providers = providerRepository.findByListId(providerIds);
        Map<Long, String> mProviders = providers.stream().collect(Collectors.toMap(Provider::getId, Provider::getName));

        for(WarehouseProduct whProduct : lists) {
            whProduct.setSkuDetails(JsonUtils.Json2ListObject(whProduct.getSkuInfo(), SkuDetails.class));
            Product product = mProducts.get(whProduct.getProductId());
            whProduct.setProduct(product);
            String mPName = mProviders.get(whProduct.getProviderId());
            whProduct.setProviderName(mPName);
        }
        return Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        var data = wareHouseRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseRepository.delete(data);
    }

    public WareHouseStatus createStatus(WareHouseStatus input) {
        if(wareHouseStatusRepository.existsByName(input.getName())){
            throw new RuntimeException("Trạng thái kho đã tồn tại rồi !");
        }
        return wareHouseStatusRepository.save(input);
    }

    public WarehouseProduct findById(Integer id) {
        return wareHouseRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
    }

    public Map<Integer, WarehouseProduct> findByIds(List<Integer> ids) {
        List<WarehouseProduct> warehouses = wareHouseRepository.findByIds(ids);
        return warehouses.stream().collect(Collectors.toMap(WarehouseProduct::getId, Function.identity()));
    }

    public List<WareHouseStatus> fetchStatus(){
        return wareHouseStatusRepository.findAll();
    }

    public void createStock(WareHouseStock input){
        if(warehouseStockRepository.existsByName(input.getName())){
            return;
        }
        warehouseStockRepository.save(input);
    }

    public List<WareHouseStock> fetchStock(){
        return warehouseStockRepository.findAll();
    }

    public void updateStock(WareHouseStock input){
        var stock = warehouseStockRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("record does not exist")
        );
        CopyProperty.CopyIgnoreNull(input, stock);
        warehouseStockRepository.save(stock);
    }
}
