package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.WarehouseHistoryFilter;
import vn.flast.models.WareHouseHistory;
import vn.flast.models.Warehouse;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseHistoryRepository;
import vn.flast.repositories.WarehouseRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
@RequiredArgsConstructor
public class WarehouseHistoryService {

    @PersistenceContext
    protected EntityManager entityManager;


    private final WareHouseHistoryRepository wareHouseHistoryRepository;

    private final WarehouseRepository warehouseRepository;

    @Transactional(rollbackFor = Exception.class)
    public WareHouseHistory created(WareHouseHistory input){
        var warehouseOld = warehouseRepository.findProductStock(input.getProviderId(), input.getProductId(), input.getSkuId());
        if(warehouseOld == null){
            Warehouse warehouse = new Warehouse();
            CopyProperty.CopyIgnoreNull(input, warehouse);
            warehouse.setTotal(input.getQuality());
            warehouseRepository.save(warehouse);
        }
        else {
            warehouseOld.setTotal(warehouseOld.getTotal() + input.getQuality());
            warehouseRepository.save(warehouseOld);
        }
        return wareHouseHistoryRepository.save(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public WareHouseHistory updated(WareHouseHistory input) {
        var warehouseHistory = wareHouseHistoryRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouseHistory);
        return wareHouseHistoryRepository.save(warehouseHistory);
    }

    public Ipage<?> fetch(WarehouseHistoryFilter filter){
        var et = EntityQuery.create(entityManager, WareHouseHistory.class);
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        return  Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = wareHouseHistoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseHistoryRepository.delete(data);
    }
}
