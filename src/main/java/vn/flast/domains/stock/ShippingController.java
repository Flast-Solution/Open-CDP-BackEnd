package vn.flast.domains.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.ShippingHistory;
import vn.flast.models.ShippingStatus;
import vn.flast.searchs.ShipFilter;
import vn.flast.service.ShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/fetch")
    public MyResponse<?> fetch(ShipFilter filter) {
        var ships = shippingService.fetch(filter);
        return MyResponse.response(ships);
    }

    @PostMapping("/update")
    public MyResponse<?> update(@RequestBody ShippingHistory input) {
        var data = shippingService.update(input);
        return MyResponse.response(data, "Giao hàng thành công !");
    }

    @PostMapping("/created-status")
    public MyResponse<?> createStatus(@RequestBody ShippingStatus input) {
        var data = shippingService.createStatus(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-status")
    public MyResponse<?> fetchStatus() {
        var data = shippingService.fetchStatus();
        return MyResponse.response(data);
    }
}
