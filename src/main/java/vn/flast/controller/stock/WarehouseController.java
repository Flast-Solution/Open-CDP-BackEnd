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
import vn.flast.models.WareHouseStock;
import vn.flast.models.Warehouse;
import vn.flast.service.WarehouseService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/created")
    public MyResponse<?> created(@Valid @RequestBody Warehouse input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = warehouseService.created(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> updated(@Valid @RequestBody Warehouse input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = warehouseService.updated(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(@RequestParam Integer page) {
        var data = warehouseService.fetch(page);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        warehouseService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }

    @GetMapping("/fetch-stock")
    public MyResponse<?> fetchStock(){
        var data = warehouseService.fetchStock();
        return MyResponse.response(data);
    }

    @PostMapping("/created-stock")
    public MyResponse<?> createdStock(@RequestBody WareHouseStock input){
        warehouseService.createStock(input);
        return MyResponse.response("oke");
    }

    @PostMapping("/update-stock")
    public MyResponse<?> updateStock(@RequestBody WareHouseStock input){
        warehouseService.updateStock(input);
        return MyResponse.response("oke");
    }
}
