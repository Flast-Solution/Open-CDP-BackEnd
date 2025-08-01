package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.domains.stock.WarehouseService;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.ShippingHistory;
import vn.flast.models.ShippingStatus;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ShippingHistoryRepository;
import vn.flast.repositories.ShippingStatusRepository;
import vn.flast.repositories.TransporterRepository;
import vn.flast.repositories.WarehouseProductRepository;
import vn.flast.utils.Common;
import vn.flast.utils.EntityQuery;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final ShippingStatusRepository shippingStatusRepository;
    private final ShippingHistoryRepository shippingHistoryRepository;
    private final WarehouseProductRepository warehouseProductRepository;
    private final TransporterRepository transporterRepository;
    private final WarehouseService warehouseService;

    public Ipage<?> fetch(Integer page) {

        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, ShippingHistory.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();

        var whIds = lists.stream().map(ShippingHistory::getWarehouseId).toList();
        var whProducts = warehouseProductRepository.findAllById(whIds);
        warehouseService.appendFieldTransient(whProducts);

        for(ShippingHistory history : lists) {
            var whProduct = whProducts.stream()
            .filter(i -> i.getId().equals(history.getWarehouseId()))
            .findFirst();
            whProduct.ifPresent(history::setWarehouseProduct);
        }
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    public ShippingHistory delivery(ShippingHistory input) {
        var warehouseProduct = warehouseProductRepository.findById(input.getWarehouseId()).orElseThrow(
            () -> new ResourceNotFoundException("")
        );
        if(warehouseProduct.getQuantity() < input.getQuantity()) {
            throw new RuntimeException("Sai số lượng ần giao không đủ trong kho !");
        }
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() - input.getQuantity());
        warehouseProductRepository.save(warehouseProduct);

        var transporter = transporterRepository.findById(input.getTransporterId()).orElseThrow();
        input.setTransportName(transporter.getName());
        input.setSsoId(Common.getSsoId());

        shippingHistoryRepository.save(input);
        return input;
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
