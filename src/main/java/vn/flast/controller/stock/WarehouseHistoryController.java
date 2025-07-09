package vn.flast.controller.stock;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.WarehouseHistoryFilter;
import vn.flast.models.WareHouseHistory;
import vn.flast.models.WareHouseStatus;
import vn.flast.service.WarehouseHistoryService;
import vn.flast.service.WarehouseService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/warehouse-history")
public class WarehouseHistoryController {

    @Autowired
    private WarehouseHistoryService warehouseHistoryService;

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/created")
    public MyResponse<?> created(@Valid @RequestBody WareHouseHistory input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = warehouseHistoryService.created(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> updated(@RequestBody WareHouseHistory input) {
        var data = warehouseHistoryService.updated(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(WarehouseHistoryFilter filter) {
        var data = warehouseHistoryService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        warehouseHistoryService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }

    @PostMapping("/created-status")
    public MyResponse<?> createStatus(@RequestBody WareHouseStatus input){
        var data = warehouseService.createStatus(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-status")
    public MyResponse<?> fetchStatus(){
        var data = warehouseService.fetchStatus();
        return MyResponse.response(data);
    }
}
