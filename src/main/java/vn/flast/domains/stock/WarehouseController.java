package vn.flast.domains.stock;
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
import vn.flast.entities.warehouse.Delivery;
import vn.flast.entities.warehouse.SaveStock;
import vn.flast.models.WareHouseStock;
import vn.flast.models.WarehouseExchange;
import vn.flast.searchs.WarehouseFilter;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/created")
    public MyResponse<?> created(@Valid @RequestBody SaveStock saveStock, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(500, "Lỗi tham số đầu vào", newErrors);
        }
        var data = warehouseService.created(saveStock);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> updated(@Valid @RequestBody SaveStock saveStock, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(500, "Lỗi tham số đầu vào", newErrors);
        }
        var data = warehouseService.updated(saveStock);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(WarehouseFilter filter) {
        var data = warehouseService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/exchange")
    public MyResponse<?> exchange(@RequestBody WarehouseExchange input) {
        var model = warehouseService.exchange(input);
        return MyResponse.response(model, "Xuất kho thành công !");
    }

    @PostMapping("/delivery")
    public MyResponse<?> delivery(@RequestBody @Valid Delivery input) {
        var model = warehouseService.delivery(input);
        return MyResponse.response(model, "Giao kho thành công !");
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        warehouseService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }

    @GetMapping("/fetch-stock")
    public MyResponse<?> fetchStock() {
        var data = warehouseService.fetchStock();
        return MyResponse.response(data);
    }

    @PostMapping("/created-stock")
    public MyResponse<?> createdStock(@RequestBody WareHouseStock input) {
        warehouseService.createStock(input);
        return MyResponse.response("oke");
    }

    @PostMapping("/update-stock")
    public MyResponse<?> updateStock(@RequestBody WareHouseStock input) {
        warehouseService.updateStock(input);
        return MyResponse.response("oke");
    }
}
