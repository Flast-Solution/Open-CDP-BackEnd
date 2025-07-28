package vn.flast.domains.stock.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.entities.warehouse.WarehouseHistoryFilter;
import vn.flast.models.WareHouseHistory;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseHistoryRepository;
import vn.flast.repositories.WarehouseStockRepository;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

@Service
@RequiredArgsConstructor
public class WarehouseHistoryService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final WareHouseHistoryRepository wareHouseHistoryRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    @Transactional(rollbackFor = Exception.class)
    public WareHouseHistory created(WareHouseHistory input) {
        var stock = warehouseStockRepository.findById(input.getStockId()).orElseThrow(
            () -> new RuntimeException("record does not exist.")
        );
        input.setStockName(stock.getName());
        input.setSkuInfo(JsonUtils.toJson(input.getItems()));
        return wareHouseHistoryRepository.save(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public WareHouseHistory updated(WareHouseHistory input) {
        throw new RuntimeException("Not Subport API !");
    }

    public Ipage<?> fetch(WarehouseHistoryFilter filter) {
        var et = EntityQuery.create(entityManager, WareHouseHistory.class);
        et.integerEqualsTo("status", filter.getStatus());
        et.longEqualsTo("productId", filter.getProductId());
        et.longEqualsTo("providerId", filter.getProviderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        lists.forEach(item -> item.setItems(JsonUtils.Json2ListObject(item.getSkuInfo(), SkuDetails.class)));
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = wareHouseHistoryRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseHistoryRepository.delete(data);
    }
}
