package vn.flast.domains.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.repositories.CustomerOrderStatusRepository;
import java.util.List;

@RestController
@RequestMapping("/order-status")
public class OrderStatusController {

    @Autowired
    private CustomerOrderStatusRepository statusRepository;

    @PostMapping(value = "/save")
    public MyResponse<?> create(@RequestBody List<CustomerOrderStatus> orderStatus) {
        var data = statusRepository.saveAll(orderStatus);
        return  MyResponse.response(data, "Cập nhật trạng thái thành công !");
    }

    @GetMapping(value = "/fetch")
    public MyResponse<?> fetch() {
        Sort sort = Sort.by(Sort.Direction.ASC, "order");
        return  MyResponse.response(statusRepository.findAll(sort));
    }
}
