package vn.flast.domains.stock.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.BaseController;
import vn.flast.entities.warehouse.SaveStock;
import vn.flast.models.WareHouseStatus;
import vn.flast.models.WareHouseStock;
import vn.flast.models.WarehouseProduct;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseStatusRepository;
import vn.flast.repositories.WarehouseProductRepository;
import vn.flast.repositories.WarehouseStockRepository;
import vn.flast.searchs.WarehouseFilter;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WarehouseService extends BaseController {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private WarehouseProductRepository wareHouseRepository;

    @Autowired
    private WareHouseStatusRepository wareHouseStatusRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

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
        et.addDescendingOrderBy("id");
        et.integerEqualsTo("productId", filter.productId())
            .setMaxResults(LIMIT)
            .setFirstResult(LIMIT * currentPage);
        var lists = et.list();
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

    public WarehouseProduct findByStockAndSku(Integer stockId, Long productId, Long skuId){
        return wareHouseRepository.findProductSku(productId, skuId, stockId);
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
