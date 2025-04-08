package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.SaleProduct;
import vn.flast.models.Product;
import vn.flast.models.StatusOrder;
import vn.flast.models.Stock;
import vn.flast.models.WareHouseStatus;
import vn.flast.models.WareHouseStock;
import vn.flast.models.Warehouse;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.StockRepository;
import vn.flast.repositories.WareHouseStatusRepository;
import vn.flast.repositories.WarehouseRepository;
import vn.flast.repositories.WarehouseStockRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private WarehouseRepository wareHouseRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WareHouseStatusRepository wareHouseStatusRepository;

    @Autowired
    private WarehouseStockRepository warehouseStockRepository;

    @Autowired
    private ProductService productService;


    public Warehouse created(Warehouse input){
        return wareHouseRepository.save(input);
    }

    public Warehouse updated(Warehouse input) {
        var warehouse = wareHouseRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouse);
        var data = wareHouseRepository.save(warehouse);
        return data;
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, Warehouse.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        List<Long> idProducts = lists.stream().map(Warehouse::getProductId).collect(Collectors.toList());
        List<SaleProduct> products = productService.findByListId(idProducts);
        Map<Long, SaleProduct> productMap = products.stream()
                .collect(Collectors.toMap(SaleProduct::getId, Function.identity()));

        // 5️⃣ Gán Product vào Warehouse
        lists.forEach(warehouse -> {
            if (productMap.containsKey(warehouse.getProductId())) {
                warehouse.setProduct(productMap.get(warehouse.getProductId()));
            }
        });
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = wareHouseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseRepository.delete(data);
    }

    public WareHouseStatus createStatus(WareHouseStatus input){
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

    public Map<Integer, Warehouse> findByIds(List<Integer> ids) {
        List<Warehouse> warehouses = wareHouseRepository.findByIds(ids);
        return warehouses.stream().collect(Collectors.toMap(Warehouse::getId, Function.identity()));
    }
    public List<WareHouseStatus> fetchStatus(){
        var data = wareHouseStatusRepository.findAll();
        return data;
    }

    public void createStock(WareHouseStock input){
        if(warehouseStockRepository.existsByName(input.getName())){
            return;
        }
        warehouseStockRepository.save(input);
    }

    public List<WareHouseStock> fetchStock(){
        var data = warehouseStockRepository.findAll();
        return data;
    }

    public void updateStock(WareHouseStock input){
        var stock = warehouseStockRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("record does not exist")
        );
        CopyProperty.CopyIgnoreNull(input, stock);
        warehouseStockRepository.save(stock);
    }
}
