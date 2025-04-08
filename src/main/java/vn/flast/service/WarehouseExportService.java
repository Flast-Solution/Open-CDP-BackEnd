package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.entities.ExportFilter;
import vn.flast.entities.ExportInput;
import vn.flast.entities.ExportItem;
import vn.flast.models.DetailItem;
import vn.flast.models.Warehouse;
import vn.flast.models.WarehouseExport;
import vn.flast.models.WarehouseExportStatus;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WarehouseExportRepository;
import vn.flast.repositories.WarehouseExportStatusRepository;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseExportService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final WarehouseExportRepository warehouseExportRepository;

    private final WarehouseExportStatusRepository warehouseExportStatusRepository;

    private final WarehouseService warehouseService;


    public WarehouseExport create(ExportInput order){
        List<DetailItem> items = order.getDetails().stream()
                .flatMap(detail -> detail.getItems().stream()).toList();
        WarehouseExport warehouseExport = new WarehouseExport();
        warehouseExport.setOrderId(order.getOrderId());
        warehouseExport.setNote(order.getNote());
        ExportItem exportItem = new ExportItem();
        exportItem.setDetaiItems(items);
        exportItem.setStt(1);
        warehouseExport.setItems(List.of(exportItem));
        warehouseExport.setInfo(JsonUtils.toJson(items));
        return warehouseExportRepository.save(warehouseExport);
    }

    public WarehouseExport update(WarehouseExport input){
        var warehouseExport = warehouseExportRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        warehouseExport.setStatus(warehouseExport.getStatus());
        warehouseExport.setNote(warehouseExport.getNote());
        List<ExportItem> items = input.getItems();
        int maxStt = items.stream()
                .filter(item -> item.getStt() != null)
                .mapToInt(ExportItem::getStt)
                .max()
                .orElse(0);
        for (ExportItem item : items) {
            if (item.getStt() == null) {
                item.setStt(++maxStt);
            }
        }
        warehouseExport.setInfo(JsonUtils.toJson(input.getItems()));
        return warehouseExportRepository.save(warehouseExport);

    }


    public Ipage<?> fetch(ExportFilter filter){
        var et = EntityQuery.create(entityManager, WarehouseExport.class);
        et.integerEqualsTo("warehouseId", filter.getWarehouseId());
        et.longEqualsTo("orderId", filter.getOrderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        List<Integer> warehouseIds = lists.stream().map(WarehouseExport::getWarehouseId).collect(Collectors.toList());
        Map<Integer, Warehouse> warehouseMap = warehouseService.findByIds(warehouseIds);
        lists.forEach(item -> {
            item.setItems(JsonUtils.Json2ListObject(item.getInfo(), ExportItem.class));
            item.setWarehouse(warehouseMap.get(item.getWarehouseId()));
        });
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }

    public WarehouseExportStatus createStatus(WarehouseExportStatus input){
        if(warehouseExportStatusRepository.existsByName(input.getName())){
            return null;
        }
        if(input.getType() == WarehouseExportStatus.TYPE_CONFIRM){
            List<WarehouseExportStatus> statuses = warehouseExportStatusRepository.findAll();
            for(WarehouseExportStatus status : statuses){
                if(status.getType() == WarehouseExportStatus.TYPE_CONFIRM){
                    status.setType(WarehouseExportStatus.TYPE_NOT_CONFIRM);
                    warehouseExportStatusRepository.save(status);
                }
            }
        }
        return warehouseExportStatusRepository.save(input);
    }

    public List<WarehouseExportStatus> fetchStatus(){
        var data = warehouseExportStatusRepository.findAll();
        return data;
    }
}
