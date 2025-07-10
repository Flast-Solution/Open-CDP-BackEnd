package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.warehouse.SaveStock;
import vn.flast.models.WareHouseStatus;
import vn.flast.models.WareHouseStock;
import vn.flast.models.Warehouse;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseStatusRepository;
import vn.flast.repositories.WarehouseRepository;
import vn.flast.repositories.WarehouseStockRepository;
import vn.flast.searchs.WarehouseFilter;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WarehouseService extends BaseController {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private WarehouseRepository wareHouseRepository;

    @Autowired
    private WareHouseStatusRepository wareHouseStatusRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

    @Autowired
    private ProductService productService;

    public Warehouse created(SaveStock saveStock) {
        var input = saveStock.model();
        input.setUserName(getUserSso());
        input.setSkuInfo(JsonUtils.toJson(saveStock.skuDetails()));
        return wareHouseRepository.save(input);
    }

    public Warehouse updated(SaveStock saveStock) {
        var input = saveStock.model();
        var warehouse = wareHouseRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouse);
        warehouse.setSkuInfo(JsonUtils.toJson(saveStock.skuDetails()));
        return wareHouseRepository.save(warehouse);
    }

    public Ipage<?> fetch(WarehouseFilter filter) {
        int LIMIT = 10;
        int currentPage = filter.page();

        var et = EntityQuery.create(entityManager, Warehouse.class);
        if(NumberUtils.isNotNull(filter.productId())) {
            LIMIT = 100;
            et.addDescendingOrderBy("id");
        }

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
            return null;
        }
        if(input.getType() == WareHouseStatus.TYPE_CONFIRM){
            List<WareHouseStatus> statuses = wareHouseStatusRepository.findAll();
            for(WareHouseStatus status : statuses){
                if(status.getType() == WareHouseStatus.TYPE_CONFIRM){
                    status.setType(WareHouseStatus.TYPE_NOT_CONFIRM);
                    wareHouseStatusRepository.save(status);
                }
            }
        }
        return wareHouseStatusRepository.save(input);
    }

    public Warehouse findById(Integer id) {
        return wareHouseRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
    }

    public Warehouse findByStockAndSku(Integer stockId, Long productId, Long skuId){
        return wareHouseRepository.findProductSku(productId, skuId, stockId);
    }

    public Map<Integer, Warehouse> findByIds(List<Integer> ids) {
        List<Warehouse> warehouses = wareHouseRepository.findByIds(ids);
        return warehouses.stream().collect(Collectors.toMap(Warehouse::getId, Function.identity()));
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
