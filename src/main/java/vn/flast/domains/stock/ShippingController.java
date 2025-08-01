package vn.flast.domains.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.ShippingHistory;
import vn.flast.models.ShippingStatus;
import vn.flast.service.ShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/fetch")
    public MyResponse<?> fetch(@RequestParam Integer page) {
        var ships = shippingService.fetch(page);
        return MyResponse.response(ships);
    }

    @PostMapping("/delivery")
    public MyResponse<?> delivery(@RequestBody ShippingHistory input){
        var data = shippingService.delivery(input);
        return MyResponse.response(data, "Giao hàng thành công !");
    }

    @PostMapping("/created-status")
    public MyResponse<?> createStatus(@RequestBody ShippingStatus input){
        var data = shippingService.createStatus(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-status")
    public MyResponse<?> fetchStatus(){
        var data = shippingService.fetchStatus();
        return MyResponse.response(data);
    }
}
