package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.ExportFilter;
import vn.flast.entities.ExportInput;
import vn.flast.entities.ExportItem;
import vn.flast.entities.ExportNotOrdrerInput;
import vn.flast.models.DetailItem;
import vn.flast.models.WareHouseHistory;
import vn.flast.models.WarehouseExport;
import vn.flast.models.WarehouseExportStatus;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseHistoryRepository;
import vn.flast.repositories.WarehouseExportRepository;
import vn.flast.repositories.WarehouseExportStatusRepository;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseExportService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final WarehouseExportRepository warehouseExportRepository;
    private final WarehouseExportStatusRepository warehouseExportStatusRepository;
    private final WareHouseHistoryRepository warehouseHistoryRepository;
    private final WarehouseService warehouseService;
    private final BaseController baseController;

    public WarehouseExport create(ExportInput order){
        var warehouseExportOld = warehouseExportRepository.findByOrderId(order.getOrderId());
        if(warehouseExportOld != null) {
            return warehouseExportOld;
        } else {
            List<DetailItem> items = order.getDetails().stream()
                .flatMap(detail -> detail.getItems().stream())
                .toList();
            WarehouseExport warehouseExport = new WarehouseExport();
            warehouseExport.setOrderId(order.getOrderId());
            warehouseExport.setNote(order.getNote());
            ExportItem exportItem = new ExportItem();
            exportItem.setDetaiItems(items);
            exportItem.setWarehouseDeliveryId(order.getWarehouseDeliveryId());
            exportItem.setStatus(order.getStatus());
            exportItem.setStt(1);
            var info = List.of(exportItem);
            warehouseExport.setItems(info);
            warehouseExport.setInfo(JsonUtils.toJson(info));
            return warehouseExportRepository.save(warehouseExport);
        }
    }

    public WarehouseExport createExportNotOrder(ExportNotOrdrerInput input){
        WarehouseExport warehouseExport = new WarehouseExport();
        warehouseExport.setWarehouseDeliveryId(input.getWarehouseDeliveryId());
        warehouseExport.setWarehouseReceivingId(input.getWarehouseReceivingId());
        warehouseExport.setNote(input.getNote());
        warehouseExport.setCreatedBy(new Date());
        warehouseExport.setType(WarehouseExport.TYPE_EXPORT_NOT_ORDER);

        ExportItem exportItem = new ExportItem();
        exportItem.setDetaiItems(input.getItems());
        exportItem.setStatus(input.getStatus());
        exportItem.setStt(1);
        var info = List.of(exportItem);
        warehouseExport.setItems(info);

        Integer statusConfirm = warehouseExportStatusRepository.findByType()
            .orElseThrow(() -> new RuntimeException("Chưa có trạng thái duyệt xuất kho !"))
            .getId();
        if(statusConfirm.equals(input.getStatus())) {
            exportItem.setStatusConfirm(WarehouseExportStatus.TYPE_CONFIRM);
            warehouseExport.setUserExport(baseController.getInfo().getId());
            for (DetailItem detailItem : input.getItems()) {
                var warehouse = warehouseService.findById(detailItem.getWarehouseId());
                if (detailItem.getQuantity() > warehouse.getQuantity()) {
                    throw new RuntimeException("Số lượng trong kho không đủ để xuất!");
                }
                warehouse.setQuantity(warehouse.getQuantity() - detailItem.getQuantity());
                warehouseService.updatedW(warehouse);
            }
            WareHouseHistory wareHouseHistory = new WareHouseHistory();
            wareHouseHistory.setCode("WH-" + System.currentTimeMillis());
            wareHouseHistory.setStockId(input.getWarehouseReceivingId());
            wareHouseHistory.setInfo(JsonUtils.toJson(input.getItems()));
            wareHouseHistory.setStatusConfirm(0);
            warehouseHistoryRepository.save(wareHouseHistory);
        }
        warehouseExport.setInfo(JsonUtils.toJson(info));
        return warehouseExportRepository.save(warehouseExport);
    }

    public WarehouseExport update(WarehouseExport input){
        var warehouseExport = warehouseExportRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
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
    private void exportProduct(List<ExportItem> items, Integer statusConfirm){
        var userName = baseController.getInfo();
        boolean isAdminOrWarehouse = userName.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")
                        || auth.getAuthority().equals("ROLE_WAREHOUSE"));
        for(ExportItem item : items){
            if (item.getStatus().equals(statusConfirm) && item.getStatusConfirm() == WarehouseExportStatus.TYPE_NOT_CONFIRM && isAdminOrWarehouse) {
                item.setStatusConfirm(WarehouseExportStatus.TYPE_CONFIRM);
                for (DetailItem detailItem : item.getDetaiItems()) {
                    var warehouse = warehouseService.findByStockAndSku(item.getWarehouseDeliveryId(), detailItem.getProductId(), detailItem.getSkuId());
                    if (detailItem.getQuantity() > warehouse.getQuantity()) {
                        throw new RuntimeException("Số lượng trong kho không đủ để xuất!");
                    }
                    warehouse.setQuantity(warehouse.getQuantity() - detailItem.getQuantity());
                    warehouseService.updatedW(warehouse);
                }
            }
        }
    }
    public Ipage<?> fetch(ExportFilter filter){
        var et = EntityQuery.create(entityManager, WarehouseExport.class);
        et.longEqualsTo("orderId", filter.getOrderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        lists.forEach(item -> {
            item.setItems(JsonUtils.Json2ListObject(item.getInfo(), ExportItem.class));
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
        return warehouseExportStatusRepository.findAll();
    }

    public WarehouseExport findOrderId(Long orderId){
        var warehouseExport = warehouseExportRepository.findByOrderId(orderId);
        if (warehouseExport != null) {
            warehouseExport.setItems(JsonUtils.Json2ListObject(warehouseExport.getInfo(), ExportItem.class));
            return warehouseExport;
        } else {
            return null;
        }
    }
}
