package vn.flast.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.ProductAttributed;
import vn.flast.models.WareHouseHistory;
import vn.flast.service.ProductAttributedService;
import vn.flast.service.WarehouseHistoryService;

@RestController
@RequestMapping("/warehouse-history")
public class WarehouseHistoryController {

    @Autowired
    private WarehouseHistoryService warehouseHistoryService;

    @PostMapping("/created")
    public MyResponse<?> created(@RequestBody WareHouseHistory input) {
        var data = warehouseHistoryService.created(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> updated(@RequestBody WareHouseHistory input) {
        var data = warehouseHistoryService.updated(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(@RequestParam Integer page) {
        var data = warehouseHistoryService.fetch(page);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        warehouseHistoryService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
