package vn.flast.controller.stock;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.ExportFilter;
import vn.flast.entities.ExportInput;
import vn.flast.entities.ExportNotOrdrerInput;
import vn.flast.entities.MyResponse;
import vn.flast.models.WarehouseExport;
import vn.flast.models.WarehouseExportStatus;
import vn.flast.service.WarehouseExportService;

@RestController
@RequestMapping("/warehouse-export")
@RequiredArgsConstructor
public class WarehouseExportController {

    private final WarehouseExportService warehouseExportService;

    @PostMapping("/created")
    public MyResponse<?> create(@RequestBody ExportInput input) {
        var data = warehouseExportService.create(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/created-not-order")
    public MyResponse<?> create(@RequestBody ExportNotOrdrerInput input) {
        var data = warehouseExportService.createExportNotOrder(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> update(@RequestBody WarehouseExport input) {
        var data = warehouseExportService.update(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(ExportFilter filter) {
        var data = warehouseExportService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/created-status")
    public MyResponse<?> createStatus(@RequestBody WarehouseExportStatus input){
        var data = warehouseExportService.createStatus(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-status")
    public MyResponse<?> fetchStatus(){
        var data = warehouseExportService.fetchStatus();
        return MyResponse.response(data);
    }

    @GetMapping("/find-order-id")
    public MyResponse<?> findOrderId(@RequestParam Long orderId){
        var data = warehouseExportService.findOrderId(orderId);
        return MyResponse.response(data);
    }

}
