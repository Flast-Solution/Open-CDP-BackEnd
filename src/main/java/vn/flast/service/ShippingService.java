package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.components.RecordNotFoundException;
import vn.flast.domains.stock.WarehouseService;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.ShippingHistory;
import vn.flast.models.ShippingStatus;
import vn.flast.models.WarehouseProduct;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ShippingHistoryRepository;
import vn.flast.repositories.ShippingStatusRepository;
import vn.flast.repositories.WarehouseProductRepository;
import vn.flast.searchs.ShipFilter;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.MapUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShippingService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final ShippingStatusRepository shippingStatusRepository;
    private final ShippingHistoryRepository shippingHistoryRepository;
    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseService warehouseService;

    public Ipage<?> fetch(ShipFilter filter) {
        int LIMIT = 10;
        int PAGE = filter.page();
        var et = EntityQuery.create(entityManager, ShippingHistory.class);
        et.stringEqualsTo("orderCode", filter.orderCode())
            .stringEqualsTo("detailCode", filter.detailCode())
            .integerEqualsTo("transporterId", filter.transporterId())
            .stringEqualsTo("transporterCode", filter.transporterCode())
            .setMaxResults(LIMIT)
            .setFirstResult(LIMIT * PAGE);
        if(Objects.nonNull(filter.from()) && Objects.nonNull(filter.to())) {
            et.dateBetween("inTime", filter.from(), filter.to());
        }
        var lists = et.list();

        var whIds = lists.stream().map(ShippingHistory::getWarehouseId).toList();
        var whProducts = warehouseProductRepository.findAllById(whIds);

        warehouseService.appendFieldTransient(whProducts);
        Map<Integer, WarehouseProduct> mProducts = MapUtils.toIdentityMap(whProducts, WarehouseProduct::getId);
        for(ShippingHistory history : lists) {
            history.setWarehouseProduct(mProducts.get(history.getWarehouseId()));
        }
        return  Ipage.generator(LIMIT, et.count(), PAGE, lists);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ShippingHistory update(ShippingHistory input) {
        var model = shippingHistoryRepository.findById(input.getId()).orElseThrow(
            () -> new RecordNotFoundException("Record not found")
        );
        var warehouseProduct = warehouseProductRepository.findById(input.getWarehouseId()).orElseThrow(
            () -> new ResourceNotFoundException("")
        );

        boolean isEditQuantity = !input.getQuantity().equals(model.getQuantity());
        if(isEditQuantity && input.getQuantity() < model.getQuantity()) {
            int revert = model.getQuantity() - input.getQuantity();
            warehouseProduct.setQuantity(warehouseProduct.getQuantity() + revert);
        } else if(isEditQuantity) {
            int shipAdd = input.getQuantity() - model.getQuantity();
            if(warehouseProduct.getQuantity() < shipAdd) {
                throw new RuntimeException("Không đủ số lượng giao thêm");
            }
            warehouseProduct.setQuantity(warehouseProduct.getQuantity() - shipAdd);
        }
        warehouseProductRepository.save(warehouseProduct);
        shippingHistoryRepository.save(model);
        return model;
    }

    public ShippingStatus createStatus(ShippingStatus input){
        if(shippingStatusRepository.existsByName(input.getName())){
            return null;
        }
        return shippingStatusRepository.save(input);
    }

    public List<ShippingStatus> fetchStatus() {
        return shippingStatusRepository.findAll();
    }
}
