package vn.flast.domains.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.order.OrderCare;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderNote;
import vn.flast.searchs.OrderFilter;

@Log4j2
@RestController
@RequestMapping("/customer-order")
@RequiredArgsConstructor
public class CSKHController {

    private final OrderService orderService;

    @GetMapping("/fetch-co-hoi")
    public MyResponse<?> fetchCoHoi(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_CO_HOI);
        return MyResponse.response(orderService.fetchList(updatedFilter));
    }

    @GetMapping("/fetch-co-hoi-not-care")
    public MyResponse<?> fetchCoHoiNotCare(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_CO_HOI);
        return MyResponse.response(orderService.fetchListCoHoiNotCare(updatedFilter));
    }

    @GetMapping("/fetch-co-hoi-care")
    public MyResponse<?> fetchCoHoiCare(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_CO_HOI);
        return MyResponse.response(orderService.fetchListCoHoiCare(updatedFilter));
    }

    @GetMapping("/find-co-hoi-care")
    public MyResponse<?> findCoHoiCare(@RequestParam Long orderId) {
        var order = orderService.findById(orderId);
        return MyResponse.response(order);
    }

    @PostMapping("/take-care-co-hoi")
    public MyResponse<?> takeCareCoHoi(@RequestBody OrderCare input){
        input.setType(CustomerOrderNote.TYPE_COHOI);
        var data = orderService.takeCareNoteCoHoi(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-order-completed")
    public MyResponse<?> fetchOrderCompleted(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        return MyResponse.response(orderService.fetchLisOrderNotCare(updatedFilter));
    }

    @GetMapping("/fetch-order-cancel")
    public MyResponse<?> fetchOrderCancel(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        return MyResponse.response(orderService.fetchLisOrderCancel(updatedFilter));
    }

    @PostMapping("/take-care-order")
    public MyResponse<?> takeCareOrder(@RequestBody OrderCare input){
        input.setType(CustomerOrderNote.TYPE_ORDER);
        var data = orderService.takeCareNoteCoHoi(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-order-take-care")
    public MyResponse<?> fetchOrderTakeCare(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        return MyResponse.response(orderService.fetchLisOrderCare(updatedFilter));
    }
}
