package vn.flast.domains.cskh;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.lead.LeadCareFilter;
import vn.flast.entities.lead.NoOrderFilter;
import vn.flast.models.DataCare;

@Log4j2
@RestController
@RequestMapping("/cs")
public class CsController extends BaseController {

    @Autowired
    private DataCareService dataCareService;

    @GetMapping("/3day-fetch")
    public MyResponse<?> find3day(NoOrderFilter filter){
        var data = dataCareService.fetchLead3Day(filter);
        return MyResponse.response(data);
    }

    @GetMapping("/ready")
    public MyResponse<?> ready(LeadCareFilter filter){
        var data = dataCareService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/3day-update")
    public MyResponse<?> update(@RequestBody DataCare input){
        var data = dataCareService.update3Day(input);
        return MyResponse.response(data, "Cập nhật LEAD 3 ngày thành công !");
    }

    @GetMapping("/co-hoi-order-fetch")
    public MyResponse<?> find7day(NoOrderFilter filter){
        var data = dataCareService.fetchCoHoiOrder(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/order-update")
    public MyResponse<?> updateOrder(@RequestBody DataCare input){
        var data = dataCareService.updatedOrder(input);
        return MyResponse.response(data, "Cập nhật Cơ hội / Đơn hàng thành công !");
    }
}
