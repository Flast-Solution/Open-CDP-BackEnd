package vn.flast.service;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




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
import vn.flast.utils.NumberUtils;

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
            .integerEqualsTo("status", filter.status())
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

        boolean isEditQuantity = NumberUtils.isNotNull(input.getQuantity())
            && !input.getQuantity().equals(model.getQuantity());
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
        if(isEditQuantity){
            warehouseProductRepository.save(warehouseProduct);
        }
        if(NumberUtils.isNotNull(input.getStatus())) {
            model.setStatus(input.getStatus());
        }
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
